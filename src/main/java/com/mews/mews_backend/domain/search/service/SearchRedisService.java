package com.mews.mews_backend.domain.search.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SearchRedisService {

    private final RedisTemplate<String, String> redisSearchTemplate;

    public SearchRedisService(@Qualifier("redisSearchTemplate") RedisTemplate<String, String> redisSearchTemplate) {
        this.redisSearchTemplate = redisSearchTemplate;
    }

    public void increaseSearchWords(String searchWord){
        Double score = 0.0;
        try {
            // 검색을하면 해당검색어를 value에 저장하고, score를 1 준다
            redisSearchTemplate.opsForZSet().incrementScore("ranking", searchWord,1);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        redisSearchTemplate.opsForZSet().incrementScore("ranking", searchWord, score);
    }

    public List<String> getPopularSearchWords() {
        String key = "ranking";
        ZSetOperations<String, String> ZSetOperations = redisSearchTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 9);

        List<String> popularSearchWordsList = new ArrayList<>();
        for (Iterator<ZSetOperations.TypedTuple<String>> iterator = typedTuples.iterator(); iterator
                .hasNext();) {
            ZSetOperations.TypedTuple<String> typedTuple = iterator.next();
            popularSearchWordsList.add(typedTuple.getValue());
        }
        return popularSearchWordsList;
    }

}
