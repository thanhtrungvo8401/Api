package fusikun.com.api.service;

import java.util.List;

import fusikun.com.api.model.Menu;

public interface MenuService {

	public Menu save(Menu entity);

	public Menu findById(Long id);
	
	public List<Menu> findAll();
	
	public Boolean existsById(Long id);
}
