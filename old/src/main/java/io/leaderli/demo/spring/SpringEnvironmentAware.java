package io.leaderli.demo.spring;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * @author leaderli
 * @since 2022/8/18 3:36 PM
 */
public class SpringEnvironmentAware implements EnvironmentAware {
    @Override
    public void setEnvironment(org.springframework.core.env.Environment environment) {

        MutablePropertySources mutablePropertySources = ((ConfigurableEnvironment) environment).getPropertySources();
        mutablePropertySources.addLast(new PropertiesPropertySource("new", new Properties()));
    }
}
