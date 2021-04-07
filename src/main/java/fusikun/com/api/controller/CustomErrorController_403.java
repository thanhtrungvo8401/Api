package fusikun.com.api.controller;

import fusikun.com.api.exceptionHandlers.Ob_ExceptionResponse;
import fusikun.com.api.exceptionHandlers.Ob_FieldError;
import fusikun.com.api.utils.ConstantErrorCodes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
public class CustomErrorController_403 implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<Object> handleError(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String path = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        List<Object> errorCodes = new LinkedList<>();
        Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse();
        exceptionResponse.setTimestamp(new Date());
        exceptionResponse.setPath(path);

        if (status == HttpStatus.FORBIDDEN.value()) {
            errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.ACCESS_DENIED));
            exceptionResponse.setErrorCodes(errorCodes);
            exceptionResponse.setMessage("Your access to '" + path  + "' is forbidden");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionResponse);
        } else {
            errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.INTERNAL_SERVER_ERROR));
            exceptionResponse.setErrorCodes(errorCodes);
            exceptionResponse.setMessage("Exception in filter or interceptor!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
        }
    }
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
