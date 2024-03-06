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