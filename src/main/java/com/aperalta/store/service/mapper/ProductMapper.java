package com.aperalta.store.service.mapper;

import com.aperalta.store.domain.Product;
import com.aperalta.store.service.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(config = EntityMapperConfigIgnoreAuditProps.class)
public interface ProductMapper {

    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO productDTO);

}
