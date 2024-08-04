package com.aperalta.store.repository.specification;

import com.aperalta.store.repository.enumeration.QueryOperationEnum;
import com.aperalta.store.utils.Utils;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class AbsctractSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    public AbsctractSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        try{
            if (criteria.getOperation().equalsIgnoreCase(QueryOperationEnum.GREATER_THAN.value())) {
                return criteriaBuilder.greaterThan(buildPath(root,criteria.getKey()), criteria.getValue().toString());
            }
            else if (criteria.getOperation().equalsIgnoreCase(QueryOperationEnum.GREATER_OR_EQUAL.value())) {
                return criteriaBuilder.greaterThanOrEqualTo(buildPath(root,criteria.getKey()), criteria.getValue().toString());
            }
            else if (criteria.getOperation().equalsIgnoreCase(QueryOperationEnum.LESS_THAN.value())) {
                return criteriaBuilder.lessThan(buildPath(root,criteria.getKey()), criteria.getValue().toString());
            }
            else if (criteria.getOperation().equalsIgnoreCase(QueryOperationEnum.LESS_OR_EQUAL.value())) {
                return criteriaBuilder.lessThanOrEqualTo(buildPath(root,criteria.getKey()), criteria.getValue().toString());
            }
            else if (criteria.getOperation().equalsIgnoreCase(QueryOperationEnum.EQUAL.value())) {
                return criteriaBuilder.equal(buildPath(root,criteria.getKey()), criteria.getValue());
            }
            else if (criteria.getOperation().equalsIgnoreCase(QueryOperationEnum.NOT_DATA.value())) {
                return criteriaBuilder.notEqual(criteriaBuilder.literal(criteria.getKey()),criteria.getValue());
            }
            else if (criteria.getOperation().equalsIgnoreCase(QueryOperationEnum.NOT_EQUAL.value())) {
                return criteriaBuilder.notEqual(buildPath(root,criteria.getKey()),criteria.getValue());
            }
            else if (criteria.getOperation().equalsIgnoreCase(QueryOperationEnum.IN.value())) {
                return buildPath(root,criteria.getKey()).in(Utils.stringToList(criteria.getValue().toString(),QueryOperationEnum.IN_SEPARATOR.value()));
            }
            else if (criteria.getOperation().equalsIgnoreCase(QueryOperationEnum.NOT_IN.value())) {
                return buildPath(root,criteria.getKey()).in(Utils.stringToList(criteria.getValue().toString(),QueryOperationEnum.IN_SEPARATOR.value())).not();
            }
            else if (criteria.getOperation().equalsIgnoreCase(QueryOperationEnum.LIKE.value())) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return criteriaBuilder.like(criteriaBuilder.lower(buildPath(root,criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
                } else {
                    return criteriaBuilder.equal(buildPath(root,criteria.getKey()), criteria.getValue());
                }
            }
            return null;
        }catch (IllegalArgumentException ex){
            return null;
        }

    }

    private Path buildPath(Root<T> root, String key){
        Path path = root;
        String[] keys = key.split(QueryOperationEnum.KEY_SEPARATOR.value());
        for (String s : keys) {
            path = path.get(s);
        }
        return path;
    }

}
