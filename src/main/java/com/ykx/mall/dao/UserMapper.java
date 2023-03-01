package com.ykx.mall.dao;


import com.ykx.mall.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);
    User selectByUsername(String username);
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    int countByUsername(String username);
    int countByEmail(String email);
}