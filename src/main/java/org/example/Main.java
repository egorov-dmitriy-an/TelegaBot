package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new MyBot());
        System.out.println("Bot started!");
//
//        if (text.equals("/start")) {
//            sendMessage(chatId, "Привет! Я простой Java-Telegram-бот.");
//        } else if (text.equals("/help")) {
//            sendMessage(chatId, "Доступные команды:\n/start — приветствие\n/help — помощь");
//        } else {
//            sendMessage(chatId, "Вы написали: " + text);
//        }
    }

//    private void sendMessage(Long chatId, String text) {
//        SendMessage msg = new SendMessage(chatId.toString(), text);
//        try {
//            execute(msg);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

}
