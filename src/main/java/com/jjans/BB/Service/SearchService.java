package com.jjans.BB.Service;

import com.jjans.BB.Document.FeedDocument;
import com.jjans.BB.Document.PlaylistDocument;
import com.jjans.BB.Document.TotalDocument;
import com.jjans.BB.Document.UsersDocument;

import java.util.List;

public interface SearchService {
    List<UsersDocument> findByNickName(String nickname);
    List<FeedDocument> findByFeedKeyword(String keyword);
    List<PlaylistDocument> findByPlistKeyword(String keyword);
    List<TotalDocument> findByTotalKeyword(String keyword);
}