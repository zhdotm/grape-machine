package com.mzh.grape.starter.spring.config;

import cn.hutool.extra.spring.SpringUtil;
import com.mzh.grape.starter.spring.holder.StateMachineHolder;
import com.mzh.grape.starter.spring.runner.AutoAssemblingRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
    public StateMachineHolder stateMachineHolder() {

        return new StateMachineHolder();
    }

    @ConditionalOnBean(StateMachineHolder.class)
    @Bean
    public AutoAssemblingRunner autoAssemblingRunner() {

        return new AutoAssemblingRunner();
    }

}
