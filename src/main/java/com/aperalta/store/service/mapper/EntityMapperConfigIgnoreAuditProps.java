package com.aperalta.store.service.mapper;

import com.aperalta.store.domain.AbstractMainEntity;
import com.aperalta.store.service.dto.AbstractMainDTO;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
@MapperConfig(componentModel = "spring", mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface EntityMapperConfigIgnoreAuditProps<D extends AbstractMainDTO, E extends AbstractMainEntity> {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    E toEntity(D dto);

    D toDto(E entity);

}
