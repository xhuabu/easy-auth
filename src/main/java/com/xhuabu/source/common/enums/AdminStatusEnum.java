package com.xhuabu.source.common.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vicky on 17/10/8.
 */
public enum AdminStatusEnum {

    ADMIN_STATUS_FREEZE(-1,"冻结"),
    ADMIN_STATUS_INIT(0,"初始"),
    ADMIN_STATUS_NORMAL(1,"正常使用");

    /**
     * 类型码
     */
    private Integer code;

    /**
     * 类型描述
     */
    private String description;

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    AdminStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    /**
     * 根据code得到相应的枚举常量
     */
    public static AdminStatusEnum getEnumFromCode(Integer code) {
        List<AdminStatusEnum> adminStatusEnumList = Arrays.asList(AdminStatusEnum.values());
        for (AdminStatusEnum adminStatusEnum : adminStatusEnumList) {
            if (adminStatusEnum.getCode() == code) {
                return adminStatusEnum;
            }
        }
        return null;
    }


}
