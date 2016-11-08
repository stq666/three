'use strict';
var appConfig;
(function(){
    appConfig = {
        // "baseURL": "http://192.168.1.121/rest",
        // "baseURL": "http://192.168.1.126/rest",
//        "baseURL": "http://192.168.0.107:8080/rec/rest",
        "baseURL": "http://localhost:8088/rec/rest",
        //"baseURL": "http://www.jiuyoulm.com/rest",
        "loginPath": "/security/login",                                                                                                  
        "logoutPath": "/security/logout",
        "jsErrorHandlerPath": "/jsErrorHandlerPath/log"
    };
})();
var myApp =angular.module('myApp', ['ngRoute','ui.bootstrap','ngResource', 'ngCookies','ngAnimate','ui.router','common-services-rpc','LocalStorageModule',
    'cornerstone-services','common-security','module-member']);
//设置登录拦截器
myApp.config([
    '$routeProvider',
    '$locationProvider',
    '$httpProvider', "SecurityConstants",'localStorageServiceProvider',
    function ($routeProvider, $locationProvider, $httpProvider, SecurityConstants,localStorageServiceProvider) {
        localStorageServiceProvider.setPrefix('myApp').setNotify(true, true);
        var interceptor = ['$location', '$q','$rootScope',"$cookieStore", function ($location, $q, $rootScope,$cookieStore) {
            function success(response) {
                return response;
            }
            function error(response) {
                //服务器端判断用户未登录,返回401状态码时,客户端跳转到登录页面进行登录.
                if (response.status === 401) {
                    //将以前的Cookie信息清除
                    if ($cookieStore.get("user") !== undefined) {
                        $cookieStore.remove("user");
                    }
                    $cookieStore.put("user", {userName: '', perms: []});
                    $rootScope.$broadcast(SecurityConstants.LOGOUT_EVENT);
                    return $q.reject(response);
                } else if (response.status >= 400 && response.status < 500) {
                    //  ErrorService.setError('服务器调用失败!');
                    console.log(response.status+", App.js,调用失败:" + response);
                    return $q.reject(response);
                }
                else {
                    return $q.reject(response);
                }
            }
            return function (promise) {
                return promise.then(success, error);
            };
        }];

        $httpProvider.interceptors.push(interceptor);
    }]);

//监听页面切换事件
myApp.run(["$log","SecurityConstants",'$state', '$location','Auth','$window','$cookieStore','$rootScope',function ($log,SecurityConstants, $state, $location,Auth,$window,$cookieStore,$rootScope) {
    //当前登录 人
    var  objuser= $cookieStore.get("user");
    if(objuser!=null&&objuser!=undefined)
        $rootScope.adminname=objuser.loginName;
    else
        $rootScope.adminname='';

    angular.$rootScope=$rootScope;
    angular.$location=$location;
    $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams) {
        $rootScope.error = null;
        if(fromState!=undefined){
            $window.sessionStorage.referrer=fromState.name;
            $window.sessionStorage.params=fromParams;
        }
        //Auth.isLoggedIn();
        if (toState!=undefined && Auth.getCurrentUser()==null) {
            $window.alert("您还没有登录，请登录后使用！");
            $state.go("/login");
        }
    });
}]);

myApp.controller('MainCtrl', ["$scope","$log","SecurityConstants",'$rootScope', '$location','Auth','$window',function($scope,$log,SecurityConstants, $rootScope, $location,Auth,$window) {
}]);