package com.jjans.BB.Service;

import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Dto.PlaylistRequestDto;
import com.jjans.BB.Dto.PlaylistResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlaylistService {

    List<PlaylistResponseDto> getAllPls(int page, int size);
    List<PlaylistResponseDto> getArticlesOrderByLikeCount(int page, int size);


    PlaylistResponseDto savePl(PlaylistRequestDto plDto, MultipartFile imageFile); //
    PlaylistResponseDto updatePl(Long feedId, PlaylistRequestDto updatedPlDto,MultipartFile imageFile);
    List<PlaylistResponseDto> getUserAllPls(String nickname,int page, int size);
    PlaylistResponseDto getUserPl(Long pl_id,String nickname);
    PlaylistResponseDto getMyPl(Long feed_id);
    List<PlaylistResponseDto> getMyPls(int page, int size);




    void likePl(Long feedId);
    void unlikePl(Long feedId);



    void deleteFeed(Long feedId);

}
