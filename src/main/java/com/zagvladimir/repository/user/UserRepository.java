package com.zagvladimir.repository.user;
import com.zagvladimir.configuration.DatabaseProperties;
import com.zagvladimir.domain.User;
import com.zagvladimir.exception.NoSuchEntityException;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static com.zagvladimir.repository.user.UserTableColumns.ID;
import static com.zagvladimir.repository.user.UserTableColumns.NAME;
import static com.zagvladimir.repository.user.UserTableColumns.PASSWORD;
import static com.zagvladimir.repository.user.UserTableColumns.LOCATION_ID;
import static com.zagvladimir.repository.user.UserTableColumns.LOCATION_DETAILS;
import static com.zagvladimir.repository.user.UserTableColumns.PHONE;
import static com.zagvladimir.repository.user.UserTableColumns.MOBILE;
import static com.zagvladimir.repository.user.UserTableColumns.EMAIL;
import static com.zagvladimir.repository.user.UserTableColumns.REGISTER_DATE;
import static com.zagvladimir.repository.user.UserTableColumns.CREATION_DATE;
import static com.zagvladimir.repository.user.UserTableColumns.MODIFICATION_DATE;
import static com.zagvladimir.util.UUIDGenerator.generatedUI;
@Repository
//@Primary
@RequiredArgsConstructor
public class UserRepository implements UserRepositoryInterface {
    private static final Logger log = Logger.getLogger(UserRepository.class);
    private final DatabaseProperties databaseProperties;

    @Override
    public User findById(Long id) {
        final String findByIdQuery = "select * from users where id = " + id;

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findByIdQuery);
            boolean hasRow = rs.next();
            if (hasRow) {
                return userRowMapping(rs);
            } else {
                throw new NoSuchEntityException("Entity User with id " + id + " does not exist", 404, generatedUI());
            }
        } catch (SQLException e) {
            log.error("DB connection process issues", e);
            throw new RuntimeException("DB connection process issues");
        }
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findById(id));
    }

    public List<User> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    private Connection getConnection() throws SQLException {
        try {
            String driver = databaseProperties.getDriverName();

            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error("JDBC Driver Cannot be loaded!", e);
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        String url = databaseProperties.getUrl();
        String login = databaseProperties.getLogin();
        String password = databaseProperties.getPassword();

        return DriverManager.getConnection(url, login, password);
    }

    private User userRowMapping(ResultSet rs) throws SQLException {
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

/*        return User.builder()
                .id(rs.getLong(ID))
                .userName(rs.getString(NAME))
                .surname(rs.getString(SURNAME))
                .birth(rs.getTimestamp(BIRTH_DATE))
                .creationDate(rs.getTimestamp(CREATED))
                .modificationDate(rs.getTimestamp(CHANGED))
                .weight(rs.getDouble(WEIGHT))
                .build();*/
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        final String findAllQuery = "select * from users order by id limit " + limit + " offset " + offset;

        List<User> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findAllQuery);

            while (rs.next()) {
                result.add(userRowMapping(rs));
            }

            return result;
        } catch (SQLException e) {
            log.error("DB connection process issues", e);
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User create(User object) {
        final String insertQuery =
                "insert into users (username, user_password, location_id, location_details, phone_number, mobile_number, email,registration_date,creation_date,modification_date) " +
                        " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(insertQuery);

            statement.setString(1, object.getUsername());
            statement.setString(2, object.getUser_password());
            statement.setInt(3, object.getLocation_id());
            statement.setString(4, object.getLocation_details());
            statement.setString(5, object.getPhone_number());
            statement.setString(6, object.getMobile_number());
            statement.setString(7, object.getEmail());
            statement.setTimestamp(8, object.getRegistration_date());
            statement.setTimestamp(9, object.getCreation_date());
            statement.setTimestamp(10, object.getModification_date());

            //executeUpdate - for INSERT, UPDATE, DELETE
            statement.executeUpdate();
            //For select
            //statement.executeQuery();

            /*Get users last insert id for finding new object in DB*/
            ResultSet resultSet = connection.prepareStatement("SELECT currval('users_id_seq') as last_id").executeQuery();
            resultSet.next();
            long userLastInsertId = resultSet.getLong("last_id");

            return findById(userLastInsertId);
        } catch (SQLException e) {
            log.error("DB connection process issues", e);
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User update(User object) {
//        final String updateQuery =
//                "update users " +
//                        "set " +
//                        "username = ?, user_password = ?, location_id = ?, location_details = ?, phone_number = ?, mobile_number = ?, email = ?, registration_date = ?, creation_date = ?, modification_date = ? " +
//                        " where id = ?";
//
//        Connection connection;
//        PreparedStatement statement;
//
//        try {
//            connection = getConnection();
//            statement = connection.prepareStatement(updateQuery);
//
//            statement.setString(1, object.getUsername());
//            statement.setString(2, object.getUser_password());
//            statement.setInt(3, object.getLocation_id());
//            statement.setString(4, object.getLocation_details());
//            statement.setString(5, object.getPhone_number());
//            statement.setString(6, object.getMobile_number());
//            statement.setString(7, object.getEmail());
//            statement.setTimestamp(8, object.getRegistration_date());
//            statement.setTimestamp(9, object.getCreation_date());
//            statement.setTimestamp(10, object.getModification_date());
//
//            statement.executeUpdate();
//
//            return findById(object.getId());
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//            throw new RuntimeException("SQL Issues!");
//        }
        return null;
    }

    @Override
    public Long delete(Long id) {
        final String deleteQuery =
                "delete from users where id = ?";

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(deleteQuery);
            statement.setLong(1, id);
            statement.executeUpdate();

            return id;
        } catch (SQLException e) {
            log.error("DB connection process issues", e);
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Map<String, Object> getUserStats() {
        final String callFunction =
                "select get_users_stats_average_weight(?)";

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(callFunction);
            statement.setBoolean(1, true);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            double functiionCall = resultSet.getDouble(1);

            return Collections.singletonMap("avg", functiionCall);
        } catch (SQLException e) {
            log.error("DB connection process issues", e);
            throw new RuntimeException("SQL Issues!");
        }
    }
}
