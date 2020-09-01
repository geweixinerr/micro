package micro.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import micro.commons.annotation.valid.DateFormatValid;

@Target({ ElementType.FIELD })

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { DateFormatValid.class })
public @interface DateFormat {

	String value();

	String message() default "日期格式不匹配~";

	boolean required() default false;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
