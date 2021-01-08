package fusikun.com.api.specificationSearch;

import java.util.Arrays;
import java.util.List;

import fusikun.com.api.exceptionHandlers.Ex_InvalidSearch;
import fusikun.com.api.utils.Constant;

public class SearchHelpers {

	/**
	 * field=filter <=> field=operatorKey objectValue .........................
	 * Example: email=like 8402, id=in 1,2,3,4
	 */
	String field;
	String filter;

	public SearchHelpers(String field, String filter) {
		this.field = field;
		this.filter = filter;
	}

	public final _SearchCriteria getSearchCriteria() {
		String[] arr = extractStringToArray(filter);
		String operatorKey = arr[0];
		String objectValue = arr[1];
		_SearchOperator operator = getSearchOperator(operatorKey);
		Object value = getValue(operatorKey, objectValue);
		return new _SearchCriteria(field, operator, value);
	}

	static public _SearchOperator getSearchOperator(String operatorkey) {
		_SearchOperator operator = null;
		switch (operatorkey) {
		case "greater-than":
			operator = _SearchOperator.GREATER_THAN;
			break;
		case "less-than":
			operator = _SearchOperator.LESS_THAN;
			break;
		case "greater-than-equal":
			operator = _SearchOperator.GREATER_THAN_EQUAL;
			break;
		case "less-than-equal":
			operator = _SearchOperator.LESS_THAN_EQUAL;
			break;
		case "not-equal":
			operator = _SearchOperator.NOT_EQUAL;
			break;
		case "equal":
			operator = _SearchOperator.EQUAL;
			break;
		case "like":
			operator = _SearchOperator.MATCH;
			break;
		case "like-start":
			operator = _SearchOperator.MATCH_START;
			break;
		case "like-end":
			operator = _SearchOperator.MATCH_END;
			break;
		case "in":
			operator = _SearchOperator.IN;
			break;
		case "not-in":
			operator = _SearchOperator.NOT_IN;
			break;
		default:
			break;
		}
		if (operator == null) {
			throw new Ex_InvalidSearch("Can not find operator with key = " + operatorkey);
		}
		return operator;
	}

	static public String[] extractStringToArray(String filter) {
		if (filter == null)
			throw new Ex_InvalidSearch("Filter is invalid filter!");
		String[] arr = filter.split(Constant.BLANK);
		if (arr.length != 2)
			throw new Ex_InvalidSearch(filter + " is invalid filter!");
		return arr;
	}

	private Object getValue(String operatorKey, Object objectValue) {
		if (operatorKey == "in" || operatorKey == "not-in") {
			try {
				String objectStr = (String) objectValue;
				String[] arr = objectStr.split(Constant.COMMA);
				List<String> listValue = Arrays.asList(arr);
				return listValue;
			} catch (Exception e) {
				e.printStackTrace();
				throw new Ex_InvalidSearch(operatorKey + " " + objectValue + " is invalid filter!");
			}
		}
		return objectValue;
	}
}
