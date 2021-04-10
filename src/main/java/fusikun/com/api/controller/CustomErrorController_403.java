package fusikun.com.api.controller;

import fusikun.com.api.exceptionHandlers.Ob_ExceptionResponse;
import fusikun.com.api.service.CustomErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController_403 implements ErrorController {
    @Autowired
    CustomErrorService customErrorService;

    @RequestMapping("/error")
    public ResponseEntity<Ob_ExceptionResponse> handleError(HttpServletRequest request) {
        return customErrorService._generateErrorResponse(request);
    }
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
