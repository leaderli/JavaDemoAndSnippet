package com.leaderli.demo.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class RedisTest {

    @Test
    public void test() {
        RedisClusterClient redisClusterClient = RedisClusterClient.create("redis://centos7:7000");
        StatefulRedisClusterConnection<String, String> connect = redisClusterClient.connect();
        RedisAdvancedClusterCommands<String, String> commands = connect.sync();
        StringBuilder luas = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            String format ="redis.call('zincrby','{hf}"+i+"',1,'%s'); ";
            format = String.format(format,"k_"+i+"");
            luas.append(format);

        }
        System.out.println("luas = " + luas);
        commands.eval(luas.toString(), ScriptOutputType.MULTI,"");
    }

    @Test
    public void test2() {
        RedisCommands<String, String> commands = RedisClient.create("redis://leaderli:7000").connect().sync();
        String hello = commands.get("hello");
        System.out.println("hello = " + hello);
        commands.set("fuck", "you");
        commands.eval("redis.call('set','hello','w2');", ScriptOutputType.BOOLEAN   ,"");
        hello = commands.get("hello");
        System.out.println("hello = " + hello);

    }

    @Test
    public void test3(){
        Jedis jedis = new Jedis("leaderli",7000);
        System.out.println(jedis.get("hello"));
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("leaderli", 7000));
//        nodes.add(new HostAndPort("10.211.55.5", 7001));
//        nodes.add(new HostAndPort("10.211.55.5", 7002));
//        nodes.add(new HostAndPort("centos", 7001));
//        nodes.add(new HostAndPort("centos", 7002));
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(10000);
//        config.setMaxIdle(500);

        JedisCluster cluster = new JedisCluster(nodes);
        cluster.get("hello");

    }
    @Test
    public void test4() throws UnsupportedEncodingException {

        System.out.println(URLEncoder.encode("中文", StandardCharsets.UTF_8.name()));
        System.out.println(URLDecoder.decode("%E4%B8%AD%E6%96%87", StandardCharsets.UTF_8.name()));

    }
}
