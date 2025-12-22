package org.example.model;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Long chatId;
    private String message;
    private String firstNameAuthor;
    private String lastNameAuthor;
    private Instant instant;

    private String sql = "CREATE TABLE messages (\n" +
            "    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,\n" +
            "    chat_id BIGINT NOT NULL,\n" +
            "    text TEXT NOT NULL,\n" +
            "    created_at TIMESTAMPTZ NOT NULL DEFAULT now()\n" +
            ");";

}
