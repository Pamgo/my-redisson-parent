package com.okay.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <h2><h2>
 *
 * @author okay
 * @create 2020-07-20 11:41
 */
public class DbUtil {
    /**
     * 模拟从db中获取邮件配置信息
     *
     * @return
     */
    public static Map<String, Object> getMailInfoFromDb() {
        Map<String, Object> result = new HashMap<>();
        result.put("mail.username", UUID.randomUUID().toString());
        return result;
    }
}
