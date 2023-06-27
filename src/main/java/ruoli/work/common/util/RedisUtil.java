package ruoli.work.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,String> template;

    public String get(final String key){
        return template.opsForValue().get(key);
    }
    public void set(final String key,String value){
        template.opsForValue().set(key,value);
    }
    public void set(final String key,String value,long seconds){
        template.opsForValue().set(key,value,seconds, TimeUnit.SECONDS);
    }
    public boolean delete(String key){
        return template.delete(key);
    }
    public boolean hasKey(String key){
        return template.hasKey(key);
    }
    public void leftPush(String key,String content){
        template.opsForList().leftPush(key,content);
    }
    public String rightPop(String key){
        return template.opsForList().rightPop(key);
    }
}
