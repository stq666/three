member.controller('GroupmemberCtrl', ['$scope','$state','$window','$filter','MemberControllerService','TableService', function($scope,$state,$window,$filter,MemberControllerService,TableService) {
    $scope.groupId = $state.params.groupid;
    $scope.member = null;
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
        $scope.checkboxes = { 'checked': false, items: {} };
        $scope.member = null;
        $scope.org =null;
        $scope.errorSerFlag = false;
        $scope.errorEmailFlag = false;
        $scope.errorPhoneFlag = false;
        $scope.oldEmail=null
    };
}]);