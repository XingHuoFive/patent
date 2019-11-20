/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: 程志祥[cheng_zx@suixingpay.com]
 * @date: 2019/08/06 14:39
 * @Copyright: ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.sxp.patMag.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @description: 自定义redisTemplate
 * @author: 程志祥[cheng_zx@suixingpay.com]
 * @date: 2019/08/06 14:39
 * @version: V1.0
 */
@Configuration
public class RedisFreedomTemplate {

    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<String, Object> initRedisFreeTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redis = new RedisTemplate<>();
        redis.setConnectionFactory(connectionFactory);

        //定义key-value的序列化方式:jackson2
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        redis.setKeySerializer(jackson2JsonRedisSerializer);
        redis.setValueSerializer(jackson2JsonRedisSerializer);
        redis.afterPropertiesSet();

        return redis;
    }
}
