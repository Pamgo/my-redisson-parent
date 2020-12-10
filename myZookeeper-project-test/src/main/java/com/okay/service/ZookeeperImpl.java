package com.okay.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @create 2020-07-28 14:29
 */
@Service
public class ZookeeperImpl {
    private static final String lockPath = "/lock/order";
    @Autowired
    private CuratorFramework zkClient;

    public void makeOrder(String product) {
        System.out.println("try do job to " + product);
        String path = lockPath + "/" + product;

        InterProcessMutex mutex = new InterProcessMutex(zkClient, path);
        try {
            if (mutex.acquire(5, TimeUnit.HOURS)) {
                System.out.println("doing"+Thread.currentThread().getName());
                Thread.sleep(5*1000);
                System.out.println("do job " + product + "done");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
