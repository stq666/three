package com.drink.cornerstone.jms;

import java.io.Serializable;

/**
 * Created by newroc on 14-4-29.
 */
public class JMSContext  implements Serializable{


    /**
     * 本机构节点代码
     */
    private String src;
    /**
     * 目标节点代码
     */
    private String des;

    private String channel;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
