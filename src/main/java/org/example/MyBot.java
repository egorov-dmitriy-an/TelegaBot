package org.example;

import org.example.dao.jdbc.MessageJDBCDaoImpl;
import org.example.model.Message;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Instant;
import java.util.List;


public class MyBot extends TelegramLongPollingBot {

    private final String BOT_TOKEN = "8564599615:AAFzAviN_EIFsSsMwUHIPhF4uzKx_NguoSY";
    private final String BOT_USERNAME = "@telegaBelomorskBot";
    Message message;
    MessageJDBCDaoImpl messageJDBCDao = JdbcRunner.messageJDBCDao;
    ;

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
        message = createMessage(update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (message.getMessage().equals("/start")) {
                sendMessage(update, "Привет! Я простой Java-Telegram-бот.");
            } else if (message.getMessage().equals("/help")) {
                sendMessage(update, "Доступные команды:" +
                        "\n/start — приветствие" +
                        "\n/exit — выход" +
                        "\n/show — посмотреть записи" +
                        "\n/help — помощь");
            } else if (message.getMessage().equals("/exit")) {
                sendMessage(update, "Вы закрыли бота");
                saveMessage(update);
                System.exit(1);
            } else if (message.getMessage().equals("/show")) {
                sendMessage(update, "Вы решили посмотреть записи");
                List<Message> lm = messageJDBCDao.findAll();
                for (Message m : lm) {
                    sendMessage(update, (m.getLastNameAuthor() + " " + m.getFirstNameAuthor() + " - " + m.getMessage() + " " + m.getInstant().toString()));
                }
            } else {
                sendMessage(update, "Вы написали: " + message.getMessage());
            }
            saveMessage(update);
        }
    }

    void sendMessage(Update update, String text) {
        SendMessage msg = new SendMessage(update.getMessage().getChatId().toString(), text);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void saveMessage(Update update) {
        Message mess = createMessage(update);
        System.out.println(mess);
        messageJDBCDao.save(mess);
    }


    private Message createMessage(Update update) {
        return new Message(1L,
                update.getMessage().getChatId(),
                update.getMessage().getFrom().getLastName(),
                update.getMessage().getFrom().getFirstName(),
                update.getMessage().getText(),
                Instant.ofEpochSecond(update.getMessage().getDate()));
    }
}