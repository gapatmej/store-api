package com.aperalta.store.web.rest;

import com.aperalta.store.service.ProductService;
import com.aperalta.store.service.StatisticsService;
import com.aperalta.store.service.dto.ProductDTO;
import com.aperalta.store.utils.GsonUtils;
import com.aperalta.store.web.rest.errors.BadRequestAlertException;
import com.aperalta.store.web.rest.util.HeaderUtil;
import com.aperalta.store.web.rest.util.PaginationUtil;
import com.aperalta.store.web.rest.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class ProductResource extends AbstractResource {

    private final ProductService productService;
    private final StatisticsService statisticsService;

    ProductResource(ProductService productService, StatisticsService statisticsService) {

        super(ProductResource.class, "product");
        this.productService = productService;
        this.statisticsService = statisticsService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", GsonUtils.entityToJson(productDTO));

        if (productDTO.getId() != null) throw new BadRequestAlertException("Un nuevo producto no puede tener un id", entityName, "idNotPermitted");

        ProductDTO result = productService.save(productDTO);
        statisticsService.increaseStatistics(result);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(entityName, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/products")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.debug("REST request to update Product : {}", GsonUtils.entityToJson(productDTO));

        if (productDTO.getId() == null) throw new BadRequestAlertException("Falta indicar el id a actualizar", entityName, "idMissing");

        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(entityName, productDTO.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);

        productService.delete(id).ifPresent(statisticsService::decreaseStatistics);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(entityName, id.toString())).build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(String search, Pageable pageable) {
        log.debug("REST request to get a page of Products");
        Page<ProductDTO> page = productService.findAll(search,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        Optional<ProductDTO> productDTO = productService.findOneDto(id);
        return ResponseUtil.wrapOrNotFound(productDTO);
    }
}
