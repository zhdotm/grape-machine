package io.github.zhdotm.grape.starter.spring.support;

import cn.hutool.extra.spring.SpringUtil;
import io.github.zhdotm.grape.domain.model.IStateMachine;
import io.github.zhdotm.grape.domain.support.IStateMachineSupport;
import io.github.zhdotm.grape.starter.spring.holder.StateMachineHolder;
import io.github.zhdotm.grape.starter.spring.listener.StateMachineInternalEvent;
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
