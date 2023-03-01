package com.ykx.mall.form;


import javax.validation.constraints.NotBlank;

/**
 *为表单创建的类
 */
public class UserLoginForm {
    //@NotBlank  用于String 判断空格
    //@NotNull 判断空
    //@NotEmpty  用于集合
    @NotBlank
    private String username;
    @NotBlank
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public UserLoginForm() {
    }

    public UserLoginForm(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
    }
}
