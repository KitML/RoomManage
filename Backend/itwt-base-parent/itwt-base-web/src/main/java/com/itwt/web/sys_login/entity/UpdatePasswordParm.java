package com.itwt.web.sys_login.entity;

import lombok.Data;


@Data
public class UpdatePasswordParm {
    private Long userId;
    private String oldPassword;
    private String password;
    private String userType;
}
