package ${mrequestPackagename};

import com.innovation.cornerstone.rest.model.MRequest;
import ${parameterWrapperPackagename}.${parameterWrapperClassName};

public class ${mrequestClassName} extends MRequest {
private ${parameterWrapperClassName} parameters=new  ${parameterWrapperClassName}();

public ${parameterWrapperClassName} getParameters(){
return parameters;
}

public void setParameters(${parameterWrapperClassName} parms){
this.parameters=parms;
}
}