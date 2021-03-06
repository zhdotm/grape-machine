package io.github.zhdotm.grape.domain.annotation;

import java.lang.annotation.*;

/**
 * 条件注解: 配合ICondition, 声明一个状态
 *
 * @author zhihao.mao
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Condition {

    /**
     * 所属状态机ID
     *
     * @return 状态机ID
     */
    String stateMachineId();

}
