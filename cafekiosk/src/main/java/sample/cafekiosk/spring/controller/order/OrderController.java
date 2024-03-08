package sample.cafekiosk.spring.controller.order;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.service.order.OrderService;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public void createOrder(@RequestBody OrderCreateRequest request) {
        LocalDateTime registeredDateTime = LocalDateTime.now();

        orderService.createOrder(request, registeredDateTime);
    }
}
