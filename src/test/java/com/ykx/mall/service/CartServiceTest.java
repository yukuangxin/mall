package com.ykx.mall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ykx.mall.MallApplicationTests;
import com.ykx.mall.form.CartAddForm;
import com.ykx.mall.vo.CartVo;
import com.ykx.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
@Slf4j
public class CartServiceTest extends MallApplicationTests {
@Autowired
private ICartService cartService;
private Gson gson=new GsonBuilder().setPrettyPrinting().create();
    @Test
    public void add() {
        CartAddForm form=new CartAddForm();
        form.setProductId(29);
        form.setSelected(true);
        cartService.add(1,form);
    }

    @Test
    public void test() {
        ResponseVo<CartVo> list = cartService.list(1);
        log.info("list={}",gson.toJson(list));
    }
}