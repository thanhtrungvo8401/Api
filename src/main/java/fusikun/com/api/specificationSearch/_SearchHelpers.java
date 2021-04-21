package fusikun.com.api.specificationSearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.enums.SearchOperator;
import fusikun.com.api.exceptionHandlers.Ex_InvalidSearch;
import fusikun.com.api.utils.Constant;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class _SearchHelpers<T extends _Specification> {
    Map<String, String> filters;
    T specification;

    public _SearchHelpers(T specification, String _filters) {
        Map<String, String> filterMap;
        try {
            filterMap = new Gson().fromJson(_filters, new TypeToken<Map<String, String>>() {}.getType());
        } catch (Exception e) {
            throw new Ex_InvalidSearch("Filters = [" + _filters + "] is " + "invalid " + "filter!");
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
            SearchOperator operator = getSearchOperator(operatorKey);
            Object value = getValue(operatorKey, objectValue);
            if (this.field.contains(Constant.DOT)) {
                String key = field.split(Constant.DOT_REGEX)[0];
                String subKey = field.split(Constant.DOT_REGEX)[1];
                return new _SearchCriteria(key, operator, value, subKey, type);
            } else {
                return new _SearchCriteria(field, operator, value, type);
            }
        }
        // helper:
        private SearchOperator getSearchOperator(String operatorkey) {
            SearchOperator operator = null;
            switch (operatorkey) {
                case "greater-than":
                    operator = SearchOperator.GREATER_THAN;
                    break;
                case "less-than":
                    operator = SearchOperator.LESS_THAN;
                    break;
                case "greater-than-equal":
                    operator = SearchOperator.GREATER_THAN_EQUAL;
                    break;
                case "less-than-equal":
                    operator = SearchOperator.LESS_THAN_EQUAL;
                    break;
                case "not-equal":
                    operator = SearchOperator.NOT_EQUAL;
                    break;
                case "equal":
                    operator = SearchOperator.EQUAL;
                    break;
                case "like":
                    operator = SearchOperator.MATCH;
                    break;
                case "like-start":
                    operator = SearchOperator.MATCH_START;
                    break;
                case "like-end":
                    operator = SearchOperator.MATCH_END;
                    break;
                case "in":
                    operator = SearchOperator.IN;
                    break;
                case "not-in":
                    operator = SearchOperator.NOT_IN;
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
            return filter.split(Constant.FILTER_DIVICE);
        }

        private Object getValue(String operatorKey, String objectValue) {
//            operatorKey == "in" || operatorKey == "not-in"
            if (Arrays.asList("in", "not-in").contains(operatorKey)) {
                try {
                    return Arrays.<Object>asList(
                            objectValue.split(Constant.COMMA)
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Ex_InvalidSearch(operatorKey + " " + objectValue + " is invalid filter!");
                }
            }
            return objectValue;
        }
    }
}