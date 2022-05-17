package io.github.zhdotm.grape.example.normal;

import io.github.zhdotm.grape.domain.constant.TransitionTypeEnum;
import io.github.zhdotm.grape.domain.model.*;
import io.github.zhdotm.grape.domain.model.abs.AbstractTransition;
import io.github.zhdotm.grape.domain.support.IStateMachineSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * 手动组装状态机
 *
 * @author zhihao.mao
 */

@Component
public class ManualAssemble implements ApplicationRunner {

    @Autowired
    private IStateMachineSupport stateMachineSupport;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ITransition transition = new AbstractTransition() {
            @Override
            public String getTransitionId() {
                return "a_normal_transition";
            }
        };

        IState startState = () -> "start_0001";

        IState toState = () -> "to_0002";

        transition = transition
                .type(TransitionTypeEnum.EXTERNAL)
                .sortId(Integer.MAX_VALUE)
                .from(startState)
                .to(toState)
                .condition(new ICondition() {
                    @Override
                    public String getConditionId() {
                        return "condition_003";
                    }

                    @Override
                    public Boolean isSatisfied(IEvent event) {
                        System.out.println("目前条件全部放过");
                        return Boolean.TRUE;
                    }
                })
                .action(new IAction() {
                    @Override
                    public String getActionId() {
                        return "action_0001";
                    }

                    @Override
                    public Boolean invoke(Object... args) {
                        System.out.println("=========执行=========");
                        System.out.println("默认执行都让过");
                        System.out.println("=========执行=========");
                        return Boolean.TRUE;
                    }
                });


        IStateMachine stateMachine = stateMachineSupport.build("这是一个状态机001", transition);

        stateMachineSupport.putStateMachine(stateMachine);
        IState nextState = stateMachine.advance(new IState() {
            @Override
            public String getStateId() {
                return "start_0001";
            }
        }, new IEvent() {
            @Override
            public String getEventId() {
                return "这是一个事件ID";
            }

            @Override
            public Object[] getPayload() {
                return new Object[0];
            }
        });
        System.out.println("推进后的状态: " + nextState.getStateId());
    }
}
