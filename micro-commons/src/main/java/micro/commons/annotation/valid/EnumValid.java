package micro.commons.annotation.valid;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import micro.commons.annotation.Enums;

/**
 * 枚举验证器
 * 
 * @author gewx
 * 
 **/
public class EnumValid implements ConstraintValidator<Enums, String> {

	private Enums enums;

	@Override
	public void initialize(Enums enums) {
		this.enums = enums;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (enums.required() && isBlank(value)) {
			return false;
		}

		if (isNotBlank(value)) {
			return Stream.of(enums.enums()).anyMatch(val -> val.equals(value));
		}
		return true;
	}
}
