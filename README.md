# Mews
![mews2](https://user-images.githubusercontent.com/76556999/224526483-49968707-ea9f-4680-b45f-229eae680df5.png)

## 프로젝트 설명
동국대학교 경영정보학과 동아리 Mews의 자체 홈페이지로, 학과 소식과 동아리 내 필진들이 기사를 작성 및 큐레이션 제작을 하여 제공하는 서비스입니다.

## 기술 스택
- **Springboot(Java)** 를 이용한 API 서버를 개발하였고, **Spring data JPA(ORM)** 와 **MySQL**을 이용한 CRUD를 구현하였습니다.  
- **AWS EC2** 를 이용해 서버를 배포하였고, **AWS RDS**를 이용해 DB 서버를 구성하였습니다.  
그리고 **AWS S3**를 이용해 이미지를 저장하였습니다.  
- **Redis** 를 활용해 유저 토큰 정보를 저장하였고, 검색어 데이터를 관리하였습니다.
- **Docker**를 활용해 API 서버를 컨테이너화하여 구축하였고, **Github Actions**를 활용해 서버 배포를 자동화하였습니다. 
- **Swagger**를 활용해 API를 문서화하였고, API 테스트를 위해 **Postman**를 사용하였습니다.
- GlobalExceptionHandler 내 @ExpectionHandler, @RestControllerAdvice를 통해 **전역적 에러 처리를 구현**하였습니다.

## 개발 내용
### 1. 회원

- 회원 가입 및 로그인/로그아웃
- 소셜 로그인
- 토큰 발급 및 검증
- 회원 탈퇴
- 프로필 조회 및 편집
- 북마크, 좋아요
- 필진 구독 및 조회

### 2. 메인 페이지

- 메인 페이지 뉴스 관리
- 조회수 TOP 5
- 유저 구독 뉴스 조회

### 3. 뉴스

- 뉴스 조회 / 작성 / 수정 / 삭제
- 페이지네이션
- 카테고리 관리
- 댓글 기능
- 북마크, 좋아요
- 조회수 관리

### 4. 파일

- 뉴스 사진 및 파일 관리

### 5. 학사 일정

- 일정 조회 / 작성 / 수정 / 삭제
- 기간 별 조회

### 6. 검색어

- 필진 및 뉴스 검색
- 인기검색어

### 7. 큐레이션

- 큐레이션 조회 / 작성 / 수정 / 삭제

### 8. 필진

- 필진 조회 / 작성 / 수정 / 삭제
- 뉴스 & 필진 연동

## ERD
![MewsERD](https://user-images.githubusercontent.com/76556999/224526366-9c1a94bc-1a3b-415b-aae5-d97378d437f4.png)


## 시스템 아키텍처
![image](https://user-images.githubusercontent.com/43109589/224528978-20cadd7a-55c8-433f-8763-99577628b43b.png)



## Pacakge View
![act drawio](https://user-images.githubusercontent.com/76556999/224527310-4cc7a3c8-d040-4c7a-a243-ad72c8be4c90.png)



## CI/CD Workflow
  


## Github Flow
![github_flow](https://user-images.githubusercontent.com/76556999/224526506-f2e6770e-7197-46ac-b482-433148e8b49c.png)

  

## Commit Convention
### Structure
[FEAT] 필진 등록 시 이미지 업로드 API #14

### Type
- FEAT : 기능 구현 시
- FIX : 구현된 기능 관련 오류 수정 시
- STYLE : 코드 포맷 변경
- CHORE : 환경 설정 시
- DOCS : 문서 작업 시


### How to use
1. Commit Type 명시
2. 관련 Issue 번호 명시
3. 개발 내용 명료하게 서술
