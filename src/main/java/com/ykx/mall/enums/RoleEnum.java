package com.ykx.mall.enums;

import lombok.Getter;

/**
 * 0-管理员，1-客户
 */
@Getter
public enum RoleEnum {
    ADMIN(0),

    CUSTOMER(1),

            ;
    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
