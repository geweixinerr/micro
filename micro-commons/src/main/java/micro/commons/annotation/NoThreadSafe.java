package micro.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 标记注解:非线程安全
 * 
 * @author gewx
 **/
@Target({ ElementType.TYPE })
public @interface NoThreadSafe {

}
