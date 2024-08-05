package com.aperalta.store.web.rest;

import com.aperalta.store.config.TestSecurityConfig;
import com.aperalta.store.repository.enumeration.ProductCategoryEnum;
import com.aperalta.store.service.ProductService;
import com.aperalta.store.service.StatisticsService;
import com.aperalta.store.service.dto.ProductDTO;
import com.aperalta.store.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.context.annotation.Import;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(ProductResource.class)
@Import(TestSecurityConfig.class)
class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private StatisticsService statisticsService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenProductDTO_whenCreateProduct_thenReturnSavedProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO()
                .mainCode("021")
                .productCategory(ProductCategoryEnum.ELECTRONICS)
                .name("Producto 1 con accesorios")
                .price(BigDecimal.ONE)
                .attribute1("Con mantenimiento")
                .active(true);

        given(productService.save(any(ProductDTO.class)))
                .willAnswer((invocation) -> {
                    ProductDTO savedProduct = invocation.getArgument(0);
                    savedProduct.setId(1L);
                    return savedProduct;
                });

        ResultActions response = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)));

        verify(statisticsService).increaseStatistics(any(ProductDTO.class));

        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.mainCode", is(productDTO.getMainCode())))
                .andExpect(jsonPath("$.productCategory", is(productDTO.getProductCategory().toString())))
                .andExpect(jsonPath("$.name", is(productDTO.getName())))
                .andExpect(jsonPath("$.price", is(productDTO.getPrice().intValue())))
                .andExpect(jsonPath("$.attribute1", is(productDTO.getAttribute1())))
                .andExpect(jsonPath("$.attribute2", is(productDTO.getAttribute2())))
                .andExpect(jsonPath("$.active", is(productDTO.isActive())));
    }

    @Test
    void whenProductHasId_thenBadRequestAlertExceptionIsThrown() throws Exception {
        ProductDTO productDTO = new ProductDTO()
                .id(1L)
                .mainCode("021")
                .productCategory(ProductCategoryEnum.ELECTRONICS)
                .name("Producto 1 con accesorios")
                .price(BigDecimal.ONE)
                .active(true);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Un nuevo producto no puede tener un id")))
                .andExpect(jsonPath("$.entityName", is("product")))
                .andExpect(jsonPath("$.errorKey", is("idNotPermitted")));
    }

    @Test
    void whenProductHasNotId_thenBadRequestAlertExceptionIsThrown() throws Exception{
        long id = 1L;
        ProductDTO productDTO = new ProductDTO()
                .id(1L)
                .mainCode("021")
                .productCategory(ProductCategoryEnum.ELECTRONICS)
                .name("Producto 1 con accesorios")
                .price(BigDecimal.ONE)
                .attribute1("Con mantenimiento")
                .active(true);

        given(productService.save(any(ProductDTO.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/api/products", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(productDTO.getId().intValue())))
                .andExpect(jsonPath("$.mainCode", is(productDTO.getMainCode())))
                .andExpect(jsonPath("$.productCategory", is(productDTO.getProductCategory().toString())))
                .andExpect(jsonPath("$.name", is(productDTO.getName())))
                .andExpect(jsonPath("$.price", is(productDTO.getPrice().intValue())))
                .andExpect(jsonPath("$.attribute1", is(productDTO.getAttribute1())))
                .andExpect(jsonPath("$.attribute2", is(productDTO.getAttribute2())))
                .andExpect(jsonPath("$.active", is(productDTO.isActive())));
    }

    @Test
    void givenBadRequestAlertException_whenHandled_thenReturnCustomResponse() throws Exception {
        ProductDTO productDTO = new ProductDTO()
                .mainCode("021")
                .productCategory(ProductCategoryEnum.ELECTRONICS)
                .name("Producto 1 con accesorios")
                .price(BigDecimal.ONE)
                .attribute1("Con mantenimiento")
                .active(true);

        mockMvc.perform(put("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Falta indicar el id a actualizar")))
                .andExpect(jsonPath("$.entityName", is("product")))
                .andExpect(jsonPath("$.errorKey", is("idMissing")));
    }

    @Test
    void whenProductServiceSaveThrowsBadRequestAlertException_thenReturnBadRequest() throws Exception {
        ProductDTO productDTO = new ProductDTO()
                .id(1L)
                .mainCode("021")
                .productCategory(ProductCategoryEnum.ELECTRONICS)
                .name("Producto 1")
                .price(BigDecimal.ONE)
                .attribute1("Con mantenimiento")
                .active(true);

        doThrow(new BadRequestAlertException("Producto no encontrado", "product", "idNotFound"))
                .when(productService).save(any(ProductDTO.class));

        mockMvc.perform(put("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Producto no encontrado")))
                .andExpect(jsonPath("$.entityName", is("product")))
                .andExpect(jsonPath("$.errorKey", is("idNotFound")));
    }

    @Test
    void givenProductId_whenDeleteProduct_thenReturn204() throws Exception{
        long id = 1L;
        when(productService.delete(anyLong())).thenReturn(Optional.of(new ProductDTO()));

        ResultActions response = mockMvc.perform(delete("/api/products/{id}", id));

        ArgumentCaptor<ProductDTO> productCaptor = ArgumentCaptor.forClass(ProductDTO.class);
        verify(statisticsService).decreaseStatistics(productCaptor.capture());

        response.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void givenProductId_whenDeleteProduct_thenReturnOptionalEmpty() throws Exception{
        long id = 1L;
        when(productService.delete(anyLong())).thenReturn(Optional.empty());
        ResultActions response = mockMvc.perform(delete("/api/products/{id}", id));
        verify(statisticsService, times(0)).decreaseStatistics(any(ProductDTO.class));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void whenGetAllProducts_thenReturnListOfProducts() throws Exception {
        ProductDTO product1 = new ProductDTO()
                .id(1L)
                .mainCode("001")
                .productCategory(ProductCategoryEnum.ELECTRONICS)
                .name("Product 1")
                .price(BigDecimal.ONE)
                .attribute1("Attribute 1")
                .active(true);
        ProductDTO product2 = new ProductDTO()
                .id(2L)
                .mainCode("002")
                .productCategory(ProductCategoryEnum.CLOTHING)
                .name("Product 2")
                .price(BigDecimal.TEN)
                .attribute1("Attribute 3")
                .attribute2("Attribute 4")
                .active(true);
        List<ProductDTO> products = Arrays.asList(product1, product2);
        Page<ProductDTO> page = new PageImpl<>(products);

        when(productService.findAll(anyString(), any(Pageable.class))).thenReturn(page);

        ResultActions response = mockMvc.perform(get("/api/products")
                .param("search", "id[]1;2")
                .param("page", "0")
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].mainCode").value(product1.getMainCode()))
                .andExpect(jsonPath("$[1].mainCode").value(product2.getMainCode()));

        ArgumentCaptor<String> searchCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(productService).findAll(searchCaptor.capture(), pageableCaptor.capture());

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].mainCode").value(product1.getMainCode()))
                .andExpect(jsonPath("$[1].mainCode").value(product2.getMainCode()));

        assertEquals("id[]1;2", searchCaptor.getValue());
        assertEquals(0, pageableCaptor.getValue().getPageNumber());
        assertEquals(10, pageableCaptor.getValue().getPageSize());
    }

    @Test
    void whenGetProduct_thenReturnProduct() throws Exception {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO()
                .id(1L)
                .mainCode("001")
                .productCategory(ProductCategoryEnum.ELECTRONICS)
                .name("Product 1")
                .price(BigDecimal.ONE)
                .attribute1("Attribute 1")
                .active(true);
        when(productService.findOneDto(productId)).thenReturn(Optional.of(productDTO));

        ResultActions response = mockMvc.perform(get("/api/products/{id}", productId)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.mainCode", is(productDTO.getMainCode())))
                .andExpect(jsonPath("$.productCategory", is(productDTO.getProductCategory().toString())))
                .andExpect(jsonPath("$.name", is(productDTO.getName())))
                .andExpect(jsonPath("$.price").value(is(productDTO.getPrice().intValue())))
                .andExpect(jsonPath("$.attribute1", is(productDTO.getAttribute1())))
                .andExpect(jsonPath("$.attribute2", is(productDTO.getAttribute2())))
                .andExpect(jsonPath("$.active", is(productDTO.isActive())));
    }

    @Test
    void whenGetProduct_thenReturnNotFound() throws Exception {
        when(productService.findOneDto(1L)).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/api/products/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON));
        response.andExpect(status().isNotFound());
    }
}

