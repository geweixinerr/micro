package micro.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.apache.commons.lang3.StringUtils;

import micro.commons.annotation.valid.ListNotNullValid;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ListNotNullValid.class })

/**
 * 验证集合是否为空
 * 
 * @author gewx
 *
 **/
public @interface ListNotNull {

	String value() default StringUtils.EMPTY;

	String message() default "集合不能为空~";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
}