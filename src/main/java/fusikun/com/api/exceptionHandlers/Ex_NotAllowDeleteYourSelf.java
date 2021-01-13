package fusikun.com.api.exceptionHandlers;

public class Ex_NotAllowDeleteYourSelf extends RuntimeException {
	private static final long serialVersionUID = 1389254616251612015L;

	public Ex_NotAllowDeleteYourSelf(String message) {
		super(message);
	}
}
