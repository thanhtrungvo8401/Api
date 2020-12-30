package fusikun.com.api.exceptionHandlers;

public class MethodNotAllowedException extends RuntimeException {
	private static final long serialVersionUID = -403862117310201993L;

	public MethodNotAllowedException(String message) {
		super(message);
	}
}
