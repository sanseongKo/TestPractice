package sample.cafekiosk.spring.api.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.api.controller.product.dto.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.dto.ProductCreateServiceRequest;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllByProductSellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

    //동시성 이슈 가능
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        //productNumber 부여
        //001, 002, 003, 004
        //DB에서 마지막 저장된 Product의 상품 번호를 읽어와서 +1 해주려 한다.
        //009 -> 010
        //nextProductNumber
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }
}
