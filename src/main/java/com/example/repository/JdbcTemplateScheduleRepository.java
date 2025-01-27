package com.example.repository;

import com.example.dto.schedule.ScheduleResponseDto;
import com.example.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("schedule_id");

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
        return List.of();
    }

    @Override
    public Schedule findScheduleById(Long id) {
        return null;
    }

    @Override
    public void deleteSchedule(Long id) {

    }
}
