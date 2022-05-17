package io.github.zhdotm.grape.domain.model;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import io.github.zhdotm.grape.domain.constant.EventTypeEnum;
import io.github.zhdotm.grape.domain.constant.StateTypeEnum;
import io.github.zhdotm.grape.domain.constant.TransitionTypeEnum;

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
        Assert.isTrue(CollectionUtil.isNotEmpty(transitions), "状态机推进失败: state[{}]在stateMachine[{}]中不存在对应transition", state.getStateId(), getStateMachineId());
        transitions = transitions
                .stream()
                .filter(transition -> (EventTypeEnum.NORMAL == event.getType() || event.getType().getValue().equalsIgnoreCase(transition.getType().getValue())) && transition.getCondition().isSatisfied(event))
                .collect(Collectors.toList());

        List<ITransition> externalTransitions = transitions
                .stream()
                .filter(transition -> transition.getType() == TransitionTypeEnum.EXTERNAL)
                .collect(Collectors.toList());

        Assert.isTrue(externalTransitions.size() < 2,
                "推进失败: stateMachine[{}], state[{}], event[{}], 符合状态事件条件的外部转换不能超过一个",
                getStateMachineId(), state.getStateId(), event.getEventId());

        List<ITransition> internalTransitions = transitions
                .stream()
                .filter(transition -> transition.getType() == TransitionTypeEnum.INTERNAL)
                .sorted(Comparator.comparingInt(ITransition::getSortId))
                .collect(Collectors.toList());

        for (ITransition transition : internalTransitions) {
            transition.transfer(event.getPayload());
        }

        for (ITransition externalTransition : externalTransitions) {
            state = externalTransition.transfer(event.getPayload());
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
