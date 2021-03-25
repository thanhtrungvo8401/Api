package fusikun.com.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fusikun.com.api.dto.ObjectsManagementList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dto.MenuRequest;
import fusikun.com.api.dto.MenuResponse;
import fusikun.com.api.enums.UrlEnpointEnums;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.model.app.Menu;
import fusikun.com.api.service.MenuService;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.validator.MenuDataValidate;

@RestController
@RequestMapping("/api/v1")
public class MenuController {

    @Autowired
    MenuDataValidate menuDataValidate;

    @Autowired
    MenuService menuService;

    @GetMapping("/menu-actions")
    public ResponseEntity<Object> getMenuActions() {
        List<Menu> listMenus = menuService.findAll();
        Long total = menuService.countActionMenus();
        List<MenuResponse> listResponse = listMenus.stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ListMenuActionsResponse(listResponse, total));
    }

    @GetMapping("/menu-actions/count")
    public ResponseEntity<Object> getMenuActionsCount() {
        Long total = menuService.countActionMenus();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/menu-actions/generate")
    public ResponseEntity<Object> generateActionsMenu() throws Ex_MethodArgumentNotValidException {
        deleteMenuActionsNotInList();
        for (UrlEnpointEnums enpoint : UrlEnpointEnums.values()) {
            MenuActionGenerateUitls(enpoint);
        }
        return ResponseEntity.status(201).body("SUCCESS");
    }

    private void MenuActionGenerateUitls(UrlEnpointEnums enpoint) throws Ex_MethodArgumentNotValidException {
        String enpointName = enpoint.toString();
        String enpointUrl = enpoint.getUrl();
        String enpointRegex = enpoint.getRegex();
        String enpointMethod = enpoint.getMethod();
        MenuRequest menuRequest = new MenuRequest();
        menuRequest.setName(enpointName);
        menuRequest.setUrl(enpointUrl);
        menuRequest.setRegex(enpointRegex);
        menuRequest.setMethod(enpointMethod);
        if (!menuService.existsByName(enpointName)) {
            if (enpointName.contains(Constant.MENU_ADDRESS_DEVIDE)) {
                String parentName = enpointName.split(Constant.MENU_ADDRESS_DEVIDE)[0];
                Menu parentMenu = menuService.findByName(parentName);
                menuRequest.setParentId(parentMenu.getId());
            }
            createMenuActions(menuRequest);
        }
    }

    private void createMenuActions(MenuRequest menuRequest) throws Ex_MethodArgumentNotValidException {
        menuDataValidate.validate(menuRequest);
        Menu menu = menuRequest.getMenu();
        Menu savedMenu = menuService.save(menu);
        System.out.println(savedMenu.toString());
    }

    private void deleteMenuActionsNotInList() {
        List<String> list = new ArrayList<>();
        for (UrlEnpointEnums enpoint : UrlEnpointEnums.values()) {
            list.add(enpoint.toString());
        }
        menuService.deleteMenuHasNameNotInList(list);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class ListMenuActionsResponse extends ObjectsManagementList<MenuResponse> {
        ListMenuActionsResponse(List<MenuResponse> list, Long total) {
            super(list, total);
        }
    }
}
