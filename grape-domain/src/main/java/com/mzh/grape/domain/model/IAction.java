package com.mzh.grape.domain.model;

/**
 * 动作
 *
 * @author zhihao.mao
 */

public interface IAction {

    /**
     * 获取动作ID
     *
     * @return 动作ID
     */
    String getActionId();

    /**
     * 执行动作
     *
     * @param args 参数
     * @return 是否执行成功
     */
    Boolean invoke(Object... args);

}
