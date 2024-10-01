package com.ls.mianshidog.model.dto.questionBankQuestion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批量向题库移除题目关系
com.ls
 */
@Data
public class QuestionBankQuestionBatchRemoveRequest implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long questionBankId;

    /**
     * 题库 id列表
     */
    private List<Long> questionIdList;

    private static final long serialVersionUID = 1L;
}