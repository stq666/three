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