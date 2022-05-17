package com.mzh.grape.starter.spring.config;

import cn.hutool.extra.spring.SpringUtil;
import com.mzh.grape.starter.spring.holder.StateMachineHolder;
import com.mzh.grape.starter.spring.listener.StateMachineInternalEventListener;
import com.mzh.grape.starter.spring.processor.StateMachineProcessor;
import com.mzh.grape.starter.spring.runner.AutoAssemblingRunner;
import com.mzh.grape.starter.spring.support.StateMachineSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 配置
 *
 * @author zhihao.mao
 */

@Import(SpringUtil.class)
@Configuration
public class GrapeConfig {


    @Bean
    @ConditionalOnMissingBean(StateMachineHolder.class)
    public StateMachineHolder stateMachineHolder() {

        return new StateMachineHolder();
    }

    @Bean
    @ConditionalOnBean(StateMachineHolder.class)
    public StateMachineProcessor stateMachineProcessor() {

        return new StateMachineProcessor();
    }

    @Bean
    @ConditionalOnBean(StateMachineHolder.class)
    public StateMachineSupport stateMachineSupport() {

        return new StateMachineSupport();
    }

    @Bean
    @ConditionalOnBean(StateMachineSupport.class)
    public AutoAssemblingRunner autoAssemblingRunner() {

        return new AutoAssemblingRunner();
    }

    @Bean
    @ConditionalOnBean(StateMachineHolder.class)
    public StateMachineInternalEventListener stateMachineListener() {

        return new StateMachineInternalEventListener();
    }

}
