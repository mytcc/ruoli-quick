package ruoli.work.core.annotation;

import ruoli.work.common.entity.Constant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestRecord {
    Constant.RecordType type() default Constant.RecordType.file;
}
