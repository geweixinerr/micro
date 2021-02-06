package micro.commons.exception;

/**
 * 归并计算异常
 * 
 * @author gewx
 **/
public final class ConcurrentMergerException extends RuntimeException {

	private static final long serialVersionUID = -3556984514037568221L;

	public ConcurrentMergerException() {
		super();
	}

	public ConcurrentMergerException(String msg) {
		super(msg);
	}
}
