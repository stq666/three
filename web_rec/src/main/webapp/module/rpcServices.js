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

    rpc.factory('SecurityControllerService', function (CSHttp, $log, $q) {
        'use strict';

        function SecurityControllerService(CSHttp) {
                SecurityControllerService.prototype.login= function (p0,p1,p2,p3) {
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
                    CSHttp.post(appConfig.baseURL +'/security/login',p0,p1,p2,p3).then(onSuccess,onError);

                    return deferred.promise;
                };
                SecurityControllerService.prototype.logout= function () {
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
                    CSHttp.post(appConfig.baseURL +'/security/logout',{}).then(onSuccess,onError);

                    return deferred.promise;
                };
                SecurityControllerService.prototype.findUserInfo= function () {
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
                    CSHttp.post(appConfig.baseURL +'/security/findUserInfo',{}).then(onSuccess,onError);

                    return deferred.promise;
                };
                SecurityControllerService.prototype.updatePwd= function (p0,p1) {
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
                    CSHttp.post(appConfig.baseURL +'/security/updatePwd',p0,p1).then(onSuccess,onError);

                    return deferred.promise;
                };
        }
        return new SecurityControllerService(CSHttp);
    }) ;
    rpc.factory('MemberControllerService', function (CSHttp, $log, $q) {
        'use strict';

        function MemberControllerService(CSHttp) {
                MemberControllerService.prototype.findPageMember= function (p0,p1) {
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
                    CSHttp.post(appConfig.baseURL +'/member/findPageMember',p0,p1).then(onSuccess,onError);

                    return deferred.promise;
                };
                MemberControllerService.prototype.findPageReward= function (p0,p1) {
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
                    CSHttp.post(appConfig.baseURL +'/member/findPageReward',p0,p1).then(onSuccess,onError);

                    return deferred.promise;
                };
                MemberControllerService.prototype.findMaxSerialNumber= function () {
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
                    CSHttp.post(appConfig.baseURL +'/member/findMaxSerialNumber',{}).then(onSuccess,onError);

                    return deferred.promise;
                };
                MemberControllerService.prototype.findEveryDayMoney= function (p0) {
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
                    CSHttp.post(appConfig.baseURL +'/member/findEveryDayMoney',p0).then(onSuccess,onError);

                    return deferred.promise;
                };
                MemberControllerService.prototype.findLonginMember= function () {
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
                    CSHttp.post(appConfig.baseURL +'/member/findLonginMember',{}).then(onSuccess,onError);

                    return deferred.promise;
                };
                MemberControllerService.prototype.registerMember= function (p0) {
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
                    CSHttp.post(appConfig.baseURL +'/member/registerMember',p0).then(onSuccess,onError);

                    return deferred.promise;
                };
                MemberControllerService.prototype.findStructurlAllMemberById= function (p0) {
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
                    CSHttp.post(appConfig.baseURL +'/member/findStructurlAllMemberById',p0).then(onSuccess,onError);

                    return deferred.promise;
                };
                MemberControllerService.prototype.showRewordDetail= function (p0) {
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
                    CSHttp.post(appConfig.baseURL +'/member/showRewordDetail',p0).then(onSuccess,onError);

                    return deferred.promise;
                };
                MemberControllerService.prototype.updateRewardStatus= function (p0) {
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
                    CSHttp.post(appConfig.baseURL +'/member/updateRewardStatus',p0).then(onSuccess,onError);

                    return deferred.promise;
                };
                MemberControllerService.prototype.saveMoneyByRewardDate= function (p0,p1) {
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
                    CSHttp.post(appConfig.baseURL +'/member/saveMoneyByRewardDate',p0,p1).then(onSuccess,onError);

                    return deferred.promise;
                };
                MemberControllerService.prototype.resetPwd= function (p0) {
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
                    CSHttp.post(appConfig.baseURL +'/member/resetPwd',p0).then(onSuccess,onError);

                    return deferred.promise;
                };
        }
        return new MemberControllerService(CSHttp);
    }) ;
