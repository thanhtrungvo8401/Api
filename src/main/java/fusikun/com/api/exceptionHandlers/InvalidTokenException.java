package fusikun.com.api.exceptionHandlers;

public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = -6817320745898546515L;

	public InvalidTokenException(String message) {
		super(message);
	}
}
