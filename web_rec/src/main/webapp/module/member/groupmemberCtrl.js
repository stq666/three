member.controller('GroupmemberCtrl', ['$scope','$state','$window','$filter','MemberControllerService','TableService', function($scope,$state,$window,$filter,MemberControllerService,TableService) {
    $scope.groupId = $state.params.groupid;
    //初始化
    $scope.init=function(){
        var onSuccess=function (data, status) {
            $scope.groupMems = data.result.returnObject;
        };
        MemberControllerService.findMemberByGroupId({groupId:$scope.groupId}).then(onSuccess,null);
    };
    $scope.init();
}]);