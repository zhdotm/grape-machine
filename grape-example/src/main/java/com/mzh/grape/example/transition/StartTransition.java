package com.mzh.grape.example.transition;

import com.mzh.grape.domain.annotation.Transition;
import com.mzh.grape.domain.constant.TransitionTypeEnum;
import com.mzh.grape.domain.model.abs.AbstractTransition;
import org.springframework.stereotype.Component;

/**
 * @author zhihao.mao
 */

@Component
@Transition(stateMachineId = "状态机000001", stateIdFrom = "start0000001",
        stateIdTo = "start0000001", type = TransitionTypeEnum.INTERNAL,
        sortId = 1, actionId = "startAction0000001",
        conditionId = "startCondition0001")
public class StartTransition extends AbstractTransition {

    @Override
    public String getTransitionId() {
        
        return "start_transition_0001";
    }

}
