package com.example.moneytransfer.controller;


import com.example.moneytransfer.dto.Group;
import com.example.moneytransfer.dto.GroupAddDTO;
import com.example.moneytransfer.dto.GroupCreateDTO;
import com.example.moneytransfer.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
@RestController
@CrossOrigin
@RequestMapping("/api/group")
public class GroupController {


    @Autowired
    GroupService groupService;


    @Transactional
    @PostMapping("/createGroup")
    public ResponseEntity<Integer> createGroup(@RequestBody Map<String, Object> request) {
        Integer group_code;
        try{
           System.out.println(request);

           GroupCreateDTO groupRequest = new GroupCreateDTO();

           groupRequest.setOwner_code((Integer) request.get("user_code"));
           groupRequest.setGroup_name((String) request.get("group_name"));
           groupRequest.setHeadcount((Integer) request.get("headcount"));


           List<Map<String,Object>> userList = (List<Map<String,Object>>) request.get("user_list");


           System.out.println(groupRequest);
           System.out.println(userList);


           groupService.createGroup(groupRequest);
           group_code = groupRequest.getGroup_code();
           Integer user_code  = groupRequest.getOwner_code();

           System.out.println(group_code);

           groupService.inviteMembers(group_code, userList);


       }catch(Exception e)
       {
           e.printStackTrace();
           return new ResponseEntity<Integer>(0,HttpStatus.BAD_REQUEST);
       }

        return new ResponseEntity<Integer>(group_code,HttpStatus.OK);
    }



    @GetMapping("/getGroupList/{user_code}")
    public List<Map<String,Object>> getGroupList(@PathVariable Integer user_code) {

        return groupService.getGroupList(user_code);
    }

    @PostMapping("/leaveGroup")
    public boolean leaveGroup(@RequestParam Integer user_code,
                           @RequestParam Integer group_code)
    {
        try{
            groupService.leaveGroup(user_code,group_code);
        }catch(Exception e){
            return false;
        }

        return true;

    }

    @PostMapping("editGroupName/{group_code}/{group_name}")
    public void editGroupName(@PathVariable int group_code,
                              @PathVariable String group_name){

        groupService.editGroupName(group_code,group_name);
    }

    @PostMapping("/disableInvite/{user_code}")
    public ResponseEntity<Void> disableInvite(@PathVariable int user_code){

        try{
            groupService.disableInvite(user_code);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/acceptInvite")
    public ResponseEntity<String> acceptInvite(@RequestBody Map<String,Object> request){
        try{
            int user_code = (int)request.get("user_code");
            int group_code = (int)request.get("group_code");
            groupService.acceptInvite(user_code,group_code);
            groupService.disableInvite(user_code);
            return new ResponseEntity("초대수락에 성공했습니다.",HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity("초대수락에 실패했습니다.",HttpStatus.BAD_REQUEST);

        }


    }
    
    @GetMapping("/getMemberListFromGroup/{group_code}")
    public ResponseEntity<Map<String,Object>> getMemberListFromGroup(@PathVariable int group_code){
        List<Map<String,Object>> list = groupService.getMemberListFromGroup(group_code);
        String group_name = groupService.getNameFromGroup(group_code);
        LocalDateTime date = groupService.getDateFromGroup(group_code);
        String owner_name = groupService.getGroupOwnerName(group_code);
        Map<String,Object> map = new HashMap<String,Object>();
        if(list==null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        System.out.println(list);
        map.put("group_name",group_name);
        map.put("createdAt",date);
        map.put("owner_name", owner_name);
        map.put("user_list",list);
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }



    @GetMapping("/getMemberListFromGroupExceptMe")
    public ResponseEntity<List<Map<String,Object>>> getMemberListFromGroupExceptMe(@RequestParam int user_code,
                                                                                   @RequestParam int group_code){
        List<Map<String,Object>> list = groupService.getMemberListFromGroupExceptMe(user_code,group_code);
        if(list==null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<Map<String,Object>>>(list,HttpStatus.OK);
    }

    @GetMapping("/checkInvite/{user_code}")
    public List<Map<String,Object>> checkInvite(@PathVariable int user_code){
        return groupService.checkInvite(user_code);
    }


}
