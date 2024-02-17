
# BeatBuddy :<br/>플레이리스트 공유  SNS 플랫폼

# 최종 정리

BeatBuddy :   SNS


### 담당 기능
      
  - 이성철
      - 게시글(Feed/Playlist/Comment), 팔로잉, 북마크 CRUD 구현(80%)
      - Docker, GitAction 활용한 CI/CD (100%)
      - Websocket(STOMP)/Kafka 활용한 채팅 서비스 구축(100%)
      - Docker-compose 작성(50%)
      - S3 유저 이미지 업로드(100%)
  - 장수현
      - User 회원가입 로그인, Google 로그인 기능 구현(100%)
      - ELK 구현 (100%)
      - 게시글(Feed/Playlist/Comment), 팔로잉, 북마크 CRUD 구현(80%)
      - 서치 컨트롤러 구현 (100%)
      - Docker-compose 작성(50%)
    

### 아키텍처 (서버)   
  <img width="1267" alt="Untitled" src="https://github.com/ay30n591/BB_Backend/assets/59824783/d15a38e1-1469-4a11-a5c8-be50e2fb216f">
    

### 협업 툴
  - Notion, Discord, GitHub

### erd
  <img width="688" alt="Untitled 1" src="https://github.com/ay30n591/BB_Backend/assets/59824783/7052a219-2b80-4422-80fc-371dc49a6f1d">

###  API
- UsersController

  <img width="1416" alt="Untitled 2" src="https://github.com/ay30n591/BB_Backend/assets/59824783/df9f5228-2161-4225-a1db-2f1a8fd1999b">
    
- FeedController    
  <img width="1425" alt="Untitled 3" src="https://github.com/ay30n591/BB_Backend/assets/59824783/605d4142-b6ef-4728-b590-4297e6140753">
  <img width="1423" alt="Untitled 4" src="https://github.com/ay30n591/BB_Backend/assets/59824783/db756898-c1bf-41dc-a7a1-1572e252ba0e">
    
- PlaylistController  
  <img width="1423" alt="Untitled 5" src="https://github.com/ay30n591/BB_Backend/assets/59824783/00f3d451-2c1f-4db5-abd1-0fb8ea2872f9">
    
- CommentController
  <img width="1422" alt="Untitled 6" src="https://github.com/ay30n591/BB_Backend/assets/59824783/c133ef47-ad78-46b9-8b45-fb8b72b50798">
    
- SearchController
  <img width="1417" alt="Untitled 7" src="https://github.com/ay30n591/BB_Backend/assets/59824783/5afe7ef9-dc69-4d1f-aa41-3300f5afd097">
    
- FollowController
  <img width="1418" alt="Untitled 8" src="https://github.com/ay30n591/BB_Backend/assets/59824783/0d2b7779-e119-418e-8228-538ad7312c71">
    
- ChatController
  <img width="1415" alt="Untitled 9" src="https://github.com/ay30n591/BB_Backend/assets/59824783/61699173-76f8-4ffc-9fae-7f0becf13fd7">


    
    
### 트러블 슈팅
  - 백엔드
      - 구글 로그인 → 로그인 시 백이랑 연결이 안되는 문제
      - ci/cd → docker 쪽 자동화 안되는 현상
      - S3 이미지 업로드시에 S3에 있는 기존 이미지 삭제 후 업로드



### 개선사항 / 업그레이드 (2024.02.xx)
  - 로그스태시 → 엘라스틱 서치 :  mysql rdb 모든 컬럼 전부 입력.
      - 로그스태시 pipeline 안에서 mysql 테이블 필터링. / 엘라스틱서치 쿼리 or 성능 개선
  - 알림 기능
  - 대댓글


