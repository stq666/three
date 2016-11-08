/**
 * Created by newroc on 13-11-19.
 */
var cornerstone_services= angular.module('cornerstone-services',[]);
cornerstone_services.factory('CSHttp', function ($http,$q,$log,$rootScope,SecurityConstants,$location,$state) {
    'use strict';
    var CSHttp = {};
    function genErrorMsg(data){
        var error="服务器调用失败";
        if(data==""){
            $state.go("login");
            return null;
        }
        if(data.hasOwnProperty("errorMsg")&&data.errorMsg){
            error =error +"错误信息:" + data.errorMsg;
        }
        if(data.hasOwnProperty("result")&&data.result.errorMessages){
            error =error +"错误信息:" + data.result.errorMessages;
        }

        if(data.hasOwnProperty("errorCode")&&data.errorCode==401){
            $state.go("login");
            return null;
        }else if(data.hasOwnProperty("errorCode")){
            error =error +",错误码:" + data.errorCode;
        }
        if(data.hasOwnProperty("result")&&data.result.errorCode==401){
            $state.go("login");
            return null;
        }else if(data.hasOwnProperty("result")&&data.result.errorCode){
            error =error +"错误码:" + data.result.errorCode;
        }
        return error;
    }

    // Append the right header to the request
    var wrapperData = function (data, context) {
        return {'requestid':(new Date()).valueOf(), 'parameters': data, 'context': context};

    };
    // Do this for each $http call
    angular.forEach(['get', 'delete', 'head', 'jsonp'], function (name) {
        CSHttp[name] = function (url, config) {
            config = config || {};

            return $http[name](url, config);
        };
    });
    angular.forEach(['post', 'put'], function (name) {
        CSHttp[name] = function (url, data, context, config) {
            //监听器 显示modal正在执行操作
            $rootScope.$broadcast(SecurityConstants.SHOW_MODAL);
            var transform = function(data){
                return $.param(data);
            };
            //config = config || {};
            config={
                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                transformRequest: transform
            };
            var deferred = $q.defer();
            var onSuccess= function(data){
                //监听器 隐藏modal正在执行操作
                $rootScope.$broadcast(SecurityConstants.HIDE_MODAL);
                if(data=='' ||data==undefined){
                    $state.go("login");
                    return null;
                }else{
                    if(data.result.success){
                        deferred.resolve(data);
                    }else{
                        var error=genErrorMsg(data);
                        if(error!=null)deferred.reject(data,error);
                    }
                }

            };
            var onError= function(data){
                //监听器 隐藏modal正在执行操作
                //    $rootScope.$broadcast(SecurityConstants.HIDE_MODAL);
                //显示 消息通知
                var error=genErrorMsg(data,status);
                //显示 消息
                //  $rootScope.$broadcast(SecurityConstants.TIPS_MODAL,error);
                if(error!=null)deferred.reject(data,error);
            };
            $http[name](url, data, config).success(onSuccess).error(onError);
            return deferred.promise ;
        };
    });
    return CSHttp;
});

