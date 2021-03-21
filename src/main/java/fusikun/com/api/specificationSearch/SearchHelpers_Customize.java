package fusikun.com.api.specificationSearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.exceptionHandlers.Ex_InvalidSearch;
import fusikun.com.api.utils.Constant;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchHelpers_Customize<T extends Specification_Customize> {
    Map<String, String> filters;
    T specification;

    public SearchHelpers_Customize(T specification, String _filters) {
        Map<String, String> filterMap;
        try {
            filterMap = new Gson().fromJson(
                    _filters,
                    new TypeToken<Map<String, String>>() {
                    }.getType());
        } catch (Exception e) {
            throw new Ex_InvalidSearch("Filters = [" + _filters + "] is " +
                    "invalid " + "filter!");
        }
        this.filters = filterMap != null ? filterMap : new HashMap<>();
        this.specification = specification;
    }

    public T getSpecification(List<String> keyAndTypes) {
        keyAndTypes.forEach(keyAndType -> {
            String key = keyAndType.split(Constant.COMMA)[0];
            ApiDataType type = ApiDataType.valueOf(keyAndType.split(Constant.COMMA)[1]);
            String filter = filters.get(key);
            if (StringUtils.hasText(filter)) {
                _SearchCriteria searchCriteria = new Helpers(key, filter, type)
                        .getSearchCriteria();
                specification.add(searchCriteria);
            }
        });
        return specification;
    }

    private class Helpers {
        /**
         * field=filter <=> field=operatorKey objectValue .........................
         * Example: email=like<!>8402, id=in<!>1,2,3,4
         */
        String field;
        String filter;
        ApiDataType type;

        public Helpers(String field, String filter, ApiDataType type) {
            this.field = field;
            this.filter = filter;
            this.type = type;
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
                return new _SearchCriteria(key, operator, value, subKey, type);
            } else {
                return new _SearchCriteria(field, operator, value, type);
            }
        }

        private _SearchOperator getSearchOperator(String operatorkey) {
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

        private String[] extractStringToArray(String filter) {
            Pattern p = Pattern.compile("^[a-z.-]{2,}<!>.{1,}$");
            Matcher m = p.matcher(filter);
            if (!m.matches())
                throw new Ex_InvalidSearch("Filter = [" + filter + "] is invalid filter!");
            String[] arr = filter.split(Constant.FILTER_DIVICE);
            return arr;
        }

        private Object getValue(String operatorKey, String objectValue) {
            if (operatorKey == "in" || operatorKey == "not-in") {
                try {
                    List<Object> listValue = Arrays.asList(
                            objectValue.toString().split(Constant.COMMA)
                    );
                    return listValue;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Ex_InvalidSearch(operatorKey + " " + objectValue + " is invalid filter!");
                }
            }
            return objectValue;
        }
    }
}