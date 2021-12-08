package com.mzh.grape.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 转换类型
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum TransitionTypeEnum {

    /**
     * 转换类型
     */
    INTERNAL("internal", "内部转换, 无状态转换"),
    EXTERNAL("external", "外部转换, 有状态转换"),
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
