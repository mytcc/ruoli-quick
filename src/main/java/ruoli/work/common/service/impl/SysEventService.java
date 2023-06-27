package ruoli.work.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import ruoli.work.common.entity.Constant;
import ruoli.work.common.entity.SysEvent;
import ruoli.work.common.mapper.SysEventMapper;
import ruoli.work.common.service.ISysEventService;
import ruoli.work.common.util.RedisUtil;

public class SysEventService extends ServiceImpl<SysEventMapper, SysEvent> implements ISysEventService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void pushEvent(String eventType, String content, String busId, String operationCode, String operationName) {
        SysEvent event=new SysEvent(eventType,content,busId,operationCode,operationName);
        String json= JSONObject.toJSONString(event);
        redisUtil.leftPush(Constant.Default.RedisMqKey_Event.name(),json);
    }
}
