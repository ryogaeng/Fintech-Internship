package com.example.moneytransfer.mapper;


import com.example.moneytransfer.dto.Group;
import com.example.moneytransfer.dto.GroupAddDTO;
import com.example.moneytransfer.dto.GroupCreateDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
@Mapper
@Repository
public interface GroupMapper {
    //모임 생성하기
    public void createGroup(@Param("group_request") GroupCreateDTO groupRequest);

    //모임에 멤버 추가하기
    public void inviteMembers(@Param("group_code") Integer group_code,
            @Param("user_list") List<Map<String,Object>> userList);

    //모임에서 멤버 삭제하기
    public void deleteMembers(@Param("delete_list") List<Integer> deleteUserList);

    public List<Map<String,Object>> getMemberListFromGroup(@Param("group_code") int group_code);
    public List<Map<String,Object>> getGroupList(@Param("user_code") Integer user_code);

    public void leaveGroup(@Param("user_code") Integer user_code,
                           @Param("group_code") Integer group_code);
    //모임 이름 수정
    public void editGroupName(@Param("group_code") int group_code, @Param("group_name") String name);

    public int getGroupCode(@Param("group_name") String name);

    public int getGroupHeadCount(@Param("group_code") Integer group_code);

    public void deleteGroup(@Param("group_code") Integer group_code);

    public void acceptInvite(@Param("user_code") int user_code, @Param("group_code") int group_code);
    public void disableInvite(@Param("user_code") Integer user_code);

    public void inviteMembers(@Param("user_code") int user_code, @Param("user_list") List<Map<String,Object>> userList);
    public List<Map<String,Object>> getMemberListFromGroupExceptMe(@Param("user_code") int user_code, @Param("group_code") int group_code);

    public List<Map<String,Object>> checkInvite(@Param("user_code") int user_code);

    public Map<String,Object> getNameAndDateFromGroup(@Param("group_code") int group_code);

    public String getNameFromGroup(@Param("group_code") int group_code);

    public String getGroupOwnerName(@Param("group_code") int group_code);
    public LocalDateTime getDateFromGroup(@Param("group_code") int group_code);
}
