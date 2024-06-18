package com.example.springbootdemo.controller.dto;

import lombok.Data;

/**
 * 接收前端登陆请求的参数
 */
@Data
public class UserDto {
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String token;
}
