package com.ls.mianshidog.mapper;

import com.ls.mianshidog.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
* @author 26611
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2024-08-27 09:43:05
* @Entity com.ls.mianshidog.model.entity.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("select * from question where updateTime >= #{minUpdateTime}")
    List<Question> listQuestionWithDelete(Date fiveMinutesAgoDate);
}




