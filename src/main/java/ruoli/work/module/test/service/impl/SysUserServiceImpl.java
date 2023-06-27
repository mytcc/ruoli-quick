package ruoli.work.module.test.service.impl;

import ruoli.work.module.test.service.ISysUserService;
import ruoli.work.module.test.entity.SysUser;
import ruoli.work.module.test.mapper.SysUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Ruoli
 * @since 2022-10-15
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
