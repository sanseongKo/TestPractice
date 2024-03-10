package sample.cafekiosk;

public class Something {}

//요구사항
/*
주문 목록에 음료 추가 삭제
주문 목록 전체 지우기
주문 목록 총 금액 계산하기
주문 생성하기

한 종류의 음료를 여러 잔에 한 번에 담는 기능

가게 운영시간(10:00 ~ 22:00) 외에는 주문을 생성할 수 없다.

키오스크 주문을 위한 상품 후보 리스트 조회하기
상품의 판매 상태: 판매중, 판매보류, 판매중지
-> 판매중, 판매보류인 상태의 상품을 화면에 보여준다.
id, 상품 번호, 상품 타입, 판매 상태, 상품 이름, 가격

상품 번호 리스트를 받아 주문 생성하기
주문은 주문 상태, 주문 등록 시간을 가진다.
주문의 총 금액을 계산할 수 있어야한다.

주문 생성 시 재고 확인 및 개수 차감 후 생성하기
재고는 상품 번호를 가진다.
재고와 관련 있는 상품 타입은 병 음료, 베이커리이다.

관리자 페이지에서 신규 상품을 등록할 수 있다.
상품명, 상ㅍ무 타입, 판매 상태, 가격등을 입력 받는다.

오늘 하루 판매한 매출을 메일로 받고싶은 니즈가 생겼다.
 */

//1일차
/*
질문하기: 암묵적이거나 아직 드러나지 않은 요구사항이 있는가?

해피 케이스

예외 케이스 - 암묵적인 경우가 많음

경계값 테스트가 중요 - 범위(이상, 이하, 초과, 미만) 구간, 날짜 등
 */


/*
TDD
프로덕션 코드보다 테스트 코드를 먼저 작성하여 테스트가 구현 과정을 주도하도록 하는 방법론

레드(실패하는 테스트) -> 그린(테스트 통과하는 최소한의 코딩) -> 리팩토링(테스트를 통과 유지하면서 구현 코드 개선)

핵심 가치 - 피드백
내가 작성한코드에 대해 빠르고 정확하게 피드백을 받을 수 있는 장점이 있다.

선기능 구현 후 테스트 작성 시
테스트 자체의 누락 가능성이 있다.
특정 케이스만 검증할 가능성이 있다.
잘못된 구현을 다소 늦게 발견할 가능성이 있다.

선테스트 후 기능구현
복잡도가 낮은 테스트 가능한 코드로 구현할 수 있게한다.
쉽게 발견하기 어려운 엣지 케이스를 놓치지 않게 해줄 수 있다.
구현에 대한 빠른 피드백을 받을 수 있다.
과감한 리팩토링이 가능해진다.
 */

/*
테스트
프로덕션 기능을 설명하는 테스트 코드 문서
다양한 테스트 케이스를 통해 프로덕션 코들르 이해하는 시각과 관점을 보완
어느 한 사람이 과거에 경험했던 고민의 결과물을 팀 차원으로 승격 시켜서 모두의 자산으로 공유할 수 있다.
 */

//2일차
/*
Display Name 을 섬세하게
명사의 나열보다는 문장으로 만든다
테스트 행위에 대한 결과까지 적는다
도메인 용어를 사용해서 한층 추상화된 내용을 담기
메서드 자체의 관점보다 도메인 정책 관점으로
테스트의 현상을 중점으로 기술하지 말것 -> 예를 들어 특정 시간 이전에 주문을 생성하면 '실패한다' 실패한다가 테스트의 현상이다.
 */

/*
BDD 스타일로 작성
TDD에서 파생된 개발 방법
함수 단위 테스트에 집중하기 보다 시나리오에 기반한 테스트 케이스 자체에 집중하여 테스트한다
개발자가 아닌 사람이 봐도 이해할 수 있을 수준의 추상화 수준을 권장
 */

