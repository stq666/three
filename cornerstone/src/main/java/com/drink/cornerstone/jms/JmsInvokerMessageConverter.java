package com.drink.cornerstone.jms;

import com.drink.cornerstone.jms.xmlbinding.XmlMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

import javax.jms.*;
import java.io.UnsupportedEncodingException;

/**
 * Created by newroc on 14-4-29.
 */
public class JmsInvokerMessageConverter extends SimpleMessageConverter {
    private XmlMapper xmlMapper;
    public static Log logger= LogFactory.getLog(JmsInvokerMessageConverter.class);

    public XmlMapper getXmlMapper() {
        return xmlMapper;
    }

    public void setXmlMapper(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        BytesMessage bytesMessage= session.createBytesMessage();
        String msg;
        JMSContext jmsContext=null;
        if(object!=null){
            if(object instanceof RemoteInvocation){
                //请求报文
                RemoteInvocation remoteInvocation= (RemoteInvocation)object;
                Object[] arguments=remoteInvocation.getArguments();
                if(arguments!=null&&arguments.length>0&&arguments[0] instanceof JMSContext){
                    jmsContext= (JMSContext) arguments[0];
                }else{
                    throw new MessageConversionException("第一个参数必须为JMSContext,实际为"+arguments);
                }
                remoteInvocation.addAttribute(JMSConstants.JMS_CONTEXT_KEY_DES, jmsContext.getDes());
                remoteInvocation.addAttribute(JMSConstants.JMS_CONTEXT_KEY_SRC,jmsContext.getSrc());
                remoteInvocation.addAttribute(JMSConstants.JMS_CONTEXT_KEY_CHANNEL,jmsContext.getChannel());
                msg=xmlMapper.toXML(remoteInvocation);

            }else if(object instanceof RemoteInvocationResult){
                //结果报文
                RemoteInvocationResult remoteInvocationResult= (RemoteInvocationResult)object;
                msg=xmlMapper.toXML(remoteInvocationResult);
            }else{
                throw new MessageConversionException("传入的对象类型不是RemoteInvocatio或RemoteInvocationResult，而是:"+object);
            }
            try {
                if(jmsContext!=null){
                    bytesMessage.setStringProperty(JMSConstants.JMS_CONTEXT_KEY_DES, jmsContext.getDes());
                    bytesMessage.setStringProperty(JMSConstants.JMS_CONTEXT_KEY_SRC,jmsContext.getSrc());
                    bytesMessage.setStringProperty(JMSConstants.JMS_CONTEXT_KEY_CHANNEL,jmsContext.getChannel());
                }
                if(logger.isDebugEnabled()){
                    logger.debug("发送报文:"+msg);
                }
                bytesMessage.writeBytes(msg.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new MessageConversionException("不支持的编码类型",e);
            }
        }
        return bytesMessage;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        String msg;
        if(message instanceof TextMessage){
            msg=extractStringFromMessage((TextMessage) message);
        }else if(message instanceof BytesMessage){
            byte[] bytesMsg=extractByteArrayFromMessage((BytesMessage)message);
            try {
                msg=new String(bytesMsg,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new MessageConversionException("不支持的编码类型",e);
            }
        }else{
            throw new MessageConversionException("不支持的消息类型，传入的消息为"+message);
        }
        if(logger.isDebugEnabled()){
            logger.debug("接收到报文:"+msg);
        }
        return xmlMapper.fromXML(msg);
    }
}
