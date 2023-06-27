package ruoli.work.common.consumer;

import org.springframework.scheduling.annotation.Async;

public interface IEvent {
    @Async
    void doConsume();
}
