package org.example;

import java.sql.*;

public class Database {

    private static final String URL = "jdbc:postgresql://localhost:5432/Chat";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveValue(long chatId, String value) {
        String sql = """
                INSERT INTO users (chatid, message)
                VALUES (?, ?)
                """;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, chatId);
            ps.setString(2, value);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(long chatId) {
        String sql = "SELECT message FROM users WHERE chatid = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, chatId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("value");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Нет данных";
    }

    public static void init() {
        String sql = "CREATE TABLE users (\n" +
                "    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,\n" +
                "    chatid BIGINT NOT NULL,\n" +
                "    message TEXT NOT NULL\n" +
                ");";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("create users");

    }

    public static void drop() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("drop users");
    }
}
