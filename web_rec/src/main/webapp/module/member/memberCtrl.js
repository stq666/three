member.controller('MemberCtrl', ['$scope','$state','$window','$filter','MemberControllerService','TableService','localStorageService', function($scope,$state,$window,$filter,MemberControllerService,TableService,localStorageService) {

    $scope.pageSize=20;
    $scope.targeFlag = { 'org': false, items: {} };
    $scope.errorSerFlag = false;
    $scope.errorEmailFlag = false;
    $scope.errorPhoneFlag = false;
    $scope.member = null;
    $scope.ifmanager = localStorageService.get('ifmanager');
    /**
     * 获取分页数据
     * @param pageSize
     * @param currentNum
     * @param name
     * @param order
     */
    function page(currentNum){
        var onSuccess=function (data, status) {
            $scope.data = data.result.returnObject.datas;
            TableService.initTable($scope);
            TableService.initPage($scope,currentNum,data.result.returnObject.totalSize);
        };
        MemberControllerService.findPageMember({
            pageSize:$scope.pageSize,
            currentNum:currentNum,
            name:$scope.searchName}).then(onSuccess,null);
    }
    $scope.pageChanged = function() {
        page($scope.bigCurrentPage);
    };

    //初始化
    $scope.init=function(){
        page(1);
        $scope.checkboxes = { 'checked': false, items: {} };
    };

    //改变页面
    $scope.pageChanged = function() {
        page($scope.bigCurrentPage);
    };

    //搜索
    $scope.search=function(){
        $scope.checkboxes = { 'checked': false, items: {} };
        page(1);
    };
    //点击添加用户，注册的第一个人没有推荐人，其余的都有推荐人
    $scope.clickAdd=function(){
        $scope.member = new Object();
        //第一步：获取注册新会员的编号
        var onSuccess=function (res, status) {
            $scope.member.serialnumber = res.result.returnObject;
        };
        var onError=function (res, status) {

        };
        MemberControllerService.findMaxSerialNumber().then(onSuccess,onError);
        if($scope.data!=null && $scope.data.length>0){
            var ids= new Array();
            angular.forEach($scope.checkboxes.items, function(val,key) {
                if(val){
                    ids.push(key);
                }
            });
            if(ids.length!=1){
                bootbox.dialog({
                    message: "<p style='color:#f00; font-size:16px'>请选择推荐人</p>",
                    title: "提示",
                    buttons: {
                        success: {
                            label: "确定",
                            className: "btn-primary"
                        }
                    }
                });
                $scope.checkboxes = { 'checked': false, items: {} };
                return;
            }
            //第二步：填充推荐人的编号
            var member = filterById($scope.data,ids[0]);
            $scope.member.pserialnumber = member.serialnumber;
            $scope.flag = 0;
        }else{
            $scope.member.pserialnumber = 0;
            $scope.flag = 1;
        }
        $("#addModal").modal('show');

    };


    //判断手机是否存在
    $scope.judgePhone=function(){
        if($scope.member==undefined || $scope.member.phone==null || $scope.member.phone=="" || $scope.member.phone==undefined){
            $scope.errorPhoneFlag = false;
        }else{
            if(!(/^1\d{10}$/.test($scope.member.phone))){//判断手机
                $scope.errorPhoneFlag = true;
                $scope.errorPhoneMessage = "手机号码格式不正确";
            }else{
                $scope.errorPhoneFlag = false;
            }
        }

    }
    /**
     * 保存用户
     */
    $scope.saveMember=function(){

        var obj = new Object();
        obj.serialnumber = $scope.member.serialnumber;
        obj.loginname = $scope.member.serialnumber;
        obj.name = $scope.member.name;
        obj.sex = $scope.sexs.id;
        obj.idcard = $scope.member.idcard;
        obj.wechat = $scope.member.wechat;
        obj.alipay = $scope.member.alipay;
        obj.cardno = $scope.member.cardno;
        obj.cardno = $scope.member.cardno;
        obj.telphone = $scope.member.telphone;
        obj.qq = $scope.member.qq;
        obj.flag = $scope.flag;
        obj.pserialnumber = $scope.member.pserialnumber;
        if($scope.member.id!=null && $scope.member.id!=undefined){
            obj.id = $scope.member.id;
        }
        $scope.saveMemberOk(obj);

    };
    /**
     * 保存添加
     * @param obj
     */
    $scope.saveMemberOk= function (obj) {
        var onSuccess=function (res, status) {
            page(1);
            $scope.cancelAdd();
        };
        var onError=function (res, status) {
            $scope.aa = res;
        };
        MemberControllerService.registerMember(obj).then(onSuccess,onError);
    }
    //每天的奖金
    $scope.showEveryDayMoney=function(serialnumber){
        var onSuccess=function (res, status) {
            $scope.noMoneys = res.result.returnObject;
            $("#noMoney").modal('show')
        };
        var onError=function (res, status) {
            $scope.aa = res;
        };
        MemberControllerService.findEveryDayMoney({serialnumber:serialnumber}).then(onSuccess,onError);
    }
    $scope.saveMoney1=function(serialnumber,rewardtime){
        var onSuccess=function (res, status) {
            $scope.showEveryDayMoney(serialnumber);
        };
        var onError=function (res, status) {
            $scope.aa = res;
        };
        MemberControllerService.saveMoneyByRewardDate({serialnumber:serialnumber,rewardtime:$filter('date')(rewardtime, 'yyyy-MM-dd', null)}).then(onSuccess,onError);
    }
    //取消
    $scope.cancelAdd=function(){
        page(1);
        $("#addModal").modal('hide');
        $scope.checkboxes = { 'checked': false, items: {} };
        $scope.member = null;
        $scope.org =null;
        $scope.errorSerFlag = false;
        $scope.errorEmailFlag = false;
        $scope.errorPhoneFlag = false;
        $scope.oldEmail=null
    };
    /**
     * 点击修改弹出框
     */
    $scope.modifyMember=function(){
        var ids= new Array();
        angular.forEach($scope.checkboxes.items, function(val,key) {
            if(val){
                ids.push(key);
            }
        });
        if(ids.length!=1){
            bootbox.dialog({
                message: "<p style='color:#f00; font-size:16px'>只能选择一条进行编辑！</p>",
                title: "提示",
                buttons: {
                    success: {
                        label: "确定",
                        className: "btn-primary"
                    }
                }
            });
            $scope.checkboxes = { 'checked': false, items: {} };
            return;
        }
        $scope.member = filterById($scope.data,ids[0]);
        $scope.flag = 0;
        $scope.sexs = filterById($scope.options1,$scope.member.sex);
        $("#addModal").modal('show');
    };
    /**
     * 查找对象
     * @param users
     * @param id
     * @returns {*}
     */
    function filterById(datas, id) {
        return datas.filter(function (datas) {
            return (datas.id == id);
        })[0];
    }

    /**
     * 根据员工主键过滤用户主键
     * @param datas
     * @param id
     * @returns {*}
     */
    function filterUserByid(datas,id){
        return datas.filter(function (datas) {
            return (datas.id == id);
        })[0];
    }
    /**
     * 监听checkbox
     */
    $scope.$watch('checkboxes.checked', function(value) {
        angular.forEach($scope.data, function(item) {
            if (angular.isDefined(item.id)) {
                $scope.checkboxes.items[item.id] = value;
            }
        });
    });
    /**
     * 点击删除员工
     */
    $scope.deleteMember=function(){
        var ids= new Array();
        angular.forEach($scope.checkboxes.items, function(val,key) {
            if(val){
                ids.push(key);
            }
        });
        if(ids=="" || ids.length==0){
            bootbox.dialog({
                message: "<p style='color:#f00; font-size:16px'>请选择要禁用的员工！</p>",
                title: "提示",
                buttons: {
                    success: {
                        label: "确定",
                        className: "btn-primary"
                    }
                }
            });
            return false;
        }
        bootbox.dialog({
            message: "<p style='color:#f00; font-size:16px'> 确定禁用？</p>",
            title: "提示",
            buttons: {
                main: {
                    label: "是",
                    className: "btn-primary",
                    callback: function () {
                        var onSuccess=function (res, status) {
                            page(1);
                        };
                        MemberControllerService.deletes({ids:ids}).then(onSuccess,null);
                    }
                },
                success: {
                    label: "否",
                    className: "btn-default",
                    callback: function () {
                        $scope.checkboxes = { 'checked': false, items: {} };
                    }
                }

            }
        });
    };
    //重置密码
    $scope.resetPwd=function(){
        var ids= new Array();
        angular.forEach($scope.checkboxes.items, function(val,key) {
            if(val){
                var obj = new Object();
                ids.push(key);
            }
        });
        if(ids=="" || ids.length==0){
            bootbox.dialog({
                message: "<p style='color:#f00; font-size:16px'>请选择要重置密码的会员！</p>",
                title: "提示",
                buttons: {
                    success: {
                        label: "确定",
                        className: "btn-primary"
                    }
                }
            });
            return false;
        }
        bootbox.dialog({
            message: "<p style='color:#f00; font-size:16px'> 确定要重置密码吗？</p>",
            title: "提示",
            buttons: {
                main: {
                    label: "是",
                    className: "btn-primary",
                    callback: function () {
                        var onSuccess=function (res, status) {
//                            page(1);
                        };
                        MemberControllerService.resetPwd({ids:ids}).then(onSuccess,null);
                    }
                },
                success: {
                    label: "否",
                    className: "btn-default",
                    callback: function () {
                        $scope.checkboxes = { 'checked': false, items: {} };
                    }
                }

            }
        });
    }

    $scope.$watch('targeFlag.org', function(value) {
        angular.forEach($scope.orglists, function(item) {
            if (angular.isDefined(item.id)) {
                $scope.targeFlag.items[item.id] = value;
            }
        });
    });

    //添加红包
    $scope.addMoney=function(serialnumber,totalMoney,rewardStatus){
        if(totalMoney==0){
            bootbox.dialog({
                message: "<p style='color:#f00; font-size:16px'>今日奖金为0,不能发放！</p>",
                title: "提示",
                buttons: {
                    success: {
                        label: "确定",
                        className: "btn-primary"
                    }
                }
            });
            return false;
        }
        $scope.serialnumber = serialnumber;
        $scope.rewardStatus = rewardStatus;
        var onSuccess=function (res, status) {
            $scope.moneys = res.result.returnObject;
        };
        var onError=function(res,status){
        };

        MemberControllerService.showRewordDetail({serialnumber:serialnumber}).then(onSuccess,onError);
        $("#addMoney").modal('show');

    }
    //保存红包，更改状态
    $scope.saveMoney=function(){

        var onSuccess=function (res, status) {
            page($scope.bigCurrentPage);
            $scope.cancelMoney();
        };
        var onError=function(res,status){
        };

        MemberControllerService.updateRewardStatus({serialnumber:$scope.serialnumber}).then(onSuccess,onError);
    }
    //取消保存
    $scope.cancelMoney=function(){
        $("#addMoney").modal('hide');
    }
    $scope.init();
}]);