package ruoli.work.common.service;

import ruoli.work.common.entity.SysRequestRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 请求日志记录 服务类
 * </p>
 *
 * @author Ruoli
 * @since 2023-06-28
 */
public interface ISysRequestRecordService extends IService<SysRequestRecord> {
    void saveRecord(String url,String param,String response,String duration);
}
