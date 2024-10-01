package com.ls.mianshidog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ls.mianshidog.common.ErrorCode;
import com.ls.mianshidog.constant.CommonConstant;
import com.ls.mianshidog.exception.BusinessException;
import com.ls.mianshidog.exception.ThrowUtils;
import com.ls.mianshidog.mapper.QuestionBankQuestionMapper;
import com.ls.mianshidog.model.dto.questionBankQuestion.QuestionBankQuestionQueryRequest;
import com.ls.mianshidog.model.entity.Question;
import com.ls.mianshidog.model.entity.QuestionBank;
import com.ls.mianshidog.model.entity.QuestionBankQuestion;

import com.ls.mianshidog.model.entity.User;
import com.ls.mianshidog.model.vo.QuestionBankQuestionVO;
import com.ls.mianshidog.model.vo.UserVO;
import com.ls.mianshidog.service.QuestionBankQuestionService;
import com.ls.mianshidog.service.QuestionBankService;
import com.ls.mianshidog.service.QuestionService;
import com.ls.mianshidog.service.UserService;
import com.ls.mianshidog.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 题库题目关联服务实现
com.ls
 */
@Service
@Slf4j
public class QuestionBankQuestionServiceImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion> implements QuestionBankQuestionService {

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private QuestionService questionService;

    @Resource
    private QuestionBankService questionBankService;

    public QuestionBankQuestionServiceImpl(QuestionBankService questionBankService) {
        this.questionBankService = questionBankService;
    }

    /**
     * 校验数据
     *
     * @param questionBankQuestion
     * @param add      对创建的数据进行校验
     */
    @Override

    public void validQuestionBankQuestion(QuestionBankQuestion questionBankQuestion, boolean add) {
        ThrowUtils.throwIf(questionBankQuestion == null, ErrorCode.PARAMS_ERROR);
        //不需要校验
//        // todo 从对象中取值
//        String title = questionBankQuestion.getTitle();
//        // 创建数据时，参数不能为空
//        if (add) {
//            // todo 补充校验规则
//            ThrowUtils.throwIf(StringUtils.isBlank(title), ErrorCode.PARAMS_ERROR);
//        }
//        // 修改数据时，有参数则校验
//        // todo 补充校验规则
//        if (StringUtils.isNotBlank(title)) {
//            ThrowUtils.throwIf(title.length() > 80, ErrorCode.PARAMS_ERROR, "标题过长");
//        }
    }

    /**
     * 获取查询条件
     *
     * @param questionBankQuestionQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest) {
        QueryWrapper<QuestionBankQuestion> queryWrapper = new QueryWrapper<>();
        if (questionBankQuestionQueryRequest == null) {
            return queryWrapper;
        }
        // todo 从对象中取值
        Long id = questionBankQuestionQueryRequest.getId();
        Long notId = questionBankQuestionQueryRequest.getNotId();
        String sortField = questionBankQuestionQueryRequest.getSortField();
        String sortOrder = questionBankQuestionQueryRequest.getSortOrder();
        Long questionBankId = questionBankQuestionQueryRequest.getQuestionBankId();
        Long questionId = questionBankQuestionQueryRequest.getQuestionId();
        Long userId = questionBankQuestionQueryRequest.getUserId();
        // todo 补充需要的查询条件

        // 模糊查询

        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionBankId), "questionBankId", questionBankId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取题库题目关联封装
     *
     * @param questionBankQuestion
     * @param request
     * @return
     */
    @Override
    public QuestionBankQuestionVO getQuestionBankQuestionVO(QuestionBankQuestion questionBankQuestion, HttpServletRequest request) {
        // 对象转封装类
        QuestionBankQuestionVO questionBankQuestionVO = QuestionBankQuestionVO.objToVo(questionBankQuestion);

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Long userId = questionBankQuestion.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        questionBankQuestionVO.setUser(userVO);
        // 2. 已登录，获取用户点赞、收藏状态

        // endregion

        return questionBankQuestionVO;
    }

