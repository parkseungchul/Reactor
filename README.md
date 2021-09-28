### reactor001
  - Food (이름, 배달상태)
  - FoodServer 한개 배달, 여러개 배달, 여러개 배달 안내 서비스
  - FoodMake1 일반적으로 만들기
  - TestFoodService001 구독하기 전까지는 아무일도 일어나지 않음
  - webflux 에서는 구독을 자동으로 해줌
  - FoodMake2 1초마다 음식 생성
  - FoodRestController 생성
  - http://localhost:8080  ~ 테스트
  - Html 테스트
  
### reactor002
  - 빌드 환경 구성
  - domain 구성 
    - Item 상품 (DB)
    - Cart 장바구니 (DB)
    - CartItem 상품과 갯수 (VO)
  - RepositoryTest 만들기
    - StepVerifier 스탭베이파이어
  - Service 만들기
  - ServiceTest 만들기  
  - Controller 만들기
  - ControllerTest 만들기

<pre><code>
# 전체 조회
curl --header "Content-Type: application/json" \
--request GET \
http://localhost:8080/api/items | jq

# 단건 조회
curl --header "Content-Type: application/json" \
--request GET \
http://localhost:8080/api/item/${id} | jq

# 신규 저장
curl --header "Content-Type: application/json" \
--request POST \
--data '{"name":"testName","description":"testDescription", "price":100.0}' \
http://localhost:8080/api/item | jq

업데이트 
curl --header "Content-Type: application/json" \
--request PUT \
--data '{"name":"upName","description":"upDescription", "price":99.0}' \
http://localhost:8080/api/item/60dbfe0694e8ce325727d058 | jq

삭제 
curl --header "Content-Type: application/json" \
--request DELETE \
http://localhost:8080/api/item/60dbff2a350d92192eb94963 | jq


</code></pre>