/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 127.0.0.1:3306
 Source Schema         : gmis_stat_0000

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 05/04/2020 13:22:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

/*==============================================================*/
/* Table: TB_PLATFORM_USER                                      */
/*==============================================================*/
drop table if exists TB_PLATFORM_USER;
create table TB_PLATFORM_USER
(
  `id`                   char(32) NOT NULL COMMENT 'ID',
  `role_id`              char(32) DEFAULT NULL COMMENT '角色ID',
  `user_name`            varchar(100) DEFAULT NULL COMMENT '姓名',
  `head_portrait`        varchar(1000) DEFAULT NULL COMMENT '头像',
  `sex`                  smallint COMMENT '性别 0:未知  1：男2：女',
  `telphone`             varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email`                varchar(50) DEFAULT NULL COMMENT '电子邮件',
  `login_code`           varchar(30) DEFAULT NULL COMMENT '登录名称',
  `login_password`       varchar(100) DEFAULT NULL COMMENT '登录密码',
  `remark`               varchar(100) DEFAULT NULL COMMENT '备注',
  `user_status`          smallint COMMENT '0：无效 1：有效',
  `delete_time`          datetime DEFAULT NULL COMMENT '',
  `delete_user_id`       char(32) DEFAULT NULL COMMENT '',
  `last_login_time`      datetime DEFAULT NULL COMMENT '',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台操作员信息';