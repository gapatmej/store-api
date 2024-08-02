package com.aperalta.store.web.rest;

import com.aperalta.store.service.ProductService;
import com.aperalta.store.service.dto.ProductDTO;
import com.aperalta.store.utils.GsonUtils;
import com.aperalta.store.web.rest.util.HeaderUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class ProductResource extends AbstractResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {

        super(ProductResource.class, "product");
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", GsonUtils.entityToJson(productDTO));

        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(true, entityName, result.getId().toString()))
            .body(result);
    }
}
