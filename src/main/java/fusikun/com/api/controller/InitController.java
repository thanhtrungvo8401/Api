package fusikun.com.api.controller;

import fusikun.com.api.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class InitController {
    @Autowired
    UserService userService;

    @Autowired
    InitService initService;

    @Autowired
    Environment env;

    @GetMapping("/initial-project")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> handleSetupProject(@RequestParam("code") String code) throws Exception {
        String validCode = env.getProperty("configure.code");
        if (!code.equals(validCode)) {
            throw new Exception("Method not found!");
        }
        long totalUser = userService.count();
        if (totalUser != 0) {
            throw new Exception("Method not found!");
        }
        initService.initProject();
        return ResponseEntity.ok("PROJECT SETUP SUCCESSFULLY!");
    }
}
