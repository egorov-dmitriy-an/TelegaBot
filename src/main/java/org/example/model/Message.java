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

   
}
