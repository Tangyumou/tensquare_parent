package com.tensquare.test;

import com.tensquare.user.UserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test(){
        redisTemplate.opsForValue().set("checkcode_18792652609","123456");
        String s = (String) redisTemplate.opsForValue().get("checkcode_18792652609");
        System.out.println(s);
    }
}
