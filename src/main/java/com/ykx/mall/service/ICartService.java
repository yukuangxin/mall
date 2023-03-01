package com.ykx.mall.service;

import com.ykx.mall.form.CartAddForm;
import com.ykx.mall.vo.CartVo;
import com.ykx.mall.vo.ResponseVo;

public interface ICartService {
    ResponseVo<CartVo> add(Integer uid,CartAddForm form);
    ResponseVo<CartVo> list(Integer uid);
}
