package com.ye.FirstBoot.common;



import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.lettuce.core.internal.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConditionalOnClass({JedisCluster.class})
public class RedisClusterConf {
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${redis.timeout}")
    private int timeout;
    @Value("${redis.minEvictableIdleTimeMillis}")
    private int maxIdle;
    @Value("${redis.numTestsPerEvictionRun}")
    private long maxWaitMillis;
    @Value("${redis.timeout}")
    private int commandTimeout;
    @Bean
    public JedisCluster getJedisCluster() {
        String[] cNodes = clusterNodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        // 分割出集群节点
        for (String node : cNodes) {
            String[] hp = node.split(":");
            nodes.add(new HostAndPort(hp[0],Integer.parseInt(hp[1])));
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 创建集群对象
//      JedisCluster jedisCluster = new JedisCluster(nodes,commandTimeout);
        return new JedisCluster(nodes,commandTimeout,jedisPoolConfig);
    }

   /* *//**
     * 设置数据存入 redis 的序列化方式
     * </br>redisTemplate 序列化默认使用的jdkSerializeable, 存储二进制字节码, 导致key会出现乱码，所以自定义
     * 序列化类
     *
     * @param redisConnectionFactory
     *//*
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
*/
}