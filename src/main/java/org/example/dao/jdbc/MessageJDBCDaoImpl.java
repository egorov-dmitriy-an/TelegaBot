package org.example.dao.jdbc;

import org.example.configuration.ConnectionManager;
import org.example.dao.Dao;
import org.example.dao.exception.DaoException;
import org.example.model.Message;
import org.example.model.Test;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MessageJDBCDaoImpl implements Dao<Long, Message> {
    private static final MessageJDBCDaoImpl INSTANCE = new MessageJDBCDaoImpl();

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE messages (
                id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                chat_id BIGINT NOT NULL,
                last_name TEXT NOT NULL,
                first_name  TEXT NOT NULL,
                message TEXT NOT NULL,
                created_at TIMESTAMP WITH TIME ZONE NOT NULL
            );""";

    private static final String CREATE_TABLE_SQL_2 = """
            CREATE TABLE IF NOT EXISTS test (
                id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                created_at TIMESTAMP WITH TIME ZONE NOT NULL
            );""";

    private static final String DELETE_TABLE_SQL = """
            Drop TABLE IF EXISTS messages;
            """;
    private static final String DELETE_ALL_SQL = """
            DELETE FROM messages;
            """;

    private static final String UPDATE_SQL = """
            UPDATE messages
            SET chat_id  = ?,
                last_name =?,
                first_name = ?,
                message = ?,
                created_at = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ALL_SQL = """
            SELECT id,
                chat_id,
                last_name,
                first_name,
                message,
                created_at
            FROM messages
            """;

    private static final String FIND_BY_ID_SQL = FIND_BY_ALL_SQL + """
             WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM messages
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO messages (
                chat_id,
                last_name,
                first_name,
                message,
                created_at)
            VALUES (?, ?, ?, ?, ?)
            """;

    private MessageJDBCDaoImpl() {
    }

    public static MessageJDBCDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_SQL);
            System.out.println("Создана таблица сообщений");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_TABLE_SQL);
            System.out.println("Удалена таблица сообщений");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteAllFromTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement();) {
            statement.execute(DELETE_ALL_SQL);
            System.out.println("Удалены все сообщения");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Message message) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, message.getChatId());
            preparedStatement.setString(2, message.getLastNameAuthor());
            preparedStatement.setString(3, message.getFirstNameAuthor());
            preparedStatement.setString(4, message.getMessage());
            preparedStatement.setTimestamp(5, Timestamp.from(message.getInstant()));
            preparedStatement.executeUpdate();
            System.out.println("Обновлено сообщение: " + message.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Message findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Message message = null;
            if (resultSet.next()) {
                message = buildMessage(resultSet);
            }
            return message;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static Message buildMessage(ResultSet resultSet) throws SQLException {
        return new Message(
                resultSet.getLong("id"),
                resultSet.getLong("chat_id"),
                resultSet.getString("last_name"),
                resultSet.getString("first_name"),
                resultSet.getString("message"),
                resultSet.getTimestamp("created_at").toInstant());
    }

    @Override
    public List<Message> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Message> messagesEntitiesList = new ArrayList<>();
            while (resultSet.next()) {
                messagesEntitiesList.add(buildMessage(resultSet));
            }
            return messagesEntitiesList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Удалено сообщение: " + id);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(Message message) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, message.getChatId());
            preparedStatement.setString(2, message.getLastNameAuthor());
            preparedStatement.setString(3, message.getFirstNameAuthor());
            preparedStatement.setString(4, message.getMessage());
            preparedStatement.setTimestamp(5, Timestamp.from(message.getInstant()));
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                message.setId(generatedKeys.getLong("id"));
            }
            System.out.println("Создано сообщение " + message.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


}
