member.controller('WelcomeCtrl', ['$scope','$rootScope','$window','$state','$filter','MemberControllerService', function($scope,$rootScope,$window,$state,$filter,MemberControllerService) {
    /**
     * 获取公司信息
     */
    $scope.init=function(){
        var onSuccess=function (data, status) {
            $scope.member = data.result.returnObject;
        };
        var onError=function (data, status) {
        };
        MemberControllerService.findLonginMember().then(onSuccess,onError);
    }
    $scope.init();
}]);