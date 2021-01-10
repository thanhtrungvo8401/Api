package fusikun.com.api.specificationSearch;

import java.util.Map;

import org.springframework.util.StringUtils;

public class SearchHelpers_Users {
	Map<String, String> filters;

	public SearchHelpers_Users(Map<String, String> filters) {
		this.filters = filters;
	}

	public Specification_User getUserSpecification() {
		Specification_User userSpecification = new Specification_User();
		String filter;
		// EMAIL SEARCH:
		filter = filters.get("email");
		if (StringUtils.hasText(filter)) {
			_SearchCriteria searchCriteria = new SearchHelpers("email", filter).getSearchCriteria();
			userSpecification.add(searchCriteria);
		}
		// ROLE ID SEARCH:
		filter = filters.get("role.id");
		if (StringUtils.hasText(filter)) {
			if (StringUtils.hasText(filter)) {
				_SearchCriteria searchCriteria = new SearchHelpers("role.id", filter).getSearchCriteria();
				userSpecification.add(searchCriteria);
			}
		}
		// ROLE NAME SEARCH:
		filter = filters.get("role.roleName");
		if (StringUtils.hasText(filter)) {
			if (StringUtils.hasText(filter)) {
				_SearchCriteria searchCriteria = new SearchHelpers("role.roleName", filter).getSearchCriteria();
				userSpecification.add(searchCriteria);
			}
		}
		return userSpecification;
	}
}