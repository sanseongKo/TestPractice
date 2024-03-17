package sample.cafekiosk.spring.service.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import sample.cafekiosk.spring.api.IntegrationTestSupport;
import sample.cafekiosk.spring.api.controller.product.dto.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;

import static org.assertj.core.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

class ProductServiceTest extends IntegrationTestSupport {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("상품을 생성 한다. 만들어진 상품의 상품 번호가 제일 최근 생성된 상품에 +1이 된다.")
    @Test
    void createProduct() {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);

        productRepository.saveAll(List.of(product1, product2, product3));

        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("카푸치노")
            .price(5000)
            .build();
        //when
        ProductResponse response = productService.createProduct(request.toServiceRequest());

        //then
        assertThat(response)
            .extracting("productNumber", "productType", "productSellingStatus", "name", "price")
            .contains("004", HANDMADE, SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products)
            .extracting("productNumber", "productType", "productSellingStatus", "name", "price")
            .containsExactlyInAnyOrder(
                tuple("004", HANDMADE, SELLING, "카푸치노", 5000),
                tuple("001", HANDMADE, SELLING, "아메리카노", 4000),
                tuple("002", HANDMADE, HOLD, "카페라떼", 4500),
                tuple("003", HANDMADE, STOP_SELLING, "팥빙수", 7000)
            );
    }

    @DisplayName("상품이 하나도 없는 상태에서 신규 상품을 등록하면, 상품이 등록되고 상품 번호는 001이 된다.")
    @Test
    void createNewProduct() {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                                           .type(HANDMADE)
                                                           .sellingStatus(SELLING)
                                                           .name("카푸치노")
                                                           .price(5000)
                                                           .build();
        //when
        ProductResponse response = productService.createProduct(request.toServiceRequest());

        //then
        assertThat(response)
            .extracting("productNumber", "productType", "productSellingStatus", "name", "price")
            .contains("001", HANDMADE, SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products)
            .extracting("productNumber", "productType", "productSellingStatus", "name", "price")
            .contains(tuple("001", HANDMADE, SELLING, "카푸치노", 5000));
    }

    private Product createProduct(String productNumber, ProductType productType,
                                  ProductSellingStatus productSellingStatus, String name, int price) {
        return Product.builder()
                      .productNumber(productNumber)
                      .productType(productType)
                      .productSellingStatus(productSellingStatus)
                      .name(name)
                      .price(price)
                      .build();
    }
}
