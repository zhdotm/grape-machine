package com.mzh.grape.domain.model;

import cn.hutool.core.lang.Assert;
import com.mzh.grape.domain.constant.StateTypeEnum;
import com.mzh.grape.domain.constant.TransitionTypeEnum;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 状态机
 *
 * @author zhihao.mao
 */

public interface IStateMachine {

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    String getStateMachineId();

    /**
     * 获取所有状态
     *
     * @return 所有状态
     */
    Collection<IState> getStates();

    /**
     * 根据状态ID获取状态
     *
     * @param stateId 状态ID
     * @return 状态
     */
    IState getStateById(String stateId);

    /**
     * 根据状态获取条件
     *
     * @param state 当前状态
     * @return 可使用条件
     */
    Collection<ITransition> getTransition(IState state);

    /**
     * 推进
     *
     * @param state 当前状态
     * @param event 事件
     * @return 下个状态(外部转换)/当前状态(内部转换)
     */
    default IState advance(IState state, IEvent event) {
        Collection<ITransition> transitions = getTransition(state);
        transitions = transitions
                .stream()
                .filter(transition -> transition.getCondition().isSatisfied(event))
                .collect(Collectors.toList());

        List<ITransition> externalTransitions = transitions
                .stream()
                .filter(transition -> transition.getTransitionType() == TransitionTypeEnum.EXTERNAL)
                .collect(Collectors.toList());

        Assert.isTrue(externalTransitions.size() < 2,
                "推进失败: stateMachine[%s], state[%s], event[%s], 符合状态事件条件的外部转换不能超过一个",
                getStateMachineId(), state.getStateId(), event.getEventId());

        List<ITransition> internalTransitions = transitions
                .stream()
                .filter(transition -> transition.getTransitionType() == TransitionTypeEnum.INTERNAL)
                .sorted(Comparator.comparingInt(ITransition::getSortId))
                .collect(Collectors.toList());

        for (ITransition transition : internalTransitions) {
            transition.transfer(state, event.getPayload());
        }

        for (ITransition externalTransition : externalTransitions) {
            state = externalTransition.transfer(state, event.getPayload());
        }

        if (StateTypeEnum.BRIDGE == state.getType()) {

            IState finalState = state;
            return new IState() {
                @Override
                public String getStateId() {

                    return finalState.getStateId();
                }

                @Override
                public String getStateMachineId() {

                    return finalState.getNextStateMachineId();
                }
            };
        }

        return state;
    }

}
