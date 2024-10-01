package com.ls.mianshidog.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批量删除题目请求
 */
@Data
public class QuestionRemoveBatchRequest implements Serializable {

    /**
     * 题目id列表
     */
    private List<Long> questionIdList;

    private static final long serialVersionUID = 1L;
}
