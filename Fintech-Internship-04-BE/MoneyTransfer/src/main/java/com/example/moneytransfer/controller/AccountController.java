package com.example.moneytransfer.controller;


import com.example.moneytransfer.dto.AccountDTO;
import com.example.moneytransfer.dto.AccountPaymentDTO;
import com.example.moneytransfer.service.AccountService;
import com.example.moneytransfer.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    GroupService groupService;


    //Post 메서드 : 출금하기
    @PostMapping("/deposit")
    public boolean deposit(@RequestBody AccountPaymentDTO payment_detail)
    {
        try{
            return accountService.deposit(payment_detail);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Post 메서드 : 그룹 정산,
    @PostMapping("/groupDeposit")
    public boolean groupDeposit(@RequestBody AccountPaymentDTO payment_detail)
    {
        try{
            accountService.deposit(payment_detail);
            int tran_amt = payment_detail.getTran_amt();
            int group_code = payment_detail.getGroup_code();
            int user_code = payment_detail.getUser_code();
            int headcount = groupService.getHeadCount(group_code);
            System.out.println(tran_amt);
            System.out.println(headcount);

            int group_tran_amt = -tran_amt;
            group_tran_amt = (int) Math.ceil(group_tran_amt*1.0/headcount);
            group_tran_amt = - group_tran_amt;
            payment_detail.setTran_amt(group_tran_amt);

            List<Map<String,Object>> memberList = groupService.getMemberListFromGroupExceptMe(user_code,group_code);
            int to_user_code = payment_detail.getUser_code();
            accountService.insertGroupPayRequest(payment_detail, memberList,to_user_code);

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @PostMapping("/checkIsRequest")
    public boolean checkIsRequest(@RequestBody Map<String,Object> request){

        try{
            accountService.checkIsRequest((List<Map<String,Object>>)request.get("user_list"));
            return true;
        }catch(Exception e)
        {

            e.printStackTrace();
            return false;
        }

    }

    @GetMapping("/getAccountList/{user_code}")
    public List<AccountDTO> getAccountList(@PathVariable Integer user_code)
    {
        return accountService.getAccountList(user_code);
    }

    @GetMapping("/getTransferHistory/{account_code}")
    public List<AccountDTO> getTransferHistory(@PathVariable int account_code
    )
    {
        List<AccountDTO> list = accountService.getTransferHistory(account_code);
        String account_num = accountService.getAccountNum(account_code);
        System.out.println(account_num);

        for(AccountDTO item:list)
        {
            item.setAccount_num(account_num);
        }
        System.out.println("hello");
       return list;
    }

    
    @GetMapping("/getAccountNum/{account_code}")
    public String getAccountNum(@PathVariable int account_code)
    {
        return accountService.getAccountNum(account_code);
    }

    @GetMapping("/getNonCalculateMemberList/{group_code}")
    public List<Map<String,Object>> getNonCalculateMemberList(@PathVariable int group_code
    )
    {
        return accountService.getNonCalculateMemberList(group_code);
    }


    @GetMapping("/getBalance/{account_code}")
    public int getBalance(@PathVariable int account_code)
    {

        return accountService.getBalance(account_code);
    }

    @GetMapping("/checkGroupPay/{user_code}")
    public int checkGroupPay(@PathVariable int user_code){

        try{
            Integer flag = accountService.checkGroupPay(user_code);
            if(flag==null)
                return 0;
            else
                return flag;
        }catch(Exception e)
        {
            e.printStackTrace();
            return -1;
        }

    }


    @GetMapping("/getGroupPay/{user_code}")
    public ResponseEntity<List<Map<String,Object>>> getGroupPay(@PathVariable int user_code)
    {
        try{
            List<Map<String,Object>> list = accountService.getGroupPay(user_code);
            return new ResponseEntity<List<Map<String,Object>>>(list,HttpStatus.OK);
        }catch(Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/acceptGroupPay")
    public ResponseEntity<Void> acceptGroupPay(@RequestBody Map<String,Object> request)
    {

        try{
            AccountPaymentDTO dto = new AccountPaymentDTO();
            dto.setTran_amt((int)request.get("tran_amt"));
            dto.setPayment_dest((String)request.get("payment_dest"));
            dto.setUser_code((int)request.get("user_code"));
            dto.setGroup_code((int)request.get("group_code"));
            dto.setTo_user_code((int)request.get("to_user_code"));

            accountService.deposit(dto);

            accountService.acceptGroupPay((int)request.get("user_code"),
                    (int)request.get("group_code"));
            return new ResponseEntity<>(HttpStatus.OK);

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/refuseGroupPay")
    public ResponseEntity<Void> refuseGroupPay(@RequestBody Map<String,Object> request)
    {

        try{
            AccountPaymentDTO dto = new AccountPaymentDTO();
            dto.setTran_amt((int)request.get("tran_amt"));
            dto.setPayment_dest((String)request.get("payment_dest"));
            dto.setUser_code((int)request.get("user_code"));
            dto.setGroup_code((int)request.get("group_code"));
            dto.setTo_user_code((int)request.get("to_user_code"));
            dto.set_taken(false);
            accountService.deposit(dto);

            accountService.refuseGroupPay((int)request.get("user_code"),
                    (int)request.get("group_code"));
            return new ResponseEntity<>(HttpStatus.OK);

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getGroupPaymentDetails/{group_code}")
    public ResponseEntity<List<Map<String,Object>>> getGroupPaymentDetails(@PathVariable int group_code)
    {
        List<Map<String,Object>> list =  accountService.getGroupPaymentDetails(group_code);
        if(list==null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<List<Map<String,Object>>>(list,HttpStatus.OK);
        }
    }


    @PostMapping("/enrollAccount")
    public ResponseEntity<Void> enrollAccount(@RequestBody AccountDTO account) {
        try{
            accountService.enrollAccount(account);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
