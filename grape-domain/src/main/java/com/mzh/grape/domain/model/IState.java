package com.mzh.grape.domain.model;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mzh.grape.domain.annotation.State;
import com.mzh.grape.domain.constant.StateTypeEnum;

/**
 * 状态
 *
 * @author zhihao.mao
 */

public interface IState {

    /**
     * 获取状态唯一ID
     *
     * @return 状态唯一ID
     */
    String getStateId();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    default String getStateMachineId() {
        Class<? extends IState> clazz = this.getClass();
        State state = AnnotationUtil.getAnnotation(clazz, State.class);

        return ObjectUtil.isNotEmpty(state) ? state.stateMachineId() : StrUtil.EMPTY;
    }

    /**
     * 状态类型
     *
     * @return 状态类型
     */
    default StateTypeEnum getType() {

        return StateTypeEnum.NORMAL;
    }

    /**
     * 下个状态机ID(只有StateTypeEnum.BRIDGE类型允许跨状态机)
     *
     * @return 下个状态机ID
     */
    default String getNextStateMachineId() {

        return StrUtil.EMPTY;
    }

}
