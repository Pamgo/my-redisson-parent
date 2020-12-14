# my-redisson-parent
#### 1、myredisson 自定义redisson缓存 @MyCacheable使用
```java
    @GetMapping("/getMyCacheable")
    @MyCacheable(cacheName = "mycache", key = "#key+'_'+#hkey", expire = 1000, timeUnit = TimeUnit.SECONDS)
    public ApiResult getMyCacheable(@RequestParam("key") String key, @RequestParam("hkey") String hkey) {
        System.out.println("key----->test");
        return ApiResult.ok(key);
    }
```

#### 2、myZookeeper-project-test分布式锁简单应用
