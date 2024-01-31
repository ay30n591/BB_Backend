# 최종 정리

BeatBuddy :   SNS

---

- 문서 (메인)
    1. 프로젝트 소개(배경) + 기간
       
       기간 :  2023/11/01 ~ 2024/01/31
        
        
    
    1. 팀원 소개 / 기능 정리
        - 프론트엔드
            - 이승윤
                - 
            - 이경주
                - 
        - 백엔드
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
    
    1. 기술 스택 / 아키텍처 (서버)
    
    서버
    
    ![Untitled](%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5%202b7ddfab8b84482ab43ac2046270afe6/Untitled.png)
    

협업 툴

- Notion, Discord, GitHub

- erd

![Untitled](%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5%202b7ddfab8b84482ab43ac2046270afe6/Untitled%201.png)

- API
- UsersController
    
    ![Untitled](%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5%202b7ddfab8b84482ab43ac2046270afe6/Untitled%202.png)
    
- FeedController
    
    ![Untitled](%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5%202b7ddfab8b84482ab43ac2046270afe6/Untitled%203.png)
    
    ![Untitled](%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5%202b7ddfab8b84482ab43ac2046270afe6/Untitled%204.png)
    
- PlaylistController
    
    ![Untitled](%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5%202b7ddfab8b84482ab43ac2046270afe6/Untitled%205.png)
    
- CommentController
    
    ![Untitled](%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5%202b7ddfab8b84482ab43ac2046270afe6/Untitled%206.png)
    
- SearchController
    
    ![Untitled](%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5%202b7ddfab8b84482ab43ac2046270afe6/Untitled%207.png)
    
- FollowController
    
    ![Untitled](%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5%202b7ddfab8b84482ab43ac2046270afe6/Untitled%208.png)
    
- ChatController
    
    ![Untitled](%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5%202b7ddfab8b84482ab43ac2046270afe6/Untitled%209.png)
    

### 회고 및 후기
    - 이승윤
    - 이경주
    - 이성철
    - 장수현
    
### 트러블 슈팅
    - 프론트엔드
        - 
    - 백엔드
        - 구글 로그인 → 로그인 시 백이랑 연결이 안되는 문제
        - ci/cd → docker 쪽 자동화 안되는 현상
        - S3 이미지 업로드시에 S3에 있는 기존 이미지 삭제 후 업로드

### 개선사항 / 업그레이드 (2024.02.xx)

- 프론트엔드

- 백엔드
    - 로그스태시 → 엘라스틱 서치 :  mysql rdb 모든 컬럼 전부 입력.
        - 로그스태시 pipeline 안에서 mysql 테이블 필터링. / 엘라스틱서치 쿼리 or 성능 개선
    - 알림 기능
    - 대댓글

