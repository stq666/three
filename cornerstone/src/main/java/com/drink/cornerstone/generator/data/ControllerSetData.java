package com.drink.cornerstone.generator.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newroc on 13-12-5.
 */
public class ControllerSetData {

    /**
     * RestController ClassName列表
     */
    private List<ControllerData> controllerDatas=new ArrayList<ControllerData>();


    public List<ControllerData> getControllerDatas() {
        return controllerDatas;
    }

    public void setControllerDatas(List<ControllerData> controllerDatas) {
        this.controllerDatas = controllerDatas;
    }


    @Override
    public String toString() {
        return "ControllerSetData{controllerDatas=" + controllerDatas + '}';
    }
}
