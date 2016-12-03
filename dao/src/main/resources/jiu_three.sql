/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50550
Source Host           : localhost:3306
Source Database       : jiu_three

Target Server Type    : MYSQL
Target Server Version : 50550
File Encoding         : 65001

Date: 2016-12-03 16:48:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否被禁用：0:启用，1:禁用',
  `if_manager` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为管理员，0：否，1：是',
  `member_id` bigint(20) DEFAULT NULL COMMENT '用户对应的员工主键',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for three_group
-- ----------------------------
DROP TABLE IF EXISTS `three_group`;
CREATE TABLE `three_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  `pid` bigint(20) DEFAULT NULL COMMENT '父组的主键',
  `group_sort` int(20) DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for three_member
-- ----------------------------
DROP TABLE IF EXISTS `three_member`;
CREATE TABLE `three_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '员工名称',
  `sex` tinyint(2) NOT NULL COMMENT '员工性别，0：男，1：女',
  `serialnumber` varchar(50) NOT NULL COMMENT '会员编号',
  `idcard` varchar(20) NOT NULL COMMENT '身份证号',
  `telphone` varchar(20) NOT NULL COMMENT '手机号',
  `cardno` varchar(30) NOT NULL COMMENT '银行卡号',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `wechat` varchar(255) DEFAULT NULL COMMENT '微信号',
  `qq` varchar(30) DEFAULT NULL COMMENT 'qq号',
  `status` tinyint(2) NOT NULL COMMENT '是否启用：0：启用，1：禁用',
  `alipay` varchar(50) DEFAULT NULL COMMENT '支付宝账号',
  `pid` bigint(20) DEFAULT NULL COMMENT '推荐人主键',
  `group_id` bigint(20) DEFAULT NULL COMMENT '所在组的主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for three_reward
-- ----------------------------
DROP TABLE IF EXISTS `three_reward`;
CREATE TABLE `three_reward` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mid` bigint(20) NOT NULL COMMENT '员工主键',
  `money` int(10) DEFAULT NULL COMMENT '奖励金额',
  `createtime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `type` tinyint(6) NOT NULL COMMENT '类型',
  `rewardtime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
