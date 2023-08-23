package com.example.moneytransfer.service;

import com.example.moneytransfer.dto.*;
import com.example.moneytransfer.mapper.AccountMapper;
import com.example.moneytransfer.mapper.GroupMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;


    @Transactional
    public boolean deposit(AccountPaymentDTO payment_detail)
    {
        Integer user_code = payment_detail.getUser_code();
        Integer to_user_code = payment_detail.getTo_user_code();
        Integer group_code = payment_detail.getGroup_code();
        payment_detail.setAccount_code(accountMapper.getAccountCode(user_code));
        int balance = accountMapper.getBalance(payment_detail.getAccount_code());
        payment_detail.setTran_amt(-payment_detail.getTran_amt());
        System.out.println(payment_detail.getTran_amt());
        System.out.println(balance);

        if (group_code != null) {
            payment_detail.setAccount_code(accountMapper.getAccountCode(user_code));
        } else {
            if (to_user_code != null) {
                payment_detail.setTo_account_code(accountMapper.getAccountCode(to_user_code));
            }
        }

        if(!payment_detail.is_taken())
        {
            accountMapper.createPaymentDetail(payment_detail);
            return false;
        }


        if(to_user_code!=null)
        {
            payment_detail.setTo_account_code(accountMapper.getAccountCode(to_user_code));
        }

        //출금하려는 계좌의 잔액이 출금액보다 더 큰 경우에
        if(balance+payment_detail.getTran_amt()>0){
            //출금
            payment_detail.set_taken(true);
            accountMapper.deposit(payment_detail);
            int from_account_code = payment_detail.getAccount_code();
            Integer to_account_code = payment_detail.getTo_account_code();
            int tran_amt = payment_detail.getTran_amt();

            if(payment_detail.getTo_user_code()==null)
            {
                payment_detail.setTo_user_code(0);
            }
            //출금내역 입력
            accountMapper.createPaymentDetail(payment_detail);

            //입금
            accountMapper.deposit(payment_detail);
            if(payment_detail.getTo_user_code()!=0)
            {
                payment_detail.setTo_user_code(user_code);
                payment_detail.setUser_code(to_user_code);
                payment_detail.setAccount_code(to_account_code);
                payment_detail.setTo_account_code(from_account_code);
                payment_detail.setTran_amt(-payment_detail.getTran_amt());
                payment_detail.setPayment_dest(accountMapper.getUserId(payment_detail.getTo_user_code()));
                //입금내역 입력
                accountMapper.createPaymentDetail(payment_detail);
            }


            return true;

        }
        //출금 실패시
        else{
            //출금을 실패한 게 모임 정산이었을 경우
            if(payment_detail.getGroup_code()!=null)
            {
                //거래내역에 실패한 모임 정산을 기록한다.
                payment_detail.set_taken(false);
                accountMapper.createPaymentDetail(payment_detail);
            }
            return false;
        }


        //출금한 쪽의 결제내역 insert
    }

    public int getBalance(Integer account_code)
    {
        return accountMapper.getBalance(account_code);
    }
    public List<AccountDTO> getAccountList(Integer user_code)
    {
        return accountMapper.getAccountList(user_code);
    }

    public List<AccountDTO> getTransferHistory(int account_code)
    {
        return accountMapper.getTransferHistory(account_code);
    }

    public List<Map<String,Object>> getNonCalculateMemberList(int group_code){
        return accountMapper.getNonCalculateMemberList(group_code);
    }

    public String getAccountNum(int account_code)
    {
        return accountMapper.getAccountNum(account_code);
    }

    public void activateGroupPay(int group_code){
        accountMapper.activateGroupPay(group_code);
    }

    public void deactivateGroupPay( int group_code){
        accountMapper.deactivateGroupPay(group_code);
    }

    public void insertGroupPayRequest(AccountPaymentDTO payment_detail,List<Map<String,Object>> memberList, int to_user_code){

        accountMapper.insertGroupPayRequest(payment_detail,memberList,to_user_code);
    }

    public int checkGroupPay(int user_code){
       return accountMapper.checkGroupPay(user_code);
    }

    public List<Map<String,Object>> getGroupPay(int user_code){
        return accountMapper.getGroupPay(user_code);
    }

    public List<Map<String,Object>> getGroupPaymentDetails(int group_code){
        return accountMapper.getGroupPaymentDetails(group_code);
    }

    public void acceptGroupPay(int user_code, int group_code){
        accountMapper.acceptGroupPay(user_code,group_code);
    }

    public void refuseGroupPay(int user_code, int group_code){
        accountMapper.refuseGroupPay(user_code,group_code);
    }

    public void enrollAccount(AccountDTO account){

        accountMapper.enrollAccount(account);
    }

    public void checkIsRequest(List<Map<String,Object>> user_list){
        accountMapper.checkIsRequest(user_list);
    }



}


