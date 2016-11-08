package com.drink.cornerstone.generator.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newroc on 13-12-7.
 */
public class ActionData {

    private String actionName;
    private List<ParameterData> parameters=new ArrayList<ParameterData>();
    private String returnType;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public List<ParameterData> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterData> parameters) {
        this.parameters = parameters;
    }


    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return "ActionData{" +
                "actionName='" + actionName + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
