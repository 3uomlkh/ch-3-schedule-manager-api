package com.example.repository;

import com.example.dto.schedule.ScheduleRequestDto;
import com.example.dto.schedule.ScheduleResponseDto;
import com.example.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("scheduleId");

        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule.getTask());
        parameters.put("writer", schedule.getWriter());
        parameters.put("password", schedule.getPassword());
        parameters.put("createdAt", now);
        parameters.put("updatedAt", now);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(), schedule.getTask(), schedule.getWriter(), now, now);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("SELECT * FROM schedules", scheduleRowMapper());
    }

    @Override
    public List<ScheduleResponseDto> findSchedules(String writer, String updatedAt) {
        StringBuilder query = new StringBuilder("SELECT * FROM schedules WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (writer != null) {
            query.append(" AND writer = ?");
            params.add(writer);
        }

        if (updatedAt != null) {
            query.append(" AND DATE(updatedAt) = ?");
            params.add(updatedAt);
        }

        query.append(" ORDER BY DATE(updatedAt) DESC");

        return jdbcTemplate.query(
                query.toString(),
                scheduleRowMapper(),
                params.toArray()
        );
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        List<ScheduleResponseDto> query = jdbcTemplate.query(
                "SELECT * FROM schedules WHERE scheduleId = ?",
                scheduleRowMapper(),
                scheduleId
        );
        return query.isEmpty() ? null : query.get(0);
    }

    @Override
    public int updateSchedule(Long scheduleId, Schedule schedule) {
        StringBuilder query = new StringBuilder("UPDATE schedules SET");
        List<Object> params = new ArrayList<>();
        boolean firstField = true;

        if (schedule.getTask() != null) {
            query.append(" task = ?");
            params.add(schedule.getTask());
            firstField = false;
        }

        if (schedule.getWriter() != null) {
            if (!firstField) query.append(",");
            query.append(" writer = ?");
            params.add(schedule.getWriter());
        }

        query.append(" WHERE scheduleId = ?");
        params.add(scheduleId);

        if (params.size() == 1) { // scheduleId만 있는 경우 (업데이트할 내용 없음)
            throw new IllegalArgumentException("업데이트할 필드가 없습니다.");
        }

        return jdbcTemplate.update(query.toString(), params.toArray());
    }

    @Override
    public void deleteSchedule(Long id) {

    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("scheduleId"),
                rs.getString("task"),
                rs.getString("writer"),
                rs.getObject("createdAt", LocalDateTime.class),
                rs.getObject("updatedAt", LocalDateTime.class)
        );
    }
}
