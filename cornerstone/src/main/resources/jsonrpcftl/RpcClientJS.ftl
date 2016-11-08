var rpc=angular.module('common-services-rpc', ['cornerstone-services','ngCookies']);
function genErrorMsg(data){
    'use strict';
    var error="服务器调用失败";
    if(data.hasOwnProperty("result")&&data.result.hasOwnProperty("errorMessages")){
        error =error +"错误信息:" + data.result.errorMessages;
    }
    if((data.hasOwnProperty("result")&&data.result.hasOwnProperty("errorCode"))){
        error =error +",错误码:" + data.result.errorCode;
    }
    return error;
}

<#list generatorData.controllerSetDatas as controllerSetData>
    <#list controllerSetData.controllerDatas as controllerData>
    rpc.factory('${controllerData.controllerClassName}Service', function (CSHttp, $log, $q) {
        'use strict';

        function ${controllerData.controllerClassName}Service(CSHttp) {
            <#list controllerData.actionDatas as actionData>
                ${controllerData.controllerClassName}Service.prototype.${actionData.actionName}= function (<#list actionData.parameters as parameter>${parameter.name}<#if (parameter_has_next)>,</#if></#list>) {
                    var deferred = $q.defer();
                    var onSuccess = function (data) {
                        if (data.result.success) {
                            deferred.resolve(data);
                        } else {
                           var error= genErrorMsg(data);
                            deferred.reject(data, error);
                        }
                    };
                    var onError = function (data) {
                        var error= genErrorMsg(data);
                        deferred.reject(data, error);
                    };
            <#if (actionData.parameters?size>0) >
                    CSHttp.post(appConfig.baseURL +'${controllerData.beanName}/${actionData.actionName}',<#list actionData.parameters as parameter>${parameter.name}<#if (parameter_has_next)>,</#if></#list>).then(onSuccess,onError);
                <#else>
                    CSHttp.post(appConfig.baseURL +'${controllerData.beanName}/${actionData.actionName}',{}).then(onSuccess,onError);
            </#if>

                    return deferred.promise;
                };
            </#list>
        }
        return new ${controllerData.controllerClassName}Service(CSHttp);
    }) ;
    </#list>
</#list>