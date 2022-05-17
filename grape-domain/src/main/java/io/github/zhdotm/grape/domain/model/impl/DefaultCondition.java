package io.github.zhdotm.grape.domain.model.impl;

import io.github.zhdotm.grape.domain.model.ICondition;
import io.github.zhdotm.grape.domain.model.IEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

/**
 * 默认条件
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public class DefaultCondition implements ICondition {

    @Getter
    @Setter
    private String conditionId;

    @Getter
    @Setter
    private Function<IEvent, Boolean> isSatisfied;

    @Override
    public Boolean isSatisfied(IEvent event) {

        return isSatisfied.apply(event);
    }

}
