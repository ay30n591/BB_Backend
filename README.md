
# BeatBuddy :<br/>플레이리스트 공유  SNS 플랫폼

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

### ERD
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
### 회고 및 후기
- 성철
    - **Keep** (좋은 결과를 만들었고, 계속 유지해나가야 할 것)
        - 디비 설계를 충분히 고민을 해봤던 것이 좋았음. 그러나 최적의 구조인지 스스로 자신할 수 없기 때문에 여러 구조를 설계해보면서 디비 설계의 지식을 늘려야할 것이다.
        - 코드를 구현하는 것외에도 프로젝트 환경을 고려하고, 현재 상황에서 사용할만한 기술들을 알아보고 선택하는 과정에서의 경험이 좋았음. 추가적으로 기존에 사용해보고 싶었던 기능(S3 활용 , Kafka, ELK)등을 활용할 수 있는 기회가 되었음
        - HTTP 통신과 Socket 통신에 대한 지식 습득을 하여 좋았음. 해당 통신외의 다른 여러 통신에 대한 공부를 진행할 것이다.
        - 팀원들과 원활한 커뮤니케이션을 통해 프로젝트가 유연하게 진행된 점이 좋았다.
    - **Problem** (아쉬운 결과를 만들었고, 앞으로 개선되어야 할 것)
        - 프로젝트 마무리 단계에서 새로운 지식을 습득하면서, 기능별로 서버를 분리하는 MSA를 고려해보지 않고 Monolitc한 구조로 구현을 시작한 것이 아쉬움. 코드 레벨의 공부 뿐만 아니라, 아키텍처를 짜고, 프로젝트의 전체 구조에 대한 생각을 해야할 것이다.
        - 테스트 코드를 활용하기 보단 Swagger를 활용하여 직접적으로 데이터 삽입하여 기능을 검토한 것이 아쉬움.
        - Git을 사용하여 협업을 했으나, 유연하게 활용하지 못했다는 점이 아쉬움. (issue, project)
    - **Try** (문제를 파악하고, 이를 해결하기 위한 구체적인 개선방안)
        - 다른 이들이나 기업의 기술 블로그를 살펴보면서, 아키텍처를 어떻게 설계하는지 참고할 것
        - git 활용에 대한 엄격한 수칙을 생성 후 , 해당 수칙에 맞게 깃을 활용해 볼 것. (pr)
        - 테스트 코드 작성법에 대한 공부 후 습관화 시킬 것.
- 수현
    - **Keep** (좋은 결과를 만들었고, 계속 유지해나가야 할 것)
        - 팀원간의 원활한 커뮤니케이션과 백엔드, 프론트엔드 협업을 유지한 점이 좋았다. 팀원들 간의 의사소통을 계속 강화하여 프로젝트의 효율성과 품질을 유지해야 할 것이다.
        - 새로운 기술 스택을 빠르게 학습하고 프로젝트에 적용함으로써 기술적으로 성장할 수 있었다.
    - **Problem** (아쉬운 결과를 만들었고, 앞으로 개선되어야 할 것)
        - 미흡한 기록 남기기 ( 노션 및 git issues )
        - 테스트 코드를 충분히 활용하지 못한 점
        - 기능별로 코드를 구분하여 작성하지 못한 점 ( user, feed 등 기능별로 project 분리)
    - **Try** (문제를 파악하고, 이를 해결하기 위한 구체적인 개선방안)
        - 코드 리뷰 및 테스트 커버리지를 강화하여 버그를 최소화하고 안정적인 서비스를 제공
        - 코드 리뷰 프로세스를 보다 엄격하게 수행하고, 테스트 케이스를 보다 철저하게 작성하여 코드 품질 향상

