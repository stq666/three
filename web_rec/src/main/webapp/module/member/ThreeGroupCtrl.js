member.controller('ThreeGroupCtrl', ['$scope','$state','$window','$filter','ThreeGroupControllerService','TableService','localStorageService', function($scope,$state,$window,$filter,ThreeGroupControllerService,TableService,localStorageService) {

    $scope.pageSize=20;
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
        ThreeGroupControllerService.findPageThreeGroupByCondition({
            pageSize:$scope.pageSize,
            currentNum:currentNum,
            groupName:$scope.searchName}).then(onSuccess,null);
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

    $scope.init();
    /**
     * 添加
     */
    $scope.saveThreeGroup=function(){

    }
}]);