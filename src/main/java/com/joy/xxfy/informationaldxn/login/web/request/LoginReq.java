package com.joy.xxfy.informationaldxn.login.web.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class LoginReq implements Serializable {

    private static final long serialVersionUID = 7562653215764454872L;

    private String phone;

    private String authCode;

    private String loginName;

    private String password;
}
