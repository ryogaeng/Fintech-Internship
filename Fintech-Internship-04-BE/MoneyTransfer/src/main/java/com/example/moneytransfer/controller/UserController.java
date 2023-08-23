package com.example.moneytransfer.controller;



import com.example.moneytransfer.dto.AccountDTO;
import com.example.moneytransfer.dto.GroupAddDTO;
import com.example.moneytransfer.dto.User;
import com.example.moneytransfer.dto.UserListDTO;
import com.example.moneytransfer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    //private final UserService userService;
    //그룹 생성

    @Autowired
    UserService userService;
    //그룹에 인원 추가


    @GetMapping("/getUserInfo/{user_code}")
    public ResponseEntity<User> getUserInfo(@PathVariable int user_code){
        if(userService.getUserInfo(user_code)==null)
        {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<User>(userService.getUserInfo(user_code),HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody User user)
    {
        try{
            userService.signUp(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody Map<String, Object> request)
    {
        System.out.println(request);
        String id = (String)request.get("id");
        String password = (String)request.get("password");
        System.out.println(userService.login(id,password));
        if(userService.login(id,password)==null){
            System.out.println("hello");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Map<String,Object>>(userService.login(id,password),HttpStatus.OK);
    }



    @GetMapping("/getUserNameByCode/{user_code}")
    public String getUserNameByCode(int user_code) {
        return userService.getUserNameByCode(user_code);
    }

    @GetMapping("/getUserList")
    public ResponseEntity<List<Map<String,Object>>> getUserList(){
        List<Map<String,Object>> list = userService.getUserList();
        if(list==null)
        {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<Map<String,Object>>>(list,HttpStatus.OK);
    }

    @GetMapping("/getUserList2")
    public ResponseEntity<List<Map<String,Object>>> getUserList2(){

        List<Map<String,Object>> list = userService.getUserList2();
//        if(list==null)
//        {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        return new ResponseEntity<List<Map<String,Object>>>(list,HttpStatus.OK);
    }
    @GetMapping("/getIsRequest/{user_code}")
    public int getIsRequest(@PathVariable int user_code){
        return userService.getIsRequest(user_code);
    }

    @GetMapping("/getIdByCode/{user_code}")
    public String getIdByCode(@PathVariable int user_code)
    {
        return userService.getIdByCode(user_code);
    }


    @PostMapping("/editUserInfo")
    public boolean editUserInfo(@RequestBody User user)
    {
        try{
            userService.editUserInfo(user);
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

    @GetMapping("/getUserByName/{keyword}")
    public List<Map<String,Object>> getUserByName(@PathVariable String keyword){
        return userService.getUserByName(keyword);
    }

    @GetMapping("/getIsTaken/{user_code}")
    public int getIsTaken(@PathVariable int user_code){
        return userService.getIsTaken(user_code);
    }

    @PostMapping("/checkIsTaken/{user_code}")
    public void checkIsTaken(@PathVariable int user_code){
        userService.checkIsTaken(user_code);
    }

}
