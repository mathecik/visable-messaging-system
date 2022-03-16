package com.lilacode.dao;

import com.lilacode.JdbcConnection;
import com.lilacode.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class MessageDaoImpl implements MessageDao {
    private static final Logger LOGGER =
            Logger.getLogger(MessageDaoImpl.class.getName());
    private final Optional<Connection> connection;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public MessageDaoImpl() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Integer> create(Message message) {
        String warning = "The message to be added should not be null";
        Message nonNullMessage = Objects.requireNonNull(message, warning);
        String sql = "INSERT INTO "
                + "public.\"MESSAGE\"(\"FROM\", \"TO\", \"CONTENT\") "
                + "VALUES(?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         conn.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, nonNullMessage.getFrom());
                statement.setInt(2, nonNullMessage.getTo());
                statement.setString(3, nonNullMessage.getContent());

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
                        new Object[]{nonNullMessage,
                                (numberOfInsertedRows > 0)});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public Optional<Message> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Message> message = Optional.empty();
            String sql = "SELECT * FROM public.\"MESSAGE\" WHERE \"ID\" = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    Integer fromId = resultSet.getInt("FROM");
                    Integer toId = resultSet.getInt("TO");
                    String content = resultSet.getString("CONTENT");
                    message = Optional.of(
                            new Message(id, content, fromId, toId));
                    LOGGER.log(Level.INFO, "Found {0} in database", message.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return message;
        });
    }

    @Override
    public Collection<Message> getAll() {
        Collection<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM public.\"MESSAGE\"";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int fromId = resultSet.getInt("FROM");
                    int toId = resultSet.getInt("TO");
                    String content = resultSet.getString("CONTENT");

                    Message message = new Message(id, content, fromId, toId);
                    messages.add(message);

                    LOGGER.log(Level.INFO, "Found {0} in database", message);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return messages;
    }

    @Override
    public void update(Message message) {
        String warning = "The message to be updated should not be null";
        Message nonNullMessage = Objects.requireNonNull(message, warning);
        String sql = "UPDATE public.\"MESSAGE\" "
                + "SET "
                + "\"FROM\" = ?, "
                + "\"TO\" = ?, "
                + "\"CONTENT\" = ? "
                + "WHERE "
                + "\"ID\" = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullMessage.getFrom());
                statement.setInt(2, nonNullMessage.getTo());
                statement.setString(3, nonNullMessage.getContent());
                statement.setInt(4, nonNullMessage.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the message updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Message message) {
        String warning = "The message to be deleted should not be null";
        Message nonNullMessage = Objects.requireNonNull(message, warning);
        String sql = "DELETE FROM public.\"MESSAGE\" WHERE \"ID\" = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullMessage.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the message deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public List<Message> findByFromId(Integer from) {
        List<Message> messagesFromUser = new ArrayList<>();
        try{
            Statement statement = connection.get().createStatement();
            String query = "SELECT * FROM public.\"MESSAGE\" WHERE \"FROM\" = " + from + ";";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int messageId = resultSet.getInt("ID");
                Integer to = resultSet.getInt("TO");
                String content = resultSet.getString("CONTENT");
                messagesFromUser.add(new Message(messageId, content, from, to));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return messagesFromUser;
    }

    public List<Message> findByToId(Integer to) {
        List<Message> messagesToUser = new ArrayList<>();
        try{
            Statement statement = connection.get().createStatement();
            String query = "SELECT * FROM public.\"MESSAGE\" WHERE \"TO\" = " + to + ";";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int messageId = resultSet.getInt("ID");
                Integer from = resultSet.getInt("FROM");
                String content = resultSet.getString("CONTENT");
                messagesToUser.add(new Message(messageId, content, from, to));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return messagesToUser;
    }
}
