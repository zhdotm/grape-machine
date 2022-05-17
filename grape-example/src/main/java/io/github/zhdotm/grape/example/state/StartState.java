package io.github.zhdotm.grape.example.state;

import io.github.zhdotm.grape.domain.annotation.State;
import io.github.zhdotm.grape.domain.model.IState;
import org.springframework.stereotype.Component;

/**
 * 开始状态
 *
 * @author zhihao.mao
 */

@Component
@State(stateMachineId = "状态机000001")
public class StartState implements IState {

    @Override
    public String getStateId() {
        return "start0000001";
    }

}
