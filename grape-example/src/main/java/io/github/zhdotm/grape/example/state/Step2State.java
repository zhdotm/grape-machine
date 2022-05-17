package io.github.zhdotm.grape.example.state;

import io.github.zhdotm.grape.domain.annotation.State;
import io.github.zhdotm.grape.domain.model.IState;
import org.springframework.stereotype.Component;

/**
 * 第二步状态状态
 *
 * @author zhihao.mao
 */

@Component
@State(stateMachineId = "状态机000001")
public class Step2State implements IState {

    @Override
    public String getStateId() {
        return "step0000002";
    }

}
