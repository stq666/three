package com.drink.cornerstone.generator.data;

/**
 * Created by newroc on 13-12-7.
 */
public class ParameterData {
    private String className;
    private String name;

    public String getClassName() {
        return className;
    }

    public void setClassName(String parameterClassName) {
        this.className = parameterClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String parameterName) {
        this.name = parameterName;
    }

    @Override
    public String toString() {
        return "ParameterData{" +
                "className='" + className + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
