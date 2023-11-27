package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Repository.FeedRepository;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public FeedServiceImpl(FeedRepository feedRepository, UsersRepository usersRepository) {
        this.feedRepository = feedRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public List<FeedResponseDto> getAllFeeds() {
        List<Feed> feeds = feedRepository.findAll();
        return feeds.stream().map(FeedResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public FeedResponseDto saveFeed(FeedRequestDto feedDto) {
        // 피드 엔티티 생성
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Feed feed = feedDto.toEntity();
        feed.setUser(user);
        Feed savedFeed = feedRepository.save(feed);

        return new FeedResponseDto(savedFeed);
    }

    @Override
    public FeedResponseDto updateFeed(Long feedId, FeedRequestDto updatedFeedDto) {
        Feed existingFeed = feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("Feed not found with id: " + feedId));

        existingFeed.setContent(updatedFeedDto.getContent());
        existingFeed.setFeedImage(updatedFeedDto.getFeedImage());

        Feed updatedFeed = feedRepository.save(existingFeed);

        return new FeedResponseDto(updatedFeed);
    }

    @Override
    public List<FeedResponseDto> getUserFeeds(String nickname) {
        Users user = usersRepository.findByNickName(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        List<Feed> feeds = feedRepository.findByUserNickName(nickname);
        return feeds.stream().map(FeedResponseDto::new).collect(Collectors.toList());
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
}
