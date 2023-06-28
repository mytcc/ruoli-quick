package ruoli.work.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 请求日志记录
 * </p>
 *
 * @author Ruoli
 * @since 2023-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_request_record")
@ApiModel(value="SysRequestRecord对象", description="请求日志记录")
public class SysRequestRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "请求URL")
    private String requestUrl;

    @ApiModelProperty(value = "请求参数")
    private String parameter;

    @ApiModelProperty(value = "响应值")
    private String response;

    @ApiModelProperty(value = "持续时长")
    private String duration;

    @ApiModelProperty(value = "请求时间")
    private LocalDateTime createTime;


}
