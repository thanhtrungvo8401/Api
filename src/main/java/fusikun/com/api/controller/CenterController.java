package fusikun.com.api.controller;

import fusikun.com.api.dtoRES.CenterResponse;
import fusikun.com.api.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CenterController {
    @Autowired
    CenterService centerService;

    @GetMapping("/api/v1/centers")
    public ResponseEntity<List<CenterResponse>> handleGetCenters() {
        return ResponseEntity.ok(centerService._findAllCenters());
    }
}
