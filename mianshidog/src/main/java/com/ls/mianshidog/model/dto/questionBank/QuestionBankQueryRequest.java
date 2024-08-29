package com.ls.mianshidog.model.dto.questionBank;

import com.ls.mianshidog.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询题库请求
com.ls
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionBankQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private Long notId;
    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片
     */
    private String picture;
    /**
     * 是否要关联查询题目列表
     * 布尔类型（boolean）本身是一个原始数据类型，它只有两个可能的值：true 和 false。
     * 因此，布尔类型的值不能是 null。null 是一个特殊的字面量，用于表示对象引用（即非原始数据类型）不指向任何对象。
     * 然而，Java 提供了 Boolean 类作为 boolean 原始数据类型的包装类。Boolean 是一个对象类型，因此它可以有一个 null 值，表示没有指向任何 Boolean 对象。但是，这并不意味着布尔类型的值（即 boolean 类型的变量）可以是 null。
     */
    private boolean needQueryQuestionList;

    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;


}