package io.github.zhdotm.grape.example.local;

import cn.hutool.core.collection.ListUtil;
import io.github.zhdotm.grape.domain.constant.TransitionTypeEnum;
import io.github.zhdotm.grape.domain.model.IEvent;
import io.github.zhdotm.grape.domain.model.IStateMachine;
import io.github.zhdotm.grape.domain.model.impl.*;
import io.github.zhdotm.grape.domain.support.impl.DefaultStateMachineSupport;

import java.util.function.Function;

/**
 * 原生使用
 *
 * @author zhihao.mao
 */

public class Demo {
    public static void main(String[] args) {
        DefaultStateMachineSupport stateMachineSupport = DefaultStateMachineSupport.getInstance();
        DefaultTransition transition = new DefaultTransition("转换00000001");
        transition.from(new DefaultState("111111"))
                .to(new DefaultState("2222"))
                .type(TransitionTypeEnum.INTERNAL)
                .action(new DefaultAction("33333", objects -> {
                    System.out.println("所有动作都暂时默认为成功");
                    return Boolean.TRUE;
                })).condition(new DefaultCondition("44444", new Function<IEvent, Boolean>() {
            @Override
            public Boolean apply(IEvent iEvent) {
                return Boolean.TRUE;
            }
        })).sortId(Integer.MAX_VALUE);
        IStateMachine stateMachine = stateMachineSupport.build("这是一个状态机kkkk", ListUtil.toList(transition));
        stateMachine.advance(new DefaultState("222"), new DefaultEvent("1111", new Object[]{1, 2, 3}));
    }
}
