package io.github.zhdotm.grape.domain.model;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.zhdotm.grape.domain.annotation.Transition;
import io.github.zhdotm.grape.domain.constant.TransitionTypeEnum;

/**
 * 转换
 *
 * @author zhihao.mao
 */

public interface ITransition {

    /**
     * 转换类型
     *
     * @param transitionTypeEnum 转换类型
     * @return this
     */
    ITransition type(TransitionTypeEnum transitionTypeEnum);

    /**
     * 来源状态
     *
     * @param state 来源状态
     * @return this
     */
    ITransition from(IState state);

    /**
     * 前往状态
     *
     * @param state 前往状态
     * @return this
     */
    ITransition to(IState state);

    /**
     * 条件
     *
     * @param condition 条件
     * @return this
     */
    ITransition condition(ICondition condition);

    /**
     * 动作
     *
     * @param action 动作
     * @return this
     */
    ITransition action(IAction action);

    /**
     * 排序号
     *
     * @param sortId 排序号
     * @return this
     */
    ITransition sortId(Integer sortId);

    /**
     * 获取转换ID
     *
     * @return 转换ID
     */
    String getTransitionId();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    default String getStateMachineId() {
        Class<? extends ITransition> clazz = this.getClass();
        Transition transition = AnnotationUtil.getAnnotation(clazz, Transition.class);

        return ObjectUtil.isNotEmpty(transition) ? transition.stateMachineId() : StrUtil.EMPTY;
    }

    /**
     * 获取排序ID(从小到大)
     *
     * @return 排序ID
     */
    Integer getSortId();

    /**
     * 获取转换类型
     *
     * @return 转换类型
     */
    TransitionTypeEnum getType();

    /**
     * 获取条件
     *
     * @return 条件
     */
    ICondition getCondition();

    /**
     * 获取动作
     *
     * @return 动作
     */
    IAction getAction();

    /**
     * 获取当前状态
     *
     * @return 触发转换的状态
     */
    IState getCurrentState();

    /**
     * 获取下个状态
     *
     * @return 新状态
     */
    IState getNextState();

    /**
     * 转换
     *
     * @param args 动作参数
     * @return 下个状态
     */
    default IState transfer(Object... args) {
        TransitionTypeEnum transitionType = getType();
        IAction action = getAction();
        Boolean invokeIsSuccess = action.invoke(args);
        Assert.isTrue(invokeIsSuccess, "transition[{}]执行失败: action[{}]执行失败", getTransitionId(), action.getActionId());

        return transitionType == TransitionTypeEnum.EXTERNAL ? getNextState() : getCurrentState();
    }

}
