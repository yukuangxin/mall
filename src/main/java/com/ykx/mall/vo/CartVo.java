package com.ykx.mall.vo;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车
 *
 */
@Data
public class CartVo {
    private List<CartProductVo> cartProductVoList;
    //是否全选
    private Boolean selectAll;
    //购物车商品总价
    private BigDecimal cartTotalPrice;
    //所有商品总数量
    private Integer cartTotalQuantity;

}
