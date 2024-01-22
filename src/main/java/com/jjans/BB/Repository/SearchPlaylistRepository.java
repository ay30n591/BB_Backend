package com.jjans.BB.Repository;

import com.jjans.BB.Document.PlaylistDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchPlaylistRepository  extends ElasticsearchRepository<PlaylistDocument, String> {
}
