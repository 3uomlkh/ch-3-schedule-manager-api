package com.example.repository;

import com.example.dto.user.UserResponseDto;
import com.example.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public User findUserByIdWithPassword(Long userId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE userId = ?",
                userWithPasswordRowMapper(),
                userId
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
    public int updateUser(Long userId, User user) {
        StringBuilder query = new StringBuilder("UPDATE users SET");
        List<Object> params = new ArrayList<>();
        boolean firstField = true;

        if (user.getName() != null) {
            query.append(" name = ?");
            params.add(user.getName());
            firstField = false;
        }

        if (user.getEmail() != null) {
            if (!firstField) query.append(",");
            query.append(" email = ?");
            params.add(user.getEmail());
        }

        if (user.getPassword() != null) {
            if (!firstField) query.append(",");
            query.append(" password = ?");
            params.add(user.getPassword());
        }

        query.append(" WHERE userId = ?");
        params.add(userId);

        if (params.size() == 1) {
            throw new IllegalArgumentException("업데이트할 필드가 없습니다.");
        }

        return jdbcTemplate.update(query.toString(), params.toArray());
    }

    @Override
    public int deleteUser(Long userId) {
        return jdbcTemplate.update("DELETE FROM users WHERE userId = ?", userId);
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

    private RowMapper<User> userWithPasswordRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getLong("userId"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getObject("createdAt", LocalDateTime.class),
                rs.getObject("updatedAt", LocalDateTime.class)
        );
    }

}
