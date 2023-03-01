package com.ykx.mall.controller;

import com.ykx.mall.form.CartAddForm;
import com.ykx.mall.vo.CartVo;
import com.ykx.mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 表单统一验证 验证注解@Valid
 */
@RestController
public class CartController {
    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm) {
        return null;
    }
}
