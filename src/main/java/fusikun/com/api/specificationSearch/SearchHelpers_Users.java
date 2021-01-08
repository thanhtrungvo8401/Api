package fusikun.com.api.specificationSearch;

import java.util.Map;

import org.springframework.util.StringUtils;

import fusikun.com.api.model.Role;

public class SearchHelpers_Users {
	Specification_User userSpecification = new Specification_User();

	public SearchHelpers_Users(Map<String, String> filters) {
		// EMAIL SEARCH:
		String emailStr = filters.get("email");
		if (StringUtils.hasText(emailStr)) {
			_SearchCriteria searchCriteria = new SearchHelpers("email", emailStr).getSearchCriteria();
			userSpecification.add(searchCriteria);
		}
		// ROLE SEARCH:
		String roleIdStr = filters.get("roleId");
		if (StringUtils.hasText(roleIdStr)) {
			String[] arr = SearchHelpers.extractStringToArray(roleIdStr);
			String operatorKey = arr[0];
			String objectValue = arr[1];
			_SearchOperator operator = SearchHelpers.getSearchOperator(operatorKey);
			Role role = new Role();
			role.setId(Long.parseLong(objectValue));
			userSpecification.add(new _SearchCriteria("role", operator, role));
		}
	}

	public Specification_User getUserSpecification() {
		return userSpecification;
	}
}
