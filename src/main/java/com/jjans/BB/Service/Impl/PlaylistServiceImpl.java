package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Utill.S3Uploader;
import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Dto.PlaylistRequestDto;
import com.jjans.BB.Dto.PlaylistResponseDto;
import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.Playlist;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Repository.PlaylistRepository;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.PlaylistService;
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
import java.util.List;
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

    @Value("${image.upload.directory}")
    private String imageUploadDirectory;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, UsersRepository usersRepository) {
        this.playlistRepository = playlistRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public List<PlaylistResponseDto> getAllPls() {
        List<Playlist> pls = playlistRepository.findAll();
        return pls.stream().map(PlaylistResponseDto::new).collect(Collectors.toList());
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

        // 피드 엔터티 생성 및 저장
        Playlist pl = plDto.toEntity();
        pl.setMusicInfoList(plDto.getMusicInfoList());
        pl.setImageUrl(imageFileUrl);
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
    public List<PlaylistResponseDto> getUserAllPls(String nickname) {
        Users user = usersRepository.findByNickName(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        List<Playlist> pls = playlistRepository.findByUserNickName(nickname);
        return pls.stream().map(PlaylistResponseDto::new).collect(Collectors.toList());
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
    public List<PlaylistResponseDto> getMyPls() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        List<Playlist> pls = playlistRepository.findByUserNickName(user.getNickName());
        return pls.stream().map(PlaylistResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public void deleteFeed(Long plId) {
        // 권한 체크 필요
        playlistRepository.deleteById(plId);
    }

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
    public String getImagePath(String fileName) {
        return imageUploadDirectory + File.separator + fileName;
    }

}
