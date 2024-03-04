package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeKioskTest {

    @Test
    @DisplayName("사람이 확인하는 테스트 케이스 cafe kiosk의 add")
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수: " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료: "+ cafeKiosk.getBeverages().get(0).getName());
    }
    /*
    테스트가 실패할 확률이 제로 = 검증에 대한 의미 없음
    자동화 실패 = 사람이 결국 확인하여야 함
    어떤 테스트를 하려는지 확인이 자신이 아니라면(자신도 시간이 지나면) 어려움
     */

    @Test
    @DisplayName("자동화 테스트 케이스 cafe kiosk의 add")
    void add_auto() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    //경계값
    @Test
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);
        //묶어서 진행 필요
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    }

    @Test
    void createOrder() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 14, 0));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }
    /*
    올바른 테스트가 아니려면 시간이라는 매번 바뀌는 변수가 안에 숨어있으면 된다.
     */

    @Test
    void createOrderOutsideOpenTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 23, 0)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 시간이 아닙니다.");
    }
}

/*
단위 테스트
- "작은 코드 단위"를 "독립적"으로 검증하는 테스트
ㄴ>작은 코드는 클래스, 메서드

- 검증 속도가 굉장히 빠르고 안정적이다.
- XUnit - Kent Beck 에서 파생된 SUnit, JUnit .... 등등
- AssertJ
ㄴ> 테스트 코드 작성을 원활하게 돕는 테스트 라이브러리
ㄴ> 풍부한 API, 메서드 체이닝 지원
 */

/*
테스트 하기 어려운 영역을 구분하고 분리하기

테스트하기 어려운 영역
- 관측할 때마다 다른 값에 의존하는 코드
ㄴ> 현재 날짜/시간, 랜덤값, 전역변수/ 함수, 사용자 입력 등
- 외부 세계에 영향을 주는 코드
ㄴ> 표준 출력, 메시지 발송, 데이터 베이스 기록하기 등

테스트하기 쉬운 영역
순수 함수
- 같은 입력에는 항상 같은 결과
- 외부 세상과 단절된 형태
 */
