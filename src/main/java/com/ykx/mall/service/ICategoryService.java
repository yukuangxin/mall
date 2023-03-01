package com.ykx.mall.service;

import com.ykx.mall.vo.CategoryVo;
import com.ykx.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {
    ResponseVo<List<CategoryVo>> selectAll();
    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
