package com.platform.enums;


/**
 * 返回码枚举
 */
public enum ResultCodeEnum {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 未授权
     */
    UNAUTHORIZED(401, "token失效，请重新登录"),
    /**
     * 资源/服务未找到
     */
    NOT_FOUND(404, "路径不存在，请检查路径是否正确"),
    /**
     * 操作失败
     */
    FAIL(500, "系统异常，请联系管理员"),
    /**
     * token失效
     */
    TOKEN(500, "token失效，请重新登录"),
    /**
     * 接口权限不足
     */
    AUTH(701, "接口权限不足"),
    /**
     * 版本号
     */
    VERSION(801, "版本过低，请升级"),
    ;

    private final Integer code;
    private final String info;

    ResultCodeEnum(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
