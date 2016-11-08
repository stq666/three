package com.drink.cornerstone.jms;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.remoting.JmsInvokerClientInterceptor;
import org.springframework.jms.support.JmsUtils;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.util.ClassUtils;

import javax.jms.*;

/**
 * Created by newroc on 14-4-29.
 */
public class MultiServiceJmsInvokerProxyFactoryBean extends JmsInvokerClientInterceptor
        implements FactoryBean<Object>, BeanClassLoaderAware {
    private Class serviceInterface;

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private Object serviceProxy;


    /**
     * Set the interface that the proxy must implement.
     * @param serviceInterface the interface that the proxy must implement
     * @throws IllegalArgumentException if the supplied {@code serviceInterface}
     * is {@code null}, or if the supplied {@code serviceInterface}
     * is not an interface type
     */
    public void setServiceInterface(Class serviceInterface) {
        if (serviceInterface == null || !serviceInterface.isInterface()) {
            throw new IllegalArgumentException("'serviceInterface' must be an interface");
        }
        this.serviceInterface = serviceInterface;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    /**
     * Execute the given remote invocation, sending an invoker request message
     * to this accessor's target queue and waiting for a corresponding response.
     * @param invocation the RemoteInvocation to execute
     * @return the RemoteInvocationResult object
     * @throws JMSException in case of JMS failure
     * @see #doExecuteRequest
     */
    protected RemoteInvocationResult executeRequest(RemoteInvocation invocation) throws JMSException {
        Connection con = createConnection();
        Session session = null;
        try {
            session = createSession(con);
            Message requestMessage = createRequestMessage(session, invocation);
            String des=requestMessage.getStringProperty(JMSConstants.JMS_CONTEXT_KEY_DES);
            String channel=requestMessage.getStringProperty(JMSConstants.JMS_CONTEXT_KEY_CHANNEL);
            Queue queueToUse =resolveQueueName(session,des+"."+channel+".IN");
            requestMessage.setStringProperty(JMSConstants.JMS_MESSAGE_PROPERTY_KEY_SERVICE, JMSConstants.JMS_SERVICE_BEAN_NAME_PREFIX+getObjectType().getName());
            con.start();
            Message responseMessage = doExecuteRequest(session, queueToUse, requestMessage);
            return extractInvocationResult(responseMessage);
        }
        finally {
            JmsUtils.closeSession(session);
            ConnectionFactoryUtils.releaseConnection(con, getConnectionFactory(), true);
        }
    }

    public void afterPropertiesSet() {
        if (getConnectionFactory() == null) {
            throw new IllegalArgumentException("Property 'connectionFactory' is required");
        }
        if (this.serviceInterface == null) {
            throw new IllegalArgumentException("Property 'serviceInterface' is required");
        }
        this.serviceProxy = new ProxyFactory(this.serviceInterface, this).getProxy(this.beanClassLoader);
    }

    public Object getObject() {
        return this.serviceProxy;
    }

    public Class<?> getObjectType() {
        return this.serviceInterface;
    }

    public boolean isSingleton() {
        return true;
    }

}
