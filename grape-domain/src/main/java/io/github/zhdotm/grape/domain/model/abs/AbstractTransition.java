package io.github.zhdotm.grape.domain.model.abs;

import io.github.zhdotm.grape.domain.constant.TransitionTypeEnum;
import io.github.zhdotm.grape.domain.model.IAction;
import io.github.zhdotm.grape.domain.model.ICondition;
import io.github.zhdotm.grape.domain.model.IState;
import io.github.zhdotm.grape.domain.model.ITransition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 转换抽象类
 *
 * @author zhihao.mao
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractTransition implements ITransition {

    private TransitionTypeEnum type;

    private IState stateFrom;

    private IState stateTo;

    private IState currentState;

    private IState nextState;

    private ICondition condition;

    private IAction action;

    private Integer sortId;

    @Override
    public ITransition type(TransitionTypeEnum transitionTypeEnum) {
        this.type = transitionTypeEnum;

        return this;
    }

    @Override
    public ITransition from(IState state) {
        this.stateFrom = state;
        this.currentState = state;

        return this;
    }

    @Override
    public ITransition to(IState state) {
        this.stateTo = state;
        this.nextState = state;

        return this;
    }

    @Override
    public ITransition condition(ICondition condition) {
        this.condition = condition;

        return this;
    }

    @Override
    public ITransition action(IAction action) {
        this.action = action;

        return this;
    }

    @Override
    public ITransition sortId(Integer sortId) {
        this.sortId = sortId;

        return this;
    }

}
