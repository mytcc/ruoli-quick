package ruoli.work.core.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import ruoli.work.common.entity.Constant;
import ruoli.work.common.service.ISysRequestRecordService;
import ruoli.work.common.util.DateUtil;
import ruoli.work.core.annotation.RequestRecord;
import ruoli.work.core.entity.CommonException;

@Aspect
@Component
@Slf4j
public class OpenFeignAspect {
    @Pointcut("@annotation(ruoli.work.core.annotation.OpenRequestRecord)")
    public void advice(){}

    @Autowired
    private Environment environment;
    @Autowired
    private ISysRequestRecordService recordService;

    @Around(value = "advice()")
    public Object around(ProceedingJoinPoint joinPoint){
        try {
            StopWatch timeWatch=new StopWatch();
            timeWatch.start("openfeign around");
            Object result = joinPoint.proceed(joinPoint.getArgs());
            try {
                timeWatch.stop();
                FeignClient client = ((MethodSignature) joinPoint.getSignature()).getMethod().getDeclaringClass().getAnnotation(FeignClient.class);
                RequestMapping mapping = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RequestMapping.class);
                MethodInvocationProceedingJoinPoint process = ((MethodInvocationProceedingJoinPoint) joinPoint);
                String url = environment.resolvePlaceholders(client.url()) + mapping.value()[0];
                String args = JSONObject.toJSONString(process.getArgs());
                String resultJson = JSONObject.toJSONString(result);
                String duration = DateUtil.convertToDuration(timeWatch.getTotalTimeMillis());
                // 判断是否需要记录日志
                RequestRecord openRecord = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RequestRecord.class);
                if (openRecord != null && openRecord.type().equals(Constant.RecordType.file)) {
                    // 日志文件记录
                    log.info("start to request url:{} , params is {} , response is {} , take: {}", url, args, resultJson, duration);
                } else if (openRecord != null && openRecord.type().equals(Constant.RecordType.db)) {
                    //todo 数据库记录
                    recordService.saveRecord(url,args,resultJson,duration);
                }
            }catch (Exception ex){
                log.error("openfeign around occurred exception : {}",ex);
            }
            return result;
        }catch (Throwable ex){
            CommonException.returnException("OpenFeign切面发生异常",ex);
        }
        return null;
    }
}

