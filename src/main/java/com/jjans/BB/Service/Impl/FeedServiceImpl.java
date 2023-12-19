package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Utill.S3Uploader;
import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Entity.HashTag;
import com.jjans.BB.Entity.MusicInfo;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Repository.FeedRepository;
import com.jjans.BB.Repository.HashTagRepository;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.FeedService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
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

    @Value("${image.upload.directory}")
    private String imageUploadDirectory;


    @Autowired
    public FeedServiceImpl(FeedRepository feedRepository, UsersRepository usersRepository, HashTagRepository hashTagRepository) {
        this.feedRepository = feedRepository;
        this.usersRepository = usersRepository;
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    public List<FeedResponseDto> getAllFeeds() {
        List<Feed> feeds = feedRepository.findAll();
        return feeds.stream().map(FeedResponseDto::new).collect(Collectors.toList());
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
        feed.setImageUrl(imageFileUrl);
        feed.setUser(user);
        entityManager.persist(feed);

        return new FeedResponseDto(feed);
    }



    @Override
    public FeedResponseDto updateFeed(Long feedId, FeedRequestDto updatedFeedDto) {
        Feed existingFeed = feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("Feed not found with id: " + feedId));

        existingFeed.setContent(updatedFeedDto.getContent());
        //existingFeed.setFeedImage(updatedFeedDto.getFeedImage());

        Feed updatedFeed = feedRepository.save(existingFeed);

        return new FeedResponseDto(updatedFeed);
    }

    @Override
    public List<FeedResponseDto> getUserAllFeeds(String nickname) {
        Users user = usersRepository.findByNickName(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        List<Feed> feeds = feedRepository.findByUserNickName(nickname);
        return feeds.stream().map(FeedResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public FeedResponseDto getUserFeed(Long feed_id, String nickname) {
        Users user = usersRepository.findByNickName(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Feed feed = feedRepository.findByIdAndUserNickName(feed_id,nickname);
        return new FeedResponseDto(feed);
    }

    @Override
    public List<FeedResponseDto> getMyFeeds() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        List<Feed> feeds = feedRepository.findByUserNickName(user.getNickName());
        return feeds.stream().map(FeedResponseDto::new).collect(Collectors.toList());
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
    public List<FeedResponseDto> findFeedsByTagName(String tagName) {

        List<Feed> feeds =  feedRepository.findByHashTags_TagName(tagName);
        return feeds.stream().map(FeedResponseDto::new).collect(Collectors.toList());
    }

}
