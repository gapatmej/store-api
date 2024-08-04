package com.aperalta.store.repository.specification;

import com.aperalta.store.repository.enumeration.QueryOperationEnum;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpecificationsBuilder {
    private final List<SearchCriteria> params;

    public SpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public void with(String type, String key, String operation, Object value) {
        params.add(new SearchCriteria(type, key, operation, value));
    }

    public Specification build() {
        if (params.isEmpty()) {
            return null;
        }
        List<Specification> specs = params.stream()
            .map(AbsctractSpecification::new)
            .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            if(QueryOperationEnum.OR.value().equals(params.get(i).getType())){
                result = Specification.where(result).or(specs.get(i));
            }else{
                result = Specification.where(result).and(specs.get(i));
            }

        }

        return result;
    }

    public int size(){
        return params.size();
    }

}
