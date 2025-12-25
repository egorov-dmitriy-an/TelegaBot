package org.example;

import org.example.dao.jdbc.MessageJDBCDaoImpl;
import org.example.model.Test;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.Instant;

public class JdbcRunner {

    static MessageJDBCDaoImpl messageJDBCDao = MessageJDBCDaoImpl.getInstance();

    public static void main(String[] args) throws Exception {
//        messageJDBCDao.deleteTable();
//        messageJDBCDao.createTable();
//        messageJDBCDao.createTable2();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new MyBot());
        System.out.println("Bot started!");
//        Test t = new Test(1L, Instant.now());
//        System.out.println(t);
//        messageJDBCDao.save2(t);
    }
}