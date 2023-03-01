package com.ykx.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ykx.mall.dao.ProductMapper;

import com.ykx.mall.enums.ProductStatusEnum;
import com.ykx.mall.pojo.Product;
import com.ykx.mall.service.ICategoryService;
import com.ykx.mall.service.IProductService;

import com.ykx.mall.vo.ProductDetailVo;
import com.ykx.mall.vo.ProductVo;
import com.ykx.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.ykx.mall.enums.ProductStatusEnum.DELETE_SALE;
import static com.ykx.mall.enums.ProductStatusEnum.OFF_SALE;
import static com.ykx.mall.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DETELE;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {
    @Resource
    private ICategoryService categoryService;
    @Resource
    private ProductMapper productMapper;

    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();

        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }
//1.
    /*    List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        log.info("products={}",products);
        List<ProductVo> productVoList=new ArrayList<>();
        for (Product product: products
             ) {
            ProductVo productVo=new ProductVo();
            BeanUtils.copyProperties(product,productVo);
            productVoList.add(productVo);
        }*/
        PageHelper.startPage(pageNum,pageSize);
        //2.
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVo> productVoList = products.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                }).collect(Collectors.toList());
        //分页
        PageInfo pageInfo=new PageInfo<>(products);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
       if(product.getStatus().equals(OFF_SALE.getCode()) || product.getStatus().equals(DELETE_SALE.getCode()) ){
           //下架或删除
           return ResponseVo.error(PRODUCT_OFF_SALE_OR_DETELE);
       }
        ProductDetailVo productDetailVo=new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);
        return ResponseVo.success(productDetailVo);
    }
}
