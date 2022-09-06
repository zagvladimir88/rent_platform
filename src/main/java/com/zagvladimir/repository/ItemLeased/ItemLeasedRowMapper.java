package com.zagvladimir.repository.ItemLeased;


import com.zagvladimir.domain.Item;
import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.zagvladimir.repository.ItemLeased.ItemLeasedTableColumns.*;


@Component
public class ItemLeasedRowMapper implements RowMapper<ItemLeased> {
    @Override
    public ItemLeased mapRow(ResultSet rs, int i) throws SQLException {
        ItemLeased itemLeased = new ItemLeased();
        itemLeased.setId(rs.getInt(ID));
        itemLeased.setItem_id(rs.getInt(ITEM_ID));
        itemLeased.setRenter_id(rs.getInt(RENTER_ID));
        itemLeased.setTime_from(rs.getTimestamp(TIME_FROM));
        itemLeased.setTime_to(rs.getTimestamp(TIME_TO));
        itemLeased.setPrice_per_hour(rs.getDouble(PRICE_PER_HOUR));
        itemLeased.setDiscount(rs.getDouble(DISCOUNT));
        itemLeased.setFee(rs.getDouble(FEE));
        itemLeased.setPrice_total(rs.getDouble(PRICE_TOTAL));
        itemLeased.setRentier_grade_description(rs.getString(RENTIER_GRADE_DESCRIPTION));
        itemLeased.setRenter_grade_description(rs.getString(RENTER_GRADE_DESCRIPTION));
        itemLeased.setCreation_date(rs.getTimestamp(CREATION_DATE));
        itemLeased.setModification_date(rs.getTimestamp(MODIFICATION_DATE));

        return itemLeased;
    }
}
