package fusikun.com.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dto.MenuRequest;
import fusikun.com.api.dto.MenuResponse;
import fusikun.com.api.enums.UrlEnpointEnums;
import fusikun.com.api.exceptionHandlers.Customize_MethodArgumentNotValidException;
import fusikun.com.api.model.Menu;
import fusikun.com.api.service.MenuService;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.validator.MenuDataValidate;

@RestController
public class MenuController {

	@Autowired
	MenuDataValidate menuDataValidate;

	@Autowired
	MenuService menuService;

	@GetMapping("/menu-actions")
	public ResponseEntity<Object> getMenuActions() {
		List<Menu> listMenus = menuService.findAll();
		List<MenuResponse> listResponse = new ArrayList<>();
		listMenus.forEach(menu -> {
			listResponse.add(new MenuResponse(menu));
		});
		return ResponseEntity.ok(listResponse);
	}

	@GetMapping("/menu-actions/generate")
	public ResponseEntity<Object> generateActionsMenu() throws Customize_MethodArgumentNotValidException {
		deleteMenuActionsNotInList();
		for (UrlEnpointEnums enpoint : UrlEnpointEnums.values()) {
			MenuActionGenerateUitls(enpoint);
		}
		return ResponseEntity.status(201).body("SUCCESS");
	}

	private void MenuActionGenerateUitls(UrlEnpointEnums enpoint) throws Customize_MethodArgumentNotValidException {
		String enpointName = enpoint.toString();
		String enpointUrl = enpoint.getUrl();
		MenuRequest menuRequest = new MenuRequest();
		menuRequest.setName(enpointName);
		menuRequest.setUrl(enpointUrl);
		if (!menuService.existsByName(enpointName)) {
			if (enpointName.indexOf(Constant.MENU_ADDRESS_DEVIDE) >= 0) {
				String parentName = enpointName.split(Constant.MENU_ADDRESS_DEVIDE)[0];
				Menu parentMenu = menuService.findByName(parentName);
				menuRequest.setParentId(parentMenu.getId());
			}
			createMenuActions(menuRequest);
		}
	}

	private void createMenuActions(MenuRequest menuRequest) throws Customize_MethodArgumentNotValidException {
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
}
