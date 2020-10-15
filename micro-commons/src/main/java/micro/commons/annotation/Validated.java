package micro.commons.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.apache.commons.lang3.StringUtils;

import micro.commons.annotation.valid.ValidatedValid;

/**
 * DTO校验注解
 * 
 * @author gewx
 * **/
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = { ValidatedValid.class })
public @interface Validated {
	
	String value() default StringUtils.EMPTY;

	String message() default "校验不通过~";

	boolean required() default false;

	/**
	 * 忽略模式: true-传递的忽略,false=未传递的忽略
	 * **/
	boolean ignoreMode() default true; 
	
	/**
	 * 忽略的数组
	 * **/
	String [] ignore() default {};
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
