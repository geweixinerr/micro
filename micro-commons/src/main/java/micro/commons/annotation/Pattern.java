package micro.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import micro.commons.annotation.valid.PatternValid;

/**
 * 正则校验注解
 * 
 * @author gewx
 **/
@Target({ ElementType.FIELD,ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { PatternValid.class })
public @interface Pattern {

	String regexp();

	String message() default "正则不匹配~";

	boolean required() default false;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
