package com.example.springbootdemo.service;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.controller.dto.UserDto;
import com.example.springbootdemo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Cigacase
 * @since 2024-06-14
 */
public interface IUserService extends IService<User> {

    UserDto login(UserDto userDto);

    User register(UserDto userDto);
}
