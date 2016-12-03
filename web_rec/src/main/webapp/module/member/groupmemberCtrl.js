member.controller('GroupmemberCtrl', ['$scope','$state','$window','$filter','MemberControllerService','TableService', function($scope,$state,$window,$filter,MemberControllerService,TableService) {
    $scope.groupId = $state.params.groupid;
    $scope.member = null;
    $scope.checkboxes = { 'checked': false, items: {} };
    //初始化
    $scope.init=function(){
        var onSuccess=function (data, status) {
            $scope.groupMems = data.result.returnObject;
        };
        MemberControllerService.findMemberByGroupId({groupId:$scope.groupId}).then(onSuccess,null);
    };
    $scope.init();

    $scope.clickAdd=function(){
        if($scope.groupMems.length>=3){
            bootbox.dialog({
                message: "<p style='color:#f00; font-size:16px'>一个小组只能添加3个成员！</p>",
                title: "提示",
                buttons: {
                    success: {
                        label: "确定",
                        className: "btn-primary"
                    }
                }
            });
            return;
        }
        //第一步：获取注册新会员的编号
        $scope.member = new Object();
        var onSuccess=function (res, status) {
            $scope.member.serialnumber = res.result.returnObject;
            $("#addModal").modal('show');
        };
        var onError=function (res, status) {

        };
        MemberControllerService.findMaxSerialNumber().then(onSuccess,onError);
    };
    $scope.saveMember=function(){
        var obj = new Object();
        obj.serialnumber = $scope.member.serialnumber;
        obj.loginname = $scope.member.serialnumber;
        obj.name = $scope.member.name;
        obj.sex = $scope.sexs.id;
        obj.idcard = $scope.member.idcard;
        obj.wechat = $scope.member.wechat;
        //obj.alipay = $scope.member.alipay;
        obj.cardno = $scope.member.cardno;
        obj.telphone = $scope.member.telphone;
        obj.groupId = $scope.groupId;
        //obj.qq = $scope.member.qq;
        //obj.flag = $scope.flag;
        obj.pserialnumber = $scope.member.pserialnumber;
        if($scope.member.id!=null && $scope.member.id!=undefined){
            obj.id = $scope.member.id;
        }
        $scope.saveMemberOk(obj);
    }
    /**
     * 保存添加
     * @param obj
     */
    $scope.saveMemberOk= function (obj) {
        var onSuccess=function (res, status) {
            $scope.init();
            $scope.cancelAdd();
        };
        var onError=function (res, status) {
            $scope.aa = res;
        };
        MemberControllerService.save(obj).then(onSuccess,onError);
    };
    $scope.cancelAdd=function(){
        $scope.init();
        $("#addModal").modal('hide');
    };
    $scope.pflag = true;
    $scope.getPserialnumber=function(serialnumber){
        if(serialnumber==0){
            $scope.pflag = true;
        }else{
            var onSuccess=function (res, status) {
                $scope.pflag = res.result.returnObject;
            };
            var onError=function (res, status) {
                $scope.aa = res;
            };
            MemberControllerService.getPserialnumber({serialnumber:serialnumber}).then(onSuccess,onError);
        }

    };
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

}]);