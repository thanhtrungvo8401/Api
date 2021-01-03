package fusikun.com.api.exceptionHandlers;

public class Ex_MethodNotAllowedException extends RuntimeException {
	private static final long serialVersionUID = -403862117310201993L;

	public Ex_MethodNotAllowedException(String message) {
		super(message);
	}
}
