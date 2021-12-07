package com.mzh.grape.domain.support;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mzh.grape.domain.constant.TransitionTypeEnum;
import com.mzh.grape.domain.model.IState;
import com.mzh.grape.domain.model.IStateMachine;
import com.mzh.grape.domain.model.ITransition;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 状态机支持
 *
 * @author zhihao.mao
 */

public interface IStateMachineSupport {

    /**
     * 通过状态机ID, 获取状态机
     *
     * @param stateMachineId 状态机ID
     * @return 状态机
     */
    IStateMachine getStateMachineById(String stateMachineId);

    /**
     * 新增状态机
     *
     * @param stateMachine 状态机
     */
    void putStateMachine(IStateMachine stateMachine);

    /**
     * 构建状态机
     *
     * @param stateMachineId 状态机ID
     * @param transition     状态机内所有状态转换
     * @return 状态机
     */
    default IStateMachine build(String stateMachineId, ITransition transition) {

        return build(stateMachineId, ListUtil.of(transition));
    }

    /**
     * 构建状态机
     *
     * @param stateMachineId    状态机ID
     * @param allTransitionList 状态机内所有状态转换
     * @return 状态机
     */
    default IStateMachine build(String stateMachineId, List<ITransition> allTransitionList) {
        Map<IState, List<ITransition>> stateTransitionListMap = allTransitionList.stream().collect(Collectors.groupingBy(ITransition::getCurrentState));
        Set<IState> stateSet = new HashSet<>(stateTransitionListMap.keySet());
        Set<String> stateIdSet = stateSet.stream().map(IState::getStateId).collect(Collectors.toSet());
        stateTransitionListMap.values().forEach(transitionList -> {
            transitionList.forEach(transition -> {
                IState nextState = transition.getNextState();
                if (ObjectUtil.isNotEmpty(nextState)) {
                    if (stateIdSet.add(nextState.getStateId())) {
                        stateSet.add(nextState);
                    }
                }
            });
        });
        List<IState> stateList = ListUtil.toList(stateSet);
        Map<String, IState> stateIdStateMap = stateList
                .stream()
                .collect(Collectors.toMap(IState::getStateId, iState -> iState));

        stateTransitionListMap.forEach((iState, transitionList) -> {
            transitionList = transitionList
                    .stream()
                    .filter(this::checkTransition)
                    .collect(Collectors.toList());
            stateTransitionListMap.put(iState, transitionList);
        });

        return new IStateMachine() {

            @Override
            public String getStateMachineId() {

                return stateMachineId;
            }

            @Override
            public Collection<IState> getStates() {

                return stateList;
            }

            @Override
            public IState getStateById(String stateId) {

                return stateIdStateMap.get(stateId);
            }

            @Override
            public Collection<ITransition> getTransition(IState state) {
                String stateId = state.getStateId();
                IState stateOld = stateIdStateMap.get(stateId);

                return stateTransitionListMap.get(stateOld);
            }
        };
    }

    /**
     * 检查转换的完整性
     *
     * @param transition 转换
     * @return 是否完整
     */
    default Boolean checkTransition(ITransition transition) {
        if (ObjectUtil.isEmpty(transition)) {
            return Boolean.FALSE;
        }
        Assert.isTrue(StrUtil.isNotBlank(transition.getTransitionId()), "检查转换完整性失败: 转换ID为空, transition[{}]", transition.toString());
        Assert.isTrue(ObjectUtil.isNotEmpty(transition.getTransitionType()), "检查转换完整性失败: 转换类型为空, transition[{}]", transition.toString());
        Assert.isTrue(ObjectUtil.isNotEmpty(transition.getCondition()), "检查转换完整性失败: 转换条件为空, transition[{}]", transition.toString());
        Assert.isTrue(ObjectUtil.isNotEmpty(transition.getAction()), "检查转换完整性失败: 转换动作为空, transition[{}]", transition.toString());
        Assert.isTrue(!(ObjectUtil.isEmpty(transition.getNextState()) && (TransitionTypeEnum.EXTERNAL == transition.getTransitionType())), "检查转换完整性失败: 外部转换类型不能没有下个转换状态, transition[{}]", transition.toString());
        Assert.isTrue(ObjectUtil.isNotEmpty(transition.getSortId()), "检查转换完整性失败: 转换排序为空, transition[{}]", transition.toString());

        return Boolean.TRUE;
    }

}
