package com.drink.cornerstone.generator.config;

import java.util.List;

/**
 * Created by newroc on 13-12-4.
 */
public class ControllerSetConfig {
//    public String DEFAULT_SUBPACKAGE_NAME="parm";
    /**
     * 生成代码的根目录
     */
    private String targetPath;
    /**
     * 生成文件的子报名
     */
   // private String subpackageName;
    /**
     * RestController ClassName列表
     */
    private List<String> controllerClassNames;


    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public List<String> getControllerClassNames() {
        return controllerClassNames;
    }

    public void setControllerClassNames(List<String> controllerClassNames) {
        this.controllerClassNames = controllerClassNames;
    }
}
