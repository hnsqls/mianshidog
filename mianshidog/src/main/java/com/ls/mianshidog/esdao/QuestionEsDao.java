package com.ls.mianshidog.esdao;

import com.ls.mianshidog.model.dto.post.PostEsDTO;

import java.util.List;

import com.ls.mianshidog.model.dto.question.QuestionEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 题目 ES 操作

 */
public interface QuestionEsDao extends ElasticsearchRepository<QuestionEsDTO, Long> {


//    List<QuestionEsDao> findByUserId(Long userId);
}