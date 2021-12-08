package com.mzh.grape.domain.model.impl;

import com.mzh.grape.domain.model.IState;
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
