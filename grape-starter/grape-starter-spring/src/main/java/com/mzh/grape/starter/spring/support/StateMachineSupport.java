package com.mzh.grape.starter.spring.support;

import com.mzh.grape.domain.model.IStateMachine;
import com.mzh.grape.domain.support.IStateMachineSupport;
import com.mzh.grape.starter.spring.holder.StateMachineHolder;
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

}
