package micro.commons.annotation.valid;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections4.CollectionUtils;

import micro.commons.annotation.ListNotNull;

/**
 * 集合注解验证器
 * 
 * @author gewx
 * 
 **/
public class ListNotNullValid implements ConstraintValidator<ListNotNull, List<String>> {

	@SuppressWarnings("unused")
	private ListNotNull list;

	@Override
	public void initialize(ListNotNull list) {
		this.list = list;
	}

	@Override
	public boolean isValid(List<String> value, ConstraintValidatorContext context) {
		return CollectionUtils.isNotEmpty(value);
	}
}
