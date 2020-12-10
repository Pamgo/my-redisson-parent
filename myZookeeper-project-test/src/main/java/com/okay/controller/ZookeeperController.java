package com.okay.controller;

import com.okay.aspect.ClusterLock;
import com.okay.service.ZookeeperImpl;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @create 2020-07-28 14:27
 */
@RestController
@RequestMapping("/zookeeper/")
public class ZookeeperController {
    @Autowired
    private CuratorFramework zkClient;
    @Autowired
    private ZookeeperImpl zookeeper;

    @PostMapping("makeOrder")
    public String makeOrder(@RequestParam("product") String product) {
        zookeeper.makeOrder(product);
        return "success";
    }

    @ClusterLock(LOCK_PATH = "/lock-01/test1")
    @PostMapping("anMakeOrder")
    public String anMakeOrder(@RequestParam("product") String product) {
        try {
            System.out.println(Thread.currentThread().getName()+"=>模拟开始");
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
