package com.ykx.mall.enums;

public enum ProductStatusEnum {
    ON_SALE(1,"在售"),
    OFF_SALE(2,"下架"),
    DELETE_SALE(3,"删除"),
    ;
    //状态码
    Integer code;
    //状态文字
    String desc;

    ProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
