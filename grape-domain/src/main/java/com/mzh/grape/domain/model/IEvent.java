package com.mzh.grape.domain.model;

/**
 * 事件
 *
 * @author zhihao.mao
 */

public interface IEvent {

    /**
     * 获取事件ID
     *
     * @return 事件ID
     */
    String getEventId();

    /**
     * 获取参数负载
     *
     * @return 参数负载
     */
    Object[] getPayload();

}
