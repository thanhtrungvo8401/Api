package fusikun.com.api.exceptionHandlers;

public class Ob_FieldError {
	private String field;
	private String code;

	public Ob_FieldError() {
	}

	public Ob_FieldError(String field, String code) {
		super();
		this.field = field;
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
