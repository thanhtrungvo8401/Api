package fusikun.com.api.controller;


import fusikun.com.api.dtoRES.ObjectsManagementList;
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

    @GetMapping("/menu-actions")
    public ResponseEntity<ObjectsManagementList> getMenuActions() {
        return ResponseEntity.ok(menuService._findAllMenuActions());
    }

    @GetMapping("/menu-actions/generate")
    public ResponseEntity<Object> generateActionsMenu() throws Ex_MethodArgumentNotValidException {
        menuService._generateActionsMenu();
        return ResponseEntity.status(201).body("SUCCESS");
    }
}
