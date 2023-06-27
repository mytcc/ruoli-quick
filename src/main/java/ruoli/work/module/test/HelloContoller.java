package ruoli.work.module.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class HelloContoller {

    @GetMapping("/hello")
    public Map<String,String> hello(){
        log.info("abc");
        Map<String,String> map=new HashMap<>();
        map.put("name","ruoli");
        log.info("=============="+TraceContext.traceId());
        int i=0;
        int j=1/i;

        return map;
    }
}
