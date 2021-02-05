/*
Navicat MySQL Data Transfer

Source Server         : gmis_defaults
Source Server Version : 50729
Source Host           : 172.16.92.156:3306
Source Database       : gmis_defaults

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-06-23 13:08:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `c_common_lang`
-- ----------------------------
DROP TABLE IF EXISTS `c_common_lang`;
CREATE TABLE `c_common_lang` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lang_key` varchar(255) DEFAULT NULL,
  `lang_value` varchar(255) DEFAULT NULL,
  `lang_type` int(11) DEFAULT NULL COMMENT '语言类型(1 中文 2 英文)',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `data_type` int(1) DEFAULT NULL COMMENT '数据类型:0-通知消息,1-菜单，2-其他',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of c_common_lang
-- ----------------------------
INSERT INTO `c_common_lang` VALUES ('1', '101', '用户中心', '1', '2020-06-08 18:52:18', '1');
INSERT INTO `c_common_lang` VALUES ('2', '101', 'User center', '2', '2020-06-08 18:55:43', '1');
INSERT INTO `c_common_lang` VALUES ('3', '102', '权限管理', '1', '2020-06-08 18:57:15', '1');
INSERT INTO `c_common_lang` VALUES ('4', '102', 'Rights management', '2', '2020-06-08 18:57:57', '1');
INSERT INTO `c_common_lang` VALUES ('5', '103', '基础配置', '1', '2020-06-08 18:58:31', '1');
INSERT INTO `c_common_lang` VALUES ('6', '103', 'Basic configuration', '2', '2020-06-08 18:59:02', '1');
INSERT INTO `c_common_lang` VALUES ('7', '104', '开发者管理', '1', '2020-06-08 18:59:30', '1');
INSERT INTO `c_common_lang` VALUES ('8', '104', 'Developer management', '2', '2020-06-08 18:59:55', '1');
INSERT INTO `c_common_lang` VALUES ('9', '105', '消息中心', '1', '2020-06-08 19:00:37', '1');
INSERT INTO `c_common_lang` VALUES ('10', '105', 'Message center', '2', '2020-06-08 19:01:04', '1');
INSERT INTO `c_common_lang` VALUES ('11', '106', '短信中心', '1', '2020-06-08 19:02:07', '1');
INSERT INTO `c_common_lang` VALUES ('12', '106', 'SMS center', '2', '2020-06-08 19:03:02', '1');
INSERT INTO `c_common_lang` VALUES ('13', '107', '文件中心', '1', '2020-06-08 19:04:52', '1');
INSERT INTO `c_common_lang` VALUES ('14', '107', 'File center', '2', '2020-06-08 19:05:18', '1');
INSERT INTO `c_common_lang` VALUES ('15', '108', '支付中心', '1', '2020-06-08 19:05:38', '1');
INSERT INTO `c_common_lang` VALUES ('16', '108', 'Pay center', '2', '2020-06-08 19:06:01', '1');
INSERT INTO `c_common_lang` VALUES ('17', '603976297063910529', '菜单配置', '1', '2020-06-08 19:06:41', '1');
INSERT INTO `c_common_lang` VALUES ('18', '603976297063910529', 'Menu configuration', '2', '2020-06-08 19:06:50', '1');
INSERT INTO `c_common_lang` VALUES ('19', '603981723864141121', '角色管理', '1', '2020-06-08 19:08:05', '1');
INSERT INTO `c_common_lang` VALUES ('20', '603981723864141121', 'Role management', '2', '2020-06-08 19:08:15', '1');
INSERT INTO `c_common_lang` VALUES ('21', '603982542332235201', '组织管理', '1', '2020-06-08 19:08:54', '1');
INSERT INTO `c_common_lang` VALUES ('22', '603982542332235201', 'Organizational management', '2', '2020-06-08 19:09:06', '1');
INSERT INTO `c_common_lang` VALUES ('23', '603982713849908801', '岗位管理', '1', '2020-06-08 19:10:15', '1');
INSERT INTO `c_common_lang` VALUES ('24', '603982713849908801', 'Position management', '2', '2020-06-08 19:10:30', '1');
INSERT INTO `c_common_lang` VALUES ('25', '603983082961243905', '用户管理', '1', '2020-06-08 19:11:32', '1');
INSERT INTO `c_common_lang` VALUES ('26', '603983082961243905', 'User management', '2', '2020-06-08 19:11:41', '1');
INSERT INTO `c_common_lang` VALUES ('27', '605078371293987105', '数据字典维护', '1', '2020-06-08 19:12:26', '1');
INSERT INTO `c_common_lang` VALUES ('28', '605078371293987105', 'Data dictionary maintenance', '2', '2020-06-08 19:12:34', '1');
INSERT INTO `c_common_lang` VALUES ('29', '605078463069552993', '地区信息维护', '1', '2020-06-08 19:13:18', '1');
INSERT INTO `c_common_lang` VALUES ('30', '605078463069552993', 'Regional information maintenance', '2', '2020-06-08 19:13:48', '1');
INSERT INTO `c_common_lang` VALUES ('31', '605078538881597857', '应用管理', '1', '2020-06-08 19:14:18', '1');
INSERT INTO `c_common_lang` VALUES ('32', '605078538881597857', 'Application management', '2', '2020-06-08 19:14:32', '1');
INSERT INTO `c_common_lang` VALUES ('33', '605078672772170209', '操作日志', '1', '2020-06-08 19:15:12', '1');
INSERT INTO `c_common_lang` VALUES ('34', '605078672772170209', 'Operation log', '2', '2020-06-08 19:15:17', '1');
INSERT INTO `c_common_lang` VALUES ('35', '605078979149300257', '数据库监控', '1', '2020-06-08 19:15:57', '1');
INSERT INTO `c_common_lang` VALUES ('36', '605078979149300257', 'Database monitoring', '2', '2020-06-08 19:16:10', '1');
INSERT INTO `c_common_lang` VALUES ('37', '605079239015793249', '接口文档', '1', '2020-06-08 19:17:37', '1');
INSERT INTO `c_common_lang` VALUES ('38', '605079239015793249', 'Interface documentation', '2', '2020-06-08 19:17:40', '1');
INSERT INTO `c_common_lang` VALUES ('39', '605079411338773153', '注册&配置中心', '1', '2020-06-08 19:18:28', '1');
INSERT INTO `c_common_lang` VALUES ('40', '605079411338773153', 'Registration & configuration center', '2', '2020-06-08 19:18:32', '1');
INSERT INTO `c_common_lang` VALUES ('41', '605079545585861345', '缓存监控', '1', '2020-06-08 19:19:19', '1');
INSERT INTO `c_common_lang` VALUES ('42', '605079545585861345', 'Cache monitoring', '2', '2020-06-08 19:19:22', '1');
INSERT INTO `c_common_lang` VALUES ('43', '605079658416833313', '服务器监控', '1', '2020-06-08 19:20:06', '1');
INSERT INTO `c_common_lang` VALUES ('44', '605079658416833313', 'Server monitoring', '2', '2020-06-08 19:20:09', '1');
INSERT INTO `c_common_lang` VALUES ('45', '605079751035454305', '消息推送', '1', '2020-06-08 19:20:57', '1');
INSERT INTO `c_common_lang` VALUES ('46', '605079751035454305', 'Message push', '2', '2020-06-08 19:21:03', '1');
INSERT INTO `c_common_lang` VALUES ('47', '605080023753294753', '我的消息', '1', '2020-06-08 19:21:44', '1');
INSERT INTO `c_common_lang` VALUES ('48', '605080023753294753', 'My message', '2', '2020-06-08 19:21:50', '1');
INSERT INTO `c_common_lang` VALUES ('49', '605080107379327969', '账号配置', '1', '2020-06-08 19:22:50', '1');
INSERT INTO `c_common_lang` VALUES ('50', '605080107379327969', 'Account configuration', '2', '2020-06-08 19:22:57', '1');
INSERT INTO `c_common_lang` VALUES ('51', '605080359394083937', '短信管理', '1', '2020-06-08 19:24:43', '1');
INSERT INTO `c_common_lang` VALUES ('52', '605080359394083937', 'SMS Management', '2', '2020-06-08 19:24:47', '1');
INSERT INTO `c_common_lang` VALUES ('53', '605080648767505601', '附件列表', '1', '2020-06-08 19:25:27', '1');
INSERT INTO `c_common_lang` VALUES ('54', '605080648767505601', 'Attachment list', '2', '2020-06-08 19:25:32', '1');
INSERT INTO `c_common_lang` VALUES ('55', '605080816296396097', '定时调度中心', '1', '2020-06-09 08:45:54', '1');
INSERT INTO `c_common_lang` VALUES ('56', '605080816296396097', 'Scheduled dispatching center', '2', '2020-06-09 08:46:02', '1');
INSERT INTO `c_common_lang` VALUES ('57', '605424535633666945', '接口查询', '1', '2020-06-09 08:47:58', '1');
INSERT INTO `c_common_lang` VALUES ('58', '605424535633666945', 'Interface query', '2', '2020-06-09 08:48:02', '1');
INSERT INTO `c_common_lang` VALUES ('59', '644111530555611361', '链路调用监控', '1', '2020-06-09 08:49:26', '1');
INSERT INTO `c_common_lang` VALUES ('60', '644111530555611361', 'Link call monitoring', '2', '2020-06-09 08:49:29', '1');
INSERT INTO `c_common_lang` VALUES ('61', '645215230518909025', '登录日志', '1', '2020-06-09 08:50:13', '1');
INSERT INTO `c_common_lang` VALUES ('62', '645215230518909025', 'Login log', '2', '2020-06-09 08:50:18', '1');
INSERT INTO `c_common_lang` VALUES ('63', '1225042542827929600', '参数配置', '1', '2020-06-09 08:51:22', '1');
INSERT INTO `c_common_lang` VALUES ('64', '1225042542827929600', 'Parameter configuration', '2', '2020-06-09 08:51:24', '1');
INSERT INTO `c_common_lang` VALUES ('65', '1268418781550477312', '微信支付', '1', '2020-06-09 08:52:18', '1');
INSERT INTO `c_common_lang` VALUES ('66', '1268418781550477312', 'Wechat payment', '2', '2020-06-09 08:52:21', '1');
INSERT INTO `c_common_lang` VALUES ('68', '1270178969018171392', '语言维护', '1', '2020-06-09 10:40:26', '1');
INSERT INTO `c_common_lang` VALUES ('69', '1270178969018171392', 'Language maintenance', '2', '2020-06-09 10:40:31', '1');
INSERT INTO `c_common_lang` VALUES ('70', 'DATE_LIMIT', '日期参数只能介于0-365天之间', '1', '2020-06-22 10:08:21', '0');
INSERT INTO `c_common_lang` VALUES ('71', 'DATE_LIMIT', 'Date parameter can only be between 0-365 days', '2', '2020-06-22 10:09:07', '0');
INSERT INTO `c_common_lang` VALUES ('72', 'PARAM_ERROR', '参数验证异常', '1', '2020-06-22 10:51:59', '0');
INSERT INTO `c_common_lang` VALUES ('73', 'PARAM_ERROR', 'Parameter validation exception', '2', '2020-06-22 10:52:47', '0');
INSERT INTO `c_common_lang` VALUES ('74', 'USER_VERIFY_PASSWORD', '密码与确认密码不一致', '1', '2020-06-22 11:09:35', '0');
INSERT INTO `c_common_lang` VALUES ('75', 'USER_VERIFY_PASSWORD', 'The password is inconsistent with the confirmation password', '2', '2020-06-22 11:10:08', '0');
INSERT INTO `c_common_lang` VALUES ('76', 'USER_VERIFY_NAME', '用户不存在', '1', '2020-06-22 11:11:57', '0');
INSERT INTO `c_common_lang` VALUES ('77', 'USER_VERIFY_NAME', 'user does not exist', '2', '2020-06-22 11:12:36', '0');
INSERT INTO `c_common_lang` VALUES ('78', 'USER_VERIFY_OLD_PASSWORD', '旧密码错误', '1', '2020-06-22 11:16:18', '0');
INSERT INTO `c_common_lang` VALUES ('79', 'USER_VERIFY_OLD_PASSWORD', 'Old password error', '2', '2020-06-22 11:16:43', '0');
INSERT INTO `c_common_lang` VALUES ('80', 'USER_VERIFY_UPDATE_PASSWORD', '2次输入的密码不一致', '1', '2020-06-22 11:22:30', '0');
INSERT INTO `c_common_lang` VALUES ('81', 'USER_VERIFY_UPDATE_PASSWORD', 'The passwords entered twice are inconsistent', '2', '2020-06-22 11:23:02', '0');
INSERT INTO `c_common_lang` VALUES ('82', 'USER_VERIFY_NAME_EXIST', '账号已经存在', '1', '2020-06-22 11:26:04', '0');
INSERT INTO `c_common_lang` VALUES ('83', 'USER_VERIFY_NAME_EXIST', 'Account already exists', '2', '2020-06-22 11:26:41', '0');
INSERT INTO `c_common_lang` VALUES ('84', 'CODE_REPETITION', '编码重复，请重新输入', '1', '2020-06-22 11:30:28', '0');
INSERT INTO `c_common_lang` VALUES ('85', 'CODE_REPETITION', 'Duplicate code, please re-enter', '2', '2020-06-22 11:30:51', '0');
INSERT INTO `c_common_lang` VALUES ('86', 'TENANT_VERIFY_MODE', '您配置的租户模式:{}不可用', '1', '2020-06-22 11:35:07', '0');
INSERT INTO `c_common_lang` VALUES ('87', 'TENANT_VERIFY_MODE', 'The tenant mode you configured: {} is not available', '2', '2020-06-22 11:35:00', '0');
INSERT INTO `c_common_lang` VALUES ('88', 'ORG_VERIFY_PARENT', '父组织不能为空', '1', '2020-06-22 11:39:05', '0');
INSERT INTO `c_common_lang` VALUES ('89', 'ORG_VERIFY_PARENT', 'Parent organization cannot be empty', '2', '2020-06-22 11:40:00', '0');
INSERT INTO `c_common_lang` VALUES ('90', 'USER_VERIFY_PASSWORD_INPUT', '请输入用户名或密码', '1', '2020-06-22 11:43:15', '0');
INSERT INTO `c_common_lang` VALUES ('91', 'USER_VERIFY_PASSWORD_INPUT', 'Please enter a user name or password', '2', '2020-06-22 11:43:42', '0');
INSERT INTO `c_common_lang` VALUES ('92', 'TENANT_VERIFY_EXIST', '企业不存在', '1', '2020-06-22 11:46:05', '0');
INSERT INTO `c_common_lang` VALUES ('93', 'TENANT_VERIFY_EXIST', 'Enterprise does not exist', '2', '2020-06-22 11:46:31', '0');
INSERT INTO `c_common_lang` VALUES ('94', 'TENANT_VERIFY_NOT_ENABLE', '企业不可用~', '1', '2020-06-22 11:48:33', '0');
INSERT INTO `c_common_lang` VALUES ('95', 'TENANT_VERIFY_NOT_ENABLE', 'Enterprise unavailable~', '2', '2020-06-22 11:48:56', '0');
INSERT INTO `c_common_lang` VALUES ('96', 'TENANT_VERIFY_EXPIRED', '企业服务已到期', '1', '2020-06-22 11:51:57', '0');
INSERT INTO `c_common_lang` VALUES ('97', 'TENANT_VERIFY_EXPIRED', 'Enterprise service expired', '2', '2020-06-22 11:52:38', '0');
INSERT INTO `c_common_lang` VALUES ('98', 'GRENT_VERIFY_TYPE', 'grantType 不支持，请传递正确的 grantType 参数', '1', '2020-06-22 11:55:06', '0');
INSERT INTO `c_common_lang` VALUES ('99', 'GRENT_VERIFY_TYPE', 'Granttype is not supported, please pass the correct granttype parameter', '2', '2020-06-22 11:55:28', '0');
INSERT INTO `c_common_lang` VALUES ('100', 'LOGIN_VERIFY_CID', '请填写正确的客户端ID或者客户端秘钥', '1', '2020-06-22 12:48:48', '0');
INSERT INTO `c_common_lang` VALUES ('101', 'LOGIN_VERIFY_CID', 'Please fill in the correct client ID or client secret key', '2', '2020-06-22 12:49:16', '0');
INSERT INTO `c_common_lang` VALUES ('102', 'LOGIN_VERIFY_CUS_NOT_ENABLE', '客户端[%s]已被禁用', '1', '2020-06-22 12:52:35', '0');
INSERT INTO `c_common_lang` VALUES ('103', 'LOGIN_VERIFY_CUS_NOT_ENABLE', 'Client [% s] has been disabled', '2', '2020-06-22 12:53:00', '0');
INSERT INTO `c_common_lang` VALUES ('104', 'LOGIN_VERIFY_NAME_PASSWORD', '用户名或密码错误!', '1', '2020-06-22 12:58:44', '0');
INSERT INTO `c_common_lang` VALUES ('105', 'LOGIN_VERIFY_NAME_PASSWORD', 'Wrong user name or password!', '2', '2020-06-22 12:59:11', '0');
INSERT INTO `c_common_lang` VALUES ('106', 'LOGIN_VERIFY_PASSWORD_EXPIRED', '用户密码已过期，请修改密码或者联系管理员重置!', '1', '2020-06-22 13:02:08', '0');
INSERT INTO `c_common_lang` VALUES ('107', 'LOGIN_VERIFY_PASSWORD_EXPIRED', 'User password has expired, please change password or contact administrator to reset!', '2', '2020-06-22 13:02:36', '0');
INSERT INTO `c_common_lang` VALUES ('108', 'LOGIN_VERIFY_USER_NOT_ENABLE', '用户被禁用，请联系管理员！', '1', '2020-06-22 13:04:51', '0');
INSERT INTO `c_common_lang` VALUES ('109', 'LOGIN_VERIFY_USER_NOT_ENABLE', 'The user is disabled, please contact the administrator!', '2', '2020-06-22 13:05:15', '0');
INSERT INTO `c_common_lang` VALUES ('110', 'LOGIN_VERIFY_USER_LOCK', '密码连续输错次数已达到{}次,用户已被锁定~', '1', '2020-06-22 13:07:18', '0');
INSERT INTO `c_common_lang` VALUES ('111', 'LOGIN_VERIFY_USER_LOCK', 'The number of consecutive password errors has reached {} and the user has been locked~', '2', '2020-06-22 13:07:41', '0');
INSERT INTO `c_common_lang` VALUES ('112', 'SMS_JOB_NOT_SAVE', '短信任务尚未保存成功', '1', '2020-06-22 13:20:36', '0');
INSERT INTO `c_common_lang` VALUES ('113', 'SMS_JOB_NOT_SAVE', 'SMS task has not been saved successfully', '2', '2020-06-22 13:20:55', '0');
INSERT INTO `c_common_lang` VALUES ('114', 'SMS_MODE_IS_NULL', '短信模板为空', '1', '2020-06-22 13:23:03', '0');
INSERT INTO `c_common_lang` VALUES ('115', 'SMS_MODE_IS_NULL', 'SMS template is empty', '2', '2020-06-22 13:23:25', '0');
INSERT INTO `c_common_lang` VALUES ('116', 'SMS_SUPPLIER_NOT_EXIST', '短信供应商不存在', '1', '2020-06-22 13:32:51', '0');
INSERT INTO `c_common_lang` VALUES ('117', 'SMS_SUPPLIER_NOT_EXIST', 'SMS supplier does not exist', '2', '2020-06-22 13:33:08', '0');
INSERT INTO `c_common_lang` VALUES ('118', 'COPY_FILE_ERROR', '复制文件异常', '1', '2020-06-22 13:39:58', '0');
INSERT INTO `c_common_lang` VALUES ('119', 'COPY_FILE_ERROR', 'Copy file exception', '2', '2020-06-22 13:42:56', '0');
INSERT INTO `c_common_lang` VALUES ('120', 'CREATE_FILE_FAILED', '创建文件失败', '1', '2020-06-22 13:42:32', '0');
INSERT INTO `c_common_lang` VALUES ('121', 'CREATE_FILE_FAILED', 'Failed to create file', '2', '2020-06-22 13:45:59', '0');
INSERT INTO `c_common_lang` VALUES ('122', 'CONVERSION_FILE_FAILED', '文件转换失败', '1', '2020-06-22 13:45:48', '0');
INSERT INTO `c_common_lang` VALUES ('123', 'CONVERSION_FILE_FAILED', 'File conversion failed', '2', '2020-06-22 13:45:36', '0');
INSERT INTO `c_common_lang` VALUES ('124', 'OUT_FILE_FAILED', '文件输出失败', '1', '2020-06-22 13:47:27', '0');
INSERT INTO `c_common_lang` VALUES ('125', 'OUT_FILE_FAILED', 'File output failed', '2', '2020-06-22 13:47:54', '0');
INSERT INTO `c_common_lang` VALUES ('126', 'FILE_URL_CONNECT_TIMEOUT', '文件地址连接超时', '1', '2020-06-22 13:51:46', '0');
INSERT INTO `c_common_lang` VALUES ('127', 'FILE_URL_CONNECT_TIMEOUT', 'File address connection timeout', '2', '2020-06-22 13:52:03', '0');

INSERT INTO `c_common_lang` VALUES ('128', 'MISSING_FIELD_DELETESTATUS', '没有逻辑删除字段deletestatus', '1', '2020-07-23 11:09:35', '0');
INSERT INTO `c_common_lang` VALUES ('129', 'MISSING_FIELD_DELETESTATUS', 'Missing field deletestatus', '2', '2020-07-23 11:10:08', '0');
INSERT INTO `c_common_lang` VALUES ('130', 'FILE_VERIFY_EXIST', '文件不存在', '1', '2020-07-23 11:09:35', '0');
INSERT INTO `c_common_lang` VALUES ('131', 'FILE_VERIFY_EXIST', 'file does not exist', '2', '2020-07-23 11:10:08', '0');
INSERT INTO `c_common_lang` VALUES ('132', 'FILE_PARSING_ERROR', '文件解析错误', '1', '2020-07-23 11:09:35', '0');
INSERT INTO `c_common_lang` VALUES ('133', 'FILE_PARSING_ERROR', 'File parsing error', '2', '2020-07-23 11:10:08', '0');
-- ----------------------------
-- Table structure for `d_global_user`
-- ----------------------------
DROP TABLE IF EXISTS `d_global_user`;
CREATE TABLE `d_global_user` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_code` varchar(10) NOT NULL COMMENT '租户编号',
  `account` varchar(30) NOT NULL COMMENT '账号',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `readonly` bit(1) DEFAULT b'0' COMMENT '是否内置',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全局账号';

-- ----------------------------
-- Records of d_global_user
-- ----------------------------
INSERT INTO `d_global_user` VALUES ('1', 'admin', 'admin', '10086', '超级管理员', '604141215@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '', '2019-08-29 16:50:35', '1', '2019-08-29 16:50:35', '1');
INSERT INTO `d_global_user` VALUES ('2', 'admin', 'demoAdmin', '10086', '超级管理员[演示]', '604141215@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '', '2019-10-30 10:29:21', '1', '2019-10-30 10:29:23', '1');

-- ----------------------------
-- Table structure for `d_tenant`
-- ----------------------------
DROP TABLE IF EXISTS `d_tenant`;
CREATE TABLE `d_tenant` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `code` varchar(20) DEFAULT '' COMMENT '企业编码',
  `name` varchar(255) DEFAULT '' COMMENT '企业名称',
  `type` varchar(10) DEFAULT 'CREATE' COMMENT '类型\n#{CREATE:创建;REGISTER:注册}',
  `status` varchar(10) DEFAULT 'NORMAL' COMMENT '状态\n#{NORMAL:正常;FORBIDDEN:禁用;WAITING:待审核;REFUSE:拒绝}',
  `readonly` bit(1) DEFAULT b'0' COMMENT '是否内置',
  `duty` varchar(50) DEFAULT NULL COMMENT '责任人',
  `expiration_time` datetime DEFAULT NULL COMMENT '有效期\n为空表示永久',
  `logo` varchar(255) DEFAULT '' COMMENT 'logo地址',
  `describe_` varchar(255) DEFAULT '' COMMENT '企业简介',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `lang_type` int(1) DEFAULT '1' COMMENT '1-中文，2-英文',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_CODE` (`code`) USING BTREE COMMENT '租户唯一编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业';

-- ----------------------------
-- Records of d_tenant
-- ----------------------------
INSERT INTO `d_tenant` VALUES ('616676078974402977', '0000', '秦川的内置企业', 'CREATE', 'NORMAL', '', '秦川', null, null, '内置企业，请勿删除', '2019-08-29 16:50:35', '1', '2019-08-29 16:50:35', '1', '2');
INSERT INTO `d_tenant` VALUES ('1268047781868601344', 'hqhc', '成都合奇幻科技有限公司', 'CREATE', 'NORMAL', '', '杨家兵', null, '', '成都合奇幻科技有限公司', '2020-06-03 13:12:05', '2', '2020-06-03 13:12:05', '2', '1');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='AT transaction mode undo table';

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_blob_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_blob_triggers`;
CREATE TABLE `xxl_job_qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_calendars`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_calendars`;
CREATE TABLE `xxl_job_qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_cron_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_cron_triggers`;
CREATE TABLE `xxl_job_qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_fired_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_fired_triggers`;
CREATE TABLE `xxl_job_qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_job_details`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_job_details`;
CREATE TABLE `xxl_job_qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_locks`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_locks`;
CREATE TABLE `xxl_job_qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_locks
-- ----------------------------
INSERT INTO `xxl_job_qrtz_locks` VALUES ('DefaultQuartzScheduler', 'STATE_ACCESS');
INSERT INTO `xxl_job_qrtz_locks` VALUES ('DefaultQuartzScheduler', 'TRIGGER_ACCESS');
INSERT INTO `xxl_job_qrtz_locks` VALUES ('getSchedulerFactoryBean', 'STATE_ACCESS');
INSERT INTO `xxl_job_qrtz_locks` VALUES ('getSchedulerFactoryBean', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for `xxl_job_qrtz_paused_trigger_grps`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_paused_trigger_grps`;
CREATE TABLE `xxl_job_qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_scheduler_state`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_scheduler_state`;
CREATE TABLE `xxl_job_qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_scheduler_state
-- ----------------------------
INSERT INTO `xxl_job_qrtz_scheduler_state` VALUES ('DefaultQuartzScheduler', 'tangyhMacBookPro.local1587831484902', '1587831708064', '5000');
INSERT INTO `xxl_job_qrtz_scheduler_state` VALUES ('getSchedulerFactoryBean', 'tangyhMacBookPro.local1553850279059', '1553850304933', '5000');

-- ----------------------------
-- Table structure for `xxl_job_qrtz_simple_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_simple_triggers`;
CREATE TABLE `xxl_job_qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_simprop_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_simprop_triggers`;
CREATE TABLE `xxl_job_qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_triggers`;
CREATE TABLE `xxl_job_qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `xxl_job_qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_trigger_group`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_group`;
CREATE TABLE `xxl_job_qrtz_trigger_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) NOT NULL COMMENT '执行器名称',
  `order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '排序',
  `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_APP_NAME` (`app_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT=' 任务组';

-- ----------------------------
-- Records of xxl_job_qrtz_trigger_group
-- ----------------------------
INSERT INTO `xxl_job_qrtz_trigger_group` VALUES ('1', 'gmis-jobs-server', 'gmis执行器', '1', '0', null);
INSERT INTO `xxl_job_qrtz_trigger_group` VALUES ('2', 'gmis-executor-server', '分布式执行器', '2', '0', null);

-- ----------------------------
-- Table structure for `xxl_job_qrtz_trigger_info`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_info`;
CREATE TABLE `xxl_job_qrtz_trigger_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) DEFAULT NULL COMMENT '任务执行CRON',
  `start_execute_time` datetime DEFAULT NULL COMMENT '执行时间 和 job_cron人选其一',
  `end_execute_time` datetime DEFAULT NULL COMMENT '执行时间 和 job_cron人选其一',
  `type_` int(11) NOT NULL DEFAULT '1' COMMENT '执行类型 1：cron  2：定时',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `interval_seconds` int(11) DEFAULT NULL COMMENT '间隔秒数',
  `repeat_count` int(11) DEFAULT NULL COMMENT '重复次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_trigger_info
-- ----------------------------
INSERT INTO `xxl_job_qrtz_trigger_info` VALUES ('41', '1', null, '2019-07-08 21:45:00', null, '2', '123', '2019-07-05 10:12:50', '2019-07-08 21:44:36', '秦川', '', 'FIRST', 'demo2JobHandler', 'hello', 'SERIAL_EXECUTION', '0', '0', 'BEAN', '', 'GLUE代码初始化', '2019-07-05 10:12:50', '', '0', '0');
INSERT INTO `xxl_job_qrtz_trigger_info` VALUES ('42', '1', '*/10 * * * * ? ', null, null, '1', '本地执行', '2019-07-07 18:33:16', '2019-07-08 14:49:19', '秦川', '', 'FIRST', 'demo2JobHandler', '', 'SERIAL_EXECUTION', '0', '0', 'BEAN', '', 'GLUE代码初始化', '2019-07-07 18:33:16', '', '0', '0');
INSERT INTO `xxl_job_qrtz_trigger_info` VALUES ('43', '1', '0 0 2 * * ?', null, null, '1', '重置租户', '2020-01-16 18:08:12', '2020-01-16 18:08:12', '秦川', '', 'FIRST', 'restTenantJobHandler', '', 'SERIAL_EXECUTION', '0', '0', 'BEAN', '', 'GLUE代码初始化', '2020-01-16 18:08:12', '', '0', '0');
INSERT INTO `xxl_job_qrtz_trigger_info` VALUES ('44', '1', '0 0 2 * * ?', null, null, '1', '重置默认租户数据', '2020-01-16 18:09:53', '2020-01-16 18:09:53', '秦川', '', 'FIRST', 'restBase0000JobHandler', '', 'SERIAL_EXECUTION', '0', '0', 'BEAN', '', 'GLUE代码初始化', '2020-01-16 18:09:53', '', '0', '0');
INSERT INTO `xxl_job_qrtz_trigger_info` VALUES ('45', '1', '0 0 0/2 * * ?', null, null, '1', '删除过期在线用户', '2020-04-03 10:44:29', '2020-04-03 10:44:29', '秦川', '', 'FIRST', 'userTokenRestJobHandler', '', 'SERIAL_EXECUTION', '0', '0', 'BEAN', '', 'GLUE代码初始化', '2020-04-03 10:44:29', '', '0', '0');
INSERT INTO `xxl_job_qrtz_trigger_info` VALUES ('46', '2', '*/10 * * * * ?', null, null, '1', '演示分布式', '2020-04-11 13:32:34', '2020-04-11 13:32:34', '秦川', '', 'FIRST', 'demo2JobHandler', '', 'SERIAL_EXECUTION', '0', '0', 'BEAN', '', 'GLUE代码初始化', '2020-04-11 13:32:34', '', '0', '0');
INSERT INTO `xxl_job_qrtz_trigger_info` VALUES ('47', '1', null, '2020-04-18 00:19:00', null, '2', '任务描述', '2020-04-18 00:15:00', '2020-04-18 00:15:00', 'admin', null, 'FIRST', 'smsSendJobHandler', '{\"id\":1251182376256536576,\"tenant\":\"1111\"}', 'SERIAL_EXECUTION', '0', '0', 'BEAN', null, null, '2020-04-18 00:15:00', null, '0', '0');
INSERT INTO `xxl_job_qrtz_trigger_info` VALUES ('48', '1', null, '2020-04-18 10:28:00', null, '2', '任务描述', '2020-04-18 10:23:41', '2020-04-18 10:23:41', 'admin', null, 'FIRST', 'smsSendJobHandler', '{\"id\":1251335539450183680,\"tenant\":\"1111\"}', 'SERIAL_EXECUTION', '0', '0', 'BEAN', null, null, '2020-04-18 10:23:41', null, '0', '0');

-- ----------------------------
-- Table structure for `xxl_job_qrtz_trigger_log`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_log`;
CREATE TABLE `xxl_job_qrtz_trigger_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text COMMENT '执行-日志',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `I_trigger_time` (`trigger_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_trigger_log
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_trigger_logglue`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_logglue`;
CREATE TABLE `xxl_job_qrtz_trigger_logglue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_trigger_logglue
-- ----------------------------

-- ----------------------------
-- Table structure for `xxl_job_qrtz_trigger_registry`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_registry`;
CREATE TABLE `xxl_job_qrtz_trigger_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(255) NOT NULL,
  `registry_key` varchar(255) NOT NULL,
  `registry_value` varchar(255) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_trigger_registry
-- ----------------------------
