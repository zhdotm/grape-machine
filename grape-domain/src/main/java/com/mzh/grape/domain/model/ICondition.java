package com.mzh.grape.domain.model;

/**
 * 条件
 *
 * @author zhihao.mao
 */

public interface ICondition {

    /**
     * 获取条件ID
     *
     * @return 条件ID
     */
    String getConditionId();

    /**
     * 是否满足条件
     *
     * @param event 事件
     * @return 出参
     */
    Boolean isSatisfied(IEvent event);

}
