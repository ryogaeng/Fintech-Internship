package com.example.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private int account_code;
    private int balance_amt;
    private int user_code;
    private String account_num;
    private String bank_name;
}
