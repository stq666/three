package com.drink.cornerstone.generator.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newroc on 13-12-5.
 */
public class GeneratorData {
    private List<ControllerSetData> controllerSetDatas=new ArrayList<ControllerSetData>();
    private String restRpcHelperFilePath;
    private String restRpcHelperPackagename;
    private String rpcClientJSFilePath;
    private String rpcAndroidProxyPackagename;
    private String rpcAndroidProxyFilePath;


    public String getRpcAndroidProxyPackagename() {
        return rpcAndroidProxyPackagename;
    }

    public void setRpcAndroidProxyPackagename(String rpcAndroidProxyPackagename) {
        this.rpcAndroidProxyPackagename = rpcAndroidProxyPackagename;
    }

    public String getRpcAndroidProxyFilePath() {
        return rpcAndroidProxyFilePath;
    }

    public void setRpcAndroidProxyFilePath(String rpcAndroidProxyFilePath) {
        this.rpcAndroidProxyFilePath = rpcAndroidProxyFilePath;
    }

    public String getRpcClientJSFilePath() {
        return rpcClientJSFilePath;
    }

    public void setRpcClientJSFilePath(String rpcClientJSFilePath) {
        this.rpcClientJSFilePath = rpcClientJSFilePath;
    }

    public String getRestRpcHelperFilePath() {
        return restRpcHelperFilePath;
    }

    public void setRestRpcHelperFilePath(String restRpcHelperFilePath) {
        this.restRpcHelperFilePath = restRpcHelperFilePath;
    }

    public String getRestRpcHelperPackagename() {
        return restRpcHelperPackagename;
    }

    public void setRestRpcHelperPackagename(String restRpcHelperPackagename) {
        this.restRpcHelperPackagename = restRpcHelperPackagename;
    }

    public List<ControllerSetData> getControllerSetDatas() {
        return controllerSetDatas;
    }

    public void setControllerSetDatas(List<ControllerSetData> pcontrollerSetDatas) {
        this.controllerSetDatas = pcontrollerSetDatas;
    }

    @Override
    public String toString() {
        return "GeneratorData{" +
                "controllerSetDatas=" + controllerSetDatas +
                '}';
    }
}
