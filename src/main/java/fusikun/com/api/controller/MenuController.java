package fusikun.com.api.controller;


import fusikun.com.api.dtoRES.ObjectsManagementList;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.service.MenuService;

@RestController
@RequestMapping("/api/v1")
public class MenuController {

    @Autowired
    MenuService menuService;

    Logger logger = LoggerFactory.getLogger(MenuController.class);

    @GetMapping("/menu-actions")
    public ResponseEntity<ObjectsManagementList> getMenuActions() {
        try {
            return ResponseEntity.ok(menuService._findAllMenuActions());
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    @GetMapping("/menu-actions/generate")
    public ResponseEntity<Object> generateActionsMenu() throws Ex_MethodArgumentNotValidException {
        try {
            menuService._generateActionsMenu();
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
        return ResponseEntity.status(201).body("SUCCESS");
    }
}
