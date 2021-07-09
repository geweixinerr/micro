package micro.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import micro.commons.annotation.valid.EnumValid;

/**
 * 枚举校验注解
 * 
 * @author gewx
 **/
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { EnumValid.class })
public @interface Enums {

	String[] enums();

	String message() default "枚举不匹配~";

	boolean required() default false;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
