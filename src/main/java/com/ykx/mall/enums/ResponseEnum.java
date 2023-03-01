package com.ykx.mall.enums;

/**
 * 转台吗自定义为枚举类型
 */
public enum ResponseEnum {
    ERROR(-1,"服务端错误"),
    SUCCESS(0,"成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USERNAME_EXIT(2,"用户名已存在"),
    PARAM_ERROR(3,"参数错误"),
    EMAIL_EXIT(4,"邮箱已存在"),

    NEED_LOGIN(10,"请先登录"),
    USERNAME_OR_PASSWORD_ERROR(11,"用户名或密码错误"),
    PRODUCT_OFF_SALE_OR_DETELE(12,"商品下架或者删除"),
    PRODUCT_NOT_EXIST(13,"商品不存在"),
    PRODUCT_STOCK_REEOR(14,"商品库存不足"),
    ;
    //状态码
    Integer code;
    //状态文字
    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
