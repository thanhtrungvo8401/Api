package fusikun.com.api.service;

import java.util.List;

import fusikun.com.api.model.Menu;

public interface MenuService {

	// insert into menu(field, field) values (?, ?);
	public Menu save(Menu entity);

	// select * from menu as M where M.id = ?
	public Menu findById(Long id);

	// select * from menu;
	public List<Menu> findAll();

	// select exists( select 1 from menu as M where M.name = ? ) as isExist;
	public Boolean existsById(Long id);

	// select count(*) from menu as M where M.name = ?;
	public Integer countByName(String name);

	// select count(*) from menu as M where M.url = ?;
	public Integer countByUrl(String url);
}
