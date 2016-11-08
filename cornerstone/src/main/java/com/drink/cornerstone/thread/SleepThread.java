package com.drink.cornerstone.thread;

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

public abstract class SleepThread implements Runnable {
    Logger logger = Logger.getLogger(this.getClass().getName());
    protected HashMap<String, Object> map = null;
    @Override
    public abstract void run();
}