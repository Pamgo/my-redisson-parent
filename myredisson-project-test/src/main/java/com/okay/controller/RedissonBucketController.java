package com.okay.controller;

import com.okay.annotation.MyCacheable;
import com.okay.model.ApiResult;
import com.okay.test.ErrModel;
import com.okay.test.OrgModel;
import com.okay.test.TokenModel;
import com.okay.test.UserModel;
import org.apache.catalina.User;
import org.redisson.api.RBucket;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <h2><h2>
 *
 * @author okay
 * @create 2020-07-08 10:33
 */
@RestController
@RequestMapping("redisson/")
public class RedissonBucketController {

    @Autowired
    private RedissonClient redissonClient;


    @GetMapping("/get/{key}")
    public ApiResult get(@PathVariable String key) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        String value = bucket.get();
        long keyCount = redissonClient.getKeys().count();
        Map<String, Object> result = new HashMap<>();
        result.put("keyCount", keyCount);
        result.put("value", value);
        return new ApiResult(200,null,result);
    }

    @PostMapping("/put/{key}/{value}")
    @Cacheable(cacheNames = "mycache", key = "#key+'_'+#value")
    public ApiResult put(@PathVariable String key, @PathVariable String value) {
        redissonClient.getBucket(key).set(value, 5, TimeUnit.MINUTES);
        long keyCount = redissonClient.getKeys().count();
        Map<String, Object> result = new HashMap<>();
        result.put("keyCount", keyCount);
        return ApiResult.ok(result);
    }

    @PostMapping("/getTest")
    @Cacheable(cacheNames = "mycache", key = "#test")
    public ApiResult getTest(String test) {

        System.out.println("test");
        RMapCache<Object, Object> anymap =
                redissonClient.getMapCache("anymap");
        anymap.put("key1","key1-Value",10,TimeUnit.MINUTES);
        anymap.put("key2","key2-Value",10,TimeUnit.MINUTES, 10, TimeUnit.SECONDS);
        return ApiResult.ok(test);
    }

    @GetMapping("/getMyCacheable")
    @MyCacheable(cacheName = "mycache", key = "#key+'_'+#hkey", expire = 1000, timeUnit = TimeUnit.SECONDS)
    public ApiResult getMyCacheable(@RequestParam("key") String key, @RequestParam("hkey") String hkey) {
        System.out.println("key----->test");
        return ApiResult.ok(key);
    }

    @GetMapping("/err")
    @ResponseBody
    public ErrModel err(@RequestParam("appid") String appid,
                        @RequestParam("secret") String secret) {
        return new ErrModel(1000,"invalid token");
    }

    @GetMapping("/token")
    @ResponseBody
    public TokenModel token(@RequestParam("appid") String appid,
                            @RequestParam("secret") String secret) {
        System.out.println(appid);
        System.out.println(secret);
        return new TokenModel("2020-07-13 20:00:00","2c7cb9bbd4474d49915a2a0eafe0ff74a86c38d7a8824e948fbfa521bd7f8747a56948d88a4b485e9beedc4df1dca1402967c5a2da6f4879a6c12aa9ee2de90b");
    }

    @GetMapping("/org")
    @ResponseBody
    public List<OrgModel> org(@RequestParam("token") String token) {
        List<OrgModel> orgModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            orgModels.add(OrgModel.build(i));
        }
        System.out.println(token);
        return orgModels;
    }

    @GetMapping("/user")
    @ResponseBody
    public List<UserModel> user(@RequestParam("token") String token,
                           @RequestParam("orgid") String orgid) {
        List<UserModel> userModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userModels.add(UserModel.build(i));
        }
        System.out.println(token);
        System.out.println(orgid);
        return userModels;
    }

}
