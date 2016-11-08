package com.drink.cornerstone.thread;

import com.drink.cornerstone.jms.JMSContext;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * 发送报文
 * Created by JJP on 2014/6/7.
 */
//public abstract class CommunicativeThread implements Runnable {
//    protected Map<String, Object> map = null;
//    protected JMSContext context = null;
//
//    public CommunicativeThread(JMSContext context, Map<String, Object> map) {
//        this.context = context;
//        this.map = map;
//    }
//}

public abstract class CommunicativeThread implements Runnable {
    Logger logger = Logger.getLogger(this.getClass().getName());
    protected HashMap<String, Object> map = null;
    protected JMSContext context = null;

    public CommunicativeThread(JMSContext context, HashMap<String, Object> map) {
        logger.debug("开始创建新的线程。。。。");
        if(context!=null){
            this.context =new JMSContext();
            this.context.setSrc(context.getSrc());
            this.context.setChannel(context.getChannel());
            this.context.setDes(context.getDes());
        }

        this.map = (HashMap)map.clone();
    }

    @Override
    public abstract void run();
}