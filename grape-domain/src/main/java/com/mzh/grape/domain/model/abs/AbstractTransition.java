package com.mzh.grape.domain.model.abs;

import com.mzh.grape.domain.constant.TransitionTypeEnum;
import com.mzh.grape.domain.model.IAction;
import com.mzh.grape.domain.model.ICondition;
import com.mzh.grape.domain.model.IState;
import com.mzh.grape.domain.model.ITransition;
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

    private TransitionTypeEnum transitionType;

    private IState stateFrom;

    private IState stateTo;

    private ICondition condition;

    private IAction action;

    private Integer sortId;

    @Override
    public ITransition type(TransitionTypeEnum transitionTypeEnum) {
        this.transitionType = transitionTypeEnum;

        return this;
    }

    @Override
    public ITransition from(IState state) {
        this.stateFrom = state;

        return this;
    }

    @Override
    public ITransition to(IState state) {
        this.stateTo = state;

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

    @Override
    public IState getNextState() {

        return this.stateTo;
    }
    
}
