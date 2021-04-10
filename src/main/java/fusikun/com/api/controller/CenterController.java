package fusikun.com.api.controller;

import fusikun.com.api.dtoRES.CenterResponse;
import fusikun.com.api.service.CenterService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CenterController {
    @Autowired
    CenterService centerService;

    Logger logger = LoggerFactory.getLogger(CenterController.class);

    @GetMapping("/api/v1/centers")
    public ResponseEntity<List<CenterResponse>> handleGetCenters() {
        try {
            return ResponseEntity.ok(centerService._findAllCenters());
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }
}
