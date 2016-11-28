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
     * 获取组下的成员
     * @param groupId
     */
    $scope.getMember=function(groupId){
        $("#groupMemsDivId").modal('show');
        var onSuccess=function (data, status) {
            $scope.mems = data.result.returnObject;
        };
        MemberControllerService.findMemberByGroupId({groupId:groupId}).then(onSuccess,null);
    }
    $scope.clickAdd=function(){
        $("#addThreeGroupDivId").modal('show');
    }
    $scope.cancelAdd=function(){
        $scope.groupName = null;
        $("#addThreeGroupDivId").modal('hide');
    }

    /**
     * 添加
     */
    $scope.saveThreeGroup=function(){
        var onSuccess=function (data, status) {
            page(1);
            $("#addThreeGroupDivId").modal('hide');
        };
        ThreeGroupControllerService.saveThreeGroup({groupName:$scope.groupName}).then(onSuccess,null);
    }
}]);