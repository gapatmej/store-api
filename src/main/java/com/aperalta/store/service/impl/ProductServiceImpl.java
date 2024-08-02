package com.aperalta.store.service.impl;

import com.aperalta.store.domain.Product;
import com.aperalta.store.repository.ProductRepository;
import com.aperalta.store.service.ProductService;
import com.aperalta.store.service.dto.ProductDTO;
import com.aperalta.store.service.mapper.ProductMapper;
import com.aperalta.store.utils.GsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProductServiceImpl extends AbstractService implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        super(ProductServiceImpl.class);
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        ProductDTO result = productMapper.toDto(save(productMapper.toEntity(productDTO)));
        log.debug("Product saved : {}", GsonUtils.entityToJson(result));
        return result;
    }


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

}
