package ruoli.work.common.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 不能在 @PostConstruct 方法中调用本类的异步方法
 */
@Component
public class ConsumerRedis {
    @Autowired
    private List<IEvent> events;
    @PostConstruct
    public void consume(){
        if(events!=null && events.size()>0){
            for (IEvent event : events) {
                event.doConsume();
            }
        }
    }
}
