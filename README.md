# ITMP-QUICK 项目开发说明

[TOC]
## 一、模块划分
所有业务模块都需在bcs.mis.itmp 的module中新建模块文件夹，模块文件夹下 固定有包：controller、entity、mapper、service 用于存放模块基础文件，开发人员可自行建立其他需要的包。  
系统所有模块中有两个非常特殊的模块。  
1、公共服务模块，此模块服务于所有业务模块或者于业务功能没有任何数据系统级功能。  
2、系统核心模块，此模块定义了系统通用拦截器，切面自定义注解，核心配置 是系统运行的通用能力模块。  
以上两个模块比较特殊，独立抽取出来放置于bcs.mis.itmp之下，于module下的模块相分离。
## 二、代码生成
itmp-quick 工程支持IDEA插件自动生成 controller、entity、mapper、service 代码，插件名称：MybatisPlus-1.7.2.zip

下载地址：\\100.89.41.10\soft\工程效能开发团队\software\tools\MybatisPlus-1.7.2.zip

插件安装完成后可在IDEA Other 菜单中 配置数据库连接和生成代码，代码可直接生成到工程的指定文件夹中，无需再次移动，如下：
![配置代码生成](./src/main/resources/static/codeGenerator.png)

## 三、配置说明
目前itmp-quick 并没有接入 apollo配置中心，配置内容放置于配置文件中，固定配置（多环境相同配置）放置于公共配置文件application.yml文件中，环境不同配置值不同的配置项放在各个环境的配置文件中。

## 四、基础开发通用能力
### 1、方法返回值
itmp-quick使用统一的封装返回，如下：
```java
{
    "code": 200, //统一返回状态码，与HTTP Status Code 保持一致，常用的有 200:正常、401:认证失败、403:鉴权失败、500:服务器内部错误
    "message": "all operations completed.", //返回消息说明，用户在异常情况下 进行友好提示，正常返回时固定值为：all operations completed.
    "data": {  //所有业务数据封装为 data 属性
        "name": "zhangsan",
        "age": 20
    },
    "traceId": "" // 链路追踪号，可查询单次请求的日志信息
}
```
所有的接口都返回200 HTTP 状态码，真实的状态信息在具体返回值code字段中。
#### a、正常返回
直接返回业务数据即可，业务数据会封装在返回值的data属性中，如下：
```java
@GetMapping("/hello")
public Map<String,Object> hello(){
    Map<String,Object> map=new HashMap<>();
    map.put("name","zhangsan");
    map.put("age",20);
    return map; //直接返回业务对象
}
```
如果为非查询操作，返回值直接设置为 void 即可，返回值为void的方法，正常返回前端为：
```json
{
    "code": 200,
    "message": "all operations completed.",
    "data": null,
    "traceId": ""
}
```
前端通过 code=200 识别到此操作已经正常返回。

