package com.example.repository;

import com.example.dto.user.UserRequestDto;
import com.example.dto.user.UserResponseDto;
import com.example.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserResponseDto saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("userId");

        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", user.getUserId());
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        parameters.put("createdAt", now);
        parameters.put("updatedAt", now);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new UserResponseDto(key.longValue(), user.getName(), user.getEmail(), now, now);
    }

    @Override
    public UserResponseDto findUserByEmail(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE email = ?",
                userRowMapper(),
                email
        );
    }

    @Override
    public UserResponseDto findUserByNameAndPassword(String name, String password) {
        return null;
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        return List.of();
    }

    @Override
    public UserResponseDto findUserByNameAndPassword(User user) {
        return null;
    }

    @Override
    public UserResponseDto findUserById(Long userId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE userId = ?",
                userRowMapper(),
                userId
        );
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRequestDto dto) {
        return null;
    }

    @Override
    public void deleteUser(Long userId, String password) {

    }

    private RowMapper<UserResponseDto> userRowMapper() {
        return (rs, rowNum) -> new UserResponseDto(
                rs.getLong("userId"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getObject("createdAt", LocalDateTime.class),
                rs.getObject("updatedAt", LocalDateTime.class)
        );
    }

}
