/*
 Navicat Premium Data Transfer

 Source Server         : dev
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 172.16.92.250:3306
 Source Schema         : gmis_base_0000

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 22/12/2020 18:36:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for c_auth_application
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_application`;
CREATE TABLE `c_auth_application`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `client_id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端ID',
  `client_secret` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端密码',
  `website` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '官网',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '应用名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '应用图标',
  `app_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型\n#{SERVER:服务应用;APP:手机应用;PC:PC网页应用;WAP:手机网页应用}\n',
  `describe_` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `status` bit(1) NULL DEFAULT b'1' COMMENT '是否启用',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `UN_APP_KEY`(`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_menu`;
CREATE TABLE `c_auth_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `label` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `describe_` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '描述',
  `is_public` bit(1) NULL DEFAULT b'0' COMMENT '公共菜单\nTrue是无需分配所有人就可以访问的',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件',
  `is_enable` bit(1) NULL DEFAULT b'1' COMMENT '状态',
  `sort_value` int(11) NULL DEFAULT 1 COMMENT '排序',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单图标',
  `group_` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分组',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父级菜单ID',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `INX_STATUS`(`is_enable`, `is_public`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_auth_resource
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_resource`;
CREATE TABLE `c_auth_resource`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '编码\n规则：\n链接：\n数据列：\n按钮：',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID\n#c_auth_menu',
  `describe_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '描述',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_CODE`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_role`;
CREATE TABLE `c_auth_role`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '编码',
  `describe_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '描述',
  `status` bit(1) NULL DEFAULT b'1' COMMENT '状态',
  `readonly` bit(1) NULL DEFAULT b'0' COMMENT '是否内置角色',
  `ds_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'SELF' COMMENT '数据权限类型\n#DataScopeType{ALL:1,全部;THIS_LEVEL:2,本级;THIS_LEVEL_CHILDREN:3,本级以及子级;CUSTOMIZE:4,自定义;SELF:5,个人;}',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_CODE`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_auth_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_role_authority`;
CREATE TABLE `c_auth_role_authority`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `authority_id` bigint(20) NOT NULL COMMENT '资源id\n#c_auth_resource\n#c_auth_menu',
  `authority_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'MENU' COMMENT '权限类型\n#AuthorizeType{MENU:菜单;RESOURCE:资源;}',
  `role_id` bigint(20) NOT NULL COMMENT '角色id\n#c_auth_role',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_KEY`(`role_id`, `authority_type`, `authority_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色的资源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_auth_role_org
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_role_org`;
CREATE TABLE `c_auth_role_org`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID\n#c_auth_role',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID\n#c_core_org',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色组织关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_auth_system_api
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_system_api`;
CREATE TABLE `c_auth_system_api`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `describe_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `request_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `content_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '响应类型',
  `service_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务ID',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求路径',
  `status` bit(1) NULL DEFAULT b'1' COMMENT '状态\n:0-无效 1-有效',
  `is_persist` bit(1) NULL DEFAULT b'0' COMMENT '保留数据 \n0-否 1-是 系统内资数据,不允许删除',
  `is_auth` bit(1) NULL DEFAULT b'1' COMMENT '是否需要认证\n: 0-无认证 1-身份认证 默认:1',
  `is_open` bit(1) NULL DEFAULT b'0' COMMENT '是否公开\n: 0-内部的 1-公开的',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类名',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNX_ID`(`id`) USING BTREE,
  UNIQUE INDEX `UNX_CODE`(`code`) USING BTREE,
  INDEX `service_id`(`service_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'API接口' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_user`;
CREATE TABLE `c_auth_user`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `org_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织ID\n#c_core_org\n@InjectionField(api = ORG_ID_CLASS, method = ORG_ID_METHOD, beanClass = Org.class) RemoteData<Long, Org>',
  `business_ball_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_ball_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `station_id` bigint(20) NULL DEFAULT NULL COMMENT '岗位ID\n#c_core_station\n@InjectionField(api = STATION_ID_CLASS, method = STATION_ID_NAME_METHOD) RemoteData<Long, String>',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '性别\n#Sex{W:女;M:男;N:未知}',
  `status` bit(1) NULL DEFAULT b'0' COMMENT '状态 \n1启用 0禁用',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像',
  `nation` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>',
  `education` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学历\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>',
  `position_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位状态\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>',
  `work_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '工作描述\r\n比如：  市长、管理员、局长等等   用于登陆展示',
  `password_error_last_time` datetime(0) NULL DEFAULT NULL COMMENT '秦川一次输错密码时间',
  `password_error_num` int(11) NULL DEFAULT 0 COMMENT '密码错误次数',
  `password_expire_time` datetime(0) NULL DEFAULT NULL COMMENT '密码过期时间',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '秦川登录时间',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `point_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置类型',
  `point_of_sale` decimal(18, 4) NULL DEFAULT NULL COMMENT '配额',
  `balance` decimal(18, 4) NULL DEFAULT NULL COMMENT '余额',
  `single_limit` decimal(18, 4) NULL DEFAULT NULL COMMENT '单笔限额',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_ACCOUNT`(`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_user_role`;
CREATE TABLE `c_auth_user_role`  (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '角色ID\n#c_auth_role',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户ID\n#c_core_accou',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_KEY`(`role_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色分配\r\n账号角色绑定' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_auth_user_token
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_user_token`;
CREATE TABLE `c_auth_user_token`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  `location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `client_id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端Key',
  `token` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'token',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '登录人ID',
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'token' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_common_area
-- ----------------------------
DROP TABLE IF EXISTS `c_common_area`;
CREATE TABLE `c_common_area`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '编码',
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '全名',
  `sort_value` int(11) NULL DEFAULT 1 COMMENT '排序',
  `longitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '经度',
  `latitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '维度',
  `level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '行政区级\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>\n\n',
  `source_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据来源',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_CODE`(`code`) USING BTREE,
  INDEX `IDX_PARENT_ID`(`parent_id`, `label`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '地区表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_common_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `c_common_dictionary`;
CREATE TABLE `c_common_dictionary`  (
  `id` bigint(20) NOT NULL,
  `type_` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '编码\r\n一颗树仅仅有一个统一的编码',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `describe_` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '描述',
  `status_` bit(1) NULL DEFAULT b'1' COMMENT '状态',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_common_dictionary_item
-- ----------------------------
DROP TABLE IF EXISTS `c_common_dictionary_item`;
CREATE TABLE `c_common_dictionary_item`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dictionary_id` bigint(20) NOT NULL COMMENT '类型ID',
  `dictionary_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '编码',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `status_` bit(1) NULL DEFAULT b'1' COMMENT '状态',
  `describe_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '描述',
  `sort_value` int(11) NULL DEFAULT 1 COMMENT '排序',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dict_code_item_code_uniq`(`dictionary_type`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_common_login_log
-- ----------------------------
DROP TABLE IF EXISTS `c_common_login_log`;
CREATE TABLE `c_common_login_log`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `request_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录IP',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '登录人ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录人姓名',
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录人账号',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录描述',
  `login_date` date NULL DEFAULT NULL COMMENT '登录时间',
  `ua` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '浏览器请求头',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器名称',
  `browser_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器版本',
  `operating_system` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_BROWSER`(`browser`) USING BTREE,
  INDEX `IDX_OPERATING`(`operating_system`) USING BTREE,
  INDEX `IDX_LOGIN_DATE`(`login_date`, `account`) USING BTREE,
  INDEX `IDX_ACCOUNT_IP`(`account`, `request_ip`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_common_opt_log
-- ----------------------------
DROP TABLE IF EXISTS `c_common_opt_log`;
CREATE TABLE `c_common_opt_log`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `request_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作IP',
  `type` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'OPT' COMMENT '日志类型\n#LogType{OPT:操作类型;EX:异常类型}',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作描述',
  `class_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类路径',
  `action_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方法',
  `request_uri` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求地址',
  `http_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'GET' COMMENT '请求类型\n#HttpMethod{GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}',
  `params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回值',
  `ex_desc` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常详情信息',
  `ex_detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常描述',
  `start_time` timestamp(0) NULL DEFAULT NULL COMMENT '开始时间',
  `finish_time` timestamp(0) NULL DEFAULT NULL COMMENT '完成时间',
  `consuming_time` bigint(20) NULL DEFAULT 0 COMMENT '消耗时间',
  `ua` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '浏览器',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_type`(`type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_common_parameter
-- ----------------------------
DROP TABLE IF EXISTS `c_common_parameter`;
CREATE TABLE `c_common_parameter`  (
  `id` bigint(20) NOT NULL,
  `key_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '参数键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '参数名称',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数值',
  `describe_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '描述',
  `status_` bit(1) NULL DEFAULT b'1' COMMENT '状态',
  `readonly_` bit(1) NULL DEFAULT NULL COMMENT '只读',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_KEY`(`key_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_core_org
-- ----------------------------
DROP TABLE IF EXISTS `c_core_org`;
CREATE TABLE `c_core_org`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `abbreviation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '简称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父ID',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ',' COMMENT '树结构',
  `sort_value` int(11) NULL DEFAULT 1 COMMENT '排序',
  `status` bit(1) NULL DEFAULT b'1' COMMENT '状态',
  `describe_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  `delete_user` bigint(20) NULL DEFAULT NULL COMMENT '删除人ID',
  `is_business_hall` smallint(6) NULL DEFAULT 0 COMMENT '是否营业厅',
  PRIMARY KEY (`id`) USING BTREE,
  FULLTEXT INDEX `FU_PATH`(`tree_path`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_core_station
-- ----------------------------
DROP TABLE IF EXISTS `c_core_station`;
CREATE TABLE `c_core_station`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `org_id` bigint(20) NULL DEFAULT 0 COMMENT '组织ID\n#c_core_org',
  `status` bit(1) NULL DEFAULT b'1' COMMENT '状态',
  `describe_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_bank_withhold_record
-- ----------------------------
DROP TABLE IF EXISTS `cb_bank_withhold_record`;
CREATE TABLE `cb_bank_withhold_record`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `gas_meter_type_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表类型id',
  `gas_meter_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表类型名称',
  `gas_meter_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表安装地址',
  `cardholder` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '持卡人',
  `bank_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账号',
  `amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '金额',
  `import_status` smallint(6) NULL DEFAULT NULL COMMENT '导入状态',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '银行代扣记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_compensation_for_read_meter
-- ----------------------------
DROP TABLE IF EXISTS `cb_compensation_for_read_meter`;
CREATE TABLE `cb_compensation_for_read_meter`  (
  `ID` bigint(32) NOT NULL COMMENT 'id',
  `read_meter_data_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表id',
  `compensation_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '补差价',
  `compensation_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '补差量',
  `compensation_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '补差金额',
  `accounting_user_id` bigint(32) NULL DEFAULT NULL COMMENT '核算人id',
  `accounting_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核算人名称',
  `review_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审批人id',
  `review_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人名称',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `review_objection` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批意见',
  `accounting_time` datetime(0) NULL DEFAULT NULL COMMENT '核算时间',
  `charge_status` smallint(6) NULL DEFAULT NULL COMMENT '收费状态',
  `charge_time` datetime(0) NULL DEFAULT NULL COMMENT '收费时间',
  `charge_order_id` bigint(32) NULL DEFAULT NULL COMMENT '收费订单id',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抄表调价补差信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_gas_meter_book_record
-- ----------------------------
DROP TABLE IF EXISTS `cb_gas_meter_book_record`;
CREATE TABLE `cb_gas_meter_book_record`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `number` smallint(5) NOT NULL COMMENT '编号',
  `read_meter_book` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表册id',
  `street_id` bigint(32) NULL DEFAULT NULL COMMENT '街道id',
  `street_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '街道名称',
  `community_id` bigint(32) NULL DEFAULT NULL COMMENT '所属区域id',
  `community_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属区域名称',
  `customer_id` bigint(32) NULL DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `customer_charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户缴费编号',
  `gas_meter_number` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `gas_meter_type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表类型',
  `use_gas_type_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型id',
  `use_gas_type_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `more_gas_meter_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装地址',
  `read_meter_user` bigint(32) NULL DEFAULT NULL COMMENT '抄表员id',
  `read_meter_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表员名称',
  `record_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `sort` int(11) NULL DEFAULT NULL COMMENT '自定义序号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抄表册关联客户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_read_meter_book
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_book`;
CREATE TABLE `cb_read_meter_book`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `community_id` bigint(32) NULL DEFAULT NULL COMMENT '所属区域id',
  `community_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属区域名称',
  `book_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表册编号',
  `book_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表册名称',
  `read_meter_user` bigint(32) NULL DEFAULT NULL COMMENT '抄表员id',
  `read_meter_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表员名称',
  `total_read_meter_count` decimal(18, 0) NULL DEFAULT 0 COMMENT '总户数',
  `book_status` smallint(6) NULL DEFAULT -1 COMMENT '状态(-1：空闲;1：未抄表；0：抄表完成；2：抄表中，)',
  `cited_count` smallint(6) NULL DEFAULT 0 COMMENT '被抄表计划引用次数',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识（0：存在；1：删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抄表册' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_read_meter_data
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_data`;
CREATE TABLE `cb_read_meter_data`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅id',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `more_gas_meter_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装地址',
  `current_cycle_total_use_gas` decimal(18,4) DEFAULT NULL COMMENT '本次周期累积量',
  `plan_id` bigint(32) NULL DEFAULT NULL COMMENT '计划id',
  `book_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表册id',
  `read_meter_year` smallint(6) NULL DEFAULT NULL COMMENT '抄表年份',
  `read_time` date NULL DEFAULT NULL,
  `read_meter_month` smallint(6) NULL DEFAULT NULL COMMENT '抄表月份',
  `last_total_gas` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '上期止数',
  `last_read_time` date NULL DEFAULT NULL COMMENT '上期抄表时间',
  `current_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '本期止数',
  `month_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '本期用量',
  `gas_meter_number` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `cycle_total_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '周期累计用气量',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型id',
  `use_gas_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `price_scheme_id` bigint(32) NULL DEFAULT NULL COMMENT '价格号',
  `process_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程状态',
  `charge_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费状态',
  `data_status` smallint(6) NULL DEFAULT -1 COMMENT '数据状态（-1：未抄表；2-取消；0-已抄）',
  `read_meter_user_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表员id',
  `read_meter_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表员名称',
  `record_user_id` bigint(32) NULL DEFAULT NULL COMMENT '记录员id',
  `record_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记录员名称',
  `record_time` date NULL DEFAULT NULL COMMENT '记录时间',
  `review_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审核人id',
  `review_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人名称',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `review_objection` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `settlement_user_id` bigint(32) NULL DEFAULT NULL COMMENT '结算人id',
  `settlement_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结算人名称',
  `settlement_time` datetime(0) NULL DEFAULT NULL COMMENT '结算时间',
  `gas_owe_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '燃气欠费编号',
  `free_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '收费金额',
  `unit_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '单价',
  `penalty` decimal(18, 4) NULL DEFAULT NULL COMMENT '滞纳金',
  `days_overdue` int(11) NULL DEFAULT NULL COMMENT '超期天数',
  `gas_1` decimal(18, 4) NULL DEFAULT NULL COMMENT '1阶气量',
  `price_1` decimal(18, 4) NULL DEFAULT NULL COMMENT '1阶价格',
  `gas_2` decimal(18, 4) NULL DEFAULT NULL COMMENT '2阶气量',
  `price_2` decimal(18, 4) NULL DEFAULT NULL COMMENT '2阶价格',
  `gas_3` decimal(18, 4) NULL DEFAULT NULL COMMENT '3阶气量',
  `price_3` decimal(18, 4) NULL DEFAULT NULL COMMENT '3阶价格',
  `gas_4` decimal(18, 4) NULL DEFAULT NULL COMMENT '4阶气量',
  `price_4` decimal(18, 4) NULL DEFAULT NULL COMMENT '4阶价格',
  `gas_5` decimal(18, 4) NULL DEFAULT NULL COMMENT '5阶气量',
  `price_5` decimal(18, 4) NULL DEFAULT NULL COMMENT '5阶价格',
  `gas_6` decimal(18, 4) NULL DEFAULT NULL COMMENT '6阶气量',
  `price_6` decimal(18, 4) NULL DEFAULT NULL COMMENT '6阶价格',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `data_bias` int(255) NULL DEFAULT NULL,
  `data_type` smallint(6) NULL DEFAULT 0 COMMENT '抄表数据类型（0-普通抄表数据，1-过户抄表数据，2-拆表抄表数据，3-换表抄表数据）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抄表数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_read_meter_data_error
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_data_error`;
CREATE TABLE `cb_read_meter_data_error`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅id',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `gas_meter_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装地址',
  `plan_id` bigint(32) NULL DEFAULT NULL COMMENT '计划id',
  `book_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表册id',
  `read_meter_year` smallint(6) NULL DEFAULT NULL COMMENT '抄表年份',
  `read_time` date NULL DEFAULT NULL,
  `read_meter_month` smallint(6) NULL DEFAULT NULL COMMENT '抄表月份',
  `last_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '上期止数',
  `current_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '本期止数',
  `month_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '本期用量',
  `gas_meter_number` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `cycle_total_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '周期累计用气量',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型id',
  `use_gas_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `price_scheme_id` bigint(32) NULL DEFAULT NULL COMMENT '价格号',
  `process_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程状态',
  `charge_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费状态',
  `data_status` smallint(6) NULL DEFAULT -1 COMMENT '数据状态（-1：未抄表；2-取消；0-已抄）',
  `read_meter_user_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表员id',
  `read_meter_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表员名称',
  `record_user_id` bigint(32) NULL DEFAULT NULL COMMENT '记录员id',
  `record_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记录员名称',
  `record_time` datetime(0) NULL DEFAULT NULL COMMENT '记录时间',
  `review_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审核人id',
  `review_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人名称',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `review_objection` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `settlement_user_id` bigint(32) NULL DEFAULT NULL COMMENT '结算人id',
  `settlement_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结算人名称',
  `settlement_time` datetime(0) NULL DEFAULT NULL COMMENT '结算时间',
  `gas_owe_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '燃气欠费编号',
  `free_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '收费金额',
  `unit_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '单价',
  `penalty` decimal(18, 4) NULL DEFAULT NULL COMMENT '滞纳金',
  `days_overdue` int(11) NULL DEFAULT NULL COMMENT '超期天数',
  `gas_1` decimal(18, 4) NULL DEFAULT NULL COMMENT '1阶气量',
  `price_1` decimal(18, 4) NULL DEFAULT NULL COMMENT '1阶价格',
  `gas_2` decimal(18, 4) NULL DEFAULT NULL COMMENT '2阶气量',
  `price_2` decimal(18, 4) NULL DEFAULT NULL COMMENT '2阶价格',
  `gas_3` decimal(18, 4) NULL DEFAULT NULL COMMENT '3阶气量',
  `price_3` decimal(18, 4) NULL DEFAULT NULL COMMENT '3阶价格',
  `gas_4` decimal(18, 4) NULL DEFAULT NULL COMMENT '4阶气量',
  `price_4` decimal(18, 4) NULL DEFAULT NULL COMMENT '4阶价格',
  `gas_5` decimal(18, 4) NULL DEFAULT NULL COMMENT '5阶气量',
  `price_5` decimal(18, 4) NULL DEFAULT NULL COMMENT '5阶价格',
  `gas_6` decimal(18, 4) NULL DEFAULT NULL COMMENT '6阶气量',
  `price_6` decimal(18, 4) NULL DEFAULT NULL COMMENT '6阶价格',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `data_bias` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抄表导入错误数据临时记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_read_meter_data_iot
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_data_iot`;
CREATE TABLE `cb_read_meter_data_iot`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅id',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `gas_meter_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装地址',
  `read_meter_year` smallint(6) NULL DEFAULT NULL COMMENT '抄表年份',
  `read_time` date NULL DEFAULT NULL,
  `read_meter_month` smallint(6) NULL DEFAULT NULL COMMENT '抄表月份',
  `last_total_gas` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '上期止数',
  `current_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '本期止数',
  `month_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '本期用量',
  `gas_meter_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `last_cycle_total_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '上次周期累计用气量',
  `cycle_total_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '周期累计用气量',
  `use_money` decimal(18, 2) NULL DEFAULT NULL COMMENT '本次使用金额',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型id',
  `use_gas_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `price_scheme_id` bigint(32) NULL DEFAULT NULL COMMENT '价格号',
  `process_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程状态',
  `charge_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费状态',
  `data_status` smallint(6) NULL DEFAULT -1 COMMENT '数据状态（-1：未抄表；2-取消；0-已抄）',
  `read_meter_user_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表员id',
  `read_meter_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表员名称',
  `record_user_id` bigint(32) NULL DEFAULT NULL COMMENT '记录员id',
  `record_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记录员名称',
  `record_time` date NULL DEFAULT NULL COMMENT '记录时间',
  `review_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审核人id',
  `review_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人名称',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `review_objection` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `settlement_user_id` bigint(32) NULL DEFAULT NULL COMMENT '结算人id',
  `settlement_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结算人名称',
  `settlement_time` datetime(0) NULL DEFAULT NULL COMMENT '结算时间',
  `gas_owe_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '燃气欠费编号',
  `free_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '浮动金额',
  `data_time` datetime(0) NULL DEFAULT NULL COMMENT '数据冻结时间',
  `unit_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '单价',
  `penalty` decimal(18, 4) NULL DEFAULT NULL COMMENT '滞纳金',
  `days_overdue` int(11) NULL DEFAULT NULL COMMENT '超期天数',
  `gas_1` decimal(18, 4) NULL DEFAULT NULL COMMENT '1阶气量',
  `price_1` decimal(18, 4) NULL DEFAULT NULL COMMENT '1阶价格',
  `money_1` decimal(18, 4) NULL DEFAULT NULL,
  `gas_2` decimal(18, 4) NULL DEFAULT NULL COMMENT '2阶气量',
  `price_2` decimal(18, 4) NULL DEFAULT NULL COMMENT '2阶价格',
  `money_2` decimal(18, 4) NULL DEFAULT NULL,
  `gas_3` decimal(18, 4) NULL DEFAULT NULL COMMENT '3阶气量',
  `price_3` decimal(18, 4) NULL DEFAULT NULL COMMENT '3阶价格',
  `money_3` decimal(18, 4) NULL DEFAULT NULL,
  `gas_4` decimal(18, 4) NULL DEFAULT NULL COMMENT '4阶气量',
  `price_4` decimal(18, 4) NULL DEFAULT NULL COMMENT '4阶价格',
  `money_4` decimal(18, 4) NULL DEFAULT NULL,
  `gas_5` decimal(18, 4) NULL DEFAULT NULL COMMENT '5阶气量',
  `price_5` decimal(18, 4) NULL DEFAULT NULL COMMENT '5阶价格',
  `money_5` decimal(18, 4) NULL DEFAULT NULL,
  `gas_6` decimal(18, 4) NULL DEFAULT NULL COMMENT '6阶气量',
  `price_6` decimal(18, 4) NULL DEFAULT NULL COMMENT '6阶价格',
  `money_6` decimal(18, 4) NULL DEFAULT NULL,
  `rel_price_id` bigint(32) NULL DEFAULT NULL COMMENT '结算的真实价格id',
  `rel_use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '结算真实用气类型',
  `frozen_gas` decimal(18,4) DEFAULT NULL COMMENT '冻结量',
  `is_create_arrears` smallint(6) DEFAULT '0' COMMENT '是否生成账单',
  `is_first` smallint(6) DEFAULT '0' COMMENT '是否第一条',
  `meter_type` varchar(255) DEFAULT NULL COMMENT '表类型',
  `data_type` smallint(6) DEFAULT '0' COMMENT '抄表数据类型（0-普通抄表数据，1-过户抄表数据，2-拆表抄表数据，3-换表抄表数据）',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `data_bias` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抄表数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_read_meter_data_iot_error
-- ----------------------------
DROP TABLE IF EXISTS cb_read_meter_data_iot_error;
CREATE TABLE `cb_read_meter_data_iot_error` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) DEFAULT NULL COMMENT '营业厅id',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `customer_id` char(32) DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(30) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` char(32) DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `gas_meter_address` varchar(100) DEFAULT NULL COMMENT '安装地址',
  `read_meter_year` smallint(6) DEFAULT NULL COMMENT '抄表年份',
  `read_time` date DEFAULT NULL,
  `read_meter_month` smallint(6) DEFAULT NULL COMMENT '抄表月份',
  `last_total_gas` decimal(18,4) DEFAULT '0.0000' COMMENT '上期止数',
  `current_total_gas` decimal(18,4) DEFAULT NULL COMMENT '本期止数',
  `month_use_gas` decimal(18,4) DEFAULT NULL COMMENT '本期用量',
  `gas_meter_number` varchar(30) DEFAULT NULL COMMENT '表身号',
  `last_cycle_total_use_gas` decimal(18,4) DEFAULT NULL COMMENT '上次周期累计用气量',
  `cycle_total_use_gas` decimal(18,4) DEFAULT NULL COMMENT '周期累计用气量',
  `use_money` decimal(18,2) DEFAULT NULL COMMENT '本次使用金额',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型id',
  `use_gas_type_name` varchar(50) DEFAULT NULL COMMENT '用气类型名称',
  `price_scheme_id` bigint(32) DEFAULT NULL COMMENT '价格号',
  `process_status` varchar(30) DEFAULT NULL COMMENT '流程状态',
  `charge_status` varchar(30) DEFAULT NULL COMMENT '收费状态',
  `data_status` smallint(6) DEFAULT '0' COMMENT '数据状态（1-有效；0-无效）',
  `read_meter_user_id` bigint(32) DEFAULT NULL COMMENT '抄表员id',
  `read_meter_user_name` varchar(100) DEFAULT NULL COMMENT '抄表员名称',
  `record_user_id` bigint(32) DEFAULT NULL COMMENT '记录员id',
  `record_user_name` varchar(100) DEFAULT NULL COMMENT '记录员名称',
  `record_time` date DEFAULT NULL COMMENT '记录时间',
  `review_user_id` bigint(32) DEFAULT NULL COMMENT '审核人id',
  `review_user_name` varchar(100) DEFAULT NULL COMMENT '审核人名称',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `review_objection` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `settlement_user_id` bigint(32) DEFAULT NULL COMMENT '结算人id',
  `settlement_user_name` varchar(100) DEFAULT NULL COMMENT '结算人名称',
  `settlement_time` datetime DEFAULT NULL COMMENT '结算时间',
  `gas_owe_code` varchar(100) DEFAULT NULL COMMENT '燃气欠费编号',
  `free_amount` decimal(18,4) DEFAULT NULL COMMENT '浮动金额',
  `data_time` datetime DEFAULT NULL COMMENT '数据冻结时间',
  `unit_price` decimal(18,4) DEFAULT NULL COMMENT '单价',
  `penalty` decimal(18,4) DEFAULT NULL COMMENT '滞纳金',
  `days_overdue` int(11) DEFAULT NULL COMMENT '超期天数',
  `gas_1` decimal(18,4) DEFAULT NULL COMMENT '1阶气量',
  `price_1` decimal(18,4) DEFAULT NULL COMMENT '1阶价格',
  `money_1` decimal(18,4) DEFAULT NULL,
  `gas_2` decimal(18,4) DEFAULT NULL COMMENT '2阶气量',
  `price_2` decimal(18,4) DEFAULT NULL COMMENT '2阶价格',
  `money_2` decimal(18,4) DEFAULT NULL,
  `gas_3` decimal(18,4) DEFAULT NULL COMMENT '3阶气量',
  `price_3` decimal(18,4) DEFAULT NULL COMMENT '3阶价格',
  `money_3` decimal(18,4) DEFAULT NULL,
  `gas_4` decimal(18,4) DEFAULT NULL COMMENT '4阶气量',
  `price_4` decimal(18,4) DEFAULT NULL COMMENT '4阶价格',
  `money_4` decimal(18,4) DEFAULT NULL,
  `gas_5` decimal(18,4) DEFAULT NULL COMMENT '5阶气量',
  `price_5` decimal(18,4) DEFAULT NULL COMMENT '5阶价格',
  `money_5` decimal(18,4) DEFAULT NULL,
  `gas_6` decimal(18,4) DEFAULT NULL COMMENT '6阶气量',
  `price_6` decimal(18,4) DEFAULT NULL COMMENT '6阶价格',
  `money_6` decimal(18,4) DEFAULT NULL,
  `rel_price_id` bigint(32) DEFAULT NULL COMMENT '结算的真实价格id',
  `rel_use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '结算真实用气类型',
  `frozen_gas` decimal(18,4) DEFAULT NULL COMMENT '冻结量',
  `is_create_arrears` smallint(6) DEFAULT '0' COMMENT '是否生成账单',
  `is_first` smallint(6) DEFAULT '0' COMMENT '是否第一条',
  `meter_type` varchar(255) DEFAULT NULL COMMENT '表类型',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `data_bias` int(255) DEFAULT NULL,
  `data_type` smallint(6) DEFAULT '0' COMMENT '抄表数据类型（0-普通抄表数据，1-过户抄表数据，2-拆表抄表数据，3-换表抄表数据）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='物联网异常抄表数据';

-- ----------------------------
-- Table structure for cb_read_meter_latest_record
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_latest_record`;
CREATE TABLE `cb_read_meter_latest_record`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `customer_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `meter_charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表缴费编号',
  `gas_meter_number` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `year` smallint(6) NULL DEFAULT NULL COMMENT '年份',
  `month` smallint(6) NULL DEFAULT NULL COMMENT '月份',
  `read_time` date NULL DEFAULT NULL,
  `current_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '本期止数',
  `current_read_time` date NULL DEFAULT NULL COMMENT '抄表时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '每个表具最新抄表数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_read_meter_month_gas
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_month_gas`;
CREATE TABLE `cb_read_meter_month_gas`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `customer_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_number` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `year` smallint(6) NULL DEFAULT NULL COMMENT '年份',
  `jan_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '一月止数',
  `feb_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '二月止数',
  `mar_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '三月止数',
  `apr_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '四月止数',
  `may_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '五月止数',
  `jun_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '六月止数',
  `jul_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '七月止数',
  `aug_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '八月止数',
  `sept_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '九月止数',
  `oct_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '十月止数',
  `nov_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '十一月止数',
  `dec_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '十二月止数',
  `jan_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '一月平均值',
  `feb_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '二月平均值',
  `mar_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '三月平均值',
  `apr_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '四月平均值',
  `may_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '五月平均值',
  `jun_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '六月平均值',
  `jul_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '七月平均值',
  `aug_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '八月平均值',
  `sept_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '九月平均值',
  `oct_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '十月平均值',
  `nov_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '十一月平均值',
  `dec_average` decimal(18, 4) NULL DEFAULT NULL COMMENT '十二月平均值',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抄表每月用气止数记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_read_meter_plan
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_plan`;
CREATE TABLE `cb_read_meter_plan`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `read_meter_year` smallint(6) NULL DEFAULT NULL COMMENT '抄表年份',
  `read_meter_month` smallint(6) NULL DEFAULT NULL COMMENT '抄表月份',
  `plan_start_time` date NULL DEFAULT NULL COMMENT '计划开始时间',
  `plan_end_time` date NULL DEFAULT NULL COMMENT '计划结束时间',
  `total_read_meter_count` int(11) NULL DEFAULT 0 COMMENT '总户数',
  `read_meter_count` int(11) NULL DEFAULT 0 COMMENT '已抄数',
  `review_count` int(11) NULL DEFAULT 0 COMMENT '已审数',
  `settlement_count` int(11) NULL DEFAULT 0 COMMENT '结算数',
  `data_status` smallint(6) NULL DEFAULT -1 COMMENT '状态（-1：未开启；1：执行中；2-暂停；0-执行完成）',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抄表计划' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cb_read_meter_plan_scope
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_plan_scope`;
CREATE TABLE `cb_read_meter_plan_scope`  (
  `ID` bigint(32) NOT NULL COMMENT 'id',
  `readmeter_plan_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表计划id',
  `book_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表册id',
  `book_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表册名称',
  `read_meter_user` bigint(32) NULL DEFAULT NULL COMMENT '抄表员id',
  `read_meter_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表员名称',
  `total_read_meter_count` int(11) NULL DEFAULT 0 COMMENT '总户数',
  `read_meter_count` int(11) NULL DEFAULT 0 COMMENT '已抄数',
  `review_count` int(11) NULL DEFAULT 0 COMMENT '已审数',
  `settlement_count` int(11) NULL DEFAULT 0 COMMENT '结算数',
  `data_status` smallint(6) NULL DEFAULT -1 COMMENT '状态（-1：未抄表；1：抄表中；2-暂停；0-抄表完成）',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间0',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识（0：存在；1：删除）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抄表任务分配' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_batch_gas
-- ----------------------------
DROP TABLE IF EXISTS `da_batch_gas`;
CREATE TABLE `da_batch_gas`  (
  `id` bigint(32) NOT NULL,
  `street_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '街道id',
  `community_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小区id',
  `install_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装编号',
  `gas_factory_id` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表厂家id',
  `version_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号id',
  `buildings` int(32) NULL DEFAULT NULL COMMENT '栋',
  `unit` int(32) NULL DEFAULT NULL COMMENT '单元',
  `storey` int(30) NULL DEFAULT NULL COMMENT '楼层',
  `households` int(30) NULL DEFAULT NULL COMMENT '户数',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装地址',
  `update_time` datetime(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_user` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_card
-- ----------------------------
DROP TABLE IF EXISTS `da_card`;
CREATE TABLE `da_card`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `gas_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `card_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡类型',
  `card_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡编号',
  `card_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡名称',
  `card_count` int(11) NULL DEFAULT NULL COMMENT '卡上次数',
  `card_volume` decimal(18, 0) NULL DEFAULT NULL COMMENT '卡上气量',
  `card_money` decimal(18, 6) NULL DEFAULT NULL COMMENT '卡上金额',
  `alarm_volume` decimal(18, 0) NULL DEFAULT NULL COMMENT '报警气量',
  `remark` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户',
  `delete_status` int(32) NULL DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_customer
-- ----------------------------
DROP TABLE IF EXISTS `da_customer`;
CREATE TABLE `da_customer`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `customer_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户类型ID',
  `customer_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '居民 商福 工业 低保',
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别  MAN表示男 ，WOMEN表示女',
  `id_type_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型ID',
  `id_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型名称',
  `id_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '身份证号码',
  `telphone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `contact_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭住址/单位地址',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `customer_status` smallint(6) NULL DEFAULT NULL COMMENT '客户状态 0.预建档  1. 在用   2.销户',
  `pre_store_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '预存总额',
  `pre_store_count` int(11) NULL DEFAULT NULL COMMENT '预存次数',
  `balance` decimal(18, 4) NULL DEFAULT NULL COMMENT '账户余额',
  `contract_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代扣合同编号',
  `contract_status` bit(1) NULL DEFAULT NULL COMMENT '代扣签约状态',
  `bank` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户行',
  `cardholder` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '持卡人',
  `bank_account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账号',
  `insurance_no` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '保险编号',
  `insurance_end_time` datetime(0) NULL DEFAULT NULL COMMENT '保险到期时间',
  `invoice_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `TIN` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票纳税人识别',
  `invoice_bank_account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票开户行及账号',
  `invoice_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票公司地址',
  `invoice_email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票电子邮箱',
  `delete_status` int(32) NULL DEFAULT NULL COMMENT '逻辑删除',
  `black_status` int(32) NULL DEFAULT NULL COMMENT '黑名单状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  `delete_user` bigint(32) NULL DEFAULT NULL COMMENT '删除人',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_customer_code`(`customer_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_customer_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `da_customer_blacklist`;
CREATE TABLE `da_customer_blacklist`  (
  `id` bigint(32) NOT NULL,
  `customer_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` smallint(6) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(32) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` bigint(32) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_customer_gas_meter_bind
-- ----------------------------
DROP TABLE IF EXISTS `da_customer_gas_meter_bind`;
CREATE TABLE `da_customer_gas_meter_bind`  (
  `id` bigint(20) NOT NULL,
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户档案编号',
  `bind_customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定的其他客户档案编号',
  `bind_gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定的其他客户的气表档案编号',
  `bind_status` tinyint(1) NULL DEFAULT NULL COMMENT '绑定状态：1、绑定  0、解绑',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户气表绑定关系表 - 代缴业务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_customer_gas_meter_related
-- ----------------------------
DROP TABLE IF EXISTS `da_customer_gas_meter_related`;
CREATE TABLE `da_customer_gas_meter_related`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编号',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户档案ID',
  `customer_charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户缴费编号',
  `customer_charge_flag` int(1) NULL DEFAULT 0 COMMENT '客户缴费编号生成标识：1自增，0人工录入',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表档案ID',
  `related_level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '\n#RelateLevelType{MAIN_HOUSEHOLD:主户;QUOTE:引用;TENANT:租户;}',
  `related_deductions` smallint(6) NULL DEFAULT NULL COMMENT '抄表抵扣或银行代扣',
  `oper_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户表具关系操作类型\r\n            开户: OPEN_ACCOUNT\r\n            过户: TRANS_ACCOUNT\r\n            销户: DIS_ACCOUNT\r\n            换表: CHANGE_METER',
  `data_status` smallint(11) NULL DEFAULT 1 COMMENT '数据状态1 有效，0 无效',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_customer_charge_no`(`customer_charge_no`, `customer_code`, `gas_meter_code`) USING BTREE COMMENT '收费编号唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户气表关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_customer_material
-- ----------------------------
DROP TABLE IF EXISTS `da_customer_material`;
CREATE TABLE `da_customer_material`  (
  `id` bigint(32) NOT NULL,
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表具编号',
  `gas_meter_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `material_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID：身份证件\r\n            CONTRACT：合同\r\n            OTHER:其他\r\n            ',
  `material_attachment_id` bigint(20) NULL DEFAULT NULL COMMENT '附件表ID',
  `material_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '材料地址',
  `material_image_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图地址',
  `material_file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原文件名',
  `material_file_extension` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原文件扩展名',
  `data_status` int(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '跟新用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_gas_meter
-- ----------------------------
DROP TABLE IF EXISTS `da_gas_meter`;
CREATE TABLE `da_gas_meter`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `gas_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表表号',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编号',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `gas_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '厂家ID',
  `gas_meter_model_id` bigint(20) NULL DEFAULT NULL COMMENT '气表型号id',
  `gas_meter_version_id` bigint(32) NULL DEFAULT NULL COMMENT '版号ID',
  `install_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `install_time` datetime(0) NULL DEFAULT NULL COMMENT '安装时间',
  `direction` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通气方向',
  `province_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省code',
  `city_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市code',
  `area_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区code',
  `street_id` bigint(32) NULL DEFAULT NULL COMMENT '街道ID',
  `community_id` bigint(32) NULL DEFAULT NULL COMMENT '小区ID',
  `community_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小区名称',
  `gas_meter_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装地址',
  `more_gas_meter_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址供查询用',
  `contract_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合同编号',
  `use_gas_type_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型编码',
  `use_gas_type_id` bigint(20) NULL DEFAULT NULL,
  `use_gas_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `population` int(11) NULL DEFAULT NULL COMMENT '使用人口',
  `node_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调压箱ID',
  `ventilate_status` smallint(6) NULL DEFAULT NULL COMMENT '通气状态',
  `ventilate_user_id` bigint(32) NULL DEFAULT NULL COMMENT '通气人ID',
  `ventilate_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '通气人名称',
  `ventilate_time` datetime(0) NULL DEFAULT NULL COMMENT '通气时间',
  `security_user_id` bigint(32) NULL DEFAULT NULL COMMENT '安全员ID',
  `security_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安全员名称',
  `longitude` decimal(18, 8) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18, 8) NULL DEFAULT NULL COMMENT '纬度',
  `stock_id` bigint(32) NULL DEFAULT NULL COMMENT '库存ID',
  `new_source` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '建档来源\n#ArchiveSourceType{BATCH_CREATE_ARCHIVE:批量建档;IMPORT_ARCHIVE:导入建档;ARTIFICIAL_CREATE_ARCHIVE:人工建档;}',
  `file_id` bigint(32) NULL DEFAULT NULL COMMENT '文件ID',
  `gas_meter_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `auto_generated` int(255) NOT NULL DEFAULT 0 COMMENT '表身号生成方式 0手动填写 1自动生成',
  `gas_meter_real_id` bigint(32) NULL DEFAULT NULL COMMENT '气表真实ID-来自卡表物联网表自身',
  `measurement_accuracy` smallint(6) NULL DEFAULT NULL COMMENT '计费精度',
  `bank_withholding` smallint(6) NULL DEFAULT NULL COMMENT '银行代扣',
  `gas_meter_base` decimal(18, 4) NULL DEFAULT NULL COMMENT '气表底数',
  `pre_built_time` datetime(0) NULL DEFAULT NULL COMMENT '预建档时间',
  `pre_built_user_id` bigint(32) NULL DEFAULT NULL COMMENT '预建档人ID',
  `pre_built_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预建档人名称',
  `open_account_time` datetime(0) NULL DEFAULT NULL COMMENT '开户时间',
  `open_account_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户人',
  `safe_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '防盗扣编号',
  `open_account_user_id` bigint(32) NULL DEFAULT NULL COMMENT '开户人ID',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '表具状态 0.预建档 1，待安装 ，2.已安装，3，已开户，4，已通气，5， 拆除,6.无效',
  `send_card_stauts` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发卡状态   WAITE_SEND 待发卡（ IC卡开户时输入）SENDED  已发卡  （发卡动作输入）',
  `whether_pre_open_account` smallint(6) NULL DEFAULT NULL COMMENT '是否预开户',
  `pre_open_account_state` smallint(6) NULL DEFAULT NULL COMMENT '预开户状态0成功1失败',
  `check_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `check_time` datetime(0) NULL DEFAULT NULL COMMENT '检定时间',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `start_ID` smallint(10) NULL DEFAULT NULL COMMENT '是否启动ID卡，1为不启动，0表示启动',
  `buy_time` datetime(0) NULL DEFAULT NULL COMMENT '购买时间',
  `delete_status` int(10) NULL DEFAULT NULL COMMENT '删除状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remote_service_flag` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '远程业务标识',
  `remove_gasmeter` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '拆表标识',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_gas_code`(`gas_code`) USING BTREE,
  UNIQUE INDEX `uni_gas_meter_number`(`gas_meter_number`, `gas_meter_factory_id`, `remove_gasmeter`) USING BTREE,
  INDEX `uni_factory_id`(`gas_meter_factory_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1344109141647425537 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表档案' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_gas_meter_info
-- ----------------------------
DROP TABLE IF EXISTS `da_gas_meter_info`;
CREATE TABLE `da_gas_meter_info`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司code',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `gas_meter_in_balance` decimal(18, 4) NULL DEFAULT NULL COMMENT '表端余量',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态0作废 1正常',
  `gasmeter_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `initial_measurement_base` decimal(18, 4) NULL DEFAULT NULL COMMENT '初始量',
  `id_card_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID卡号',
  `ic_card_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IC卡号',
  `card_verify_data` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IC卡校验码',
  `total_charge_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '累计充值计量',
  `total_charge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '累计充值金额',
  `total_charge_count` int(11) NULL DEFAULT NULL COMMENT '累计充值次数',
  `total_recharge_meter_count` int(11) NULL DEFAULT NULL COMMENT '累计充值上表次数',
  `total_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '累计用气量',
  `total_use_gas_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '累计用气金额',
  `cycle_charge_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '周期累计充值量',
  `cycle_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '周期累计使用量',
  `current_price` decimal(18,4) DEFAULT NULL COMMENT '当前价格',
  `current_ladder` smallint(6) DEFAULT '1' COMMENT '当前阶梯',
  `alarm_status` smallint(6) DEFAULT '0' COMMENT '报警器状态(0-未连接,1-已连接)',
  `meter_total_gas` decimal(18,4) DEFAULT NULL COMMENT '表端实时累积量',
  `gas_meter_balance` decimal(18, 4) NULL DEFAULT NULL COMMENT '户表账户余额',
  `gas_meter_give` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值赠送余额',
  `price_scheme_id` bigint(32) NULL DEFAULT NULL COMMENT '表端价格号',
  `value_1` decimal(18, 4) NULL DEFAULT NULL COMMENT '上次充值量',
  `value_2` decimal(18, 4) NULL DEFAULT NULL COMMENT '上上次充值量',
  `value_3` decimal(18, 4) NULL DEFAULT NULL COMMENT '上上上次充值量',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表使用情况' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_gas_meter_info_serial
-- ----------------------------
DROP TABLE IF EXISTS `da_gas_meter_info_serial`;
CREATE TABLE `da_gas_meter_info_serial`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `gas_meter_info_id` bigint(32) NULL DEFAULT NULL COMMENT '账户号',
  `gas_meter_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `customer_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `serial_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流水号',
  `bill_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记费记录号( 根据各个场景记录不同的编号)\r\n            例如：收费扣款或者收费预存存放 对应单号。',
  `bill_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收入支出类型：\r\n            预存抵扣\r\n            预存\r\n            抵扣退费\r\n            预存退费\r\n            ',
  `book_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '记账金额',
  `book_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '记账前余额',
  `book_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '记账后余额',
  `give_book_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账金额',
  `give_book_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账前余额',
  `give_book_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账后余额',
  `remark` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流水说明',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '记账人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '记账时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_serial_no`(`serial_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户流水' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_input_output_meter_story
-- ----------------------------
DROP TABLE IF EXISTS `da_input_output_meter_story`;
CREATE TABLE `da_input_output_meter_story`  (
  `ID` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `org_id` bigint(32) NULL DEFAULT NULL,
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `serial_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `gas_meter_factory_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gas_meter_factory_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gas_meter_version_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号id',
  `gas_meter_version_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号名称',
  `gas_meter_type_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '普表\r\n            卡表\r\n            物联网表\r\n            流量计(普表)\r\n            流量计(卡表）\r\n            流量计(物联网表）',
  `gas_meter_type_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` smallint(6) NULL DEFAULT NULL COMMENT '1表示出库\r\n			0表示入库\r\n		',
  `store_count` bigint(20) NULL DEFAULT NULL COMMENT '表示库存数量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '出入库时间',
  `create_user` bigint(32) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` bigint(32) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `uni_serial_code`(`serial_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_meter_stock
-- ----------------------------
DROP TABLE IF EXISTS `da_meter_stock`;
CREATE TABLE `da_meter_stock`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `gas_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '厂家ID',
  `gas_meter_factory_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂家名称',
  `gas_meter_version_id` bigint(32) NULL DEFAULT NULL COMMENT '版号ID',
  `gas_meter_version_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号名称',
  `gas_meter_type_id` bigint(32) NULL DEFAULT NULL COMMENT '表类型',
  `gas_meter_type_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表类型名称 普表 卡表 物联网表 流量计(普表) 流量计(卡表）流量计(物联网表）',
  `buy_gas` datetime(0) NULL DEFAULT NULL COMMENT '购买时间',
  `calibration_time` datetime(0) NULL DEFAULT NULL COMMENT '检定时间',
  `safe_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '防盗扣编码',
  `remark` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更信任',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '删除标识 1-表示删除，0-正常使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '表具库存信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_node
-- ----------------------------
DROP TABLE IF EXISTS `da_node`;
CREATE TABLE `da_node`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司code',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `parent_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父节点ID',
  `node_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `node_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '节点编号',
  `node_base` decimal(18, 4) NULL DEFAULT NULL COMMENT '节点底数',
  `node_factory_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂家',
  `node_type_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '型号',
  `caliber` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管道口径',
  `upper_temperature_limit` int(11) NULL DEFAULT NULL COMMENT '温度上限',
  `lower_temperature_limit` int(11) NULL DEFAULT NULL COMMENT '温度下限',
  `upper_pressure_limit` int(11) NULL DEFAULT NULL COMMENT '压力上限',
  `lower_pressure_limit` int(11) NULL DEFAULT NULL COMMENT '压力下限',
  `upper_flow_limit` int(11) NULL DEFAULT NULL COMMENT '流量上限',
  `lower_flow_limit` int(11) NULL DEFAULT NULL COMMENT '流量下限',
  `longitude` decimal(18, 8) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18, 8) NULL DEFAULT NULL COMMENT '纬度',
  `principal_user_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人ID',
  `principal_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人名称',
  `telphone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `alarm_start_time` datetime(0) NULL DEFAULT NULL COMMENT '报警开始时间',
  `update_alarm_time` datetime(0) NULL DEFAULT NULL COMMENT '最新报警时间',
  `alarm_status` smallint(6) NULL DEFAULT NULL COMMENT '报警状态',
  `alarm_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警信息',
  `temperature` decimal(18, 4) NULL DEFAULT NULL COMMENT '温度',
  `pressure` decimal(18, 4) NULL DEFAULT NULL COMMENT '压力',
  `instantaneous_flow_rate` decimal(18, 4) NULL DEFAULT NULL COMMENT '瞬时流量',
  `working_flow` decimal(18, 4) NULL DEFAULT NULL COMMENT '工况流量',
  `standard_flow` decimal(18, 4) NULL DEFAULT NULL COMMENT '标况流量',
  `total_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '累计用气量',
  `data_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `yesterday_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '昨日止数',
  `yesterday_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '昨日用量',
  `last_month_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '上月止数',
  `last_month_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '上月用量',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `delete_status` int(32) NULL DEFAULT NULL COMMENT '删除标识',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  `delete_user` bigint(32) NULL DEFAULT NULL COMMENT '删除人',
  `install_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_purchase_limit_customer
-- ----------------------------
DROP TABLE IF EXISTS `da_purchase_limit_customer`;
CREATE TABLE `da_purchase_limit_customer`  (
  `id` bigint(32) NOT NULL,
  `limit_id` bigint(32) NULL DEFAULT NULL COMMENT '限购ID',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户ID',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户id',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识1-删除,0-正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '限购客户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for f_attachment
-- ----------------------------
DROP TABLE IF EXISTS `f_attachment`;
CREATE TABLE `f_attachment`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务ID',
  `biz_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务类型\n#AttachmentType',
  `data_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'IMAGE' COMMENT '数据类型\n#DataType{DIR:目录;IMAGE:图片;VIDEO:视频;AUDIO:音频;DOC:文档;OTHER:其他}',
  `submitted_file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '原始文件名',
  `group_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'FastDFS返回的组\n用于FastDFS',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'FastDFS的远程文件名\n用于FastDFS',
  `relative_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件相对路径',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件访问链接\n需要通过nginx配置路由，才能访问',
  `file_md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件md5值',
  `context_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件上传类型\n取上传文件的值',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '唯一文件名',
  `ext` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '后缀\n (没有.)',
  `size` bigint(20) NULL DEFAULT 0 COMMENT '大小',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织ID\n#c_core_org',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '图标',
  `create_month` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建年月\n格式：yyyy-MM 用于统计',
  `create_week` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时处于当年的第几周\nyyyy-ww 用于统计',
  `create_day` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建年月日\n格式： yyyy-MM-dd 用于统计',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(11) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '秦川修改时间',
  `update_user` bigint(11) NULL DEFAULT NULL COMMENT '秦川修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '附件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for f_file
-- ----------------------------
DROP TABLE IF EXISTS `f_file`;
CREATE TABLE `f_file`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `data_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'IMAGE' COMMENT '数据类型\n#DataType{DIR:目录;IMAGE:图片;VIDEO:视频;AUDIO:音频;DOC:文档;OTHER:其他}',
  `submitted_file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '原始文件名',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ',' COMMENT '父目录层级关系',
  `grade` int(11) NULL DEFAULT 1 COMMENT '层级等级\n从1开始计算',
  `is_delete` bit(1) NULL DEFAULT b'0' COMMENT '是否删除\n#BooleanStatus{TRUE:1,已删除;FALSE:0,未删除}',
  `folder_id` bigint(20) NULL DEFAULT 0 COMMENT '父文件夹ID',
  `url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件访问链接\n需要通过nginx配置路由，才能访问',
  `size` bigint(20) NULL DEFAULT 0 COMMENT '文件大小\n单位字节',
  `folder_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '父文件夹名称',
  `group_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'FastDFS组\n用于FastDFS',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'FastDFS远程文件名\n用于FastDFS',
  `relative_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件的相对路径 ',
  `file_md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'md5值',
  `context_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件类型\n取上传文件的值',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '唯一文件名',
  `ext` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件名后缀 \n(没有.)',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件图标\n用于云盘显示',
  `create_month` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时年月\n格式：yyyy-MM 用于统计',
  `create_week` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时年周\nyyyy-ww 用于统计',
  `create_day` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时年月日\n格式： yyyy-MM-dd 用于统计',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '秦川修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '秦川修改人',
  PRIMARY KEY (`id`) USING BTREE,
  FULLTEXT INDEX `FU_TREE_PATH`(`tree_path`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_gas_install_file
-- ----------------------------
DROP TABLE IF EXISTS `gc_gas_install_file`;
CREATE TABLE `gc_gas_install_file`  (
  `id` bigint(32) NOT NULL,
  `install_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装编号',
  `material_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID：身份证件\r\n            CONTRACT：合同\r\n            OTHER:其他\r\n            ',
  `material_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '材料地址',
  `material_image_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图地址',
  `material_file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原文件名',
  `material_file_extension` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原文件扩展名',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报装资料' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_install_charge_detail
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_charge_detail`;
CREATE TABLE `gc_install_charge_detail`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `install_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装编号',
  `charge_item_id` bigint(32) NOT NULL COMMENT '收费项目ID',
  `charge_item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称',
  `charge_state` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费状态\r\n            WAIT_CHARGE: 等待收费\r\n            CHARGED: 已收费\r\n            FREE_CHARGE: 免收费\r\n            ',
  `amount` decimal(32, 4) NULL DEFAULT NULL COMMENT '金额',
  `delete_status` int(2) NULL DEFAULT NULL COMMENT '删除状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报装费用清单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_install_design
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_design`;
CREATE TABLE `gc_install_design`  (
  `id` bigint(20) NOT NULL,
  `install_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装编码',
  `sketch_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '草图地址',
  `sketch_user` bigint(20) NULL DEFAULT NULL COMMENT '草图设计人',
  `design_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设计图地址',
  `design_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设计图设计人name',
  `design_user` bigint(20) NULL DEFAULT NULL COMMENT '设计图设计人',
  `budget` decimal(10, 0) NULL DEFAULT NULL COMMENT '预算金额',
  `budget_user` bigint(20) NULL DEFAULT NULL COMMENT '预算人',
  `budget_time` datetime(0) NULL DEFAULT NULL COMMENT '预算时间',
  `workload` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '施工工作量描述',
  `process_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程状态(TO_BE_REVIEWED-待审核,SUBMIT_FOR_REVIEW-提审,REVIEW_REJECTED-审核驳回,APPROVED-审核通过)',
  `review_user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人id',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `review_objection` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `data_status` smallint(6) NULL DEFAULT 1 COMMENT '数据状态1-有效，0-无效',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报装设计及预算' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_install_detail
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_detail`;
CREATE TABLE `gc_install_detail`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `install_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装编号',
  `province_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省CODE',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省名称',
  `city_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市ID',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市名称',
  `area_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区县CODE',
  `area_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域名称',
  `street_id` bigint(32) NULL DEFAULT NULL COMMENT '街道(乡镇)ID',
  `street_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '街道(乡镇)名称',
  `community_id` bigint(32) NULL DEFAULT NULL COMMENT '小区(村)ID',
  `community_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小区(村)名称',
  `address_detail` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `gas_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '厂家ID',
  `gas_meter_factory_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂家名称',
  `gas_meter_version_id` bigint(32) NULL DEFAULT NULL COMMENT '版号ID',
  `gas_meter_version_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号名称',
  `gas_meter_model_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表型号ID',
  `gas_meter_model_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表型号名称',
  `use_gas_type_id` bigint(20) NULL DEFAULT NULL COMMENT '用气类型id',
  `use_gas_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型编号',
  `use_gas_type_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `surge_tank_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调压箱编号',
  `gas_meter_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表具编码',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '施工安装明细信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_install_order_record
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_order_record`;
CREATE TABLE `gc_install_order_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `install_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装编号',
  `work_order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单编号',
  `work_order_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单类型\r\n            survey: 踏勘\r\n            install: 施工',
  `urgency` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '紧急程度\r\n            normal:正常\r\n            urgent:紧急\r\n            very_urgent:非常紧急',
  `receive_user_id` bigint(32) NULL DEFAULT NULL COMMENT '接单人ID',
  `receive_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接单人名称',
  `receive_user_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '接单时间',
  `work_order_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单内容',
  `invalid_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单作废原因：转单，拒单，退单原因描述',
  `termination_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终止原因：业务终止',
  `end_job_user_id` bigint(32) NULL DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime(0) NULL DEFAULT NULL COMMENT '结单时间',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `order_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单状态\r\n            WAITE_COMPLETE: 待结单\r\n            COMPLETE:已结单\r\n            INVALID: 已作废',
  `oper_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单操作状态\r\n            WAITE_RECEIVE: 待接单\r\n            RECEIVE: 已接单\r\n            REJECT: 已拒单\r\n            BACK:已退单\r\n            TERMINATION:已终止\r\n            COMPLETE:已结单\r\n            ',
  `turn_id` bigint(32) NULL DEFAULT NULL COMMENT '转单ID',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报装工单记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_install_process_record
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_process_record`;
CREATE TABLE `gc_install_process_record`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `install_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装编号',
  `process_state` smallint(6) NULL DEFAULT NULL COMMENT '流程状态',
  `process_user_id` bigint(32) NULL DEFAULT NULL COMMENT '操作人员ID',
  `process_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人员名称',
  `process_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `process_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `create_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报装流程操作记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_install_record
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_record`;
CREATE TABLE `gc_install_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `install_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装编号',
  `install_type_id` bigint(32) NULL DEFAULT NULL COMMENT '报装类型ID：数字类型（字典表ID）\r\n            SFINSTALL:商福报装\r\n            GYINSTALL:工业报装\r\n            LSINSTALL:零散报装\r\n            JZINSTALL:集中报装',
  `install_type_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装类型名称\r\n            SFINSTALL:商福报装\r\n            GYINSTALL:工业报装\r\n            LSINSTALL:零散报装\r\n            JZINSTALL:集中报装',
  `city_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市CODE',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市名称',
  `area_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区县CODE',
  `area_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区县名称',
  `street_id` bigint(20) NULL DEFAULT NULL COMMENT '街道（乡镇）ID',
  `street_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '街道（乡镇）',
  `community_id` bigint(20) NULL DEFAULT NULL COMMENT '小区（村）ID',
  `community_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小区（村）',
  `storied_id` bigint(20) NULL DEFAULT NULL COMMENT '楼栋（组）',
  `storied_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '楼栋（组）',
  `floor_num` int(11) NULL DEFAULT NULL COMMENT '楼层',
  `population` int(11) NULL DEFAULT NULL COMMENT '户数',
  `address_remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址备注',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装人',
  `telphone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `id_type_id` bigint(32) NULL DEFAULT NULL COMMENT '证件类型ID：数字类型(字典表ID)\r\n            ICID:身份证\r\n            BLID:营业执照\r\n            ',
  `id_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型ID：\r\n            ICID:身份证\r\n            BLID:营业执照',
  `id_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `accept_user` bigint(20) NULL DEFAULT NULL COMMENT '受理人id',
  `accept_time` datetime(0) NULL DEFAULT NULL COMMENT '受理时间',
  `check_user` bigint(20) NULL DEFAULT NULL COMMENT '验收人',
  `check_time` datetime(0) NULL DEFAULT NULL COMMENT '验收时间',
  `step_on_user_id` bigint(32) NULL DEFAULT NULL COMMENT '踏勘人ID',
  `step_on_user_name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '踏勘人名称',
  `step_on_time` datetime(0) NULL DEFAULT NULL COMMENT '踏勘时间',
  `design_user_id` bigint(32) NULL DEFAULT NULL COMMENT '安装方案设计人ID',
  `design_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装方案设计人名称',
  `design_time` datetime(0) NULL DEFAULT NULL COMMENT '安装方案设计时间',
  `review_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审核人ID',
  `review_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人名称',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `review_objection` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `money` decimal(18, 4) NULL DEFAULT NULL COMMENT '安装收费金额',
  `install_charge_user` bigint(32) NULL DEFAULT NULL COMMENT '安装收费人',
  `charge_time` datetime(0) NULL DEFAULT NULL COMMENT '安装收费时间',
  `charge_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装收费状态\r\n            CHARGING: 缴费中\r\n            CHARGED: 缴费完成\r\n            CHARGE_FAILED: 缴费失败\r\n            ',
  `install_user_id` bigint(32) NULL DEFAULT NULL COMMENT '安装施工人ID',
  `install_user_name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装施工人名称',
  `install_time` datetime(0) NULL DEFAULT NULL COMMENT '安装施工时间',
  `return_visit_user` bigint(32) NULL DEFAULT NULL COMMENT '客服回访人ID',
  `return_visit_time` datetime(0) NULL DEFAULT NULL COMMENT '客服回访时间',
  `end_job_user_id` bigint(32) NULL DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime(0) NULL DEFAULT NULL COMMENT '结单时间',
  `stop_user_id` bigint(32) NULL DEFAULT NULL COMMENT '终止人ID',
  `stop_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终止人名称',
  `stop_time` datetime(0) NULL DEFAULT NULL COMMENT '终止时间',
  `stop_reason` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终止原因',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '报装状态\n0 待受理      \n1 待踏勘\n2 待设计        \n3 待审核\n4 待收费\n5 待施工\n6 待验收\n7 待结单\n8 已结单\n9 终止',
  `order_status` smallint(6) NULL DEFAULT NULL COMMENT '工单状态\r\n            0: 待接单\r\n            1: 已接单\r\n            2: 已拒单\r\n            3: 已结单\r\n            4: 已取消',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `accept_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '受理人名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_install_number`(`install_number`) USING BTREE COMMENT '报装编号唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报装记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_operation_acceptance
-- ----------------------------
DROP TABLE IF EXISTS `gc_operation_acceptance`;
CREATE TABLE `gc_operation_acceptance`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `company_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `alarm_customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警人编号',
  `alarm_customer_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警人名称',
  `alarm_customer_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警人电话',
  `alarm_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警内容',
  `gas_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `gas_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '气表厂家ID',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `oper_accept_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运维受理编号',
  `plan_handle_user_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划处警人ID',
  `plan_handle_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划处警人名称',
  `plan_handle_time` datetime(0) NULL DEFAULT NULL COMMENT '计划处警时间',
  `handle_user_id` bigint(32) NULL DEFAULT NULL COMMENT '处警人ID',
  `handle_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处警人名称',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '处警时间',
  `terminate_user_id` bigint(32) NULL DEFAULT NULL COMMENT '终止人ID',
  `terminate_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终止人名称',
  `terminate_time` datetime(0) NULL DEFAULT NULL COMMENT '终止时间',
  `stop_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终止原因',
  `accept_status` smallint(6) NULL DEFAULT NULL COMMENT '受理状态:\r\n             0 待处理\r\n             1 已处理\r\n             2 未处理',
  `accept_process_state` smallint(6) NULL DEFAULT NULL,
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '运行维护受理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_operation_process
-- ----------------------------
DROP TABLE IF EXISTS `gc_operation_process`;
CREATE TABLE `gc_operation_process`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `accept_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运行维护受理编号',
  `process_state` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程状态',
  `oper_process_state` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作状态',
  `remark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作备注',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '运行维护流程操作记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_operation_record
-- ----------------------------
DROP TABLE IF EXISTS `gc_operation_record`;
CREATE TABLE `gc_operation_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `company_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `gas_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '气表厂家ID',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `accept_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运维受理编号',
  `receive_user_id` bigint(32) NULL DEFAULT NULL COMMENT '接单人ID',
  `receive_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接单人名称',
  `receive_user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接单人电话',
  `receive_user_time` datetime(0) NULL DEFAULT NULL COMMENT '接单人时间',
  `book_operation_time` datetime(0) NULL DEFAULT NULL COMMENT '预约时间',
  `review_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审批人ID',
  `review_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人名称',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `review_objection` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批意见',
  `distribution_user_id` bigint(32) NULL DEFAULT NULL COMMENT '派单人ID',
  `distribution_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '派单人名称',
  `distribution_time` datetime(0) NULL DEFAULT NULL COMMENT '派单时间',
  `end_job_user_id` bigint(32) NULL DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime(0) NULL DEFAULT NULL COMMENT '结单时间',
  `reject_reason` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '驳回原因',
  `terminate_user_id` bigint(32) NULL DEFAULT NULL COMMENT '终止人ID',
  `terminate_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终止人名称',
  `terminate_time` datetime(0) NULL DEFAULT NULL COMMENT '终止时间',
  `stop_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终止原因',
  `order_type` smallint(6) NULL DEFAULT NULL COMMENT '工单类型\r\n            0 普通运行维护\r\n            1 点火通气',
  `urgency_level` smallint(6) NULL DEFAULT NULL COMMENT '紧急程度\r\n            0 正常\r\n            1 紧急\r\n            2 非常紧急',
  `order_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单内容',
  `operation_status` smallint(6) NULL DEFAULT NULL COMMENT '运维状态:\r\n             0 待提审\r\n             1 待审核\r\n             2 待接单\r\n             3 待运维\r\n             4 运维中\r\n             5 终止\r\n             6 作废\r\n             7 结单',
  `operation_process_state` smallint(6) NULL DEFAULT NULL COMMENT '运维流程状态\r\n            0 提审\r\n            1 审核\r\n            2 派单\r\n            3 驳回\r\n            4 撤回\r\n            5 接单确认\r\n            6 拒单\r\n            7 退单\r\n            8 转单\r\n            9 预约\r\n            10 上门\r\n            11 结单\r\n            12 终止',
  `transfer_id` bigint(32) NULL DEFAULT NULL COMMENT '转单ID',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '运行维护工单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_order_job_file
-- ----------------------------
DROP TABLE IF EXISTS `gc_order_job_file`;
CREATE TABLE `gc_order_job_file`  (
  `id` bigint(32) NOT NULL,
  `job_id` bigint(32) NULL DEFAULT NULL,
  `material_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID：身份证件\r\n            CONTRACT：合同\r\n            OTHER:其他\r\n            ',
  `material_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `material_image_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `material_file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `material_file_extension` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `data_status` smallint(6) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单现场资料' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_order_record
-- ----------------------------
DROP TABLE IF EXISTS `gc_order_record`;
CREATE TABLE `gc_order_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_type` smallint(6) NULL DEFAULT NULL COMMENT '业务类型\r\n            1  报装工单\r\n            2  安检工单\r\n            3  运维工单\r\n            4  客服工单',
  `business_order_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务订单编号',
  `work_order_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单类型\r\n            survey: 踏勘\r\n            install: 施工  check验收',
  `work_order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单编号',
  `urgency` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '紧急程度\r\n            normal:正常\r\n            urgent:紧急\r\n            very_urgent:非常紧急',
  `work_order_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单内容',
  `receive_user_id` bigint(32) NULL DEFAULT NULL COMMENT '接单人ID',
  `receive_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接单人名称',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '接单时间',
  `receive_user_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `review_user_id` bigint(32) NULL DEFAULT NULL COMMENT '结单人id',
  `review_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人名称',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `review_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `book_operation_time` datetime(0) NULL DEFAULT NULL COMMENT '预约处理时间',
  `end_job_user_id` bigint(32) NULL DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime(0) NULL DEFAULT NULL COMMENT '结单时间',
  `reject_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '驳回原因',
  `stop_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终止原因：业务终止',
  `invalid_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单取消原因：转单，拒单，退单原因描述',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `order_status` smallint(6) NULL DEFAULT NULL COMMENT '工单状态\r\n            0: 待接单\r\n            1: 已接单\r\n            2: 已拒单\r\n            3: 已结单\r\n            4: 已取消\r\n            ',
  `turn_id` bigint(32) NULL DEFAULT NULL COMMENT '转单ID',
  `longitude` decimal(18, 6) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18, 6) NULL DEFAULT NULL COMMENT '纬度',
  `address_detail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户地址详情描述',
  `customer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `meter_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表具编码',
  `customer_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号code',
  `customer_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户联系电话',
  `is_check` smallint(6) NULL DEFAULT 0 COMMENT '客户是否签名确认(0-未签,1-已签)',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_install_num`(`business_type`, `work_order_type`, `work_order_no`) USING BTREE COMMENT '编号唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户工单记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_order_return_visit
-- ----------------------------
DROP TABLE IF EXISTS `gc_order_return_visit`;
CREATE TABLE `gc_order_return_visit`  (
  `ID` bigint(32) NOT NULL,
  `GC__id` bigint(32) NULL DEFAULT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `org_id` bigint(32) NULL DEFAULT NULL,
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `telphone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gas_meter_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gas_meter_type_id` bigint(32) NULL DEFAULT NULL,
  `gas_meter_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `job_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `satisfaction` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `data_status` smallint(6) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user_id` bigint(32) NULL DEFAULT NULL,
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_user` bigint(32) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单回访记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_security_check_items
-- ----------------------------
DROP TABLE IF EXISTS `gc_security_check_items`;
CREATE TABLE `gc_security_check_items`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `company_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `sc_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sc_result_id` bigint(32) NULL DEFAULT NULL COMMENT '安检结果id',
  `sc_term_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检项ID',
  `sc_term_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检项名称',
  `sc_term_items_id` bigint(32) NULL DEFAULT NULL,
  `sc_term_items_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `hidden_danger` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检隐患',
  `handle_info` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '整改信息',
  `danger_leve` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `handle_status` smallint(6) NULL DEFAULT NULL COMMENT '隐患处理状态',
  `handle_user_id` bigint(32) NULL DEFAULT NULL COMMENT '隐患处理登记人ID',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '隐患处理登记时间',
  `attache_info` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件信息',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '隐患列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_security_check_order_record
-- ----------------------------
DROP TABLE IF EXISTS `gc_security_check_order_record`;
CREATE TABLE `gc_security_check_order_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `sc_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安检计划编号',
  `work_order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工单编号',
  `work_order_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工单类型',
  `urgency` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '紧急程度',
  `receive_user_id` bigint(32) NULL DEFAULT NULL COMMENT '接单人ID',
  `receive_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接单人名称',
  `receive_user_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `reveive_time` datetime(0) NULL DEFAULT NULL COMMENT '接单时间',
  `work_order_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工单内容',
  `terminate_reason` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '终止原因',
  `cancellation_reason` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作废原因（包括转单、拒单、退单）',
  `end_job_user_id` bigint(32) NULL DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime(0) NULL DEFAULT NULL COMMENT '结单时间',
  `transfer_order_id` bigint(32) NULL DEFAULT NULL COMMENT '转单id',
  `order_status` smallint(6) NULL DEFAULT NULL COMMENT '工单状态\r\n            0 待接单\r\n             1 转单\r\n             2 已接单\r\n             3 待安检\r\n             4 安检异常\r\n             5 结单\r\n             6 终止\r\n             7 拒单\r\n             8 退单',
  `order_process_status` smallint(6) NULL DEFAULT NULL COMMENT '工单流程状态\r\n            0 预约安检\r\n            1 上门安检\r\n            2 待整改\r\n            3 已整改\r\n            4 未整改\r\n            5 作废\r\n            6 终止\r\n            7 结单',
  `hidden_danger` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安全隐患',
  `hidden_danger_handle_status` smallint(6) NULL DEFAULT NULL COMMENT '隐患处理状态\r\n            0、待处理\r\n            1、已按要求整改\r\n            2、未按要求整改',
  `hidden_danger_handle_time` datetime(0) NULL DEFAULT NULL COMMENT '隐患处理登记时间',
  `hidden_danger_handle_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '隐患处理登记人ID',
  `hidden_danger_handle_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '隐患处理登记人名称',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '安检工单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_security_check_process
-- ----------------------------
DROP TABLE IF EXISTS `gc_security_check_process`;
CREATE TABLE `gc_security_check_process`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `security_check_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `process_state` smallint(20) NULL DEFAULT NULL,
  `process_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人员ID',
  `process_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人员名称',
  `process_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `process_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_check_number`(`security_check_number`) USING BTREE COMMENT '安检编号唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '安检流程操作记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_security_check_record
-- ----------------------------
DROP TABLE IF EXISTS `gc_security_check_record`;
CREATE TABLE `gc_security_check_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `company_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `gas_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '气表厂家ID',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `sc_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检编号',
  `plan_security_check_user_id` bigint(32) NULL DEFAULT NULL COMMENT '计划安检员ID',
  `plan_security_check_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划安检员名称',
  `plan_security_check_time` datetime(0) NULL DEFAULT NULL COMMENT '计划安检时间',
  `security_check_user_id` bigint(32) NULL DEFAULT NULL COMMENT '安检员ID',
  `security_check_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检员名称',
  `security_check_time` datetime(0) NULL DEFAULT NULL COMMENT '安检时间',
  `security_check_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检内容',
  `security_check_result` smallint(6) NULL DEFAULT NULL COMMENT '安检结果',
  `review_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审批人ID',
  `review_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人名称',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `review_objection` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批意见',
  `distribution_user_id` bigint(32) NULL DEFAULT NULL COMMENT '派工人ID',
  `distribution_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '派工人名称',
  `distribution_time` datetime(0) NULL DEFAULT NULL COMMENT '派人时间',
  `end_job_user_id` bigint(32) NULL DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime(0) NULL DEFAULT NULL COMMENT '结单时间',
  `reject_user_id` bigint(10) NULL DEFAULT NULL COMMENT '驳回人id',
  `reject_user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '驳回人名称',
  `reject_time` datetime(0) NULL DEFAULT NULL COMMENT '驳回时间 ',
  `reject_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '驳回原因',
  `stop_user_id` bigint(32) NULL DEFAULT NULL COMMENT '终止人ID',
  `stop_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终止人名称',
  `stop_time` datetime(0) NULL DEFAULT NULL COMMENT '终止时间',
  `stop_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终止原因',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '安检状态:\r\n            0 待提审\r\n            1 待审核\r\n            2 待派工\r\n            3 待安检\r\n            4 待回访\r\n            5  异常\r\n            6  已结单\r\n            7  终止 \r\n            ',
  `order_state` smallint(6) NULL DEFAULT NULL COMMENT '工单状态\r\n            0: 待接单\r\n            1: 已接单\r\n            2: 已拒单\r\n            3: 已结单\r\n            4: 已取消',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_sc_no`(`sc_no`) USING BTREE COMMENT '安检编号唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '安检计划记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gc_security_check_result
-- ----------------------------
DROP TABLE IF EXISTS `gc_security_check_result`;
CREATE TABLE `gc_security_check_result`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `company_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `customer_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `gas_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '气表厂家ID',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `sc_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检计划编号',
  `security_check_user_id` bigint(32) NULL DEFAULT NULL COMMENT '安检员ID',
  `security_check_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检员名称',
  `security_check_time` datetime(0) NULL DEFAULT NULL COMMENT '安检时间',
  `security_check_result` smallint(6) NULL DEFAULT NULL COMMENT '安检结果',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '安检结果' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_account_open_task_info
-- ----------------------------
DROP TABLE IF EXISTS `gt_account_open_task_info`;
CREATE TABLE `gt_account_open_task_info`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `total` bigint(32) NULL DEFAULT NULL COMMENT '合计户数',
  `success_total` bigint(32) NULL DEFAULT NULL COMMENT '成功户数',
  `fail_total` bigint(32) NULL DEFAULT NULL COMMENT '失败户数',
  `import_details` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '导入明细',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '操作员ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_adjust_calculation_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_adjust_calculation_record`;
CREATE TABLE `gt_adjust_calculation_record`  (
  `ID` bigint(32) NOT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `body_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `gas_meter_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装地址',
  `total_num` bigint(10) NULL DEFAULT NULL COMMENT '总条数',
  `accounting_user_id` bigint(32) NULL DEFAULT NULL COMMENT '核算人ID',
  `accounting_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核算人名称',
  `accounting_time` datetime(0) NULL DEFAULT NULL COMMENT '核算时间',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态0核算中1核算完成2核算失败',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_adjust_price_process
-- ----------------------------
DROP TABLE IF EXISTS `gt_adjust_price_process`;
CREATE TABLE `gt_adjust_price_process`  (
  `id` bigint(32) NOT NULL,
  `adjust_price_record_id` bigint(32) NULL DEFAULT NULL COMMENT '调价补差记录ID',
  `compensation_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '补差价',
  `compensation_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '补差量',
  `compensation_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '补差金额',
  `accounting_user_id` bigint(32) NULL DEFAULT NULL COMMENT '核算人ID',
  `accounting_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核算人名称',
  `review_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审批人ID',
  `review_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人名称',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `review_objection` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批意见',
  `accounting_time` datetime(0) NULL DEFAULT NULL COMMENT '核算时间',
  `charge_status` smallint(6) NULL DEFAULT NULL COMMENT '收费状态',
  `charge_time` datetime(0) NULL DEFAULT NULL COMMENT '收费时间',
  `charge_order_id` bigint(32) NULL DEFAULT NULL COMMENT '收费订单ID',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_adjust_price_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_adjust_price_record`;
CREATE TABLE `gt_adjust_price_record`  (
  `ID` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `recharge_readmeter_id` bigint(32) NULL DEFAULT NULL COMMENT '充值抄表ID',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `customer_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户类型编码',
  `customer_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户类型名称',
  `compensation_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调价补差编号',
  `gasmeter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `gas_meter_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '普表\r\n            卡表\r\n            物联网表\r\n            流量计(普表)\r\n            流量计(卡表）\r\n            流量计(物联网表）',
  `gas_meter_type_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `body_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `customer_charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户缴费编号',
  `compensation_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '补差价',
  `compensation_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '补差量',
  `compensation_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '补差金额',
  `accounting_user_id` bigint(32) NULL DEFAULT NULL COMMENT '核算人ID',
  `accounting_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核算人名称',
  `review_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审批人ID',
  `review_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人名称',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `review_objection` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批意见',
  `accounting_time` datetime(0) NULL DEFAULT NULL COMMENT '核算时间',
  `charge_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费状态\r\n            待收费\r\n            已收费\r\n            收费失败',
  `charge_time` datetime(0) NULL DEFAULT NULL COMMENT '收费时间',
  `charge_record_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费记录编码',
  `data_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补差状态\r\n            已完成\r\n            待完成',
  `source` smallint(6) NULL DEFAULT NULL COMMENT '数据来源0抄表1充值',
  `record_time` datetime(0) NULL DEFAULT NULL COMMENT '抄表时间',
  `recharge_time` datetime(0) NULL DEFAULT NULL COMMENT '充值时间',
  `check_start_time` date NULL COMMENT '开始时间',
  `check_end_time` date NULL COMMENT '结束时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调价补差记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_arrears_notice
-- ----------------------------
DROP TABLE IF EXISTS `gt_arrears_notice`;
CREATE TABLE `gt_arrears_notice`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `customer_address` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户地址',
  `arrears_months` int(11) NULL DEFAULT NULL COMMENT '欠费月数',
  `arrears_money` decimal(18, 0) NULL DEFAULT NULL COMMENT '欠费金额',
  `press_date` date NULL DEFAULT NULL COMMENT '催款日期',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `start_date` date NULL DEFAULT NULL COMMENT '开始日期',
  `end_date` date NULL DEFAULT NULL COMMENT '结束日期',
  `payment_date` date NULL DEFAULT NULL COMMENT '缴费日期',
  `limit_time` int(11) NULL DEFAULT NULL COMMENT '限期天数',
  `customizations` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义内容',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '欠费通知单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_buyer_taxpayer_info
-- ----------------------------
DROP TABLE IF EXISTS `gt_buyer_taxpayer_info`;
CREATE TABLE `gt_buyer_taxpayer_info`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `buyer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方名称',
  `buyer_tin_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方纳税人识别号',
  `buyer_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方地址',
  `buyer_phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方电话',
  `buyer_bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方开户行',
  `buyer_bank_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方账户',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '购买方税务相关信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_card_info
-- ----------------------------
DROP TABLE IF EXISTS `gt_card_info`;
CREATE TABLE `gt_card_info`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `card_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
  `unique_param` varchar(32) not null default '0000' comment '唯一控制参数，数据无效更新时改变为原来ID' ,
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编码',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `card_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡类型\r\n            IC卡\r\n            ID卡',
  `card_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开卡状态\r\n            待收费\r\n            待写卡\r\n            已发卡',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '卡上合计量',
  `card_balance` decimal(18, 4) NULL DEFAULT NULL COMMENT '卡上余额',
  `card_charge_count` int(11) NULL DEFAULT NULL COMMENT '卡上充值次数',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费单号',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态:\r\n            1: 正常\r\n            0: 作废',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_card_no_status`(`card_no`,`unique_param`,`data_status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表卡信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_card_make_tool_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_card_make_tool_record`;
CREATE TABLE `gt_card_make_tool_record`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `write_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '制卡气量',
  `write_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '制卡金额',
  `write_count` int(11) NULL DEFAULT NULL COMMENT '制卡次数',
  `write_fee` decimal(18, 4) NULL DEFAULT NULL COMMENT '制卡费用',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '制卡人名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '制卡时间',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '制卡原因',
  `write_card_id` bigint(32) NULL DEFAULT NULL COMMENT '写卡记录ID',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '制卡人ID',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '制工具卡记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_card_rec_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_card_rec_record`;
CREATE TABLE `gt_card_rec_record`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `card_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编码',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态:\r\n            1: 正常\r\n            0: 作废',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卡收回记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_card_rep_gas_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_card_rep_gas_record`;
CREATE TABLE `gt_card_rep_gas_record`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `card_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编码',
  `rep_gas_method` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补气方式\r\n            补上次充值\r\n            按需补充',
  `rep_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '补充气量',
  `rep_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '补充金额',
  `rep_gas_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补气原因',
  `rep_gas_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补气状态\r\n            待写卡\r\n            待上表\r\n            补气完成',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态:\r\n            1: 正常\r\n            0: 作废',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卡补气记录' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for gt_card_rep_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_card_rep_record`;
CREATE TABLE `gt_card_rep_record`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `card_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编码',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `card_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡类型\r\n            IC卡\r\n            ID卡',
  `is_recovery_old_card` tinyint(1) NULL DEFAULT NULL COMMENT '是否回收老卡',
  `rep_card_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补卡类型\r\n            新用户卡\r\n            购气卡',
  `rep_card_method` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补卡方式\r\n            补上次充值\r\n            无充值',
  `rep_card_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '补卡气量',
  `rep_card_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '补卡金额',
  `rep_card_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补卡原因',
  `rep_card_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补卡状态\r\n            待收费\r\n            待写卡\r\n            已补卡',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态:\r\n            1: 正常\r\n            0: 作废',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '补换卡记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_change_meter_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_change_meter_record`;
CREATE TABLE `gt_change_meter_record`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编号',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `charge_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `change_meter_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '换表记录编号',
  `old_meter_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '旧气表表号',
  `old_meter_number`  varchar(30) NULL COMMENT '旧表的表身号',
  `old_meter_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '旧表类型',
  `new_meter_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新气表表号',
  `new_meter_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新表的表身号',
  `new_meter_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新表类型',
  `new_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '新表厂家ID',
  `new_meter_factory_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新表厂家名称',
  `new_meter_model_id` bigint(32) NULL DEFAULT NULL COMMENT '新表气表型号id',
  `new_meter_model_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新表气表型号名称',
  `new_meter_version_id` bigint(32) NULL DEFAULT NULL COMMENT '新表版号ID',
  `new_meter_version_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新表版号名称',
  `change_mode` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '换表方式：CHANGE_NEW、换新表；CHANGE_ZERO、气表清零',
  `change_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '换表类型：GAS_TO_GAS、气量表换气表量；GAS_TO_MONEY、气量表换金额表；MONEY_TO_GAS、金额表换金额表；MONEY_TO_MONEY、金额表换气量表 ',
  `change_scene` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '换表场景：ChangeSceneEnum',
  `change_reason` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '换表原因',
  `charge_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '换表收费金额',
  `old_meter_end_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '旧表止数',
  `new_meter_start_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '新表底数',
  `supplement_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '补充气量',
  `supplement_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '补充金额',
  `cycle_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '周期累计使用量',
  `read_meter_id`  bigint(32) NULL COMMENT '生成的抄表数据Id',
  `arrears_detail_id`  bigint(32) NULL DEFAULT NULL COMMENT '结算产生的欠费记录id' ,
  `arrears_detail_id_list`  varchar(300) NULL COMMENT '结算产生的多条欠费记录id',
  `change_date` date NULL DEFAULT NULL COMMENT '换表日期',
  `card_operate` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '换卡表时的读写卡操作结果',
  `status` int(2) NULL DEFAULT NULL COMMENT '业务状态：0取消；1完成；2待缴费；3处理中',
  `delete_status` int(10) NULL DEFAULT NULL COMMENT '删除状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_change_meter_no`(`change_meter_no`) USING BTREE COMMENT '换表记录编号唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1342714988048941057 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表换表记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_charge_insurance_detail
-- ----------------------------
DROP TABLE IF EXISTS `gt_charge_insurance_detail`;
CREATE TABLE `gt_charge_insurance_detail`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `insurance_contract_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '保险合同编号',
  `insurance_premium` decimal(18, 4) NULL DEFAULT NULL COMMENT '保险费',
  `insurance_start_date` date NULL DEFAULT NULL COMMENT '保险开始时间',
  `insurance_end_date` date NULL DEFAULT NULL COMMENT '保险到期时间',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态: 1: 正常 0: 作废 ',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '保险明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_charge_item_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_charge_item_record`;
CREATE TABLE `gt_charge_item_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `toll_item_scene` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项场景编码',
  `toll_item_id` bigint(32) NULL DEFAULT NULL COMMENT '收费项ID',
  `charge_item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项名称',
  `charge_item_time` datetime(0) NULL DEFAULT NULL COMMENT '收费项产生时间',
  `charge_item_source_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项来源编码',
  `charge_item_source_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项来源名称',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `charge_item_source_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项来源ID',
  `charge_item_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '收费项金额',
  `charge_item_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '收费项气量',
  `charge_frequency` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费频次',
  `money_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金额方式\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            默认固定金额\r\n            ',
  `recharge_give_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值赠送气量',
  `recharge_give_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值赠送金额',
  `is_reduction_item` tinyint(1) NULL DEFAULT NULL COMMENT '是否是减免项',
  `create_user_id` bigint(32) NULL DEFAULT NULL COMMENT '收费员ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注/理由',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态: 1: 正常 0: 作废 ',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_ladder_price` tinyint(4) NULL DEFAULT NULL COMMENT '是否阶梯价 1是 0 不是',
  `ladder_price_detail` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阶梯计费明细',
  `price` decimal(18, 4) NULL DEFAULT NULL COMMENT '单价',
  `total_count` int(11) NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '缴费项记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_charge_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_charge_record`;
CREATE TABLE `gt_charge_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_charge_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL comment '客户缴费编号',
  `customer_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `charge_channel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费渠道 柜台收费  GT 微信小程序 WX_MS 支付宝小程序 ALIPAY_MS 客户APP  APP 第三方收费 THIRD_PARTY',
  `gas_meter_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `charge_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `charge_method_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费方式编码',
  `charge_method_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费方式名称\r\n            现金\r\n            POS\r\n            转账\r\n            微信\r\n            支付宝',
  `payable_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '应缴金额',
  `charge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '收费金额',
  `precharge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '预存金额',
  `recharge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值金额',
  `recharge_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值气量',
  `recharge_give_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值赠送气量',
  `recharge_give_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值赠送金额',
  `reduction_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '减免金额',
  `precharge_deduction_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '预存抵扣',
  `receivable_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '应收金额',
  `actual_income_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '实收金额',
  `give_change` decimal(18, 4) NULL DEFAULT NULL COMMENT '找零',
  `insurance_contract_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保险合同编号',
  `insurance_premium` decimal(18, 4) NULL DEFAULT NULL COMMENT '保险费',
  `insurance_start_date` date NULL DEFAULT NULL COMMENT '保险开始日期',
  `insurance_end_date` date NULL DEFAULT NULL COMMENT '保险到期日期',
  `give_deduction_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '账户赠送抵扣',
  `give_book_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账前余额',
  `give_book_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账后余额',
  `charge_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '账户收费前余额',
  `charge_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '账户收费后余额',
  `charge_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            CHARGE_FAILURE: 收费失败',
  `refund_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退费状态',
  `summary_bill_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扎帐标识：UNBILL 未扎帐,BILLED 已扎帐',
  `invoice_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票状态',
  `receipt_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收据状态',
  `invoice_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开票类型',
  `invoice_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票编号',
  `create_user_id` bigint(32) NULL DEFAULT NULL COMMENT '收费员ID',
  `create_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费员名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注/理由',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态:\r\n            1: 正常\r\n            0: 作废',
  `pay_time` datetime comment '发起支付时间',
  `pay_real_time` datetime comment '确认支付时间',
  `pay_err_reason` varchar(600) comment '支付失败原因' ,
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_charge_no`(`charge_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '缴费记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_charge_record_pay
-- ----------------------------
DROP TABLE IF EXISTS `gt_charge_record_pay`;
CREATE TABLE `gt_charge_record_pay`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `fee_record_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '费用记录类型\r\n            recharge充值\r\n            charge缴费\r\n            precharge预存',
  `fee_record_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '费用记录编号：\r\n            充值记录编号\r\n            缴费记录编号\r\n            预存记录编号',
  `payment_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付类型\r\n            支付宝\r\n            微信支付\r\n            现金\r\n            POS',
  `service_provider` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网络支付服务商\r\n            微信\r\n            支付宝',
  `payment_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网络支付方式\r\n            扫码支付\r\n            被扫扣款',
  `fee_record_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '费用金额',
  `fee_record_description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '费用记录描述',
  `expiry` smallint(6) NULL DEFAULT NULL COMMENT '订单有效期',
  `qr_code` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二维码',
  `service_order_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务商订单号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `payment_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付状态：\r\n            未发起支付\r\n            已发起支付\r\n            支付成功\r\n            支付失败',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付完成时间',
  `payment_result` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付结果\r\n            返回内容记录',
  `proof_status` smallint(6) NULL DEFAULT NULL COMMENT '对账状态\r\n            未支付成功\r\n            待对账\r\n            已对账',
  `proof_time` datetime(0) NULL DEFAULT NULL COMMENT '对账时间',
  `proof_result` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对账结果\r\n            对账通过\r\n            差异',
  `manual_proof_usre_id` bigint(32) NULL DEFAULT NULL COMMENT '人工校对人ID',
  `manual_proof_usre_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人工校对人名称',
  `update_user` bigint(32) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '第三方支付记录明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_charge_refund
-- ----------------------------
DROP TABLE IF EXISTS `gt_charge_refund`;
CREATE TABLE `gt_charge_refund`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `refund_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退费编号',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费记录编号',
  `charge_method_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费方式编码',
  `charge_method_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费方式名称\r\n            现金\r\n            POS\r\n            转账\r\n            微信\r\n            支付宝',
  `invoice_type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票类型',
  `invoice_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票编号',
  `charge_time` datetime(0) NULL DEFAULT NULL COMMENT '收费时间',
  `back_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '应退金额',
  `audit_remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核备注',
  `back_method_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退费方式编码',
  `back_method_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费方式名称\r\n            现金\r\n            POS\r\n            转账\r\n            微信\r\n            支付宝',
  `back_reason` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退费原因',
  `back_finish_time` datetime(0) NULL DEFAULT NULL COMMENT '退费完成时间',
  `back_result` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退费结果',
  `refund_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退费状态\r\n            APPLY:发起申请\r\n            AUDITING:审核中\r\n            UNREFUND:不予退费\r\n            REFUNDABLE: 可退费\r\n            REFUND_ERR:退费失败\r\n            THIRDPAY_REFUND:三方支付退费中\r\n            REFUNDED: 退费完成\r\n            ',
  `whether_no_card` tinyint(1) NULL DEFAULT 0 COMMENT '是否无卡退费0:有卡 1:无卡',
  `refund_failed_reason` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退费失败原因',
  `charge_user_id` bigint(32) NULL DEFAULT NULL COMMENT '收费员ID',
  `charge_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员名称',
  `audit_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人名称',
  `apply_user_id` bigint(32) NULL DEFAULT NULL COMMENT '申请人ID',
  `apply_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请人名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_charge_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL comment '客户缴费编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `apply_time` datetime comment '申请时间',
  `audit_time` datetime comment '审核时间',
  `refund_time` datetime comment '发起退款时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_refund_no`(`refund_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '退费记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_customer_account
-- ----------------------------
DROP TABLE IF EXISTS `gt_customer_account`;
CREATE TABLE `gt_customer_account`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `account_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户号',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `account_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '账户金额',
  `give_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送金额',
  `settlement_lock` smallint(2) NULL DEFAULT NULL COMMENT '结算锁\r\n            1 结算中已锁\r\n            0 未锁定',
  `status` smallint(2) NULL DEFAULT NULL COMMENT '账户状态\r\n            0 未激活\r\n            1 激活\r\n            2 冻结',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '删除状态\r\n            1 删除\r\n            0 正常',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '操作员ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_account_code`(`account_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_customer_account_serial
-- ----------------------------
DROP TABLE IF EXISTS `gt_customer_account_serial`;
CREATE TABLE `gt_customer_account_serial`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `account_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户号',
  `customer_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `gas_meter_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `serial_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流水号',
  `bill_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记费记录号( 根据各个场景记录不同的编号)\r\n            例如：收费扣款或者收费预存存放 对应单号。',
  `bill_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收入支出类型：\r\n            预存抵扣\r\n            预存\r\n            抵扣退费\r\n            预存退费\r\n            ',
  `book_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '记账金额',
  `book_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '记账前余额',
  `book_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '记账后余额',
  `give_book_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账金额',
  `give_book_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账前余额',
  `give_book_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账后余额',
  `remark` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流水说明',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '记账人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '记账时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_serial_no`(`serial_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户流水' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_customer_enjoy_activity_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_customer_enjoy_activity_record`;
CREATE TABLE `gt_customer_enjoy_activity_record`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `activity_id` bigint(32) NULL DEFAULT NULL COMMENT '活动ID',
  `activity_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `toll_item_scene` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项场景编码',
  `toll_item_id` bigint(32) NULL DEFAULT NULL COMMENT '收费项ID',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `give_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送气量（充值场景才能设置）',
  `activity_money_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动金额方式（缴费场景无百分比选项）\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            percent: 百分比',
  `activity_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '活动金额',
  `activity_percent` decimal(18, 4) NULL DEFAULT NULL COMMENT '活动比例',
  `activity_scene` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动场景\r\n            RECHARGE: 充值赠送\r\n            PRECHARGE: 预存赠送\r\n            CHARGE: 缴费减免',
  `use_gas_types` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型(只针对充值场景)\r\n            存放jsonarray数据 包含用气类型id和用气类型名称。',
  `total_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '合计金额',
  `total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '合计气量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态: 1: 正常 0: 作废 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户享受活动明细记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_customer_scene_charge_order
-- ----------------------------
DROP TABLE IF EXISTS `gt_customer_scene_charge_order`;
CREATE TABLE `gt_customer_scene_charge_order`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `scene_charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场景费用单编号',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `toll_item_scene` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费场景',
  `toll_item_id` bigint(32) NULL DEFAULT NULL COMMENT '收费项ID',
  `toll_item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项名称',
  `toll_item_frequency` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费频次',
  `business_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务编号',
  `charge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '缴费金额',
  `discount_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠金额',
  `total_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '合计金额',
  `charge_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费状态：\r\n            UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            CHARGE_FAILURE: 收费失败',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态:\r\n            0: 正常\r\n            1: 作废',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_charge_no`(`scene_charge_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户场景费用单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_gas_type_change_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_gas_type_change_record`;
CREATE TABLE `gt_gas_type_change_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_ball_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅id',
  `business_ball_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编码',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编码',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `change_time` datetime(0) NULL DEFAULT NULL COMMENT '变更时间',
  `old_gas_type_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更前用气类型名称',
  `old_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '变更前用气类型id',
  `new_gas_type_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更后',
  `new_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '变更后用气类型id',
  `start_time_new` date NULL DEFAULT NULL COMMENT '变更后价格方案生效时间',
  `end_time_new` date NULL DEFAULT NULL COMMENT '变更后价格方案失效时间',
  `start_time_old` date NULL DEFAULT NULL COMMENT '变更前价格方案生效时间',
  `end_time_old` date NULL DEFAULT NULL COMMENT '变更后价格方案失效时间',
  `old_price_num` int(11) NULL DEFAULT NULL COMMENT '变更前价格方案号',
  `new_price_num` int(11) NULL DEFAULT NULL COMMENT '变更后价格方案号',
  `old_price_id` bigint(32) NULL DEFAULT NULL COMMENT '旧价格方案id',
  `new_price_id` bigint(32) NULL DEFAULT NULL COMMENT '新价格方案id',
  `cycle_sum_control` smallint(6) NULL DEFAULT NULL COMMENT '周期量控制（是否清零1-清零，0-不清零）',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户用气类型变更记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_gasmeter_arrears_detail
-- ----------------------------
DROP TABLE IF EXISTS `gt_gasmeter_arrears_detail`;
CREATE TABLE `gt_gasmeter_arrears_detail`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `settlement_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结算明细编号',
  `readmeter_data_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表数据ID',
  `readmeter_month` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表月',
  `gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '用气量',
  `gas_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '用气金额',
  `arrears_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '欠费金额',
  `billing_date` date NULL DEFAULT NULL COMMENT '计费日期',
  `late_fee_start_date` date NULL DEFAULT NULL COMMENT '滞纳金开始日期',
  `latepay_days` bigint(20) NULL DEFAULT NULL COMMENT '滞纳天数',
  `latepay_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '滞纳金',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `arrears_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '欠费状态\r\n            UNCHARGE:待收费\r\n            CHARGED: 完结',
  `data_status` smallint(6) NULL DEFAULT 1 COMMENT '数据状态(1-有效、0-无效)',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_ladder_price` tinyint(4) NULL DEFAULT NULL COMMENT '是否阶梯价 1是 0 不是',
  `ladder_price_detail` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阶梯计费明细',
  `price` decimal(18, 4) NULL DEFAULT NULL COMMENT '固定计费单价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表欠费明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_gasmeter_settlement_detail
-- ----------------------------
DROP TABLE IF EXISTS `gt_gasmeter_settlement_detail`;
CREATE TABLE `gt_gasmeter_settlement_detail`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `settlement_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结算明细编号',
  `readmeter_data_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表数据ID',
  `readmeter_month` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄表月',
  `population_float_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '人口浮动金额',
  `gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '用气量',
  `gas_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '用气金额',
  `discount_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠金额',
  `give_deduction_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送抵扣',
  `precharge_deduction_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '预存抵扣',
  `give_book_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账前余额',
  `give_back_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账后余额',
  `settlement_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '结算前余额',
  `settlement_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '结算后余额',
  `settlement_meter_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '结算前表账户余额',
  `settlement_meter_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '计算后表账户余额',
  `give_meter_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '户表账户计费前金额',
  `give_meter_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '户表账户计费后金额',
  `arrears_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '欠费金额',
  `billing_date` date NULL DEFAULT NULL COMMENT '计费日期',
  `latepay_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '滞纳金',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `price` decimal(18, 4) NULL DEFAULT NULL COMMENT '单价',
  `ladder_charge_mode_id` bigint(32) NULL DEFAULT NULL COMMENT '阶梯计费方式ID',
  `ladder_charge_mode_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阶梯计费方式名称',
  `gas_1` decimal(11, 4) NULL DEFAULT NULL,
  `price_1` decimal(18, 4) NULL DEFAULT NULL,
  `money_1` decimal(18, 4) NULL DEFAULT NULL,
  `gas_2` decimal(11, 4) NULL DEFAULT NULL,
  `price_2` decimal(18, 4) NULL DEFAULT NULL,
  `money_2` decimal(18, 4) NULL DEFAULT NULL,
  `gas_3` decimal(11, 4) NULL DEFAULT NULL,
  `price_3` decimal(18, 4) NULL DEFAULT NULL,
  `money_3` decimal(18, 4) NULL DEFAULT NULL,
  `gas_4` decimal(11, 4) NULL DEFAULT NULL,
  `price_4` decimal(18, 4) NULL DEFAULT NULL,
  `money_4` decimal(18, 4) NULL DEFAULT NULL,
  `gas_5` decimal(11, 4) NULL DEFAULT NULL,
  `price_5` decimal(18, 4) NULL DEFAULT NULL,
  `money_5` decimal(18, 4) NULL DEFAULT NULL,
  `gas_6` decimal(11, 4) NULL DEFAULT NULL,
  `price_6` decimal(18, 4) NULL DEFAULT NULL,
  `money_6` decimal(18, 4) NULL DEFAULT NULL,
  `data_status` smallint(6) NULL DEFAULT 1 COMMENT '数据状态(0-无效，1-有效)',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表结算明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_gasmeter_transfer_account
-- ----------------------------
DROP TABLE IF EXISTS `gt_gasmeter_transfer_account`;
CREATE TABLE `gt_gasmeter_transfer_account`  (
  `id` bigint(32) NOT NULL,
  `org_id` bigint(32) not NULL COMMENT '组织ID',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表具编号',
  `old_customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原客户编号',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新客户code',
  `customer_charge_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '缴费编号',
  `population` int(3) DEFAULT null COMMENT '使用人口数' ,
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int(2) NULL DEFAULT NULL COMMENT '过户业务状态：0’取消；1‘完成；2’待缴费；3‘处理中',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '柜台综合：过户业务表，业务状态未完成之前不会更改主表数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_invoice
-- ----------------------------
DROP TABLE IF EXISTS `gt_invoice`;
CREATE TABLE `gt_invoice`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `invoice_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票编号',
  `pay_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费编号',
  `invoice_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票类型\r\n            007普票\r\n            004专票\r\n            026电票',
  `invoice_kind` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票种类:0红票,1蓝票',
  `billing_date` datetime(0) NULL DEFAULT NULL COMMENT '开票日期',
  `invoice_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票代码',
  `invoice_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票号码',
  `invoice_code_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票代码+发票号码',
  `buyer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方名称',
  `buyer_tin_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方纳税人识别号',
  `buyer_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方地址',
  `buyer_phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方电话',
  `buyer_bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方开户行名称',
  `buyer_bank_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方开户行账号',
  `seller_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售方名称',
  `seller_tin_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售方纳税人识别号',
  `seller_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售方地址',
  `seller_phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售方电话',
  `seller_bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售方开户行名称',
  `seller_bank_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售方开户行账号',
  `total_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '合计金额',
  `total_tax` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '合计税额',
  `price_tax_total_upper` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '价税合计（大写）',
  `price_tax_total_lower` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '价税合计（小写）',
  `payee_id` bigint(32) NULL DEFAULT NULL COMMENT '收款人',
  `payee_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收款人名称',
  `reviewer_id` bigint(32) NULL DEFAULT NULL COMMENT '复核人',
  `reviewer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复核人名称',
  `drawer_id` bigint(32) NULL DEFAULT NULL COMMENT '开票人',
  `drawer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开票人名称',
  `telphone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `push_method` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子票推送方式\r\n            0短信\r\n            1邮件\r\n            2微信',
  `void_user_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作废人ID',
  `void_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作废人名称',
  `red_user_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冲红人ID',
  `red_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冲红人名称',
  `invoice_file_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票文件地址',
  `pdf_download_url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票pdf下载地址',
  `invoice_serial_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开票流水号',
  `red_request_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专票冲红申请号',
  `blue_order_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蓝票订单号',
  `blue_invoice_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蓝票编号',
  `blue_serial_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蓝票开票流水号',
  `invoice_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开票状态:0未开票 1开票中 2已开票 3开票失败',
  `invoice_result` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开票结果',
  `invalid_status` smallint(6) NULL DEFAULT 0 COMMENT '发票是否作废（0：未作废、1：已作废）',
  `print_times` smallint(6) NULL DEFAULT NULL COMMENT '打印次数',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `summary_bill_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扎帐标识：UNBILL 未扎帐,BILLED 已扎帐',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_invoice_number`(`invoice_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_invoice_callback_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_invoice_callback_record`;
CREATE TABLE `gt_invoice_callback_record`  (
  `id` bigint(32) NOT NULL,
  `tenant` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户code',
  `plat_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回调的平台编号',
  `plat_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回调的平台名称',
  `callback_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '回调内容',
  `callback_date` datetime(0) NULL DEFAULT NULL COMMENT '回调时间',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电子发票回调记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_invoice_details
-- ----------------------------
DROP TABLE IF EXISTS `gt_invoice_details`;
CREATE TABLE `gt_invoice_details`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `invoice_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票ID',
  `type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票行性质0正常行、1折扣行、2被折扣行',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '自行编码(一般不建议使用自行编码)',
  `preferentialPolicyFlg` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠政策标识。0：不使用；1：使用',
  `addedValueTaxFlg` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '增值税特殊管理（当优惠政策标识为1时必填）。',
  `zeroTaxRateFlg` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '零税率标识。1:免税,2:不征税,3:普通零税率。税率为零的情况下，如果不传，则默认为1:免税。',
  `goods_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品代码',
  `goods_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `specification_model` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格型号',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
  `number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '单价',
  `money` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '金额',
  `tax_rate` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '税率',
  `tax_money` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '税额',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_invoice_push_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_invoice_push_record`;
CREATE TABLE `gt_invoice_push_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `push_plat` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推送的平台',
  `push_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推送的url',
  `push_cmd_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推送的接口执行的操作',
  `serial_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推送的操作流水号',
  `invoice_id` bigint(32) NULL DEFAULT NULL COMMENT '推送的发票id',
  `invoice_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推送的发票编号',
  `push_data` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推送的数据',
  `push_date` date NULL DEFAULT NULL COMMENT '推送日期',
  `push_result_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推送结果code',
  `push_result` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推送结果',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电子发票推送记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_open_account_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_open_account_record`;
CREATE TABLE `gt_open_account_record`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `relate_id` bigint(32) NULL DEFAULT NULL COMMENT '关联ID',
  `customer_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `install_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报装编号',
  `gas_meter_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_type_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表类型ID',
  `gas_meter_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表类型名称',
  `gas_meter_factory_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表厂家ID',
  `gas_meter_factory_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表厂家名称',
  `gas_meter_version_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表版号ID',
  `gas_meter_version_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表版号名称',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '操作员ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_precharge_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_precharge_record`;
CREATE TABLE `gt_precharge_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `precharge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预存编号',
  `give_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送金额',
  `precharge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '预存金额',
  `actual_income_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '实收金额',
  `receivable_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '应收费金额',
  `charge_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '账户收费前余额',
  `charge_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '账户收费后余额',
  `give_book_pre_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账前余额',
  `give_book_after_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送记账后余额',
  `give_change` decimal(18, 4) NULL DEFAULT NULL COMMENT '找零',
  `charge_method_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费方式编码',
  `charge_method_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费方式名称\r\n            现金\r\n            POS\r\n            转账\r\n            微信\r\n            支付宝',
  `charge_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费状态：\r\n            UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            CHARGE_FAILURE: 收费失败',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态:\r\n            0: 正常\r\n            1: 作废',
  `create_user_id` bigint(32) NULL DEFAULT NULL COMMENT '收费员ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员名称',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预存记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_receipt
-- ----------------------------
DROP TABLE IF EXISTS `gt_receipt`;
CREATE TABLE `gt_receipt`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `receipt_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '票据编号',
  `receipt_type` smallint(6) NULL DEFAULT NULL COMMENT '票据类型\r\n            0充值\r\n            1缴费\r\n            2预存',
  `pay_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费编号',
  `pay_type` smallint(6) NULL DEFAULT NULL COMMENT '收费类型\r\n            0现金\r\n            1支付宝\r\n            2微信\r\n            3POS',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '收费时间',
  `charge_amount_total_lowercase` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '收费项目合计（小写）',
  `charge_amount_total_uppercase` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目合计（大写）',
  `recharge_give_gas` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '充值活动赠送气量',
  `discount_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '充值活动赠送金额',
  `should_pay` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '应收金额',
  `make_collections` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '入账金额（实收金额-找零金额+抵扣金额）',
  `actual_collection` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '实收金额（收款金额）',
  `give_change` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '找零金额',
  `pre_deposit` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '零存',
  `customer_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `customer_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户地址',
  `customer_phone` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户电话',
  `operator_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员编号',
  `operator_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员姓名',
  `buyer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方名称',
  `buyer_tin_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方纳税人识别号',
  `buyer_address` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方地址',
  `buyer_phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方联系电话',
  `buyer_bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方开户行名称',
  `buyer_bank_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买方开户行及账号',
  `deduct_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '抵扣金额',
  `recharge_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '充值金额',
  `recharge_gas_volume` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '充值气量',
  `predeposit_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '预存金额',
  `premium` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '保费',
  `receipt_state` smallint(6) NULL DEFAULT 0 COMMENT '开票状态0未开票据1已开票据未开发票2已开票据发票',
  `billing_date` datetime(0) NULL DEFAULT NULL COMMENT '开票日期',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_receipt_no`(`receipt_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_receipt_detail
-- ----------------------------
DROP TABLE IF EXISTS `gt_receipt_detail`;
CREATE TABLE `gt_receipt_detail`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `receipt_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '票据ID',
  `goods_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goods_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品代码\r\n            0充值\r\n            1燃气收费\r\n            2商品销售\r\n            3场景收费\r\n            4附加费\r\n            5保险费\r\n            ',
  `specification_model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格型号',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
  `number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '单价',
  `money` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '金额',
  `tax_rate` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '税率',
  `tax_money` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '税额',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_receipt_invoice_association
-- ----------------------------
DROP TABLE IF EXISTS `gt_receipt_invoice_association`;
CREATE TABLE `gt_receipt_invoice_association`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `receipt_id` bigint(32) NULL DEFAULT NULL COMMENT '票据ID',
  `invoice_id` bigint(32) NULL DEFAULT NULL COMMENT '发票ID',
  `state` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_receipt_print_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_receipt_print_record`;
CREATE TABLE `gt_receipt_print_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `print_date` date NULL DEFAULT NULL COMMENT '打印日期',
  `operator_id` bigint(32) NULL DEFAULT NULL COMMENT '操作人id',
  `operator_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人姓名',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '票据打印记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_recharge_record`;
CREATE TABLE `gt_recharge_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `gasmeter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `gas_meter_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表类型编码',
  `gas_meter_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表类型名称',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `recharge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值金额',
  `recharge_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值气量',
  `recharge_give_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值赠送气量',
  `recharge_give_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值赠送金额',
  `total_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '合计金额',
  `total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '合计气量',
  `money_flow_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金额流转状态\r\n            waite_isuue: 待下发\r\n            waite_write_card: 待写卡\r\n            success:  下发成功 或 上卡成功\r\n            failure：写卡失败 或 下发失败',
  `money_flow_serial_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金额流转序列号',
  `charge_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费状态：\r\n            UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            REFUND: 已退费\r\n            CHARGE_FAILURE: 收费失败',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态: 1: 正常 0: 作废 2: 锁定',
  `create_user_id` bigint(32) NULL DEFAULT NULL COMMENT '收费员ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员名称',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '充值记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_remove_meter_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_remove_meter_record`;
CREATE TABLE `gt_remove_meter_record`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编号',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `remove_meter_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拆表记录编号',
  `charge_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `meter_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '厂家ID',
  `meter_factory_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂家名称',
  `meter_model_id` bigint(32) NULL DEFAULT NULL COMMENT '气表型号id',
  `meter_model_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表型号名称',
  `meter_version_id` bigint(32) NULL DEFAULT NULL COMMENT '版号ID',
  `meter_version_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号名称',
  `meter_end_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '气表止数',
  `remove_reason` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拆表原因',
  `remove_date` date NULL DEFAULT NULL COMMENT '拆表日期',
  `meter_balance`  decimal(18,4) NULL COMMENT '表具余额',
  `read_meter_id`  bigint(32) NULL COMMENT '生成的抄表数据Id',
  `arrears_detail_id`  bigint(32) NULL DEFAULT NULL COMMENT '结算产生的欠费记录id',
  `arrears_detail_id_list`  varchar(300) NULL COMMENT '结算产生的多条欠费记录id',
  `status` int(2) NULL DEFAULT NULL COMMENT '业务状态：0取消；1完成；2待缴费；3处理中',
  `delete_status` int(10) NULL DEFAULT NULL COMMENT '删除状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_remove_meter_no`(`remove_meter_no`) USING BTREE COMMENT '拆表记录编号唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1340886877447651329 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表拆表记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_seller_taxpayer_info
-- ----------------------------
DROP TABLE IF EXISTS `gt_seller_taxpayer_info`;
CREATE TABLE `gt_seller_taxpayer_info`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `seller_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售方名称',
  `seller_tin_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售方纳税人识别号',
  `seller_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售方地址、电话',
  `seller_phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '售卖方电话',
  `seller_bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '售卖方开户行名称',
  `seller_bank_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售方开户行及账户',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_summary_bill
-- ----------------------------
DROP TABLE IF EXISTS `gt_summary_bill`;
CREATE TABLE `gt_summary_bill`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `total_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '合计金额',
  `cash_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '现金金额',
  `bank_transfer_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '银行转账金额',
  `alipay_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '支付宝金额',
  `wechat_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '微信金额',
  `refund_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '退费金额',
  `pre_deposit_deduction` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '预存抵扣',
  `receipt_invoice_total_num` int(11) NULL DEFAULT NULL COMMENT '合计发票数',
  `receipt_total_num` int(11) NULL DEFAULT NULL COMMENT '票据数',
  `blue_invoice_total_num` int(11) NULL DEFAULT NULL COMMENT '蓝票数',
  `red_invoice_total_num` int(11) NULL DEFAULT NULL COMMENT '红票数',
  `invalid_invoice_total_num` int(11) NULL DEFAULT NULL COMMENT '废票数',
  `receipt_invoice_total_amount` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '合计发票金额',
  `operator_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员编号',
  `operator_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员姓名',
  `summary_start_date` datetime(0) NULL DEFAULT NULL COMMENT '扎帐开始日期',
  `summary_end_date` datetime(0) NULL DEFAULT NULL COMMENT '扎帐结束始日期',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `summary_bill_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扎帐标识：UNBILL 未扎帐,BILLED 已扎帐',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_summary_bill_detail
-- ----------------------------
DROP TABLE IF EXISTS `gt_summary_bill_detail`;
CREATE TABLE `gt_summary_bill_detail`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `source_id` bigint(32) NULL DEFAULT NULL COMMENT '扎帐来源ID',
  `source_type` smallint(6) NULL DEFAULT NULL COMMENT '扎帐来源类型',
  `summary_bill_id` bigint(32) NULL DEFAULT NULL COMMENT '银行扎帐ID转账金额',
  `operator_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员编号',
  `summary_bill_date` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扎帐日期',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_supplement_gas_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_supplement_gas_record`;
CREATE TABLE `gt_supplement_gas_record`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编号',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `rep_gas_method` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补充方式：补上次充值、按需补充',
  `rep_gas_desc` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补气原因',
  `rep_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '补充气量',
  `rep_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '补充金额',
  `rep_gas_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补气状态',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '删除状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1339115759867002881 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '补气记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_toll_item_charge_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_toll_item_charge_record`;
CREATE TABLE `gt_toll_item_charge_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `sys_item_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统项编码',
  `toll_item_id` bigint(32) NOT NULL COMMENT '收费项ID',
  `item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项名称',
  `charge_frequency` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费频次\r\n            ON_DEMAND:按需\r\n            ONE_TIME：一次性\r\n            BY_MONTH：按月\r\n            QUARTERLY：按季\r\n            BY_YEAR：按年',
  `charge_period` smallint(6) NULL DEFAULT NULL COMMENT '收费期限',
  `cycle_value` smallint(6) NULL DEFAULT NULL COMMENT '周期值,固定1',
  `money` decimal(18, 4) NULL DEFAULT NULL COMMENT '金额',
  `money_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金额方式\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            默认固定金额\r\n            ',
  `financial_subject` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '财务科目',
  `vat_general_rate` decimal(18, 4) NULL DEFAULT NULL COMMENT '增值税普税税率',
  `tax_category_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '税收分类编码',
  `favoured_status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否享受优惠',
  `favoured_policy` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠政策',
  `zero_tax_status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '零税率标识',
  `custom_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业自编码',
  `tax_deduction_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '税扣除额',
  `code_version` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码版本号',
  `scene_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场景编码',
  `scene_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场景名称',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `use_gas_types` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型(多个)(可以json，可以分割字符窜)',
  `total_end_time` date NULL DEFAULT NULL COMMENT '累积结束日期',
  `total_start_time` date NULL DEFAULT NULL COMMENT '累积开始日期',
  `total_cycle_count` smallint(6) NULL DEFAULT NULL COMMENT '累积周期数量',
  `total_cycle_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '累积周期金额',
  `total_charge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '合计缴费金额',
  `charge_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费状态：\r\n            UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            CHARGE_FAILURE: 收费失败',
  `charge_time` datetime(0) NULL DEFAULT NULL COMMENT '缴费日期',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态  1: 正常 0: 作废',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收费项缴费记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for m_order
-- ----------------------------
DROP TABLE IF EXISTS `m_order`;
CREATE TABLE `m_order`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单(用于测试)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for m_product
-- ----------------------------
DROP TABLE IF EXISTS `m_product`;
CREATE TABLE `m_product`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `stock` int(11) NULL DEFAULT NULL COMMENT '库存',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  `type_` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品类型\n#ProductType{ordinary:普通;gift:赠品}',
  `type2` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品类型2\n#{ordinary:普通;gift:赠品;}',
  `type3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学历\n@InjectionField(api = DICTIONARY_ITEM_FEIGN_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>\n',
  `status` bit(1) NULL DEFAULT NULL COMMENT '状态',
  `test4` tinyint(10) NULL DEFAULT NULL,
  `test5` date NULL DEFAULT NULL COMMENT '时间',
  `test6` datetime(0) NULL DEFAULT NULL COMMENT '日期',
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `sort_value` int(11) NULL DEFAULT NULL,
  `test7` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'xxx\n@InjectionField(api = “userApi”, method = USER_ID_NAME_METHOD) RemoteData<Long, String>',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户\n@InjectionField(api = USER_ID_FEIGN_CLASS, method = USER_ID_NAME_METHOD) RemoteData<Long, String>',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织\n@InjectionField(api = ORG_ID_FEIGN_CLASS, method = \"findOrgNameByIds\") RemoteData<Long, String>',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品(用于测试)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for m_wx_app
-- ----------------------------
DROP TABLE IF EXISTS `m_wx_app`;
CREATE TABLE `m_wx_app`  (
  `id` bigint(20) NOT NULL COMMENT '微信公众号号唯一标识',
  `app_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信公众号appid',
  `app_secret` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信公众号appsecret',
  `token` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信公众号token',
  `pay_mach_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信支付商户号',
  `pay_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信支付key',
  `notify_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信支付回调url',
  `pay_sslcert_path` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证书地址',
  `pay_sslcert_key` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证书密钥',
  `notes` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公众号备注',
  `pay_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付客户ip',
  `fun_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能支持',
  `channel` int(11) NULL DEFAULT NULL COMMENT 'APP渠道(1:微信公众平台 , 2:微信开放平台)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for m_wx_pay
-- ----------------------------
DROP TABLE IF EXISTS `m_wx_pay`;
CREATE TABLE `m_wx_pay`  (
  `id` bigint(20) NOT NULL COMMENT '微信交易记录唯一标识',
  `pay_notes` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付备注',
  `body` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `detail` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品详情',
  `product_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品id',
  `order_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统订单号',
  `transaction_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信支付订单号',
  `order_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单标题',
  `time_start` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易起始时间	',
  `time_expire` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易结束时间	',
  `pay` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `pay_openId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付人唯一标识',
  `pay_state` int(11) NULL DEFAULT 0 COMMENT '支付状态（1：已支付，0：待支付）',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `notify` int(11) NULL DEFAULT NULL COMMENT '通知(1:已通知,0:未通知)',
  `notify_conten` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知内容',
  `isSandbox` smallint(6) NULL DEFAULT 0 COMMENT '是否沙箱环境账号',
  `auth_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order`(`order_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT = '微信订单记录';

-- 微信退款记录
DROP TABLE IF EXISTS `m_wx_refund`;
CREATE TABLE `m_wx_refund` (
  `id` bigint(20) NOT NULL COMMENT '微信退款记录唯一标识',
  `pay_notes` varchar(50) DEFAULT NULL COMMENT '支付备注',
  `body` varchar(128) DEFAULT NULL COMMENT '商品描述',
  `detail` varchar(1000) DEFAULT NULL COMMENT '商品详情',
  `product_id` varchar(50) DEFAULT NULL COMMENT '产品id',
  `order_number` varchar(50) DEFAULT NULL COMMENT '系统订单号',
  `transaction_id` varchar(50) DEFAULT NULL COMMENT '微信支付订单号',
  `out_refund_no` varchar(64) DEFAULT NULL COMMENT '商户退款单号',
  `refund_id` varchar(32) DEFAULT NULL COMMENT '微信退款单号',
  `pay` decimal(10,2) DEFAULT NULL COMMENT '订单支付金额',
  `refund_fee` decimal(10,2) DEFAULT NULL COMMENT '申请退款金额',
  `settlement_refund_fee` decimal(10,2) DEFAULT NULL COMMENT '实际退款金额',
  `refund_status` int(1) DEFAULT '0' COMMENT '退款状态：0-申请成功，1-退款成功，2-退款异常，3-退款关闭',
  `success_time` datetime DEFAULT NULL COMMENT '退款成功时间',
  `notify` int(1) DEFAULT NULL COMMENT '通知(1:已通知,0:未通知)',
  `refund_recv_accout` varchar(32) DEFAULT NULL COMMENT '退款入账账户:0-退回银行卡,1-退回支付用户零钱,2-退还商户,3-退回支付用户零钱通',
  `refund_desc` varchar(80) DEFAULT '' COMMENT '退款原因',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order`(`order_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT = '微信退款记录';

DROP TABLE IF EXISTS `m_wx_bill`;
CREATE TABLE `m_wx_bill` (
  `id` bigint(20) NOT NULL COMMENT '微信对账单唯一标识',
	`transaction_date` date DEFAULT NULL COMMENT '交易日期',
	`order_pay` decimal(10,2) DEFAULT NULL COMMENT '系统订单金额',
  `transaction_time` varchar(20) DEFAULT null COMMENT '交易时间',
  `public_account_id` varchar(20) DEFAULT '' COMMENT '公众账号ID',
  `merchant_number` int(10) DEFAULT NULL COMMENT '商户号',
  `sub_merchant_number` int(10) DEFAULT NULL COMMENT '特约商户号',
  `device_info` int(10) DEFAULT null COMMENT '设备号',
  `transaction_id` varchar(50) DEFAULT '' COMMENT '微信订单号',
  `order_number` varchar(50) DEFAULT NULL COMMENT '系统订单号（商户订单号）',
  `open_id` varchar(50) DEFAULT NULL COMMENT '用户标识',
  `trade_type` int(1) DEFAULT NULL COMMENT '交易类型',
  `trade_state` int(1) DEFAULT NULL COMMENT '交易状态',
  `paying_bank` varchar(20) DEFAULT '' COMMENT '付款银行',
  `currency` varchar(5) DEFAULT '' COMMENT '货币种类',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '微信记录应结订单金额',
  `voucher` decimal(10,2) DEFAULT NULL COMMENT '代金券金额',
  `refund_id` varchar(32) DEFAULT NULL COMMENT '微信退款单号',
  `out_refund_no` varchar(64) DEFAULT NULL COMMENT '商户退款单号',
  `settlement_refund_fee` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `coupon_refund` decimal(10,2) DEFAULT NULL COMMENT '充值券退款金额',
  `refund_type` int(1) DEFAULT NULL COMMENT '退款类型',
  `refund_state` int(1) DEFAULT NULL COMMENT '退款状态',
  `product_name` varchar(50) DEFAULT '' COMMENT '商品名称',
  `merchant_data` varchar(50) DEFAULT '' COMMENT '商户数据包',
  `service_charge` decimal(10,5) DEFAULT NULL COMMENT '手续费',
  `rate` decimal(10,2) DEFAULT NULL COMMENT '费率',
  `pay` decimal(10,2) DEFAULT NULL COMMENT '微信记录订单金额',
  `refund_fee` decimal(10,2) DEFAULT NULL COMMENT '申请退款金额',
  `rate_remark` varchar(64) DEFAULT '' COMMENT '费率备注',
	`customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
	`contrast_state` int(1) DEFAULT NULL COMMENT '对比状态:0-正常，1-异常',
  `contrast_remark` varchar(50) DEFAULT '' COMMENT '状态异常描述',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_transTime`(`transaction_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT = '微信交易账单';

-- ----------------------------
-- Table structure for mail_provider
-- ----------------------------
DROP TABLE IF EXISTS `mail_provider`;
CREATE TABLE `mail_provider`  (
  `id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'ID',
  `mail_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'TENCENT' COMMENT '邮箱类型\n#MailType{SINA:新浪;QQ:腾讯;WY163:网易}',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱授权码',
  `host` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '主机',
  `port` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '端口',
  `protocol` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '协议',
  `auth` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否进行用户名密码校验',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `properties` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件供应商' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mail_send_status
-- ----------------------------
DROP TABLE IF EXISTS `mail_send_status`;
CREATE TABLE `mail_send_status`  (
  `id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务id\n#mail_task',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收件邮箱',
  `mail_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'UNREAD' COMMENT '邮件状态\r\n#MailStatus{UNREAD:未读;READ:已读;DELETED:已删除;ABNORMAL:异常;VIRUSES:病毒;TRASH:垃圾}',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件发送状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mail_task
-- ----------------------------
DROP TABLE IF EXISTS `mail_task`;
CREATE TABLE `mail_task`  (
  `id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'ID',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'WAITING' COMMENT '执行状态\r\n#TaskStatus{WAITING:等待执行;SUCCESS:执行成功;FAIL:执行失败}',
  `provider_id` bigint(20) NULL DEFAULT NULL COMMENT '发件人id\n#mail_provider',
  `to` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '收件人\n多个,号分割',
  `cc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '抄送人\n多个,分割',
  `bcc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密送人\n多个,分割',
  `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主题',
  `body` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '正文',
  `err_msg` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '发送失败原因',
  `sender_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '发送商编码',
  `plan_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计划发送时间\n(默认当前时间，可定时发送)',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for msgs_center_info
-- ----------------------------
DROP TABLE IF EXISTS `msgs_center_info`;
CREATE TABLE `msgs_center_info`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务ID\n业务表的唯一id',
  `biz_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务类型\n#MsgsBizType{USER_LOCK:账号锁定;USER_REG:账号申请;WORK_APPROVAL:考勤审批;}',
  `msgs_center_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NOTIFY' COMMENT '消息类型\n#MsgsCenterType{WAIT:待办;NOTIFY:通知;PUBLICITY:公告;WARN:预警;}',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '发布人',
  `handler_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '处理地址\n以http开头时直接跳转，否则与#c_application表拼接后跳转\nhttp可带参数',
  `handler_params` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '处理参数',
  `is_single_handle` bit(1) NULL DEFAULT b'1' COMMENT '是否单人处理',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '秦川修改时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '秦川修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息中心表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for msgs_center_info_receive
-- ----------------------------
DROP TABLE IF EXISTS `msgs_center_info_receive`;
CREATE TABLE `msgs_center_info_receive`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `msgs_center_id` bigint(20) NOT NULL COMMENT '消息中心ID\n#msgs_center_info',
  `user_id` bigint(20) NOT NULL COMMENT '接收人ID\n#c_user',
  `is_read` bit(1) NULL DEFAULT b'0' COMMENT '是否已读\n#BooleanStatus',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '秦川修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '秦川修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息中心接收表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_company_use_node_type
-- ----------------------------
DROP TABLE IF EXISTS `pt_company_use_node_type`;
CREATE TABLE `pt_company_use_node_type`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `node_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '厂家ID',
  `node_type_id` bigint(32) NULL DEFAULT NULL COMMENT '型号ID',
  `node_facotry_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂家名称',
  `node_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '型号名称',
  `use_status` smallint(6) NULL DEFAULT NULL COMMENT '启用\r\n            禁用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '节点类型管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_gas_meter_factory
-- ----------------------------
DROP TABLE IF EXISTS `pt_gas_meter_factory`;
CREATE TABLE `pt_gas_meter_factory`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `gas_meter_factory_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '厂家名称',
  `gas_meter_factory_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '厂家编号',
  `gas_meter_factory_mark_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据状态  2 秦川 其他待定',
  `gas_meter_factory_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '厂家地址',
  `telphone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `contacts` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `fax_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '传真号码',
  `manager` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经理',
  `tax_registration_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '税务登记号',
  `bank` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账号',
  `gas_meter_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '普表\r\n ic卡智能燃气表\r\n 物联网表\r\n 流量计(普表)\r\n 流量 计(ic卡表)\r\n 流量计(物联网表)\r\n',
  `gas_meter_factory_status` smallint(6) NOT NULL DEFAULT 1 COMMENT '厂家状态：（0：启用；1：禁用）',
  `compatible_parameter` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '兼容参数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识（0：存在；1-删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表厂家' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_gas_meter_model
-- ----------------------------
DROP TABLE IF EXISTS `pt_gas_meter_model`;
CREATE TABLE `pt_gas_meter_model`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_id` bigint(32) NOT NULL COMMENT '生产厂家ID',
  `company_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产厂家编号',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产厂家名称',
  `gas_meter_version_id` bigint(32) NOT NULL COMMENT '版号ID',
  `gas_meter_version_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号名称',
  `model_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '型号名称',
  `data_status` smallint(6) NULL DEFAULT 1 COMMENT '是否启用（0：启用；1-禁用）',
  `specification` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '型号规格',
  `nominal_flow_rate` decimal(12, 2) NULL DEFAULT NULL COMMENT '最大流量',
  `max_flow` decimal(12, 2) NULL DEFAULT NULL COMMENT '最大流量',
  `min_flow` decimal(8, 3) NULL DEFAULT NULL COMMENT '最小流量',
  `max_pressure` decimal(12, 2) NULL DEFAULT NULL COMMENT '最大压力',
  `max_word_wheel` decimal(12, 5) NULL DEFAULT NULL COMMENT '字轮最大值',
  `rotational_volume` decimal(12, 2) NULL DEFAULT NULL COMMENT '回转体积',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识（0：存在；1-删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表型号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_gas_meter_version
-- ----------------------------
DROP TABLE IF EXISTS `pt_gas_meter_version`;
CREATE TABLE `pt_gas_meter_version`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `gas_meter_version_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号名称',
  `gas_meter_describe` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号描述',
  `company_id` bigint(32) NULL DEFAULT NULL COMMENT '生产厂家ID',
  `company_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产厂家编号',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产厂家名称',
  `equipment_vendors_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '无线通信设备厂家名称',
  `wireless_version` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '无线通信版本',
  `order_source_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单来源名称',
  `amount_mark` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金额标志',
  `remote_service_flag` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `amount_digits` smallint(6) NULL DEFAULT NULL COMMENT '气量表金额位数',
  `single_level_amount` smallint(6) NULL DEFAULT 0,
  `ic_card_core_mark` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IC卡内核标识',
  `measurement_accuracy` smallint(6) NULL DEFAULT NULL COMMENT '计费精度（小数位数）',
  `select_address` smallint(6) NULL DEFAULT 0,
  `version_state` smallint(6) NULL DEFAULT 1 COMMENT '开启\r\n		禁用',
  `accumulated_amount` smallint(6) NULL DEFAULT 0,
  `accumulated_count` smallint(6) NULL DEFAULT 0,
  `card_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号类型',
  `card_number_prefix` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号前缀',
  `card_number_length` smallint(6) NULL DEFAULT NULL COMMENT '卡号长度',
  `open_in_meter_number` smallint(6) NULL DEFAULT NULL COMMENT '开户录入表号',
  `issuing_cards` smallint(6) NULL DEFAULT 0,
  `verification_table_no` smallint(6) NULL DEFAULT 0 COMMENT '远程业务标志',
  `openValve` int(1) NULL DEFAULT 0 COMMENT '是否开阀：默认0;1、是;0、否',
  `radix` int(1) NULL DEFAULT 0 COMMENT '是否录入底数：默认0;1、是;0、否',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号说明(备注)',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识（0：存在；1-删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表版本' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_business_hall
-- ----------------------------
DROP TABLE IF EXISTS `pz_business_hall`;
CREATE TABLE `pz_business_hall`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `business_hall_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅地址',
  `contacts` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `telphone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `business_hall_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅电话',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系邮件',
  `point_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置类型',
  `is_sale` smallint(6) NULL DEFAULT NULL COMMENT '代售点',
  `point_of_sale` smallint(6) NULL DEFAULT NULL COMMENT '代售点（配额）',
  `restrict_status` smallint(6) NULL DEFAULT NULL COMMENT '营业限制0：不限制，1：限制',
  `balance` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '营业余额',
  `single_limit` decimal(18, 4) NULL DEFAULT NULL COMMENT '单笔限额',
  `business_hall_status` smallint(6) NULL DEFAULT NULL COMMENT '状态1：启用 0：停用',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_user` bigint(32) NULL DEFAULT NULL COMMENT '删除人id',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  `delete_status` smallint(6) NULL DEFAULT 1 COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '营业厅信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_business_template
-- ----------------------------
DROP TABLE IF EXISTS `pz_business_template`;
CREATE TABLE `pz_business_template`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `template_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板编码',
  `template_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `file_id` bigint(20) NULL DEFAULT NULL COMMENT '文件id',
  `item_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `item_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_template_code`(`template_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_community
-- ----------------------------
DROP TABLE IF EXISTS `pz_community`;
CREATE TABLE `pz_community`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司ID',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `street_id` bigint(32) NULL DEFAULT NULL COMMENT '街道ID',
  `community_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小区名称',
  `community_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小区地址',
  `longitude` decimal(18, 8) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18, 8) NULL DEFAULT NULL COMMENT '纬度',
  `meter_count` int(11) NULL DEFAULT NULL COMMENT '表数',
  `dismantle_count` int(11) NULL DEFAULT NULL COMMENT '已拆数',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态（1-生效，0-无效）',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '删除状态(1-删除,0-有效)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1337585588567539713 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_company_user_quota_record
-- ----------------------------
DROP TABLE IF EXISTS `pz_company_user_quota_record`;
CREATE TABLE `pz_company_user_quota_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司code',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `user_id` bigint(32) NULL DEFAULT NULL COMMENT '用户ID',
  `quota_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '\n#QuotaType{CUMULATIVE:累加;COVER:覆盖;}',
  `quota_time` datetime(0) NULL DEFAULT NULL COMMENT '配额时间',
  `money` decimal(18, 4) NULL DEFAULT NULL COMMENT '金额',
  `single_limit` decimal(18, 4) NULL DEFAULT NULL COMMENT '单笔限额',
  `total_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '配后金额',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '0：不限制\r\n            1：限制',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '营业厅配额记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_detailed_address
-- ----------------------------
DROP TABLE IF EXISTS `pz_detailed_address`;
CREATE TABLE `pz_detailed_address`  (
  `id` bigint(32) NOT NULL,
  `community_id` bigint(10) NULL DEFAULT NULL COMMENT '小区id',
  `building` smallint(10) NULL DEFAULT NULL COMMENT '栋',
  `unit` smallint(10) NULL DEFAULT NULL COMMENT '单元',
  `detail_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址（不包含栋，单元的地址）',
  `more_detail_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '包括栋单元的详细地址',
  `flag` smallint(1) NULL DEFAULT NULL COMMENT '0表示农村，1表示城市',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  `delete_user` bigint(32) NULL DEFAULT NULL COMMENT '删除人',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '删除状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_exception_rule_conf
-- ----------------------------
DROP TABLE IF EXISTS `pz_exception_rule_conf`;
CREATE TABLE `pz_exception_rule_conf`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司code',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型id',
  `surge_multiple` decimal(4, 1) NULL DEFAULT NULL COMMENT '暴增倍数（大于1）',
  `sharp_decrease` decimal(4, 1) NULL DEFAULT NULL COMMENT '锐减比例（0~1）',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抄表异常规则配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_function_switch
-- ----------------------------
DROP TABLE IF EXISTS `pz_function_switch`;
CREATE TABLE `pz_function_switch`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `open_id_card` smallint(6) NULL DEFAULT NULL COMMENT '启用ID卡  启用1，禁用0',
  `open_customer_prefix` smallint(6) NULL DEFAULT NULL COMMENT '自编客户编号前缀',
  `transfer_account_flag` int(1) NULL DEFAULT 0 COMMENT '过户修改缴费编号：1是;0否',
  `customer_prefix` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号前缀',
  `settlement_date` date NULL DEFAULT NULL COMMENT '结算日期',
  `open_vat_invoice` smallint(6) NULL DEFAULT 1 COMMENT '启用增值税发票',
  `tax_rate` decimal(5, 2) NULL DEFAULT NULL COMMENT '税率',
  `rounding` smallint(6) NULL DEFAULT NULL COMMENT '取整方式',
  `open_black_list` smallint(6) NULL DEFAULT NULL COMMENT '启用黑名单限购',
  `black_max_volume` decimal(18, 2) NULL DEFAULT NULL COMMENT '黑名单限购最大充值气量',
  `black_max__money` decimal(18, 2) NULL DEFAULT NULL COMMENT '黑名单限购最大充值金额',
  `open_check_limit` smallint(6) NULL DEFAULT NULL COMMENT '启动安检限购',
  `check_max_volume` decimal(18, 2) NULL DEFAULT NULL COMMENT '安检限购最大充值气量',
  `check_max_money` decimal(18, 2) NULL DEFAULT NULL COMMENT '安检限购最大充值金额',
  `invoice_code_rule` int(1) NULL DEFAULT 1 COMMENT '发票编码规则设置:1自动生成,0人工录入',
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '功能项配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_function_switch_plus
-- ----------------------------
DROP TABLE IF EXISTS `pz_function_switch_plus`;
CREATE TABLE `pz_function_switch_plus`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `item` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'key',
  `value` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '值',
  `desc_value` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '功能项配置plus' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_give_reduction_conf
-- ----------------------------
DROP TABLE IF EXISTS `pz_give_reduction_conf`;
CREATE TABLE `pz_give_reduction_conf`  (
  `ID` bigint(32) NOT NULL COMMENT 'ID',
  `activity_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `give_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '赠送气量（充值场景才能设置）',
  `activity_money_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动金额方式（缴费场景无百分比选项）\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            percent: 百分比',
  `activity_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '活动金额',
  `activity_percent` decimal(18, 4) NULL DEFAULT NULL COMMENT '活动比例',
  `activity_scene` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动场景\r\n            RECHARGE_GIVE: 充值赠送\r\n            PRECHARGE_GIVE: 预存赠送\r\n            CHARGE_DE: 缴费减免',
  `toll_item_scene` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项场景编码',
  `toll_item_id` bigint(32) NULL DEFAULT NULL COMMENT '收费项ID',
  `use_gas_types` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型(只针对充值场景)\r\n            存放jsonarray数据 包含用气类型id和用气类型名称。',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '是否启用\r\n            1 启用\r\n            0 不启用',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '删除状态\r\n            1 删除\r\n            0 正常',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '赠送减免活动配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_invoice_param
-- ----------------------------
DROP TABLE IF EXISTS `pz_invoice_param`;
CREATE TABLE `pz_invoice_param`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `account_level` smallint(6) NULL DEFAULT NULL COMMENT '账号等级（0-总公司，1-分公司）',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销方名称',
  `taxpayer_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销方纳税人编号',
  `address` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销方地址',
  `telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销方电话',
  `bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销方开户行名称',
  `bank_account` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销方银行账户',
  `invoice_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` bigint(32) NULL DEFAULT NULL COMMENT '删除标识 1：存在 0：删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_invoice_plat_config
-- ----------------------------
DROP TABLE IF EXISTS `pz_invoice_plat_config`;
CREATE TABLE `pz_invoice_plat_config`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `plat_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子发票服务平台编码',
  `plat_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子发票平台名称',
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用标识：发票服务平台授权的应用唯一标识',
  `app_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用密码：发票平台授权的密码',
  `other_param` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其它参数：使用json格式保存',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求路径',
  `contacts` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `fileName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件名称',
  `fileUrl` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件保存地址',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '是否启用状态 0：未启用, 1:启用',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电子发票服务平台配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_mobile_message
-- ----------------------------
DROP TABLE IF EXISTS `pz_mobile_message`;
CREATE TABLE `pz_mobile_message`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `template_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板编码',
  `template_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `template_describe` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板描述',
  `template_cntent` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板内容',
  `file_path` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `template_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信模板配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `pz_pay_info`;
CREATE TABLE `pz_pay_info`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `account_level` smallint(6) NULL DEFAULT NULL COMMENT '账号等级（0-总公司，1-分公司）',
  `used_parent_pay` smallint(6) NULL DEFAULT NULL COMMENT '使用总公司账号',
  `pay_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付类型编码',
  `pay_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付类型名称',
  `native_merchant_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '秦川分配的商户号',
  `third_merchant_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成都支付通分配的商户号',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'APPID',
  `app_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'APPSECRET',
  `api_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'API秘钥',
  `merchant_private_key` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户私钥',
  `alipay_public_key` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付宝公钥',
  `service_ratio` decimal(4, 3) NULL DEFAULT NULL COMMENT '提现用户承担手续费比例',
  `service_status` smallint(6) NULL DEFAULT 0 COMMENT '是否启用手续费比例（0-启用，1-禁用）',
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `pay_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识',
  `data_status` smallint(1) NULL DEFAULT 1 COMMENT '是否启用:1 启用；0 不启用',
  `interface_mode` smallint(1) NULL DEFAULT 1 COMMENT '接口调用方式:0 本地调用；1 成都支付通',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_penalty
-- ----------------------------
DROP TABLE IF EXISTS `pz_penalty`;
CREATE TABLE `pz_penalty`  (
  `ID` bigint(32) NOT NULL AUTO_INCREMENT,
  `execute_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '滞纳金名称',
  `use_gas_type_id` varchar(1500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型ID',
  `execute_day` int(11) NULL DEFAULT NULL COMMENT '滞纳金执行日',
  `rate` decimal(18, 4) NULL DEFAULT NULL COMMENT '利率',
  `compound_interest` smallint(6) NULL DEFAULT NULL COMMENT '复利',
  `principal_cap` smallint(6) NULL DEFAULT NULL COMMENT '本金及上限',
  `fixed_cap` decimal(18, 4) NULL DEFAULT NULL COMMENT '固定上限',
  `activation_time` date NULL DEFAULT NULL COMMENT '启用时间',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态，1表示未超过本金呢，2超过本金，3启用，4未启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `execute_month` int(11) NULL DEFAULT NULL COMMENT '滞纳金执行月',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1339846629800280065 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '滞纳金方案配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_preferential
-- ----------------------------
DROP TABLE IF EXISTS `pz_preferential`;
CREATE TABLE `pz_preferential`  (
  `ID` bigint(32) NOT NULL COMMENT 'ID',
  `PZ__id` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类_ID',
  `use_gas_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型code',
  `use_gas_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `preferential_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠活动名称',
  `preferential_activate` smallint(6) NULL DEFAULT NULL COMMENT '活动有效期',
  `is_always_preferential` smallint(6) NULL DEFAULT NULL COMMENT '持续优惠',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '是否启用',
  `preferential_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠气量',
  `preferential_type` smallint(6) NULL DEFAULT NULL COMMENT '优惠方式',
  `preferential_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠金额',
  `preferential_percent` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠比例',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '删除状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_price_mapping
-- ----------------------------
DROP TABLE IF EXISTS `pz_price_mapping`;
CREATE TABLE `pz_price_mapping`  (
  `id` bigint(20) NOT NULL,
  `price_id` bigint(20) NOT NULL COMMENT '价格id',
  `price_num` int(11) NOT NULL COMMENT '价格号',
  `use_gas_type_num` int(11) NOT NULL COMMENT '用气类型号',
  `gas_meter_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '气表档案编号',
  `pre_price_id` bigint(20)   COMMENT '上次价格方案id ',
  `pre_price_num` int(11)   COMMENT '上次价格号',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型id',
  `pre_use_gas_type_num` int(11)   COMMENT '上次用气类型号',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '价格方案映射表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_price_scheme
-- ----------------------------
DROP TABLE IF EXISTS `pz_price_scheme`;
CREATE TABLE `pz_price_scheme`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '价格id',
  `company_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司id',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型ID',
  `compensation_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '补差价格',
  `gas_1` decimal(18, 4) NULL DEFAULT NULL COMMENT '一阶气量',
  `price_1` decimal(18, 4) NULL DEFAULT NULL COMMENT '一阶价格',
  `gas_2` decimal(18, 4) NULL DEFAULT NULL COMMENT '二阶气量',
  `price_2` decimal(18, 4) NULL DEFAULT NULL COMMENT '二阶价格',
  `gas_3` decimal(18, 4) NULL DEFAULT NULL COMMENT '三阶气量',
  `price_3` decimal(18, 4) NULL DEFAULT NULL COMMENT '三阶价格',
  `gas_4` decimal(18, 4) NULL DEFAULT NULL COMMENT '四阶气量',
  `price_4` decimal(18, 4) NULL DEFAULT NULL COMMENT '四阶价格',
  `gas_5` decimal(18, 4) NULL DEFAULT NULL COMMENT '五阶气量id',
  `price_5` decimal(18, 4) NULL DEFAULT NULL COMMENT '五阶价格',
  `gas_6` decimal(18, 4) NULL DEFAULT NULL COMMENT '六阶气量',
  `price_6` decimal(18, 4) NULL DEFAULT NULL COMMENT '六阶价格',
  `gas_7` decimal(18, 4) NULL DEFAULT NULL COMMENT '七阶气量',
  `price_7` decimal(18, 4) NULL DEFAULT NULL COMMENT '七阶价格',
  `start_time` date NULL DEFAULT NULL COMMENT '启用时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `cycle_start_time` date NULL DEFAULT NULL COMMENT '切换时间',
  `start_month` smallint(6) NULL DEFAULT NULL COMMENT '起始月',
  `settlement_day` smallint(6) NULL DEFAULT NULL COMMENT '结算日',
  `cycle` smallint(6) NULL DEFAULT NULL COMMENT '周期',
  `price_num` int(11) NULL DEFAULT 1 COMMENT '价格方案号',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户',
  `price_change_is_clear` smallint(6) NULL DEFAULT 0 COMMENT '调价是否清零(1-清零，0-不清零)',
  `fixed_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '固定单价',
  `is_heating` smallint(6) NULL DEFAULT 0 COMMENT '0-非采暖，1-采暖',
  `cycle_enable_date` date NULL DEFAULT NULL COMMENT '周期开始时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1339828131380330497 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '价格配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_purchase_limit
-- ----------------------------
DROP TABLE IF EXISTS `pz_purchase_limit`;
CREATE TABLE `pz_purchase_limit`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `limit_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '限制名称',
  `use_gas_type_id` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `limit_type` smallint(30) NULL DEFAULT NULL COMMENT '1-个人  0-所有',
  `start_time` date NULL DEFAULT NULL COMMENT '启用时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `cycle` int(11) NULL DEFAULT NULL COMMENT '周期',
  `max_charge_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '最大充值气量',
  `max_charge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '最大充值金额',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户',
  `cycle_num` int(11) NULL DEFAULT NULL COMMENT '周期重复次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1311199346057805825 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '限购配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_security_check
-- ----------------------------
DROP TABLE IF EXISTS `pz_security_check`;
CREATE TABLE `pz_security_check`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `security_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检项编码',
  `security_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检项名称',
  `danger_level` smallint(6) NULL DEFAULT NULL COMMENT '隐患级别(1-一般事故隐患，2-中度事故隐患，0-重度事故隐患)',
  `data_status` smallint(6) NULL DEFAULT 1 COMMENT '数据状态(1-有效，0-无效)',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '删除状态(1-删除，0-有效)',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '安检子项配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_security_check_item
-- ----------------------------
DROP TABLE IF EXISTS `pz_security_check_item`;
CREATE TABLE `pz_security_check_item`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子项名称',
  `security_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检项编码',
  `security_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安检项名称',
  `danger_level` smallint(6) NULL DEFAULT NULL COMMENT '隐患级别(1-一般事故隐患，2-中度事故隐患，0-重度事故隐患)',
  `data_status` smallint(6) NULL DEFAULT 1 COMMENT '数据状态(1-有效，0-无效)',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '删除状态(1-删除，0-有效)',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '安检子项配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_street
-- ----------------------------
DROP TABLE IF EXISTS `pz_street`;
CREATE TABLE `pz_street`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司ID',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `province_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省code',
  `province_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省名称',
  `city_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市code',
  `city_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市名称',
  `area_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区code',
  `area_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区名称',
  `street_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '街道名称',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态（1-生效，0-无效）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户id',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  `delete_user` bigint(32) NULL DEFAULT NULL COMMENT '删除用户id',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '删除状态(1-删除,0-有效)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1336498802260967425 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_template
-- ----------------------------
DROP TABLE IF EXISTS `pz_template`;
CREATE TABLE `pz_template`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `parent_id` bigint(32) NULL DEFAULT NULL COMMENT '父级id',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `template_type_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板类型编码',
  `template_type_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板类型名称',
  `template_type_describe` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板类型描述',
  `template_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模板类型配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_template_item
-- ----------------------------
DROP TABLE IF EXISTS `pz_template_item`;
CREATE TABLE `pz_template_item`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `template_type_id` bigint(20) NULL DEFAULT NULL COMMENT '模板类型id',
  `template_type_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板类型名称',
  `template_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板编码',
  `template_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `template_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板标题',
  `file_id` bigint(32) NULL DEFAULT NULL COMMENT '文件id',
  `default_file_id` bigint(32) NULL DEFAULT NULL COMMENT '默认文件id',
  `file_path` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `item_status` smallint(6) NULL DEFAULT 1 COMMENT '状态（1-默认文件，0-自定义文件）',
  `default_status` smallint(6) NULL DEFAULT NULL COMMENT '是否默认模板（0-默认，1-非默认）',
  `item_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `item_content` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '内容',
  `data_status` smallint(6) NULL DEFAULT -2 COMMENT '审核状态（-1-未通过，0-提交但未审核，1-审核通过，-2-未提交审核）',
  `reject_reson` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '驳回理由',
  `sort_value` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模板项目表（Item）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_template_record
-- ----------------------------
DROP TABLE IF EXISTS `pz_template_record`;
CREATE TABLE `pz_template_record`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `template_id` bigint(20) NULL DEFAULT NULL COMMENT '模板id',
  `template_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板编码',
  `template_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '默认模板配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_toll_item
-- ----------------------------
DROP TABLE IF EXISTS `pz_toll_item`;
CREATE TABLE `pz_toll_item`  (
  `id` bigint(32) NOT NULL,
  `level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'DEFAULT：默认(不可编辑项，由平台统一管理)\r\n            COMPANY：公司级',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `sys_item_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统项编码',
  `item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项名称',
  `charge_frequency` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费频次\r\n            ON_DEMAND:按需\r\n            ONE_TIME：一次性\r\n            BY_MONTH：按月\r\n            QUARTERLY：按季\r\n            BY_YEAR：按年',
  `charge_period` smallint(6) NULL DEFAULT NULL COMMENT '收费期限',
  `cycle_value` smallint(6) NULL DEFAULT NULL COMMENT '周期值,固定1',
  `money` decimal(18, 4) NULL DEFAULT NULL COMMENT '金额',
  `money_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金额方式\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            默认固定金额\r\n            ',
  `financial_subject` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '财务科目',
  `vat_general_rate` decimal(18, 4) NULL DEFAULT NULL COMMENT '增值税普税税率',
  `tax_category_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '税收分类编码',
  `favoured_status` smallint(6) NULL DEFAULT NULL COMMENT '是否享受优惠 0：表示享受优惠，1：不享受优惠',
  `favoured_policy` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠政策',
  `zero_tax_status` smallint(6) NULL DEFAULT NULL COMMENT '零税率标识 0表示是零税率，1表示不是零税率',
  `custom_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业自编码',
  `tax_deduction_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '税扣除额',
  `code_version` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码版本号',
  `scene_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场景编码',
  `scene_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场景名称',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `use_gas_types` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型(多个)(可以json，可以分割字符窜)',
  `data_status` smallint(6) NULL DEFAULT 0 COMMENT '状态\r\n            1 启用\r\n            0 禁用',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除状态\r\n            1 删除\r\n            0 正常',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收费项配置\r\n开户费\r\n换表费\r\n补卡费\r\n报装费\r\n卡费' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pz_use_gas_type
-- ----------------------------
DROP TABLE IF EXISTS `pz_use_gas_type`;
CREATE TABLE `pz_use_gas_type`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司ID',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `use_type_num` int(11) NULL DEFAULT NULL COMMENT '用气类型编号',
  `user_type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户类型名称',
  `use_gas_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `gas_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'NATURAL_GAS:天然气\r\n            COAL_GAS:煤气\r\n            LIQUID_GAS:液化气',
  `price_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HEATING_PRICE:采暖价\r\n            LADDER_PRICE:阶梯价\r\n            FIXED_PRICE:固定价',
  `max_charge_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '最大充值气量',
  `max_charge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '最大充值金额',
  `max_deposit` decimal(18, 4) NULL DEFAULT NULL COMMENT '最大储蓄量',
  `min_deposit` decimal(18, 4) NULL DEFAULT NULL COMMENT '最小储蓄量',
  `alarm_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '报警气量',
  `alarm_money` decimal(18,4) DEFAULT NULL COMMENT '一级报警金额',
  `alarm_money_two` decimal(18,4) DEFAULT NULL COMMENT '二级余额不足报警',
  `open_decrement` smallint(6) NULL DEFAULT NULL COMMENT '开户按月递减低价气量;1-开启,0-关闭',
  `decrement` decimal(18, 4) NULL DEFAULT NULL COMMENT '递减量',
  `population_growth` smallint(6) NULL DEFAULT NULL COMMENT '启用家庭人口增量;1-启用,0-禁用',
  `population_base` int(11) NULL DEFAULT NULL COMMENT '家庭人口基数',
  `population_increase` decimal(11, 0) NULL DEFAULT NULL COMMENT '人口增量',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_use_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  `delete_user` bigint(32) NULL DEFAULT NULL COMMENT '删除人ID',
  `delete_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人名称',
  `delete_status` smallint(6) UNSIGNED NULL DEFAULT NULL COMMENT '删除状态(0-有效，1-删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1341276876022218753 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qw_iot_alarm
-- ----------------------------
DROP TABLE IF EXISTS `qw_iot_alarm`;
CREATE TABLE `qw_iot_alarm`  (
  `id` bigint(20) NOT NULL,
  `alarm_status` smallint(6) NULL DEFAULT NULL COMMENT '报警状态',
  `alarm_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警类型',
  `alarm_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '报警信息',
  `device_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报警信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qw_iot_device_data_history
-- ----------------------------
DROP TABLE IF EXISTS `qw_iot_device_data_history`;
CREATE TABLE `qw_iot_device_data_history`  (
  `id` bigint(20) NOT NULL,
  `device_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备出厂编号',
  `data_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据类型',
  `device_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '上报数据',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qw_iot_device_params
-- ----------------------------
DROP TABLE IF EXISTS `qw_iot_device_params`;
CREATE TABLE `qw_iot_device_params`  (
  `id` bigint(32) NOT NULL,
  `gas_meter_number` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '表身号',
  `un_upLoad_close` int(11) NULL DEFAULT 5 COMMENT '多天未上报权限关阀：0--禁止；1 - 255 表示天数。 默认：5天',
  `valve_no_upLoad` int(11) NULL DEFAULT NULL COMMENT '多天不上传普通关阀控制参数：0--禁止；1 - 255 表示天数',
  `valve_close_nouse` int(11) NULL DEFAULT NULL COMMENT '多天不用气关阀控制参数：  0--禁止；1 - 255 表示天数',
  `time_interva` int(11) NULL DEFAULT 15 COMMENT '设置错峰时间间隔,单位秒，范围15 - 43。默认15',
  `balance_alarm_value_one` decimal(18, 4) NULL DEFAULT NULL COMMENT '余额不足报警值：值：数字',
  `flowover_enable` smallint(6) NULL DEFAULT NULL COMMENT '过流报警使能(默认关闭),0：禁止；1：使能',
  `timed_param_meter` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '定时上传参数 （标志/周期/小时/分钟)',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '物联网设备参数设置记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qw_iot_gas_meter_command
-- ----------------------------
DROP TABLE IF EXISTS `qw_iot_gas_meter_command`;
CREATE TABLE `qw_iot_gas_meter_command`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `gas_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '厂家id',
  `gas_meter_factory_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂家名称',
  `gas_meter_version_id` bigint(32) NULL DEFAULT NULL COMMENT '版号id',
  `gas_meter_version_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号名称',
  `gas_meter_type_id` bigint(32) NULL DEFAULT NULL COMMENT '气表类型id',
  `gas_meter_type_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '普表\r\n            卡表\r\n            物联网表\r\n            流量计(普表)\r\n            流量计(卡表）\r\n            流量计(物联网表）',
  `gas_meter_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `meter_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `command_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指令类型\n#CommandType{CHANGE_METER:换表;RECHARGE:充值;OPEN_VALVE:开阀;CLOSE_VALVE:关阀;ADJ_PRICE:调价;}',
  `transaction_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QNMS3.0流水号',
  `to_3_time` datetime(0) NULL DEFAULT NULL COMMENT '到达3.0时间',
  `execute_result` smallint(6) NULL DEFAULT 0 COMMENT '是否执行成功(0-未执行，1-执行成功，-1-执行失败)',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型id',
  `use_gas_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `price_id` bigint(32) NULL DEFAULT NULL COMMENT '价格方案id',
  `recharge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '充值金额',
  `command_parameter` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '指令信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '物联网气表指令数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qw_iot_gas_meter_command_detail
-- ----------------------------
DROP TABLE IF EXISTS `qw_iot_gas_meter_command_detail`;
CREATE TABLE `qw_iot_gas_meter_command_detail`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `command_id` bigint(32) NULL DEFAULT NULL COMMENT '指令id',
  `meter_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `gas_meter_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `command_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指令类型\n#CommandType{CHANGE_METER:换表;RECHARGE:充值;OPEN_VALVE:开阀;CLOSE_VALVE:关阀;ADJ_PRICE:调价;}',
  `transaction_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QNMS3.0流水号',
  `to_3_time` datetime(0) NULL DEFAULT NULL COMMENT '到达3.0时间',
  `execute_time` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
  `execute_result` smallint(6) NULL DEFAULT NULL COMMENT '0:未执行\r\n            1:执行成功\r\n            其他自编：失败',
  `command_stage` smallint(6) NULL DEFAULT NULL COMMENT '0:待发送\r\n            1:已发至3.0\r\n            2:已执行\r\n            10：重试\r\n            100：取消执行',
  `command_status` smallint(6) NULL DEFAULT NULL COMMENT '0：无效\r\n            1：有效',
  `command_parameter` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其他参数，建议存Json',
  `error_desc` varchar(500) DEFAULT NULL COMMENT '异常描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '物联网气表指令明细数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qw_iot_gas_meter_day_data
-- ----------------------------
DROP TABLE IF EXISTS `qw_iot_gas_meter_day_data`;
CREATE TABLE `qw_iot_gas_meter_day_data` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `gas_meter_code` varchar(255) DEFAULT NULL COMMENT '气表code',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户姓名',
  `customer_phone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `version_name` varchar(255) DEFAULT NULL COMMENT '版号名称',
  `gas_meter_number` varchar(50) DEFAULT NULL COMMENT '表身号',
  `gas_meter_balance` decimal(18,4) DEFAULT NULL COMMENT '气表余额',
  `total_use_gas` decimal(18,4) DEFAULT NULL COMMENT '累积用气量',
  `day_use_1` decimal(18,4) DEFAULT NULL COMMENT '近一天用气量',
  `day_use_2` decimal(18,4) DEFAULT NULL COMMENT '近2天用气量',
  `day_use_3` decimal(18,4) DEFAULT NULL COMMENT '近3天用气量',
  `day_use_4` decimal(18,4) DEFAULT NULL COMMENT '近4天用气量',
  `day_use_5` decimal(18,4) DEFAULT NULL COMMENT '近5天用气量',
  `valve_state` smallint(6) DEFAULT '0' COMMENT '阀门状态',
  `meter_type` smallint(6) DEFAULT '0' COMMENT '表类型(0-表端,1-中心计费)',
  `freezing_time` datetime DEFAULT NULL COMMENT '冻结时间',
  `install_address` varchar(1000) DEFAULT NULL COMMENT '安装地址',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for qw_iot_gas_meter_upload_data
-- ----------------------------
DROP TABLE IF EXISTS `qw_iot_gas_meter_upload_data`;
CREATE TABLE `qw_iot_gas_meter_upload_data`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `gas_meter_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `gas_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '厂家id',
  `gas_meter_factory_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂家名称',
  `gas_meter_version_id` bigint(32) NULL DEFAULT NULL COMMENT '版号id',
  `gas_meter_version_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版号名称',
  `gas_meter_model_id` bigint(32) NULL DEFAULT NULL COMMENT '气表型号id',
  `gas_meter_model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表型号名称',
  `archive_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备档案id',
  `domain_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '逻辑域id',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表id',
  `gas_meter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `initial_measurement_base` decimal(18, 4) NULL DEFAULT NULL COMMENT '初始气量',
  `total_charge_value` decimal(18, 4) NULL DEFAULT NULL COMMENT '累计充值量',
  `day_use_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '日用金额',
  `day_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '日用氣量',
  `charge_count` int(11) NULL DEFAULT NULL COMMENT '充值次数',
  `use_gas_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `use_gas_type_id` bigint(32) NULL DEFAULT NULL COMMENT '用气类型id',
  `price_scheme_id` bigint(32) NULL DEFAULT NULL COMMENT '价格方案id',
  `current_ladder` smallint(6) NULL DEFAULT NULL COMMENT '当前阶梯',
  `current_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '当前价格',
  `is_online` smallint(6) NULL DEFAULT NULL COMMENT '是否在线',
  `valve_status` smallint(6) NULL DEFAULT NULL COMMENT '阀门状态',
  `direction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通气方向',
  `specification` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '型号规格',
  `ventilate_time` datetime(0) NULL DEFAULT NULL COMMENT '通气时间',
  `install_time` datetime(0) NULL DEFAULT NULL COMMENT '安装时间',
  `latitude` decimal(18, 6) NULL DEFAULT NULL COMMENT '经度',
  `longitude` decimal(18, 6) NULL DEFAULT NULL COMMENT '纬度',
  `cycle_total_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '周期累计用气量',
  `total_use_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '累计用气量',
  `customer_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户code',
  `customer_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `meter_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '户表地址',
  `day_read_num` int(11) NULL DEFAULT 0 COMMENT '抄表数据上报次数',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上报时间',
  `freezing_time` datetime(0) NULL DEFAULT NULL COMMENT '冻结时间',
  `device_state` int(11) NULL DEFAULT -1 COMMENT '设备状态',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '物联网气表上报数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qw_iot_subscribe
-- ----------------------------
DROP TABLE IF EXISTS `qw_iot_subscribe`;
CREATE TABLE `qw_iot_subscribe`  (
  `id` bigint(20) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `licence` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'qnms3.0返回的licence',
  `domain_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '逻辑域id',
  `subscribe` int(11) NULL DEFAULT NULL COMMENT '是否订阅',
  `notice_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知类型',
  `notice_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知地址',
  `notice_cache_day` int(11) NULL DEFAULT NULL COMMENT '通知缓存天数',
  `factory_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂家名称',
  `factory_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂家编码',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'appid',
  `app_secret` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'app密钥',
  `gateway_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网关地址',
  `tenant_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户编码',
  `gateway_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网关名称',
  `gateway_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网关版本',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_send_status
-- ----------------------------
DROP TABLE IF EXISTS `sms_send_status`;
CREATE TABLE `sms_send_status`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务ID\n#sms_task',
  `send_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'WAITING' COMMENT '发送状态\n#SendStatus{WAITING:等待发送;SUCCESS:发送成功;FAIL:发送失败}',
  `receiver` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接收者手机号\n单个手机号',
  `biz_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '发送回执ID\n阿里：发送回执ID,可根据该ID查询具体的发送状态  腾讯：sid 标识本次发送id，标识一次短信下发记录  百度：requestId 短信发送请求唯一流水ID',
  `ext` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '发送返回\n阿里：RequestId 请求ID  腾讯：ext：用户的session内容，腾讯server回包中会原样返回   百度：无',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '状态码\n阿里：返回OK代表请求成功,其他错误码详见错误码列表  腾讯：0表示成功(计费依据)，非0表示失败  百度：1000 表示成功',
  `message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '状态码的描述',
  `fee` int(11) NULL DEFAULT 0 COMMENT '短信计费的条数\n腾讯专用',
  `create_month` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建时年月\n格式：yyyy-MM 用于统计',
  `create_week` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建时年周\n创建时处于当年的第几周 yyyy-ww 用于统计',
  `create_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建时年月日\n格式： yyyy-MM-dd 用于统计',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '秦川修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '秦川修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信发送状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_sign
-- ----------------------------
DROP TABLE IF EXISTS `sms_sign`;
CREATE TABLE `sms_sign`  (
  `id` bigint(20) NOT NULL COMMENT '签名ID',
  `sign_id` bigint(20) NOT NULL COMMENT '服务器签名返回ID',
  `sign_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '签名名称',
  `sign_type` int(11) NOT NULL COMMENT ' 签名类型 0：公司（0，1，2，3）。 1：APP',
  `document_type` int(11) NOT NULL COMMENT '证明类型  0：三证合一。 1：企业营业执照。 2：组织机构代码证书。 3：社会信用代码证书。 4：应用后台管理截图（个人开发APP',
  `international` int(11) NOT NULL COMMENT '是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信',
  `used_method` int(11) NOT NULL COMMENT '签名用途： 0：自用。 1：他用。',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '秦川修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '秦川修改时间',
  `sign_status` smallint(6) NULL DEFAULT 1 COMMENT '签名审核状态（0-通过，1-待审核，-1-未通过）',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识',
  `review_reply` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信签名' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_task
-- ----------------------------
DROP TABLE IF EXISTS `sms_task`;
CREATE TABLE `sms_task`  (
  `id` bigint(20) NOT NULL COMMENT '短信记录ID',
  `template_id` bigint(20) NOT NULL COMMENT '模板ID\n#sms_template',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'WAITING' COMMENT '执行状态\n(手机号具体发送状态看sms_send_status表) \n#TaskStatus{WAITING:等待执行;SUCCESS:执行成功;FAIL:执行失败}',
  `source_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'APP' COMMENT '来源类型\n#SourceType{APP:应用;SERVICE:服务}\n',
  `receiver` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接收者手机号\n群发用英文逗号分割.\n支持2种格式:\n1: 手机号,手机号 \n2: 姓名<手机号>,姓名<手机号>',
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主题',
  `template_params` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数 \n需要封装为{‘key’:’value’, ...}格式\n且key必须有序\n\n',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '发送内容\n需要封装正确格式化: 您好，张三，您有一个新的快递。',
  `draft` bit(1) NULL DEFAULT b'0' COMMENT '是否草稿',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '秦川修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '秦川修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '发送任务\n所有的短息发送调用，都视为是一次短信任务，任务表只保存数据和执行状态等信息，\n具体的发送状态查看发送状态（#sms_send_status）表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_template
-- ----------------------------
DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template`  (
  `id` bigint(20) NOT NULL COMMENT '模板ID',
  `template_type_id` bigint(20) NOT NULL COMMENT '模板类型ID',
  `template_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板类型名称',
  `provider_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商类型\n#ProviderType{ALI:OK,阿里云短信;TENCENT:0,腾讯云短信;BAIDU:1000,百度云短信}',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用ID',
  `app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用密码',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'SMS服务域名\n百度、其他厂商会用',
  `custom_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '模板编码\n用于api发送',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模板名称',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '模板内容',
  `sms_type` smallint(6) NOT NULL DEFAULT 0 COMMENT '短信类型，0表示普通短信, 1表示营销短信。',
  `internat_type` smallint(6) NOT NULL DEFAULT 0 COMMENT '是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信。',
  `template_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '模板参数',
  `template_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '模板CODE',
  `sign_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '签名',
  `template_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '秦川修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '秦川修改时间',
  `template_status` smallint(6) NULL DEFAULT 1 COMMENT '模板状态（0-启用，1-禁用）',
  `examine_status` smallint(6) NULL DEFAULT 1 COMMENT '审核状态（0-通过，1-未审核，-1未通过）',
  `review_reply` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核未通过原因',
  `delete_status` smallint(6) NULL DEFAULT 0 COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_CODE`(`custom_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sts_account_now
-- ----------------------------
DROP TABLE IF EXISTS `sts_account_now`;
CREATE TABLE `sts_account_now`  (
  `id` bigint(255) NOT NULL,
  `t_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '租户CODE',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织ID',
  `business_hall_id` bigint(20) NULL DEFAULT NULL COMMENT '营业厅ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '开户员的ID',
  `type` smallint(6) NOT NULL COMMENT '1.开户 2.过户 3.销户 4.拆表 ',
  `amount` int(255) NOT NULL DEFAULT 1 COMMENT '数量',
  `sts_day` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '统计的是哪一天的数据',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_user` bigint(32) NOT NULL COMMENT '创建人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` bigint(32) NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sts_customer_now
-- ----------------------------
DROP TABLE IF EXISTS `sts_customer_now`;
CREATE TABLE `sts_customer_now`  (
  `id` bigint(20) NOT NULL,
  `t_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户CODE',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `business_hall_id` bigint(20) NULL DEFAULT NULL COMMENT '营业厅ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建档案的操作员的ID',
  `customer_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户类型ID',
  `type` tinyint(255) NOT NULL COMMENT '0预建档 1有效 2无效 3添加黑名单 4移除黑名单',
  `amount` int(255) NOT NULL DEFAULT 1 COMMENT '数量',
  `sts_day` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '统计的是哪一天的数据',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户的客户档案的统计' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sts_fei_day
-- ----------------------------
DROP TABLE IF EXISTS `sts_fei_day`;
CREATE TABLE `sts_fei_day`  (
  `id` bigint(11) NOT NULL,
  `t_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户CODE',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织ID',
  `business_hall_id` bigint(20) NULL DEFAULT NULL COMMENT '营业厅ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '收费员ID',
  `total_number` int(18) NOT NULL DEFAULT 1 COMMENT '总的数量',
  `type` tinyint(10) NOT NULL COMMENT '1收费 2退费',
  `online` tinyint(10) NOT NULL COMMENT '1线上 2线下',
  `charge_item_source_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收费类型(充值赠送注意)',
  `charge_method_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付宝 现金 微信',
  `charge_item_money` decimal(18, 4) NOT NULL COMMENT '收费项金额',
  `amount` int(255) NOT NULL DEFAULT 1 COMMENT '数目',
  `sts_day` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '统计的是哪一天的数据',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '整个系统的费用的统计历史数据按照天来统计，实时统计。\r\n数据统计的维度到项目和收费员这个最小的维度，每个项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sts_gas_fei_now
-- ----------------------------
DROP TABLE IF EXISTS `sts_gas_fei_now`;
CREATE TABLE `sts_gas_fei_now`  (
  `id` bigint(11) NOT NULL,
  `t_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户CODE',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织ID',
  `business_hall_id` bigint(20) NULL DEFAULT NULL COMMENT '营业厅ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '操作员的ID',
  `customer_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户类型ID',
  `order_source_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单来源名称',
  `total_number` int(18) NOT NULL DEFAULT 1 COMMENT '总的数量',
  `gas_amount` decimal(18, 4) NOT NULL COMMENT '气量',
  `fei_amount` decimal(18, 4) NOT NULL COMMENT '金额',
  `sts_day` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '统计的是哪一天的数据',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '燃气费-气表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sts_insure_now
-- ----------------------------
DROP TABLE IF EXISTS `sts_insure_now`;
CREATE TABLE `sts_insure_now`  (
  `id` bigint(11) NOT NULL,
  `t_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户CODE',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `business_hall_id` bigint(20) NULL DEFAULT NULL COMMENT '营业厅ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '处理的管理员的ID',
  `type` tinyint(255) NOT NULL COMMENT '1新增 2续保 3作废',
  `amount` int(255) NOT NULL DEFAULT 1 COMMENT '数量',
  `sts_day` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '统计的是哪一天的数据',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '保险的统计信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sts_invoice_day
-- ----------------------------
DROP TABLE IF EXISTS `sts_invoice_day`;
CREATE TABLE `sts_invoice_day`  (
  `id` bigint(255) NOT NULL DEFAULT 1,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织ID',
  `business_hall_id` bigint(20) NULL DEFAULT NULL COMMENT '营业厅ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '收费员ID',
  `amount` int(255) NOT NULL DEFAULT 1 COMMENT '发票的数量',
  `total_amount` decimal(18, 4) NOT NULL COMMENT '合计金额',
  `total_tax` decimal(18, 4) NOT NULL COMMENT '合计税额',
  `sts_day` datetime(0) NOT NULL COMMENT '收费的时间是哪一天',
  `invoice_kind` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发票种类\r\n            0红票\r\n            1蓝票\r\n            2废票',
  `invoice_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发票类型\r\n            007普票\r\n            004专票\r\n            026电票',
  `create_user` bigint(32) NOT NULL COMMENT '创建人id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` bigint(32) NOT NULL COMMENT '修改人id',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sts_meter_now
-- ----------------------------
DROP TABLE IF EXISTS `sts_meter_now`;
CREATE TABLE `sts_meter_now`  (
  `id` bigint(11) NOT NULL,
  `t_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户CODE',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织ID',
  `business_hall_id` bigint(20) NULL DEFAULT NULL COMMENT '营业厅ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '操作员的ID',
  `gas_meter_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表类型ID',
  `gas_meter_factory_id` bigint(32) NOT NULL COMMENT '厂家ID',
  `amount` int(255) NOT NULL DEFAULT 1 COMMENT '数量',
  `sts_day` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '统计的时间',
  `create_user` bigint(32) NOT NULL COMMENT '创建人id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` bigint(32) NOT NULL COMMENT '修改人id',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '表具的厂家，类型的两个维度' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sts_meter_plan_now
-- ----------------------------
DROP TABLE IF EXISTS `sts_meter_plan_now`;
CREATE TABLE `sts_meter_plan_now`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `plan_id` bigint(32) NULL DEFAULT NULL COMMENT '抄表计划的ID',
  `t_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户CODE',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司CODE',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `business_hall_id` bigint(20) NULL DEFAULT NULL COMMENT '营业厅ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建计划的用户的id',
  `read_meter_year` smallint(6) NOT NULL COMMENT '抄表年份',
  `read_meter_month` smallint(6) NOT NULL COMMENT '抄表月份',
  `total_read_meter_count` int(11) NOT NULL DEFAULT 0 COMMENT '总户数',
  `read_meter_count` int(11) NOT NULL DEFAULT 0 COMMENT '已抄数',
  `review_count` int(11) NOT NULL DEFAULT 0 COMMENT '已审数',
  `settlement_count` int(11) NOT NULL DEFAULT 0 COMMENT '结算数',
  `fee_count` decimal(18, 4) NOT NULL COMMENT '实际收费金额',
  `fee_total` decimal(18, 4) NOT NULL COMMENT '需要收费金额',
  `data_status` smallint(6) NULL DEFAULT -1 COMMENT '状态（-1：未开启；1：执行中；2-暂停；0-执行完成）',
  `create_user` bigint(32) NOT NULL COMMENT '创建人id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` bigint(32) NOT NULL COMMENT '修改人id',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_plan_id`(`plan_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对每一个抄表计划统计' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sts_meter_upload_sts_now
-- ----------------------------
DROP TABLE IF EXISTS `sts_meter_upload_sts_now`;
CREATE TABLE `sts_meter_upload_sts_now`  (
     `id` bigint(11) NOT NULL,
     `t_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '租户CODE',
     `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司CODE',
     `org_id`  bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
     `business_hall_id` bigint(20) NULL DEFAULT NULL COMMENT '营业厅ID',
     `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户的ID',
     `device_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备id',
     `type` tinyint(255) NULL DEFAULT NULL COMMENT '1在线 2报警',
     `alarm_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '报警类型',
     `upload_times` int(255) NOT NULL DEFAULT 1 COMMENT '次数',
     `sts_day` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '统计的时间',
     `create_user` bigint(32) NOT NULL COMMENT '创建人id',
     `create_time` datetime(0) NOT NULL COMMENT '创建时间',
     `update_user` bigint(32) NOT NULL COMMENT '修改人id',
     `update_time` datetime(0) NOT NULL COMMENT '修改时间',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '在线 报警的表具数量' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_business_hall_quota_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_business_hall_quota_record`;
CREATE TABLE `tb_business_hall_quota_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司ID',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '0：不限制\r\n            1：限制',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `quota_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '\n#QuotaType{CUMULATIVE:累加;COVER:覆盖;}',
  `quota_time` datetime(0) NULL DEFAULT NULL COMMENT '配额时间',
  `money` decimal(18, 4) NULL DEFAULT NULL COMMENT '金额',
  `balance` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '当前余额',
  `total_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '配后金额',
  `single_limit` decimal(18, 4) NULL DEFAULT NULL COMMENT '单笔限额',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'increment id',
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(0) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(0) NOT NULL COMMENT 'modify datetime',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 166385 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wx_applyment_info
-- ----------------------------
DROP TABLE IF EXISTS `wx_applyment_info`;
CREATE TABLE `wx_applyment_info`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `business_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务申请编号(系统生成)',
  `contact_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '超级管理员姓名',
  `contact_id_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 超级管理员身份证件号码',
  `openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 超级管理员微信openid',
  `mobile_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 联系手机',
  `contact_email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 联系邮箱',
  `subject_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'SUBJECT_TYPE_ENTERPRISE' COMMENT ' SUBJECT_TYPE_ENTERPRISE（企业）',
  `license_copy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 营业执照照片MediaID',
  `license_copy_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 营业执照照片原始文件id',
  `license_copy_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 营业执照照片原始文件url',
  `license_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 注册号/统一社会信用代码',
  `merchant_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 商户名称',
  `legal_person` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 个体户经营者/法人姓名',
  `id_doc_type` varchar(42) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'IDENTIFICATION_TYPE_IDCARD' COMMENT ' 证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)',
  `id_card_copy` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 身份证人像面照片MediaID',
  `id_card_copy_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 身份证人像面照片原始文件id',
  `id_card_copy_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 身份证人像面照片url',
  `id_card_national` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 身份证国徽面照片MediaID',
  `id_card_national_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 身份证国徽面照片原始文件id',
  `id_card_national_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 身份证国徽面照片原始文件url',
  `id_card_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 身份证姓名',
  `id_card_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 身份证号码',
  `card_period_begin` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 身份证有效期开始时间',
  `card_period_end` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 身份证有效期结束时间',
  `owner` tinyint(1) NOT NULL COMMENT ' 经营者/法人是否为受益人（1-true,0-false）',
  `ubo_id_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'IDENTIFICATION_TYPE_IDCARD' COMMENT ' 受益人证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)',
  `ubo_id_card_copy` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 受益人身份证人像面照片MediaID',
  `ubo_id_card_copy_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 受益人身份证人像面照片原始文件id',
  `ubo_id_card_copy_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 受益人身份证人像面照片原始文件url',
  `ubo_id_card_national` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 受益人身份证国徽面照片MediaID',
  `ubo_id_card_national_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 受益人身份证国徽面照片原始文件id',
  `ubo_id_card_national_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 受益人身份证国徽面照片原始文件url',
  `ubo_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 受益人姓名',
  `ubo_id_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 受益人证件号码',
  `ubo_id_period_begin` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 受益人证件有效期开始时间',
  `ubo_id_period_end` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 证件有效期结束时间',
  `merchant_shortname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 商户简称',
  `service_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 客服电话',
  `sales_scenes_type` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 经营场景类型',
  `biz_store_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 门店名称',
  `biz_address_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 门店省市编码	',
  `biz_store_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 门店地址',
  `store_entrance_pic` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 门店门头照片MediaID',
  `store_entrance_pic_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 门店门头照片原始文件id',
  `store_entrance_pic_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 门店门头照片原始文件url',
  `indoor_pic` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 店内环境照片MediaID',
  `indoor_pic_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 店内环境照片原始文件id',
  `indoor_pic_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 店内环境照片原始文件url',
  `mini_program_appid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 服务商小程序APPID',
  `mini_program_sub_appid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 商家小程序APPID',
  `app_appid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 服务商应用APPID',
  `app_sub_appid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 商家应用APPID',
  `domain` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 互联网网站域名',
  `settlement_id` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '730' COMMENT ' 入驻结算规则ID',
  `qualification_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '公共事业（水电煤气）' COMMENT ' 所属行业',
  `qualifications` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 特殊资质图片MediaID',
  `qualifications_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 特殊资质图片原始文件id',
  `qualifications_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 特殊资质图片原始文件url',
  `bank_account_type` varchar(27) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'BANK_ACCOUNT_TYPE_CORPORATE' COMMENT ' 账户类型（BANK_ACCOUNT_TYPE_CORPORATE：对公银行账户）',
  `account_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 开户名称',
  `account_bank` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 开户银行',
  `bank_address_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 开户银行省市编码',
  `bank_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 开户银行全称（含支行]',
  `account_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 银行账号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(32) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` bigint(32) NULL DEFAULT NULL,
  `examine_status` smallint(1) NULL DEFAULT 0 COMMENT '状态(-1:审核驳回，0-待审核，1-审核通过)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '特约商户进件申请信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_other_fee_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_other_fee_record`;
CREATE TABLE `gt_other_fee_record`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表名称',
  `charge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缴费编号',
  `toll_item_id` bigint(32) NULL DEFAULT NULL COMMENT '收费项ID',
  `item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项名称',
  `charge_frequency` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费频次\r\n            ON_DEMAND:按需\r\n            ONE_TIME：一次性\r\n            BY_MONTH：按月\r\n            QUARTERLY：按季\r\n            BY_YEAR：按年',
  `charge_period` smallint(6) NULL DEFAULT NULL COMMENT '收费期限',
  `cycle_value` smallint(6) NULL DEFAULT NULL COMMENT '周期值,固定1',
  `money_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金额方式\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            默认固定金额\r\n            ',
  `total_end_time` date NULL DEFAULT NULL COMMENT '累积结束日期',
  `total_start_time` date NULL DEFAULT NULL COMMENT '累积开始日期',
  `total_cycle_count` smallint(6) NULL DEFAULT NULL COMMENT '累积周期数量',
  `total_cycle_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '累积周期金额',
  `total_charge_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '合计缴费金额',
  `charge_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费状态：\r\n            UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            CHARGE_FAILURE: 收费失败',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '状态\r\n            1 启用\r\n            0 禁用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '附加费记录' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for gt_meter_account_cancel
-- ----------------------------
DROP TABLE IF EXISTS `gt_meter_account_cancel`;
CREATE TABLE `gt_meter_account_cancel`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编号',
  `card_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '卡内金额',
  `meter_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '表内金额',
  `meter_total_gas` decimal(18, 4) NULL DEFAULT NULL COMMENT '表累计气量',
  `recharge_untometer_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '未上表金额合计(充值补气和卡上余额)',
  `is_restored_factory` tinyint(1) NULL DEFAULT NULL COMMENT '表是否已恢复出厂',
  `is_back_card` tinyint(1) NULL DEFAULT NULL COMMENT '卡是否回收',
  `is_back_meter` tinyint(1) NULL DEFAULT NULL COMMENT '表是否回收',
  `reason`  varchar(500) NULL COMMENT '销户原因',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '操作员ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '销户记录' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for gt_account_refund
-- ----------------------------
DROP TABLE IF EXISTS `gt_account_refund`;
CREATE TABLE `gt_account_refund`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `account_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户号',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `account_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '账户金额',
  `refund_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '退费金额',
  `apply_user_id` bigint(32) NULL DEFAULT NULL COMMENT '申请人ID',
  `apply_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请人名称',
  `apply_reason` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请退费原因',
  `apply_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `method_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退费方式编码',
  `method_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费方式名称 现金 转账',
  `audit_user_id` bigint(32) NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人名称',
  `audit_remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核备注',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `refund_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退费状态\r\n            APPLY:发起申请\r\n            AUDITING:审核中\r\n            UNREFUND:不予退费\r\n            REFUNDABLE: 可退费\r\n            REFUNDED: 退费完成\r\n            ',
  `result_remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退费结果描述',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '退费完成时间',
  `data_status` smallint(2) NULL DEFAULT NULL COMMENT '数据状态0 正常，1 作废',
  `delete_status` smallint(6) NULL DEFAULT NULL COMMENT '删除状态\r\n            1 删除\r\n            0 正常',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '操作员ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账户退费记录' ROW_FORMAT = Dynamic;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gt_gas_meter_temp
-- ----------------------------
DROP TABLE IF EXISTS `gt_gas_meter_temp`;
CREATE TABLE `gt_gas_meter_temp`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `gas_code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表表号',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编号',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `gas_meter_factory_id` bigint(32) NULL DEFAULT NULL COMMENT '厂家ID',
  `gas_meter_model_id` bigint(20) NULL DEFAULT NULL COMMENT '气表型号id',
  `gas_meter_version_id` bigint(32) NULL DEFAULT NULL COMMENT '版号ID',
  `install_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `install_time` datetime(0) NULL DEFAULT NULL COMMENT '安装时间',
  `direction` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通气方向',
  `province_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省code',
  `city_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市code',
  `area_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区code',
  `street_id` bigint(32) NULL DEFAULT NULL COMMENT '街道ID',
  `community_id` bigint(32) NULL DEFAULT NULL COMMENT '小区ID',
  `community_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小区名称',
  `gas_meter_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装地址',
  `more_gas_meter_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址供查询用',
  `contract_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合同编号',
  `use_gas_type_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型编码',
  `use_gas_type_id` bigint(20) NULL DEFAULT NULL,
  `use_gas_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用气类型名称',
  `population` int(11) NULL DEFAULT NULL COMMENT '使用人口',
  `node_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调压箱ID',
  `longitude` decimal(18, 8) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18, 8) NULL DEFAULT NULL COMMENT '纬度',
  `stock_id` bigint(32) NULL DEFAULT NULL COMMENT '库存ID',
  `gas_meter_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表身号',
  `gas_meter_base` decimal(18, 4) NULL DEFAULT NULL COMMENT '气表底数',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1346372154383400961 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '气表档案临时表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gt_card_back_gas_record
-- ----------------------------
DROP TABLE IF EXISTS `gt_card_back_gas_record`;
CREATE TABLE `gt_card_back_gas_record`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业厅名称',
  `card_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编码',
  `customer_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编码',
  `back_gas_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '充值退气\r\n            补气退气',
  `biz_id_or_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '退气业务ID或编号',
  `back_value` decimal(18, 4) NULL DEFAULT NULL COMMENT '退气量或金额',
  `data_status` smallint(6) NULL DEFAULT NULL COMMENT '数据状态:\r\n            1: 正常\r\n            0: 作废',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卡退气记录' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for gt_card_price_scheme
-- ----------------------------
DROP TABLE IF EXISTS `gt_card_price_scheme`;
CREATE TABLE `gt_card_price_scheme`  (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `gas_meter_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气表编码',
  `write_card_price_id` bigint(32) NULL DEFAULT NULL COMMENT '当前写入方案',
  `into_meter_price_id` bigint(32) NULL DEFAULT NULL COMMENT '当前上表方案',
  `pre_write_card_price_id` bigint(32) NULL DEFAULT NULL COMMENT '预调价写入方案',
  `pre_into_meter_price_id` bigint(32) NULL DEFAULT NULL COMMENT '预调价上表方案',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卡表价格方案记录' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for gt_customer_temp
-- ----------------------------
DROP TABLE IF EXISTS `gt_customer_temp`;
CREATE TABLE `gt_customer_temp`  (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) NULL DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `customer_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户类型ID',
  `customer_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '居民 商福 工业 低保',
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别  MAN表示男 ，WOMEN表示女',
  `id_type_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型ID',
  `id_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型名称',
  `id_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '身份证号码',
  `telphone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `contact_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭住址/单位地址',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `customer_status` smallint(6) NULL DEFAULT NULL COMMENT '客户状态 0.预建档  1. 在用   2.销户',
  `pre_store_money` decimal(18, 4) NULL DEFAULT NULL COMMENT '预存总额',
  `pre_store_count` int(11) NULL DEFAULT NULL COMMENT '预存次数',
  `balance` decimal(18, 4) NULL DEFAULT NULL COMMENT '账户余额',
  `contract_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代扣合同编号',
  `contract_status` bit(1) NULL DEFAULT NULL COMMENT '代扣签约状态',
  `bank` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户行',
  `cardholder` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '持卡人',
  `bank_account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账号',
  `insurance_no` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '保险编号',
  `insurance_end_time` datetime(0) NULL DEFAULT NULL COMMENT '保险到期时间',
  `invoice_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `TIN` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票纳税人识别',
  `invoice_bank_account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票开户行及账号',
  `invoice_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票公司地址',
  `invoice_email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票电子邮箱',
  `delete_status` int(32) NULL DEFAULT NULL COMMENT '逻辑删除',
  `black_status` int(32) NULL DEFAULT NULL COMMENT '黑名单状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  `delete_user` bigint(32) NULL DEFAULT NULL COMMENT '删除人',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_customer_code`(`customer_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

