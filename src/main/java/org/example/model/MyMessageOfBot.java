package org.example.model;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyMessageOfBot {
    private Long id;
    private Long chatId;
    private String lastNameAuthor;
    private String firstNameAuthor;
    private String message;
    private Instant instant;
}
