'use strict';
/**
 * Created by sunye on 14-10-28.
 */
var common_security=angular.module('common-security', ['ngRoute', 'ngResource', 'ngCookies', 'cornerstone-services','common-services-rpc']);
common_security.constant('SecurityConstants', {
    USER_NOT_LOGIN: "NOT_LOGIN",
    returnUrl: "",
    nextUrl:"",
    USER_PERMISSION_ANON: "anon",
    LOGIN_EVENT:"loginEvent",
    LOGOUT_EVENT:"logoutEvent",
    SHOW_MODAL:"showModalEvent",
    HIDE_MODAL:"hideModalEvent",
    TIPS_MODAL:"TIPSMODALEvent",
    TIPS_MODAL_HIDE:"TIPSMODALHIDE"

});

/**
 * @ngdoc service
 * @name app-security.Auth
 * @description
 * 前端与登陆相关的服务。
 */
common_security.service('Auth', function ($rootScope, $cookieStore, $state, $log, CSHttp, SecurityConstants,SecurityControllerService) {
    // var accessLevels = routingConfig.accessLevels;
    // var userRoles = routingConfig.userRoles;

    // var user = $cookieStore.get('user') || { userName: '',  role: SecurityConstants.USER_PERMISSION_ANON };
    // $cookieStore.remove('user');
    var defaultUser = {loginName: '', perms: []};
    //var user = $cookieStore.get('user') || defaultUser;
    var user;
    if ($cookieStore.get("user")) {
        user = $cookieStore.get("user");
    } else {
        user = defaultUser;
        $cookieStore.put("user", user);
    }

    /**
     * 更改内存中的user和cookie中的user
     * @param newUser
     */
    function changeUser(newUser) {
        //  _.extend(user, user);
        user = newUser;
        //将更改的用户信息增加到Cookie中
        if ($cookieStore.get("user") !== undefined) {
            $cookieStore.remove("user");
        }
        $cookieStore.put("user", user);

    }

    /*
     * 设置用户信息
     */
    this.setUser = function(userInfo){
        changeUser(userInfo);
    };
    /*
     * 清除用户信息
     */
    this.clearUser = function(){
        changeUser(defaultUser);
    };

    /**
     * @ngdoc method
     * @name app-security.Auth#getCurrentUser
     * @methodOf app-security.Auth
     * @description
     * 获得当前用户的对象。
     * 用户对象结构：
     * {"perms":["user","wine","transfer","account"],"rememberMe":true,"userId":-2,"userName":"admin"}
     */
    this.getCurrentUser = function () {
        return user;
    };

    this.authorize = function (requestPerms, currentPerms) {
        if (requestPerms.length === 0) {
            return true;
        }
        if (currentPerms === undefined) {
            /*if(user.perms===undefined){
             return false;
             }
             currentPerms = user.perms;*/
            if (user === undefined) {
                return false;
            }
            currentPerms = user.permissions;
        }
        //查找perms中的其中一种内容是否在user.perms中出现过
        for (var i = 0; i < requestPerms.length; i++) {
            //如果权限是匿名的，直接返回true
            if (requestPerms[i] === "anon") {
                return true;
            }
            for (var j = 0; j < currentPerms.length; j++) {
                if (requestPerms[i].trim() === currentPerms[j].trim()) {
                    return true;
                }
            }
        }
        return false;
    };

    /**
     * @ngdoc method
     * @name app-security.Auth#isLoggedIn
     * @methodOf app-security.Auth
     * @description
     * 判断是否已经登陆。
     * 当前判断方式：根据用户信息中的用户名是否为空来判断是否登陆。
     */
    this.isLoggedIn = function () {
        this.login({},null,null);
    };

    function register(user, success, error) {
        CSHttp.post('/register', user).success(function (res) {
            changeUser(res);
            if (success !== undefined) {
                success();
            }
        }).error(error);
    }

    /**
     * @ngdoc method
     * @name app-security.Auth#login
     * @methodOf app-security.Auth
     * @description
     * 登陆方法。
     * 登陆成功与否有三种情况：
     * 1.登陆成功。登陆成功将有$rootScope广播SecurityConstants.LOGIN_EVENT事件。如果success参数不为空，会执行success方法。
     * 2.用户名密码不正确，登陆失败。如果error参数不为空，会执行error方法，并且会在控制台打印出登陆失败信息。
     * 3.没有连上服务器，登陆失败。如果error参数不为空，会执行error方法，并且会在控制台打印出登陆失败信息。
     * @param {Object} loginInfo 用户信息。包含用户名、密码以及是否需要服务器记住账户登录信息。
     * @param {Function} success 登陆成功用户可以执行的函数。
     * @param {Function} error 登陆失败的时候用户可以执行的函数。
     */
    this.login = function (loginInfo, success, error) {
        var errormsg;
        var onSuccess = function (data) {
            if (undefined !== data.result.success && data.result.success) {
                changeUser(data.result.returnObject);

                //将登录事件广播
                $rootScope.$broadcast(SecurityConstants.LOGIN_EVENT);

                if (success !== undefined&&success!=null) {
                    success(data);
                }
            } else {
                $log.error('登录失败' + data.result.errorMessages);
                errormsg = '登录失败' + data.result.errorMessages;
                if (error !== undefined&&error!=null) {
                    if ($cookieStore.get("user") !== undefined) {
                        $cookieStore.remove("user");
                    }
                    user=null;
                    error(data, errormsg);
                }
            }
        };
        var onError = function (data) {
            $log.error('登录失败' + data);
            errormsg = '登录失败' + data;
            if (error !== undefined&&error!=null) {
                error(data, errormsg);
            }
        };
        SecurityControllerService.login(loginInfo).then(onSuccess,onError);
    };

    /**
     * @ngdoc method
     * @name app-security.Auth#logout
     * @methodOf app-security.Auth
     * @description
     * 登出方法。
     * 退出成功的时候会将用户信息换成：{username:"",perms:[]}
     * 退出失败会在控制台打印出失败信息。
     * @param {Function} success 登出成功时，执行的方法。
     * @param {Function} error 登出失败时，执行的方法。
     */
    this.logout = function (success, error) {
        var onSuccess = function (data) {
            /*changeUser({
                username: "",
                perms: []
            });*/
            $state.go('login');
            if (success !== undefined) {
                success(data);
            }
        };
        var onError = function (data) {
            $log.error('退出失败' + data);
        };
        SecurityControllerService.logout().then(onSuccess, onError);
    };

});