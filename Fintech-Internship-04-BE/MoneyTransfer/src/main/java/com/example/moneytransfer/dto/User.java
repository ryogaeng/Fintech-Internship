package com.example.moneytransfer.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int user_code;

    private String Id;

    private String password;


    private String phone_num;

    private String user_name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean is_buttonOn;


}
