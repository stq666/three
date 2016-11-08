'use strict';
var member =angular.module('module-member', ['ngRoute','ui.bootstrap','ngResource','ui.router', 'ngCookies','ngAnimate','common-services-rpc','LocalStorageModule',
    'angularFileUpload','ngTable','angular-advanced-searchbox']);


member.config([ '$stateProvider', '$urlRouterProvider',function($stateProvider,   $urlRouterProvider){
        $stateProvider .state('login', {url: "/login",templateUrl: "module/member/login.tpl.html",controller: 'LoginCtrl',permissions: ['user']})
            .state('base',{url: "/base",abstract: true,controller:"BaseIndexCtrl",templateUrl: "module/common/index.tpl.html",permissions: ['user']})
            .state('base.welcome', {url: "/welcome",templateUrl:"module/welcome/welcome.tpl.html",controller: 'WelcomeCtrl',permissions: ['user']})
            .state('base.rule', {url: "/rule",templateUrl:"module/welcome/ruleinfo.tpl.html",controller: 'RuleinfoCtrl',permissions: ['user']})
            .state('base.member', {url: "/member",templateUrl:"module/member/member.tpl.html",controller: 'MemberCtrl',permissions: ['user']})
            .state('base.structure', {url: "/structure/:id",templateUrl:"module/member/structure.tpl.html",controller: 'StructureCtrl',permissions: ['user']})
            .state('base.reward', {url: "/reward/:serialnumber",templateUrl:"module/member/reward.tpl.html",controller: 'RewardCtrl',permissions: ['user']})
        ;

    $urlRouterProvider.otherwise("login");
    }
]);
