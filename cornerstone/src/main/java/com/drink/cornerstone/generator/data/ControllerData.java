package com.drink.cornerstone.generator.data;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by newroc on 13-12-5.
 */
public class ControllerData {
    private String packagename;
    private String controllerClassName;
    private String beanName;
    private boolean needContext;
    private List<ActionData> actionDatas=new ArrayList<ActionData>();

    public boolean isNeedContext() {
        return needContext;
    }

    public void setNeedContext(boolean needContext) {
        this.needContext = needContext;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public List<ActionData> getActionDatas() {
        return actionDatas;
    }

    public void setActionDatas(List<ActionData> actionDatas) {
        this.actionDatas = actionDatas;
    }


    public String getControllerClassName() {
        return controllerClassName;
    }

    public void setControllerClassName(String controllerClassName) {
        this.controllerClassName = controllerClassName;
    }

    public String getControllerName(){
     String str=beanName.replaceAll("/","");
        str  = str.substring(0,1).toUpperCase()+str.substring(1);
        return str;
    }


    @Override
    public String toString() {
        return "ControllerData{" +
                "packageName='" + packagename + '\'' +
                ", controllerClassName='" + controllerClassName + '\'' +
                ", actionDatas=" + actionDatas +
                '}';
    }
}
