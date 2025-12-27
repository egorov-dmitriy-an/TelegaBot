package org.example;

import org.example.dao.jdbc.MessageJDBCDaoImpl;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class JdbcRunner {

    static MessageJDBCDaoImpl messageJDBCDao = MessageJDBCDaoImpl.getInstance();

    public static void main(String[] args) throws Exception {
        messageJDBCDao.deleteTable();
        messageJDBCDao.createTable();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new MyBot());
        System.out.println("Bot started!");
    }
}