package ruoli.work.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_event")
@ApiModel(value="SysEvent对象", description="系统事件表，记录系统及业务事件")
@NoArgsConstructor
public class SysEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "时间类型")
    private String eventType;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "业务ID")
    private String busId;

    @ApiModelProperty(value = "操作人编码")
    private String operationCode;

    @ApiModelProperty(value = "操作人姓名")
    private String operationName;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;

    public SysEvent(String eventType, String content, String busId, String operationCode, String operationName) {
        this.eventType = eventType;
        this.content = content;
        this.busId = busId;
        this.operationCode = operationCode;
        this.operationName = operationName;

    }
}