package fusikun.com.api.exceptionHandlers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Ob_ExceptionResponse {
	private Date timestamp;
	private String message;
	private String path;
	private List<Object> errorCodes = new LinkedList<>();

	public Ob_ExceptionResponse() {
	}

	public Ob_ExceptionResponse(Date timestamp, String message, String path, List<Object> errorCodes) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.path = path;
		if (errorCodes instanceof List) {
			this.errorCodes = errorCodes;
		}
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Object> getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(List<Object> errorCodes) {
		this.errorCodes = errorCodes;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [timestamp=" + timestamp + ", message=" + message + ", path=" + path
				+ ", errorCodes=" + errorCodes + "]";
	}
}
