package com.jjans.BB.Repository;

//import com.jjans.BB.Document.UsersDocument;
import com.jjans.BB.Dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
@Repository
@RequiredArgsConstructor
public class SearchQueryRepository {

    private final ElasticsearchOperations operations;

//    public List<UsersDocument> findByCondition(UserRequestDto.SearchCondition searchCondition, Pageable pageable) {
//        CriteriaQuery query = createConditionCriteriaQuery(searchCondition).setPageable(pageable);
//
//        SearchHits<UsersDocument> search = operations.search(query, UsersDocument.class);
//        return search.stream()
//                .map(SearchHit::getContent)
//                .collect(Collectors.toList());
//    }

    private CriteriaQuery createConditionCriteriaQuery(UserRequestDto.SearchCondition searchCondition) {
        CriteriaQuery query = new CriteriaQuery(new Criteria());

        if (searchCondition == null)
            return query;

        if (searchCondition.getEmail() != null)
            query.addCriteria(Criteria.where("email").is(searchCondition.getEmail()));

        if(StringUtils.hasText(searchCondition.getUser_name()))
            query.addCriteria(Criteria.where("username").is(searchCondition.getUser_name()));

        if(StringUtils.hasText(searchCondition.getNick_name()))
            query.addCriteria(Criteria.where("nickname").is(searchCondition.getNick_name()));

        return query;
    }
}