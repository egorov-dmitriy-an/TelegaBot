package org.example.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Long chatId;
    private String message;
    private String firstNameAuthor;
    private String lastNameAuthor;

}
