package com.mzh.grape.starter.spring.support;

import cn.hutool.extra.spring.SpringUtil;
import com.mzh.grape.domain.model.IStateMachine;
import com.mzh.grape.domain.support.IStateMachineSupport;
import com.mzh.grape.starter.spring.holder.StateMachineHolder;
import com.mzh.grape.starter.spring.listener.StateMachineInternalEvent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 状态机支持
 *
 * @author zhihao.mao
 */

public class StateMachineSupport implements IStateMachineSupport {

    @Autowired
    private StateMachineHolder stateMachineHolder;

    @Override
    public IStateMachine getStateMachineById(String stateMachineId) {

        return stateMachineHolder.get(stateMachineId);
    }

    @Override
    public void putStateMachine(IStateMachine stateMachine) {

        stateMachineHolder.put(stateMachine, Boolean.TRUE);
    }

    /**
     * 状态机内部事件发布
     *
     * @param stateMachineInternalEvent 状态机内部事件
     */
    public void publish(StateMachineInternalEvent stateMachineInternalEvent) {

        SpringUtil.publishEvent(stateMachineInternalEvent);
    }

}
