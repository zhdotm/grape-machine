package com.mzh.grape.starter.spring.holder;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.mzh.grape.domain.model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 状态机持有者
 *
 * @author zhihao.mao
 */

@Slf4j
@Data
public class StateMachineHolder {

    public Map<String, IStateMachine> idStateMachineMap = MapUtil.newConcurrentHashMap();

    public Map<String, List<IAction>> stateMachineIdActionListMap = MapUtil.newConcurrentHashMap();

    public Map<String, List<ICondition>> stateMachineIdConditionListMap = MapUtil.newConcurrentHashMap();

    public Map<String, List<IState>> stateMachineIdStateListMap = MapUtil.newConcurrentHashMap();

    public Map<String, List<ITransition>> stateMachineIdTransitionListMap = MapUtil.newConcurrentHashMap();

    public void put(String stateMachineId, IAction action) {
        List<IAction> actionList = stateMachineIdActionListMap.getOrDefault(stateMachineId, ListUtil.toList());
        List<String> actionIdList = actionList
                .stream()
                .map(IAction::getActionId)
                .collect(Collectors.toList());
        if (actionIdList.contains(action.getActionId())) {
            log.warn("添加动作失败: 状态机[{}]已存在动作[{}]", stateMachineId, action.getActionId());

            return;
        }
        actionList.add(action);
        stateMachineIdActionListMap.put(stateMachineId, actionList);
        log.info("添加动作: stateMachineId[{}], action[{}]", stateMachineId, action.getActionId());
    }

    public void put(String stateMachineId, ICondition condition) {
        List<ICondition> conditionList = stateMachineIdConditionListMap.getOrDefault(stateMachineId, ListUtil.toList());
        List<String> conditionIdList = conditionList
                .stream()
                .map(ICondition::getConditionId)
                .collect(Collectors.toList());
        if (conditionIdList.contains(condition.getConditionId())) {
            log.warn("添加条件失败: 状态机[{}]已存在条件[{}]", stateMachineId, condition.getConditionId());

            return;
        }
        conditionList.add(condition);
        stateMachineIdConditionListMap.put(stateMachineId, conditionList);
        log.info("添加条件: stateMachineId[{}], condition[{}]", stateMachineId, condition.getConditionId());
    }

    public void put(String stateMachineId, IState state) {
        List<IState> stateList = stateMachineIdStateListMap.getOrDefault(stateMachineId, ListUtil.toList());
        List<String> stateIdList = stateList
                .stream()
                .map(IState::getStateId)
                .collect(Collectors.toList());
        if (stateIdList.contains(state.getStateId())) {
            log.warn("添加状态失败: 状态机[{}]已存在状态[{}]", stateMachineId, state.getStateId());

            return;
        }
        stateList.add(state);
        stateMachineIdStateListMap.put(stateMachineId, stateList);
        log.info("添加状态: stateMachineId[{}], state[{}]", stateMachineId, state.getStateId());
    }

    public void put(String stateMachineId, ITransition transition) {
        List<ITransition> transitionList = stateMachineIdTransitionListMap.getOrDefault(stateMachineId, ListUtil.toList());
        List<String> transitionIdList = transitionList
                .stream()
                .map(ITransition::getTransitionId)
                .collect(Collectors.toList());
        if (transitionIdList.contains(transition.getTransitionId())) {
            log.warn("添加转换失败: 状态机[{}]已存在转换[{}]", stateMachineId, transition.getTransitionId());

            return;
        }
        transitionList.add(transition);
        stateMachineIdTransitionListMap.put(stateMachineId, transitionList);
        log.info("添加转换: stateMachineId[{}], transition[{}]", stateMachineId, transition.getTransitionId());
    }

    /**
     * 添加状态机
     *
     * @param stateMachine                        状态机
     * @param isNeedRegisterStateMachineComponent 是否需要注册状态机组件
     */
    public void put(IStateMachine stateMachine, Boolean isNeedRegisterStateMachineComponent) {
        String stateMachineId = stateMachine.getStateMachineId();
        if (idStateMachineMap.containsKey(stateMachineId)) {
            log.warn("添加状态机失败: 已存在状态机[{}]", stateMachineId);

            return;
        }

        if (isNeedRegisterStateMachineComponent) {
            //添加状态机组件
            Collection<IState> states = stateMachine.getStates();
            if (CollectionUtil.isNotEmpty(states)) {
                states.forEach(state -> {
                    //添加状态
                    put(stateMachineId, state);
                    Collection<ITransition> transitions = stateMachine.getTransition(state);
                    if (CollectionUtil.isNotEmpty(transitions)) {
                        transitions.forEach(transition -> {
                            //添加转换
                            put(stateMachineId, transition);
                            //添加条件
                            ICondition condition = transition.getCondition();
                            put(stateMachineId, condition);
                            //添加动作
                            IAction action = transition.getAction();
                            put(stateMachineId, action);
                        });
                    }
                });
            }
        }

        //添加状态机
        idStateMachineMap.put(stateMachineId, stateMachine);

        log.info("添加状态机: {}", stateMachine.getStateMachineId());
    }

    /**
     * 移除状态机
     *
     * @param stateMachineId 移除状态机
     */
    public void remove(String stateMachineId) {

        idStateMachineMap.remove(stateMachineId);
        stateMachineIdActionListMap.remove(stateMachineId);
        stateMachineIdConditionListMap.remove(stateMachineId);
        stateMachineIdStateListMap.remove(stateMachineId);
        stateMachineIdTransitionListMap.remove(stateMachineId);
        log.info("删除状态机: {}", stateMachineId);
    }

    /**
     * 获取状态机
     *
     * @param stateMachineId 状态机ID
     * @return 状态机
     */
    public IStateMachine get(String stateMachineId) {

        return idStateMachineMap.get(stateMachineId);
    }

}
