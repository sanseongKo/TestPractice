package sample.cafekiosk.spring.api.controller.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import sample.cafekiosk.spring.api.ControllerTestSupport;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends ControllerTestSupport {

    @DisplayName("신규 주문을 등록한다")
    @Test
    void createOrder() throws Exception {
        //given
        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001")).build();


        //when
        //then

        mockMvc.perform(post("/api/v1/orders/new")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value("200"))
               .andExpect(jsonPath("$.status").value("OK"))
               .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("신규 주문을 등록할 때 상품 번호는 1개 이상이여 한다.")
    @Test
    void createOrderWithEmptyProductNumbers() throws Exception {
        //given

        OrderCreateRequest request = OrderCreateRequest.builder()
                                                       .productNumbers(List.of()).build();
        //when
        //then

        mockMvc.perform(post("/api/v1/orders/new")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
               )
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.code").value("400"))
               .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
               .andExpect(jsonPath("$.message").value("상품 번호 리스트는 필수 입니다."));
    }
}
