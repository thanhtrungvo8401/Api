package fusikun.com.api.exceptionHandlers;

public class Ex_OverRangeException extends RuntimeException {

	private static final long serialVersionUID = -7069394470084847358L;

	public Ex_OverRangeException(String message) {
		super(message);
	}

}
