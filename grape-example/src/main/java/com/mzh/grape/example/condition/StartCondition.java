package com.mzh.grape.example.condition;

import com.mzh.grape.domain.annotation.Condition;
import com.mzh.grape.domain.model.ICondition;
import com.mzh.grape.domain.model.IEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhihao.mao
 */

@Slf4j
@Component
@Condition(stateMachineId = "状态机000001")
public class StartCondition implements ICondition {

    @Override
    public String getConditionId() {
        return "startCondition0001";
    }

    @Override
    public Boolean isSatisfied(IEvent event) {
        boolean isStart = event.getEventId().contains("start");
        log.info("开始条件是否通过: {}", isStart);

        return isStart;
    }

}
