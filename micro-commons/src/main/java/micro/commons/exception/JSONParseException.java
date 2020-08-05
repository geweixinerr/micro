package micro.commons.exception;

/**
 * JSON解析自定义异常
 * 
 * @author gewx
 **/
public class JSONParseException extends RuntimeException {

	private static final long serialVersionUID = 5857040407766692603L;

	public JSONParseException() {
		super();
	}

	public JSONParseException(String msg) {
		super(msg);
	}
	
	public JSONParseException(Throwable cause) {
		super(cause);
	}
}