cornerstone_services.factory('safeApply', function() {
    return function($scope, fn) {
        var phase = $scope.$root.$$phase;
        if(phase == '$apply' || phase == '$digest') {
            if (fn) {
                $scope.$eval(fn);
            }
        } else {
            if (fn) {
                $scope.$apply(fn);
            } else {
                $scope.$apply();
            }
        }
    }
});
cornerstone_services.service('SavePageDataService', function () {

    var pageData0;
    var pageData1;
    /**
     * @ngdoc  method
     * @name cornerstone-services.SavePageDataService#getPageData0
     * @methodOf cornerstone-services.SavePageDataService
     * @description
     * getPageData0 方法  得到存放的值
     */
    this.getPageData0= function () {
        return pageData0;
    };
    /**
     * @ngdoc  method
     * @name cornerstone-services.SavePageDataService#setPageData0
     * @methodOf cornerstone-services.SavePageDataService
     * @param {String} value 要存放的值
     * @description
     * setPageData0 方法  要存放的值
     */
    this.setPageData0=function(ppageData0){
        pageData0=ppageData0;
    };
    this.getPageData1= function () {
        return pageData1;
    };

    this.setPageData1=function(ppageData1){
        pageData1=ppageData1;
    };
});
//1.处理table的服务：此table分页
cornerstone_services.service("TableService",['ngTableParams',function(ngTableParams){
    return{
        initPage:function(obj,currentNum,totalSize){
            obj.totalItems = totalSize;
            obj.currentPage = currentNum;
            obj.maxSize = 5;
            obj.bigTotalItems = totalSize;
            obj.bigCurrentPage = currentNum;
        },
        initTable:function(obj,obj1){
            if(obj.tableParams==null||obj.tableParams==undefined){
                obj.tableParams = new ngTableParams({ page: 1,count: obj.pageSize,sorting: {serialnumber: 'desc'}},
                    {
                        counts: [],
                        getData: function($defer, params) {
                            if(obj.master!=null&&!angular.equals(params.$params.sorting, obj.master)){
                                obj.master= angular.copy(params.$params.sorting);
                                var keys=null;
                                for(var key in params.$params.sorting){
                                    keys=key;
                                }
                                obj.order=keys;
                                obj.asc_desc=params.$params.sorting[keys];
                                obj1.page(obj.pageSize);
                                return null;
                            }
                            if(obj.master==null){
                                obj.master= angular.copy(params.$params.sorting);
                            }
                            $defer.resolve(obj.data);

                        }
                    });
            }else{
                obj.tableParams.data=obj.data;
                obj.tableParams.total(obj.data.length);
                obj.tableParams.reload();
            }
        }
    }
}]);
//日期控件的服务
cornerstone_services.service("DateTimePicker",[function(){
    function today(obj){
        obj.dt = new Date();
        obj.bt = new Date();
    };
    function toggleMin(obj){
        obj.minDate = obj.minDate ? null : new Date();
        obj.minDate2 = obj.minDate2 ? null : new Date();
    };
    return{
        init:function(obj){
            today(obj);
            toggleMin(obj);
        },
        clear:function(obj){
            obj.dt = null;
            obj.bt = null;
        },
        disabled:function(date, mode){
            return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
        },
        open:function(obj,$event,index){
            $event.preventDefault();
            $event.stopPropagation();
            index==0?obj.opened = true:obj.opened2 = true;
        },
        dateOptions:{
            formatYear: 'yy',
            startingDay: 1
        }
    }
}]);
cornerstone_services.service('TreeDataService', function () {
    /**
     * 创建树
     *treeId：树id
     * zNodes 节点
     * type 页面中有多少个tree
     * dragMove 是否启动拖拽
     * beforeDrag :退拽前触发
     * ztreeDrop :退拽后出发的方法
     * ifCheck   :是否启动多选或单选框
     * chkStyle:  radio或checkbox
     * defaultClick:是否启用默认点击事件
     * selectNodeId: 默认选择中id=selectNodeid 的节点
     * 树的点击方法
     */
    var findAllNodes=[];
    var findOldNodes=[];
    var firstFind=[];
    var  count=0;
    var searname='';
    this.createTree=function(treeId,zNodes,zTreeOnClick,type,dragMove,beforeDrag,zTreeOnDrop,ifCheck,chkStyle,defaultClick,selectNodeId) {
        if(chkStyle==0){
            chkStyle="radio";
        }else if(chkStyle==1){
            chkStyle="checkbox";
        }
        var setting =null;
        if(ifCheck==undefined||ifCheck==null)ifCheck=false;
        if(dragMove){
            setting={
                check: {enable: ifCheck},
                data: {simpleData: {enable: true}},
                edit:{
                    enable: true,
                    showRemoveBtn: false,
                    showRenameBtn: false,
                    drag:{
                        isMove:true,
                        inner :true,
                        isCopy:false,
                        next  :true,
                        prev :true
                    }
                },
                callback: {
                    onClick:zTreeOnClick,
                    beforeDrag: beforeDrag,
                    onDrop: zTreeOnDrop
                },
                view: {fontCss: getFontCss}
            };
        }else{
            if(ifCheck==undefined||ifCheck==null||ifCheck==false){
                setting={
                    check: {enable: false},
                    data: {simpleData: {enable: true}},
                    callback: { onClick: zTreeOnClick},
                    view: {fontCss: getFontCss}
                };
            }else{
                setting={
                    check: {enable: true,chkStyle: chkStyle,radioType: "all",chkboxType:{'Y':'s','N':'s'}},
                    data: {simpleData: {enable: true}},
                    callback: { onClick: zTreeOnClick},
                    view: {fontCss: getFontCss}
                };
            }
        }
        var treeOBject=  $.fn.zTree.init($("#"+treeId), setting, zNodes);

        var nodetemp="";
        var nodeFlag=false;
        treeOBject.refresh();
        if( selectNodeId!=null&&selectNodeId!='undefined'&& selectNodeId&&selectNodeId!=-1){
            var nodess=treeOBject.getNodesByParam("id",selectNodeId, null);
            if(nodess.length>0){
                nodetemp=nodess[0];
                //展现二级
                if(nodetemp.getParentNode()){
                    $.fn.zTree.getZTreeObj(treeId).expandNode(nodetemp.getParentNode(),true,false,true);
                }else{
                    $.fn.zTree.getZTreeObj(treeId).expandNode(nodetemp,true,false,true);
                }
            }
        }else{
            var nodess=treeOBject.getNodesByParam("pId",null, null);
            if(nodess.length>0){
                nodetemp=nodess[0];
                $.fn.zTree.getZTreeObj(treeId).expandNode(nodetemp,true,false,true);
            }
        }
        $.fn.zTree.getZTreeObj(treeId).selectNode(nodetemp);
        if(defaultClick==undefined||defaultClick)zTreeOnClick(null,treeId,nodetemp);
        init(type);
    };
    /**
     *查找树
     *treeId：树id
     * oldName:查找节点的名字
     * type:绑定tree数的
     */

    function init(type) {
        findAllNodes=[type];
        findOldNodes=[type];
        firstFind=[type];
        count=[type];
        searname=[type];
        for(var i=0;i<findAllNodes.length;i++){
            findAllNodes[i]=[];
        }
        for(var i=0;i<findOldNodes.length;i++){
            findOldNodes[i]=[];
        }
        for(var i=0;i<firstFind.length;i++){
            firstFind[i]=false;
        }

        for(var i=0;i<count.length;i++){
            count[i]=0;
        }
        for(var i=0;i<searname.length;i++){
            searname[i]="";
        }
    }

    /**
     * 查询节点
     * @param treeId
     * @param oldName 查询名字
     * @param type    第几棵树
     */
    this.searchTree=function(treeId,oldName,type) {
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        if(searname[type]!=''){
            if(oldName!=searname[type]){
                firstFind[type]=false;
                count[type]=0;
                findAllNodes[type]=[];
                findOldNodes[type]=[];
                var nodesall = zTree.getNodesByParamFuzzy("name", $.trim(searname[type]));
                updateNodes(zTree,false,nodesall);
            }
        }
        searname[type]=oldName;
        //查找符合条件的节点
        var nodeList = zTree.getNodesByParamFuzzy("name", $.trim(searname[type]));
        /**
         * 定位查找进行高亮的处理
         * 第一步：如果第一次全部高亮，
         */
        if(nodeList!=null && nodeList.length>0){
            if(count[type]<nodeList.length){
                findAllNodes[type]=[];
                for(var i=0;i<nodeList.length;i++){
                    findAllNodes[type].push(nodeList[i]);
                }
            }else{
                count[type]=0;
                findOldNodes[type]=[];
            }
            updateNodes(zTree,true,findAllNodes[type]);
            if(count[type]<nodeList.length){
                if(firstFind[type]){
                    updateNodes(zTree,false,findOldNodes[type]);
                }
                findOldNodes[type].push(nodeList[count[type]]);
                firstFind[type]=true;
            }
            count[type]++;
        }else{
            count[type]=0;
            firstFind[type]=false;
        }
    }


    /**
     * 批量更新节点,将节点高亮
     * <ul>
     *     <li>更新查找的节点，置为高亮</li>
     *     <li>将查找的节点展开</li>
     * </ul>
     * @param highlight
     */
    function updateNodes(zTree,highlight,node) {
        for( var i=0, l=node.length; i<l; i++) {
            node[i].highlight = highlight;
            zTree.updateNode(node[i]);
            zTree.expandNode(node[i].getParentNode(), true, null, null, null);
        }
    }
    /**
     * 改变节点的颜色
     * @param treeId
     * @param treeNode
     * @returns {{color: string, font-weight: string}}
     */
    function getFontCss(treeId, treeNode) {
        return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
    }



});
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
'use strict';
var member =angular.module('module-member', ['ngRoute','ui.bootstrap','ngResource','ui.router', 'ngCookies','ngAnimate','common-services-rpc','LocalStorageModule',
    'angularFileUpload','ngTable','angular-advanced-searchbox']);


