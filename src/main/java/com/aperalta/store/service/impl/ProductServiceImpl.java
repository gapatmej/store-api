package com.aperalta.store.service.impl;

import com.aperalta.store.domain.Product;
import com.aperalta.store.repository.ProductRepository;
import com.aperalta.store.repository.specification.UtilsSpecification;
import com.aperalta.store.service.ProductService;
import com.aperalta.store.service.dto.ProductDTO;
import com.aperalta.store.service.mapper.ProductMapper;
import com.aperalta.store.utils.GsonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


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

    @Override
    public Optional<ProductDTO> delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        Optional<ProductDTO> productDTO = findOneDto(id);
        productDTO.ifPresent(p->productRepository.deleteById(p.getId()));
        return productDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String search, Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll( UtilsSpecification.getSpecification(search), pageable).map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOneDto(Long id) {
        return findOne(id).map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id);
    }


}
