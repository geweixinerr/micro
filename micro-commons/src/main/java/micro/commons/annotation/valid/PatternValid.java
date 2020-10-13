package micro.commons.annotation.valid;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections4.map.LazyMap;
import org.apache.commons.lang3.StringUtils;

import micro.commons.annotation.Pattern;

/**
 * 正则验证器
 * 
 * @author gewx
 * 
 **/
public class PatternValid implements ConstraintValidator<Pattern, String> {

	private static final Map<String, java.util.regex.Pattern> lazyPattern = new ConcurrentHashMap<>(32);

	private Pattern pattern;

	@Override
	public void initialize(Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (pattern.required() && StringUtils.isBlank(value)) {
			return false;
		}

		if (StringUtils.isNotBlank(value)) {
			java.util.regex.Pattern regex = LazyMap.lazyMap(lazyPattern, () -> {
				return java.util.regex.Pattern.compile(pattern.regexp());
			}).get(pattern.regexp());

			return regex.matcher(pattern.regexp()).matches();
		}
		return true;
	}
}
