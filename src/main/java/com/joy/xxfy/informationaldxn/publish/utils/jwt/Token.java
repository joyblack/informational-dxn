package com.joy.xxfy.informationaldxn.publish.utils.jwt;

/**
 * @author 13562
 */
public enum Token {
    // 用户信息
    USER("USER", "用户信息"),
    // 请求头
    AUTHORIZATION("AUTHORIZATION-GM", "token请求头"),
    // claims
    CLAIMS("CLAIMS", "CLAIMS");

    private String name;

    private String describe;

    Token() {
    }

    Token(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
