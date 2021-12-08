package com.mzh.grape.starter.spring.listener;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.mzh.grape.domain.model.IEvent;
import com.mzh.grape.domain.model.IEventContext;
import com.mzh.grape.domain.model.IState;
import com.mzh.grape.domain.model.IStateMachine;
import com.mzh.grape.starter.spring.holder.StateMachineHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

/**
 * 状态机内部事件监听器
 * 注意: 由于监听器会丢失状态机推进(advance)后的响应(state), 所以只适用于内部状态机事件
 *
 * @author zhihao.mao
 */

public class StateMachineInternalEventListener implements ApplicationListener<StateMachineInternalEvent> {

    @Autowired
    private StateMachineHolder stateMachineHolder;

    @Override
    public void onApplicationEvent(StateMachineInternalEvent stateMachineInternalEvent) {
        IEventContext eventContext = (IEventContext) stateMachineInternalEvent.getSource();
        String stateMachineId = eventContext.getStateMachineId();
        String stateId = eventContext.getStateId();
        IEvent event = eventContext.getEvent();
        IStateMachine stateMachine = stateMachineHolder.get(stateMachineId);
        Assert.isTrue(ObjectUtil.isNotEmpty(stateMachine), "处理事件[{}]失败: 状态机[{}]不存在", event.getEventId(), stateMachineId);
        IState state = stateMachine.getStateById(stateId);

        stateMachine.advance(state, event);
    }

}
