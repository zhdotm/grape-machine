package io.github.zhdotm.grape.domain.model.impl;

import io.github.zhdotm.grape.domain.model.IState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 默认状态
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public class DefaultState implements IState {

    @Getter
    @Setter
    private String stateId;
    
}
