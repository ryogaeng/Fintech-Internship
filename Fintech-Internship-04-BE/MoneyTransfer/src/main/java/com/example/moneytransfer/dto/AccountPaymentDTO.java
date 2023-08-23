package com.example.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountPaymentDTO {


    private Integer bank_tran_id;

    private Integer balance_amt;

    private Integer tran_amt;

    private boolean is_taken;

    private String payment_dest;

    private Integer to_account_code;

    private Integer user_code;

    private Integer group_code;

    private String account_num;

    private Integer account_code;

    private Integer to_user_code;

}
