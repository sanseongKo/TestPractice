package sample.cafekiosk.spring.service.product;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.service.product.response.ProductResponse;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllByProductSellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }
}
