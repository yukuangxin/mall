package com.ykx.mall.controller;


import com.ykx.mall.consts.MallConst;
import com.ykx.mall.enums.ResponseEnum;
import com.ykx.mall.form.UserLoginForm;
import com.ykx.mall.form.UserRegisterForm;
import com.ykx.mall.pojo.User;
import com.ykx.mall.service.IUserService;
import com.ykx.mall.vo.ResponseVo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.ykx.mall.consts.MallConst.CURRENT_USER;
import static com.ykx.mall.enums.ResponseEnum.PARAM_ERROR;


/**
 * 参数 @RequestBody User user /  @RequestParam String username
 *
 * @Valid 表单校验, 控制台会提示报错
 */

@RestController
//@RequestMapping("")
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/user/register")
    public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userRegisterForm) {

     /*   //表单验证
        if (bindingResult.hasErrors()) {
            //拿到的是notBlank注解上面的信息,getField():获取字段，getDefaultMessage()：获取信息。
            log.error("注册参数有误,{}，{}", bindingResult.getFieldError().getField(), bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(PARAM_ERROR, bindingResult);
        }*/

        User user = new User();
        //拷贝，将第一个对象拷贝给第二个对象。
        BeanUtils.copyProperties(userRegisterForm, user);
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm, HttpSession session) {
        /**
         * //做了统一验证，所以不需要了 login(@Valid @RequestBody UserLoginForm userLoginForm, BindingResult bindingResult, HttpSession session
         * //表单验证
         *if (bindingResult.hasErrors()) {
         *  return ResponseVo.error(PARAM_ERROR, bindingResult);
         * }
         */

//youbianhuama
        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        //要设置sesison
        session.setAttribute(CURRENT_USER, userResponseVo.getData());
        log.info("/user session_id={}", session.getId());
        return userResponseVo;
    }

    //session存储到内存中，最好存储在redis中,改进为：token+redis.
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
       /*
       //已经替换拦截器
       if (user==null){
            return ResponseVo.error(ResponseEnum.NEED_LOGIN);

        }*/
        return ResponseVo.success(user);

    }

    @PostMapping("/user/logout")
    /**
     * {@link org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory}
     *
     */
    public ResponseVo logout(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
      /*  if (user==null){
            return ResponseVo.error(ResponseEnum.NEED_LOGIN);

        }*/
        session.removeAttribute(CURRENT_USER);
        return ResponseVo.success();
    }
}
