package ruoli.work.core.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PermissionInterceptor implements HandlerInterceptor {

    //controller层之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//
//            // 判断方法是否有 忽略鉴权注解
//            IgnoreAuthorize ignoreAuthorize =
//                    handlerMethod.getMethod().getAnnotation(IgnoreAuthorize.class);
//            if (ignoreAuthorize!=null) {
//                return true;
//            }
//
//            //从请求头中获取所需参数
//            String token = request.getHeader("Authorization");
//            System.out.println("打印Token = " + token);
//            boolean isHavePermission = (token != null);
//            //返回 true，放行  false，拦截
//            if (isHavePermission) {
//                System.out.println("权限检查通过::操作放行");
//                return true;
//            } else {
//                //设置response，手动返回response响应
//                response.setContentType("application/json;charset=utf-8");
//                CommonResponse commonResponse=new CommonResponse(401,"认证失败");
//                ObjectMapper mapper=new ObjectMapper();
//                String json=mapper.writeValueAsString(commonResponse);
//                response.getWriter().print(json);
//
//                System.out.println("拦截不通过");
//                //设置response，手动返回response响应
//                return false;
//            }
//
//        }
        return true;

    }

}

