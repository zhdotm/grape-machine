package io.github.zhdotm.grape.example.action;

import io.github.zhdotm.grape.domain.annotation.Action;
import io.github.zhdotm.grape.domain.model.IAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhihao.mao
 */

@Component
@Slf4j
@Action(stateMachineId = "状态机000001")
public class StartAction implements IAction {

    @Override
    public String getActionId() {
        return "startAction0000001";
    }

    @Override
    public Boolean invoke(Object... args) {
        log.info("从事件获取的参数: {}", args);
        log.info("执行开始动作");
        return Boolean.TRUE;
    }

}
