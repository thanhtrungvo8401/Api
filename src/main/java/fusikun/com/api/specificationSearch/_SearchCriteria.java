package fusikun.com.api.specificationSearch;

import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.enums.SearchOperator;
import org.joda.time.Instant;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class _SearchCriteria {
    private final String key;
    private String subKey;
    private final Object value;
    private final SearchOperator operator;
    private ApiDataType type;

    public _SearchCriteria(String key, SearchOperator operator, Object value) {
        this.key = key;
        this.value = value;
        this.operator = operator;
    }

    public _SearchCriteria(String key, SearchOperator operator, Object value, ApiDataType type) {
        this.key = key;
        this.value = value;
        this.operator = operator;
        this.type = type;
    }

    public _SearchCriteria(String key, SearchOperator operator, Object value, String subKey, ApiDataType type) {
        this.key = key;
        this.value = value;
        this.operator = operator;
        this.subKey = subKey;
        this.type = type;
    }

    public Boolean hasSubkey() {
        return this.subKey != null;
    }

    public String getSubKey() {
        return subKey;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        if (type == null) return value;
        switch (type) {
            case INTEGER_TYPE:
                return !(value instanceof List)
                        ? Integer.parseInt(value.toString())
                        : ((List<?>) value)
                        .stream()
                        .map(el -> Integer.parseInt(el.toString()))
                        .collect(Collectors.toList());
            case UUID_TYPE:
                return !(value instanceof List)
                        ? UUID.fromString(value.toString())
                        : ((List<?>) value).stream()
                        .map(el -> UUID.fromString(el.toString()))
                        .collect(Collectors.toList());
            case DATE_TYPE:
                return !(value instanceof List)
                        ? new Date(Instant.parse(value.toString()).getMillis())
                        : ((List<?>) value).stream()
                        .map(el -> new Date(Instant.parse(el.toString()).getMillis()))
                        .collect(Collectors.toList());
            default:
                return !(value instanceof List)
                        ? value.toString()
                        : ((List<?>) value).stream()
                        .map(el -> el.toString())
                        .collect(Collectors.toList());
        }
    }

    public Comparable getComparableValue() {
//        GREATER_THAN, LESS_THAN, GREATER_THAN_EQUAL, LESS_THAN_EQUAL
        if (type == null) return null;
        switch (type) {
            case INTEGER_TYPE:
                return Integer.parseInt(value.toString());
            case UUID_TYPE:
                return UUID.fromString(value.toString());
            case DATE_TYPE:
                return new Date(Instant.parse(value.toString()).getMillis());
            default:
                return value.toString();
        }
    }

    public SearchOperator getOperator() {
        return operator;
    }
}
