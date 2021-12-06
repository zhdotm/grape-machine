package com.mzh.grape.starter.spring.processor;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.mzh.grape.domain.annotation.Action;
import com.mzh.grape.domain.annotation.Condition;
import com.mzh.grape.domain.annotation.State;
import com.mzh.grape.domain.annotation.Transition;
import com.mzh.grape.domain.model.IAction;
import com.mzh.grape.domain.model.ICondition;
import com.mzh.grape.domain.model.IState;
import com.mzh.grape.domain.model.ITransition;
import com.mzh.grape.starter.spring.holder.StateMachineHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;


/**
 * 状态机后置处理器
 *
 * @author zhihao.mao
 */

public class StateMachineProcessor implements SmartInstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        StateMachineHolder stateMachineHolder = SpringUtil.getBean(StateMachineHolder.class);
        if (ObjectUtil.isEmpty(stateMachineHolder)) {
            SpringUtil.registerBean("stateMachineHolder", stateMachineHolder);
        }
        stateMachineHolder = SpringUtil.getBean(StateMachineHolder.class);
        Class<?> clazz = bean.getClass();
        if (bean instanceof IAction) {
            Action action = AnnotationUtil.getAnnotation(clazz, Action.class);
            if (ObjectUtil.isNotEmpty(action)) {
                stateMachineHolder.put(action.stateMachineId(), (IAction) bean);
            }
        }
        if (bean instanceof ICondition) {
            Condition condition = AnnotationUtil.getAnnotation(clazz, Condition.class);
            if (ObjectUtil.isNotEmpty(condition)) {
                stateMachineHolder.put(condition.stateMachineId(), (ICondition) bean);
            }
        }
        if (bean instanceof IState) {
            State state = AnnotationUtil.getAnnotation(clazz, State.class);
            if (ObjectUtil.isNotEmpty(state)) {
                stateMachineHolder.put(state.stateMachineId(), (IState) bean);
            }
        }
        if (bean instanceof ITransition) {
            Transition transition = AnnotationUtil.getAnnotation(clazz, Transition.class);
            if (ObjectUtil.isNotEmpty(transition)) {
                stateMachineHolder.put(transition.stateMachineId(), (ITransition) bean);
            }
        }

        return bean;
    }

}
