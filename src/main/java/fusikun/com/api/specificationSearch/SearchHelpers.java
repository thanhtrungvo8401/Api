package fusikun.com.api.specificationSearch;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		if (this.field.contains(Constant.DOT)) {
			String key = field.split(Constant.DOT_REGEX)[0];
			String subKey = field.split(Constant.DOT_REGEX)[1];
			return new _SearchCriteria(key, operator, value, subKey);
		} else {
			return new _SearchCriteria(field, operator, value);
		}
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
		Pattern p = Pattern.compile("^[a-z.-]{2,}<!>.{1,}$");
		Matcher m = p.matcher(filter);
		if (!m.matches())
			throw new Ex_InvalidSearch("Filter = [" + filter + "] is invalid filter!");
		String[] arr = filter.split(Constant.FILTER_DIVICE);
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
