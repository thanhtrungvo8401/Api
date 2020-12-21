package fusikun.com.api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fusikun.com.api.dao.MenuRepository;
import fusikun.com.api.model.Menu;
import fusikun.com.api.specificationSearch.MenuSpecification;
import fusikun.com.api.specificationSearch.SearchCriteria;
import fusikun.com.api.specificationSearch.SearchOperator;

@Service
@Transactional(rollbackOn = Exception.class)
public class MenuServiceImpl implements MenuService {
	@Autowired
	MenuRepository menuRepository;

	@Override
	public Menu findById(Long id) {
		Optional<Menu> optMenu = menuRepository.findById(id);
		if (optMenu.isPresent()) {
			return optMenu.get();
		} else
			return null;
	}

	@Override
	public Menu save(Menu entity) {
		entity.setCreatedDate(new Date());
		if (entity.getId() == null) {
			entity.setUpdatedDate(new Date());
		}
		entity.setIsActive(true);
		return menuRepository.save(entity);
	}

	@Override
	public List<Menu> findAll() {
		return menuRepository.findAll();
	}

	@Override
	public List<Menu> findAllParentMenus() {
		return menuRepository.findAllParentMenu();
	}

	@Override
	public Boolean existsById(Long id) {
		return menuRepository.existsById(id);
	}

	@Override
	public Boolean existsByName(String name) {
		return this.countByName(name) > 0;
	}

	@Override
	public Integer countByName(String name) {
		return menuRepository.countByName(name);
	}

	@Override
	public Integer countByUrl(String url) {
		return menuRepository.countByUrl(url);
	}

	@Override
	public void deleteById(Long id) {
		menuRepository.deleteById(id);
	}

	@Override
	public void deleteMenuHasNameNotInList(List<String> listName) {
		MenuSpecification menuSpecification = new MenuSpecification();
		menuSpecification.add(new SearchCriteria("name", SearchOperator.NOT_IN, listName));
		List<Menu> menusNotInList = menuRepository.findAll(menuSpecification);
		menuRepository.deleteAll(menusNotInList);
	}

	@Override
	public Menu findByName(String name) {
		return menuRepository.findByName(name).get(0);
	}
}
