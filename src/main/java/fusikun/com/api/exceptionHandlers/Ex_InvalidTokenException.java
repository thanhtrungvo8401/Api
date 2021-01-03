package fusikun.com.api.exceptionHandlers;

public class Ex_InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = -6817320745898546515L;

	public Ex_InvalidTokenException(String message) {
		super(message);
	}
}
