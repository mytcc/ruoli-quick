package ruoli.work.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import ruoli.work.common.entity.Constant;

@NoArgsConstructor
@Setter
@Getter
@Slf4j
public class CommonException  extends RuntimeException{

    private Integer errorCode;
    private String errorMsg;


    public CommonException(Integer errorCode,String errorMsg){
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
    }
    public CommonException(Integer errorCode,String errorMsg,Throwable ex){
        super(ex);
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
    }

    // 断言错误，如果值为空，则返回错误
    public static void returnIfNull(Object obj,String errorMsg){
        if(obj==null || (obj instanceof String && !StringUtils.hasText(obj.toString()))){
            throw new CommonException(404,errorMsg);
        }
    }
    public static void returnIfTure(boolean result,String errorMsg){
        if(result){
            throw new CommonException(400,errorMsg);
        }
    }
    public static void returnMesssage(String msg){
        log.error("{}",msg);
        throw new CommonException(Constant.HttpStatus.Code400.getCode(),msg);
    }
    public static void returnException(String msg,Throwable ex){
        log.error("{},{}",msg,ex);
        throw new CommonException(Constant.HttpStatus.Code500.getCode(),msg,ex);
    }

}
