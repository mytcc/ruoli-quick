package ruoli.work.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ruoli.work.common.entity.SysEvent;

public interface ISysEventService extends IService<SysEvent> {

    void pushEvent(String eventType,String content,String busId,String operationCode,String operationName);
}
