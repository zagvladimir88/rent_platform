package com.zagvladimir.repository.user;
import com.zagvladimir.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;


import static com.zagvladimir.repository.user.UserTableColumns.*;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(ID));
        user.setUsername(rs.getString(NAME));
        user.setUser_password(rs.getString(PASSWORD));
        user.setLocation_id(rs.getInt(LOCATION_ID));
        user.setLocation_details(rs.getString(LOCATION_DETAILS));
        user.setPhone_number(rs.getString(PHONE));
        user.setMobile_number(rs.getString(MOBILE));
        user.setEmail(rs.getString(EMAIL));
        user.setRegistration_date(rs.getTimestamp(REGISTER_DATE));
        user.setCreation_date(rs.getTimestamp(CREATION_DATE));
        user.setModification_date(rs.getTimestamp(MODIFICATION_DATE));
        return user;
    }
}
