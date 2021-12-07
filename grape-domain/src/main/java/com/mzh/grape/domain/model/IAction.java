package com.mzh.grape.domain.model;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mzh.grape.domain.annotation.Action;

/**
 * 动作
 *
 * @author zhihao.mao
 */

public interface IAction {

    /**
     * 获取动作ID
     *
     * @return 动作ID
     */
    String getActionId();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    default String getStateMachineId() {
        Class<? extends IAction> clazz = this.getClass();
        Action action = AnnotationUtil.getAnnotation(clazz, Action.class);

        return ObjectUtil.isNotEmpty(action) ? action.stateMachineId() : StrUtil.EMPTY;
    }

    /**
     * 执行动作
     *
     * @param args 参数
     * @return 是否执行成功
     */
    Boolean invoke(Object... args);

}