//3일차
/*
레이어 아키텍처를 사용하는 의미
관심사를 분리하기 위하여 사용한다.
통합테스트가 필요하다 -> 단위테스트만으로 커버하기 힘든 곳이 있다.
 */

/*
통합테스트
여러 모듈이 협력하는 기능을 통합적으로 검증하는 테스트
일반적으로 작은 범위의 단위 테스트만으로는 기능 전체의 신뢰성을 보장할 수 없다.
풍부한 단위 테스트 & 큰 기능 단위를 검증하는 통합 테스트
 */

/*
스프링을 얘기할 때 library vs framework
- 라이브러리: 내 코드가 주체가 되고 필요한 기능이 있을 때 외부에 기능을 가져온다
- 프레임워크: 프레임워크 주체가 되고 내 코드는 수동적으로 변한다.

스프링의 3대 개념
- IoC: A객체가 B객체를 사용하고 싶을 때, A가 사용하면 B를 해제해주는 거까지 였는데, A와 B의 관계가 너무 긴밀하다 ->
이게 좀 분리가 되었으면 좋겠다(서로 너무 알고 있다.) 그래서 스프링이 이런 부분을 만들어서 넣어주고 관리해준다.
객체의 생명주기를 제 3자가 관리한다.
- DI: 스프링은 대체적으로 인터페이스로 약결합을 해주는데
- AOP: 로그나 트랜잭션 같이 비지니스 흐름과 관계없는 부가적인 부분을 모아서 하나의 모듈로 분리하게 된다.
프록시를 사용해서 구현하고 있다.
 */

/*
ORM? -> 객체지향 패러다임과 관계형 데이터베이스 패러다임이 다른데, 개발자가 객체의 데이터를 하나하나 매핑해서 DB에 저장하고 조회했는데
ORM을 사용해서 개발자는 매핑작업없이 다른 비지니스 로직에 집중할 수 있게 만들어 주었다.

JPA
자바 진영의 ORM 기술 표준
인터페이스이고 Hibernate를 구현체로 보통 쓴다.
반복적인 CRUD sql 생성및 실행 해주고 여러 부가기능 들을 제공한다.
편리하지만 쿼릴르 직접 작성하지 않기 때문에, 어떤 식으로 쿼리가 만들어지고 실행되는지 명확하게 이해하고 있어야한다.

Spring에서는 한번 더 추상화해서 Spring Data JPA를 제공한다.
Querydsl과 조합하여 많이 사용한다.(타입 체크, 동적 쿼리 작성에 좋다.)
 */

//4일차
/*
Persistence Layer
Data Access 역할
비즈니스 가공 로직이 포함되면 안되고 Data에 대한 CRUD에만 집중한 레이어이다.

Business Layer
비즈니스 로직을 구현하는 역할
Persistence Layer와 상호작용을 통해 비즈니스 로직을 전개시킨다.
트랜잭션을 보장해야 한다.
 */

/*
비지니스 레이어의 테스트는
서비스와 레파지토리를 합하여 테스트한다.
 */

/*
테스트에 @Transactional
테스트 마다 자동으로 롤백을 지원한다.

 */

//5일차
/*
Presentation Layer
외부 세계의 요청을 가장 먼저 받는 계층
파라미터에 대한 최소한의 검증을 수행한다. -> 넘겨온 값들에 대한 validation이 중요하다 생각 넘겨온 값이 유효한지

이 부분을 테스트 할때는 서비스 퍼시스턴스 레이어는 모킹할 것이다.

MockMvc -> Mock 객체를 사용해 스프링 MVC 동작을 재현할 수 있는 테스트 프레임워크
테스트 하는 부분만 빠르게 테스트하고 싶은데, 테스트를 하기위해 준비해야 할 것이 너무 많다.
 */

