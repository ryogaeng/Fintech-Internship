package com.example.moneytransfer.service;
import com.example.moneytransfer.dto.AccountDTO;
import com.example.moneytransfer.dto.UserListDTO;
import com.example.moneytransfer.mapper.UserMapper;
import com.example.moneytransfer.dto.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public User getUserInfo(int user_code) {
        return userMapper.getUserInfo(user_code);
    }

    public void signUp(User user){

        userMapper.signUp(user);
    }
    public Map<String,Object> login(String id, String password){
        return userMapper.login(id,password);
    }

    public List<Map<String,Object>> getUserList(){

        return userMapper.getUserList();
    }

    public List<Map<String,Object>> getUserList2(){

        return userMapper.getUserList2();
    }

    public String getIdByCode(int user_code){
        return userMapper.getIdByCode(user_code);
    }


    public void editUserInfo(User user){
         userMapper.editUserInfo(user);
    }

    public List<Map<String,Object>> getUserByName(@Param("keyword") String keyword){
        return userMapper.getUserByName(keyword);
    }
    public String getUserNameByCode(int user_code) {
        return userMapper.getUserNameByCode(user_code);
    }

    public int getIsRequest( int user_code){
        return userMapper.getIsRequest(user_code);
    }
    public int getIsTaken(int user_code){
        return userMapper.getIsTaken(user_code);
    }

    public void checkIsTaken(int user_code){
        userMapper.checkIsTaken(user_code);
    }
}