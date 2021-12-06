package com.mzh.grape.example.action;

import com.mzh.grape.domain.annotation.Action;
import com.mzh.grape.domain.model.IAction;
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
