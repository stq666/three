package com.drink.cornerstone.context;

import org.springframework.web.context.ContextCleanupListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Bootstrap listener to start up and shut down Spring's root {@link org.springframework.web.context.WebApplicationContext}.
 * Simply delegates to {@link org.springframework.web.context.ContextLoader} as well as to {@link org.springframework.web.context.ContextCleanupListener}.
 * <p/>
 * <p>This listener should be registered after
 * {@link org.springframework.web.util.Log4jConfigListener}
 * in {@code web.xml}, if the latter is used.
 * <p/>
 * <p>As of Spring 3.1, {@code ContextLoaderListener} supports injecting the root web
 * application context via the {@link #NewContextLoaderListener(org.springframework.web.context.WebApplicationContext)}
 * constructor, allowing for programmatic configuration in Servlet 3.0+ environments. See
 * {@link org.springframework.web.WebApplicationInitializer} for usage examples.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @see org.springframework.web.WebApplicationInitializer
 * @see org.springframework.web.util.Log4jConfigListener
 * Created by newroc on 13-11-8.
 * @since 17.02.2003
 */
public class NewContextLoaderListener extends ContextLoader implements ServletContextListener {

    private ContextLoader contextLoader;


    /**
     * Create a new {@code ContextLoaderListener} that will create a web application
     * context based on the "contextClass" and "contextConfigLocation" servlet
     * context-params. See {@link ContextLoader} superclass documentation for details on
     * default values for each.
     * <p>This constructor is typically used when declaring {@code ContextLoaderListener}
     * as a {@code <listener>} within {@code web.xml}, where a no-arg constructor is
     * required.
     * <p>The created application context will be registered into the ServletContext under
     * the attribute name {@link org.springframework.web.context.WebApplicationContext#ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE}
     * and the Spring application context will be closed when the {@link #contextDestroyed}
     * lifecycle method is invoked on this listener.
     *
     * @see ContextLoader
     * @see #NewContextLoaderListener(org.springframework.web.context.WebApplicationContext)
     * @see #contextInitialized(javax.servlet.ServletContextEvent)
     * @see #contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public NewContextLoaderListener() {
    }

    /**
     * Create a new {@code ContextLoaderListener} with the given application context. This
     * constructor is useful in Servlet 3.0+ environments where instance-based
     * registration of listeners is possible through the {@link javax.servlet.ServletContext#addListener}
     * API.
     * <p>The context may or may not yet be {@linkplain
     * org.springframework.context.ConfigurableApplicationContext#refresh() refreshed}. If it
     * (a) is an implementation of {@link org.springframework.web.context.ConfigurableWebApplicationContext} and
     * (b) has <strong>not</strong> already been refreshed (the recommended approach),
     * then the following will occur:
     * <ul>
     * <li>If the given context has not already been assigned an {@linkplain
     * org.springframework.context.ConfigurableApplicationContext#setId id}, one will be assigned to it</li>
     * <li>{@code ServletContext} and {@code ServletConfig} objects will be delegated to
     * the application context</li>
     * <li>{@link #customizeContext} will be called</li>
     * <li>Any {@link org.springframework.context.ApplicationContextInitializer ApplicationContextInitializer}s
     * specified through the "contextInitializerClasses" init-param will be applied.</li>
     * <li>{@link org.springframework.context.ConfigurableApplicationContext#refresh refresh()} will be called</li>
     * </ul>
     * If the context has already been refreshed or does not implement
     * {@code ConfigurableWebApplicationContext}, none of the above will occur under the
     * assumption that the user has performed these actions (or not) per his or her
     * specific needs.
     * <p>See {@link org.springframework.web.WebApplicationInitializer} for usage examples.
     * <p>In any case, the given application context will be registered into the
     * ServletContext under the attribute name {@link
     * org.springframework.web.context.WebApplicationContext#ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE} and the Spring
     * application context will be closed when the {@link #contextDestroyed} lifecycle
     * method is invoked on this listener.
     *
     * @param context the application context to manage
     * @see #contextInitialized(javax.servlet.ServletContextEvent)
     * @see #contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public NewContextLoaderListener(WebApplicationContext context) {
        super(context);
    }

    /**
     * Initialize the root web application context.
     * 当contextConfigLocation为null时 默认使用classpath*:META-INF/springdev/*.xml,classpath*:META-INF/springcommon/*.xml
     */
    public void contextInitialized(ServletContextEvent event) {
        String myParm = System.getProperty(ContextLoader.CONFIG_LOCATION_PARAM);
        //System.out.println("myParam:"+myParm);
        if ((myParm != null && !myParm.trim().equals(""))) {
            event.getServletContext().setInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, myParm);
        }else{
            //System.out.print("!!!!contextConfigLocation is null，Use default value to init appliaction.  classpath*:META-INF/springcommon/*.xml,classpath*:META-INF/springdev/*.xml,classpath*:META-INF/spring/jms.xml !!!!");
            //event.getServletContext().setInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,"classpath*:META-INF/springcommon/*.xml,classpath*:META-INF/springdev/*.xml,classpath*:META-INF/spring/jms.xml");
            event.getServletContext().setInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,"classpath*:META-INF/springcommon/*.xml,classpath*:META-INF/springdev/*.xml");
        }

        this.contextLoader = createContextLoader();
        if (this.contextLoader == null) {
            this.contextLoader = this;
        }
        WebApplicationContext webApplicationContext = this.contextLoader.initWebApplicationContext(event.getServletContext());
    }

    /**
     * Create the ContextLoader to use. Can be overridden in subclasses.
     *
     * @return the new ContextLoader
     * @deprecated in favor of simply subclassing ContextLoaderListener itself
     * (which extends ContextLoader, as of Spring 3.0)
     */
    @Deprecated
    protected ContextLoader createContextLoader() {
        return null;
    }

    /**
     * Return the ContextLoader used by this listener.
     *
     * @return the current ContextLoader
     * @deprecated in favor of simply subclassing ContextLoaderListener itself
     * (which extends ContextLoader, as of Spring 3.0)
     */
    @Deprecated
    public ContextLoader getContextLoader() {
        return this.contextLoader;
    }


    /**
     * Close the root web application context.
     */
    public void contextDestroyed(ServletContextEvent event) {
        if (this.contextLoader != null) {
            this.contextLoader.closeWebApplicationContext(event.getServletContext());
        }
        ContextCleanupListener contextCleanupListener = new ContextCleanupListener();
        contextCleanupListener.contextDestroyed(event);

    }

}

