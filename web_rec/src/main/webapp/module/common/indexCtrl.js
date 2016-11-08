member.controller('BaseIndexCtrl', ['$scope','$rootScope','$location','SecurityControllerService','$window','$state','Auth','$cookieStore','localStorageService', function($scope,$rootScope,$location,SecurityControllerService,$window,$state,Auth,$cookieStore,localStorageService) {
    'use strict';
    $scope.userName=Auth.getCurrentUser().loginName;
    $scope.ifmanager = localStorageService.get('ifmanager');
    $scope.npwdclue_show1=false;
    $scope.logout=function(){
        Auth.logout();
    };
    $scope.modifyPwd=function(){
        $scope.opwdclue_show=false;
        $scope.npwdclue_show=false;
        $scope.npwdclue_show1=false;
        $("#changePwdForm").modal("show");
    };
    $scope.flag=true;
    $scope.markCss=function(id,url){
        if(id=='rec_1'){
            $scope.rec1=!$scope.rec1;
        }else if(id=='rec_0'){
            $scope.rec0=!$scope.rec0;
        }else if(id=='rec_4'){
            $scope.rec4=!$scope.rec4;
        }else if(id=='rec_6'){
            $scope.rec6=!$scope.rec6;
        }else if(id=='rec_8'){
            $scope.rec8=!$scope.rec8;
        }else if(id=='rec_9'){
            $scope.rec9=!$scope.rec9;
        }else if(id=='rec_12'){
            $scope.rec12=!$scope.rec12;
        }
        $(".verc_left_cur").each(function(i,k){
            $(this).removeClass("verc_left_cur");
        });
        $("#"+id).addClass("verc_left_cur");
        if(url!=null){
            $location.path(url);
        }
    }
    //判断密码
    $scope.checkPassword=function(){
        if($scope.newpassword.length<8){
            $scope.npwdclue_show=true;
        }else{
            $scope.npwdclue_show=false;
        }
    }
    //newpassword!=newpassword1&&(npwdclue_show1==true)||(npwdclue_show1==false)
    $scope.checkPassword1=function(){
        if($scope.newpassword1!=$scope.newpassword){
            $scope.npwdclue_show1=true;
        }else{
            $scope.npwdclue_show1=false;
        }
    }

    /**
     * 修改密码
     */
    $scope.submitChangePwd=function(){
        var onSuccess=function (data, status) {
            bootbox.dialog({
                message: "<p style='color:#3e8f3e; font-size:16px'>密码修改成功,以后需要用新修改的密码登陆，如果忘记密码，请联系管理员！</p>",
                title: "提示",
                buttons: {
                    success: {
                        label: "确定",
                        className: "btn-primary"
                    }
                }
            });
            $scope.cancelChangePwd();

        };
        var onError=function(data,status){
            var error = data.result.errorMessages;
            if(error=='errorPassord'){
                $scope.opwdclue_show=true;
            }else{
                $scope.opwdclue_show=false;
            }
        }
        SecurityControllerService.updatePwd({oldpassword:$scope.oldpassword,newpassword:$scope.newpassword}).then(onSuccess,onError);
    }
    $scope.cancelChangePwd=function(){
        $scope.oldpassword=null;
        $scope.newpassword=null;
        $scope.newpassword1=null;
        $("#changePwdForm").modal("hide");
    }

    /**
     * 切换左侧导航手风琴菜单折叠
     */
    $(document).ready(function () {
        $("dd:first").hide();
        $("dd:not(:first)").hide();
        $("dt a").click(function () {
            $("dd:visible").slideUp("slow");
            $(this).parent().next().slideDown("slow");
            return false;
        });
    });
}]);