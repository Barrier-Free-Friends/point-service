# Point Service

Barrier Free Friends MSA의 포인트 서비스입니다. 제보 활동에 대한 포인트 적립/사용, 뱃지, 리워드(교환)를 관리합니다.

전체 서비스 구성은 [조직 README](https://github.com/Barrier-Free-Friends)를 참고하세요.

## 기술 스택
- Java 21, Spring Boot 3.5.8 (JPA, QueryDSL)
- PostgreSQL, Spring Batch, Caffeine 캐시
- Kafka (제보 이벤트 소비)
- AWS S3 (뱃지 이미지)
- Resilience4j, Eureka Client, Config Client

## 주요 구조
- `application/command`, `application/query` — Badge/Point/Reward 유스케이스
- `application/event` — `ReportEventHandler`, `IdempotencyService` (report-service 이벤트 소비)
- `domain/entity` — badge, point, reward 하위 패키지
- `domain/service` — PointGainService, PointUseService, BadgePolicyService 등

## 실행

```bash
./gradlew bootRun
```

기본 포트: `4000`

필요 환경변수: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, AWS 자격증명

## Docker

```bash
docker build -t point-service .
docker run -p 4000:4000 point-service
```
