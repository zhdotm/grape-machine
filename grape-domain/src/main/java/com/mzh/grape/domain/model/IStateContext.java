package com.mzh.grape.domain.model;

/**
 * 状态上下文环境
 *
 * @author zhihao.mao
 */

public interface IStateContext {

    /**
     * 获取状态
     *
     * @return 状态
     */
    IState getState();

    /**
     * 获取事件
     *
     * @return 事件
     */
    IEvent getEvent();

    /**
     * 获取状态机
     *
     * @return 状态机
     */
    IStateMachine getStateMachine();

}
