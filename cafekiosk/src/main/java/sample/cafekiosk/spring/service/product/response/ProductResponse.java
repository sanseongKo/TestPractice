package sample.cafekiosk.spring.service.product.response;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductResponse {
    private Long id;

    private String productNumber;

    private ProductType productType;

    private ProductSellingStatus productSellingStatus;

    private String name;

    private int price;

    @Builder
    private ProductResponse(Long id, String productNumber, ProductType productType,
                            ProductSellingStatus productSellingStatus, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.productType = productType;
        this.productSellingStatus = productSellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .productNumber(product.getProductNumber())
            .productType(product.getProductType())
            .productSellingStatus(product.getProductSellingStatus())
            .name(product.getName())
            .price(product.getPrice())
            .build();
    }


}
