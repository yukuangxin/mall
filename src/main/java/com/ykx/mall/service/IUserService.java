package com.ykx.mall.service;

import com.ykx.mall.pojo.User;
import com.ykx.mall.vo.ResponseVo;

public interface IUserService {
    /*
    注册*/
    ResponseVo register(User user);

/*
登录
* */
ResponseVo login(String username,String password);

}