#### b、异常返回
```java
@PostMapping("/addTrainBr")
public String addTrainBr(@RequestBody ReleaseTrainBrParams releaseTrainBr) {
    
    // 需求编号不允许为空，可通过断言的形式直接进行为空判断，不用显式的进行if判断，可节省开发时间，简化代码。
    // CommonException.returnIfTure 可在参数为true时 返回错误，此处可以扩展更多形式。
    CommonException.returnIfNull(releaseTrainBr.getSerialNumber(),"需求编号不能为空");
    // 插入记录
    releaseTrainDataService.addTrainBr(releaseTrainBr);
    return "OK"; //此处返回不规范
}
```
异常返回数据中包含了真实的错误堆栈，可用于错误排查，此错误会通过接口返回给前端，但前端并不会进行用户提示，如下：
```json
{
    "code": 404,
    "message": "需求编号不能为空",
    "data": "bcs.mis.itmp.module.common.entity.CommonException\r\n\tat bcs.mis.itmp.module.common.entity.CommonException.returnIfNull(CommonException.java:36)\r\n\tat bcs.mis.itmp.module.test.controller.SysUserController.get(SysUserController.java:39)\r\n\t",
    "traceId": ""
}
```
#### c、不使用统一返回
若业务需要 不需要进行统一封装返回，可在类或者方法上添加注解 @IgnoreResponse 即可，如下：
```java
@GetMapping("/hello")
@IgnoreResponse
public Map<String,String> hello(){
    log.info("abc");
    Map<String,String> map=new HashMap<>();
    map.put("name","ruoli");
    log.info("=============="+TraceContext.traceId());
    return map;
}
```
返回值如下：
```json
{
    "name": "ruoli"
}
```
### 2、权限拦截
itmp-quick 认证方式与 itmp-data-service 一致，使用oauth方式，head 中 传递如下：
```
Authorization: Bearer 4QhrOFRQAxA8E9t2L7mnDA
```
系统默认开启权限拦截，即 除静态资源外 所有业务请求都需要进行权限认证，若接口不需要进行权限认证，则在类或者方法上添加 @IgnoreAuthorize 注解即可，如下：  
类上使用此注解，如下
```java
@GetMapping("/test")
@IgnoreAuthorize  //忽略鉴权
public List<Map<String,String>> test(@RequestParam String userId){
    List<Map<String,String>> user=itmpClient.getUser();
    return user;
}
```
方法上使用此注解，如下
```java
@IgnoreAuthorize
    public Map<String,String> hello(HttpServletRequest request){
        SysUser user=(SysUser)request.getAttribute(Constant.Default.UserInfo.name());
        log.info("abc");
        Map<String,String> map=new HashMap<>();
        map.put("name","ruoli");
        log.info("=============="+TraceContext.traceId());
        return map;
    }
```
若请求内容不属于静态内容，且没有源码配置，则可以在配置文件 application.yml 中配置白名单，白名单上的url 不进行鉴权操作，如下：
```yaml
permission:
  white-list:
    uri: swagger-resources //白名单配置，多条以英文逗号分隔
```
### 3、跨服务调用
itmp-quick 使用了open-feign 进行服务调用，为了便于查看和统一管理，每个外部服务单独一个类进行处理，可参考：HrmClient.java、ItmpClient，如下：
```java
/**
 * <h2>通过 OpenFeign 访问 itmp 鉴权</h2>
 * */
@RequestMapping(value = "/api/common/resolveToken",
        method = RequestMethod.GET,
        consumes = "application/json",
        produces = "application/json")
//@OpenRequestRecord(type = Constant.RecordType.none) 不开启请求信息记录，默认值，和不写此注解是相同效果
//@OpenRequestRecord(type = Constant.RecordType.db) //开启请求信息输出到数据库，对应数据库表 sys_request_recode
@OpenRequestRecord(type = Constant.RecordType.file) //开启请求信息输出到日志文件
    String resolveToken(@RequestParam String userToken);
```
quick已支持记录跨服务请求日志信息输出至文件或数据库，可通过 @OpenRequestRecord 注解配置，详细见上述代码示例。  
开启日志文件记录后，日志文件中会输出：  
`
start to request url:http://162.16.1.113:8088/api/common/resolveToken , params is ["3QI140EyAFgB4HHS01ubKm"] , response is "\"13482\"" , take: 31 ms
`


### 4、获取当前登录用户信息
在Controller中可以获取到当前登录人的信息，但如果此类或此方法使用了 忽略权限注解 @IgnoreAuthorize，则获取不到用户信息
```java
    @GetMapping("/hello")
    @IgnoreResponse
    public Map<String,String> hello(HttpServletRequest request){
        //获取当前登录用户信息
        SysUser user=(SysUser)request.getAttribute(Constant.Default.UserInfo.name());
        log.info("abc");
        Map<String,String> map=new HashMap<>();
        map.put("name","ruoli");
        log.info("=============="+TraceContext.traceId());
        return map;
    }
```

### 5、常量定义
项目常量统一定义在常量类中，类名为：Constant.java,基本内容如下：
```java
public interface Constant {
    // 常量 需要按照业务进行分组，无法归属具体业务的  或者属于公共模块的 可放置在Default 分组中
    enum Default{
        UserInfo
    }

    // 业务分组1
    enum CombackType{
        Custom, //普通撤回，下一步为单用户审批，撤回到当前节点，当前节点可以为普通单用户审批，也可以是会签
        Muilt, //多节点撤回，下一步为 并行网关、排他网关、包容网关
    }
}
```
说明：
常量需要按照业务类型进行分组，无法归属具体业务的  或者属于公共模块的 可放置在Default 分组中。

