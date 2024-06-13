package com.example.springbootdemo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public boolean saveUser(User user) {
        return saveOrUpdate(user);
    }

//    @Autowired
//    private UserMapper userMapper;

//    public int save(User user){
//        if(user.getId() == null){
//            return userMapper.insert(user);
//        }else{
//            // 更新
//            return userMapper.update(user);
//        }
//    }
}
