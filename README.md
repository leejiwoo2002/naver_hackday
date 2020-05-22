# SNS 타임라인

### 프로젝트 주제 설명

- 인터넷 환경에서 사람들은 다양한 관계를 맺고 새로운 컨텐츠를 서로 주고 받습니다.
- 관심있는 사람들과의 소셜네트워크를 구성하여 컨텐츠를 생성/전달하고 컨텐츠를 모아 볼수 있는 타임라인을 구성합니다.
- 백엔드 시스템에 집중하여 화면 구성은 최소한으로 하고 서버 부분을 집중적으로 처리합니다.

### 개발 환경 

- Spring boot 2.2
- JAVA8
- Thymeleaf
- ElasticSearch
- MySQL
- Redis

#### 배포환경

- NCloud
- Jenkins
- Docker

### 플랫폼

- IntelliJ
- Github

#### Web : <http://49.50.172.59:8080/>
#### Swagger : <http://49.50.172.59:8080/swagger-ui.html>

## 구현 사항

### Yunsang

#### Sign (회원 관리)
- Spring security를 사용하여 권한 설정
- Request 요청 시 SecurityFilterChain 에서 권한 확인

#### Search (친구 찾기)

- 회원 정보 중 검색어와 매치되는 회원을 찾기위해 Elasticsearch 검색 엔진 사용
- 회원의 이메일, 이름을 인덱싱하여 검색 최적화

#### Subscribe (구독)

- 검색 결과로 나온 회원들을 구독 & 구독 취소

#### Deployment (배포)

- Service 배포 서버, DB 서버 운용 (Naver Cloud Server)
- Jenkins 빌드 설정으로 Git + Jenkins + Docker 연동
- jar file docker image build -> docker hub push -> docker image run
<pre><code>
docker image pull : $ docker pull yunsang/hackday
</code></pre>

#### Elasticsearch + Kibana

- Docker compose 를 사용하여 Elasticsearch + Kibana 컨테이너 생성
- 회원의 email, name text type 으로 인덱싱하여 저장
- Logstash 로 MySQL 과 동기화 예정



## 구현 사항

### Jiwoo

#### 게시글 Create

- form 태그로 입력받아 DB Insert

#### 게시글 Read

- 내가 쓴 게시글의 경우 Redis Cache 사용하여 DB 접근 없이 정보 확인
- Create, Update, Delete시 Cache 제거
- 추후에 Read 사용시 DB 접근 후 Cache 생성

#### 게시글 Update

- form 태그로 입력받아 DB Update

#### 게시글 Delete

- 삭제 버튼 클릭시 해당 게시글의 isDelete 컬럼 = true로 변경
- 실제 게시글 데이터는 DB에 남아있고, Read시 isDelete = false 인 경우만 Read

#### 타임라인

- 구독자 리스트를 받아 게시글중 작성자 id가 리스트에 존재할경우 Select
- 날짜별로 내림차순

#### 사진 저장

- 폴더를 지정하여 사진 파일을 저장
- 폴더 한곳에 사진을 모두 저장하지 않고 날짜별로 생성하여 이미지를 저장
- 게시글 Read시 작성일에 해당하는 폴더 접근하여 파일 이름이 saveName 컬럼에 해당하는 값을 가져옴

### Redis

- 내 게시글 확인시 Cache가 존재하면 DB에 접근하지 않고 Redis Cache에 접근하여 데이터 수신
- key : User name
- 게시글 생성, 수정, 삭제시 캐시 삭제 -> Read시 캐시가 없으면 캐시 생성, 캐시가 존재하면 해당 캐시 수신


