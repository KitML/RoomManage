package com.itwt.web.sys_login.entity;

import lombok.Data;

@Data
public class LoginParm {
    private String username;

    private String password;

    private String userType;

    private String code;
}