使用方式1（只使用常量值）如下：
```java
@GetMapping("/hello")
    @IgnoreResponse
    public Map<String,String> hello(HttpServletRequest request){
        // 使用常量 UserInfo
        SysUser user=(SysUser)request.getAttribute(Constant.Default.UserInfo.name());
        log.info("abc");
        Map<String,String> map=new HashMap<>();
        map.put("name","ruoli");
        log.info("=============="+ TraceContext.traceId());
        return map;
    }
```
使用方式2（返回常量类型、根据常量类型进行判断）如下：
```java
@Override
public Constant.CombackType comebackType() {
  return Constant.CombackType.Custom;
}

public void executeOANotification(Constant.CombackType combackType){
   //转发特有逻辑
   if(combackType.equals(Constant.CombackType.Dispatch)){
    //xxx
   }else if(combackType.equals(Constant.CombackType.Delegate)){
    //xxx
   }
}
```

### 6、数据库表名、字段名命名规则
数据库数据库表名、字段名命名统一使用小写蛇形命名法，各个单词之间使用下划线连接，例如：task_id，表名前面为模块名称，后面为业务名称，中间使用下划线分割。
由于历史原因，若字段使用驼峰命名法，通过myBatis生成实体类时会出现找不到字段错误，就是由于命名不规范导致的，通过注解指定列名即可，如下：
```java
// 数据库字段命名不规范，导致需要按如下方式 手动指定字段名称
@TableField("createDate") //指定数据库字段为 createDate
private LocalDateTime createDate;
@TableField("createUser") //指定数据库字段为 createUser
private String createUser; 
@TableField("modifyDate") //指定数据库字段为 modifyDate
private LocalDateTime modifyDate;
@TableField("modifyUser") //指定数据库字段为 modifyUser
private String modifyUser;
```

### 7、日期类型处理
#### a、日期类型使用
数据库日期类型使用date,date 类型映射为Java程序中为 LocalDate 类型，  
数据库时间类型使用datetime，datetime  类型映射为Java程序中为 LocalDateTime 类型。
#### b、日期类型工具类
统一使用 bcs.mis.itmp.module.common.util.DateUtil.java 进行日期判断和处理

#### c、日期前后端互传
Quick工程中已对LocalDate、LocalDateTime 做了全局配置，  
后端可直接使用LocalDate类型接收前端传入 2022-12-16 格式的字符串数据，  
同理 后端也可直接使用LocalDateTime类型接收前端传入 2022-12-16 10:25:32 格式的字符串数据，  
反向传递 将LocalDate、LocalDateTime 类型返回前端时，也会自动转换为 2022-12-16、2022-12-16 10:25:32 格式的字符串。


### 8、对象/集合/Map 与 Json互转
springboot默认使用jackson进行json转换操作，常用的转换实例可参考如下：
```java
ObjectMapper mapper = new ObjectMapper();

// 将 Map 转换成 Json字符串
String string = mapper.writeValueAsString(map);

// 将 Json字符串 转换成 Map对象
Map<String,Object> readValue = mapper.readValue(str, Map.class);

// 将 对象 转换成 Json字符串
Person p = new Person("张三",23,"北京");
String string = mapper.writeValueAsString(p);

// 将 Json字符串 转换为 对象
Person readValue = mapper.readValue(str, Person.class);

// 将 数组 转换成 Json字符串
Person p1 = new Person("张三",23,"北京");
Person p2 = new Person("李四",24,"北京");
Person p3 = new Person("王五",25,"北京");
Person[] persons = {p1,p2,p3};
String string = mapper.writeValueAsString(persons);

// 将 字符串 转换成 数组
String str = "[{\"addr\":\"北京\",\"age\":23,\"name\":\"张三\"},{\"addr\":\"北京\",\"age\":24,\"name\":\"李四\"},{\"addr\":\"北京\",\"age\":25,\"name\":\"王五\"}]";
Person[] persons = mapper.readValue(str,new TypeReference<Person[]>(){});

// 将 集合 转换成 Json 字符串
List<Person> list = new ArrayList<Person>();
Person p1 = new Person("张三",23,"北京");
Person p2 = new Person("李四",24,"北京");
Person p3 = new Person("王五",25,"北京");
list.add(p1);
list.add(p2);
list.add(p3);
String string = mapper.writeValueAsString(list);

//将 Json字符串 转换成 集合
String str = "[{\"addr\":\"北京\",\"age\":23,\"name\":\"张三\"},{\"addr\":\"北京\",\"age\":24,\"name\":\"李四\"},{\"addr\":\"北京\",\"age\":25,\"name\":\"王五\"}]";
List<Person> persons = mapper.readValue(str,new TypeReference<List<Person>>(){});
```
也可直接使用工具类处理：  
bcs.mis.itmp.module.common.util.JsonUtil  
系统中也集成了FastJson，可直接使用。

