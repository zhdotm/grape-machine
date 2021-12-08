package com.mzh.grape.domain.model.impl;

import com.mzh.grape.domain.model.IAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

/**
 * 默认动作
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public class DefaultAction implements IAction {

    @Getter
    @Setter
    private String actionId;

    @Getter
    @Setter
    private Function<Object[], Boolean> invoke;

    @Override
    public Boolean invoke(Object... args) {

        return invoke.apply(args);
    }

}
