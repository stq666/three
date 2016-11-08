package com.drink.cornerstone.context;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by newroc on 13-11-11.
 */
public class ApplicationContextUtils {


    public static Object getBean(String beanName) {
        return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
    }

    public static WebApplicationContext getWebApplicationContext() {
        return ContextLoader.getCurrentWebApplicationContext();
    }

}
