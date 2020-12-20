package fusikun.com.api.specificationSearch;

public class SearchCriteria {
	private String key;
	private Object value;
	private SearchOperator operator;

	public SearchCriteria() {
	}

	public SearchCriteria(String key, SearchOperator operator, Object value) {
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

	public SearchOperator getOperator() {
		return operator;
	}

	public void setOperator(SearchOperator operator) {
		this.operator = operator;
	}

}
