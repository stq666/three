package com.drink.cornerstone.generator.config;

import java.util.List;

/**
 * Created by newroc on 13-12-4.
 */
public class GeneratorConfig {
    private List<ControllerSetConfig> controllerSetConfigs;
    private String templeteDir;
    private String restRpcHelperFilePath;
    private String restRpcHelperPackagename;
    private String rpcClientJSFilePath;


    public String getRpcClientJSFilePath() {
        return rpcClientJSFilePath;
    }

    public void setRpcClientJSFilePath(String rpcClientJSFilePath) {
        this.rpcClientJSFilePath = rpcClientJSFilePath;
    }

    public String getRestRpcHelperPackagename() {
        return restRpcHelperPackagename;
    }

    public void setRestRpcHelperPackagename(String restRpcHelperPackagename) {
        this.restRpcHelperPackagename = restRpcHelperPackagename;
    }

    public String getRestRpcHelperFilePath() {
        return restRpcHelperFilePath;
    }

    public void setRestRpcHelperFilePath(String restRpcHelperFilePath) {
        this.restRpcHelperFilePath = restRpcHelperFilePath;
    }

    public String getTempleteDir() {
        return templeteDir;
    }

    public void setTempleteDir(String templeteDir) {
        this.templeteDir = templeteDir;
    }

    public List<ControllerSetConfig> getControllerSetConfigs() {
        return controllerSetConfigs;
    }

    public void setControllerSetConfigs(List<ControllerSetConfig> controllerSetConfigs) {
        this.controllerSetConfigs = controllerSetConfigs;
    }
}
