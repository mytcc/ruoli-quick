package ruoli.work.common.consumer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ruoli.work.common.entity.Constant;
import ruoli.work.common.entity.SysEvent;
import ruoli.work.common.util.RedisUtil;
import ruoli.work.common.util.StringUtils;

import java.time.LocalDateTime;

@Component
@Slf4j
public class EventConsumer implements IEvent{
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void doConsume() {
        log.info("EventConsumer is ready");
        while(true){
            String content=redisUtil.rightPop(Constant.Default.RedisMqKey_Event.name());
            if(StringUtils.isNotBlank(content)){
                SysEvent event=JSONObject.parseObject(content, SysEvent.class);
                event.setCreateTime(LocalDateTime.now());
            }
        }

    }
}
