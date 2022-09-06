package com.zagvladimir.repository.ItemLeased;

import com.zagvladimir.domain.ItemLeased;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
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
        return null;
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
