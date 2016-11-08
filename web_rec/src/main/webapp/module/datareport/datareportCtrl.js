member.controller('MemberCtrl', ['$scope','$state','$window','$filter','MemberControllerService','TableService','localStorageService', function($scope,$state,$window,$filter,MemberControllerService,TableService,localStorageService) {

    $scope.pageSize=10;
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
        MemberControllerService.findMoneybyCondition({
            pageSize:$scope.pageSize,
            currentNum:currentNum,
            name:$scope.searchName,
            startTime:$scope.startTime,
            endTime:$scope.endTime}).then(onSuccess,null);
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

}]);