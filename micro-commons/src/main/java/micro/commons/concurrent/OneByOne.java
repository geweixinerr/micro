package micro.commons.concurrent;

/**
 * 函数式接口,并发业务逻辑处理
 * 
 * @author gewx 
 **/
@FunctionalInterface
public interface OneByOne<T> {

	T invoke();
}
