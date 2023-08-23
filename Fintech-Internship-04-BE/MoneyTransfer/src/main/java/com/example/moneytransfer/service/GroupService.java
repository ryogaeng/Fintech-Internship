package com.example.moneytransfer.service;
import com.example.moneytransfer.dto.Group;
import com.example.moneytransfer.dto.GroupAddDTO;
import com.example.moneytransfer.dto.GroupCreateDTO;
import com.example.moneytransfer.mapper.GroupMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
@Service
public class GroupService {

    @Autowired
    GroupMapper groupMapper;
    public void createGroup(GroupCreateDTO groupRequest){

        groupMapper.createGroup(groupRequest);
    }

    public void inviteMembers(Integer group_code,List<Map<String,Object>> userList){


        groupMapper.inviteMembers(group_code, userList);
    }

    public List<Map<String,Object>> getGroupList(Integer user_code){


        List<Map<String,Object>> list = groupMapper.getGroupList(user_code);

        System.out.println(list);
        for(Map<String,Object> item : list)
        {
            int group_code = (int)item.get("group_code");
            List<Map<String,Object>> userList = groupMapper.getMemberListFromGroup(group_code);
            item.put("userList",userList);
        }

        return list;
    }

    public List<Map<String,Object>> getMemberListFromGroup(int group_code){
        return groupMapper.getMemberListFromGroup(group_code);
    }


    public String getGroupOwnerName( int group_code){
        return groupMapper.getGroupOwnerName(group_code);
    }
    public Map<String,Object> getNameAndDateFromGroup(int group_code){
        return groupMapper.getNameAndDateFromGroup(group_code);
    }
    public List<Map<String,Object>> getMemberListFromGroupExceptMe(int user_code, int group_code){
        return groupMapper.getMemberListFromGroupExceptMe(user_code,group_code);
    }

    public String getNameFromGroup(@Param("group_code") int group_code){
        return groupMapper.getNameFromGroup(group_code);
    }
    public LocalDateTime getDateFromGroup(@Param("group_code") int group_code){
        return groupMapper.getDateFromGroup(group_code);
    }

    public void leaveGroup(Integer user_code,
                            Integer group_code)
    {
        groupMapper.leaveGroup(user_code,group_code);
        if(groupMapper.getGroupHeadCount(group_code)==0)
        {
            groupMapper.deleteGroup(group_code);
        }

    }

    public void editGroupName(int group_code, String name){
        groupMapper.editGroupName(group_code, name);
    }


    public int getHeadCount(int group_code)
    {
        return groupMapper.getGroupHeadCount(group_code);
    }

    public void disableInvite(Integer user_code) {
        groupMapper.disableInvite(user_code);
    }
    public void acceptInvite(int user_code, int group_code){
        groupMapper.acceptInvite(user_code,group_code);
    }

    public List<Map<String,Object>> checkInvite(int user_code){
        return groupMapper.checkInvite(user_code);
    }

}