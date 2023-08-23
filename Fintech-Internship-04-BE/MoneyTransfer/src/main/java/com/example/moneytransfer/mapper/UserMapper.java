package com.example.moneytransfer.mapper;


import com.example.moneytransfer.dto.AccountDTO;
import com.example.moneytransfer.dto.User;
import com.example.moneytransfer.dto.UserListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper {
    public User getUserInfo(@Param("user_code") int userCode);

    public void signUp(@Param("user") User user);

    public Map<String,Object> login(@Param("id") String id,
                         @Param("password") String password);

    public List<Map<String,Object>> getUserList();

    public List<Map<String,Object>> getUserList2();
    public List<AccountDTO> getAccountList(@Param("user_code")
                                           int user_code);

    public String getIdByCode(@Param("user_code") int user_code);

    public void editUserInfo(@Param("user") User user);

    public List<Map<String,Object>> getUserByName(@Param("keyword") String keyword);

    public String getUserNameByCode(@Param("user_code") int user_code);

    public int getIsRequest(@Param("user_code") int user_code);

    public int getIsTaken(@Param("user_code") int user_code);

    public void checkIsTaken(@Param("user_code") int user_code);

}
