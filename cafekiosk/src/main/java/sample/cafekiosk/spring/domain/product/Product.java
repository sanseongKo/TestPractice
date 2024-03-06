package sample.cafekiosk.spring.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.AuditingEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus productSellingStatus;

    private String name;

    private int price;

    @Builder
    private Product(String productNumber, ProductType productType, ProductSellingStatus productSellingStatus,
                   String name, int price) {
        this.productNumber = productNumber;
        this.productType = productType;
        this.productSellingStatus = productSellingStatus;
        this.name = name;
        this.price = price;
    }
}
