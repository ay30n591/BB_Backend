package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Utill.S3Uploader;
import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Dto.PlaylistRequestDto;
import com.jjans.BB.Dto.PlaylistResponseDto;
import com.jjans.BB.Entity.*;
import com.jjans.BB.Repository.HashTagRepository;
import com.jjans.BB.Repository.PlaylistRepository;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.PlaylistService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private S3Uploader s3Uploader;
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger log = LogManager.getLogger(FeedServiceImpl.class);
    private final PlaylistRepository playlistRepository;
    private final UsersRepository usersRepository;
    private HashTagRepository hashTagRepository;


    public PlaylistServiceImpl(PlaylistRepository playlistRepository, UsersRepository usersRepository, HashTagRepository hashTagRepository) {
        this.playlistRepository = playlistRepository;
        this.usersRepository = usersRepository;
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    public List<PlaylistResponseDto> getAllPls(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Playlist> pls = playlistRepository.findAllByOrderByCreateDateDesc(pageable);
        return pls.getContent().stream().map(PlaylistResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<PlaylistResponseDto> getArticlesOrderByLikeCount(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Playlist> pls = playlistRepository.findAllOrderByLikeCountDesc(pageable);
        return pls.getContent().stream().map(PlaylistResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PlaylistResponseDto savePl(PlaylistRequestDto plDto, MultipartFile imageFile) {
        // 현재 사용자 정보 가져오기
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        String imageFileUrl = null;
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                imageFileUrl = s3Uploader.upload(imageFile,"playlist-image");

            }
        } catch (IOException e) {
            e.printStackTrace();
            // 이미지 저장에 실패한 경우 예외 처리
            throw new RuntimeException("Failed to save image.");
        }


        Set<HashTag> hashTags = new HashSet<>();
        for (HashTag tag : plDto.getHashTags()) {
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
        // 피드 엔터티 생성 및 저장
        Playlist pl = plDto.toEntity();
        pl.setMusicInfoList(plDto.getMusicInfoList());
        pl.setHashTags(hashTags);
        pl.setImgSrc(imageFileUrl);
        pl.setUser(user);
        entityManager.persist(pl);

        return new PlaylistResponseDto(pl);
    }

    @Override
    public PlaylistResponseDto updatePl(Long plId, PlaylistRequestDto updatedPlDto) {
        Playlist existingPl = playlistRepository.findById(plId)
                .orElseThrow(() -> new RuntimeException("Feed not found with id: " + plId));

        existingPl.setContent(updatedPlDto.getContent());
        //existingFeed.setFeedImage(updatedFeedDto.getFeedImage());

        Playlist updatedPl = playlistRepository.save(existingPl);

        return new PlaylistResponseDto(updatedPl);
    }

    @Override
    public List<PlaylistResponseDto> getUserAllPls(String nickname, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Users user = usersRepository.findByNickName(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Page<Playlist> pls = playlistRepository.findByUserNickName(nickname, pageable);
        return pls.getContent().stream().map(PlaylistResponseDto::new).collect(Collectors.toList());

    }

    @Override
    public PlaylistResponseDto getUserPl(Long plId, String nickname) {

        Users user = usersRepository.findByNickName(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Playlist pl = playlistRepository.findByIdAndUserNickName(plId,nickname);
        return new PlaylistResponseDto(pl);
    }

    @Override
    public PlaylistResponseDto getMyPl(Long feed_id) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Playlist pl = playlistRepository.findByIdAndUserNickName(feed_id,user.getNickName());

        return new PlaylistResponseDto(pl);
    }

    @Override
    public List<PlaylistResponseDto> getMyPls(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Page<Playlist> pls = playlistRepository.findByUserNickName(user.getNickName(),pageable);
        return pls.getContent().stream().map(PlaylistResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public void likePl(Long plId) {
        Playlist pl = playlistRepository.findById(plId)
                .orElseThrow(() -> new EntityNotFoundException("피드 ID 찾을 수 없음: " + plId));

        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        ArticleLike articleLike = new ArticleLike();
        articleLike.setUser(user);
        articleLike.setArticle(pl);

        pl.addArticleLike(articleLike);
        playlistRepository.save(pl);
    }

    @Override
    public void unlikePl(Long plId) {
        Playlist pl = playlistRepository.findById(plId)
                .orElseThrow(() -> new EntityNotFoundException("피드 ID 찾을 수 없음: " + plId));
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        // Find the existing ArticleLike associated with the user and the feed
        ArticleLike existingLike = pl.getLikes().stream()
                .filter(like -> like.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자가 이 피드를 좋아하지 않았습니다."));

        // Remove the ArticleLike from the Feed's list
        pl.removeArticleLike(existingLike);

        // Save the updated Feed
        playlistRepository.save(pl);
    }



    @Override
    public void deleteFeed(Long plId) {
        // 권한 체크 필요
        playlistRepository.deleteById(plId);
    }


}
