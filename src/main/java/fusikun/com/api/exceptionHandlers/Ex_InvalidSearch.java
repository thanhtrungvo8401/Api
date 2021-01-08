package fusikun.com.api.exceptionHandlers;

public class Ex_InvalidSearch extends RuntimeException {

	private static final long serialVersionUID = 7238248815357267162L;

	public Ex_InvalidSearch(String message) {
		super(message);
	}

}
