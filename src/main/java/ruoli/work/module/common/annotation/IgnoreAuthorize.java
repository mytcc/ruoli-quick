package ruoli.work.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>忽略统一响应注解定义</h1>
 * */

@Target({ElementType.TYPE, ElementType.METHOD}) // 注解可以标记在类 或者 方法上。
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreAuthorize {
}
