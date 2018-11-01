package com.ye.FirstBoot.dataAccess.mybatis.dao;

import java.util.List;

import com.ye.FirstBoot.dataAccess.mybatis.model.User;



public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> selectAllUsers();
}