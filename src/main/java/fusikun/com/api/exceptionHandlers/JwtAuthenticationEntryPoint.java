package fusikun.com.api.exceptionHandlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import fusikun.com.api.utils.ConstantErrorCodes;
import fusikun.com.api.utils.ConstantMessages;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = 4418997171920318541L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		PrintWriter out = response.getWriter();
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(ConstantErrorCodes.INVALID_TOKEN);
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), authException.getMessage(),
				ConstantMessages.INVALID_TOKEN, errorCodes);
		String exceptionResponseJson = new Gson().toJson(exceptionResponse);
		out.print(exceptionResponseJson);
		out.flush();
	}

}
