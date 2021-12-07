package com.mzh.grape.starter.spring.runner;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.mzh.grape.domain.annotation.Action;
import com.mzh.grape.domain.annotation.Condition;
import com.mzh.grape.domain.annotation.State;
import com.mzh.grape.domain.annotation.Transition;
import com.mzh.grape.domain.model.*;
import com.mzh.grape.starter.spring.holder.StateMachineHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自动装配状态机
 *
 * @author zhihao.mao
 */

@Slf4j
public class AutoAssemblingRunner implements ApplicationRunner {

    @Autowired
    private StateMachineHolder stateMachineHolder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始组装状态机");
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();

        List<IState> stateList = applicationContext
                .getBeansWithAnnotation(State.class)
                .values()
                .stream()
                .filter(o -> o instanceof IState)
                .map(o -> (IState) o)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(stateList)) {
            log.warn("停止组装状态机: 不存在可用状态");
            return;
        }

        List<ITransition> transitionList = applicationContext
                .getBeansWithAnnotation(Transition.class)
                .values()
                .stream()
                .filter(o -> o instanceof ITransition)
                .map(o -> (ITransition) o)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(transitionList)) {
            log.warn("停止组装状态机: 不存在可用转换");
            return;
        }

        List<ICondition> conditionList = applicationContext
                .getBeansWithAnnotation(Condition.class)
                .values()
                .stream()
                .filter(o -> o instanceof ICondition)
                .map(o -> (ICondition) o)
                .collect(Collectors.toList());

        List<IAction> actionList = applicationContext
                .getBeansWithAnnotation(Action.class)
                .values()
                .stream()
                .filter(o -> o instanceof IAction)
                .map(o -> (IAction) o)
                .collect(Collectors.toList());

        Map<String, List<IState>> stateMachineIdStateListMap = stateList
                .stream()
                .collect(Collectors.groupingBy(IState::getStateMachineId));

        Map<String, List<ICondition>> stateMachineIdConditionListMap = conditionList
                .stream()
                .collect(Collectors.groupingBy(ICondition::getStateMachineId));

        Map<String, List<IAction>> stateMachineIdActionListMap = actionList
                .stream()
                .collect(Collectors.groupingBy(IAction::getStateMachineId));

        Map<String, List<ITransition>> stateMachineIdTransitionListMap = transitionList
                .stream()
                .collect(Collectors.groupingBy(iTransition -> {
                    Transition transition = AnnotationUtil.getAnnotation(iTransition.getClass(), Transition.class);
                    String stateMachineId = transition.stateMachineId();
                    iTransition.type(transition.type());
                    iTransition.sortId(transition.sortId());
                    List<IState> stateListTemp = stateMachineIdStateListMap.get(stateMachineId);
                    stateListTemp.forEach(iState -> {
                        if (iState.getStateId().equalsIgnoreCase(transition.stateIdFrom())) {
                            iTransition.from(iState);
                            return;
                        }
                        if (iState.getStateId().equalsIgnoreCase(transition.stateIdTo())) {
                            iTransition.to(iState);
                        }
                    });
                    List<ICondition> conditionListTemp = stateMachineIdConditionListMap.get(stateMachineId);
                    ICondition condition = conditionListTemp
                            .stream()
                            .filter(iCondition -> iCondition.getConditionId().equalsIgnoreCase(transition.conditionId()))
                            .findFirst()
                            .orElse(null);
                    iTransition.condition(condition);

                    List<IAction> actionListTemp = stateMachineIdActionListMap.get(stateMachineId);
                    IAction action = actionListTemp
                            .stream()
                            .filter(iAction -> iAction.getActionId().equalsIgnoreCase(transition.actionId()))
                            .findFirst()
                            .orElse(null);
                    iTransition.action(action);

                    return stateMachineId;
                }));

        stateMachineIdStateListMap
                .forEach((stateMachineId, iStates) -> {
                    IStateMachine abstractStateMachine = new IStateMachine() {
                        @Override
                        public String getStateMachineId() {
                            return stateMachineId;
                        }

                        @Override
                        public Collection<IState> getStates() {
                            return iStates;
                        }

                        @Override
                        public IState getStateById(String stateId) {

                            return iStates
                                    .stream()
                                    .filter(iState -> iState.getStateId().equalsIgnoreCase(stateId))
                                    .findFirst()
                                    .orElse(null);
                        }

                        @Override
                        public Collection<ITransition> getTransition(IState state) {
                            List<ITransition> iTransitions = stateMachineIdTransitionListMap.get(stateMachineId);

                            return iTransitions.stream().filter(iTransition -> {
                                Transition transition = AnnotationUtil.getAnnotation(iTransition.getClass(), Transition.class);
                                return transition.stateIdFrom().equalsIgnoreCase(state.getStateId());
                            }).collect(Collectors.toList());
                        }
                    };

                    stateMachineHolder.put(abstractStateMachine, Boolean.FALSE);
                    SpringUtil.registerBean(stateMachineId, abstractStateMachine);
                    log.info("组装状态机成功: {}", stateMachineId);
                });
        log.info("全部状态机组装完毕");
    }


}
