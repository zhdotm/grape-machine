package io.github.zhdotm.grape.starter.spring.processor;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.zhdotm.grape.domain.annotation.Action;
import io.github.zhdotm.grape.domain.annotation.Condition;
import io.github.zhdotm.grape.domain.annotation.State;
import io.github.zhdotm.grape.domain.annotation.Transition;
import io.github.zhdotm.grape.domain.model.IAction;
import io.github.zhdotm.grape.domain.model.ICondition;
import io.github.zhdotm.grape.domain.model.IState;
import io.github.zhdotm.grape.domain.model.ITransition;
import io.github.zhdotm.grape.starter.spring.holder.StateMachineHolder;
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
