package fusikun.com.api.specificationSearch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class Specification_Customize<T> implements Specification<T> {
    private static final long serialVersionUID = -5036585350713413952L;
    private List<_SearchCriteria> list;

    public Specification_Customize() {
        this.list = new ArrayList<>();
    }

    public void add(_SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        // Compare criteria.Operator => Predicates.add(operator, value);
        for (_SearchCriteria criteria : list) {
            switch (criteria.getOperator()) {
                case GREATER_THAN:
                    if (criteria.hasSubkey()) {
                        predicates.add(builder.greaterThan(root.join(criteria.getKey()).get(criteria.getSubKey()),
                                criteria.getComparableValue()));
                    } else {
                        predicates.add(builder.greaterThan(root.get(criteria.getKey()),
                                criteria.getComparableValue()));
                    }
                    break;
                case LESS_THAN:
                    if (criteria.hasSubkey()) {
                        predicates.add(builder.lessThan(root.join(criteria.getKey()).get(criteria.getSubKey()),
                                criteria.getComparableValue()));
                    } else {
                        predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getComparableValue()));
                    }

                    break;
                case GREATER_THAN_EQUAL:
                    if (criteria.hasSubkey()) {
                        predicates.add(builder.greaterThanOrEqualTo(root.join(criteria.getKey()).get(criteria.getSubKey()),
                                criteria.getComparableValue()));
                    } else {
                        predicates.add(
                                builder.greaterThanOrEqualTo(root.get(criteria.getKey()),
                                        criteria.getComparableValue()));
                    }

                    break;
                case LESS_THAN_EQUAL:
                    if (criteria.hasSubkey()) {
                        predicates.add(builder.lessThanOrEqualTo(root.join(criteria.getKey()).get(criteria.getSubKey()),
                                criteria.getComparableValue()));
                    } else {
                        predicates.add(
                                builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getComparableValue()));
                    }

                    break;
                case NOT_EQUAL:
                    if (criteria.hasSubkey()) {
                        predicates.add(builder.notEqual(root.join(criteria.getKey()).get(criteria.getSubKey()),
                                criteria.getValue()));
                    } else {
                        predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
                    }
                    break;
                case EQUAL:
                    if (criteria.hasSubkey()) {
                        predicates.add(
                                builder.equal(root.join(criteria.getKey()).get(criteria.getSubKey()), criteria.getValue()));
                    } else {
                        predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    }
                    break;
                case MATCH:
                    if (criteria.hasSubkey()) {
                        predicates.add(builder.like(builder.lower(root.join(criteria.getKey()).get(criteria.getSubKey())),
                                "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    } else {
                        predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
                                "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    }

                    break;
                case MATCH_START:
                    if (criteria.hasSubkey()) {
                        predicates.add(builder.like(builder.lower(root.join(criteria.getKey()).get(criteria.getSubKey())),
                                criteria.getValue().toString().toLowerCase() + "%"));
                    } else {
                        predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
                                criteria.getValue().toString().toLowerCase() + "%"));
                    }

                    break;
                case MATCH_END:
                    if (criteria.hasSubkey()) {
                        predicates.add(builder.like(builder.lower(root.join(criteria.getKey()).get(criteria.getSubKey())),
                                "%" + criteria.getValue().toString().toLowerCase()));
                    } else {
                        predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
                                "%" + criteria.getValue().toString().toLowerCase()));
                    }

                    break;
                case IN:
                    if (criteria.hasSubkey()) {
                        predicates.add(builder.in(root.join(criteria.getKey()).get(criteria.getSubKey()))
                                .value(criteria.getValue()));
                    } else {
                        predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
                    }

                    break;
                case NOT_IN:
                    if (criteria.hasSubkey()) {
                        predicates.add(builder.in(root.join(criteria.getKey()).get(criteria.getSubKey()))
                                .value(criteria.getValue()).not());
                    } else {
                        predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()).not());
                    }

                    break;
                default:
                    break;
            }
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
