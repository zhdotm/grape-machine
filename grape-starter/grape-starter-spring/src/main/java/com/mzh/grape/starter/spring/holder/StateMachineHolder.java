package com.mzh.grape.starter.spring.holder;

import com.mzh.grape.domain.model.IStateMachine;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态机持有者
 *
 * @author zhihao.mao
 */

@Slf4j
@Data
public class StateMachineHolder {

    private Map<String, IStateMachine> idStateMachineMap = new HashMap<>();

    /**
     * 添加状态机
     *
     * @param stateMachineId 状态机ID
     * @param stateMachine   状态机
     */
    public void put(String stateMachineId, IStateMachine stateMachine) {

        idStateMachineMap.put(stateMachineId, stateMachine);
        log.info("添加状态机: {}", stateMachineId);
    }

    /**
     * 移除状态机
     *
     * @param stateMachineId 移除状态机
     */
    public void remove(String stateMachineId) {

        idStateMachineMap.remove(stateMachineId);
        log.info("删除状态机: {}", stateMachineId);
    }

    /**
     * 获取状态机
     *
     * @param stateMachineId 状态机ID
     * @return 状态机
     */
    public IStateMachine get(String stateMachineId) {

        return idStateMachineMap.get(stateMachineId);
    }

}
