package com.hkw.zookeeper.service;

import com.hkw.zookeeper.pojo.User;

public interface UserService {
    public String getUser();

    public User findUserById(Integer id);
}
