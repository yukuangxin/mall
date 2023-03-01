package com.ykx.mall.service;

import com.github.pagehelper.PageInfo;
import com.ykx.mall.vo.ProductDetailVo;
import com.ykx.mall.vo.ProductVo;
import com.ykx.mall.vo.ResponseVo;

import java.util.List;

public interface IProductService {
    //分页查询商品
    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);
    //商品详情
    ResponseVo<ProductDetailVo> detail(Integer id);
}
