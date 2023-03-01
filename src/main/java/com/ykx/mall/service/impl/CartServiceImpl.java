package com.ykx.mall.service.impl;

import com.google.gson.Gson;
import com.ykx.mall.dao.ProductMapper;
import com.ykx.mall.enums.ProductStatusEnum;
import com.ykx.mall.enums.ResponseEnum;
import com.ykx.mall.form.CartAddForm;
import com.ykx.mall.pojo.Cart;
import com.ykx.mall.pojo.Product;
import com.ykx.mall.service.ICartService;
import com.ykx.mall.vo.CartProductVo;
import com.ykx.mall.vo.CartVo;
import com.ykx.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements ICartService {
    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";
    @Resource
    private ProductMapper productMapper;
    @Resource
    private StringRedisTemplate redisTemplate;
    //转成json 需要的对象
    private Gson gson = new Gson();

    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm form) {
        Integer quantity = 1;
        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        //商品是否存在
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //商品是否在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DETELE);
        }
        //商品库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_REEOR);
        }
        //校验完写入到redis中
        //key:cart_1
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        //redis_id
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        //
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        System.out.println(value);
        Cart cart;
        if (StringUtils.isEmpty(value)) {
            //说明redis中没有该商品，新增该商品
            cart = new Cart(product.getId(), quantity, form.getSelected());
        } else {
            //redis中已经有了，数量要加1

            //反序列化，将redis中的json格式转换成对象
            cart = gson.fromJson(value, Cart.class);
            //cart数量加1
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        //将新对象序列化到redis中
        opsForHash.put(redisKey, String.valueOf(product.getId()), gson.toJson(cart));


        return null;
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        //获取列表
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        //定义变量
        Boolean selectAll = true;
        Integer  cartTotalQuantity= 0;
        BigDecimal cartTotalPrice=BigDecimal.ZERO;


        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        
        for (Map.Entry<String, String> entry : entries.entrySet()
        ) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);

            // 需要优化，使用mysql里的in
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                CartProductVo cartProductVo = new CartProductVo(
                        productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected()
                );
                cartProductVoList.add(cartProductVo);

                //查看是否被选中
                if (!cart.getProductSelected()) {
                    selectAll = false;
                }
                //购物车选中商品总价
                if(cart.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }
            cartTotalQuantity += cart.getQuantity();

        }
        cartVo.setCartProductVoList(cartProductVoList);
        //是否全选
        cartVo.setSelectAll(selectAll);
        //总价
        cartVo.setCartTotalPrice(cartTotalPrice);
        //
        cartVo.setCartTotalQuantity(cartTotalQuantity);

        return ResponseVo.success(cartVo);
    }
}