member.config([ '$stateProvider', '$urlRouterProvider',function($stateProvider,   $urlRouterProvider){
        $stateProvider .state('login', {url: "/login",templateUrl: "module/member/login.tpl.html",controller: 'LoginCtrl',permissions: ['user']})
            .state('base',{url: "/base",abstract: true,controller:"BaseIndexCtrl",templateUrl: "module/common/index.tpl.html",permissions: ['user']})
            .state('base.welcome', {url: "/welcome",templateUrl:"module/welcome/welcome.tpl.html",controller: 'WelcomeCtrl',permissions: ['user']})
            .state('base.rule', {url: "/rule",templateUrl:"module/welcome/ruleinfo.tpl.html",controller: 'RuleinfoCtrl',permissions: ['user']})
            .state('base.member', {url: "/member",templateUrl:"module/member/member.tpl.html",controller: 'MemberCtrl',permissions: ['user']})
            .state('base.structure', {url: "/structure/:id",templateUrl:"module/member/structure.tpl.html",controller: 'StructureCtrl',permissions: ['user']})
            .state('base.reward', {url: "/reward/:serialnumber",templateUrl:"module/member/reward.tpl.html",controller: 'RewardCtrl',permissions: ['user']})
        ;

    $urlRouterProvider.otherwise("login");
    }
]);

'use strict';
var appConfig;
(function(){
    appConfig = {
        // "baseURL": "http://192.168.1.121/rest",
        // "baseURL": "http://192.168.1.126/rest",
//        "baseURL": "http://192.168.0.107:8080/rec/rest",
//        "baseURL": "http://localhost:8088/rec/rest",
        "baseURL": "http://www.jiuyoulm.com/rest",
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