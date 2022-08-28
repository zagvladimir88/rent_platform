package com.zagvladimir.repository.item;

import com.zagvladimir.domain.Item;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.zagvladimir.repository.item.ItemTableColumns.*;

@Component
public class ItemRowMapper implements RowMapper<Item> {

    private final Logger log = Logger.getLogger(ItemRowMapper.class);

    @Override
    public Item mapRow(ResultSet rs, int i) throws SQLException {
        log.info("Item row mapping start");

        Item item = new Item();

        item.setId(rs.getLong(ID));
        item.setItem_name(rs.getString(ITEM_NAME));
        item.setItem_type_id(rs.getInt(ITEM_TYPE_ID));
        item.setLocation_id(rs.getInt(LOCATION_ID));
        item.setItem_location(rs.getString(ITEM_LOCATION_DETAILS));
        item.setDescription(rs.getString(DESCRIPTION));
        item.setOwner_id(rs.getLong(OWNER_ID));
        item.setPrice_per_hour(rs.getDouble(PRICE_PER_HOUR));
        item.setAvailable(rs.getBoolean(AVAILABLE));
        item.setCreation_date(rs.getTimestamp(CREATION_DATE));
        item.setModification_date(rs.getTimestamp(MODIFICATION_DATE));

        log.info("Item row mapping end");
        return item;
    }
}
