package ruoli.work.module.test.controller;


import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import ruoli.work.module.common.feign.ItmpFeignClient;
import ruoli.work.module.test.entity.EasyExcelUtils;
import ruoli.work.module.test.entity.SysUser;
import ruoli.work.module.test.service.impl.SysUserServiceImpl;
import ruoli.work.common.annotation.IgnoreAuthorize;
import ruoli.work.common.entity.CommonResponse;
import ruoli.work.common.entity.CommonException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Ruoli
 * @since 2022-10-15
 */
@Controller
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private SysUserServiceImpl userService;
    @Resource
    private ItmpFeignClient itmpFeignClient;

    @GetMapping("/get")
    @IgnoreAuthorize
    public SysUser hello(@RequestParam String userId){
        ruoli.work.common.entity.CommonException.returnIfNull(userId,"无法查询到人员信息");
        SysUser user=userService.getById("123");
        return user;
    }

    @GetMapping("/test")
    @IgnoreAuthorize
    public CommonResponse<SysUser> test(@RequestParam String userId){
        CommonResponse<SysUser> user=itmpFeignClient.getUser(userId);
        return user;
    }

    @GetMapping("/sql-execute")
    @IgnoreAuthorize
    public String sqlExecute() throws IOException {
        return "sql";
    }

    @PostMapping("/sql")
    @IgnoreAuthorize
    public List<Map<String,String>> getResult(HttpServletResponse response,@RequestParam(required = false) String sql,@RequestParam String sqlType) throws IOException {
        CommonException.returnIfNull(sql,"查询SQL不可为空");
        CommonException.returnIfTure(!sql.toLowerCase().startsWith("select"),"此操作仅限查询SQL");
        List<LinkedHashMap<String,String>> result= userService.getBaseMapper().selectCommonListBySQL(sql);
        EasyExcelUtils easyExcelUtils = new EasyExcelUtils();
        easyExcelUtils.exportToExcel(result, response);

        return null;
    }
}
