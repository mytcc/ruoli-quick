package ruoli.work.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@NoArgsConstructor
@Setter
@Getter
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

}
