package sample.cafekiosk.spring.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import sample.cafekiosk.spring.api.controller.product.ProductController;
import sample.cafekiosk.spring.api.controller.product.dto.ProductCreateRequest;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.api.service.product.ProductService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("신규 상품을 등록한다")
    @Test
    void createProduct() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                                           .type(ProductType.HANDMADE)
                                                           .sellingStatus(ProductSellingStatus.SELLING)
                                                           .name("아메리카노")
                                                           .price(400)
                                                           .build();
        //when
        //then

        mockMvc.perform(post("/api/v1/products/new").content(objectMapper.writeValueAsString(request))
                                                    .contentType(MediaType.APPLICATION_JSON))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isOk());
    }

    @DisplayName("신규 상품을 등록시 상품 타입이 없는 경우 예외가 발생한다.")
    @Test
    void createProductWithoutType() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                                           .sellingStatus(ProductSellingStatus.SELLING)
                                                           .name("아메리카노")
                                                           .price(400)
                                                           .build();
        //when
        //then
        mockMvc.perform(post("/api/v1/products/new").content(objectMapper.writeValueAsString(request))
                                                    .contentType(MediaType.APPLICATION_JSON))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.code").value("400"))
               .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
               .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
               .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록시 상품 판매 상태가 없는 경우 예외가 발생한다.")
    @Test
    void createProductWithoutSellingType() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                                           .type(ProductType.HANDMADE)
                                                           .name("아메리카노")
                                                           .price(400)
                                                           .build();
        //when
        //then
        mockMvc.perform(post("/api/v1/products/new").content(objectMapper.writeValueAsString(request))
                                                    .contentType(MediaType.APPLICATION_JSON))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.code").value("400"))
               .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
               .andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
               .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록시 상품 이름이 없는 경우 예외가 발생한다.")
    @Test
    void createProductWithoutName() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                                           .type(ProductType.HANDMADE)
                                                           .sellingStatus(ProductSellingStatus.SELLING)
                                                           .price(400)
                                                           .build();
        //when
        //then
        mockMvc.perform(post("/api/v1/products/new").content(objectMapper.writeValueAsString(request))
                                                    .contentType(MediaType.APPLICATION_JSON))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.code").value("400"))
               .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
               .andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
               .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록시 상품 가격이 없는 경우 예외가 발생한다.")
    @Test
    void createProductWithoutPrice() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                                           .type(ProductType.HANDMADE)
                                                           .sellingStatus(ProductSellingStatus.SELLING)
                                                           .name("아메리카노")
                                                           .build();
        //when
        //then
        mockMvc.perform(post("/api/v1/products/new").content(objectMapper.writeValueAsString(request))
                                                    .contentType(MediaType.APPLICATION_JSON))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.code").value("400"))
               .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
               .andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
               .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("판매 상품을 조회한다.")
    @Test
    void getSellingProducts() throws Exception {
        //given
        when(productService.getSellingProducts()).thenReturn(List.of());
        //when
        //then
        mockMvc.perform(get("/api/v1/products/selling"))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value("200"))
               .andExpect(jsonPath("$.status").value("OK"))
               .andExpect(jsonPath("$.message").value("OK"))
               .andExpect(jsonPath("$.data").isArray());
    }
}
