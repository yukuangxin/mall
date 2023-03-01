package com.ykx.mall.service.impl;


import com.ykx.mall.dao.UserMapper;
import com.ykx.mall.enums.ResponseEnum;
import com.ykx.mall.enums.RoleEnum;
import com.ykx.mall.pojo.User;
import com.ykx.mall.service.IUserService;
import com.ykx.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

import static com.ykx.mall.enums.ResponseEnum.*;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {
        //error();
        //用户名，邮箱不能重复
        System.out.println("欢迎光临");
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
//            throw new RuntimeException("该用户名已被注册");
            return ResponseVo.error(USERNAME_EXIT);
        }
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
//            throw new RuntimeException("邮箱已经被注册");
            return ResponseVo.error(EMAIL_EXIT);

        }
        //注册默认为普通用户
        user.setRole(RoleEnum.CUSTOMER.getCode());
//密码MD5加密,DigestUtils(摘要算法)Spring自带
        user.setPassword(
                DigestUtils.md5DigestAsHex(
                        user.getPassword().getBytes(StandardCharsets.UTF_8)
                ));

        //写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
//            throw new RuntimeException("注册失败");
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.success();

    }


   /* private void error(){
        throw new RuntimeException("意外错误");
    }*/


    /**
     * 登录
     */
    @Override
    public ResponseVo<User> login(String username, String password) {
        //查用户
        User user = userMapper.selectByUsername(username);

        if (user == null) {
            //用户名不存在
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }

        if (!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(
                password.getBytes(StandardCharsets.UTF_8)))) {
            //密码错误
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassword("");
        return ResponseVo.success(user);
    }
}
