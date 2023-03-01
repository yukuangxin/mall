package com.ykx.mall.form;

import javax.validation.constraints.NotBlank;
/**
 *为注册创建的表单
 */
public class UserRegisterForm {



    //@NotBlank  用于String 判断空格
    //@NotNull 判断空
    //@NotEmpty  用于集合
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public UserRegisterForm() {
    }

    public UserRegisterForm(@NotBlank String username, @NotBlank String password, @NotBlank String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
