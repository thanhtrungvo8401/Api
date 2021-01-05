package fusikun.com.api.dto;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String password;

	// need default constructor for JSON Parsing
	public JwtRequest() {
	}

	public JwtRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
