package io.github.zhdotm.grape.domain.support.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import io.github.zhdotm.grape.domain.model.IStateMachine;
import io.github.zhdotm.grape.domain.support.IStateMachineSupport;

import java.util.Map;

/**
 * 默认状态机支持, 默认用hashmap缓存状态机信息
 *
 * @author zhihao.mao
 */

public class DefaultStateMachineSupport implements IStateMachineSupport {

    private final Map<String, IStateMachine> idStateMachineMap = MapUtil.newConcurrentHashMap();

    private static volatile DefaultStateMachineSupport stateMachineSupport;

    public static DefaultStateMachineSupport getInstance() {
        if (ObjectUtil.isEmpty(stateMachineSupport)) {
            synchronized (DefaultStateMachineSupport.class) {
                if (ObjectUtil.isEmpty(stateMachineSupport)) {
                    stateMachineSupport = new DefaultStateMachineSupport();
                    return stateMachineSupport;
                }
            }
        }

        return stateMachineSupport;
    }

    @Override
    public IStateMachine getStateMachineById(String stateMachineId) {

        return idStateMachineMap.get(stateMachineId);
    }

    @Override
    public void putStateMachine(IStateMachine stateMachine) {
        idStateMachineMap.put(stateMachine.getStateMachineId(), stateMachine);
    }

    private DefaultStateMachineSupport() {
    }


}
