member.controller('LoginCtrl', ['$scope','$rootScope','SecurityControllerService','$window','$state','Auth','$cookieStore','localStorageService', function($scope,$rootScope,SecurityControllerService,$window,$state,Auth,$cookieStore,localStorageService) {

    $scope.show_result=false;
    $scope.result_msg=null;
    var onSuccess=function (data, status) {
        var  objuser= $cookieStore.get("user");
        if(objuser!=null&&objuser!=undefined){
            $rootScope.adminname=objuser.loginName;
            localStorageService.set("ifmanager",objuser.ifmanager);
        }else{
            $rootScope.adminname='';
        }
        if(data.result.success){
            if(objuser.ifmanager==0){//非管理员
                $state.go("base.rule");
            }else{//管理员
                $state.go("base.member");
            }

        }else{
            if(objuser.loginName!=null){
                $scope.show_result=true;
                $scope.result_msg=data.result.errorMessages;
            }
        }
    };
    /**
     * 登陆
     */
    $scope.login = function (user) {

        var onError=function (data, status) {
            $scope.show_result=true;
            $scope.result_msg = data.result.errorCode;
        };
        if(user.hasOwnProperty("password"))$scope.user.password=$scope.user.password;
        Auth.login({loginName:user.loginName,pwd:user.password},onSuccess,onError);
    };
    Auth.login({loginName:null,pwd:null},onSuccess,function(res){

        if(res.result.errorCode=="401")$scope.user={loginName:res.result.errorMessages,password:null};
    });
    if(Auth.getCurrentUser()!=null){
        $scope.user={loginName:Auth.getCurrentUser().loginName,password:null};
    }

}]);