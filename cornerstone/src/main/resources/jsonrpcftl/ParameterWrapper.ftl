package ${parameterWrapperPackagename};

import com.innovation.cornerstone.rest.model.MRequest;
import  java.io.Serializable;
import com.innovation.cornerstone.rest.model.ParameterWrapper;

public class ${parameterWrapperClassName} extends ParameterWrapper {
<#list parameterDatas as parameter>
private ${parameter.className} ${parameter.name} ;

public void set${parameter.name?cap_first}( ${parameter.className} p${parameter.name}){
    this.${parameter.name}=p${parameter.name};
}

public ${parameter.className} get${parameter.name?cap_first}(){
return this.${parameter.name};
}
 </#list>
}