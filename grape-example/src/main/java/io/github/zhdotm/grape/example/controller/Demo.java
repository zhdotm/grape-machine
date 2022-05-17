package io.github.zhdotm.grape.example.controller;

import io.github.zhdotm.grape.domain.constant.EventTypeEnum;
import io.github.zhdotm.grape.domain.model.IEvent;
import io.github.zhdotm.grape.domain.model.IState;
import io.github.zhdotm.grape.domain.model.IStateMachine;
import io.github.zhdotm.grape.domain.model.impl.DefaultEvent;
import io.github.zhdotm.grape.domain.model.impl.DefaultEventContext;
import io.github.zhdotm.grape.example.state.StartState;
import io.github.zhdotm.grape.starter.spring.listener.StateMachineInternalEvent;
import io.github.zhdotm.grape.starter.spring.support.StateMachineSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhihao.mao
 */

@RestController
public class Demo {

    @Autowired
    private StateMachineSupport stateMachineSupport;

    @GetMapping("/demo")
    public void test() {
        IStateMachine stateMachine = stateMachineSupport.getStateMachineById("状态机000001");
        IState state = stateMachine.advance(new StartState(), new IEvent() {
            @Override
            public String getEventId() {
                return "start";
            }

            @Override
            public Object[] getPayload() {
                return new Object[0];
            }
        });
        System.out.println(state.getStateId());
    }

    @GetMapping("/publish")
    public void test22() {
        DefaultEvent event = new DefaultEvent("start", new Object[]{}) {
            @Override
            public EventTypeEnum getType() {
                return EventTypeEnum.INTERNAL;
            }
        };
        DefaultEventContext eventContext = new DefaultEventContext("状态机000001", "start0000001", event);
        stateMachineSupport.publish(new StateMachineInternalEvent(eventContext));
    }

}