### 9、数据字典获取
系统支持数据字典获取，对应数据库表 common_dictionary，实例如下：
```java
CommonDictionary dictBusinessLevel = dictService.getDictionaryByCode(Constant.Dictionary.business_level.name());
// 获取字典全部 name->value，并转换为Map
Map<String, String> map = dictBusinessLevel.transToMap();
// 根据单个 name 获取 val
String val=dictBusinessLevel.getVal("name");
// 根据单个 val 获取 name
String name=dictBusinessLevel.getName("val");
// 根据 多个 vals 获取 name 数组
String[] names=dictBusinessLevel.getNames("vals");
```
### 10、文件上传、下载
知脉使用容器部署，由于容器本身的无状态属性，不可在容器中保存文件等，目前采用对象存储的方式保存附件，也是现在最主流前言的方式；  
Qucik已集成华为云OBS对象存储服务，对象保存在对象存储服务中；
文件操作类可参考 FileService.java 服务类，已提供常见的上传、下载、读取模板等方法；
可通过obs-browser-plus工具查看对象存储中的文件信息，下载地址：  
`
\\100.89.41.10\soft\工程效能开发团队\software\tools\obs-browser-plus Setup 3.22.7.exe
`

安装此软件后需要填写授权信息以访问对象存储内容，SIT环境配置如下：  
UAT环境配置与SIT环境一致，桶名换为 itmp-uat即可。

```java
账号名:随意不限制
服务器地址:100.115.10.252  
Access Key ID:E9OK3TXLD3XSVR60GUZZ
Secret Access Key:hCMFmwTx4dmA7FdmekILOnl4BJmKyvf1Rx1xgkd5
访问路径:obs://itmp-sit
```
![查看对象存储服务中的文件](./src/main/resources/static/obsLogin.png)

### 11、Redis操作
针对查询场景高频热点数据查询应使用缓存以加快查询速度，目前系统中已集成了Redis,使用的是Springboot RedisTemplate，操作简单;  
同时也提供了Reids操作工具类 RedisUtil.java,推荐使用工具类操作。  
当然Redis最主要的场景是缓存，但是分布式锁、计数器、限流、消息队列、排行榜 等都是其使用场景。




## 五、Swagger集成
itmp-quick 已经集成了 swagger，可通过如下地址访问：
```
http://localhost:8085/quick/doc.html
帐号：itmp
密码：dev@1243
```


## 六、链路追踪
itmp-quick可使用Skywalking进行链路追踪，而且默认集成了skywalking日志采集。  
SIT环境Skywalking地址：  
http://100.89.41.120:8090/  
开发环境接入Skywalking只需在启动配置 vm options 中追加如下参数即可：  
`-javaagent:D:\software\skywalking-agent\skywalking-agent.jar -Dskywalking.agent.service_name=quick-sit -Dskywalking.collector.backend_service=100.89.41.120:11800`
### 1、链路信息查看
通过请求链路信息可以查看本次请求的详细信息，对性能优化，错误分析等提供重要的分析依据，如下图：
![img.png](src/main/resources/static/traceview.png)

### 2、日志采集查看
可在Skywalking日志模块查看所有请求的日志信息，当然如果日志非常多，也可通过追踪号快速筛选某一次请求的日志信息，  
项目已在项目返回值中自动添加追踪ID号 traceId 显示，如下：
```java
{
    "code": 200,
    "message": "all operations completed.",
    "data": {
        "records": [],
        "total": 0,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "searchCount": true,
        "countId": null,
        "maxLimit": null,
        "pages": 0
    },
    "traceId": "c6b0403f8b2048feaf1c0a8dde57eca5.374.16722877643740001"
}
```
通过此追踪号，在日志页面即可搜索本次请求的所有日志：
![img.png](src/main/resources/static/skywaklinglogshow.png)
