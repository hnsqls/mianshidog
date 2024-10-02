package com.ls.mianshidog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ls.mianshidog.model.dto.questionBankQuestion.QuestionBankQuestionQueryRequest;
import com.ls.mianshidog.model.entity.QuestionBankQuestion;
import com.ls.mianshidog.model.entity.User;
import com.ls.mianshidog.model.vo.QuestionBankQuestionVO;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 题库题目关联服务
com.ls
 */
public interface QuestionBankQuestionService extends IService<QuestionBankQuestion> {

    /**
     * 校验数据
     *
     * @param questionBankQuestion
     * @param add 对创建的数据进行校验
     */
    void validQuestionBankQuestion(QuestionBankQuestion questionBankQuestion, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionBankQuestionQueryRequest
     * @return
     */
    QueryWrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest);
    
    /**
     * 获取题库题目关联封装
     *
     * @param questionBankQuestion
     * @param request
     * @return
     */
    QuestionBankQuestionVO getQuestionBankQuestionVO(QuestionBankQuestion questionBankQuestion, HttpServletRequest request);

    /**
     * 分页获取题库题目关联封装
     *
     * @param questionBankQuestionPage
     * @param request
     * @return
     */
    Page<QuestionBankQuestionVO> getQuestionBankQuestionVOPage(Page<QuestionBankQuestion> questionBankQuestionPage, HttpServletRequest request);

    /**
     * 通过题目id集合批量加入到题库
     * @param questionIdList
     * @param questionBankId
     * @param user
     */
    void addQuestionToQuestionBankByIdList(List<Long> questionIdList, long questionBankId, User user);


    /**
     * 通过题目id集合批量加入到题库  --分批操作内部调用
     * @param questionIdList
     * @param questionBankId
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    void batchAddQuestionToQuestionBankInner(List<Long> questionIdList, long questionBankId, User user);

    /**
     * 通过题目id集合批量移除到题库关系
     * @param questionIdList
     * @param questionBankId
     * @param user
     */
    void removeQuestionToQuestionBankByIdList(List<Long> questionIdList, long questionBankId, User user);


}
