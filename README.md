# Commerce_Order_API
## 1. 개요
### e-commerce 서비스 도메인 개발
  1) 유저 : 서비스를 통해 상품 선택 주문 고객
  2) 파트너 : 서비스에 입점하여 상품을 판매하는 업체
  3) 내부 운영자 : 해당 서비스 운영 및 관리 담당자

## 2. 주요 도메인
  1) 파트너 : 파트너 등록과 운영 처리
  2) 상품 : 상품과 상품의 옵션 정보를 등록하고 관리함
  3) 주문 : 유저가 선택한 상품정보와 주문정보를 관리 및 결제 처리

## 3. 주요 도메인 상세 요구사항
### 1. 파트너
    - 시스템에 등록된 파트너만 상품을 등록하고 주문을 처리한다
    - 파트너 등록시 파트너명, 사업자 등록번호, 이메일이 필수값이다
    - 파트너는 계약이 종료되면 비활성 상태로 전환된다 (정보 삭제 x)
    - 파트너 등록 성공시 등록된 이메일로 가입 완료 안내 메일을 발송한다
    - 그 외 시스템을 사용하는 유저가 기본적으로 기대하는 조회, 등록, 수정, 삭제 등의 기능을 제공해야한다.

### 2. 상품
    - 시스템에 등록되고 활성화된 파트너는 상품을 등록할 수 있다.
    - 등록된 상품은 유저의 주문을 받아 판매될 수 있다.
    - 상품은 상품명 가격 등의 기본 정보와 색상, 사이즈와 같은 옵션으로 구성된다.
    - 상품은 옵션 정보 없이 기본값으로만 저장될 수도 있다.
    - 주문 화면에서 보여지는 상품의 옵션은 파트너사가 원하는 순서에 맞게 노출될 수 있다.
    - 상품 구매 시 특정한 옵션을 선택하면 가격이 추가될 수 있다.
    - 상품은 판매 준비중, 판매중, 판매종료와 같은 상태를 가진다.
    - 그 외 시스템을 사용하는 유저가 기본적으로 기대하는 조회, 등록, 수정, 삭제 등의 기능을 제공해야한다.
    - 수량, 품절상태 등

### 3. 주문
    - 시스템에 등록된 상품은 유저가 주문할 수 있다.
    - 주문은 주문등록, 결제, 배송준비, 배송중, 배송완료의 단계를 가진다.
    - 주문 등록 과정에서는 결제 수단을 선택하고 상품 및 상품 옵션을 선택한다.
    - 시스템에서 사용 가능한 결제 수단은 카드, 토스페이, 카카오페이, 네이버페이 등이 있다.
    - 결제 과정에서는 유저가 선택한 결제 수단으로 결제를 진행한다.
    - 결제 완료 후 유저에게 카카오톡으로 주문 성공 알림이 전달된다.
    - 결제가 완료되면 배송준비 단계로 넘어간다.
    - 배송중, 배송 완료의 단계도 순차적으로 진행된다.
    - 결제 연동, 취소 등


----------------------------------
1) Partner : 파트너
    - 상품, 주문과 관련해서 1 : N 관계
    - 필수 속성 (partnerName, businessNo, email)
    - 필수 메서드 (파트너 상태 활성화 / 비활성화)
----------------------------------
1) Item : 상품
2) ItemOptionGruop : 상품 관련 설정 분류
3) ItemOption : 실제 상품 관련 설정
    - 각 아래 도메인에 대해 1:N 관계
----------------------------------
1) Order : 주문
2) OrderItem : 주문의 특정 상품
3) OrderItemOptionGroup : 특정 상품 옵션 관련 분류
4) OrderItemOption : 특정 상품 세부 옵션
    - 각 아래 도메인에 대해 1:N 관계
----------------------------------




## 4. PartnerService 정의
    - 파트너 등록
    - 파트너 정보 조회
    - 파트너 활성화
    - 파트너 비활성화

## 5. PartnerService의 request와 response 정의
    - Command : CUD
    - Criteria : R (조회 기능)
    - Info(리턴 객체) // 필요에 따라 Entity의 일부 속성 가공 가능


## 6. HTTP API 요청과 응답 표준
1. 성공응답
```json
{
  "result" : "SUCCESS",
  "data" : "plain text 또는 json 데이터",
  "message" : "성공 메시지",
  "error_code" : null
}
```

2. 실패응답
```json
{
  "result" : "FAIL",
  "data" : "null 또는 json 데이터",
  "message" : "에러 메시지",
  "error_code" : "plain text"
}
```
    - 비즈니스적인 에러상황과 개발단계에서 충분히 예상 및 지정한 에러(HttpStatus.2xx)
    - 잘못된 파라미터 등 .. (HttpStatus.4xx)
    - 요청에 대한 응답을 서버가 받아내지 못했거나 지정하지 않은 에러(HttpStatus.5xx) → 모니링 대상


* 도메인 API 구현
    - 동기식 http api 구현
    - content-type = application/json
    - API endpoint = /api/{version}/partners 의 패턴을 PREFIX로 한다.

* 시스템 간 일관된 응답 체계 구축
    - BaseException : 커스텀 예외 관련 기본 형태 (명시적 예외의 기본 구조)
    - CommonResponse : 응답 관련 기본 형태
    - CommonControllerAdvice : 컨트롤러 인터셉터

* 중앙화된 로그 시스템 구축
    - API 의 request, response 로그 체크
