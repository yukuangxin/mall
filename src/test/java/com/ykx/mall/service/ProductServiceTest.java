package com.ykx.mall.service;

import com.ykx.mall.MallApplicationTests;
import com.ykx.mall.enums.ResponseEnum;
import com.ykx.mall.vo.ProductDetailVo;
import com.ykx.mall.vo.ProductVo;
import com.ykx.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceTest extends MallApplicationTests {
    @Autowired
    private IProductService productService;

    @Test
    public void list() {
       // ResponseVo<List<ProductVo>> responseVo = productService.list(null, 1, 1);
     // Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void detail() {
        //ResponseVo<ProductDetailVo> responseVo = productService.detail(28);
        // Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}