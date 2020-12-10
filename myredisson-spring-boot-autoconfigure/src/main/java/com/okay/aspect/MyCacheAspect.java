package com.okay.aspect;

import com.okay.annotation.MyCacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * <h2>缓存切面<h2>
 * @author okay
 * @create 2020-07-10 09:56
 */
@Aspect
public class MyCacheAspect {
    private final Logger logger = LoggerFactory.getLogger(MyCacheAspect.class);

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(mycacheable)")
    public void pointcut(MyCacheable mycacheable) {}

    @Around("pointcut(mycacheable)")
    public Object around(ProceedingJoinPoint pjp, MyCacheable mycacheable) throws Throwable {
        logger.info("before==>{}", pjp.getSignature());
        Method method = getMethod(pjp);
        String prefixKey = getPrefixKey(pjp, mycacheable);
        // 获取key，支持spEL
        String suffixKey = getSuffixKey(mycacheable, method, pjp.getArgs());
        String key = prefixKey +":" + suffixKey;
        // 获取缓存数据
        RBucket<Object> bucket = redissonClient.getBucket(key);
        Object cacheResult = bucket.get();
        if (!StringUtils.isEmpty(cacheResult)) { // 命中缓存数据
            logger.info("命中缓存数据=>{}", key);
            return cacheResult;
        }
        cacheResult = pjp.proceed();
        if (!StringUtils.isEmpty(cacheResult)) {
            long expire = mycacheable.expire();
            TimeUnit timeUnit = mycacheable.timeUnit();
            bucket.set(cacheResult, expire, timeUnit);
        }
        logger.info("after==>{}", pjp.getSignature());
        return cacheResult;
    }

    private String getSuffixKey(MyCacheable mycacheable, Method method, Object[] args) {
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }

        StringBuilder parseKeyBuff = new StringBuilder();
        String[] keys = mycacheable.key();
        for (String key : keys) {
            if(StringUtils.isEmpty(key)){
                parseKeyBuff.append(":");
            }else {
                parseKeyBuff.append(":").append(parser.parseExpression(key).getValue(context, String.class));
            }
        }
        return parseKeyBuff.toString();
    }

    private String getPrefixKey(ProceedingJoinPoint pjp, MyCacheable mycacheable) {
        return mycacheable.cacheName() +":"+ pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
    }

    private Method getMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        Signature signature = pjp.getSignature();
        String methodName = signature.getName();
        MethodSignature methodSignature = (MethodSignature) signature;
        Class[] parameterTypes = methodSignature.getParameterTypes();
        Method method = pjp.getTarget().getClass().getMethod(methodName, parameterTypes);
        return method;
    }
}
