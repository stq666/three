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
        //第一步：获取注册新会员的编号
        $scope.member = new Object();
        var onSuccess=function (res, status) {
            $scope.member.serialnumber = res.result.returnObject;
            $("#addModal").modal('show');
        };
        var onError=function (res, status) {

        };
        MemberControllerService.findMaxSerialNumber().then(onSuccess,onError);


    }
}]);