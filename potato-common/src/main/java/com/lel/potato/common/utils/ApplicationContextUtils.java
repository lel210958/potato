package com.lel.potato.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>Description: </p>
 *
 * @author liuenlu
 * @version v1.0.0
 * @since 2022/9/21 09:54
 **/
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz){
        return CONTEXT.getBean(clazz);
    }
}
