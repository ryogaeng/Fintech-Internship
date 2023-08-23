package com.example.moneytransfer.mapper;


import com.example.moneytransfer.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AccountMapper {

    public void deposit(@Param("payment_detail") AccountPaymentDTO payment_detail);

    public List<AccountDTO> getAccountList(@Param("user_code") int user_code);

    public List<AccountDTO> getTransferHistory(@Param("account_code") int account_code);
    public void createPaymentDetail(@Param("payment_detail") AccountPaymentDTO payment_detail);

    public int getBalance(@Param("account_code") int account_code);

    public String getUserId(@Param("user_code") int user_code);

    public List<Map<String,Object>> getNonCalculateMemberList(@Param("group_code") int group_code);

    public String getAccountNum(@Param("account_code") int account_code);

    public void activateGroupPay(@Param("group_code") int group_code);

    public void deactivateGroupPay(@Param("group_code") int group_code);

    public int getAccountCode(@Param("user_code") int user_code);

    public boolean insertGroupPayRequest(@Param("payment_detail") AccountPaymentDTO payment_detail,
                                         @Param("member_list") List<Map<String,Object>> memberList
                                        ,@Param("to_user_code") int to_user_code);
    public List<Map<String,Object>> getGroupPay(@Param("user_code") int group_code);
    public int checkGroupPay(@Param("user_code") int user_code);

    public void acceptGroupPay(@Param("user_code") int user_code, @Param("group_code") int group_code);


    public void refuseGroupPay(@Param("user_code") int user_code, @Param("group_code") int group_code);
    public List<Map<String,Object>> getGroupPaymentDetails(@Param("group_code") int group_code);

    public void enrollAccount(@Param("account") AccountDTO account);

    public void checkIsRequest(@Param("user_list") List<Map<String,Object>> user_list);


}
