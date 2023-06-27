package ruoli.work.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 通用响应对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    /**
     * 返回编码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 返回业务参数
     */
    private T data;
    public CommonResponse(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    private String traceId;

}
