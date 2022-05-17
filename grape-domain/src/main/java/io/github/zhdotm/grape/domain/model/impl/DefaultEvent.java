package io.github.zhdotm.grape.domain.model.impl;

import io.github.zhdotm.grape.domain.model.IEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 默认事件
 *
 * @author zhihao.mao
 */

@Data
@AllArgsConstructor
public class DefaultEvent implements IEvent {

    private final String eventId;

    private final Object[] payload;

}
