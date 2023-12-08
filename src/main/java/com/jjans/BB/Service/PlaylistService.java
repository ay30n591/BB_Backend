package com.jjans.BB.Service;

import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Dto.PlaylistRequestDto;
import com.jjans.BB.Dto.PlaylistResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlaylistService {

    List<PlaylistResponseDto> getAllPls();
    PlaylistResponseDto savePl(PlaylistRequestDto plDto, MultipartFile imageFile); //
    PlaylistResponseDto updatePl(Long feedId, PlaylistRequestDto updatedPlDto);
    List<PlaylistResponseDto> getUserAllPls(String nickname);
    PlaylistResponseDto getUserPl(Long pl_id,String nickname);

    List<PlaylistResponseDto> getMyPls();
    //PlaylistResponseDto getMyPl(Long feed_id);


    void deleteFeed(Long feedId);

}
