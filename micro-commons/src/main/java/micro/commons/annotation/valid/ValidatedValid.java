package micro.commons.annotation.valid;

import java.io.Serializable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import micro.commons.annotation.Validated;
import micro.commons.util.ValidatorUtils;

/**
 * DTO校验注解验证器
 * 
 * @author gewx
 **/
public class ValidatedValid implements ConstraintValidator<Validated, Serializable> {

	private Validated validated;

	@Override
	public void initialize(Validated validated) {
		this.validated = validated;
	}

	@Override
	public boolean isValid(Serializable bean, ConstraintValidatorContext context) {
		if (validated.required() && bean == null) {
			return false;
		}

		if (bean != null) {
			ValidatorUtils.FieldBean fieldBean = ValidatorUtils.validator(bean);
			if (fieldBean.isSuccess()) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(fieldBean.getMsg()).addConstraintViolation();
				return false;
			}
		}

		return true;
	}

}
