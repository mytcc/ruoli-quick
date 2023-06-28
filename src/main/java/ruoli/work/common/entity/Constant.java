package ruoli.work.common.entity;

import lombok.Getter;

public interface Constant {

    enum Default{
        UserInfo, // 用户信息KEY
        IgnoreAuthorize, // 忽略全局权限校验KEY
        RedisMqKey_Event // redis消费事件KEY
    }

    enum RecordType{
        file,  // 记录日志类型-文件记录
        db // 记录日志类型-数据库记录
    }
    @Getter
    enum HttpStatus{
        Code200(200,"All operation completed"),
        Code400(400,"Bad Request"),
        Code401(401,"Unauthorized"),
        Code403(403,"Forbidden"),
        Code500(500,"Internal Server Error");


        private int code;
        private String describe;
        HttpStatus(int code,String describe){
            this.code=code;
            this.describe=describe;
        }
    }




}
