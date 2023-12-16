package com.hkw.zookeeper.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hkw.zookeeper.pojo.User;
import com.hkw.zookeeper.service.UserService;

//timeout 默认超时时间位1s，这边设置为3s,同时消费者那边也可以设置时间，以消费者优先高于这边，都是一般在这边设置，
// retries 重试默认为2次，加上第一次，所以一共三次，这边是把重试设置为4
@Service(timeout = 3000,retries = 4,version = "v2.0")
public class UserServiceImpl2 implements UserService {
    @Override
    public String getUser() {
        return "getUser成功了";
    }

    @Override
    public User findUserById(Integer id) {
        System.out.println("new.....");
        User user = new User(id,"李华",18);
        return user;
    }
}
