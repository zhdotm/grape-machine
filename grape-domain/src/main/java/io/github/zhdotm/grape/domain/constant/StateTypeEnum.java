package io.github.zhdotm.grape.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态类型
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum StateTypeEnum {

    /**
     * 状态类型
     */
    NORMAL("normal", "普通状态类型, 仍处于一个状态机内"),
    BRIDGE("bridge", "桥类型, 连接两个状态机"),
    ;

    /**
     * 代码
     */
    @Getter
    private final String value;

    /**
     * 描述
     */
    @Getter
    private final String description;

}