    /**
     * 分页获取题库题目关联封装
     *
     * @param questionBankQuestionPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionBankQuestionVO> getQuestionBankQuestionVOPage(Page<QuestionBankQuestion> questionBankQuestionPage, HttpServletRequest request) {
        List<QuestionBankQuestion> questionBankQuestionList = questionBankQuestionPage.getRecords();
        Page<QuestionBankQuestionVO> questionBankQuestionVOPage = new Page<>(questionBankQuestionPage.getCurrent(), questionBankQuestionPage.getSize(), questionBankQuestionPage.getTotal());
        if (CollUtil.isEmpty(questionBankQuestionList)) {
            return questionBankQuestionVOPage;
        }
        // 对象列表 => 封装对象列表
        List<QuestionBankQuestionVO> questionBankQuestionVOList = questionBankQuestionList.stream().map(questionBankQuestion -> {
            return QuestionBankQuestionVO.objToVo(questionBankQuestion);
        }).collect(Collectors.toList());

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionBankQuestionList.stream().map(QuestionBankQuestion::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));

        // 填充信息
        questionBankQuestionVOList.forEach(questionBankQuestionVO -> {
            Long userId = questionBankQuestionVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionBankQuestionVO.setUser(userService.getUserVO(user));

        });
        // endregion

        questionBankQuestionVOPage.setRecords(questionBankQuestionVOList);
        return questionBankQuestionVOPage;
    }


    /**
     * 通过题目id集合批量加入到题库
     * @param questionIdList
     * @param questionBankId
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addQuestionToQuestionBankByIdList(List<Long> questionIdList, long questionBankId, User user) {
        // 逻辑校验参数
        ThrowUtils.throwIf(questionIdList ==null,ErrorCode.NOT_FOUND_ERROR,"题目id集合不能为空");
        ThrowUtils.throwIf(questionBankId <= 0,ErrorCode.NOT_FOUND_ERROR,"题库id不能为空");
        ThrowUtils.throwIf(user == null,ErrorCode.NOT_FOUND_ERROR,"用户不能为空");

        // 业务校验参数
        //校验题目id集合
        List<Question> questions = questionService.listByIds(questionIdList);
        List<Long> idList = questions.stream()
                .map(Question::getId)
                .collect(Collectors.toList());

        ThrowUtils.throwIf(idList == null,ErrorCode.NOT_FOUND_ERROR,"题目id集合不能为空");

        //校验题库id
        QuestionBank questionBank = questionBankService.getById(questionBankId);
        ThrowUtils.throwIf(questionBank ==null,ErrorCode.NOT_FOUND_ERROR,"题库不存在");


        //批量插入
        for (Long questionId : idList) {
            QuestionBankQuestion questionBankQuestion = new QuestionBankQuestion();
            questionBankQuestion.setQuestionBankId(questionBankId);
            questionBankQuestion.setQuestionId(questionId);
            questionBankQuestion.setUserId(user.getId());

            //先判断题库题目关系是否已经存在
            LambdaQueryWrapper<QuestionBankQuestion> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(QuestionBankQuestion::getQuestionId,questionId)
                    .eq(QuestionBankQuestion::getQuestionBankId,questionBankId);
            QuestionBankQuestion questionBankQuestionOne = this.getOne(queryWrapper);
            if (questionBankQuestionOne == null) {
                boolean result = this.save(questionBankQuestion);
                //如果失败
                if (!result) {
                    throw  new BusinessException(ErrorCode.OPERATION_ERROR,"向题库添加题目失败");
                }
            }
        }
    }


    /**
     * 通过题目id集合批量移除题目题库关系
     * @param questionIdList
     * @param questionBankId
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeQuestionToQuestionBankByIdList(List<Long> questionIdList, long questionBankId, User user) {
        // 逻辑校验参数
        ThrowUtils.throwIf(questionIdList ==null,ErrorCode.NOT_FOUND_ERROR,"题目id集合不能为空");
        ThrowUtils.throwIf(questionBankId <= 0,ErrorCode.NOT_FOUND_ERROR,"题库id不能为空");
        ThrowUtils.throwIf(user == null,ErrorCode.NOT_FOUND_ERROR,"用户不能为空");

        // 业务校验参数
        //校验题目id集合
        List<Question> questions = questionService.listByIds(questionIdList);
        List<Long> idList = questions.stream()
                .map(Question::getId)
                .collect(Collectors.toList());

        ThrowUtils.throwIf(idList == null,ErrorCode.NOT_FOUND_ERROR,"题目id集合不能为空");

        //校验题库id
        QuestionBank questionBank = questionBankService.getById(questionBankId);
        ThrowUtils.throwIf(questionBank ==null,ErrorCode.NOT_FOUND_ERROR,"题库不存在");


        //批量删除
        for (Long questionId : idList) {
            LambdaQueryWrapper<QuestionBankQuestion> queryWrapper = new LambdaQueryWrapper<>();

            queryWrapper.eq(QuestionBankQuestion::getQuestionId,questionId)
                    .eq(QuestionBankQuestion::getQuestionBankId,questionBankId);
            boolean result = this.remove(queryWrapper);

            //如果失败
            if (!result) {
                throw  new BusinessException(ErrorCode.OPERATION_ERROR,"向题库批量移除关系失败");
            }
        }
    }

}
