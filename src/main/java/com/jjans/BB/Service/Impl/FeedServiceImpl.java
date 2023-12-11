package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Dto.HashTagDto;
import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Entity.HashTag;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Repository.FeedRepository;
import com.jjans.BB.Repository.HashtagRepository;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.FeedService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Service
public class FeedServiceImpl implements FeedService {
    private static final Logger log = LogManager.getLogger(FeedServiceImpl.class);
    private final FeedRepository feedRepository;
    private final UsersRepository usersRepository;
    private final HashtagRepository hashtagRepository;


    @Value("${image.upload.directory}")
    private String imageUploadDirectory;


    @Autowired
    public FeedServiceImpl(FeedRepository feedRepository, UsersRepository usersRepository, HashtagRepository hashtagRepository) {
        this.feedRepository = feedRepository;
        this.usersRepository = usersRepository;
        this.hashtagRepository = hashtagRepository;
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

        String imageFileUrl = null;
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                imageFileUrl = saveImage(imageFile);

            }
        } catch (IOException e) {
            e.printStackTrace();
            // 이미지 저장에 실패한 경우 예외 처리
            throw new RuntimeException("Failed to save image.");
        }

        // 피드 엔터티 생성 및 저장
        Feed feed = feedRequestDto.toEntity();
        feed.setFeedImageUrl(imageFileUrl);
        feed.setUser(user);
        Feed savedFeed = feedRepository.save(feed);


        return new FeedResponseDto(savedFeed);
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
    public void deleteFeed(Long feedId) {
        feedRepository.deleteById(feedId);
    }
    // 이미지 저장 로직
    private String saveImage(MultipartFile imageFile) throws IOException {
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 파일 확장자 추출

        // UUID를 사용하여 고유한 파일 이름 생성
        String fileName = UUID.randomUUID().toString() + fileExtension;
        String filePath = imageUploadDirectory + File.separator + fileName;

        File dest = new File(filePath);
        imageFile.transferTo(dest);

        return fileName;
    }

    // 이미지 불러오기 로직
    public String getImagePath(String fileName) {
        return imageUploadDirectory + File.separator + fileName;
    }

    @Override
    public List<HashTagDto> getAllHashTags() {
        return null;
    }

    @Override
    public HashTagDto getHashTagByName(String tagName) {
        return null;
    }

    @Override
    public HashTagDto createHashTag(HashTagDto hashTagDto) {
        return null;
    }

    @Override
    public HashTagDto updateHashTag(Long id, HashTagDto updatedHashTagDto) {
        return null;
    }

    @Override
    public void deleteHashTag(Long id) {

    }

    @Override
    public Feed saveHashtag(Feed feed) {
        // 해시태그 추출
        Set<HashTag> hashTags = extractHashTags(feed.getContent());

        // DB에 이미 있는 해시태그를 사용하도록 설정
        Set<HashTag> existingHashTags = new HashSet<>();
        for (HashTag hashTag : hashTags) {
            hashtagRepository.findByTagName(hashTag.getTagName())
                    .ifPresent(existingHashTags::add);
        }
        hashTags.removeAll(existingHashTags);
        hashTags.addAll(existingHashTags);

        feed.setHashTags(hashTags);

        return feedRepository.save(feed);
    }
    @Override
    public Set<HashTag> extractHashTags(String content) {
        // 정규식을 사용하여 내용에서 해시태그 추출
        // 예: #Java #SpringBoot
        // 정규식 패턴은 적절하게 변경 가능
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(content);

        Set<HashTag> hashTags = new HashSet<>();
        while (matcher.find()) {
            String tagName = matcher.group().substring(1); // # 제외
            hashTags.add(new HashTag(tagName));
        }

        return hashTags;
    }


}
