package fusikun.com.api.exceptionHandlers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String details;
	private List<Object> errorCodes = new LinkedList<>();

	public ExceptionResponse() {
	}

	public ExceptionResponse(Date timestamp, String message, String details, List<Object> errorCodes) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<Object> getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(List<Object> errorCodes) {
		this.errorCodes = errorCodes;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [timestamp=" + timestamp + ", message=" + message + ", details=" + details
				+ ", errorCodes=" + errorCodes + "]";
	}
}
