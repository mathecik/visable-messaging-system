package com.lilacode.dao;

import com.lilacode.JdbcConnection;
import com.lilacode.entities.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER =
            Logger.getLogger(MessageDaoImpl.class.getName());
    private final Optional<Connection> connection;

    public UserDaoImpl() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Integer> create(User user) {
        String warning = "The user to be added should not be null";
        User nonNullUser = Objects.requireNonNull(user, warning);

        String sql = "INSERT INTO public.\"LILA_USER\"(\"NICK_NAME\") VALUES (?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         conn.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullUser.getNickname());

                int numberOfInsertedRows = statement.executeUpdate();

                // Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                LOGGER.log(
                        Level.INFO,
                        "{0} created successfully? {1}",
                        new Object[]{nonNullUser,
                                (numberOfInsertedRows > 0)});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public Optional<User> get(int id) {
        return connection.flatMap(conn -> {
            Optional<User> user = Optional.empty();
            String sql = "SELECT * FROM public.\"LILA_USER\" WHERE \"ID\" = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String nickname = resultSet.getString("NICK_NAME");
                    user = Optional.of(
                            new User(id, nickname));

                    LOGGER.log(Level.INFO, "Found {0} in database", user.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return user;
        });
    }

    @Override
    public Collection<User> getAll() {
        Collection<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"LILA_USER\"";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String nickname = resultSet.getString("NICK_NAME");

                    User user = new User(id, nickname);
                    users.add(user);

                    LOGGER.log(Level.INFO, "Found {0} in database", user);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return users;
    }

    @Override
    public void update(User user) {
        String warning = "The user to be updated should not be null";
        User nonNullUser = Objects.requireNonNull(user, warning);
        String sql = "UPDATE public.\"LILA_USER\" "
                + "SET "
                + "\"NICK_NAME\" = ? "
                + "WHERE "
                + "\"ID\" = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, nonNullUser.getNickname());
                statement.setInt(2, nonNullUser.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the user updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(User user) {
        String warning = "The user to be deleted should not be null";
        User nonNullUser = Objects.requireNonNull(user, warning);
        String sql = "DELETE FROM public.\"LILA_USER\" WHERE \"ID\" = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullUser.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the user deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
