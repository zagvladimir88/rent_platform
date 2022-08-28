package com.zagvladimir.repository.item;

import com.zagvladimir.domain.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class ItemRepository implements ItemRepositoryInterface{

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final ItemRowMapper itemRowMapper;

    @Override
    public Item findById(Long id) {
        return jdbcTemplate.queryForObject("select * from items where id = " + id, itemRowMapper);
    }

    @Override
    public Optional<Item> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<Item> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<Item> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from items limit " + limit + " offset " + offset, itemRowMapper);

    }

    @Override
    public Item create(Item object) {
        final String insertQuery =
                "insert into items (item_name, item_type_id, location_id, item_location, description, owner_id, price_per_hour,available,creation_date,modification_date) " +
                        " values (:item_name, :item_type_id, :location_id, :item_location, :description, :owner_id, :price_per_hour, :available, :creation_date, :modification_date);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", object.getId());
        mapSqlParameterSource.addValue("item_name", object.getItem_name());
        mapSqlParameterSource.addValue("item_type_id", object.getItem_type_id());
        mapSqlParameterSource.addValue("location_id", object.getLocation_id());
        mapSqlParameterSource.addValue("item_location", object.getItem_location());
        mapSqlParameterSource.addValue("description", object.getDescription());
        mapSqlParameterSource.addValue("owner_id", object.getOwner_id());
        mapSqlParameterSource.addValue("price_per_hour", object.getPrice_per_hour());
        mapSqlParameterSource.addValue("available", object.getAvailable());
        mapSqlParameterSource.addValue("creation_date", object.getCreation_date());
        mapSqlParameterSource.addValue("modification_date", object.getModification_date());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        Long lastInsertId = namedParameterJdbcTemplate.query("SELECT currval('rental_items_id_seq') as last_id",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getLong("last_id");
                });

        return findById(lastInsertId);
    }

    @Override
    public Item update(Item object) {
        final String updateQuery =
                "UPDATE users set item_name = :item_name, item_type_id = :item_type_id, location_id = :location_id, item_location = :item_location" +
                        ", description = :description, owner_id = :owner_id, price_per_hour = :price_per_hour, available = :available, modification_date = :modification_date where id = :id";


        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", object.getId());
        mapSqlParameterSource.addValue("item_name", object.getItem_name());
        mapSqlParameterSource.addValue("item_type_id", object.getItem_type_id());
        mapSqlParameterSource.addValue("location_id", object.getLocation_id());
        mapSqlParameterSource.addValue("item_location", object.getItem_location());
        mapSqlParameterSource.addValue("description", object.getDescription());
        mapSqlParameterSource.addValue("owner_id", object.getOwner_id());
        mapSqlParameterSource.addValue("price_per_hour", object.getPrice_per_hour());
        mapSqlParameterSource.addValue("available", object.getCreation_date());
        mapSqlParameterSource.addValue("modification_date", new Timestamp(new Date().getTime()));

        namedParameterJdbcTemplate.update(updateQuery, mapSqlParameterSource);

        return findById(object.getId());
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("delete from items where id = " + id);
        return id;
    }

    @Override
    public List<Item> getItemsByCategory(int itemTypeId) {
        return jdbcTemplate.query("select * from items where item_type_id = " + itemTypeId, itemRowMapper);
    }

    @Override
    public List<Item> searchItemsByName(String name) {
        return jdbcTemplate.query("select * from items where item_name ilike '%" + name +"%'", itemRowMapper);
    }
}
