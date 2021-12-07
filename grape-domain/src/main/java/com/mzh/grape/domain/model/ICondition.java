package com.mzh.grape.domain.model;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mzh.grape.domain.annotation.Condition;

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
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    default String getStateMachineId() {
        Class<? extends ICondition> clazz = this.getClass();
        Condition condition = AnnotationUtil.getAnnotation(clazz, Condition.class);

        return ObjectUtil.isNotEmpty(condition) ? condition.stateMachineId() : StrUtil.EMPTY;
    }

    /**
     * 是否满足条件
     *
     * @param event 事件
     * @return 出参
     */
    Boolean isSatisfied(IEvent event);

}
