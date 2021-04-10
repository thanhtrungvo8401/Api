package fusikun.com.api.service;

import fusikun.com.api.exceptionHandlers.Ob_ExceptionResponse;
import fusikun.com.api.exceptionHandlers.Ob_FieldError;
import fusikun.com.api.utils.ConstantErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class CustomErrorService {
    public ResponseEntity<Ob_ExceptionResponse> _generateErrorResponse(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String path = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        List<Object> errorCodes = new LinkedList<>();
        Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse();
        exceptionResponse.setTimestamp(new Date());
        exceptionResponse.setPath(path);

        String code = status == HttpStatus.FORBIDDEN.value()
                ? ConstantErrorCodes.ACCESS_DENIED
                : ConstantErrorCodes.INTERNAL_SERVER_ERROR;
        String message = status == HttpStatus.FORBIDDEN.value()
                ? "Your access to '" + path + "' is forbidden"
                : "Exception in filter or interceptor!";
        HttpStatus httpStatus = status == HttpStatus.FORBIDDEN.value()
                ? HttpStatus.FORBIDDEN
                : HttpStatus.INTERNAL_SERVER_ERROR;

        errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, code));
        exceptionResponse.setErrorCodes(errorCodes);
        exceptionResponse.setMessage(message);
        return ResponseEntity.status(httpStatus).body(exceptionResponse);
    }
}
