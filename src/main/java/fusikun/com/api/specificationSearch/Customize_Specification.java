package fusikun.com.api.specificationSearch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class Customize_Specification<T> implements Specification<T> {
	private static final long serialVersionUID = -5036585350713413952L;
	private List<SearchCriteria> list;

	public Customize_Specification() {
		this.list = new ArrayList<>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();
		// Compare criteria.Operator => Predicates.add(operator, value);
		for (SearchCriteria criteria : list) {
			switch (criteria.getOperator()) {
			case GREATER_THAN:
				predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
				break;
			case LESS_THAN:
				predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
				break;
			case GREATER_THAN_EQUAL:
				predicates
						.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
				break;
			case LESS_THAN_EQUAL:
				predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
				break;
			case NOT_EQUAL:
				predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
				break;
			case EQUAL:
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
				break;
			case MATCH:
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase() + "%"));
				break;
			case MATCH_END:
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						criteria.getValue().toString().toLowerCase() + "%"));
				break;
			case MATCH_START:
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase()));
				break;
			case IN:
				predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
				break;
			case NOT_IN:
				predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()).not());
				break;
			default:
				break;
			}
		}
		return builder.and(predicates.toArray(new Predicate[0]));
	}

}
