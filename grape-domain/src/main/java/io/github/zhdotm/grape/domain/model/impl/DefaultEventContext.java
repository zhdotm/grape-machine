package io.github.zhdotm.grape.domain.model.impl;

import io.github.zhdotm.grape.domain.model.IEvent;
import io.github.zhdotm.grape.domain.model.IEventContext;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 事件上下文
 *
 * @author zhihao.mao
 */

@Data
@AllArgsConstructor
public class DefaultEventContext implements IEventContext {

    private String stateMachineId;

    private String stateId;

    private IEvent event;

}
