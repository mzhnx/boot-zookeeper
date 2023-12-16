package com.hkw.zookeeper.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hkw.zookeeper.pojo.User;
import com.hkw.zookeeper.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//缓存机制，当我们第一次调用服务者后，我们回进行缓存服务者地址，之后即使注册中心挂了，也可以继续调用，
//但是在后续如果修改了服务者代码，仍然需要注册中心的通知
//loadbalance负载均衡策略 有下面四种,连续按两次shift可以收缩类，
// 关于这个直接收索loadbalance的抽象类，然后挨个找就可以了
//① RoundRobin(roundrobin):  计算权重，根据权重配置查询次数，然后轮询。
//② Random(random):  从不同权重的N个元素中随机选择一个，并使得总体选择结果是按照权重分布的。
//③ LeastActive(leastactive):统计上一次查询执行时间，选择最少的时间的服务器作为本次命中点。
//④ConsistentHash:根据哈希值计算命中点,即可以看为参数一样（如下面findUser中id）的永远到同一台服务器
// cluster = "failover"这个是配置集群容错机制的，下面有一张图片（集群容错机制.jpg）可以看一下
//服务降级, 这个是因为我们的机器无法承受，所以要进行一些其他业务的放弃，或者其他处理，有下面两种
//(mock="force:return+nul1")，直接不调用这个服务返回空
//(mock="fail:return+nul1") 调用错误返回空

@RestController
@RequestMapping(value = "/user")
public class UserController {

//    @Reference(timeout = 1000)
//    @Reference(cluster = "failover")
//    @Reference(mock="force:return+nul1")
    @Reference(version = "v2.0",loadbalance = "random",cluster = "failover")
    private UserService userService;

    @GetMapping("/getUser")
    String getUser(){
      return  userService.getUser();
    }

    @GetMapping("/getUserById/{id}")
    User findUser(@PathVariable Integer id){
        return  userService.findUserById(id);
    }
}
