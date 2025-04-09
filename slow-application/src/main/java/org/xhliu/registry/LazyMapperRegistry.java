package org.xhliu.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import tk.mybatis.spring.mapper.MapperFactoryBean;

public class LazyMapperRegistry
        implements BeanDefinitionRegistryPostProcessor {

    private final static Logger log = LoggerFactory.getLogger(LazyMapperRegistry.class);

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (String definitionName : registry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(definitionName);
            if (!(beanDefinition instanceof AbstractBeanDefinition)) continue;
            AbstractBeanDefinition definition = (AbstractBeanDefinition) beanDefinition;
            if (definition.hasBeanClass() && MapperFactoryBean.class.isAssignableFrom(definition.getBeanClass())) {
                definition.setLazyInit(true);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
