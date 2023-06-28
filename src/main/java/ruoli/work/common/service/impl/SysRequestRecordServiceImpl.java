package ruoli.work.common.service.impl;

import org.springframework.scheduling.annotation.Async;
import ruoli.work.common.entity.SysRequestRecord;
import ruoli.work.common.mapper.SysRequestRecordMapper;
import ruoli.work.common.service.ISysRequestRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 请求日志记录 服务实现类
 * </p>
 *
 * @author Ruoli
 * @since 2023-06-28
 */
@Service
public class SysRequestRecordServiceImpl extends ServiceImpl<SysRequestRecordMapper, SysRequestRecord> implements ISysRequestRecordService {

    @Override
    @Async
    public void saveRecord(String url, String param, String response, String duration) {
        SysRequestRecord record=new SysRequestRecord();
        record.setRequestUrl(url);
        record.setParameter(param);
        record.setResponse(response);
        record.setDuration(duration);
        record.setCreateTime(LocalDateTime.now());
        this.save(record);
    }
}
