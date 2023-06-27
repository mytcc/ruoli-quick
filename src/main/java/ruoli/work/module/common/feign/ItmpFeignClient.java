package ruoli.work.module.common.feign;


import com.sun.java.swing.plaf.windows.WindowsBorders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ruoli.work.module.test.entity.SysUser;

/**
 * <h1>与 Authority 服务通信的 Feign Client 接口定义</h1>
 * */
@FeignClient(value = "itmp")
public interface ItmpFeignClient{

    /**
     * <h2>通过 OpenFeign 访问 Authority 获取 Token</h2>
     * */
    @RequestMapping(value = "/itmp/user/get",
                    method = RequestMethod.GET,
                    consumes = "application/json",
                    produces = "application/json")
    ruoli.work.common.entity.CommonResponse<SysUser> getUser(@RequestParam String userId);
}
