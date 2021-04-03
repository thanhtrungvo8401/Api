package fusikun.com.api.controller;

import fusikun.com.api.dto.CenterResponse;
import fusikun.com.api.model.study.Center;
import fusikun.com.api.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CenterController {
    @Autowired
    CenterService centerService;

    @GetMapping("/api/v1/centers")
    public ResponseEntity<List<CenterResponse>> handleGetCenters() {
        List<Center> centers = centerService.findAll();
        return ResponseEntity.ok(centers.stream().map(CenterResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/api/common/v1/centers")
    public ResponseEntity<CenterResponse> handleGetCenterByCenterName(
            @RequestParam(name = "centerName") String centerName
    ) {
        Center center = centerService.findByCenterName(centerName);
        return ResponseEntity.ok(new CenterResponse(center));
    }
}
