package io.github.zhdotm.grape.domain.model.impl;

import io.github.zhdotm.grape.domain.model.abs.AbstractTransition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 默认转换
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public class DefaultTransition extends AbstractTransition {

    @Getter
    @Setter
    private String transitionId;

}
