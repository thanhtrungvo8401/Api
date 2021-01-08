package fusikun.com.api.specificationSearch;

public class _SearchCriteria {
	private String key;
	private Object value;
	private _SearchOperator operator;

	public _SearchCriteria() {
	}

	public _SearchCriteria(String key, _SearchOperator operator, Object value) {
		this.key = key;
		this.value = value;
		this.operator = operator;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public _SearchOperator getOperator() {
		return operator;
	}

	public void setOperator(_SearchOperator operator) {
		this.operator = operator;
	}

}