/*
로직이 얼마 없는 서비스의 테스트의 경우 코드가 얼마 되지 않아 레포지토리 테스트와 거의 동일할 수 있다.
하지만 두 테스트 모두 진행해주는 것이 좋다.
왜냐하면, 검증하는 항목이 조금 더 많을 수도 있고, 기능이 추가될 수록 서비스가 좀 더 발전 되기 때문이다.
 */

/*
transactional 어노테이션에 readOnly
readOnly = true를 주면 읽기 전용 트랜잭션이 열린다.
cud 작업이 동작이 되지 않는다.
jpa: cud 스냅샷 저장, 변경감지 X 성능향상 효과
CQRS - Command 서비스와 Query 서비스를 분리하자
-> 쓰기 전용 데이터베이스 읽기 전용 데이터 베이스가 있을 때 해당 어노테이션의 값을 보고 데이터 베이스를 고를 수 있다.
장애 격리가 가능하다
 */

/*
MockMvc -> WebMvcTest 어노테이션이 있어야 사용가능하다.
 */

/*
validation 라이브러리
NotNull -> null이 아닌것 "", "   " 가능
NotBlank -> null, "", "   " 둘다 불가능
NotEmpty -> "     " 가능

스트링 값에 문자열 제한을 둘경우
ex) @Max(20)
-> 근데 이것을 여기서 검증하는 것이 맞나? 고민해봐야 함
 */

//6일차
/*
Mock을 마주하는 자세

Mail 전송하는 로직에는 Transaction을 붙여주지 않는게 좋다.
트랜잭션을 가지고 디비 조회를 하고 할때 디비 커넥션을 계속 들고 있는데
메일 전송 같이 네트워크를 오래쓰는 로직의 경우 커넥션을 돌려주지 못해서 계속 잡고있는 것 보단
트랜잭션에 참여하지 않는게 좋다.
 */

/*
Test Double
- Dummy: 아무것도 하지 않는 깡통 객체
- Fake: 단순한 형태로 동일한 기능을 수행하나, 프로덕션에 사용하기엔 부족한 객체
- Stub: 테스트에서 요청한 것에 대해 미리 준비한 결과를 제공하는 객체 정의하지 않은 요청에는 응답하지 않는다.
- Spy: Stub이면서 호출된 내용을 기록하여 보여주는 객체, 일부는 실제 객체처럼 동작시키고 일부만 Stubbing할 수 있다.
- Mock: 행위에 대한 기대를 명세하고, 그에 따라 동작하도록 만들어진 객체

Stub과 Mock의 차이는
Stub의 목적은 상태를 검증 ex) get으로 상태를 검증해본다던가 내부적으로 상태가 어떻게 변했는지
Mock은 행위를 검증한다. ex) 이 메서드가 어떤 것을 했을 때 어떤 값을 리턴할 것이다하는 행위
 */

/*
순수 Mockito로 검증해보기

@Spy
-> 실제 객체를 기반으로 만들어진다.
-> 클래스 내부에 많은 로직, 메서드가 있는 경우 일부의 부분을 스터빙을 하고싶고, 일부분을 실제 메서드로 사용하고 싶을 경우 사용할 수 있다.
 */

/*
BDD Mockito
-> Mockito를 상속받아 사용하고 모든 동작이 동일한데, BDD 스타일로 given when then 에 맞춰 사용할 수 있도록 만들었을 뿐이다.
 */

/*
Mockist - 모든걸 Mocking 처리하자, 잘라서 간단하게 테스트하자
Classicist - Mocking을 다 해버리면 어떡하냐 실제 프로덕션 코드들이 동작할때 실제 객체들이 협업할건데 어떻게 보장할 것이냐
ㄴ> 실제 프로덕션 코드에서 런타임 시점에 일어날 일을 정확하게 Stubbing 했다고 단언 할 수 있는가? 이럴바엔 통합테스트 좀 더 넓은 범위를 테스트 하는것이 더 좋은거 같다.
ㄴ> 강사님의 생각
 */
