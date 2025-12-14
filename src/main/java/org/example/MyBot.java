package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    private final String BOT_TOKEN = "8564599615:AAFzAviN_EIFsSsMwUHIPhF4uzKx_NguoSY";
    private final String BOT_USERNAME = "@telegaBelomorskBot";

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (text.equals("/start")) {
                sendMessage(chatId, "Привет! Я простой Java-Telegram-бот.");
            } else if (text.equals("/help")) {
                sendMessage(chatId, "Доступные команды:\n/start — приветствие\n/help — помощь");
            } else {
                sendMessage(chatId, "Вы написали: " + text);
            }
            saveMessage(chatId, text);
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage msg = new SendMessage(chatId.toString(), text);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void saveMessage(Long chatId, String text) {
        System.out.println(chatId + " --- " + text);
        Database.saveValue(chatId, text);
        System.out.println("Сохранено в базу");
    }
}