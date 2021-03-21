package fusikun.com.api.specificationSearch;

import fusikun.com.api.enums.ApiDataType;
import org.joda.time.Instant;

import java.util.Date;
import java.util.UUID;

public class _SearchCriteria {
    private final String key;
    private String subKey;
    private final Object value;
    private final _SearchOperator operator;
    private ApiDataType type;

    public _SearchCriteria(String key, _SearchOperator operator, Object value) {
        this.key = key;
        this.value = value;
        this.operator = operator;
    }

    public _SearchCriteria(String key, _SearchOperator operator, Object value, ApiDataType type) {
        this.key = key;
        this.value = value;
        this.operator = operator;
        this.type = type;
    }

    public _SearchCriteria(String key, _SearchOperator operator, Object value, String subKey, ApiDataType type) {
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
        return value;
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

    public _SearchOperator getOperator() {
        return operator;
    }
}
