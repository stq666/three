package com.drink.cornerstone.jms.xmlbinding;

import com.thoughtworks.xstream.XStream;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

/**
 * Created by newroc on 14-4-29.
 */
public class XmlMapper {
   private XStream xstream;
    public void init(){
         xstream = new XStream();
         addAlias(xstream);
    }

    protected void addAlias(XStream xstream){
        xstream.alias("MSG", RemoteInvocation.class);
    }

//    public String toXML(MessageObject msgObj){
//        return xstream.toXML(msgObj);
//    }

//    public MessageObject fromXML(String msg){
//        MessageObject messageObject= (MessageObject) xstream.fromXML(msg);
//        return messageObject;
//    }

    public String toXML(RemoteInvocation remoteInvocation){
        return xstream.toXML(remoteInvocation);
    }

    public String toXML(RemoteInvocationResult remoteInvocationResult){
        return xstream.toXML(remoteInvocationResult);
    }

    public Object fromXML(String msg){
        Object messageObject= xstream.fromXML(msg);
        return messageObject;
    }
}
