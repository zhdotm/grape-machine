package io.github.zhdotm.grape.example.transition;

import io.github.zhdotm.grape.domain.annotation.Transition;
import io.github.zhdotm.grape.domain.constant.TransitionTypeEnum;
import io.github.zhdotm.grape.domain.model.abs.AbstractTransition;
import org.springframework.stereotype.Component;

/**
 * @author zhihao.mao
 */

@Component
@Transition(stateMachineId = "状态机000001", stateIdFrom = "start0000001",
        stateIdTo = "step0000002", type = TransitionTypeEnum.EXTERNAL,
        sortId = 1, actionId = "startAction0000001",
        conditionId = "startCondition0001")
public class StartTransition extends AbstractTransition {

    @Override
    public String getTransitionId() {

        return "start_transition_0001";
    }

}
