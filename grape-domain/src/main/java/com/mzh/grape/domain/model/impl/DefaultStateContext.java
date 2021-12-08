package com.mzh.grape.domain.model.impl;

import com.mzh.grape.domain.model.IEvent;
import com.mzh.grape.domain.model.IState;
import com.mzh.grape.domain.model.IStateContext;
import com.mzh.grape.domain.model.IStateMachine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 状态上下文
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public class DefaultStateContext implements IStateContext {

    @Getter
    @Setter
    private IState state;

    @Getter
    @Setter
    private IEvent event;

    @Getter
    @Setter
    private IStateMachine stateMachine;

}
