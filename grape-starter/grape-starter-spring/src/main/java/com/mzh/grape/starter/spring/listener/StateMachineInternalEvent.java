package com.mzh.grape.starter.spring.listener;

import cn.hutool.core.lang.Assert;
import com.mzh.grape.domain.constant.EventTypeEnum;
import com.mzh.grape.domain.model.IEvent;
import com.mzh.grape.domain.model.IEventContext;
import org.springframework.context.ApplicationEvent;

/**
 * 状态机内部事件
 *
 * @author zhihao.mao
 */

public class StateMachineInternalEvent extends ApplicationEvent {

    public StateMachineInternalEvent(Object source) {
        super(source);
        Assert.isTrue(((source instanceof IEventContext)), "新建状态机事件失败: 状态机事件必须包含事件上下文");
        IEventContext eventContext = (IEventContext) source;
        IEvent event = eventContext.getEvent();
        Assert.isTrue(((event.getType() == EventTypeEnum.INTERNAL)), "新建状态机事件失败: 由于事件监听器会丢失响应, 所以状态机事件只适用于内部转换事件");
    }

}
