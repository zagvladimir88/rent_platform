package com.zagvladimir.repository.jdbctemplate;


import com.zagvladimir.domain.User;
import com.zagvladimir.repository.user.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Primary
public class JdbcTemplateUserRepository implements UserRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final UserRowMapper userRowMapper;

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject("select * from users where id = " + id, userRowMapper);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<User> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from users limit " + limit + " offset " + offset, userRowMapper);
    }

    @Override
    public User create(User object) {
        final String insertQuery =
                "insert into users (username, user_password, location_id, location_details, phone_number, mobile_number, email,registration_date,creation_date,modification_date) " +
                        " values (:username, :user_password, :location_id, :location_details, :phone_number, :mobile_number, :email,:registration_date ,:creation_date ,:modification_date);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", object.getId());
        mapSqlParameterSource.addValue("username", object.getUsername());
        mapSqlParameterSource.addValue("user_password", object.getUser_password());
        mapSqlParameterSource.addValue("location_id", object.getLocation_id());
        mapSqlParameterSource.addValue("location_details", object.getLocation_details());
        mapSqlParameterSource.addValue("phone_number", object.getPhone_number());
        mapSqlParameterSource.addValue("mobile_number", object.getMobile_number());
        mapSqlParameterSource.addValue("email", object.getEmail());
        mapSqlParameterSource.addValue("registration_date", object.getRegistration_date());
        mapSqlParameterSource.addValue("creation_date", object.getCreation_date());
        mapSqlParameterSource.addValue("modification_date", object.getModification_date());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        Long lastInsertId = namedParameterJdbcTemplate.query("SELECT currval('users_id_seq') as last_id",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getLong("last_id");
                });

        return findById(lastInsertId);
    }

    @Override
    public User update(User object) {
        final String updateQuery =
                "UPDATE users set username = :username, user_password = :user_password, location_id = :location_id, location_details = :location_details" +
                        ", phone_number = :phone_number, mobile_number = :mobile_number, email = :email, registration_date = :registration_date,creation_date = :creation_date,modification_date = :modification_date where id = :id";


        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", object.getId());
        mapSqlParameterSource.addValue("username", object.getUsername());
        mapSqlParameterSource.addValue("user_password", object.getUser_password());
        mapSqlParameterSource.addValue("location_id", object.getLocation_id());
        mapSqlParameterSource.addValue("location_details", object.getLocation_details());
        mapSqlParameterSource.addValue("phone_number", object.getPhone_number());
        mapSqlParameterSource.addValue("mobile_number", object.getMobile_number());
        mapSqlParameterSource.addValue("email", object.getEmail());
        mapSqlParameterSource.addValue("registration_date", object.getRegistration_date());
        mapSqlParameterSource.addValue("creation_date", object.getCreation_date());
        mapSqlParameterSource.addValue("modification_date", new Timestamp(new Date().getTime()));

        namedParameterJdbcTemplate.update(updateQuery, mapSqlParameterSource);

        return findById(object.getId());
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("delete from users where id = " + id);
        return id;
    }

    @Override
    public Map<String, Object> getUserStats() {
        return jdbcTemplate.query("select get_users_stats_average_weight(true)", resultSet -> {

            resultSet.next();
            return Collections.singletonMap("avg", resultSet.getDouble(1));
        });
    }
}
