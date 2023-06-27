package ruoli.work.module.test.mapper;

import ruoli.work.module.test.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Ruoli
 * @since 2022-10-15
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<LinkedHashMap<String,String>> selectCommonListBySQL(String sql);
}
