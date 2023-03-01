package com.ykx.mall.service.impl;

import com.ykx.mall.MallApplication;
import com.ykx.mall.MallApplicationTests;
import com.ykx.mall.enums.ResponseEnum;
import com.ykx.mall.pojo.User;
import com.ykx.mall.service.IUserService;
import com.ykx.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
public class UserServiceImplTest extends MallApplicationTests {
public static final String USERNAME="jack";
public static final String PASSWORD="jack";

    @Autowired
    private IUserService iUserService;

    @Test
    public void register() {

    }

    @Test
    public void login() {
        ResponseVo<User> responseVo=iUserService.login(USERNAME,PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());

    }
}