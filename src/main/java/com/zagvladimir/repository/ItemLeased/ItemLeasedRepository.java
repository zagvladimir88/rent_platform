package com.zagvladimir.repository.ItemLeased;

import com.zagvladimir.domain.ItemLeased;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class ItemLeasedRepository implements ItemLeasedRepositoryInterface{

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final ItemLeasedRowMapper itemLeasedRowMapper;

    @Override
    public ItemLeased findById(Long id) {
        return jdbcTemplate.queryForObject("select * from items_leased where id = " + id, itemLeasedRowMapper);
    }

    @Override
    public Optional<ItemLeased> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<ItemLeased> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<ItemLeased> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from items_leased limit " + limit + " offset " + offset, itemLeasedRowMapper);
    }

    @Override
    public ItemLeased create(ItemLeased object) {
        final String insertQuery =
                "insert into items_leased (item_id, renter_id, time_from, time_to, price_per_hour, discount, fee,price_total,rentier_grade_description,renter_grade_description, creation_date,modification_date,status) " +
                        " values (:item_id, :renter_id, :time_from, :time_to, :price_per_hour, :discount, :fee, :price_total, :rentier_grade_description, :renter_grade_description, :creation_date,:modification_date,:status);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", object.getId());
        mapSqlParameterSource.addValue("item_id", object.getItem_id());
        mapSqlParameterSource.addValue("renter_id", object.getRenter_id());
        mapSqlParameterSource.addValue("time_from", object.getTime_from());
        mapSqlParameterSource.addValue("time_to", object.getTime_to());
        mapSqlParameterSource.addValue("price_per_hour", object.getPrice_per_hour());
        mapSqlParameterSource.addValue("discount", object.getDiscount());
        mapSqlParameterSource.addValue("fee", object.getFee());
        mapSqlParameterSource.addValue("price_total", object.getPrice_total());
        mapSqlParameterSource.addValue("rentier_grade_description", object.getRentier_grade_description());
        mapSqlParameterSource.addValue("renter_grade_description", object.getRenter_grade_description());
        mapSqlParameterSource.addValue("creation_date", object.getCreation_date());
        mapSqlParameterSource.addValue("modification_date", object.getModification_date());
        mapSqlParameterSource.addValue("status", String.valueOf(object.getStatus()));

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        Long lastInsertId = namedParameterJdbcTemplate.query("SELECT currval('item_leased_id_seq') as last_id",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getLong("last_id");
                });

        return findById(lastInsertId);
    }

    @Override
    public ItemLeased update(ItemLeased object) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("delete from items_leased where id = " + id);
        return id;
    }
}
