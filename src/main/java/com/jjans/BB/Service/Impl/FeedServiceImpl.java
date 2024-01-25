package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Utill.S3Uploader;
import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Dto.PlaylistResponseDto;
import com.jjans.BB.Entity.*;
import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Repository.FeedRepository;
import com.jjans.BB.Repository.HashTagRepository;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.FeedService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private S3Uploader s3Uploader;
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger log = LogManager.getLogger(FeedServiceImpl.class);
    private final FeedRepository feedRepository;
    private final UsersRepository usersRepository;

    private HashTagRepository hashTagRepository;



    @Autowired
    public FeedServiceImpl(FeedRepository feedRepository, UsersRepository usersRepository, HashTagRepository hashTagRepository) {
        this.feedRepository = feedRepository;
        this.usersRepository = usersRepository;
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    public List<FeedResponseDto> getAllFeeds(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        List<FeedResponseDto> feedResponseDtos = new ArrayList<>();

        Page<Feed> feeds = feedRepository.findAll(pageable);

        for (Feed feed : feeds.getContent()) {
            List<Users> likedUsers = feed.getLikedUsers();
            boolean userLiked = likedUsers.stream()
                    .anyMatch(likedUser -> likedUser.getId() == user.getId());

            FeedResponseDto feedResponseDto = new FeedResponseDto(feed);
            feedResponseDto.setLikeCheck(userLiked);
            feedResponseDtos.add(feedResponseDto);
        }


        return feedResponseDtos;
    }

    @Override
    @Transactional
    public FeedResponseDto saveFeed(FeedRequestDto feedRequestDto, MultipartFile imageFile) {
        // 현재 사용자 정보 가져오기
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        // 이미지 s3 업로드
        String imageFileUrl = null;
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                imageFileUrl = s3Uploader.upload(imageFile,"feed-image");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 이미지 저장에 실패한 경우 예외 처리
            throw new RuntimeException("Failed to save image.");
        }


        Set<HashTag> hashTags = new HashSet<>();
        for (HashTag tag : feedRequestDto.getHashTags()) {
            HashTag existingHashTag = hashTagRepository.findByTagName(tag.getTagName());
            if (existingHashTag == null) {
                // 데이터베이스에 HashTag가 존재하지 않으면 새로 생성하고 저장
                HashTag newHashTag = new HashTag(tag.getTagName());
                entityManager.persist(newHashTag);
                hashTags.add(newHashTag);
            } else {
                // 데이터베이스에 이미 존재하는 경우 기존 것을 사용
                hashTags.add(existingHashTag);
            }
        }

        MusicInfo musicInfo = feedRequestDto.getMusicInfo();

        Feed feed = feedRequestDto.toEntity();

        feed.setMusicInfo(musicInfo);
        feed.setHashTags(hashTags);
        feed.setImgSrc(imageFileUrl);
        feed.setUser(user);
        entityManager.persist(feed);

        return new FeedResponseDto(feed);
    }



    @Override
    public FeedResponseDto updateFeed(Long feedId, FeedRequestDto updatedFeedDto) {
        Feed existingFeed = feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("Feed not found with id: " + feedId));

        existingFeed.setContent(updatedFeedDto.getContent());

        Feed updatedFeed = feedRepository.save(existingFeed);

        return new FeedResponseDto(updatedFeed);
    }

    @Override
    public List<FeedResponseDto> getUserAllFeeds(String nickname,int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Users user = usersRepository.findByNickName(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Page<Feed> feeds = feedRepository.findByUserNickName(nickname, pageable);

        return feeds.getContent().stream().map(FeedResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public FeedResponseDto getUserFeed(Long feed_id, String nickname) {
        Users user = usersRepository.findByNickName(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Feed feed = feedRepository.findByIdAndUserNickName(feed_id,nickname);
        return new FeedResponseDto(feed);
    }

    @Override
    public List<FeedResponseDto> getMyFeeds(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Page<Feed> feeds = feedRepository.findByUserNickName(user.getNickName(), pageable);
        return feeds.getContent().stream().map(FeedResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public FeedResponseDto getMyFeed(Long feed_id) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));


        Feed feed = feedRepository.findByIdAndUserNickName(feed_id, user.getNickName());

        return new FeedResponseDto(feed);
    }

    @Override
    public void deleteFeed(Long feedId) {
        feedRepository.deleteById(feedId);
    }

    @Override
    public List<FeedResponseDto> findFeedsByTagName(String tagName,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Feed> feeds =  feedRepository.findByHashTags_TagName(tagName, pageable);
        return feeds.getContent().stream().map(FeedResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public void likeFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new EntityNotFoundException("피드 ID 찾을 수 없음: " + feedId));
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        ArticleLike articleLike = new ArticleLike();
        articleLike.setUser(user);
        articleLike.setArticle(feed);

        feed.addArticleLike(articleLike);

        feedRepository.save(feed);

    }

    @Override
    public void unlikeFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new EntityNotFoundException("피드 ID 찾을 수 없음: " + feedId));
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        // Find the existing ArticleLike associated with the user and the feed
        ArticleLike existingLike = feed.getLikes().stream()
                .filter(like -> like.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자가 이 피드를 좋아하지 않았습니다."));

        // Remove the ArticleLike from the Feed's list
        feed.removeArticleLike(existingLike);

        // Save the updated Feed
        feedRepository.save(feed);
    }

    @Override
    public void bookmarkFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new EntityNotFoundException("피드 ID를 찾을 수 없음: " + feedId));

        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("인증 정보 없음."));

        BookMark bookMark = new BookMark();
        bookMark.setUser(user);
        bookMark.setArticle(feed);

        feed.addBookmark(bookMark);
        feedRepository.save(feed);

    }
    @Override
    public void unbookmarkFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new EntityNotFoundException("피드 ID를 찾을 수 없음: " + feedId));
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("인증 정보 없음."));

        BookMark existingBookmark = feed.getBookMarks().stream()
                .filter(like -> like.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자가 이 피드를 좋아하지 않았습니다."));

        // Remove the ArticleLike from the Feed's list
        feed.removeBookmark(existingBookmark);

        // Save the updated Feed
        feedRepository.save(feed);
    }
    @Override
    public List<FeedResponseDto> getBookmarkedFeeds(int page, int size) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Pageable pageable = PageRequest.of(page, size);

        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Page<Feed> bookmarkedFeeds = feedRepository.findByBookMarks_User(user,pageable);
        return bookmarkedFeeds.getContent().stream().map(FeedResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<FeedResponseDto> getFeedsOfFollowing(int page, int size) {

        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Set<UserFollower> followers = user.getFollowing();
        System.out.println(followers);

        Set<Users> users =  new HashSet<>();

        for (UserFollower userFollower : followers) {
            if (userFollower.getFollower() != null) {
                users.add(userFollower.getFollower());
            }
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createDate")));

        Page<Feed> feedsOfMultipleUsers = feedRepository.findByUserIn(users, pageable);


        return feedsOfMultipleUsers.getContent().stream()
                .map(FeedResponseDto::new)
                .collect(Collectors.toList());    }
}