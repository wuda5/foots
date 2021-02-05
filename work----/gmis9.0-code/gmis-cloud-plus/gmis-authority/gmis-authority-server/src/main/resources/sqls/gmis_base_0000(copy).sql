/*
Navicat MySQL Data Transfer

Source Server         : GMIS9.0
Source Server Version : 50724
Source Host           : 172.16.92.250:3306
Source Database       : gmis_base_0000

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-09-14 20:31:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `c_auth_application`
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_application`;
CREATE TABLE `c_auth_application` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `client_id` varchar(24) DEFAULT NULL COMMENT '客户端ID',
  `client_secret` varchar(32) DEFAULT NULL COMMENT '客户端密码',
  `website` varchar(100) DEFAULT '' COMMENT '官网',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '应用名称',
  `icon` varchar(255) DEFAULT '' COMMENT '应用图标',
  `app_type` varchar(10) DEFAULT NULL COMMENT '类型\n#{SERVER:服务应用;APP:手机应用;PC:PC网页应用;WAP:手机网页应用}\n',
  `describe_` varchar(200) DEFAULT '' COMMENT '备注',
  `status` bit(1) DEFAULT b'1' COMMENT '是否启用',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `UN_APP_KEY` (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用';

-- ---------------------------
-- Table structure for `c_auth_menu`
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_menu`;
CREATE TABLE `c_auth_menu` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `label` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `describe_` varchar(200) DEFAULT '' COMMENT '描述',
  `is_public` bit(1) DEFAULT b'0' COMMENT '公共菜单\nTrue是无需分配所有人就可以访问的',
  `path` varchar(255) DEFAULT '' COMMENT '路径',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `is_enable` bit(1) DEFAULT b'1' COMMENT '状态',
  `sort_value` int(11) DEFAULT '1' COMMENT '排序',
  `icon` varchar(255) DEFAULT '' COMMENT '菜单图标',
  `group_` varchar(20) DEFAULT '' COMMENT '分组',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父级菜单ID',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `INX_STATUS` (`is_enable`,`is_public`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单';

-- ----------------------------
-- Table structure for `c_auth_resource`
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_resource`;
CREATE TABLE `c_auth_resource` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `code` varchar(50) DEFAULT '' COMMENT '编码\n规则：\n链接：\n数据列：\n按钮：',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID\n#c_auth_menu',
  `describe_` varchar(255) DEFAULT '' COMMENT '描述',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_CODE` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源';

-- ----------------------------
-- Table structure for `c_auth_role`
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_role`;
CREATE TABLE `c_auth_role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(20) DEFAULT '' COMMENT '编码',
  `describe_` varchar(100) DEFAULT '' COMMENT '描述',
  `status` bit(1) DEFAULT b'1' COMMENT '状态',
  `readonly` bit(1) DEFAULT b'0' COMMENT '是否内置角色',
  `ds_type` varchar(20) NOT NULL DEFAULT 'SELF' COMMENT '数据权限类型\n#DataScopeType{ALL:1,全部;THIS_LEVEL:2,本级;THIS_LEVEL_CHILDREN:3,本级以及子级;CUSTOMIZE:4,自定义;SELF:5,个人;}',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_CODE` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Table structure for `c_auth_role_authority`
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_role_authority`;
CREATE TABLE `c_auth_role_authority` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `authority_id` bigint(20) NOT NULL COMMENT '资源id\n#c_auth_resource\n#c_auth_menu',
  `authority_type` varchar(10) NOT NULL DEFAULT 'MENU' COMMENT '权限类型\n#AuthorizeType{MENU:菜单;RESOURCE:资源;}',
  `role_id` bigint(20) NOT NULL COMMENT '角色id\n#c_auth_role',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人',
  PRIMARY KEY (`id`),
  KEY `IDX_KEY` (`role_id`,`authority_type`,`authority_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色的资源';

-- ----------------------------
-- Table structure for `c_auth_role_org`
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_role_org`;
CREATE TABLE `c_auth_role_org` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID\n#c_auth_role',
  `org_id` bigint(20) DEFAULT NULL COMMENT '部门ID\n#c_core_org',
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色组织关系';

-- ----------------------------
-- Table structure for `c_auth_system_api`
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_system_api`;
CREATE TABLE `c_auth_system_api` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `describe_` varchar(100) DEFAULT NULL COMMENT '描述',
  `request_method` varchar(255) DEFAULT NULL COMMENT '请求方式',
  `content_type` varchar(255) DEFAULT NULL COMMENT '响应类型',
  `service_id` varchar(50) NOT NULL COMMENT '服务ID',
  `path` varchar(255) DEFAULT NULL COMMENT '请求路径',
  `status` bit(1) DEFAULT b'1' COMMENT '状态\n:0-无效 1-有效',
  `is_persist` bit(1) DEFAULT b'0' COMMENT '保留数据 \n0-否 1-是 系统内资数据,不允许删除',
  `is_auth` bit(1) DEFAULT b'1' COMMENT '是否需要认证\n: 0-无认证 1-身份认证 默认:1',
  `is_open` bit(1) DEFAULT b'0' COMMENT '是否公开\n: 0-内部的 1-公开的',
  `class_name` varchar(255) DEFAULT NULL COMMENT '类名',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNX_ID` (`id`) USING BTREE,
  UNIQUE KEY `UNX_CODE` (`code`) USING BTREE,
  KEY `service_id` (`service_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='API接口';

-- ----------------------------
-- Table structure for `c_auth_user`
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_user`;
CREATE TABLE `c_auth_user` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `account` varchar(30) NOT NULL COMMENT '账号',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `org_name` varchar(255) DEFAULT NULL COMMENT '组织名称',
  `org_id` bigint(20) DEFAULT NULL COMMENT '组织ID\n#c_core_org\n@InjectionField(api = ORG_ID_CLASS, method = ORG_ID_METHOD, beanClass = Org.class) RemoteData<Long, Org>',
  `business_ball_id` bigint(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_ball_name` varchar(255) DEFAULT NULL COMMENT '营业厅名称',
  `station_id` bigint(20) DEFAULT NULL COMMENT '岗位ID\n#c_core_station\n@InjectionField(api = STATION_ID_CLASS, method = STATION_ID_NAME_METHOD) RemoteData<Long, String>',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机',
  `sex` varchar(1) DEFAULT 'N' COMMENT '性别\n#Sex{W:女;M:男;N:未知}',
  `status` bit(1) DEFAULT b'0' COMMENT '状态 \n1启用 0禁用',
  `avatar` varchar(255) DEFAULT '' COMMENT '头像',
  `nation` varchar(20) DEFAULT NULL COMMENT '民族\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>',
  `education` varchar(20) DEFAULT NULL COMMENT '学历\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>',
  `position_status` varchar(20) DEFAULT NULL COMMENT '职位状态\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>',
  `work_describe` varchar(255) DEFAULT '' COMMENT '工作描述\r\n比如：  市长、管理员、局长等等   用于登陆展示',
  `password_error_last_time` datetime DEFAULT NULL COMMENT '秦川一次输错密码时间',
  `password_error_num` int(11) DEFAULT '0' COMMENT '密码错误次数',
  `password_expire_time` datetime DEFAULT NULL COMMENT '密码过期时间',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `last_login_time` datetime DEFAULT NULL COMMENT '秦川登录时间',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `point_type` varchar(255) DEFAULT NULL COMMENT '配置类型',
  `point_of_sale` decimal(18,4) DEFAULT NULL COMMENT '配额',
  `balance` decimal(18,4) DEFAULT NULL COMMENT '余额',
  `single_limit` decimal(18,4) DEFAULT NULL COMMENT '单笔限额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_ACCOUNT` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- ----------------------------
-- Table structure for `c_auth_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_user_role`;
CREATE TABLE `c_auth_user_role` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色ID\n#c_auth_role',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID\n#c_core_accou',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `IDX_KEY` (`role_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色分配\r\n账号角色绑定';

-- ----------------------------
-- Table structure for `c_auth_user_token`
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_user_token`;
CREATE TABLE `c_auth_user_token` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '登录IP',
  `location` varchar(50) DEFAULT NULL COMMENT '登录地点',
  `client_id` varchar(24) DEFAULT NULL COMMENT '客户端Key',
  `token` text COMMENT 'token',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `account` varchar(30) DEFAULT NULL COMMENT '账号',
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL COMMENT '登录人ID',
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='token';

-- ----------------------------
-- Table structure for `c_common_area`
-- ----------------------------
DROP TABLE IF EXISTS `c_common_area`;
CREATE TABLE `c_common_area` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '编码',
  `label` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `full_name` varchar(255) DEFAULT '' COMMENT '全名',
  `sort_value` int(11) DEFAULT '1' COMMENT '排序',
  `longitude` varchar(255) DEFAULT '' COMMENT '经度',
  `latitude` varchar(255) DEFAULT '' COMMENT '维度',
  `level` varchar(10) DEFAULT '' COMMENT '行政区级\n@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>\n\n',
  `source_` varchar(255) DEFAULT NULL COMMENT '数据来源',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_CODE` (`code`) USING BTREE,
  KEY `IDX_PARENT_ID` (`parent_id`,`label`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地区表';

-- ----------------------------
-- Table structure for `c_common_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `c_common_dictionary`;
CREATE TABLE `c_common_dictionary` (
  `id` bigint(20) NOT NULL,
  `type_` varchar(64) NOT NULL DEFAULT '' COMMENT '编码\r\n一颗树仅仅有一个统一的编码',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
  `describe_` varchar(200) DEFAULT '' COMMENT '描述',
  `status_` bit(1) DEFAULT b'1' COMMENT '状态',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型';

-- ----------------------------
-- Table structure for `c_common_dictionary_item`
-- ----------------------------
DROP TABLE IF EXISTS `c_common_dictionary_item`;
CREATE TABLE `c_common_dictionary_item` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dictionary_id` bigint(20) NOT NULL COMMENT '类型ID',
  `dictionary_type` varchar(64) NOT NULL COMMENT '类型',
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '编码',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
  `status_` bit(1) DEFAULT b'1' COMMENT '状态',
  `describe_` varchar(255) DEFAULT '' COMMENT '描述',
  `sort_value` int(11) DEFAULT '1' COMMENT '排序',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `dict_code_item_code_uniq` (`dictionary_type`,`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典项';

-- ----------------------------
-- Table structure for `c_common_login_log`
-- ----------------------------
DROP TABLE IF EXISTS `c_common_login_log`;
CREATE TABLE `c_common_login_log` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `request_ip` varchar(50) DEFAULT '' COMMENT '登录IP',
  `user_id` bigint(20) DEFAULT NULL COMMENT '登录人ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '登录人姓名',
  `account` varchar(30) DEFAULT '' COMMENT '登录人账号',
  `description` varchar(255) DEFAULT '' COMMENT '登录描述',
  `login_date` date DEFAULT NULL COMMENT '登录时间',
  `ua` varchar(500) DEFAULT '0' COMMENT '浏览器请求头',
  `browser` varchar(255) DEFAULT NULL COMMENT '浏览器名称',
  `browser_version` varchar(255) DEFAULT NULL COMMENT '浏览器版本',
  `operating_system` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `location` varchar(50) DEFAULT '' COMMENT '登录地点',
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_BROWSER` (`browser`) USING BTREE,
  KEY `IDX_OPERATING` (`operating_system`) USING BTREE,
  KEY `IDX_LOGIN_DATE` (`login_date`,`account`) USING BTREE,
  KEY `IDX_ACCOUNT_IP` (`account`,`request_ip`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志';

-- ----------------------------
-- Table structure for `c_common_opt_log`
-- ----------------------------
DROP TABLE IF EXISTS `c_common_opt_log`;
CREATE TABLE `c_common_opt_log` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `request_ip` varchar(50) DEFAULT '' COMMENT '操作IP',
  `type` varchar(5) DEFAULT 'OPT' COMMENT '日志类型\n#LogType{OPT:操作类型;EX:异常类型}',
  `user_name` varchar(50) DEFAULT '' COMMENT '操作人',
  `description` varchar(255) DEFAULT '' COMMENT '操作描述',
  `class_path` varchar(255) DEFAULT '' COMMENT '类路径',
  `action_method` varchar(50) DEFAULT '' COMMENT '请求方法',
  `request_uri` varchar(50) DEFAULT '' COMMENT '请求地址',
  `http_method` varchar(10) DEFAULT 'GET' COMMENT '请求类型\n#HttpMethod{GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}',
  `params` longtext COMMENT '请求参数',
  `result` longtext COMMENT '返回值',
  `ex_desc` longtext COMMENT '异常详情信息',
  `ex_detail` longtext COMMENT '异常描述',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `finish_time` timestamp NULL DEFAULT NULL COMMENT '完成时间',
  `consuming_time` bigint(20) DEFAULT '0' COMMENT '消耗时间',
  `ua` varchar(500) DEFAULT '' COMMENT '浏览器',
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

-- ----------------------------
-- Table structure for `c_common_parameter`
-- ----------------------------
DROP TABLE IF EXISTS `c_common_parameter`;
CREATE TABLE `c_common_parameter` (
  `id` bigint(20) NOT NULL,
  `key_` varchar(255) NOT NULL DEFAULT '' COMMENT '参数键',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '参数名称',
  `value` varchar(255) NOT NULL COMMENT '参数值',
  `describe_` varchar(255) DEFAULT '' COMMENT '描述',
  `status_` bit(1) DEFAULT b'1' COMMENT '状态',
  `readonly_` bit(1) DEFAULT NULL COMMENT '只读',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_KEY` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参数配置';

-- ----------------------------
-- Table structure for `c_core_org`
-- ----------------------------
DROP TABLE IF EXISTS `c_core_org`;
CREATE TABLE `c_core_org` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `label` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `abbreviation` varchar(255) DEFAULT '' COMMENT '简称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父ID',
  `tree_path` varchar(255) DEFAULT ',' COMMENT '树结构',
  `sort_value` int(11) DEFAULT '1' COMMENT '排序',
  `status` bit(1) DEFAULT b'1' COMMENT '状态',
  `describe_` varchar(255) DEFAULT '' COMMENT '描述',
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_user` bigint(20) DEFAULT NULL COMMENT '删除人ID',
  `is_business_hall` smallint(6) DEFAULT '0' COMMENT '是否营业厅',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `FU_PATH` (`tree_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织';

-- ----------------------------
-- Table structure for `c_core_station`
-- ----------------------------
DROP TABLE IF EXISTS `c_core_station`;
CREATE TABLE `c_core_station` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `org_id` bigint(20) DEFAULT '0' COMMENT '组织ID\n#c_core_org',
  `status` bit(1) DEFAULT b'1' COMMENT '状态',
  `describe_` varchar(255) DEFAULT '' COMMENT '描述',
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位';

-- ----------------------------
-- Table structure for `cb_bank_withhold_record`
-- ----------------------------
DROP TABLE IF EXISTS `cb_bank_withhold_record`;
CREATE TABLE `cb_bank_withhold_record` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` char(32) DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `gas_meter_type_id` char(32) DEFAULT NULL COMMENT '气表类型id',
  `gas_meter_type_name` varchar(100) DEFAULT NULL COMMENT '气表类型名称',
  `gas_meter_address` varchar(200) DEFAULT NULL COMMENT '气表安装地址',
  `cardholder` varchar(30) DEFAULT NULL COMMENT '持卡人',
  `bank_account` varchar(100) DEFAULT NULL COMMENT '银行账号',
  `amount` decimal(18,4) DEFAULT NULL COMMENT '金额',
  `import_status` smallint(6) DEFAULT NULL COMMENT '导入状态',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='银行代扣记录';

-- ----------------------------
-- Table structure for `cb_compensation_for_read_meter`
-- ----------------------------
DROP TABLE IF EXISTS `cb_compensation_for_read_meter`;
CREATE TABLE `cb_compensation_for_read_meter` (
  `ID` bigint(32) NOT NULL COMMENT 'id',
  `read_meter_data_id` bigint(32) DEFAULT NULL COMMENT '抄表id',
  `compensation_price` decimal(18,4) DEFAULT NULL COMMENT '补差价',
  `compensation_gas` decimal(18,4) DEFAULT NULL COMMENT '补差量',
  `compensation_money` decimal(18,4) DEFAULT NULL COMMENT '补差金额',
  `accounting_user_id` bigint(32) DEFAULT NULL COMMENT '核算人id',
  `accounting_user_name` varchar(100) DEFAULT NULL COMMENT '核算人名称',
  `review_user_id` bigint(32) DEFAULT NULL COMMENT '审批人id',
  `review_user_name` varchar(100) DEFAULT NULL COMMENT '审批人名称',
  `review_time` datetime DEFAULT NULL COMMENT '审批时间',
  `review_objection` varchar(100) DEFAULT NULL COMMENT '审批意见',
  `accounting_time` datetime DEFAULT NULL COMMENT '核算时间',
  `charge_status` smallint(6) DEFAULT NULL COMMENT '收费状态',
  `charge_time` datetime DEFAULT NULL COMMENT '收费时间',
  `charge_order_id` bigint(32) DEFAULT NULL COMMENT '收费订单id',
  `data_status` smallint(6) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表调价补差信息';

-- ----------------------------
-- Table structure for `cb_gas_meter_book_record`
-- ----------------------------
DROP TABLE IF EXISTS `cb_gas_meter_book_record`;
CREATE TABLE `cb_gas_meter_book_record` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `read_meter_book` char(32) DEFAULT NULL COMMENT '抄表册id',
  `street_id` bigint(32) DEFAULT NULL COMMENT '街道id',
  `street_name` varchar(100) DEFAULT NULL COMMENT '街道名称',
  `community_id` bigint(32) DEFAULT NULL COMMENT '所属区域id',
  `community_name` varchar(100) DEFAULT NULL COMMENT '所属区域名称',
  `customer_id` bigint(32) DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(30) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(60) DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` varchar(60) DEFAULT NULL COMMENT '气表编号',
  `gas_meter_type` varchar(60) DEFAULT NULL COMMENT '气表类型',
  `use_gas_type_id` varchar(30) DEFAULT NULL COMMENT '用气类型id',
  `use_gas_type_name` varchar(60) DEFAULT NULL COMMENT '用气类型名称',
  `gas_meter_address` varchar(200) DEFAULT NULL COMMENT '安装地址',
  `record_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `sort` int(11) DEFAULT NULL COMMENT '自定义序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表册关联客户';

-- ----------------------------
-- Table structure for `cb_read_meter_book`
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_book`;
CREATE TABLE `cb_read_meter_book` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `community_id` bigint(32) DEFAULT NULL COMMENT '所属区域id',
  `community_name` varchar(100) DEFAULT NULL COMMENT '所属区域名称',
  `book_code` varchar(10) DEFAULT NULL COMMENT '抄表册编号',
  `book_name` varchar(30) DEFAULT NULL COMMENT '抄表册名称',
  `read_meter_user` char(32) DEFAULT NULL COMMENT '抄表员id',
  `read_meter_user_name` varchar(100) DEFAULT NULL COMMENT '抄表员名称',
  `total_read_meter_count` decimal(18,0) DEFAULT NULL COMMENT '总户数',
  `book_status` smallint(6) DEFAULT '-1' COMMENT '状态(-1：空闲;1：未抄表；0：抄表完成；2：抄表中，)',
  `cited_count` smallint(6) DEFAULT '0' COMMENT '被抄表计划引用次数',
  `remark` varchar(100) DEFAULT NULL COMMENT '说明',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(6) DEFAULT '0' COMMENT '删除标识（0：存在；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表册';

-- ----------------------------
-- Table structure for `cb_read_meter_data`
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_data`;
CREATE TABLE `cb_read_meter_data` (
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
  `plan_id` bigint(32) DEFAULT NULL COMMENT '计划id',
  `book_id` bigint(32) DEFAULT NULL COMMENT '抄表册id',
  `read_meter_year` smallint(6) DEFAULT NULL COMMENT '抄表年份',
  `read_time` date DEFAULT NULL,
  `read_meter_month` smallint(6) DEFAULT NULL COMMENT '抄表月份',
  `last_total_gas` decimal(18,4) DEFAULT NULL COMMENT '上期止数',
  `current_total_gas` decimal(18,4) DEFAULT NULL COMMENT '本期止数',
  `month_use_gas` decimal(18,4) DEFAULT NULL COMMENT '本期用量',
  `gas_meter_number` char(30) DEFAULT NULL COMMENT '表身号',
  `cycle_total_use_gas` decimal(18,4) DEFAULT NULL COMMENT '周期累计用气量',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型id',
  `use_gas_type_name` varchar(50) DEFAULT NULL COMMENT '用气类型名称',
  `price_scheme_id` int(11) DEFAULT NULL COMMENT '价格号',
  `process_status` varchar(30) DEFAULT NULL COMMENT '流程状态',
  `charge_status` varchar(30) DEFAULT NULL COMMENT '收费状态',
  `data_status` smallint(6) DEFAULT '-1' COMMENT '数据状态（-1：未抄表；2-取消；0-已抄）',
  `read_meter_user_id` bigint(32) DEFAULT NULL COMMENT '抄表员id',
  `read_emter_user_name` varchar(100) DEFAULT NULL COMMENT '抄表员名称',
  `record_user_id` bigint(32) DEFAULT NULL COMMENT '记录员id',
  `record_user_name` varchar(100) DEFAULT NULL COMMENT '记录员名称',
  `record_time` datetime DEFAULT NULL COMMENT '记录时间',
  `review_user_id` bigint(32) DEFAULT NULL COMMENT '审核人id',
  `review_user_name` varchar(100) DEFAULT NULL COMMENT '审核人名称',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `review_objection` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `settlement_user_id` bigint(32) DEFAULT NULL COMMENT '结算人id',
  `settlement_user_name` varchar(100) DEFAULT NULL COMMENT '结算人名称',
  `settlement_time` datetime DEFAULT NULL COMMENT '结算时间',
  `gas_owe_code` char(32) DEFAULT NULL COMMENT '燃气欠费编号',
  `free_amount` decimal(18,4) DEFAULT NULL COMMENT '收费金额',
  `unit_price` decimal(18,4) DEFAULT NULL COMMENT '单价',
  `penalty` decimal(18,4) DEFAULT NULL COMMENT '滞纳金',
  `days_overdue` int(11) DEFAULT NULL COMMENT '超期天数',
  `gas_1` decimal(18,4) DEFAULT NULL COMMENT '1阶气量',
  `price_1` decimal(18,4) DEFAULT NULL COMMENT '1阶价格',
  `gas_2` decimal(18,4) DEFAULT NULL COMMENT '2阶气量',
  `price_2` decimal(18,4) DEFAULT NULL COMMENT '2阶价格',
  `gas_3` decimal(18,4) DEFAULT NULL COMMENT '3阶气量',
  `price_3` decimal(18,4) DEFAULT NULL COMMENT '3阶价格',
  `gas_4` decimal(18,4) DEFAULT NULL COMMENT '4阶气量',
  `price_4` decimal(18,4) DEFAULT NULL COMMENT '4阶价格',
  `gas_5` decimal(18,4) DEFAULT NULL COMMENT '5阶气量',
  `price_5` decimal(18,4) DEFAULT NULL COMMENT '5阶价格',
  `gas_6` decimal(18,4) DEFAULT NULL COMMENT '6阶气量',
  `price_6` decimal(18,4) DEFAULT NULL COMMENT '6阶价格',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `data_bias` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表数据';

-- ----------------------------
-- Table structure for `cb_read_meter_month_gas`
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_month_gas`;
CREATE TABLE `cb_read_meter_month_gas` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `customer_id` char(32) DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(30) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` char(32) DEFAULT NULL COMMENT '气表编号',
  `gas_meter_number` char(30) DEFAULT NULL COMMENT '表身号',
  `gas_meter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `year` smallint(6) DEFAULT NULL COMMENT '年份',
  `jan_total_gas` decimal(18,4) DEFAULT NULL COMMENT '一月止数',
  `feb_total_gas` decimal(18,4) DEFAULT NULL COMMENT '二月止数',
  `mar_total_gas` decimal(18,4) DEFAULT NULL COMMENT '三月止数',
  `apr_total_gas` decimal(18,4) DEFAULT NULL COMMENT '四月止数',
  `may_total_gas` decimal(18,4) DEFAULT NULL COMMENT '五月止数',
  `jun_total_gas` decimal(18,4) DEFAULT NULL COMMENT '六月止数',
  `jul_total_gas` decimal(18,4) DEFAULT NULL COMMENT '七月止数',
  `aug_total_gas` decimal(18,4) DEFAULT NULL COMMENT '八月止数',
  `sept_total_gas` decimal(18,4) DEFAULT NULL COMMENT '九月止数',
  `oct_total_gas` decimal(18,4) DEFAULT NULL COMMENT '十月止数',
  `nov_total_gas` decimal(18,4) DEFAULT NULL COMMENT '十一月止数',
  `dec_total_gas` decimal(18,4) DEFAULT NULL COMMENT '十二月止数',
  `jan_average` decimal(18,4) DEFAULT NULL COMMENT '一月平均值',
  `feb_average` decimal(18,4) DEFAULT NULL COMMENT '二月平均值',
  `mar_average` decimal(18,4) DEFAULT NULL COMMENT '三月平均值',
  `apr_average` decimal(18,4) DEFAULT NULL COMMENT '四月平均值',
  `may_average` decimal(18,4) DEFAULT NULL COMMENT '五月平均值',
  `jun_average` decimal(18,4) DEFAULT NULL COMMENT '六月平均值',
  `jul_average` decimal(18,4) DEFAULT NULL COMMENT '七月平均值',
  `aug_average` decimal(18,4) DEFAULT NULL COMMENT '八月平均值',
  `sept_average` decimal(18,4) DEFAULT NULL COMMENT '九月平均值',
  `oct_average` decimal(18,4) DEFAULT NULL COMMENT '十月平均值',
  `nov_average` decimal(18,4) DEFAULT NULL COMMENT '十一月平均值',
  `dec_average` decimal(18,4) DEFAULT NULL COMMENT '十二月平均值',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表每月用气止数记录表';

-- ----------------------------
-- Table structure for `cb_read_meter_plan`
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_plan`;
CREATE TABLE `cb_read_meter_plan` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `read_meter_year` smallint(6) DEFAULT NULL COMMENT '抄表年份',
  `read_meter_month` smallint(6) DEFAULT NULL COMMENT '抄表月份',
  `plan_start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
  `plan_end_time` datetime DEFAULT NULL COMMENT '计划结束时间',
  `total_read_meter_count` int(11) DEFAULT NULL COMMENT '总户数',
  `read_meter_count` int(11) DEFAULT NULL COMMENT '已抄数',
  `review_count` int(11) DEFAULT NULL COMMENT '已审数',
  `settlement_count` int(11) DEFAULT NULL COMMENT '结算数',
  `data_status` smallint(6) DEFAULT '-1' COMMENT '状态（-1：未开启；1：执行中；2-暂停；0-执行完成）',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表计划';

-- ----------------------------
-- Table structure for `cb_read_meter_plan_scope`
-- ----------------------------
DROP TABLE IF EXISTS `cb_read_meter_plan_scope`;
CREATE TABLE `cb_read_meter_plan_scope` (
  `ID` bigint(32) NOT NULL COMMENT 'id',
  `readmeter_plan_id` bigint(32) DEFAULT NULL COMMENT '抄表计划id',
  `book_id` bigint(32) DEFAULT NULL COMMENT '抄表册id',
  `book_name` varchar(30) DEFAULT NULL COMMENT '抄表册名称',
  `total_read_meter_count` int(11) DEFAULT NULL COMMENT '总户数',
  `read_meter_count` int(11) DEFAULT NULL COMMENT '已抄数',
  `review_count` int(11) DEFAULT NULL COMMENT '已审数',
  `settlement_count` int(11) DEFAULT NULL COMMENT '结算数',
  `data_status` smallint(6) DEFAULT '-1' COMMENT '状态（-1：未抄表；1：抄表中；2-暂停；0-抄表完成）',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间0',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表任务分配';

-- ----------------------------
-- Table structure for `da_batch_gas`
-- ----------------------------
DROP TABLE IF EXISTS `da_batch_gas`;
CREATE TABLE `da_batch_gas` (
  `id` bigint(32) NOT NULL,
  `street_id` char(32) DEFAULT NULL COMMENT '街道id',
  `community_id` varchar(50) DEFAULT NULL COMMENT '小区id',
  `install_code` varchar(32) DEFAULT NULL COMMENT '报装编号',
  `gas_factory_id` char(1) DEFAULT NULL COMMENT '气表厂家id',
  `version_id` varchar(10) DEFAULT NULL COMMENT '版号id',
  `buildings` int(32) DEFAULT NULL COMMENT '栋',
  `unit` int(32) DEFAULT NULL COMMENT '单元',
  `storey` int(30) DEFAULT NULL COMMENT '楼层',
  `households` int(30) DEFAULT NULL COMMENT '户数',
  `address` varchar(255) DEFAULT NULL COMMENT '安装地址',
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(10) DEFAULT NULL,
  `create_user` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `da_card`
-- ----------------------------
DROP TABLE IF EXISTS `da_card`;
CREATE TABLE `da_card` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `gas_code` varchar(30) DEFAULT NULL COMMENT '气表编号',
  `card_type` varchar(30) DEFAULT NULL COMMENT '卡类型',
  `card_code` varchar(100) DEFAULT NULL COMMENT '卡编号',
  `card_name` varchar(60) DEFAULT NULL COMMENT '卡名称',
  `card_count` int(11) DEFAULT NULL COMMENT '卡上次数',
  `card_volume` decimal(18,0) DEFAULT NULL COMMENT '卡上气量',
  `card_money` decimal(18,6) DEFAULT NULL COMMENT '卡上金额',
  `alarm_volume` decimal(18,0) DEFAULT NULL COMMENT '报警气量',
  `remark` varchar(20) DEFAULT NULL COMMENT '备注',
  `data_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户',
  `delete_status` int(32) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `da_customer`
-- ----------------------------
DROP TABLE IF EXISTS `da_customer`;
CREATE TABLE `da_customer` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `install_id` char(32) DEFAULT NULL COMMENT '报装ID',
  `install_number` varchar(30) DEFAULT NULL COMMENT '报装编号',
  `customer_code` varchar(30) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `customer_type_code` varchar(32) DEFAULT NULL COMMENT '客户类型ID',
  `customer_type_name` varchar(100) DEFAULT NULL COMMENT '居民 商福 工业 低保',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别  MAN表示男 ，WOMEN表示女',
  `id_type_code` varchar(10) DEFAULT NULL COMMENT '证件类型ID',
  `id_type_name` varchar(100) DEFAULT NULL COMMENT '证件类型名称',
  `id_number` varchar(30) DEFAULT NULL COMMENT '证件号码',
  `telphone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `contact_address` varchar(100) DEFAULT NULL COMMENT '家庭住址/单位地址',
  `remark` varchar(100) DEFAULT NULL,
  `customer_status` smallint(6) DEFAULT NULL COMMENT '客户状态 1.预建档  2. 有效   3.销户',
  `pre_store_money` decimal(18,4) DEFAULT NULL COMMENT '预存总额',
  `pre_store_count` int(11) DEFAULT NULL COMMENT '预存次数',
  `balance` decimal(18,4) DEFAULT NULL COMMENT '账户余额',
  `contract_number` varchar(30) DEFAULT NULL COMMENT '代扣合同编号',
  `contract_status` bit(1) DEFAULT NULL COMMENT '代扣签约状态',
  `bank` varchar(50) DEFAULT NULL COMMENT '开户行',
  `cardholder` varchar(100) DEFAULT NULL COMMENT '持卡人',
  `bank_account` varchar(30) DEFAULT NULL COMMENT '银行账号',
  `insurance_no` varchar(60) DEFAULT NULL COMMENT '保险编号',
  `insurance_end_time` datetime DEFAULT NULL COMMENT '保险到期时间',
  `invoice_title` varchar(100) DEFAULT NULL COMMENT '发票抬头',
  `TIN` varchar(30) DEFAULT NULL COMMENT '发票纳税人识别',
  `invoice_bank_account` varchar(30) DEFAULT NULL COMMENT '发票开户行及账号',
  `invoice_address` varchar(100) DEFAULT NULL COMMENT '发票公司地址',
  `invoice_email` varchar(50) DEFAULT NULL COMMENT '发票电子邮箱',
  `delete_status` int(32) DEFAULT NULL COMMENT '逻辑删除',
  `black_status` bigint(32) DEFAULT NULL COMMENT '黑名单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
  `delete_user` bigint(32) DEFAULT NULL COMMENT '删除人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `da_customer_blacklist`
-- ----------------------------
DROP TABLE IF EXISTS `da_customer_blacklist`;
CREATE TABLE `da_customer_blacklist` (
  `id` bigint(32) NOT NULL,
  `customer_code` char(32) DEFAULT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `da_customer_gas_meter_related`
-- ----------------------------
DROP TABLE IF EXISTS `da_customer_gas_meter_related`;
CREATE TABLE `da_customer_gas_meter_related` (
  `id` bigint(32) NOT NULL,
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户档案ID',
  `gas_meter_code` varchar(32) DEFAULT NULL COMMENT '气表档案ID',
  `related_level` varchar(20) DEFAULT NULL COMMENT '\n#RelateLevelType{MAIN_HOUSEHOLD:主户;QUOTE:引用;TENANT:租户;}',
  `related_deductions` smallint(6) DEFAULT NULL COMMENT '抄表抵扣或银行代扣',
  `data_status` smallint(6) DEFAULT NULL COMMENT '1.有效 0.无效 2.过户 3.销户 4.拆表 5.预开户  6.限购',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户气表关联表';

-- ----------------------------
-- Table structure for `da_customer_material`
-- ----------------------------
DROP TABLE IF EXISTS `da_customer_material`;
CREATE TABLE `da_customer_material` (
  `id` bigint(32) NOT NULL,
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `material_type` varchar(10) DEFAULT NULL COMMENT 'ID：身份证件\r\n            CONTRACT：合同\r\n            OTHER:其他\r\n            ',
  `material_url` varchar(1000) DEFAULT NULL COMMENT '材料地址',
  `material_image_url` varchar(1000) DEFAULT NULL COMMENT '缩略图地址',
  `material_file_name` varchar(200) DEFAULT NULL COMMENT '原文件名',
  `material_file_extension` varchar(10) DEFAULT NULL COMMENT '原文件扩展名',
  `data_status` int(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '跟新用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `da_gas_meter`
-- ----------------------------
DROP TABLE IF EXISTS `da_gas_meter`;
CREATE TABLE `da_gas_meter` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `gas_code` char(32) DEFAULT NULL COMMENT '气表表号',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `gas_meter_factory_id` bigint(32) DEFAULT NULL COMMENT '厂家ID',
  `gas_meter_factory_name` varchar(100) DEFAULT NULL COMMENT '厂家名称',
  `gas_meter_version_id` bigint(32) DEFAULT NULL COMMENT '版号ID',
  `gas_meter_version_name` varchar(30) DEFAULT NULL COMMENT '版号名称',
  `gas_meter_model_id` bigint(20) DEFAULT NULL COMMENT '气表型号id',
  `gas_meter_model_name` varchar(30) DEFAULT NULL COMMENT '气表型号名称',
  `gas_meter_type_id` bigint(32) DEFAULT NULL COMMENT '表类型ID',
  `gas_meter_type_name` varchar(30) DEFAULT NULL COMMENT '表类型名称 普表\r\n            卡表\r\n            物联网表\r\n            流量计(普表)\r\n            流量计(卡表）\r\n            流量计(物联网表）',
  `install_number` varchar(30) DEFAULT '报装编号',
  `direction` smallint(6) DEFAULT NULL COMMENT '通气方向',
  `province_id` bigint(32) DEFAULT NULL COMMENT '省id',
  `city_id` bigint(32) DEFAULT NULL COMMENT '市ID',
  `area_id` bigint(32) DEFAULT NULL COMMENT '区ID',
  `street_id` bigint(32) DEFAULT NULL COMMENT '街道ID',
  `community_id` bigint(32) DEFAULT NULL COMMENT '小区ID',
  `community_name` varchar(100) DEFAULT '小区名称',
  `gas_meter_address` varchar(100) DEFAULT NULL COMMENT '安装地址',
  `contract_number` varchar(30) DEFAULT NULL COMMENT '合同编号',
  `use_gas_type_code` varchar(100) DEFAULT NULL COMMENT '用气类型编码',
  `use_gas_type_id` varchar(32) DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(50) DEFAULT NULL COMMENT '用气类型名称',
  `population` int(11) DEFAULT NULL COMMENT '使用人口',
  `node_number` varchar(32) DEFAULT NULL COMMENT '调压箱ID',
  `ventilate_status` smallint(6) DEFAULT NULL COMMENT '通气状态',
  `ventilate_user_id` bigint(32) DEFAULT NULL COMMENT '通气人ID',
  `ventilate_user_name` varchar(100) DEFAULT NULL COMMENT '通气人名称',
  `ventilate_time` datetime DEFAULT NULL COMMENT '通气时间',
  `security_user_id` bigint(32) DEFAULT NULL COMMENT '安全员ID',
  `security_user_name` varchar(100) DEFAULT NULL COMMENT '安全员名称',
  `longitude` decimal(18,8) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18,8) DEFAULT NULL COMMENT '纬度',
  `stock_id` bigint(32) DEFAULT NULL COMMENT '库存ID',
  `new_source` varchar(30) DEFAULT NULL COMMENT '建档来源\n#ArchiveSourceType{BATCH_CREATE_ARCHIVE:批量建档;IMPORT_ARCHIVE:导入建档;ARTIFICIAL_CREATE_ARCHIVE:人工建档;}',
  `file_id` bigint(32) DEFAULT NULL COMMENT '文件ID',
  `gas_meter_number` varchar(30) DEFAULT NULL COMMENT '表身号',
  `settlement_type` varchar(10) DEFAULT NULL COMMENT '结算类型\n#SettlementType{GAS:气量;MONEY:金额}',
  `settlement_mode` varchar(255) DEFAULT NULL COMMENT '结算方式\n#SettlementModel{READMETER:抄表结算;METER:表端结算;SERVICE:服务端结算;}',
  `charge_type` varchar(20) DEFAULT NULL COMMENT '缴费类型\n#SettlementModel{IC_RECHARGE:IC卡充值;READMETER_PAY:抄表缴费;REMOTE_RECHARGE:远程充值;}',
  `measurement_accuracy` smallint(6) DEFAULT NULL COMMENT '计费精度',
  `bank_withholding` smallint(6) DEFAULT NULL COMMENT '银行代扣',
  `gas_meter_base` decimal(18,4) DEFAULT NULL COMMENT '气表底数',
  `pre_built_time` datetime DEFAULT NULL COMMENT '预建档时间',
  `pre_built_user_id` bigint(32) DEFAULT NULL COMMENT '预建档人ID',
  `pre_built_user_name` varchar(100) DEFAULT NULL COMMENT '预建档人名称',
  `open_account_time` datetime DEFAULT NULL COMMENT '开户时间',
  `open_account_user_name` varchar(100) DEFAULT NULL COMMENT '开户人',
  `safe_code` varchar(30) DEFAULT NULL COMMENT '防盗扣编号',
  `open_account_user_id` bigint(32) DEFAULT NULL COMMENT '开户人ID',
  `data_status` smallint(6) DEFAULT NULL COMMENT '0，预建档 1，待安装 ，2待开户，3，待通气，4，已通气，5， 拆除',
  `check_user` varchar(32) DEFAULT NULL COMMENT '检定人',
  `check_time` date DEFAULT NULL COMMENT '检定时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `start_ID` smallint(10) DEFAULT NULL COMMENT '是否启动ID卡，1为不启动，0表示启动',
  `buy_time` date DEFAULT NULL COMMENT '购买时间',
  `delete_status` int(10) DEFAULT NULL COMMENT '删除状态',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1303873267370557441 DEFAULT CHARSET=utf8mb4 COMMENT='气表档案';

-- ----------------------------
-- Table structure for `da_gas_meter_info`
-- ----------------------------
DROP TABLE IF EXISTS `da_gas_meter_info`;
CREATE TABLE `da_gas_meter_info` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司code',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(100) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(100) DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `gas_meter_factory_id` bigint(20) DEFAULT NULL COMMENT '厂家ID',
  `gas_meter_factory_name` varchar(30) DEFAULT NULL COMMENT '厂家名称',
  `gas_meter_type_id` bigint(20) DEFAULT NULL COMMENT '气表类型id',
  `gas_meter_type_name` varchar(30) DEFAULT NULL COMMENT '气表类型名称',
  `use_gas_type_id` bigint(20) DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(30) DEFAULT NULL COMMENT '用气类型名称',
  `initial_measurement_base` decimal(18,4) DEFAULT NULL COMMENT '初始量',
  `id_card_number` varchar(30) DEFAULT NULL COMMENT 'ID卡号',
  `ic_card_number` varchar(30) DEFAULT NULL COMMENT 'IC卡号',
  `card_verify_data` varchar(10) DEFAULT NULL COMMENT 'IC卡校验码',
  `total_charge_gas` decimal(18,4) DEFAULT NULL COMMENT '累计充值计量',
  `total_charge_money` decimal(18,4) DEFAULT NULL COMMENT '累计充值金额',
  `total_charge_count` int(11) DEFAULT NULL COMMENT '累计充值次数',
  `total_replacement_card_count` int(11) DEFAULT NULL COMMENT '累计补卡次数',
  `total_maintenance_count` int(11) DEFAULT NULL COMMENT '累计维修次数',
  `total_additional_count` int(11) DEFAULT NULL COMMENT '累计补气次数',
  `total_read_meter_count` int(11) DEFAULT NULL COMMENT '累计抄表次数',
  `total_use_gas` decimal(18,4) DEFAULT NULL COMMENT '累计用气量',
  `total_use_gas_money` decimal(18,4) DEFAULT NULL COMMENT '累计用气金额',
  `cycle_charge_gas` decimal(18,4) DEFAULT NULL COMMENT '周期累计充值量',
  `cycle_use_gas` decimal(18,4) DEFAULT NULL COMMENT '周期累计使用量',
  `gas_meter_gas_balance` decimal(18,4) DEFAULT NULL COMMENT '表内余量',
  `gas_meter_balance` decimal(18,4) DEFAULT NULL COMMENT '表内余额',
  `price_scheme_id` int(11) DEFAULT NULL COMMENT '表端价格号',
  `current_price` decimal(18,4) DEFAULT NULL COMMENT '当前价格',
  `value_1` decimal(18,4) DEFAULT NULL COMMENT '上次充值量',
  `value_2` decimal(18,4) DEFAULT NULL COMMENT '上上次充值量',
  `value_3` decimal(18,4) DEFAULT NULL COMMENT '上上上次充值量',
  `charge_count` int(11) DEFAULT NULL COMMENT '气表充值次数',
  `additional_count` int(11) DEFAULT NULL COMMENT '气表补充次数',
  `replacement_card_count` int(11) DEFAULT NULL COMMENT '气表补卡次数',
  `compatible_parameter` varchar(1000) DEFAULT NULL COMMENT '兼容特殊参数',
  `last_charge_time` datetime DEFAULT NULL COMMENT '最后充值时间',
  `last_read_meter_time` datetime DEFAULT NULL COMMENT '最后抄表时间',
  `read_meter_start_gas` decimal(18,4) DEFAULT NULL COMMENT '最后抄表初量',
  `read_meter_end_gas` decimal(18,4) DEFAULT NULL COMMENT '最后抄表末值',
  `read_meter_gas` decimal(18,4) DEFAULT NULL COMMENT '最后抄表结算值',
  `insure` smallint(6) DEFAULT NULL COMMENT '购保',
  `insure_end_time` date DEFAULT NULL COMMENT '保险截止日期',
  `first_insure_end_time` date DEFAULT NULL COMMENT '购保前截止日期',
  `last_security_check_time` datetime DEFAULT NULL COMMENT '最后安检时间',
  `security_check_user_id` char(32) DEFAULT NULL COMMENT '安检员ID',
  `security_check_user_name` varchar(100) DEFAULT NULL COMMENT '安检员名称',
  `security_check_type` varchar(3) DEFAULT NULL COMMENT '安检类型',
  `security_check_status` varchar(30) DEFAULT NULL COMMENT '安检状态',
  `security_check_result` varchar(1000) DEFAULT NULL COMMENT '安检结果',
  `last_scenes_` varchar(30) DEFAULT NULL COMMENT '最后的业务场景',
  `last_scenes_charge_status` smallint(6) DEFAULT NULL COMMENT '最后的业务场景收费状态',
  `first_scenes_id` bigint(32) DEFAULT NULL COMMENT '前一次的业务场景ID',
  `first_scenes_name` varchar(20) DEFAULT NULL COMMENT '前一次业务场景名称',
  `first_scenes_charge_status` smallint(6) DEFAULT NULL COMMENT '前一次的业务场景收费状态',
  `day_iot_use_gas` decimal(18,4) DEFAULT NULL COMMENT '物联网表上日止数',
  `month_iot_use_gas` decimal(18,4) DEFAULT NULL COMMENT '物联网上月止数',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表使用情况';

-- ----------------------------
-- Table structure for `da_input_output_meter_story`
-- ----------------------------
DROP TABLE IF EXISTS `da_input_output_meter_story`;
CREATE TABLE `da_input_output_meter_story` (
  `ID` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL,
  `company_name` varchar(100) DEFAULT NULL,
  `org_id` bigint(32) DEFAULT NULL,
  `org_name` varchar(100) DEFAULT NULL,
  `serial_code` varchar(100) DEFAULT NULL COMMENT '批次号',
  `gas_meter_factory_id` char(32) DEFAULT NULL,
  `gas_meter_factory_name` varchar(100) DEFAULT NULL,
  `gas_meter_version_id` char(32) DEFAULT NULL COMMENT '版号id',
  `gas_meter_version_name` varchar(30) DEFAULT NULL COMMENT '版号名称',
  `gas_meter_type_id` char(32) DEFAULT NULL COMMENT '普表\r\n            卡表\r\n            物联网表\r\n            流量计(普表)\r\n            流量计(卡表）\r\n            流量计(物联网表）',
  `gas_meter_type_name` varchar(30) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL COMMENT '1表示出库\r\n			0表示入库\r\n		',
  `store_count` bigint(20) DEFAULT NULL COMMENT '表示库存数量',
  `create_time` datetime DEFAULT NULL COMMENT '出入库时间',
  `create_user` bigint(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `da_meter_stock`
-- ----------------------------
DROP TABLE IF EXISTS `da_meter_stock`;
CREATE TABLE `da_meter_stock` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `gas_code` varchar(30) DEFAULT NULL COMMENT '气表编号',
  `gas_meter_factory_id` bigint(32) DEFAULT NULL COMMENT '厂家ID',
  `gas_meter_factory_name` varchar(100) DEFAULT NULL COMMENT '厂家名称',
  `gas_meter_version_id` bigint(32) DEFAULT NULL COMMENT '版号ID',
  `gas_meter_version_name` varchar(30) DEFAULT NULL COMMENT '版号名称',
  `gas_meter_type_id` bigint(32) DEFAULT NULL COMMENT '表类型',
  `gas_meter_type_name` varchar(30) DEFAULT NULL COMMENT '表类型名称 普表 卡表 物联网表 流量计(普表) 流量计(卡表）流量计(物联网表）',
  `buy_gas` datetime DEFAULT NULL COMMENT '购买时间',
  `calibration_time` datetime DEFAULT NULL COMMENT '检定时间',
  `safe_code` varchar(30) DEFAULT NULL COMMENT '防盗扣编码',
  `remark` varchar(20) DEFAULT NULL COMMENT '备注',
  `data_status` smallint(6) DEFAULT NULL COMMENT '数据状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更信任',
  `delete_status` smallint(6) DEFAULT NULL COMMENT '删除标识 1-表示删除，0-正常使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表具库存信息';

-- ----------------------------
-- Table structure for `da_node`
-- ----------------------------
DROP TABLE IF EXISTS `da_node`;
CREATE TABLE `da_node` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司code',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `parent_id` char(32) DEFAULT NULL COMMENT '父节点ID',
  `node_name` varchar(100) DEFAULT NULL COMMENT '节点名称',
  `node_number` varchar(30) DEFAULT NULL COMMENT '节点编号',
  `node_base` decimal(18,4) DEFAULT NULL COMMENT '节点底数',
  `node_factory_id` char(32) DEFAULT NULL COMMENT '厂家',
  `node_type_id` char(32) DEFAULT NULL COMMENT '型号',
  `caliber` varchar(10) DEFAULT NULL COMMENT '管道口径',
  `upper_temperature_limit` int(11) DEFAULT NULL COMMENT '温度上限',
  `lower_temperature_limit` int(11) DEFAULT NULL COMMENT '温度下限',
  `upper_pressure_limit` int(11) DEFAULT NULL COMMENT '压力上限',
  `lower_pressure_limit` int(11) DEFAULT NULL COMMENT '压力下限',
  `upper_flow_limit` int(11) DEFAULT NULL COMMENT '流量上限',
  `lower_flow_limit` int(11) DEFAULT NULL COMMENT '流量下限',
  `longitude` decimal(18,8) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18,8) DEFAULT NULL COMMENT '纬度',
  `principal_user_id` char(32) DEFAULT NULL COMMENT '负责人ID',
  `principal_user_name` varchar(100) DEFAULT NULL COMMENT '负责人名称',
  `telphone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `alarm_start_time` datetime DEFAULT NULL COMMENT '报警开始时间',
  `update_alarm_time` datetime DEFAULT NULL COMMENT '最新报警时间',
  `alarm_status` smallint(6) DEFAULT NULL COMMENT '报警状态',
  `alarm_content` varchar(500) DEFAULT NULL COMMENT '报警信息',
  `temperature` decimal(18,4) DEFAULT NULL COMMENT '温度',
  `pressure` decimal(18,4) DEFAULT NULL COMMENT '压力',
  `instantaneous_flow_rate` decimal(18,4) DEFAULT NULL COMMENT '瞬时流量',
  `working_flow` decimal(18,4) DEFAULT NULL COMMENT '工况流量',
  `standard_flow` decimal(18,4) DEFAULT NULL COMMENT '标况流量',
  `total_use_gas` decimal(18,4) DEFAULT NULL COMMENT '累计用气量',
  `data_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `yesterday_total_gas` decimal(18,4) DEFAULT NULL COMMENT '昨日止数',
  `yesterday_use_gas` decimal(18,4) DEFAULT NULL COMMENT '昨日用量',
  `last_month_total_gas` decimal(18,4) DEFAULT NULL COMMENT '上月止数',
  `last_month_use_gas` decimal(18,4) DEFAULT NULL COMMENT '上月用量',
  `data_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `delete_status` int(32) DEFAULT NULL COMMENT '删除标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_user` bigint(32) DEFAULT NULL COMMENT '删除人',
  `install_address` varchar(100) DEFAULT NULL COMMENT '安装地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------
-- Table structure for `da_purchase_limit_customer`
-- ----------------------------
DROP TABLE IF EXISTS `da_purchase_limit_customer`;
CREATE TABLE `da_purchase_limit_customer` (
  `id` bigint(32) NOT NULL,
  `limit_id` bigint(32) DEFAULT NULL COMMENT '限购ID',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `gas_meter_code` varchar(32) DEFAULT NULL COMMENT '气表ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户id',
  `delete_status` smallint(6) DEFAULT '0' COMMENT '删除标识1-删除,0-正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='限购客户';

-- ----------------------------
-- Table structure for `f_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `f_attachment`;
CREATE TABLE `f_attachment` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `biz_id` varchar(64) DEFAULT NULL COMMENT '业务ID',
  `biz_type` varchar(255) DEFAULT NULL COMMENT '业务类型\n#AttachmentType',
  `data_type` varchar(255) DEFAULT 'IMAGE' COMMENT '数据类型\n#DataType{DIR:目录;IMAGE:图片;VIDEO:视频;AUDIO:音频;DOC:文档;OTHER:其他}',
  `submitted_file_name` varchar(255) DEFAULT '' COMMENT '原始文件名',
  `group_` varchar(255) DEFAULT '' COMMENT 'FastDFS返回的组\n用于FastDFS',
  `path` varchar(255) DEFAULT '' COMMENT 'FastDFS的远程文件名\n用于FastDFS',
  `relative_path` varchar(255) DEFAULT '' COMMENT '文件相对路径',
  `url` varchar(255) DEFAULT '' COMMENT '文件访问链接\n需要通过nginx配置路由，才能访问',
  `file_md5` varchar(255) DEFAULT NULL COMMENT '文件md5值',
  `context_type` varchar(255) DEFAULT '' COMMENT '文件上传类型\n取上传文件的值',
  `filename` varchar(255) DEFAULT '' COMMENT '唯一文件名',
  `ext` varchar(64) DEFAULT '' COMMENT '后缀\n (没有.)',
  `size` bigint(20) DEFAULT '0' COMMENT '大小',
  `org_id` bigint(20) DEFAULT NULL COMMENT '组织ID\n#c_core_org',
  `icon` varchar(64) DEFAULT '' COMMENT '图标',
  `create_month` varchar(10) DEFAULT NULL COMMENT '创建年月\n格式：yyyy-MM 用于统计',
  `create_week` varchar(10) DEFAULT NULL COMMENT '创建时处于当年的第几周\nyyyy-ww 用于统计',
  `create_day` varchar(12) DEFAULT NULL COMMENT '创建年月日\n格式： yyyy-MM-dd 用于统计',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(11) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '秦川修改时间',
  `update_user` bigint(11) DEFAULT NULL COMMENT '秦川修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='附件';

-- ----------------------------
-- Table structure for `f_file`
-- ----------------------------
DROP TABLE IF EXISTS `f_file`;
CREATE TABLE `f_file` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `data_type` varchar(255) DEFAULT 'IMAGE' COMMENT '数据类型\n#DataType{DIR:目录;IMAGE:图片;VIDEO:视频;AUDIO:音频;DOC:文档;OTHER:其他}',
  `submitted_file_name` varchar(255) DEFAULT '' COMMENT '原始文件名',
  `tree_path` varchar(255) DEFAULT ',' COMMENT '父目录层级关系',
  `grade` int(11) DEFAULT '1' COMMENT '层级等级\n从1开始计算',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否删除\n#BooleanStatus{TRUE:1,已删除;FALSE:0,未删除}',
  `folder_id` bigint(20) DEFAULT '0' COMMENT '父文件夹ID',
  `url` varchar(1000) DEFAULT '' COMMENT '文件访问链接\n需要通过nginx配置路由，才能访问',
  `size` bigint(20) DEFAULT '0' COMMENT '文件大小\n单位字节',
  `folder_name` varchar(255) DEFAULT '' COMMENT '父文件夹名称',
  `group_` varchar(255) DEFAULT '' COMMENT 'FastDFS组\n用于FastDFS',
  `path` varchar(255) DEFAULT '' COMMENT 'FastDFS远程文件名\n用于FastDFS',
  `relative_path` varchar(255) DEFAULT '' COMMENT '文件的相对路径 ',
  `file_md5` varchar(255) DEFAULT '' COMMENT 'md5值',
  `context_type` varchar(255) DEFAULT '' COMMENT '文件类型\n取上传文件的值',
  `filename` varchar(255) DEFAULT '' COMMENT '唯一文件名',
  `ext` varchar(64) DEFAULT '' COMMENT '文件名后缀 \n(没有.)',
  `icon` varchar(64) DEFAULT '' COMMENT '文件图标\n用于云盘显示',
  `create_month` varchar(10) DEFAULT NULL COMMENT '创建时年月\n格式：yyyy-MM 用于统计',
  `create_week` varchar(10) DEFAULT NULL COMMENT '创建时年周\nyyyy-ww 用于统计',
  `create_day` varchar(12) DEFAULT NULL COMMENT '创建时年月日\n格式： yyyy-MM-dd 用于统计',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '秦川修改时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '秦川修改人',
  PRIMARY KEY (`id`) USING BTREE,
  FULLTEXT KEY `FU_TREE_PATH` (`tree_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件表';

-- ----------------------------
-- Table structure for `gc_gas_install_file`
-- ----------------------------
DROP TABLE IF EXISTS `gc_gas_install_file`;
CREATE TABLE `gc_gas_install_file` (
  `id` bigint(32) NOT NULL,
  `install_number` varchar(32) DEFAULT NULL COMMENT '报装编号',
  `material_type` varchar(10) DEFAULT NULL COMMENT 'ID：身份证件\r\n            CONTRACT：合同\r\n            OTHER:其他\r\n            ',
  `material_url` varchar(1000) DEFAULT NULL COMMENT '材料地址',
  `material_image_url` varchar(1000) DEFAULT NULL COMMENT '缩略图地址',
  `material_file_name` varchar(200) DEFAULT NULL COMMENT '原文件名',
  `material_file_extension` varchar(10) DEFAULT NULL COMMENT '原文件扩展名',
  `status` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报装资料';

-- ----------------------------
-- Table structure for `gc_install_charge_detail`
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_charge_detail`;
CREATE TABLE `gc_install_charge_detail` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `install_id` bigint(32) DEFAULT NULL COMMENT '报装ID',
  `charge_item_id` bigint(32) NOT NULL COMMENT '收费项目ID',
  `charge_item_name` varchar(100) DEFAULT NULL COMMENT '收费项目名称',
  `charge_state` varchar(32) DEFAULT NULL COMMENT '收费状态\r\n            WAIT_CHARGE: 等待收费\r\n            CHARGED: 已收费\r\n            FREE_CHARGE: 免收费\r\n            ',
  `amount` decimal(32,4) DEFAULT NULL COMMENT '金额',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_status` int(2) DEFAULT NULL COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报装费用清单';

-- ----------------------------
-- Table structure for `gc_install_detail`
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_detail`;
CREATE TABLE `gc_install_detail` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `install_number` varchar(32) DEFAULT NULL COMMENT '报装编号',
  `address_detail` varchar(32) DEFAULT NULL COMMENT '详细地址',
  `gas_meter_factory_id` bigint(32) DEFAULT NULL COMMENT '厂家ID',
  `gas_meter_factory_name` varchar(32) DEFAULT NULL COMMENT '厂家名称',
  `gas_meter_id` bigint(32) DEFAULT NULL COMMENT '气表ID',
  `gas_meter_name` varchar(32) DEFAULT NULL COMMENT '气表名称',
  `gas_meter_version_id` bigint(32) DEFAULT NULL COMMENT '版号ID',
  `gas_meter_version_name` varchar(30) DEFAULT NULL COMMENT '版号名称',
  `surge_tank_number` varchar(32) DEFAULT NULL COMMENT '调压箱编号',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='安装明细';

-- ----------------------------
-- Table structure for `gc_install_order_record`
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_order_record`;
CREATE TABLE `gc_install_order_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `install_number` varchar(32) DEFAULT NULL COMMENT '报装编号',
  `work_order_no` varchar(32) DEFAULT NULL COMMENT '工单编号',
  `work_order_type` varchar(32) DEFAULT NULL COMMENT '工单类型\r\n            survey: 踏勘\r\n            install: 施工',
  `urgency` varchar(20) DEFAULT NULL COMMENT '紧急程度\r\n            normal:正常\r\n            urgent:紧急\r\n            very_urgent:非常紧急',
  `receive_user_id` bigint(32) DEFAULT NULL COMMENT '接单人ID',
  `receive_user_name` varchar(100) DEFAULT NULL COMMENT '接单人名称',
  `receive_user_mobile` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `receive_time` datetime DEFAULT NULL COMMENT '接单时间',
  `work_order_desc` varchar(1000) DEFAULT NULL COMMENT '工单内容',
  `invalid_desc` varchar(1000) DEFAULT NULL COMMENT '工单作废原因：转单，拒单，退单原因描述',
  `termination_desc` varchar(1000) DEFAULT NULL COMMENT '终止原因：业务终止',
  `end_job_user_id` bigint(32) DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime DEFAULT NULL COMMENT '结单时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_status` varchar(32) DEFAULT NULL COMMENT '工单状态\r\n            WAITE_COMPLETE: 待结单\r\n            COMPLETE:已结单\r\n            INVALID: 已作废',
  `oper_status` varchar(32) DEFAULT NULL COMMENT '工单操作状态\r\n            WAITE_RECEIVE: 待接单\r\n            RECEIVE: 已接单\r\n            REJECT: 已拒单\r\n            BACK:已退单\r\n            TERMINATION:已终止\r\n            COMPLETE:已结单\r\n            ',
  `turn_id` bigint(32) DEFAULT NULL COMMENT '转单ID',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报装工单记录';

-- ----------------------------
-- Table structure for `gc_install_process_record`
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_process_record`;
CREATE TABLE `gc_install_process_record` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `install_id` bigint(32) DEFAULT NULL COMMENT '报装ID',
  `process_state` varchar(32) DEFAULT NULL COMMENT '流程状态',
  `oper_process_state` varchar(32) DEFAULT NULL COMMENT '操作状态',
  `remark` varchar(32) DEFAULT NULL COMMENT '操作备注',
  `create_user_name` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报装流程操作记录';

-- ----------------------------
-- Table structure for `gc_install_record`
-- ----------------------------
DROP TABLE IF EXISTS `gc_install_record`;
CREATE TABLE `gc_install_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `install_number` varchar(30) DEFAULT NULL COMMENT '报装编号',
  `install_type_id` bigint(32) DEFAULT NULL COMMENT '报装类型ID：数字类型（字典表ID）\r\n            SFINSTALL:商福报装\r\n            GYINSTALL:工业报装\r\n            LSINSTALL:零散报装\r\n            JZINSTALL:集中报装',
  `install_type_name` varchar(30) DEFAULT NULL COMMENT '报装类型名称\r\n            SFINSTALL:商福报装\r\n            GYINSTALL:工业报装\r\n            LSINSTALL:零散报装\r\n            JZINSTALL:集中报装',
  `city_id` bigint(20) DEFAULT NULL COMMENT '市ID',
  `city_name` varchar(32) DEFAULT NULL COMMENT '市',
  `area_id` bigint(20) DEFAULT NULL COMMENT '区县ID',
  `area_name` varchar(32) DEFAULT NULL COMMENT '区县ID',
  `street_id` bigint(20) DEFAULT NULL COMMENT '街道（乡镇）ID',
  `street_name` varchar(100) DEFAULT NULL COMMENT '街道（乡镇）',
  `community_id` bigint(20) DEFAULT NULL COMMENT '小区（村）ID',
  `community_name` varchar(100) DEFAULT NULL COMMENT '小区（村）',
  `storied_id` bigint(20) DEFAULT NULL COMMENT '楼栋（组）',
  `storied_name` varchar(32) DEFAULT NULL COMMENT '楼栋（组）',
  `floor_num` int(11) DEFAULT NULL COMMENT '楼层',
  `population` int(11) DEFAULT NULL COMMENT '户数',
  `address_remark` varchar(1000) DEFAULT NULL COMMENT '详细地址备注',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '报装人',
  `telphone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `id_type_id` bigint(32) DEFAULT NULL COMMENT '证件类型ID：数字类型(字典表ID)\r\n            ICID:身份证\r\n            BLID:营业执照\r\n            ',
  `id_type_name` varchar(50) DEFAULT NULL COMMENT '证件类型ID：\r\n            ICID:身份证\r\n            BLID:营业执照',
  `id_number` varchar(30) DEFAULT NULL COMMENT '证件号码',
  `accept_time` datetime DEFAULT NULL COMMENT '受理时间',
  `charge_status` varchar(32) DEFAULT NULL COMMENT '收费状态\r\n            CHARGING: 缴费中\r\n            CHARGED: 缴费完成\r\n            CHARGE_FAILED: 缴费失败\r\n            ',
  `money` decimal(18,4) DEFAULT NULL COMMENT '收费金额',
  `process_status` varchar(32) DEFAULT NULL COMMENT '流程状态\r\n            WAITE_SUBMIT_AUDIT：待提审\r\n            WAITE_AUDIT：待审核\r\n            AUDIT_REJECT：审核驳回\r\n            WAITE_SURVEY_DISPATCH：待踏勘派工\r\n            WAITE_SURVEY_RECEIVE_ORDER：待踏勘接单\r\n            WAITE_SURVEYING：踏勘中\r\n            SURVEY_UNTHROW：踏勘不通过\r\n            WAITE_INSTALL_DISPATCH：待安装派工\r\n            WAITE_INSTALL_RECEIVE_ORDER：待安装接单\r\n            WAITE_INSTALLING：安装中\r\n            WAITE_CHARGE：待收费\r\n            WAITE_COMPLETE：待确认完结\r\n            COMPLETE：已完结\r\n            TERMINATION：终止',
  `step_on_user_id` bigint(32) DEFAULT NULL COMMENT '踏勘人ID',
  `step_on_user_name` varchar(1000) DEFAULT NULL COMMENT '踏勘人名称',
  `step_on_time` datetime DEFAULT NULL COMMENT '踏勘时间',
  `install_user_id` bigint(32) DEFAULT NULL COMMENT '施工人ID',
  `install_user_name` varchar(1000) DEFAULT NULL COMMENT '施工人名称',
  `install_time` datetime DEFAULT NULL COMMENT '施工时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `review_user_id` bigint(32) DEFAULT NULL COMMENT '审核人ID',
  `review_user_name` varchar(100) DEFAULT NULL COMMENT '审核人名称',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `review_objection` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `end_job_user_id` bigint(32) DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime DEFAULT NULL COMMENT '结单时间',
  `termination_user_id` bigint(32) DEFAULT NULL COMMENT '终止人ID',
  `termination_user_name` varchar(100) DEFAULT NULL COMMENT '终止人名称',
  `termination_time` datetime DEFAULT NULL COMMENT '终止时间',
  `termination_reason` varchar(1000) DEFAULT NULL COMMENT '终止原因',
  `data_status` varchar(32) DEFAULT NULL COMMENT '报装状态\r\n            WAITE_SUBMIT_AUDIT：待提审\r\n            WAITE_AUDIT：待审核\r\n            AUDIT_REJECT：审核驳回\r\n            WAITE_SURVEY：待踏勘\r\n            WAITE_INSTALL：待施工\r\n            WAITE_CHARGE：待收费\r\n            WAITE_COMPLETE：待完结\r\n            COMPLETE：完结',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报装记录';

-- ----------------------------
-- Table structure for `gc_operation_acceptance`
-- ----------------------------
DROP TABLE IF EXISTS `gc_operation_acceptance`;
CREATE TABLE `gc_operation_acceptance` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) NOT NULL COMMENT '公司CODE',
  `company_name` varchar(300) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `alarm_customer_code` varchar(32) DEFAULT NULL COMMENT '报警人编号',
  `alarm_customer_name` varchar(60) DEFAULT NULL COMMENT '报警人名称',
  `alarm_customer_phone` varchar(20) DEFAULT NULL COMMENT '报警人电话',
  `alarm_content` varchar(1000) DEFAULT NULL COMMENT '报警内容',
  `gas_code` char(32) DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `gas_meter_factory_id` bigint(32) DEFAULT NULL COMMENT '气表厂家ID',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型ID',
  `oper_accept_no` varchar(32) DEFAULT NULL COMMENT '运维受理编号',
  `plan_handle_user_id` char(32) DEFAULT NULL COMMENT '计划处警人ID',
  `plan_handle_user_name` varchar(100) DEFAULT NULL COMMENT '计划处警人名称',
  `plan_handle_time` datetime DEFAULT NULL COMMENT '计划处警时间',
  `handle_user_id` bigint(32) DEFAULT NULL COMMENT '处警人ID',
  `handle_user_name` varchar(100) DEFAULT NULL COMMENT '处警人名称',
  `handle_time` datetime DEFAULT NULL COMMENT '处警时间',
  `terminate_user_id` bigint(32) DEFAULT NULL COMMENT '终止人ID',
  `terminate_user_name` varchar(100) DEFAULT NULL COMMENT '终止人名称',
  `terminate_time` datetime DEFAULT NULL COMMENT '终止时间',
  `stop_desc` varchar(1000) DEFAULT NULL COMMENT '终止原因',
  `accept_status` smallint(6) DEFAULT NULL COMMENT '受理状态:\r\n             0 待处理\r\n             1 已处理\r\n             2 未处理',
  `accept_process_state` smallint(6) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运行维护受理';

-- ----------------------------
-- Table structure for `gc_operation_process`
-- ----------------------------
DROP TABLE IF EXISTS `gc_operation_process`;
CREATE TABLE `gc_operation_process` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `accept_id` varchar(32) DEFAULT NULL COMMENT '运行维护受理编号',
  `process_state` varchar(32) DEFAULT NULL COMMENT '流程状态',
  `oper_process_state` varchar(32) DEFAULT NULL COMMENT '操作状态',
  `remark` varchar(32) DEFAULT NULL COMMENT '操作备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运行维护流程操作记录';

-- ----------------------------
-- Table structure for `gc_operation_record`
-- ----------------------------
DROP TABLE IF EXISTS `gc_operation_record`;
CREATE TABLE `gc_operation_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) NOT NULL COMMENT '公司CODE',
  `company_name` varchar(300) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(60) DEFAULT NULL COMMENT '客户名称',
  `gas_code` char(32) DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `gas_meter_factory_id` bigint(32) DEFAULT NULL COMMENT '气表厂家ID',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型ID',
  `accept_no` varchar(32) DEFAULT NULL COMMENT '运维受理编号',
  `receive_user_id` bigint(32) DEFAULT NULL COMMENT '接单人ID',
  `receive_user_name` varchar(100) DEFAULT NULL COMMENT '接单人名称',
  `receive_user_phone` varchar(20) DEFAULT NULL COMMENT '接单人电话',
  `receive_user_time` datetime DEFAULT NULL COMMENT '接单人时间',
  `book_operation_time` datetime DEFAULT NULL COMMENT '预约时间',
  `review_user_id` bigint(32) DEFAULT NULL COMMENT '审批人ID',
  `review_user_name` varchar(100) DEFAULT NULL COMMENT '审批人名称',
  `review_time` datetime DEFAULT NULL COMMENT '审批时间',
  `review_objection` varchar(100) DEFAULT NULL COMMENT '审批意见',
  `distribution_user_id` bigint(32) DEFAULT NULL COMMENT '派单人ID',
  `distribution_user_name` varchar(100) DEFAULT NULL COMMENT '派单人名称',
  `distribution_time` datetime DEFAULT NULL COMMENT '派单时间',
  `end_job_user_id` bigint(32) DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime DEFAULT NULL COMMENT '结单时间',
  `reject_reason` varchar(1000) DEFAULT NULL COMMENT '驳回原因',
  `terminate_user_id` bigint(32) DEFAULT NULL COMMENT '终止人ID',
  `terminate_user_name` varchar(100) DEFAULT NULL COMMENT '终止人名称',
  `terminate_time` datetime DEFAULT NULL COMMENT '终止时间',
  `stop_desc` varchar(1000) DEFAULT NULL COMMENT '终止原因',
  `order_type` smallint(6) DEFAULT NULL COMMENT '工单类型\r\n            0 普通运行维护\r\n            1 点火通气',
  `urgency_level` smallint(6) DEFAULT NULL COMMENT '紧急程度\r\n            0 正常\r\n            1 紧急\r\n            2 非常紧急',
  `order_content` varchar(1000) DEFAULT NULL COMMENT '工单内容',
  `operation_status` smallint(6) DEFAULT NULL COMMENT '运维状态:\r\n             0 待提审\r\n             1 待审核\r\n             2 待接单\r\n             3 待运维\r\n             4 运维中\r\n             5 终止\r\n             6 作废\r\n             7 结单',
  `operation_process_state` smallint(6) DEFAULT NULL COMMENT '运维流程状态\r\n            0 提审\r\n            1 审核\r\n            2 派单\r\n            3 驳回\r\n            4 撤回\r\n            5 接单确认\r\n            6 拒单\r\n            7 退单\r\n            8 转单\r\n            9 预约\r\n            10 上门\r\n            11 结单\r\n            12 终止',
  `transfer_id` bigint(32) DEFAULT NULL COMMENT '转单ID',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运行维护工单';

-- ----------------------------
-- Table structure for `gc_security_check_order_record`
-- ----------------------------
DROP TABLE IF EXISTS `gc_security_check_order_record`;
CREATE TABLE `gc_security_check_order_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(300) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `sc_no` varchar(32) DEFAULT NULL COMMENT '安检计划编号',
  `work_order_no` varchar(32) DEFAULT NULL COMMENT '工单编号',
  `work_order_type` varchar(32) DEFAULT NULL COMMENT '工单类型',
  `urgency` varchar(20) DEFAULT NULL COMMENT '紧急程度',
  `receive_user_id` bigint(32) DEFAULT NULL COMMENT '接单人ID',
  `receive_user_name` varchar(100) DEFAULT NULL COMMENT '接单人名称',
  `receive_user_mobile` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `reveive_time` datetime DEFAULT NULL COMMENT '接单时间',
  `work_order_desc` varchar(1000) DEFAULT NULL COMMENT '工单内容',
  `terminate_reason` varchar(1000) DEFAULT NULL COMMENT '终止原因',
  `cancellation_reason` varchar(1000) DEFAULT NULL COMMENT '作废原因（包括转单、拒单、退单）',
  `end_job_user_id` bigint(32) DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime DEFAULT NULL COMMENT '结单时间',
  `transfer_order_id` bigint(32) DEFAULT NULL COMMENT '转单id',
  `order_status` smallint(6) DEFAULT NULL COMMENT '工单状态\r\n            0 待接单\r\n             1 转单\r\n             2 已接单\r\n             3 待安检\r\n             4 安检异常\r\n             5 结单\r\n             6 终止\r\n             7 拒单\r\n             8 退单',
  `order_process_status` smallint(6) DEFAULT NULL COMMENT '工单流程状态\r\n            0 预约安检\r\n            1 上门安检\r\n            2 待整改\r\n            3 已整改\r\n            4 未整改\r\n            5 作废\r\n            6 终止\r\n            7 结单',
  `hidden_danger` varchar(1000) DEFAULT NULL COMMENT '安全隐患',
  `hidden_danger_handle_status` smallint(6) DEFAULT NULL COMMENT '隐患处理状态\r\n            0、待处理\r\n            1、已按要求整改\r\n            2、未按要求整改',
  `hidden_danger_handle_time` datetime DEFAULT NULL COMMENT '隐患处理登记时间',
  `hidden_danger_handle_user_id` varchar(100) DEFAULT NULL COMMENT '隐患处理登记人ID',
  `hidden_danger_handle_user_name` varchar(100) DEFAULT NULL COMMENT '隐患处理登记人名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安检工单';

-- ----------------------------
-- Table structure for `gc_security_check_process`
-- ----------------------------
DROP TABLE IF EXISTS `gc_security_check_process`;
CREATE TABLE `gc_security_check_process` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `security_check_id` bigint(32) DEFAULT NULL COMMENT '安检计划ID',
  `process_state` varchar(32) DEFAULT NULL COMMENT '流程状态',
  `oper_process_state` varchar(32) DEFAULT NULL COMMENT '操作状态',
  `remark` varchar(32) DEFAULT NULL COMMENT '操作备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_120` (`security_check_id`),
  CONSTRAINT `FK_Reference_120` FOREIGN KEY (`security_check_id`) REFERENCES `gc_security_check_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='安检流程操作记录';

-- ----------------------------
-- Table structure for `gc_security_check_record`
-- ----------------------------
DROP TABLE IF EXISTS `gc_security_check_record`;
CREATE TABLE `gc_security_check_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) NOT NULL COMMENT '公司CODE',
  `company_name` varchar(300) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(60) DEFAULT NULL COMMENT '客户名称',
  `gas_code` char(32) DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `gas_meter_factory_id` bigint(32) DEFAULT NULL COMMENT '气表厂家ID',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型ID',
  `sc_no` varchar(32) DEFAULT NULL COMMENT '安检编号',
  `plan_security_check_user_id` char(32) DEFAULT NULL COMMENT '计划安检员ID',
  `plan_security_check_user_name` varchar(100) DEFAULT NULL COMMENT '计划安检员名称',
  `plan_security_check_time` datetime DEFAULT NULL COMMENT '计划安检时间',
  `security_check_content` varchar(1000) DEFAULT NULL COMMENT '安检内容',
  `security_check_user_id` bigint(32) DEFAULT NULL COMMENT '安检员ID',
  `security_check_user_name` varchar(100) DEFAULT NULL COMMENT '安检员名称',
  `security_check_time` datetime DEFAULT NULL COMMENT '安检时间',
  `review_user_id` bigint(32) DEFAULT NULL COMMENT '审批人ID',
  `review_user_name` varchar(100) DEFAULT NULL COMMENT '审批人名称',
  `review_time` datetime DEFAULT NULL COMMENT '审批时间',
  `review_objection` varchar(100) DEFAULT NULL COMMENT '审批意见',
  `distribution_user_id` bigint(32) DEFAULT NULL COMMENT '派工人ID',
  `distribution_user_name` varchar(100) DEFAULT NULL COMMENT '派工人名称',
  `distribution_time` datetime DEFAULT NULL COMMENT '派人时间',
  `end_job_user_id` bigint(32) DEFAULT NULL COMMENT '结单人ID',
  `end_job_user_name` varchar(100) DEFAULT NULL COMMENT '结单人名称',
  `end_job_time` datetime DEFAULT NULL COMMENT '结单时间',
  `reject_desc` varchar(1000) DEFAULT NULL COMMENT '驳回原因',
  `terminate_user_id` bigint(32) DEFAULT NULL COMMENT '终止人ID',
  `terminate_user_name` varchar(100) DEFAULT NULL COMMENT '终止人名称',
  `terminate_time` datetime DEFAULT NULL COMMENT '终止时间',
  `stop_desc` varchar(1000) DEFAULT NULL COMMENT '终止原因',
  `data_status` smallint(6) DEFAULT NULL COMMENT '安检状态:0 待提审    \r\n             1 待审核\r\n             2 待派工\r\n             3 待安检\r\n             4 安检中\r\n             5 异常\r\n             6 结单\r\n             7 终止',
  `security_check_process_state` smallint(6) DEFAULT NULL COMMENT '安检流程状态\r\n            0 安检提审\r\n            1 安检计划审核\r\n            2 安检派工\r\n            3 接单确认\r\n            4 预约安检时间\r\n            5 上门安检\r\n            6 待整改\r\n            7 已整改\r\n            8 结单\r\n            9 终止\r\n            ',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安检记录';

-- ----------------------------
-- Table structure for `gc_security_check_result`
-- ----------------------------
DROP TABLE IF EXISTS `gc_security_check_result`;
CREATE TABLE `gc_security_check_result` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) NOT NULL COMMENT '公司CODE',
  `company_name` varchar(300) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `customer_code` bigint(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(60) DEFAULT NULL COMMENT '客户名称',
  `gas_code` char(32) DEFAULT NULL COMMENT '气表编号',
  `gas_meter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `gas_meter_factory_id` bigint(32) DEFAULT NULL COMMENT '气表厂家ID',
  `use_gas_type_id` char(32) DEFAULT NULL COMMENT '用气类型ID',
  `sc_no` varchar(32) DEFAULT NULL COMMENT '安检编号',
  `security_check_content` varchar(1000) DEFAULT NULL COMMENT '安检内容',
  `security_check_user_id` bigint(32) DEFAULT NULL COMMENT '安检员ID',
  `security_check_user_name` varchar(100) DEFAULT NULL COMMENT '安检员名称',
  `security_check_time` datetime DEFAULT NULL COMMENT '安检时间',
  `work_order_no` varchar(32) DEFAULT NULL COMMENT '工单编号',
  `terminate_user_id` bigint(32) DEFAULT NULL COMMENT '终止人ID',
  `terminate_user_name` varchar(100) DEFAULT NULL COMMENT '终止人名称',
  `terminate_time` datetime DEFAULT NULL COMMENT '终止时间',
  `stop_desc` varchar(1000) DEFAULT NULL COMMENT '终止原因',
  `data_status` smallint(6) DEFAULT NULL COMMENT '安检状态:0 待提审    \r\n             1 待审核\r\n             2 待派工\r\n             3 待安检\r\n             4 安检中\r\n             5 异常\r\n             6 结单\r\n             7 终止',
  `security_check_process_state` smallint(6) DEFAULT NULL COMMENT '安检流程状态\r\n            0 安检提审\r\n            1 安检计划审核\r\n            2 安检派工\r\n            3 接单确认\r\n            4 预约安检时间\r\n            5 上门安检\r\n            6 待整改\r\n            7 已整改\r\n            8 结单\r\n            9 终止\r\n            ',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安检结果';

-- ----------------------------
-- Table structure for `gt_account_open_task_info`
-- ----------------------------
DROP TABLE IF EXISTS `gt_account_open_task_info`;
CREATE TABLE `gt_account_open_task_info` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `total` bigint(32) DEFAULT NULL COMMENT '合计户数',
  `success_total` bigint(32) DEFAULT NULL COMMENT '成功户数',
  `fail_total` bigint(32) DEFAULT NULL COMMENT '失败户数',
  `import_details` mediumtext COMMENT '导入明细',
  `status` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) DEFAULT NULL COMMENT '操作员ID',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `gt_adjust_price_record`
-- ----------------------------
DROP TABLE IF EXISTS `gt_adjust_price_record`;
CREATE TABLE `gt_adjust_price_record` (
  `ID` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `customer_type_code` bigint(32) DEFAULT NULL COMMENT '客户类型编码',
  `customer_type_name` varchar(100) DEFAULT NULL COMMENT '客户类型名称',
  `compensation_no` varchar(32) DEFAULT NULL COMMENT '调价补差编号',
  `gasmeter_code` varchar(32) DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(100) DEFAULT NULL COMMENT '用气类型名称',
  `gas_meter_type_code` varchar(32) DEFAULT NULL COMMENT '普表\r\n            卡表\r\n            物联网表\r\n            流量计(普表)\r\n            流量计(卡表）\r\n            流量计(物联网表）',
  `gas_meter_type_name` varchar(30) DEFAULT NULL,
  `compensation_price` decimal(18,4) DEFAULT NULL COMMENT '补差价',
  `compensation_gas` decimal(18,4) DEFAULT NULL COMMENT '补差量',
  `compensation_money` decimal(18,4) DEFAULT NULL COMMENT '补差金额',
  `accounting_user_id` bigint(32) DEFAULT NULL COMMENT '核算人ID',
  `accounting_user_name` varchar(100) DEFAULT NULL COMMENT '核算人名称',
  `review_user_id` bigint(32) DEFAULT NULL COMMENT '审批人ID',
  `review_user_name` varchar(100) DEFAULT NULL COMMENT '审批人名称',
  `review_time` datetime DEFAULT NULL COMMENT '审批时间',
  `review_objection` varchar(100) DEFAULT NULL COMMENT '审批意见',
  `accounting_time` datetime DEFAULT NULL COMMENT '核算时间',
  `charge_status` varchar(32) DEFAULT NULL COMMENT '收费状态\r\n            待收费\r\n            已收费\r\n            收费失败',
  `charge_time` datetime DEFAULT NULL COMMENT '收费时间',
  `charge_record_code` varchar(32) DEFAULT NULL COMMENT '收费记录编码',
  `data_status` varchar(32) DEFAULT NULL COMMENT '补差状态\r\n            已完成\r\n            待完成',
  `create_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(32) DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调价补差记录';

-- ----------------------------
-- Table structure for `gt_charge_insurance_detail`
-- ----------------------------
DROP TABLE IF EXISTS `gt_charge_insurance_detail`;
CREATE TABLE `gt_charge_insurance_detail` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_ball_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `charge_no` varchar(32) DEFAULT NULL COMMENT '缴费编号',
  `insurance_contract_no` varchar(32) DEFAULT NULL COMMENT '保险合同编号',
  `insurance_premium` decimal(18,4) DEFAULT NULL COMMENT '保险费',
  `insurance_end_date` date DEFAULT NULL COMMENT '保险到期时间',
  `status` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='保险明细';


-- ----------------------------
-- Table structure for `gt_charge_item_record`
-- ----------------------------
DROP TABLE IF EXISTS `gt_charge_item_record`;
CREATE TABLE `gt_charge_item_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` varchar(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `charge_item_name` varchar(100) DEFAULT NULL COMMENT '收费项名称',
  `charge_item_source_code` varchar(32) DEFAULT NULL COMMENT '收费项来源编码',
  `charge_item_source_name` varchar(100) DEFAULT NULL COMMENT '收费项来源名称',
  `charge_no` varchar(32) DEFAULT NULL COMMENT '缴费编号',
  `charge_item_source_id` varchar(32) DEFAULT NULL COMMENT '收费项来源ID',
  `charge_item_money` decimal(18,4) DEFAULT NULL COMMENT '收费项金额',
  `charge_item_gas` decimal(18,4) DEFAULT NULL COMMENT '收费项气量',
  `money_method` varchar(32) DEFAULT NULL COMMENT '金额方式\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            默认固定金额\r\n            ',
  `recharge_give_gas` decimal(18,4) DEFAULT NULL COMMENT '充值赠送气量',
  `recharge_give_money` decimal(18,4) DEFAULT NULL COMMENT '充值赠送金额',
  `is_reduction_item` tinyint(1) DEFAULT NULL COMMENT '是否是减免项',
  `create_user_id` bigint(32) DEFAULT NULL COMMENT '收费员ID',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '收费员名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注/理由',
  `data_status` smallint(6) DEFAULT NULL COMMENT '数据状态:\r\n            0: 正常\r\n            1: 作废',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='缴费项记录';

-- ----------------------------
-- Table structure for `gt_charge_record`
-- ----------------------------
DROP TABLE IF EXISTS `gt_charge_record`;
CREATE TABLE `gt_charge_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` varchar(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `charge_no` varchar(32) DEFAULT NULL COMMENT '缴费编号',
  `charge_method_code` varchar(32) DEFAULT NULL COMMENT '收费方式编码',
  `charge_method_name` varchar(50) DEFAULT NULL COMMENT '收费方式名称\r\n            现金\r\n            POS\r\n            转账\r\n            微信\r\n            支付宝',
  `payable_money` decimal(18,4) DEFAULT NULL COMMENT '应缴金额',
  `charge_money` decimal(18,4) DEFAULT NULL COMMENT '收费金额',
  `precharge_money` decimal(18,4) DEFAULT NULL COMMENT '预存金额',
  `recharge_money` decimal(18,4) DEFAULT NULL COMMENT '充值金额',
  `recharge_gas` decimal(18,4) DEFAULT NULL COMMENT '充值气量',
  `recharge_give_gas` decimal(18,4) DEFAULT NULL COMMENT '充值赠送气量',
  `recharge_give_money` decimal(18,4) DEFAULT NULL COMMENT '充值赠送金额',
  `reduction_money` decimal(18,4) DEFAULT NULL COMMENT '减免金额',
  `precharge_deduction_money` decimal(18,4) DEFAULT NULL COMMENT '预存抵扣',
  `receivable_money` decimal(18,4) DEFAULT NULL COMMENT '应收金额',
  `actual_income_money` decimal(18,4) DEFAULT NULL COMMENT '实收金额',
  `give_change` decimal(18,4) DEFAULT NULL COMMENT '找零',
  `insurance_contract_no` varchar(100) DEFAULT NULL COMMENT '保险合同编号',
  `insurance_premium` decimal(18,4) DEFAULT NULL COMMENT '保险费',
  `insurance_expire_date` date DEFAULT NULL COMMENT '保险到期日期',
  `give_deduction_money` decimal(18,4) DEFAULT NULL COMMENT '账户赠送抵扣',
  `give_book_pre_money` decimal(18,4) DEFAULT NULL COMMENT '赠送记账前余额',
  `give_book_after_money` decimal(18,4) DEFAULT NULL COMMENT '赠送记账后余额',
  `charge_pre_money` decimal(18,4) DEFAULT NULL COMMENT '账户收费前余额',
  `charge_after_money` decimal(18,4) DEFAULT NULL COMMENT '账户收费后余额',
  `charge_status` varchar(32) DEFAULT NULL COMMENT 'UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            CHARGE_FAILURE: 收费失败',
  `invoce_status` varchar(32) DEFAULT NULL COMMENT '开票状态\r\n            已开票\r\n            未开票',
  `invoice_type` varchar(32) DEFAULT NULL COMMENT '开票类型',
  `invoice_no` varchar(100) DEFAULT NULL COMMENT '发票编号',
  `create_user_id` bigint(32) DEFAULT NULL COMMENT '收费员ID',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '收费员名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注/理由',
  `data_status` smallint(6) DEFAULT NULL COMMENT '数据状态:\r\n            0: 正常\r\n            1: 作废',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='缴费记录';

-- ----------------------------
-- Table structure for `gt_charge_record_pay`
-- ----------------------------
DROP TABLE IF EXISTS `gt_charge_record_pay`;
CREATE TABLE `gt_charge_record_pay` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` varchar(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `fee_record_type` varchar(32) DEFAULT NULL COMMENT '费用记录类型\r\n            recharge充值\r\n            charge缴费\r\n            precharge预存',
  `fee_record_no` varchar(32) DEFAULT NULL COMMENT '费用记录编号：\r\n            充值记录编号\r\n            缴费记录编号\r\n            预存记录编号',
  `payment_type` varchar(32) DEFAULT NULL COMMENT '支付类型\r\n            支付宝\r\n            微信支付\r\n            现金\r\n            POS',
  `service_provider` varchar(32) DEFAULT NULL COMMENT '网络支付服务商\r\n            微信\r\n            支付宝',
  `payment_method` varchar(32) DEFAULT NULL COMMENT '网络支付方式\r\n            扫码支付\r\n            被扫扣款',
  `fee_record_money` decimal(18,4) DEFAULT NULL COMMENT '费用金额',
  `fee_record_description` varchar(100) DEFAULT NULL COMMENT '费用记录描述',
  `expiry` smallint(6) DEFAULT NULL COMMENT '订单有效期',
  `qr_code` varchar(1000) DEFAULT NULL COMMENT '二维码',
  `service_order_number` varchar(50) DEFAULT NULL COMMENT '服务商订单号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建人名称',
  `payment_status` varchar(32) DEFAULT NULL COMMENT '支付状态：\r\n            未发起支付\r\n            已发起支付\r\n            支付成功\r\n            支付失败',
  `payment_time` datetime DEFAULT NULL COMMENT '支付完成时间',
  `payment_result` varchar(100) DEFAULT NULL COMMENT '支付结果\r\n            返回内容记录',
  `proof_status` smallint(6) DEFAULT NULL COMMENT '对账状态\r\n            未支付成功\r\n            待对账\r\n            已对账',
  `proof_time` datetime DEFAULT NULL COMMENT '对账时间',
  `proof_result` varchar(100) DEFAULT NULL COMMENT '对账结果\r\n            对账通过\r\n            差异',
  `manual_proof_usre_id` bigint(32) DEFAULT NULL COMMENT '人工校对人ID',
  `manual_proof_usre_name` varchar(100) DEFAULT NULL COMMENT '人工校对人名称',
  `update_user` bigint(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方支付记录明细';

-- ----------------------------
-- Table structure for `gt_charge_reduction_detail_record`
-- ----------------------------
DROP TABLE IF EXISTS `gt_charge_reduction_detail_record`;
CREATE TABLE `gt_charge_reduction_detail_record` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `charge_no` varchar(32) DEFAULT NULL COMMENT '缴费编号',
  `activity_id` bigint(32) DEFAULT NULL COMMENT '活动ID',
  `activity_name` varchar(100) DEFAULT NULL COMMENT '活动名称',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `give_gas` decimal(18,4) DEFAULT NULL COMMENT '赠送气量（充值场景才能设置）',
  `activity_money_type` smallint(6) DEFAULT NULL COMMENT '活动金额方式（缴费场景无百分比选项）\r\n            1.百分比  （百分比，金额不可用）\r\n            2.固定金额\r\n            3.不固定金额',
  `activity_money` decimal(18,4) DEFAULT NULL COMMENT '活动金额',
  `activity_percent` decimal(18,4) DEFAULT NULL COMMENT '活动比例',
  `activity_scene` varchar(32) DEFAULT NULL COMMENT '活动场景\r\n            RECHARGE: 充值赠送\r\n            PRECHARGE: 预存赠送\r\n            CHARGE: 缴费减免',
  `use_gas_types` varchar(32) DEFAULT NULL COMMENT '用气类型(只针对充值场景)\r\n            存放jsonarray数据 包含用气类型id和用气类型名称。',
  `actual_reduction_money` decimal(18,4) DEFAULT NULL COMMENT '实际减免金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='缴费减免活动项明细记录';

-- ----------------------------
-- Table structure for `gt_customer_account`
-- ----------------------------
DROP TABLE IF EXISTS `gt_customer_account`;
CREATE TABLE `gt_customer_account` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` varchar(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `account_code` varchar(32) DEFAULT NULL COMMENT '账户号',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(32) DEFAULT NULL COMMENT '客户名称',
  `account_money` decimal(18,4) DEFAULT NULL COMMENT '账户金额',
  `give_money` decimal(18,4) DEFAULT NULL COMMENT '赠送金额',
  `settlement_lock` smallint(2) DEFAULT NULL COMMENT '结算锁\r\n            1 结算中已锁\r\n            0 未锁定',
  `status` smallint(2) DEFAULT NULL COMMENT '账户状态\r\n            0 未激活\r\n            1 激活\r\n            2 冻结',
  `delete_status` smallint(6) DEFAULT NULL COMMENT '删除状态\r\n            1 删除\r\n            0 正常',
  `create_user` bigint(32) DEFAULT NULL COMMENT '操作员ID',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户表';

-- ----------------------------
-- Table structure for `gt_customer_account_serial`
-- ----------------------------
DROP TABLE IF EXISTS `gt_customer_account_serial`;
CREATE TABLE `gt_customer_account_serial` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` varchar(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `account_code` varchar(32) DEFAULT NULL COMMENT '账户号',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `serial_no` varchar(32) DEFAULT NULL COMMENT '流水号',
  `bill_no` varchar(32) DEFAULT NULL COMMENT '记费记录号( 根据各个场景记录不同的编号)\r\n            例如：收费扣款或者收费预存存放 对应单号。',
  `bill_type` varchar(32) DEFAULT NULL COMMENT '收入支出类型：\r\n            缴费找零预存\r\n            充值找零预存\r\n            缴费预存抵扣\r\n            充值预存抵扣\r\n            预存\r\n            ',
  `book_money` decimal(18,4) DEFAULT NULL COMMENT '记账金额',
  `book_pre_money` decimal(18,4) DEFAULT NULL COMMENT '记账前余额',
  `book_after_money` decimal(18,4) DEFAULT NULL COMMENT '记账后余额',
  `give_money` decimal(18,4) DEFAULT NULL COMMENT '赠送金额',
  `give_deduction_money` decimal(18,4) DEFAULT NULL COMMENT '赠送抵扣金额',
  `give_book_pre_money` decimal(18,4) DEFAULT NULL COMMENT '赠送记账前余额',
  `give_back_after_money` decimal(18,4) DEFAULT NULL COMMENT '赠送记账后余额',
  `remark` varchar(400) DEFAULT NULL COMMENT '流水说明',
  `create_user` bigint(32) DEFAULT NULL COMMENT '记账人ID',
  `create_time` datetime DEFAULT NULL COMMENT '记账时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户流水';


-- ----------------------------
-- Table structure for `gt_customer_enjoy_activity_record`
-- ----------------------------
DROP TABLE IF EXISTS `gt_customer_enjoy_activity_record`;
CREATE TABLE `gt_customer_enjoy_activity_record` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `charge_no` varchar(32) DEFAULT NULL COMMENT '缴费编号',
  `activity_id` bigint(32) DEFAULT NULL COMMENT '活动ID',
  `activity_name` varchar(100) DEFAULT NULL COMMENT '活动名称',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `give_gas` decimal(18,4) DEFAULT NULL COMMENT '赠送气量（充值场景才能设置）',
  `activity_money_type` varchar(32) DEFAULT NULL COMMENT '活动金额方式（缴费场景无百分比选项）\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            percent: 百分比',
  `activity_money` decimal(18,4) DEFAULT NULL COMMENT '活动金额',
  `activity_percent` decimal(18,4) DEFAULT NULL COMMENT '活动比例',
  `activity_scene` varchar(32) DEFAULT NULL COMMENT '活动场景\r\n            RECHARGE: 充值赠送\r\n            PRECHARGE: 预存赠送\r\n            CHARGE: 缴费减免',
  `use_gas_types` varchar(32) DEFAULT NULL COMMENT '用气类型(只针对充值场景)\r\n            存放jsonarray数据 包含用气类型id和用气类型名称。',
  `total_money` decimal(18,4) DEFAULT NULL COMMENT '合计金额',
  `total_gas` decimal(18,4) DEFAULT NULL COMMENT '合计气量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户享受活动明细记录';

-- ----------------------------
-- Table structure for `gt_customer_scene_charge_order`
-- ----------------------------
DROP TABLE IF EXISTS `gt_customer_scene_charge_order`;
CREATE TABLE `gt_customer_scene_charge_order` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(100) DEFAULT NULL COMMENT '用气类型名称',
  `scene_charge_no` varchar(32) DEFAULT NULL COMMENT '场景费用单编号',
  `charge_no` varchar(32) DEFAULT NULL COMMENT '缴费编号',
  `charge_item_id` bigint(32) DEFAULT NULL COMMENT '收费项ID',
  `sys_item_code` varchar(32) DEFAULT NULL COMMENT '系统收费项编码',
  `item_name` varchar(100) DEFAULT NULL COMMENT '收费项名称',
  `business_no` varchar(32) DEFAULT NULL COMMENT '业务编号',
  `charge_money` decimal(18,4) DEFAULT NULL COMMENT '缴费金额',
  `discount_money` decimal(18,4) DEFAULT NULL COMMENT '优惠金额',
  `total_money` decimal(18,4) DEFAULT NULL COMMENT '合计金额',
  `charge_status` varchar(32) DEFAULT NULL COMMENT '收费状态：\r\n            UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            CHARGE_FAILURE: 收费失败',
  `data_status` smallint(6) DEFAULT NULL COMMENT '数据状态:\r\n            0: 正常\r\n            1: 作废',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户场景费用单';

-- ----------------------------
-- Table structure for `gt_gasmeter_arrears_detail`
-- ----------------------------
DROP TABLE IF EXISTS `gt_gasmeter_arrears_detail`;
CREATE TABLE `gt_gasmeter_arrears_detail` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `settlement_no` varchar(32) DEFAULT NULL COMMENT '结算明细编号',
  `readmeter_data_id` bigint(32) DEFAULT NULL COMMENT '抄表数据ID',
  `readmeter_month` varchar(32) DEFAULT NULL COMMENT '抄表月',
  `population_float_money` decimal(18,4) DEFAULT NULL COMMENT '人口浮动金额',
  `gas` decimal(18,4) DEFAULT NULL COMMENT '用气量',
  `gas_money` decimal(18,4) DEFAULT NULL COMMENT '用气金额',
  `discount_money` decimal(18,4) DEFAULT NULL COMMENT '优惠金额',
  `give_deduction_money` decimal(18,4) DEFAULT NULL COMMENT '赠送抵扣',
  `precharge_deduction_money` decimal(18,4) DEFAULT NULL COMMENT '预存抵扣',
  `give_book_pre_money` decimal(18,4) DEFAULT NULL COMMENT '赠送记账前余额',
  `give_back_after_money` decimal(18,4) DEFAULT NULL COMMENT '赠送记账后余额',
  `settlement_pre_money` decimal(18,4) DEFAULT NULL COMMENT '结算前余额',
  `settlement_after_money` decimal(18,4) DEFAULT NULL COMMENT '结算后余额',
  `arrears_money` decimal(18,4) DEFAULT NULL COMMENT '欠费金额',
  `billing_date` date DEFAULT NULL COMMENT '计费日期',
  `late_fee_start_date` date DEFAULT NULL COMMENT '滞纳金开始日期',
  `latepay_days` bigint(20) DEFAULT NULL COMMENT '滞纳天数',
  `latepay_amount` decimal(18,4) DEFAULT NULL COMMENT '滞纳金',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(100) DEFAULT NULL COMMENT '用气类型名称',
  `price` decimal(18,4) DEFAULT NULL COMMENT '单价',
  `ladder_charge_mode_id` bigint(32) DEFAULT NULL COMMENT '阶梯计费方式ID',
  `ladder_charge_mode_name` varchar(32) DEFAULT NULL COMMENT '阶梯计费方式名称',
  `gas_1` int(11) DEFAULT NULL,
  `price_1` decimal(18,4) DEFAULT NULL,
  `money_1` decimal(18,4) DEFAULT NULL,
  `gas_2` int(11) DEFAULT NULL,
  `price_2` decimal(18,4) DEFAULT NULL,
  `money_2` decimal(18,4) DEFAULT NULL,
  `gas_3` int(11) DEFAULT NULL,
  `price_3` decimal(18,4) DEFAULT NULL,
  `money_3` decimal(18,4) DEFAULT NULL,
  `gas_4` int(11) DEFAULT NULL,
  `price_4` decimal(18,4) DEFAULT NULL,
  `money_4` decimal(18,4) DEFAULT NULL,
  `gas_5` int(11) DEFAULT NULL,
  `price_5` decimal(18,4) DEFAULT NULL,
  `money_5` decimal(18,4) DEFAULT NULL,
  `gas_6` int(11) DEFAULT NULL,
  `price_6` decimal(18,4) DEFAULT NULL,
  `money_6` decimal(18,4) DEFAULT NULL,
  `arrears_status` varchar(32) DEFAULT NULL COMMENT '欠费状态\r\n            UNCHARGE:待收费\r\n            CHARGED: 完结',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表欠费明细';

-- ----------------------------
-- Table structure for `gt_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `gt_invoice`;
CREATE TABLE `gt_invoice` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `invoice_number` varchar(30) DEFAULT NULL COMMENT '发票编号',
  `pay_no` varchar(32) DEFAULT NULL COMMENT '收费编号',
  `invoice_type` varchar(30) DEFAULT NULL COMMENT '发票类型\r\n            007普票\r\n            004专票\r\n            026电票',
  `invoice_kind` char(10) DEFAULT NULL COMMENT '发票种类\r\n            0红票\r\n            1蓝票\r\n            2废票',
  `billing_date` datetime DEFAULT NULL COMMENT '开票日期',
  `invoice_code` varchar(32) DEFAULT NULL COMMENT '发票代码',
  `invoice_no` varchar(32) DEFAULT NULL COMMENT '发票号码',
  `buyer_name` varchar(100) DEFAULT NULL COMMENT '购买方名称',
  `buyer_tin_no` varchar(20) DEFAULT NULL COMMENT '购买方纳税人识别号',
  `buyer_addr_phone` varchar(100) DEFAULT NULL COMMENT '购买方地址、电话',
  `buyer_bank_account` varchar(100) DEFAULT NULL COMMENT '购买方开户行及账号',
  `seller_name` varchar(100) DEFAULT NULL COMMENT '销售方名称',
  `seller_tin_no` varchar(20) DEFAULT NULL COMMENT '销售方纳税人识别号',
  `seller_addr_phone` varchar(100) DEFAULT NULL COMMENT '销售方地址、电话',
  `seller_bank_account` varchar(100) DEFAULT NULL COMMENT '销售方开户行及账户',
  `total_amount` decimal(18,4) DEFAULT NULL COMMENT '合计金额',
  `total_tax` decimal(18,4) DEFAULT NULL COMMENT '合计税额',
  `price_tax_total_upper` varchar(32) DEFAULT NULL COMMENT '价税合计（大写）',
  `price_tax_total_lower` decimal(18,4) DEFAULT NULL COMMENT '价税合计（小写）',
  `payee_id` bigint(32) DEFAULT NULL COMMENT '收款人',
  `payee_name` varchar(100) DEFAULT NULL COMMENT '收款人名称',
  `reviewer_id` bigint(32) DEFAULT NULL COMMENT '复核人',
  `reviewer_name` varchar(100) DEFAULT NULL COMMENT '复核人名称',
  `drawer_id` bigint(32) DEFAULT NULL COMMENT '开票人',
  `drawer_name` varchar(100) DEFAULT NULL COMMENT '开票人名称',
  `telphone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `push_method` varchar(30) DEFAULT NULL COMMENT '电子票推送方式\r\n            0短信\r\n            1邮件\r\n            2微信',
  `void_user_id` char(32) DEFAULT NULL COMMENT '作废人ID',
  `void_user_name` varchar(100) DEFAULT NULL COMMENT '作废人名称',
  `red_user_id` char(32) DEFAULT NULL COMMENT '冲红人ID',
  `red_user_name` varchar(100) DEFAULT NULL COMMENT '冲红人名称',
  `invoice_file_url` varchar(1000) DEFAULT NULL COMMENT '发票文件地址',
  `invoice_serial_number` varchar(30) DEFAULT NULL COMMENT '开票流水号',
  `red_request_number` varchar(30) DEFAULT NULL COMMENT '专票冲红申请号',
  `blue_order_number` varchar(30) DEFAULT NULL COMMENT '蓝票订单号',
  `blue_invoice_number` varchar(30) DEFAULT NULL COMMENT '蓝票编号',
  `blue__serial_number` varchar(30) DEFAULT NULL COMMENT '蓝票开票流水号',
  `invoice_status` varchar(30) DEFAULT NULL COMMENT '开票状态\r\n            0未开票\r\n            1已开票\r\n            2作废',
  `invoice_result` varchar(100) DEFAULT NULL COMMENT '开票结果',
  `print_times` smallint(6) DEFAULT NULL COMMENT '打印次数',
  `data_status` smallint(6) DEFAULT NULL COMMENT '数据状态',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `gt_invoice_details`
-- ----------------------------
DROP TABLE IF EXISTS `gt_invoice_details`;
CREATE TABLE `gt_invoice_details` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `invoice_id` char(32) DEFAULT NULL COMMENT '发票ID',
  `goods_code` varchar(32) DEFAULT NULL COMMENT '商品代码',
  `goods_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `specification_model` varchar(32) DEFAULT NULL COMMENT '规格型号',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `number` varchar(32) DEFAULT NULL COMMENT '数量',
  `price` decimal(18,4) DEFAULT NULL COMMENT '单价',
  `money` decimal(18,4) DEFAULT NULL COMMENT '金额',
  `tax_rate` decimal(18,4) DEFAULT NULL COMMENT '税率',
  `tax_money` decimal(18,4) DEFAULT NULL COMMENT '税额',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `gt_precharge_record`
-- ----------------------------
DROP TABLE IF EXISTS `gt_precharge_record`;
CREATE TABLE `gt_precharge_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` varchar(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `precharge_no` varchar(32) DEFAULT NULL COMMENT '预存编号',
  `give_money` decimal(18,4) DEFAULT NULL COMMENT '赠送金额',
  `precharge_money` decimal(18,4) DEFAULT NULL COMMENT '预存金额',
  `actual_income_money` decimal(18,4) DEFAULT NULL COMMENT '实收金额',
  `receivable_money` decimal(18,4) DEFAULT NULL COMMENT '应收费金额',
  `charge_pre_money` decimal(18,4) DEFAULT NULL COMMENT '账户收费前余额',
  `charge_after_money` decimal(18,4) DEFAULT NULL COMMENT '账户收费后余额',
  `give_book_pre_money` decimal(18,4) DEFAULT NULL COMMENT '赠送记账前余额',
  `give_book_after_money` decimal(18,4) DEFAULT NULL COMMENT '赠送记账后余额',
  `give_change` decimal(18,4) DEFAULT NULL COMMENT '找零',
  `charge_method_code` varchar(32) DEFAULT NULL COMMENT '收费方式编码',
  `charge_method_name` varchar(50) DEFAULT NULL COMMENT '收费方式名称\r\n            现金\r\n            POS\r\n            转账\r\n            微信\r\n            支付宝',
  `charge_status` varchar(32) DEFAULT NULL COMMENT '收费状态：\r\n            UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            CHARGE_FAILURE: 收费失败',
  `remark` varchar(100) DEFAULT NULL,
  `data_status` smallint(6) DEFAULT NULL COMMENT '数据状态:\r\n            0: 正常\r\n            1: 作废',
  `create_user_id` bigint(32) DEFAULT NULL COMMENT '收费员ID',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '收费员名称',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预存记录';

-- ----------------------------
-- Table structure for `gt_receipt`
-- ----------------------------
DROP TABLE IF EXISTS `gt_receipt`;
CREATE TABLE `gt_receipt` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` char(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `receipt_no` varchar(32) DEFAULT NULL COMMENT '票据编号',
  `receipt_type` smallint(6) DEFAULT NULL COMMENT '票据类型\r\n            0充值\r\n            1缴费\r\n            2预存',
  `pay_no` varchar(32) DEFAULT NULL COMMENT '收费编号',
  `pay_type` smallint(6) DEFAULT NULL COMMENT '收费类型\r\n            0现金\r\n            1支付宝\r\n            2微信\r\n            3POS',
  `pay_time` datetime DEFAULT NULL COMMENT '收费时间',
  `charge_amount_total_lowercase` decimal(18,4) DEFAULT NULL COMMENT '收费项目合计（小写）',
  `charge_amount_total_uppercase` char(32) DEFAULT NULL COMMENT '收费项目合计（大写）',
  `recharge_give_gas` varchar(32) DEFAULT NULL COMMENT '充值活动赠送气量',
  `discount_amount` decimal(18,4) DEFAULT NULL COMMENT '充值活动赠送金额',
  `should_pay` decimal(18,4) DEFAULT NULL COMMENT '应收金额',
  `make_collections` decimal(18,4) DEFAULT NULL COMMENT '收款',
  `actual_collection` decimal(18,4) DEFAULT NULL COMMENT '实收金额',
  `give_change` decimal(18,4) DEFAULT NULL COMMENT '找零金额',
  `pre_deposit` decimal(18,4) DEFAULT NULL COMMENT '零存',
  `customer_no` varchar(100) DEFAULT NULL COMMENT '客户编号',
  `customer_name` char(32) DEFAULT NULL COMMENT '客户名称',
  `customer_address` varchar(100) DEFAULT NULL COMMENT '客户地址',
  `customer_phone` varchar(1000) DEFAULT NULL COMMENT '客户电话',
  `operator_no` varchar(30) DEFAULT NULL COMMENT '操作员编号',
  `operator_name` varchar(30) DEFAULT NULL COMMENT '操作员姓名',
  `buyer_name` varchar(100) DEFAULT NULL COMMENT '购买方名称',
  `buyer_tin_no` varchar(30) DEFAULT NULL COMMENT '购买方纳税人识别号',
  `buyer_addr_phone` varchar(120) DEFAULT NULL COMMENT '购买方地址、电话',
  `buyer_bank_account` varchar(100) DEFAULT NULL COMMENT '购买方开户行及账号',
  `deduct_amount` decimal(18,4) DEFAULT NULL COMMENT '抵扣金额',
  `recharge_amount` decimal(18,4) DEFAULT NULL COMMENT '充值金额',
  `recharge_gas_volume` varchar(32) DEFAULT NULL COMMENT '充值气量',
  `predeposit_amount` decimal(18,4) DEFAULT NULL COMMENT '预存金额',
  `premium` decimal(18,4) DEFAULT NULL COMMENT '保费',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `data_status` smallint(6) DEFAULT NULL COMMENT '数据状态',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `gt_receipt_detail`
-- ----------------------------
DROP TABLE IF EXISTS `gt_receipt_detail`;
CREATE TABLE `gt_receipt_detail` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `receipt_id` char(32) DEFAULT NULL COMMENT '票据ID',
  `goods_id` varchar(32) DEFAULT NULL COMMENT '商品名称',
  `goods_name` varchar(100) DEFAULT NULL COMMENT '商品代码\r\n            0充值\r\n            1燃气收费\r\n            2商品销售\r\n            3场景收费\r\n            4附加费\r\n            5保险费\r\n            ',
  `specification_model` varchar(20) DEFAULT NULL COMMENT '规格型号',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `number` varchar(32) DEFAULT NULL COMMENT '数量',
  `price` decimal(18,4) DEFAULT NULL COMMENT '单价',
  `money` decimal(18,4) DEFAULT NULL COMMENT '金额',
  `tax_rate` decimal(18,4) DEFAULT NULL COMMENT '税率',
  `tax_money` decimal(18,4) DEFAULT NULL COMMENT '税额',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `gt_receipt_invoice_association`
-- ----------------------------
DROP TABLE IF EXISTS `gt_receipt_invoice_association`;
CREATE TABLE `gt_receipt_invoice_association` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `receipt_id` bigint(32) DEFAULT NULL COMMENT '票据ID',
  `invoice_id` bigint(32) DEFAULT NULL COMMENT '发票ID',
  `state` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `gt_recharge_record`
-- ----------------------------
DROP TABLE IF EXISTS `gt_recharge_record`;
CREATE TABLE `gt_recharge_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` varchar(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `charge_no` varchar(32) DEFAULT NULL COMMENT '缴费编号',
  `gasmeter_code` varchar(32) DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `gas_meter_type_code` varchar(32) DEFAULT NULL COMMENT '气表类型编码',
  `gas_meter_type_name` varchar(100) DEFAULT NULL COMMENT '气表类型名称',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(100) DEFAULT NULL COMMENT '用气类型名称',
  `recharge_money` decimal(18,4) DEFAULT NULL COMMENT '充值金额',
  `recharge_gas` decimal(18,4) DEFAULT NULL COMMENT '充值气量',
  `recharge_give_gas` decimal(18,4) DEFAULT NULL COMMENT '充值赠送气量',
  `recharge_give_money` decimal(18,4) DEFAULT NULL COMMENT '充值赠送金额',
  `total_money` decimal(18,4) DEFAULT NULL COMMENT '合计金额',
  `total_gas` decimal(18,4) DEFAULT NULL COMMENT '合计气量',
  `money_flow_status` varchar(32) DEFAULT NULL COMMENT '金额流转状态\r\n            waite_isuue: 待下发\r\n            waite_write_card: 待写卡\r\n            success:  下发成功 或 上卡成功\r\n            failure：写卡失败 或 下发失败',
  `charge_status` varchar(32) DEFAULT NULL COMMENT '收费状态：\r\n            UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            REFUND: 已退费\r\n            CHARGE_FAILURE: 收费失败',
  `remark` varchar(100) DEFAULT NULL,
  `data_status` smallint(6) DEFAULT NULL COMMENT '数据状态:\r\n            0: 正常\r\n            1: 作废',
  `create_user_id` bigint(32) DEFAULT NULL COMMENT '收费员ID',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '收费员名称',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值记录';

-- ----------------------------
-- Table structure for `gt_seller_taxpayer_info`
-- ----------------------------
DROP TABLE IF EXISTS `gt_seller_taxpayer_info`;
CREATE TABLE `gt_seller_taxpayer_info` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `seller_name` varchar(100) DEFAULT NULL COMMENT '销售方名称',
  `seller_tin_no` varchar(20) DEFAULT NULL COMMENT '销售方纳税人识别号',
  `seller_addr_phone` varchar(100) DEFAULT NULL COMMENT '销售方地址、电话',
  `seller_bank_account` varchar(100) DEFAULT NULL COMMENT '销售方开户行及账户',
  `state` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `gt_toll_item_charge_record`
-- ----------------------------
DROP TABLE IF EXISTS `gt_toll_item_charge_record`;
CREATE TABLE `gt_toll_item_charge_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `compan_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `customer_code` varchar(32) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `gasmeter_code` varchar(32) DEFAULT NULL COMMENT '气表编号',
  `gasmeter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `charge_no` varchar(32) DEFAULT NULL COMMENT '缴费编号',
  `sys_item_code` varchar(32) DEFAULT NULL COMMENT '系统项编码',
  `toll_item_id` bigint(32) NOT NULL COMMENT '收费项ID',
  `item_name` varchar(100) DEFAULT NULL COMMENT '收费项名称',
  `charge_frequency` varchar(32) DEFAULT NULL COMMENT '收费频次\r\n            ON_DEMAND:按需\r\n            ONE_TIME：一次性\r\n            BY_MONTH：按月\r\n            QUARTERLY：按季\r\n            BY_YEAR：按年',
  `charge_period` smallint(6) DEFAULT NULL COMMENT '收费期限',
  `cycle_value` smallint(6) DEFAULT NULL COMMENT '周期值,固定1',
  `money` decimal(18,4) DEFAULT NULL COMMENT '金额',
  `money_method` varchar(32) DEFAULT NULL COMMENT '金额方式\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            默认固定金额\r\n            ',
  `financial_subject` varchar(32) DEFAULT NULL COMMENT '财务科目',
  `vat_general_rate` decimal(18,4) DEFAULT NULL COMMENT '增值税普税税率',
  `tax_category_code` varchar(100) DEFAULT NULL COMMENT '税收分类编码',
  `favoured_status` varchar(100) DEFAULT NULL COMMENT '是否享受优惠',
  `favoured_policy` varchar(100) DEFAULT NULL COMMENT '优惠政策',
  `zero_tax_status` varchar(100) DEFAULT NULL COMMENT '零税率标识',
  `custom_code` varchar(100) DEFAULT NULL COMMENT '企业自编码',
  `tax_deduction_money` decimal(18,4) DEFAULT NULL COMMENT '税扣除额',
  `code_version` varchar(100) DEFAULT NULL COMMENT '编码版本号',
  `scene_code` varchar(32) DEFAULT NULL COMMENT '场景编码',
  `scene_name` varchar(100) DEFAULT NULL COMMENT '场景名称',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `use_gas_types` varchar(1000) DEFAULT NULL COMMENT '用气类型(多个)(可以json，可以分割字符窜)',
  `total_end_time` date DEFAULT NULL COMMENT '累积结束日期',
  `total_start_time` date DEFAULT NULL COMMENT '累积开始日期',
  `total_cycle_count` smallint(6) DEFAULT NULL COMMENT '累积周期数量',
  `total_cycle_money` decimal(18,4) DEFAULT NULL COMMENT '累积周期金额',
  `total_charge_money` decimal(18,4) DEFAULT NULL COMMENT '合计缴费金额',
  `charge_status` varchar(32) DEFAULT NULL COMMENT '收费状态：\r\n            UNCHARGE: 待收费\r\n            CHARGED: 已收费\r\n            CHARGE_FAILURE: 收费失败',
  `charge_time` datetime DEFAULT NULL COMMENT '缴费日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收费项缴费记录';

-- ----------------------------
-- Table structure for `gt_toll_item_cycle_last_charge_record`
-- ----------------------------
DROP TABLE IF EXISTS `gt_toll_item_cycle_last_charge_record`;
CREATE TABLE `gt_toll_item_cycle_last_charge_record` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `compan_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `gasmeter_code` varchar(32) DEFAULT NULL COMMENT '气表编号',
  `toll_item_id` bigint(32) NOT NULL COMMENT '收费项ID',
  `billing_deadline` date DEFAULT NULL COMMENT '计费截至日期',
  `charge_time` datetime DEFAULT NULL COMMENT '缴费时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='周期收费项最后一次缴费';

-- ----------------------------
-- Table structure for `m_order`
-- ----------------------------
DROP TABLE IF EXISTS `m_order`;
CREATE TABLE `m_order` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `code` varchar(255) DEFAULT NULL COMMENT '编号',
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单(用于测试)';

-- ----------------------------
-- Table structure for `m_product`
-- ----------------------------
DROP TABLE IF EXISTS `m_product`;
CREATE TABLE `m_product` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `stock` int(11) DEFAULT NULL COMMENT '库存',
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `type_` text COMMENT '商品类型\n#ProductType{ordinary:普通;gift:赠品}',
  `type2` longtext COMMENT '商品类型2\n#{ordinary:普通;gift:赠品;}',
  `type3` varchar(255) DEFAULT NULL COMMENT '学历\n@InjectionField(api = DICTIONARY_ITEM_FEIGN_CLASS, method = DICTIONARY_ITEM_METHOD) RemoteData<String, String>\n',
  `status` bit(1) DEFAULT NULL COMMENT '状态',
  `test4` tinyint(10) DEFAULT NULL,
  `test5` date DEFAULT NULL COMMENT '时间',
  `test6` datetime DEFAULT NULL COMMENT '日期',
  `parent_id` bigint(20) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL COMMENT '名称',
  `sort_value` int(11) DEFAULT NULL,
  `test7` char(10) DEFAULT NULL COMMENT 'xxx\n@InjectionField(api = “userApi”, method = USER_ID_NAME_METHOD) RemoteData<Long, String>',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户\n@InjectionField(api = USER_ID_FEIGN_CLASS, method = USER_ID_NAME_METHOD) RemoteData<Long, String>',
  `org_id` bigint(20) DEFAULT NULL COMMENT '组织\n@InjectionField(api = ORG_ID_FEIGN_CLASS, method = "findOrgNameByIds") RemoteData<Long, String>',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品(用于测试)';

-- ----------------------------
-- Table structure for `m_wx_app`
-- ----------------------------
DROP TABLE IF EXISTS `m_wx_app`;
CREATE TABLE `m_wx_app` (
  `id` varchar(50) NOT NULL COMMENT '微信公众号号唯一标识',
  `app_id` varchar(50) DEFAULT NULL COMMENT '微信公众号appid',
  `app_secret` varchar(50) DEFAULT NULL COMMENT '微信公众号appsecret',
  `token` varchar(50) DEFAULT NULL COMMENT '微信公众号token',
  `pay_mach_id` varchar(50) DEFAULT NULL COMMENT '微信支付商户号',
  `pay_key` varchar(50) DEFAULT NULL COMMENT '微信支付key',
  `notify_url` varchar(200) DEFAULT NULL COMMENT '微信支付回调url',
  `pay_sslcert_path` varchar(500) DEFAULT NULL COMMENT '证书地址',
  `pay_sslcert_key` varchar(500) DEFAULT NULL COMMENT '证书密钥',
  `notes` varchar(100) DEFAULT NULL COMMENT '公众号备注',
  `pay_ip` varchar(50) DEFAULT NULL COMMENT '支付客户ip',
  `fun_id` varchar(100) DEFAULT NULL COMMENT '功能支持',
  `channel` int(11) DEFAULT NULL COMMENT 'APP渠道(1:微信公众平台 , 2:微信开放平台)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `m_wx_pay`
-- ----------------------------
DROP TABLE IF EXISTS `m_wx_pay`;
CREATE TABLE `m_wx_pay` (
  `id` varchar(50) NOT NULL COMMENT '微信交易记录唯一标识',
  `pay_notes` varchar(50) DEFAULT NULL COMMENT '支付备注',
  `pay` double(10,0) DEFAULT NULL COMMENT '支付金额',
  `pay_openId` varchar(50) DEFAULT NULL COMMENT '支付人唯一标识',
  `pay_state` int(11) DEFAULT NULL COMMENT '支付状态（1：已支付，0：待支付）',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `notify` int(11) DEFAULT NULL COMMENT '通知(1:已通知,0:未通知)',
  `notify_conten` varchar(500) DEFAULT NULL COMMENT '通知内容',
  `create_time` datetime DEFAULT NULL,
  `isSandbox` smallint(6) DEFAULT '0' COMMENT '是否沙箱环境账号',
  `auth_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `mail_provider`
-- ----------------------------
DROP TABLE IF EXISTS `mail_provider`;
CREATE TABLE `mail_provider` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ID',
  `mail_type` varchar(10) DEFAULT 'TENCENT' COMMENT '邮箱类型\n#MailType{SINA:新浪;QQ:腾讯;WY163:网易}',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱授权码',
  `host` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '主机',
  `port` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '端口',
  `protocol` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '协议',
  `auth` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否进行用户名密码校验',
  `name` varchar(60) DEFAULT NULL COMMENT '名称',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `properties` varchar(500) DEFAULT NULL COMMENT '属性',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='邮件供应商';

-- ----------------------------
-- Table structure for `mail_send_status`
-- ----------------------------
DROP TABLE IF EXISTS `mail_send_status`;
CREATE TABLE `mail_send_status` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务id\n#mail_task',
  `email` varchar(64) NOT NULL COMMENT '收件邮箱',
  `mail_status` varchar(255) NOT NULL DEFAULT 'UNREAD' COMMENT '邮件状态\r\n#MailStatus{UNREAD:未读;READ:已读;DELETED:已删除;ABNORMAL:异常;VIRUSES:病毒;TRASH:垃圾}',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='邮件发送状态';

-- ----------------------------
-- Table structure for `mail_task`
-- ----------------------------
DROP TABLE IF EXISTS `mail_task`;
CREATE TABLE `mail_task` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ID',
  `status` varchar(10) DEFAULT 'WAITING' COMMENT '执行状态\r\n#TaskStatus{WAITING:等待执行;SUCCESS:执行成功;FAIL:执行失败}',
  `provider_id` bigint(20) DEFAULT NULL COMMENT '发件人id\n#mail_provider',
  `to` varchar(500) DEFAULT '' COMMENT '收件人\n多个,号分割',
  `cc` varchar(255) DEFAULT '' COMMENT '抄送人\n多个,分割',
  `bcc` varchar(255) DEFAULT '' COMMENT '密送人\n多个,分割',
  `subject` varchar(255) DEFAULT '' COMMENT '主题',
  `body` text CHARACTER SET utf8 COMMENT '正文',
  `err_msg` varchar(500) DEFAULT '' COMMENT '发送失败原因',
  `sender_code` varchar(64) DEFAULT '' COMMENT '发送商编码',
  `plan_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '计划发送时间\n(默认当前时间，可定时发送)',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='邮件任务';

-- ----------------------------
-- Table structure for `msgs_center_info`
-- ----------------------------
DROP TABLE IF EXISTS `msgs_center_info`;
CREATE TABLE `msgs_center_info` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `biz_id` varchar(64) DEFAULT NULL COMMENT '业务ID\n业务表的唯一id',
  `biz_type` varchar(64) DEFAULT NULL COMMENT '业务类型\n#MsgsBizType{USER_LOCK:账号锁定;USER_REG:账号申请;WORK_APPROVAL:考勤审批;}',
  `msgs_center_type` varchar(20) NOT NULL DEFAULT 'NOTIFY' COMMENT '消息类型\n#MsgsCenterType{WAIT:待办;NOTIFY:通知;PUBLICITY:公告;WARN:预警;}',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `content` text COMMENT '内容',
  `author` varchar(50) DEFAULT '' COMMENT '发布人',
  `handler_url` varchar(255) DEFAULT '' COMMENT '处理地址\n以http开头时直接跳转，否则与#c_application表拼接后跳转\nhttp可带参数',
  `handler_params` varchar(400) DEFAULT '' COMMENT '处理参数',
  `is_single_handle` bit(1) DEFAULT b'1' COMMENT '是否单人处理',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '秦川修改时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '秦川修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息中心表';

-- ----------------------------
-- Table structure for `msgs_center_info_receive`
-- ----------------------------
DROP TABLE IF EXISTS `msgs_center_info_receive`;
CREATE TABLE `msgs_center_info_receive` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `msgs_center_id` bigint(20) NOT NULL COMMENT '消息中心ID\n#msgs_center_info',
  `user_id` bigint(20) NOT NULL COMMENT '接收人ID\n#c_user',
  `is_read` bit(1) DEFAULT b'0' COMMENT '是否已读\n#BooleanStatus',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人',
  `update_user` bigint(20) DEFAULT '0' COMMENT '秦川修改人',
  `update_time` datetime DEFAULT NULL COMMENT '秦川修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息中心接收表';

-- ----------------------------
-- Table structure for `pt_company_use_node_type`
-- ----------------------------
DROP TABLE IF EXISTS `pt_company_use_node_type`;
CREATE TABLE `pt_company_use_node_type` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `node_factory_id` bigint(32) DEFAULT NULL COMMENT '厂家ID',
  `node_type_id` bigint(32) DEFAULT NULL COMMENT '型号ID',
  `node_facotry_name` varchar(100) DEFAULT NULL COMMENT '厂家名称',
  `node_name` varchar(50) DEFAULT NULL COMMENT '型号名称',
  `use_status` smallint(6) DEFAULT NULL COMMENT '启用\r\n            禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='节点类型管理';

------------------------------
-- Table structure for `pt_gas_meter_factory`
-- ----------------------------
DROP TABLE IF EXISTS `pt_gas_meter_factory`;
CREATE TABLE `pt_gas_meter_factory` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `gas_meter_factory_name` varchar(100) NOT NULL COMMENT '厂家名称',
  `gas_meter_factory_code` varchar(10) NOT NULL COMMENT '厂家编号',
  `gas_meter_factory_address` varchar(100) NOT NULL COMMENT '厂家地址',
  `telphone` varchar(11) DEFAULT NULL COMMENT '电话',
  `contacts` varchar(10) DEFAULT NULL COMMENT '联系人',
  `fax_number` varchar(20) DEFAULT NULL COMMENT '传真号码',
  `manager` varchar(10) DEFAULT NULL COMMENT '经理',
  `tax_registration_no` varchar(30) DEFAULT NULL COMMENT '税务登记号',
  `bank` varchar(20) DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(20) DEFAULT NULL COMMENT '银行账号',
  `gas_meter_type` varchar(100) DEFAULT NULL COMMENT '普表\r\n ic卡智能燃气表\r\n 物联网表\r\n 流量计(普表)\r\n 流量 计(ic卡表)\r\n 流量计(物联网表)\r\n',
  `gas_meter_factory_status` smallint(6) NOT NULL DEFAULT '1' COMMENT '厂家状态：（0：启用；1：禁用）',
  `compatible_parameter` varchar(1000) DEFAULT NULL COMMENT '兼容参数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
  `delete_status` smallint(6) DEFAULT '0' COMMENT '删除标识（0：存在；1-删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表厂家';

-- ----------------------------
-- Table structure for `pt_gas_meter_model`
-- ----------------------------
DROP TABLE IF EXISTS `pt_gas_meter_model`;
CREATE TABLE `pt_gas_meter_model` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_id` bigint(32) NOT NULL COMMENT '生产厂家ID',
  `company_code` varchar(10) DEFAULT NULL COMMENT '生产厂家编号',
  `company_name` varchar(100) DEFAULT NULL COMMENT '生产厂家名称',
  `gas_meter_version_id` bigint(32) NOT NULL COMMENT '版号ID',
  `gas_meter_version_name` varchar(30) DEFAULT NULL COMMENT '版号名称',
  `model_name` varchar(30) NOT NULL COMMENT '型号名称',
  `data_status` smallint(6) DEFAULT '1' COMMENT '是否启用（0：启用；1-禁用）',
  `specification` varchar(10) NOT NULL COMMENT '型号规格',
  `nominal_flow_rate` decimal(12,2) NOT NULL COMMENT '公称流量',
  `max_flow` decimal(12,3) NOT NULL COMMENT '最大流量',
  `min_flow` decimal(8,3) NOT NULL COMMENT '最小流量',
  `max_pressure` decimal(12,2) NOT NULL COMMENT '最大压力',
  `max_word_wheel` decimal(12,4) NOT NULL COMMENT '字轮最大值',
  `rotational_volume` decimal(12,2) NOT NULL COMMENT '回转体积',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
  `delete_status` smallint(6) DEFAULT '0' COMMENT '删除标识（0：存在；1-删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表型号';

-- ----------------------------
-- Table structure for `pt_gas_meter_version`
-- ----------------------------
DROP TABLE IF EXISTS `pt_gas_meter_version`;
CREATE TABLE `pt_gas_meter_version` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `gas_meret_version_name` varchar(30) DEFAULT NULL COMMENT '版号名称',
  `gas_meter_describe` varchar(30) DEFAULT NULL COMMENT '版号描述',
  `company_id` bigint(32) DEFAULT NULL COMMENT '生产厂家ID',
  `company_code` varchar(10) DEFAULT NULL COMMENT '生产厂家编号',
  `company_name` varchar(100) DEFAULT NULL COMMENT '生产厂家名称',
  `equipment_vendors_name` varchar(100) DEFAULT NULL COMMENT '无线通信设备厂家名称',
  `wireless_version` varchar(10) DEFAULT NULL COMMENT '无线通信版本',
  `order_source_name` varchar(20) NOT NULL COMMENT '订单来源名称',
  `amount_mark` varchar(20) DEFAULT NULL COMMENT '金额标志',
  `remote_service_flag` varchar(20) DEFAULT NULL COMMENT '远程业务标志',
  `amount_digits` smallint(6) DEFAULT NULL COMMENT '气量表金额位数',
  `single_level_amount` smallint(6) DEFAULT NULL COMMENT '是否单阶金额表：0:是；1-否',
  `ic_card_core_mark` varchar(30) DEFAULT NULL COMMENT 'IC卡内核标识',
  `measurement_accuracy` smallint(6) DEFAULT NULL COMMENT '计费精度（小数位数）',
  `select_address` smallint(6) DEFAULT '1' COMMENT '是否选择地址：0:是；1-否',
  `version_state` smallint(6) DEFAULT '1' COMMENT '开启\r\n		禁用',
  `accumulated_amount` smallint(6) DEFAULT '1' COMMENT '补气是否累加金额',
  `accumulated_count` smallint(6) DEFAULT '1' COMMENT '补气是否累加次数',
  `card_type` varchar(30) DEFAULT NULL COMMENT '卡号类型',
  `card_number_prefix` varchar(10) DEFAULT NULL COMMENT '卡号前缀',
  `card_number_length` smallint(6) DEFAULT NULL COMMENT '卡号长度',
  `open_in_meter_number` smallint(6) DEFAULT NULL COMMENT '开户录入表号',
  `issuing_cards` smallint(6) DEFAULT '1' COMMENT '是否发卡',
  `verification_table_no` smallint(6) DEFAULT '1' COMMENT '是否验证表号',
  `gas_meter_type` varchar(30) DEFAULT NULL COMMENT 'ORDINARY_METER:普表\r\n            IC_METER:IC卡智能燃气表\r\n            IOT_METER物联网表\r\n            FLOWMETER_ORDINARY_METER:流量计(普表)\r\n            FLOWMETER_IC_METER:流量 计(IC卡表)\r\n            FLOWMETER_IOT_METER:流量计(物联网表)',
  `settlement_type` varchar(10) DEFAULT NULL COMMENT 'GAS:气量\r\n            MONEY:金额',
  `settlement_mode` varchar(10) DEFAULT NULL COMMENT 'READMETER:抄表结算\r\n            METER:表端结算\r\n            SERVICE:服务端结算',
  `charge_type` varchar(20) DEFAULT NULL COMMENT 'IC_RECHARGE:IC卡充值\r\n            READMETER_PAY:抄表缴费\r\n            REMOTE_RECHARGE:远程充值',
  `compatible_parameter` varchar(1000) DEFAULT NULL COMMENT '如气表功能控制、兼容标识、兼容参数等。\r\n            建议JSON存储',
  `gas_meter_function` varchar(1000) DEFAULT NULL COMMENT 'READMETER:抄表\r\n            CLOSE_VALVE:普通关阀\r\n            FORCED_CLOSING_VALVE:强制关阀\r\n            OPEN_VALVE:开阀\r\n            MODIFY_PRICE:调价\r\n            UPDATE_CYCLE_GAS:周期累计量修改\r\n\r\n\r\n            ',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(100) DEFAULT NULL COMMENT '版号说明(备注)',
  `delete_status` smallint(6) DEFAULT '0' COMMENT '删除标识（0：存在；1-删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表版本';

-- ----------------------------
-- Table structure for `pz_business_hall`
-- ----------------------------
DROP TABLE IF EXISTS `pz_business_hall`;
CREATE TABLE `pz_business_hall` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `business_hall_address` varchar(100) DEFAULT NULL COMMENT '营业厅地址',
  `contacts` varchar(100) DEFAULT NULL COMMENT '联系人',
  `telphone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `business_hall_phone` varchar(20) DEFAULT NULL COMMENT '营业厅电话',
  `email` varchar(30) DEFAULT NULL COMMENT '联系邮件',
  `point_type` varchar(30) DEFAULT NULL COMMENT '配置类型',
  `is_sale` smallint(6) DEFAULT NULL COMMENT '代售点',
  `point_of_sale` smallint(6) DEFAULT NULL COMMENT '代售点（配额）',
  `restrict_status` smallint(6) DEFAULT NULL COMMENT '营业限制0：不限制，1：限制',
  `balance` decimal(18,4) DEFAULT '0.0000' COMMENT '营业余额',
  `single_limit` decimal(18,4) DEFAULT NULL COMMENT '单笔限额',
  `business_hall_status` smallint(6) DEFAULT NULL COMMENT '状态1：启用 0：停用',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_user` bigint(32) DEFAULT NULL COMMENT '删除人id',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_status` smallint(6) DEFAULT '1' COMMENT '删除标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营业厅信息表';

-- ----------------------------
-- Table structure for `pz_business_template`
-- ----------------------------
DROP TABLE IF EXISTS `pz_business_template`;
CREATE TABLE `pz_business_template` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `template_code` varchar(64) DEFAULT NULL COMMENT '模板编码',
  `template_name` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `file_id` bigint(20) DEFAULT NULL COMMENT '文件id',
  `item_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `item_describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_template_code` (`template_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `pz_community`
-- ----------------------------
DROP TABLE IF EXISTS `pz_community`;
CREATE TABLE `pz_community` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` char(32) DEFAULT NULL COMMENT '公司ID',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `street_id` char(32) DEFAULT NULL COMMENT '街道ID',
  `community_name` varchar(50) DEFAULT NULL COMMENT '小区名称',
  `community_address` varchar(100) DEFAULT NULL COMMENT '小区地址',
  `longitude` decimal(18,8) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18,8) DEFAULT NULL COMMENT '纬度',
  `meter_count` int(11) DEFAULT NULL COMMENT '表数',
  `dismantle_count` int(11) DEFAULT NULL COMMENT '已拆数',
  `data_status` smallint(6) DEFAULT NULL COMMENT '状态（1-生效，0-无效）',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_status` smallint(6) DEFAULT NULL COMMENT '删除状态(1-删除,0-有效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1305414685058662401 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `pz_company_user_quota_record`
-- ----------------------------
DROP TABLE IF EXISTS `pz_company_user_quota_record`;
CREATE TABLE `pz_company_user_quota_record` (
  `id` bigint(32) NOT NULL,
  `company_code` char(32) DEFAULT NULL COMMENT '公司code',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `business_hall_id` bigint(32) DEFAULT NULL COMMENT '营业厅ID',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户ID',
  `quota_type` varchar(10) DEFAULT NULL COMMENT '\n#QuotaType{CUMULATIVE:累加;COVER:覆盖;}',
  `quota_time` datetime DEFAULT NULL COMMENT '配额时间',
  `money` decimal(18,4) DEFAULT NULL COMMENT '金额',
  `single_limit` decimal(18,4) DEFAULT NULL COMMENT '单笔限额',
  `total_money` decimal(18,4) DEFAULT NULL COMMENT '配后金额',
  `data_status` smallint(6) DEFAULT NULL COMMENT '0：不限制\r\n            1：限制',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营业厅配额记录';

-- ----------------------------
-- Table structure for `pz_exception_rule_conf`
-- ----------------------------
DROP TABLE IF EXISTS `pz_exception_rule_conf`;
CREATE TABLE `pz_exception_rule_conf` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` char(32) DEFAULT NULL COMMENT '公司code',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型id',
  `surge_multiple` decimal(4,1) DEFAULT NULL COMMENT '暴增倍数（大于1）',
  `sharp_decrease` decimal(4,1) DEFAULT NULL COMMENT '锐减比例（0~1）',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表异常规则配置';

-- ----------------------------
-- Table structure for `pz_function_switch`
-- ----------------------------
DROP TABLE IF EXISTS `pz_function_switch`;
CREATE TABLE `pz_function_switch` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `open_id_card` smallint(6) DEFAULT NULL COMMENT '启用ID卡  启用1，禁用0',
  `open_customer_prefix` smallint(6) DEFAULT NULL COMMENT '自编客户编号前缀',
  `customer_prefix` varchar(100) DEFAULT NULL COMMENT '客户编号前缀',
  `settlement_date` date DEFAULT NULL COMMENT '结算日期',
  `open_vat_invoice` smallint(6) DEFAULT NULL COMMENT '启用增值税发票',
  `tax_rate` decimal(5,2) DEFAULT NULL COMMENT '税率',
  `rounding` smallint(6) DEFAULT NULL COMMENT '四舍五入',
  `open_black_list` smallint(6) DEFAULT NULL COMMENT '启用黑名单限购',
  `black_max_volume` decimal(18,2) DEFAULT NULL COMMENT '黑名单限购最大充值气量',
  `black_max__money` decimal(18,2) DEFAULT NULL COMMENT '黑名单限购最大充值金额',
  `open_check_limit` smallint(6) DEFAULT NULL COMMENT '启动安检限购',
  `check_max_volume` decimal(18,2) DEFAULT NULL COMMENT '安检限购最大充值气量',
  `check_max_money` decimal(18,2) DEFAULT NULL COMMENT '安检限购最大充值金额',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='功能项配置';

-- ----------------------------
-- Table structure for `pz_give_reduction_conf`
-- ----------------------------
DROP TABLE IF EXISTS `pz_give_reduction_conf`;
CREATE TABLE `pz_give_reduction_conf` (
  `ID` bigint(32) NOT NULL COMMENT 'ID',
  `activity_name` varchar(100) DEFAULT NULL COMMENT '活动名称',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `give_gas` decimal(18,4) DEFAULT NULL COMMENT '赠送气量（充值场景才能设置）',
  `activity_money_type` varchar(32) DEFAULT NULL COMMENT '活动金额方式（缴费场景无百分比选项）\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            percent: 百分比',
  `activity_money` decimal(18,4) DEFAULT NULL COMMENT '活动金额',
  `activity_percent` decimal(18,4) DEFAULT NULL COMMENT '活动比例',
  `activity_scene` varchar(32) DEFAULT NULL COMMENT '活动场景\r\n            RECHARGE_GIVE: 充值赠送\r\n            PRECHARGE_GIVE: 预存赠送\r\n            CHARGE_DE: 缴费减免',
  `use_gas_types` varchar(1000) DEFAULT NULL COMMENT '用气类型(只针对充值场景)\r\n            存放jsonarray数据 包含用气类型id和用气类型名称。',
  `data_status` smallint(6) DEFAULT NULL COMMENT '是否启用\r\n            1 启用\r\n            0 不启用',
  `delete_status` smallint(6) DEFAULT NULL COMMENT '删除状态\r\n            1 删除\r\n            0 正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赠送减免活动配置';

-- ----------------------------
-- Table structure for `pz_invoice_param`
-- ----------------------------
DROP TABLE IF EXISTS `pz_invoice_param`;
CREATE TABLE `pz_invoice_param` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `account_level` smallint(6) DEFAULT NULL COMMENT '账号等级（0-总公司，1-分公司）',
  `name` varchar(100) DEFAULT NULL COMMENT '销方名称',
  `taxpayer_no` varchar(100) DEFAULT NULL COMMENT '销方纳税人编号',
  `address` varchar(300) DEFAULT NULL COMMENT '销方地址',
  `telephone` varchar(20) DEFAULT NULL COMMENT '销方电话',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '销方开户行名称',
  `bank_account` varchar(60) DEFAULT NULL COMMENT '销方银行账户',
  `invoice_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `pz_mobile_message`
-- ----------------------------
DROP TABLE IF EXISTS `pz_mobile_message`;
CREATE TABLE `pz_mobile_message` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` char(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `template_code` varchar(64) DEFAULT NULL COMMENT '模板编码',
  `template_name` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `template_describe` varchar(300) DEFAULT NULL COMMENT '模板描述',
  `template_cntent` varchar(300) DEFAULT NULL COMMENT '模板内容',
  `file_path` varchar(300) DEFAULT NULL COMMENT '文件路径',
  `template_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信模板配置';

-- ----------------------------
-- Table structure for `pz_pay_info`
-- ----------------------------
DROP TABLE IF EXISTS `pz_pay_info`;
CREATE TABLE `pz_pay_info` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` char(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `account_level` smallint(6) DEFAULT NULL COMMENT '账号等级（0-总公司，1-分公司）',
  `used_parent_pay` smallint(6) DEFAULT NULL COMMENT '使用总公司账号',
  `pay_code` varchar(64) DEFAULT NULL COMMENT '支付类型编码',
  `pay_type` varchar(64) DEFAULT NULL COMMENT '支付类型名称',
  `merchant_no` varchar(100) DEFAULT NULL COMMENT '商户号',
  `app_id` varchar(100) DEFAULT NULL COMMENT 'APPID',
  `app_secret` varchar(100) DEFAULT NULL COMMENT 'APPSECRET',
  `api_secret` varchar(100) DEFAULT NULL COMMENT 'API秘钥',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `pay_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(6) DEFAULT '1' COMMENT '删除标识',
  `merchant_private_key` varchar(2000) DEFAULT NULL COMMENT '商家私钥',
  `alipay_public_key` varchar(400) DEFAULT NULL COMMENT '支付宝公钥',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付配置';

-- ----------------------------
-- Table structure for `pz_penalty`
-- ----------------------------
DROP TABLE IF EXISTS `pz_penalty`;
CREATE TABLE `pz_penalty` (
  `ID` bigint(32) NOT NULL AUTO_INCREMENT,
  `execute_name` varchar(100) DEFAULT '' COMMENT '滞纳金名称',
  `use_gas_type_id` varchar(100) DEFAULT NULL COMMENT '用气类型ID',
  `execute_day` int(11) DEFAULT NULL COMMENT '滞纳金执行日',
  `rate` decimal(18,4) DEFAULT NULL COMMENT '利率',
  `compound_interest` smallint(6) DEFAULT NULL COMMENT '复利',
  `principal_cap` smallint(6) DEFAULT NULL COMMENT '本金及上限',
  `fixed_cap` decimal(18,4) DEFAULT NULL COMMENT '固定上限',
  `activation_time` date DEFAULT NULL COMMENT '启用时间',
  `data_status` smallint(6) DEFAULT NULL COMMENT '数据状态，1表示未超过本金呢，2超过本金，3启用，4未启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人ID',
  `execute_month` int(11) DEFAULT NULL COMMENT '滞纳金执行月',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1298524133264457729 DEFAULT CHARSET=utf8mb4 COMMENT='滞纳金方案配置';

-- ----------------------------
-- Table structure for `pz_preferential`
-- ----------------------------
DROP TABLE IF EXISTS `pz_preferential`;
CREATE TABLE `pz_preferential` (
  `ID` bigint(32) NOT NULL COMMENT 'ID',
  `PZ__id` varchar(1000) DEFAULT NULL COMMENT '用气类_ID',
  `use_gas_type_code` varchar(32) DEFAULT NULL COMMENT '用气类型code',
  `use_gas_type_name` varchar(50) DEFAULT NULL COMMENT '用气类型名称',
  `preferential_name` varchar(100) DEFAULT NULL COMMENT '优惠活动名称',
  `preferential_activate` smallint(6) DEFAULT NULL COMMENT '活动有效期',
  `is_always_preferential` smallint(6) DEFAULT NULL COMMENT '持续优惠',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `data_status` smallint(6) DEFAULT NULL COMMENT '是否启用',
  `preferential_gas` decimal(18,4) DEFAULT NULL COMMENT '优惠气量',
  `preferential_type` smallint(6) DEFAULT NULL COMMENT '优惠方式',
  `preferential_money` decimal(18,4) DEFAULT NULL COMMENT '优惠金额',
  `preferential_percent` decimal(18,4) DEFAULT NULL COMMENT '优惠比例',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户',
  `delete_status` smallint(6) DEFAULT NULL COMMENT '删除状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠活动表';

-- ----------------------------
-- Table structure for `pz_price_scheme`
-- ----------------------------
DROP TABLE IF EXISTS `pz_price_scheme`;
CREATE TABLE `pz_price_scheme` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '价格id',
  `company_code` char(32) DEFAULT NULL COMMENT '公司id',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `use_gas_type_id` bigint(32) DEFAULT NULL COMMENT '用气类型ID',
  `compensation_price` decimal(18,4) DEFAULT NULL COMMENT '补差价格',
  `gas_1` decimal(18,4) DEFAULT NULL COMMENT '一阶气量',
  `price_1` decimal(18,4) DEFAULT NULL COMMENT '一阶价格',
  `gas_2` decimal(18,4) DEFAULT NULL COMMENT '二阶气量',
  `price_2` decimal(18,4) DEFAULT NULL COMMENT '二阶价格',
  `gas_3` decimal(18,4) DEFAULT NULL COMMENT '三阶气量',
  `price_3` decimal(18,4) DEFAULT NULL COMMENT '三阶价格',
  `gas_4` decimal(18,4) DEFAULT NULL COMMENT '四阶气量',
  `price_4` decimal(18,4) DEFAULT NULL COMMENT '四阶价格',
  `gas_5` decimal(18,4) DEFAULT NULL COMMENT '五阶气量id',
  `price_5` decimal(18,4) DEFAULT NULL COMMENT '五阶价格',
  `gas_6` decimal(18,4) DEFAULT NULL COMMENT '六阶气量',
  `price_6` decimal(18,4) DEFAULT NULL COMMENT '六阶价格',
  `gas_7` decimal(18,4) DEFAULT NULL COMMENT '七阶气量',
  `price_7` decimal(18,4) DEFAULT NULL COMMENT '七阶价格',
  `start_time` date DEFAULT NULL COMMENT '启用时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `cycle_start_time` date DEFAULT NULL COMMENT '切换时间',
  `start_money` smallint(6) DEFAULT NULL COMMENT '起始月',
  `settlement_day` smallint(6) DEFAULT NULL COMMENT '结算日',
  `cycle` smallint(6) DEFAULT NULL COMMENT '周期',
  `data_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户',
  `price_change_is_clear` smallint(6) DEFAULT '0' COMMENT '调价是否清零(1-清零，0-不清零)',
  `fixed_price` decimal(18,4) DEFAULT NULL COMMENT '固定单价',
  `is_heating` smallint(6) DEFAULT '0' COMMENT '0-非采暖，1-采暖',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1305388597066596353 DEFAULT CHARSET=utf8mb4 COMMENT='价格配置表';

-- ----------------------------
-- Table structure for `pz_purchase_limit`
-- ----------------------------
DROP TABLE IF EXISTS `pz_purchase_limit`;
CREATE TABLE `pz_purchase_limit` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` char(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `limit_name` varchar(50) DEFAULT NULL COMMENT '限制名称',
  `use_gas_type_id` varchar(200) DEFAULT NULL COMMENT '用气类型ID',
  `use_gas_type_name` varchar(200) DEFAULT NULL COMMENT '用气类型名称',
  `limit_type` smallint(30) DEFAULT NULL COMMENT '1-个人  0-所有',
  `start_time` date DEFAULT NULL COMMENT '启用时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `cycle` int(11) DEFAULT NULL COMMENT '周期',
  `max_charge_gas` decimal(18,4) DEFAULT NULL COMMENT '最大充值气量',
  `max_charge_money` decimal(18,4) DEFAULT NULL COMMENT '最大充值金额',
  `data_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户',
  `cycle_num` int(11) DEFAULT NULL COMMENT '周期重复次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1305398862680162305 DEFAULT CHARSET=utf8mb4 COMMENT='限购配置';

-- ----------------------------
-- Table structure for `pz_reduction_item`
-- ----------------------------
DROP TABLE IF EXISTS `pz_reduction_item`;
CREATE TABLE `pz_reduction_item` (
  `id` bigint(32) NOT NULL,
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `compan_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `item_name` varchar(100) DEFAULT NULL COMMENT '减免项名称',
  `load_method` varchar(32) DEFAULT NULL COMMENT '项加载方式\r\n            fixed: 一次性加载\r\n            demand_add: 按需添加',
  `money_method` varchar(32) DEFAULT NULL COMMENT '金额方式\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            percent： 百分比\r\n            默认固定金额\r\n            ',
  `reduction_money` decimal(18,4) DEFAULT NULL COMMENT '减免预设金额',
  `reduction_percent` decimal(18,4) DEFAULT NULL COMMENT '减免预设百分比',
  `data_status` smallint(6) DEFAULT NULL COMMENT '状态\r\n            1 启用\r\n            0 禁用',
  `delete_status` smallint(6) DEFAULT NULL COMMENT '删除状态\r\n            1 删除\r\n            0 正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='减免项配置';

-- ----------------------------
-- Table structure for `pz_street`
-- ----------------------------
DROP TABLE IF EXISTS `pz_street`;
CREATE TABLE `pz_street` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` char(32) DEFAULT NULL COMMENT '公司ID',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `province_id` bigint(32) DEFAULT NULL COMMENT '省id',
  `province_name` varchar(255) DEFAULT NULL COMMENT '省名称',
  `area_id` bigint(32) DEFAULT NULL COMMENT '区id',
  `area_name` varchar(50) DEFAULT NULL COMMENT '区名称',
  `city_id` bigint(32) DEFAULT NULL COMMENT '市ID',
  `city_name` varchar(255) DEFAULT NULL COMMENT '市名称',
  `street_name` varchar(100) DEFAULT NULL COMMENT '街道名称',
  `data_status` smallint(6) DEFAULT NULL COMMENT '状态（1-生效，0-无效）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户id',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_user` bigint(32) DEFAULT NULL COMMENT '删除用户id',
  `delete_status` smallint(6) DEFAULT NULL COMMENT '删除状态(1-删除,0-有效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1305411430903709697 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `pz_template`
-- ----------------------------
DROP TABLE IF EXISTS `pz_template`;
CREATE TABLE `pz_template` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `template_type_code` varchar(64) DEFAULT NULL COMMENT '模板类型编码',
  `template_type_name` varchar(64) DEFAULT NULL COMMENT '模板类型名称',
  `template_type_describe` varchar(200) DEFAULT NULL COMMENT '模板类型描述',
  `template_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(6) DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板类型配置表';

-- ----------------------------
-- Table structure for `pz_template_item`
-- ----------------------------
DROP TABLE IF EXISTS `pz_template_item`;
CREATE TABLE `pz_template_item` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `template_type_id` bigint(20) DEFAULT NULL COMMENT '模板类型id',
  `template_type_name` varchar(64) DEFAULT NULL COMMENT '模板类型名称',
  `template_code` varchar(64) DEFAULT NULL COMMENT '模板编码',
  `template_name` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `file_id` bigint(32) DEFAULT NULL COMMENT '文件id',
  `default_file_id` bigint(32) DEFAULT NULL,
  `file_path` varchar(300) DEFAULT NULL COMMENT '文件路径',
  `item_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `item_describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `sort_value` int(11) DEFAULT NULL COMMENT '排序',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` smallint(6) DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板项目表（Item）';

-- ----------------------------
-- Table structure for `pz_toll_item`
-- ----------------------------
DROP TABLE IF EXISTS `pz_toll_item`;
CREATE TABLE `pz_toll_item` (
  `id` bigint(32) NOT NULL,
  `level` varchar(20) DEFAULT NULL COMMENT 'DEFAULT：默认(不可编辑项，由平台统一管理)\r\n            COMPANY：公司级',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `compan_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `sys_item_code` varchar(32) DEFAULT NULL COMMENT '系统项编码',
  `item_name` varchar(100) DEFAULT NULL COMMENT '收费项名称',
  `charge_frequency` varchar(32) DEFAULT NULL COMMENT '收费频次\r\n            ON_DEMAND:按需\r\n            ONE_TIME：一次性\r\n            BY_MONTH：按月\r\n            QUARTERLY：按季\r\n            BY_YEAR：按年',
  `charge_period` smallint(6) DEFAULT NULL COMMENT '收费期限',
  `cycle_value` smallint(6) DEFAULT NULL COMMENT '周期值,固定1',
  `money` decimal(18,4) DEFAULT NULL COMMENT '金额',
  `money_method` varchar(32) DEFAULT NULL COMMENT '金额方式\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            默认固定金额\r\n            ',
  `financial_subject` varchar(32) DEFAULT NULL COMMENT '财务科目',
  `vat_general_rate` decimal(18,4) DEFAULT NULL COMMENT '增值税普税税率',
  `tax_category_code` varchar(100) DEFAULT NULL COMMENT '税收分类编码',
  `favoured_status` smallint(6) DEFAULT NULL COMMENT '是否享受优惠 0：表示享受优惠，1：不享受优惠',
  `favoured_policy` varchar(100) DEFAULT NULL COMMENT '优惠政策',
  `zero_tax_status` smallint(6) DEFAULT NULL COMMENT '零税率标识 0表示是零税率，1表示不是零税率',
  `custom_code` varchar(100) DEFAULT NULL COMMENT '企业自编码',
  `tax_deduction_money` decimal(18,4) DEFAULT NULL COMMENT '税扣除额',
  `code_version` varchar(100) DEFAULT NULL COMMENT '编码版本号',
  `scene_code` varchar(32) DEFAULT NULL COMMENT '场景编码',
  `scene_name` varchar(100) DEFAULT NULL COMMENT '场景名称',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `use_gas_types` varchar(1000) DEFAULT NULL COMMENT '用气类型(多个)(可以json，可以分割字符窜)',
  `data_status` smallint(6) DEFAULT NULL COMMENT '状态\r\n            1 启用\r\n            0 禁用',
  `delete_status` smallint(6) DEFAULT NULL COMMENT '删除状态\r\n            1 删除\r\n            0 正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收费项配置\r\n开户费\r\n换表费\r\n补卡费\r\n报装费\r\n卡费';

-- ----------------------------
-- Table structure for `pz_use_gas_type`
-- ----------------------------
DROP TABLE IF EXISTS `pz_use_gas_type`;
CREATE TABLE `pz_use_gas_type` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `company_code` char(32) DEFAULT NULL COMMENT '公司ID',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `user_type_code` varchar(50) DEFAULT NULL,
  `user_type_name` varchar(50) DEFAULT NULL COMMENT '用气类型名称',
  `use_gas_type_name` varchar(50) DEFAULT NULL COMMENT '用气类型名称',
  `gas_type` varchar(20) DEFAULT NULL COMMENT 'NATURAL_GAS:天然气\r\n            COAL_GAS:煤气\r\n            LIQUID_GAS:液化气',
  `price_type` varchar(20) DEFAULT NULL COMMENT 'HEATING_PRICE:采暖价\r\n            LADDER_PRICE:阶梯价\r\n            FIXED_PRICE:固定价',
  `max_charge_gas` decimal(18,4) DEFAULT NULL COMMENT '最大充值气量',
  `max_charge_money` decimal(18,4) DEFAULT NULL COMMENT '最大充值金额',
  `alarm_gas` decimal(18,4) DEFAULT NULL COMMENT '报警气量',
  `alarm_money` decimal(18,4) DEFAULT NULL COMMENT '报警金额',
  `open_decrement` smallint(6) DEFAULT NULL COMMENT '开户按月递减低价气量;1-开启,0-关闭',
  `decrement` decimal(18,4) DEFAULT NULL COMMENT '递减量',
  `population_growth` smallint(6) DEFAULT NULL COMMENT '启用家庭人口增量;1-启用,0-禁用',
  `population_base` int(11) DEFAULT NULL COMMENT '家庭人口基数',
  `population_increase` decimal(11,0) DEFAULT NULL COMMENT '人口增量',
  `data_status` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_use_name` varchar(100) DEFAULT NULL COMMENT '创建人名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人ID',
  `update_user_name` varchar(100) DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_user` bigint(32) DEFAULT NULL COMMENT '删除人ID',
  `delete_user_name` varchar(100) DEFAULT NULL COMMENT '删除人名称',
  `delete_status` smallint(6) unsigned DEFAULT NULL COMMENT '删除状态(0-有效，1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1305329763019128833 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `sms_send_status`
-- ----------------------------
DROP TABLE IF EXISTS `sms_send_status`;
CREATE TABLE `sms_send_status` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务ID\n#sms_task',
  `send_status` varchar(10) NOT NULL DEFAULT 'WAITING' COMMENT '发送状态\n#SendStatus{WAITING:等待发送;SUCCESS:发送成功;FAIL:发送失败}',
  `receiver` varchar(20) NOT NULL COMMENT '接收者手机号\n单个手机号',
  `biz_id` varchar(255) DEFAULT '' COMMENT '发送回执ID\n阿里：发送回执ID,可根据该ID查询具体的发送状态  腾讯：sid 标识本次发送id，标识一次短信下发记录  百度：requestId 短信发送请求唯一流水ID',
  `ext` varchar(255) DEFAULT '' COMMENT '发送返回\n阿里：RequestId 请求ID  腾讯：ext：用户的session内容，腾讯server回包中会原样返回   百度：无',
  `code` varchar(255) DEFAULT '' COMMENT '状态码\n阿里：返回OK代表请求成功,其他错误码详见错误码列表  腾讯：0表示成功(计费依据)，非0表示失败  百度：1000 表示成功',
  `message` varchar(500) DEFAULT '' COMMENT '状态码的描述',
  `fee` int(11) DEFAULT '0' COMMENT '短信计费的条数\n腾讯专用',
  `create_month` varchar(7) DEFAULT '' COMMENT '创建时年月\n格式：yyyy-MM 用于统计',
  `create_week` varchar(10) DEFAULT '' COMMENT '创建时年周\n创建时处于当年的第几周 yyyy-ww 用于统计',
  `create_date` varchar(10) DEFAULT '' COMMENT '创建时年月日\n格式： yyyy-MM-dd 用于统计',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '秦川修改人',
  `update_time` datetime DEFAULT NULL COMMENT '秦川修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='短信发送状态';

-- ----------------------------
-- Table structure for `sms_sign`
-- ----------------------------
DROP TABLE IF EXISTS `sms_sign`;
CREATE TABLE `sms_sign` (
  `id` bigint(20) NOT NULL COMMENT '签名ID',
  `sign_id` bigint(20) NOT NULL COMMENT '服务器签名返回ID',
  `sign_name` varchar(20) NOT NULL COMMENT '签名名称',
  `sign_type` int(11) NOT NULL COMMENT ' 签名类型 0：公司（0，1，2，3）。 1：APP',
  `document_type` int(11) NOT NULL COMMENT '证明类型  0：三证合一。 1：企业营业执照。 2：组织机构代码证书。 3：社会信用代码证书。 4：应用后台管理截图（个人开发APP',
  `international` int(11) NOT NULL COMMENT '是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信',
  `used_method` int(11) NOT NULL COMMENT '签名用途： 0：自用。 1：他用。',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '秦川修改人',
  `update_time` datetime DEFAULT NULL COMMENT '秦川修改时间',
  `sign_status` smallint(6) DEFAULT '1' COMMENT '签名审核状态（0-通过，1-待审核，-1-未通过）',
  `delete_status` smallint(6) DEFAULT '0' COMMENT '删除标识',
  `review_reply` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信签名';

-- ----------------------------
-- Table structure for `sms_task`
-- ----------------------------
DROP TABLE IF EXISTS `sms_task`;
CREATE TABLE `sms_task` (
  `id` bigint(20) NOT NULL COMMENT '短信记录ID',
  `template_id` bigint(20) NOT NULL COMMENT '模板ID\n#sms_template',
  `status` varchar(10) DEFAULT 'WAITING' COMMENT '执行状态\n(手机号具体发送状态看sms_send_status表) \n#TaskStatus{WAITING:等待执行;SUCCESS:执行成功;FAIL:执行失败}',
  `source_type` varchar(10) DEFAULT 'APP' COMMENT '来源类型\n#SourceType{APP:应用;SERVICE:服务}\n',
  `receiver` text COMMENT '接收者手机号\n群发用英文逗号分割.\n支持2种格式:\n1: 手机号,手机号 \n2: 姓名<手机号>,姓名<手机号>',
  `topic` varchar(255) DEFAULT '' COMMENT '主题',
  `template_params` varchar(500) DEFAULT '' COMMENT '参数 \n需要封装为{‘key’:’value’, ...}格式\n且key必须有序\n\n',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `content` varchar(500) DEFAULT '' COMMENT '发送内容\n需要封装正确格式化: 您好，张三，您有一个新的快递。',
  `draft` bit(1) DEFAULT b'0' COMMENT '是否草稿',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '秦川修改人',
  `update_time` datetime DEFAULT NULL COMMENT '秦川修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='发送任务\n所有的短息发送调用，都视为是一次短信任务，任务表只保存数据和执行状态等信息，\n具体的发送状态查看发送状态（#sms_send_status）表';

-- ----------------------------
-- Table structure for `sms_template`
-- ----------------------------
DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template` (
  `id` bigint(20) NOT NULL COMMENT '模板ID',
  `template_type_id` bigint(20) NOT NULL COMMENT '模板类型ID',
  `template_type_name` varchar(50) DEFAULT NULL COMMENT '模板类型名称',
  `provider_type` varchar(10) DEFAULT NULL COMMENT '供应商类型\n#ProviderType{ALI:OK,阿里云短信;TENCENT:0,腾讯云短信;BAIDU:1000,百度云短信}',
  `app_id` varchar(255) DEFAULT NULL COMMENT '应用ID',
  `app_secret` varchar(255) DEFAULT NULL COMMENT '应用密码',
  `url` varchar(255) DEFAULT '' COMMENT 'SMS服务域名\n百度、其他厂商会用',
  `custom_code` varchar(20) NOT NULL DEFAULT '' COMMENT '模板编码\n用于api发送',
  `name` varchar(255) DEFAULT '' COMMENT '模板名称',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '模板内容',
  `sms_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '短信类型，0表示普通短信, 1表示营销短信。',
  `internat_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信。',
  `template_params` varchar(255) NOT NULL DEFAULT '' COMMENT '模板参数',
  `template_code` varchar(255) NOT NULL DEFAULT '' COMMENT '模板CODE',
  `sign_name` varchar(100) DEFAULT '' COMMENT '签名',
  `template_describe` varchar(255) DEFAULT '' COMMENT '备注',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '秦川修改人',
  `update_time` datetime DEFAULT NULL COMMENT '秦川修改时间',
  `template_status` smallint(6) DEFAULT '0' COMMENT '模板状态（0-启用，1-禁用）',
  `examine_status` smallint(6) DEFAULT '1' COMMENT '审核状态（0-通过，1-未审核，-1未通过）',
  `review_reply` varchar(255) DEFAULT NULL COMMENT '审核未通过原因',
  `delete_status` smallint(6) DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_CODE` (`custom_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='短信模板';

-- ----------------------------
-- Table structure for `tb_business_hall_quota_record`
-- ----------------------------
DROP TABLE IF EXISTS `tb_business_hall_quota_record`;
CREATE TABLE `tb_business_hall_quota_record` (
  `id` bigint(32) NOT NULL,
  `company_code` char(32) DEFAULT NULL COMMENT '公司ID',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `org_id` bigint(32) DEFAULT NULL COMMENT '组织ID',
  `business_hall_id` bigint(32) DEFAULT NULL COMMENT '营业厅ID',
  `data_status` smallint(6) DEFAULT NULL COMMENT '0：不限制\r\n            1：限制',
  `business_hall_name` varchar(100) DEFAULT NULL COMMENT '营业厅名称',
  `quota_type` varchar(10) DEFAULT NULL COMMENT '\n#QuotaType{CUMULATIVE:累加;COVER:覆盖;}',
  `quota_time` datetime DEFAULT NULL COMMENT '配额时间',
  `money` decimal(18,4) DEFAULT NULL COMMENT '金额',
  `balance` decimal(18,4) DEFAULT '0.0000' COMMENT '当前余额',
  `total_money` decimal(18,4) DEFAULT NULL COMMENT '配后金额',
  `single_limit` decimal(18,4) DEFAULT NULL COMMENT '单笔限额',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `undo_log`
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'increment id',
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime NOT NULL COMMENT 'create datetime',
  `log_modified` datetime NOT NULL COMMENT 'modify datetime',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='AT transaction mode undo table';
