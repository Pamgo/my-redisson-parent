package com.okay.aspect;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <h2><h2>
 *
 * @author pangy
 * @create 2020-07-28 15:01
 */
@Aspect
@Component
public class ClusterLockAdvice {
    @Autowired
    private CuratorFramework zkClient;

    @Pointcut("@annotation(clusterLock)")
    public void pointcut(ClusterLock clusterLock) {}

    @Around("pointcut(clusterLock)")
    public void around(ProceedingJoinPoint pjp, ClusterLock clusterLock) {

        try {
            Object arg = pjp.getArgs()[0];
            if (null == arg || !(arg instanceof String)){
                pjp.proceed();
                return;
            }
            System.out.println("尝试业务=》" + arg);
            String lockPath = clusterLock.LOCK_PATH();
            lockPath = lockPath +"/"+ String.valueOf(arg);
            InterProcessMutex mutex = new InterProcessMutex(zkClient, lockPath);
            try {
                if (mutex.acquire(10, TimeUnit.HOURS)) {
                    System.out.println(Thread.currentThread().getName()+"=>获得锁");
                    pjp.proceed(pjp.getArgs());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mutex.release();
                System.out.println(Thread.currentThread().getName()+"=>释放锁");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
