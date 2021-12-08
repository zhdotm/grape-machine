package com.mzh.grape.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 事件类型
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum EventTypeEnum {

    /**
     * 事件类型
     */
    NORMAL("normal", "普通事件类型, 既可以用于内部转换又可以用户外部转换"),
    INTERNAL("internal", "内部事件类型, 内部转换"),
    EXTERNAL("external", "外部事件类型, 外部转换(有状态转换)"),
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
