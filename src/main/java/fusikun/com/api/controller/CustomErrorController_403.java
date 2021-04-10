package fusikun.com.api.controller;

import fusikun.com.api.exceptionHandlers.Ob_ExceptionResponse;
import fusikun.com.api.service.CustomErrorService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(CustomErrorController_403.class);

    @RequestMapping("/error")
    public ResponseEntity<Ob_ExceptionResponse> handleError(HttpServletRequest request) {
        try {
            return customErrorService._generateErrorResponse(request);
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
