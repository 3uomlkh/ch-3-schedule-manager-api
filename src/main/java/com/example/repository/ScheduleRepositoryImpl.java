package com.example.repository;

import com.example.dto.schedule.ScheduleResponseDto;
import com.example.entity.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("scheduleId");

        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule.getTask());
        parameters.put("userId", schedule.getUserId());
        parameters.put("password", schedule.getPassword());
        parameters.put("createdAt", now);
        parameters.put("updatedAt", now);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // userId에 해당하는 name 조회
        String findUserQuery = "SELECT name FROM users WHERE userId = ?";
        String writerName = jdbcTemplate.queryForObject(findUserQuery, String.class, schedule.getUserId());

        return new ScheduleResponseDto(key.longValue(), schedule.getTask(), writerName, now, now);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(int page, int size) {
        String query = getScheduleWithUserQuery() + " ORDER BY DATE(s.updatedAt) DESC LIMIT ? OFFSET ?";
        int offset = page * size;
        return jdbcTemplate.query(
                query,
                scheduleRowMapper(),
                size, offset
        );
    }

    @Override
    public List<ScheduleResponseDto> findSchedules(String writer, String updatedAt, int page, int size) {
        StringBuilder query = new StringBuilder(getScheduleWithUserQuery());
        query.append(" WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (writer != null) {
            query.append(" AND u.name = ?");
            params.add(writer);
        }

        if (updatedAt != null) {
            query.append(" AND DATE(s.updatedAt) = ?");
            params.add(updatedAt);
        }

        query.append(" ORDER BY DATE(s.updatedAt) DESC");

        query.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add(page * size);

        return jdbcTemplate.query(
                query.toString(),
                scheduleRowMapper(),
                params.toArray()
        );
    }

    @Override
    public int countSchedules() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM schedules", Integer.class);
    }

    @Override
    public int countSchedulesByWriter(String writer, String updatedAt) {
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("""
            SELECT COUNT(*)
            FROM schedules s
            JOIN users u ON s.userId = u.userId
        """);

        if (writer != null || updatedAt != null) {
            query.append(" WHERE");

            boolean addAnd = false;

            if (writer != null) {
                query.append(" u.name = ?");
                params.add(writer);
                addAnd = true;
            }

            if (updatedAt != null) {
                if (addAnd) query.append(" AND");
                query.append(" DATE(s.updatedAt) = ?");
                params.add(updatedAt);
            }
        }

        return jdbcTemplate.queryForObject(
                query.toString(),
                Integer.class,
                params.toArray()
        );
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        String query = getScheduleWithUserQuery() + " WHERE s.scheduleId = ?";
        List<ScheduleResponseDto> result = jdbcTemplate.query(
                query,
                scheduleRowMapper(),
                scheduleId
        );
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Schedule findScheduleByIdWithPassword(Long scheduleId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM schedules WHERE scheduleId = ?",
                scheduleWithPasswordRowMapper(),
                scheduleId
        );
    }

    @Override
    public Long findUserIdByScheduleId(Long scheduleId) {
        String query = "SELECT userId FROM schedules WHERE scheduleId = ?";
        return jdbcTemplate.queryForObject(query, Long.class, scheduleId);
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

        if (schedule.getUserId() != null) {
            if (!firstField) query.append(",");
            query.append(" userId = ?");
            params.add(schedule.getUserId());
        }

        query.append(" WHERE scheduleId = ?");
        params.add(scheduleId);

        if (params.size() == 1) { // scheduleId만 있는 경우 (업데이트할 내용 없음)
            throw new IllegalArgumentException("업데이트할 필드가 없습니다.");
        }

        return jdbcTemplate.update(query.toString(), params.toArray());
    }

    @Override
    public int deleteSchedule(Long scheduleId) {
        return jdbcTemplate.update("DELETE FROM schedules WHERE scheduleId = ?", scheduleId);
    }

    private String getScheduleWithUserQuery() {
        return """
        SELECT s.scheduleId, s.task, u.name AS writer, s.createdAt, s.updatedAt
        FROM schedules s
        JOIN users u ON s.userId = u.userId
    """;
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

    private RowMapper<Schedule> scheduleWithPasswordRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("scheduleId"),
                rs.getString("task"),
                rs.getLong("userId"),
                rs.getString("password"),
                rs.getObject("createdAt", LocalDateTime.class),
                rs.getObject("updatedAt", LocalDateTime.class)
        );
    }
}
