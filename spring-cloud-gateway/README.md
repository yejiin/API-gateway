# Spring Cloud GateWay

📜 참고: [Spring Cloud Gateway 공식 문서](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#gateway-starter)

- Spring Reactive 생태계 위에 구현된 API Gateway
- 논블로킹(non-blocking), 비동기(Asynchronous) 방식의 Netty Server
- 라우트(Route) rule 설정, 필터(Filters) 구현 함으로써 Customizing 가능한 API gateway

## 용어
- **Route**: gateway 의 기본 빌딩 블록. ID, URI, Predicate 모음, Filter 모음으로 정의
- **Predicate**
- **Filter**

## 작동 원리
![](https://user-images.githubusercontent.com/63090006/189785598-1051f040-24e2-41ec-b9b9-85cf7e22678c.png)

## Route Predicate Factories
`After`  
`Before`  
`Between`  
`Cookie`  
`Header`  
`Host`  
`Method`  
`Path`  
`Query`  
`RemoteAddr`  
`Weight`  
`XForwardedRemoteAddr`

```yaml
spring:
	cloud:
		gateway:
			routes:
			- id: after_route
				uri: https://example.org
				predicates:
				- After=2017-01-20T17:42:47.789-07:00[America/Denver]
```


## GatewayFilter Factories
HTTP 요청, HTTP 응답 수정


## GlobalFilter
모든 경로에 조건부로 적용되는 특수 필터