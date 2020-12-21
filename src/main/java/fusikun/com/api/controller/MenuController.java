package fusikun.com.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dto.MenuResponse;
import fusikun.com.api.enums.UrlEnpointEnums;
import fusikun.com.api.model.Menu;
import fusikun.com.api.service.MenuService;
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
	public ResponseEntity<Object> generateActionsMenu() {
		deleteMenuActionsNotInList();
		for (UrlEnpointEnums enpoint : UrlEnpointEnums.values()) {
			MenuActionGenerateUitls(enpoint);
		}
		return null;
	}

	private void MenuActionGenerateUitls(UrlEnpointEnums enpoint) {
		String enpointName = enpoint.toString();
		String enpointUrl = enpoint.getUrl();

		System.out.println(enpoint + ": " + enpoint.getUrl());
	}

	private void deleteMenuActionsNotInList() {
		List<String> list = new ArrayList<>();
		for (UrlEnpointEnums enpoint : UrlEnpointEnums.values()) {
			list.add(enpoint.toString());
		}
		menuService.deleteMenuHasNameNotInList(list);
	}

//	private void getParentMenu() {
//	List<Menu> listParentMenus = menuService.findAllParentMenus();
//	List<MenuResponse> listResponse = new ArrayList<>();
//	listParentMenus.forEach(menu -> {
//		listResponse.add(new MenuResponse(menu));
//	});
//}
//
//private void createMenuActions(MenuRequest menuRequest)
//		throws Customize_MethodArgumentNotValidException {
//	menuDataValidate.validate(menuRequest);
//	Menu menu = menuRequest.getMenu();
//	Menu savedMenu = menuService.save(menu);
//}
//

//@GetMapping("/menu-actions/defined-url")
//public ResponseEntity<Object> getDefinedUrls() {
//	return null;
//}
}
