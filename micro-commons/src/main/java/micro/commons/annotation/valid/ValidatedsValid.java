package micro.commons.annotation.valid;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import micro.commons.annotation.Validateds;
import micro.commons.util.ValidatorUtils;

/**
 * DTO校验注解验证器
 * 
 * @author gewx
 **/
public class ValidatedsValid implements ConstraintValidator<Validateds, Object> {

	private Validateds validated;

	@Override
	public void initialize(Validateds validated) {
		this.validated = validated;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean isValid(Object bean, ConstraintValidatorContext context) {
		if (validated.required() && bean == null) {
			return false;
		}

		if (bean != null) {
			ValidatorUtils.FieldBean fieldBean = null;
			if (Collection.class.isInstance(bean)) {
				fieldBean = ValidatorUtils.validator((Collection) bean, validated.ignoreMode(), validated.ignore());
			} else {
				fieldBean = ValidatorUtils.validator(bean, validated.ignoreMode(), validated.ignore());
			}
			if (fieldBean.isSuccess()) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(fieldBean.getMsg()).addConstraintViolation();
				return false;
			}
		}

		return true;
	}

}
