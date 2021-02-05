/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 127.0.0.1:3306
 Source Schema         : gmis_base_0000

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 26/04/2020 23:25:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for c_auth_application
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
  PRIMARY KEY (`id`) USING BTREE,
  KEY `UN_APP_KEY` (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用';

-- ----------------------------
-- Records of c_auth_application
-- ----------------------------
BEGIN;
INSERT INTO `c_auth_application` VALUES (1, 'gmis_ui', 'gmis_ui_secret', 'http://tangyh.top:10000/gmis-ui/', 'SaaS微服务管理后台', '', 'PC', '内置', b'1', 1, '2020-04-02 15:05:14', 1, '2020-04-02 15:05:17');
INSERT INTO `c_auth_application` VALUES (2, 'gmis_admin_ui', 'gmis_admin_ui_secret', 'http://tangyh.top:180/gmis-admin-ui/', 'SaaS微服务超级管理后台', '', 'PC', '内置', b'1', 1, '2020-04-02 15:05:22', 1, '2020-04-02 15:05:19');
COMMIT;

-- ----------------------------
-- Table structure for c_auth_menu
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
  PRIMARY KEY (`id`) USING BTREE,
  KEY `INX_STATUS` (`is_enable`,`is_public`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单';

-- ----------------------------
-- Records of c_auth_menu
-- ----------------------------
BEGIN;
INSERT INTO `c_auth_menu` VALUES (101, '用户中心', '用户组织机构', b'0', '/user', 'Layout', b'1', 1, 'el-icon-user-solid', '', 0, 1, '2019-07-25 15:35:12', 3, '2019-11-11 14:32:02');
INSERT INTO `c_auth_menu` VALUES (102, '权限管理', '管理权限相关', b'0', '/auth', 'Layout', b'1', 2, 'el-icon-lock', '', 0, 1, '2019-07-27 11:48:49', 3, '2019-11-11 14:35:39');
INSERT INTO `c_auth_menu` VALUES (103, '基础配置', '基础的配置', b'0', '/base', 'Layout', b'1', 3, 'el-icon-set-up', '', 0, 1, '2019-11-11 14:38:29', 3, '2019-11-11 14:35:42');
INSERT INTO `c_auth_menu` VALUES (104, '开发者管理', '开发者', b'0', '/developer', 'Layout', b'1', 4, 'el-icon-user-solid', '', 0, 1, '2019-11-11 14:38:34', 3, '2019-11-11 14:35:44');
INSERT INTO `c_auth_menu` VALUES (105, '消息中心', '站内信', b'0', '/msgs', 'Layout', b'1', 5, 'el-icon-chat-line-square', '', 0, 1, '2019-11-11 14:38:32', 3, '2019-11-11 14:35:47');
INSERT INTO `c_auth_menu` VALUES (106, '短信中心', '短信接口', b'0', '/sms', 'Layout', b'1', 6, 'el-icon-chat-line-round', '', 0, 1, '2019-11-11 14:38:36', 3, '2019-11-11 14:35:49');
INSERT INTO `c_auth_menu` VALUES (107, '文件中心', '附件接口', b'0', '/file', 'Layout', b'1', 7, 'el-icon-folder-add', '', 0, 1, '2019-11-11 14:38:38', 3, '2019-11-11 14:35:51');
INSERT INTO `c_auth_menu` VALUES (603976297063910529, '菜单配置', '', b'0', '/auth/menu', 'gmis/auth/menu/Index', b'1', 0, '', '', 102, 1, '2019-07-25 15:46:11', 3, '2019-11-11 14:31:52');
INSERT INTO `c_auth_menu` VALUES (603981723864141121, '角色管理', '', b'0', '/auth/role', 'gmis/auth/role/Index', b'1', 1, '', '', 102, 1, '2019-07-25 16:07:45', 3, '2019-11-11 14:31:57');
INSERT INTO `c_auth_menu` VALUES (603982542332235201, '组织管理', '', b'0', '/user/org', 'gmis/user/org/Index', b'1', 0, '', '', 101, 1, '2019-07-25 16:11:00', 3, '2019-11-11 14:28:40');
INSERT INTO `c_auth_menu` VALUES (603982713849908801, '岗位管理', '', b'0', '/user/station', 'gmis/user/station/Index', b'1', 1, '', '', 101, 1, '2019-07-25 16:11:41', 3, '2019-11-11 14:28:43');
INSERT INTO `c_auth_menu` VALUES (603983082961243905, '用户管理', '', b'0', '/user/user', 'gmis/user/user/Index', b'1', 2, '', '', 101, 1, '2019-07-25 16:13:09', 3, '2019-11-11 14:28:49');
INSERT INTO `c_auth_menu` VALUES (605078371293987105, '数据字典维护', '', b'0', '/base/dict', 'gmis/base/dict/Index', b'1', 0, '', '', 103, 1, '2019-07-28 16:45:26', 3, '2019-11-11 14:34:23');
INSERT INTO `c_auth_menu` VALUES (605078463069552993, '地区信息维护', '', b'0', '/base/area', 'gmis/base/area/Index', b'1', 1, '', '', 103, 1, '2019-07-28 16:45:48', 3, '2019-11-11 14:34:26');
INSERT INTO `c_auth_menu` VALUES (605078538881597857, '应用管理', '', b'0', '/developer/application', 'gmis/developer/application/Index', b'1', 0, '', '', 104, 1, '2019-07-28 16:46:06', 3, '2019-12-25 16:19:43');
INSERT INTO `c_auth_menu` VALUES (605078672772170209, '操作日志', '', b'0', '/developer/optLog', 'gmis/developer/optLog/Index', b'1', 3, '', '', 104, 1, '2019-07-28 16:46:38', 3, '2019-11-11 14:35:14');
INSERT INTO `c_auth_menu` VALUES (605078979149300257, '数据库监控', '', b'0', '/developer/db', 'gmis/developer/db/Index', b'1', 2, '', '', 104, 1, '2019-07-28 16:47:51', 3, '2019-11-16 16:35:50');
INSERT INTO `c_auth_menu` VALUES (605079239015793249, '接口文档', '', b'0', 'http://127.0.0.1:8760/api/gate/doc.html', 'Layout', b'1', 5, '', '', 104, 1, '2019-07-28 16:48:53', 3, '2019-11-16 10:55:03');
INSERT INTO `c_auth_menu` VALUES (605079411338773153, '注册&配置中心', '', b'0', 'http://127.0.0.1:8848/nacos', 'Layout', b'1', 6, '', '', 104, 1, '2019-07-28 16:49:34', 3, '2019-11-16 10:55:06');
INSERT INTO `c_auth_menu` VALUES (605079545585861345, '缓存监控', '', b'0', 'http://www.baidu.com', 'Layout', b'1', 7, '', '', 104, 1, '2019-07-28 16:50:06', 3, '2019-11-16 10:55:08');
INSERT INTO `c_auth_menu` VALUES (605079658416833313, '服务器监控', '', b'0', 'http://127.0.0.1:8762/gmis-monitor', 'Layout', b'1', 8, '', '', 104, 1, '2019-07-28 16:50:33', 3, '2019-11-16 10:55:15');
INSERT INTO `c_auth_menu` VALUES (605079751035454305, '消息推送', '', b'0', '/msgs/sendMsgs', 'gmis/msgs/sendMsgs/Index', b'1', 0, '', '', 105, 1, '2019-07-28 16:50:55', 3, '2019-11-11 14:28:30');
INSERT INTO `c_auth_menu` VALUES (605080023753294753, '我的消息', '', b'0', '/msgs/myMsgs', 'gmis/msgs/myMsgs/Index', b'1', 1, '', '', 105, 1, '2019-07-28 16:52:00', 3, '2019-11-11 14:28:27');
INSERT INTO `c_auth_menu` VALUES (605080107379327969, '账号配置', '', b'0', '/sms/template', 'gmis/sms/template/Index', b'1', 1, '', '', 106, 1, '2019-07-28 16:52:20', 3, '2019-11-21 19:53:17');
INSERT INTO `c_auth_menu` VALUES (605080359394083937, '短信管理', '', b'0', '/sms/manage', 'gmis/sms/manage/Index', b'1', 0, '', '', 106, 1, '2019-07-28 16:53:20', 3, '2019-11-21 19:53:09');
INSERT INTO `c_auth_menu` VALUES (605080648767505601, '附件列表', '', b'0', '/file/attachment', 'gmis/file/attachment/Index', b'1', 0, '', '', 107, 1, '2019-07-28 16:54:29', 3, '2019-11-11 14:28:07');
INSERT INTO `c_auth_menu` VALUES (605080816296396097, '定时调度中心', '', b'0', 'http://127.0.0.1:8767/gmis-jobs-server', 'Layout', b'1', 9, '', '', 104, 1, '2019-07-28 16:55:09', 3, '2019-11-16 10:55:18');
INSERT INTO `c_auth_menu` VALUES (605424535633666945, '接口查询', '', b'0', '/developer/systemApi', 'gmis/developer/systemApi/Index', b'1', 1, '', '', 104, 1, '2019-07-29 15:40:58', 3, '2019-12-24 14:40:47');
INSERT INTO `c_auth_menu` VALUES (644111530555611361, '链路调用监控', '', b'0', 'http://127.0.0.1:8772/zipkin', 'Layout', b'1', 10, '', '', 104, 3, '2019-11-13 09:49:16', 3, '2019-11-13 09:56:51');
INSERT INTO `c_auth_menu` VALUES (645215230518909025, '登录日志', '', b'0', '/developer/loginLog', 'gmis/developer/loginLog/Index', b'1', 4, '', '', 104, 3, '2019-11-16 10:54:59', 3, '2019-11-16 10:54:59');
INSERT INTO `c_auth_menu` VALUES (1225042542827929600, '参数配置', '', b'0', '/base/parameter', 'gmis/base/parameter/Index', b'1', 3, '', '', 103, 3, '2020-02-05 21:04:37', 3, '2020-02-05 21:04:37');
COMMIT;

-- ----------------------------
-- Table structure for c_auth_resource
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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_CODE` (`code`) USING BTREE COMMENT '编码唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源';

-- ----------------------------
-- Records of c_auth_resource
-- ----------------------------
BEGIN;
INSERT INTO `c_auth_resource` VALUES (643444685339100193, 'org:add', '添加', 603982542332235201, '', 3, '2019-11-11 13:39:28', 3, '2019-11-11 13:39:50');
INSERT INTO `c_auth_resource` VALUES (643444685339100194, 'role:config', '配置', 603981723864141121, '', 3, '2019-11-11 13:39:28', 3, '2019-11-11 13:39:50');
INSERT INTO `c_auth_resource` VALUES (643444685339100195, 'resource:add', '添加', 603976297063910529, '', 3, '2019-11-11 13:39:28', 3, '2019-11-11 13:39:50');
INSERT INTO `c_auth_resource` VALUES (643444685339100196, 'resource:update', '修改', 603976297063910529, '', 3, '2019-11-11 13:39:28', 3, '2019-11-11 13:39:50');
INSERT INTO `c_auth_resource` VALUES (643444685339100197, 'resource:delete', '删除', 603976297063910529, '', 3, '2019-11-11 13:39:28', 3, '2019-11-11 13:39:50');
INSERT INTO `c_auth_resource` VALUES (643444685339100198, 'resource:view', '查看', 603976297063910529, '', 3, '2020-04-05 19:02:42', 3, '2020-04-05 19:02:46');
INSERT INTO `c_auth_resource` VALUES (643444819758154945, 'org:update', '修改', 603982542332235201, '', 3, '2019-11-11 13:40:00', 3, '2019-11-11 13:40:00');
INSERT INTO `c_auth_resource` VALUES (643444858974897441, 'org:delete', '删除', 603982542332235201, '', 3, '2019-11-11 13:40:09', 3, '2019-11-11 13:40:09');
INSERT INTO `c_auth_resource` VALUES (643444897201784193, 'org:view', '查询', 603982542332235201, '', 3, '2019-11-11 13:40:18', 3, '2019-11-11 13:40:18');
INSERT INTO `c_auth_resource` VALUES (643444992357959137, 'org:import', '导入', 603982542332235201, '', 3, '2019-11-11 13:40:41', 3, '2019-11-11 13:40:41');
INSERT INTO `c_auth_resource` VALUES (643445016773002817, 'org:export', '导出', 603982542332235201, '', 3, '2019-11-11 13:40:47', 3, '2019-11-11 13:40:47');
INSERT INTO `c_auth_resource` VALUES (643445116756821697, 'station:add', '添加', 603982713849908801, '', 3, '2019-11-11 13:41:11', 3, '2019-11-11 13:41:11');
INSERT INTO `c_auth_resource` VALUES (643445162915137313, 'station:update', '修改', 603982713849908801, '', 3, '2019-11-11 13:41:22', 3, '2019-11-11 13:41:22');
INSERT INTO `c_auth_resource` VALUES (643445197954353025, 'station:delete', '删除', 603982713849908801, '', 3, '2019-11-11 13:41:30', 3, '2019-11-11 13:41:30');
INSERT INTO `c_auth_resource` VALUES (643445229575210977, 'station:view', '查看', 603982713849908801, '', 3, '2019-11-11 13:41:38', 3, '2019-11-11 13:41:38');
INSERT INTO `c_auth_resource` VALUES (643445262110427201, 'station:export', '导出', 603982713849908801, '', 3, '2019-11-11 13:41:45', 3, '2019-11-11 13:41:45');
INSERT INTO `c_auth_resource` VALUES (643445283996305569, 'station:import', '导入', 603982713849908801, '', 3, '2019-11-11 13:41:51', 3, '2019-11-11 13:41:51');
INSERT INTO `c_auth_resource` VALUES (643445352703199521, 'user:add', '添加', 603983082961243905, '', 3, '2019-11-11 13:42:07', 3, '2019-11-11 13:42:07');
INSERT INTO `c_auth_resource` VALUES (643445412774021505, 'user:update', '修改', 603983082961243905, '', 3, '2019-11-11 13:42:21', 3, '2019-11-11 13:42:21');
INSERT INTO `c_auth_resource` VALUES (643445448081672673, 'user:delete', '删除', 603983082961243905, '', 3, '2019-11-11 13:42:30', 3, '2019-11-11 13:42:30');
INSERT INTO `c_auth_resource` VALUES (643445477274028609, 'user:view', '查看', 603983082961243905, '', 3, '2019-11-11 13:42:37', 3, '2019-11-11 13:42:37');
INSERT INTO `c_auth_resource` VALUES (643445514607528609, 'user:import', '导入', 603983082961243905, '', 3, '2019-11-11 13:42:46', 3, '2019-11-11 13:42:46');
INSERT INTO `c_auth_resource` VALUES (643445542076025601, 'user:export', '导出', 603983082961243905, '', 3, '2019-11-11 13:42:52', 3, '2019-11-11 13:42:52');
INSERT INTO `c_auth_resource` VALUES (643445641149680705, 'menu:add', '添加', 603976297063910529, '', 3, '2019-11-11 13:43:16', 3, '2019-11-11 13:43:16');
INSERT INTO `c_auth_resource` VALUES (643445674330819745, 'menu:update', '修改', 603976297063910529, '', 3, '2019-11-11 13:43:24', 3, '2019-11-11 13:43:24');
INSERT INTO `c_auth_resource` VALUES (643445704177487105, 'menu:delete', '删除', 603976297063910529, '', 3, '2019-11-11 13:43:31', 3, '2019-11-11 13:43:31');
INSERT INTO `c_auth_resource` VALUES (643445747320098145, 'menu:view', '查看', 603976297063910529, '', 3, '2019-11-11 13:43:41', 3, '2019-11-11 13:43:41');
INSERT INTO `c_auth_resource` VALUES (643445774687931841, 'menu:export', '导出', 603976297063910529, '', 3, '2019-11-11 13:43:48', 3, '2019-11-11 13:43:48');
INSERT INTO `c_auth_resource` VALUES (643445802106097185, 'menu:import', '导入', 603976297063910529, '', 3, '2019-11-11 13:43:54', 3, '2019-11-11 13:43:54');
INSERT INTO `c_auth_resource` VALUES (643448338154263521, 'role:add', '添加', 603981723864141121, '', 3, '2019-11-11 13:53:59', 3, '2019-11-11 13:53:59');
INSERT INTO `c_auth_resource` VALUES (643448369779315777, 'role:update', '修改', 603981723864141121, '', 3, '2019-11-11 13:54:06', 3, '2019-11-11 13:54:06');
INSERT INTO `c_auth_resource` VALUES (643448507767723169, 'role:delete', '删除', 603981723864141121, '', 3, '2019-11-11 13:54:39', 3, '2019-11-11 13:54:39');
INSERT INTO `c_auth_resource` VALUES (643448611161511169, 'role:view', '查看', 603981723864141121, '', 3, '2019-11-11 13:55:04', 3, '2019-11-11 13:55:04');
INSERT INTO `c_auth_resource` VALUES (643448656451605857, 'role:export', '导出', 603981723864141121, '', 3, '2019-11-11 13:55:15', 3, '2019-11-11 13:55:15');
INSERT INTO `c_auth_resource` VALUES (643448730950833601, 'role:import', '导入', 603981723864141121, '', 3, '2019-11-11 13:55:32', 3, '2019-11-11 13:55:32');
INSERT INTO `c_auth_resource` VALUES (643448826945869409, 'role:auth', '授权', 603981723864141121, '', 3, '2019-11-11 13:55:55', 3, '2019-11-11 13:55:55');
INSERT INTO `c_auth_resource` VALUES (643449540959016737, 'dict:add', '添加', 605078371293987105, '', 3, '2019-11-11 13:58:45', 3, '2019-11-11 13:58:45');
INSERT INTO `c_auth_resource` VALUES (643449573045442433, 'dict:update', '修改', 605078371293987105, '', 3, '2019-11-11 13:58:53', 3, '2019-11-11 13:58:53');
INSERT INTO `c_auth_resource` VALUES (643449629173618657, 'dict:view', '查看', 605078371293987105, '', 3, '2019-11-11 13:59:07', 3, '2019-11-11 13:59:07');
INSERT INTO `c_auth_resource` VALUES (643449944866297985, 'dict:delete', '删除', 605078371293987105, '', 3, '2019-11-11 14:00:22', 3, '2019-11-11 14:00:22');
INSERT INTO `c_auth_resource` VALUES (643449998909905121, 'dict:export', '导出', 605078371293987105, '', 3, '2019-11-11 14:00:35', 3, '2019-11-11 14:00:35');
INSERT INTO `c_auth_resource` VALUES (643450072595437889, 'dict:import', '导入', 605078371293987105, '', 3, '2019-11-11 14:00:52', 3, '2019-11-11 14:00:52');
INSERT INTO `c_auth_resource` VALUES (643450171333548481, 'area:add', '添加', 605078463069552993, '', 3, '2019-11-11 14:01:16', 3, '2019-11-11 14:01:16');
INSERT INTO `c_auth_resource` VALUES (643450210449627681, 'area:update', '修改', 605078463069552993, '', 3, '2019-11-11 14:01:25', 3, '2019-11-11 14:01:25');
INSERT INTO `c_auth_resource` VALUES (643450295858240129, 'area:delete', '删除', 605078463069552993, '', 3, '2019-11-11 14:01:45', 3, '2019-11-11 14:01:45');
INSERT INTO `c_auth_resource` VALUES (643450326619265761, 'area:view', '查看', 605078463069552993, '', 3, '2019-11-11 14:01:53', 3, '2019-11-11 14:01:53');
INSERT INTO `c_auth_resource` VALUES (643450551291353921, 'area:export', '导出', 605078463069552993, '', 3, '2019-11-11 14:02:46', 3, '2019-11-11 14:02:46');
INSERT INTO `c_auth_resource` VALUES (643450602218593185, 'area:import', '导入', 605078463069552993, '', 3, '2019-11-11 14:02:59', 3, '2019-11-11 14:02:59');
INSERT INTO `c_auth_resource` VALUES (643450770317909249, 'optLog:view', '查看', 605078672772170209, '', 3, '2019-11-11 14:03:39', 3, '2019-11-11 14:03:39');
INSERT INTO `c_auth_resource` VALUES (643450853134441825, 'optLog:export', '导出', 605078672772170209, '', 3, '2019-11-11 14:03:58', 3, '2019-11-11 14:03:58');
INSERT INTO `c_auth_resource` VALUES (643451893279892129, 'msgs:view', '查看', 605080023753294753, '', 3, '2019-11-11 14:08:06', 3, '2019-11-11 14:08:06');
INSERT INTO `c_auth_resource` VALUES (643451945020826369, 'msgs:delete', '删除', 605080023753294753, '', 3, '2019-11-11 14:08:19', 3, '2019-11-11 14:08:19');
INSERT INTO `c_auth_resource` VALUES (643451994945626977, 'msgs:update', '修改', 605080023753294753, '', 3, '2019-11-11 14:08:31', 3, '2019-11-11 14:08:31');
INSERT INTO `c_auth_resource` VALUES (643452086981239745, 'msgs:export', '导出', 605080023753294753, '', 3, '2019-11-11 14:08:53', 3, '2019-11-11 14:08:53');
INSERT INTO `c_auth_resource` VALUES (643452393857492033, 'sms:template:add', '添加', 605080107379327969, '', 3, '2019-11-11 14:10:06', 3, '2019-11-11 14:10:06');
INSERT INTO `c_auth_resource` VALUES (643452429496493217, 'sms:template:update', '修改', 605080107379327969, '', 3, '2019-11-11 14:10:14', 3, '2019-11-11 14:10:14');
INSERT INTO `c_auth_resource` VALUES (643452458693043457, 'sms:template:view', '查看', 605080107379327969, '', 3, '2019-11-11 14:10:21', 3, '2019-11-11 14:10:21');
INSERT INTO `c_auth_resource` VALUES (643452488447436129, 'sms:template:delete', '删除', 605080107379327969, '', 3, '2019-11-11 14:10:28', 3, '2019-11-11 14:10:28');
INSERT INTO `c_auth_resource` VALUES (643452536954561985, 'sms:template:import', '导入', 605080107379327969, '', 3, '2019-11-11 14:10:40', 3, '2019-11-11 14:10:40');
INSERT INTO `c_auth_resource` VALUES (643452571645650465, 'sms:template:export', '导入', 605080107379327969, '', 3, '2019-11-11 14:10:48', 3, '2019-11-11 14:10:48');
INSERT INTO `c_auth_resource` VALUES (643454073500084577, 'sms:manage:add', '添加', 605080359394083937, '', 3, '2019-11-11 14:16:46', 3, '2019-11-11 14:16:46');
INSERT INTO `c_auth_resource` VALUES (643454110992968129, 'sms:manage:update', '修改', 605080359394083937, '', 3, '2019-11-11 14:16:55', 3, '2019-11-11 14:16:55');
INSERT INTO `c_auth_resource` VALUES (643454150905965089, 'sms:manage:view', '查看', 605080359394083937, '', 3, '2019-11-11 14:17:05', 3, '2019-11-11 14:17:05');
INSERT INTO `c_auth_resource` VALUES (643454825052252961, 'sms:manage:delete', '删除', 605080359394083937, '', 3, '2019-11-11 14:19:45', 3, '2019-11-11 14:19:45');
INSERT INTO `c_auth_resource` VALUES (643455175503129569, 'sms:manage:export', '导出', 605080359394083937, '', 3, '2019-11-11 14:21:09', 3, '2019-11-11 14:26:05');
INSERT INTO `c_auth_resource` VALUES (643455720519380097, 'sms:manage:import', '导入', 605080359394083937, '', 3, '2019-11-11 14:23:19', 3, '2019-11-11 14:23:19');
INSERT INTO `c_auth_resource` VALUES (643456690892582401, 'file:add', '添加', 605080648767505601, '', 3, '2019-11-11 14:27:10', 3, '2019-11-11 14:27:10');
INSERT INTO `c_auth_resource` VALUES (643456724186967649, 'file:update', '修改', 605080648767505601, '', 3, '2019-11-11 14:27:18', 3, '2019-11-11 14:27:18');
INSERT INTO `c_auth_resource` VALUES (643456761927315137, 'file:delete', '删除', 605080648767505601, '', 3, '2019-11-11 14:27:27', 3, '2019-11-11 14:27:27');
INSERT INTO `c_auth_resource` VALUES (643456789920100129, 'file:view', '查看', 605080648767505601, '', 3, '2019-11-11 14:27:34', 3, '2019-11-11 14:27:34');
INSERT INTO `c_auth_resource` VALUES (643456884694593409, 'file:download', '下载', 605080648767505601, '', 3, '2019-11-11 14:27:56', 3, '2019-11-11 14:27:56');
INSERT INTO `c_auth_resource` VALUES (645288214990422241, 'optLog:delete', '删除', 605078672772170209, '', 3, '2019-11-16 15:45:00', 3, '2019-11-16 15:45:00');
INSERT INTO `c_auth_resource` VALUES (645288283693121889, 'loginLog:delete', '删除', 645215230518909025, '', 3, '2019-11-16 15:45:16', 3, '2019-11-16 15:45:16');
INSERT INTO `c_auth_resource` VALUES (645288375300915649, 'loginLog:export', '导出', 645215230518909025, '', 3, '2019-11-16 15:45:38', 3, '2019-11-16 15:45:38');
INSERT INTO `c_auth_resource` VALUES (658002570005972001, 'msgs:add', '新增', 605080023753294753, '', 3, '2019-12-21 17:47:18', 3, '2019-12-21 17:47:18');
INSERT INTO `c_auth_resource` VALUES (658002632467547265, 'msgs:mark', '标记已读', 605080023753294753, '', 3, '2019-12-21 17:47:33', 3, '2019-12-21 17:47:33');
INSERT INTO `c_auth_resource` VALUES (659045058871296641, 'systemApi:add', '新增', 605424535633666945, '', 3, '2019-12-24 14:49:47', 3, '2019-12-24 14:49:47');
INSERT INTO `c_auth_resource` VALUES (659045100646564577, 'systemApi:delete', '删除', 605424535633666945, '', 3, '2019-12-24 14:49:57', 3, '2019-12-24 14:49:57');
INSERT INTO `c_auth_resource` VALUES (659045145735332673, 'systemApi:update', '修改', 605424535633666945, '', 3, '2019-12-24 14:50:07', 3, '2019-12-24 14:50:07');
INSERT INTO `c_auth_resource` VALUES (659045207890723745, 'systemApi:export', '导出', 605424535633666945, '', 3, '2019-12-24 14:50:22', 3, '2019-12-24 14:50:22');
INSERT INTO `c_auth_resource` VALUES (659430164874134689, 'systemApi:view', '查看', 605424535633666945, '', 3, '2019-12-25 16:20:03', 3, '2019-12-25 16:20:03');
INSERT INTO `c_auth_resource` VALUES (659702641965662497, 'application:add', '新增', 605078538881597857, '', 3, '2019-12-26 10:22:47', 3, '2019-12-26 10:22:47');
INSERT INTO `c_auth_resource` VALUES (659702674622513537, 'application:update', '修改', 605078538881597857, '', 3, '2019-12-26 10:22:55', 3, '2019-12-26 10:22:55');
INSERT INTO `c_auth_resource` VALUES (659702756889592289, 'application:delete', '删除', 605078538881597857, '', 3, '2019-12-26 10:23:14', 3, '2019-12-26 10:23:14');
INSERT INTO `c_auth_resource` VALUES (659702791312245313, 'application:view', '查看', 605078538881597857, '', 3, '2019-12-26 10:23:22', 3, '2019-12-26 10:23:22');
INSERT INTO `c_auth_resource` VALUES (659702853945787041, 'application:export', '导出', 605078538881597857, '', 3, '2019-12-26 10:23:37', 3, '2019-12-26 10:23:37');
INSERT INTO `c_auth_resource` VALUES (1225042691843162112, 'parameter:add', '添加', 1225042542827929600, '', 3, '2020-02-05 21:05:13', 3, '2020-02-05 21:05:13');
INSERT INTO `c_auth_resource` VALUES (1225042821497487360, 'parameter:update', '修改', 1225042542827929600, '', 3, '2020-02-05 21:05:43', 3, '2020-02-05 21:05:43');
INSERT INTO `c_auth_resource` VALUES (1225042949172101120, 'parameter:delete', '删除', 1225042542827929600, '', 3, '2020-02-05 21:06:14', 3, '2020-02-05 21:06:14');
INSERT INTO `c_auth_resource` VALUES (1225043012455759872, 'parameter:export', '导出', 1225042542827929600, '', 3, '2020-02-05 21:06:29', 3, '2020-02-05 21:06:29');
INSERT INTO `c_auth_resource` VALUES (1237035586045345792, 'parameter:import', '导入', 1225042542827929600, '', 3, '2020-03-09 23:20:41', 3, '2020-03-09 23:20:41');
INSERT INTO `c_auth_resource` VALUES (1237035798587506688, 'msgs:import', '导入', 605080023753294753, '', 3, '2020-03-09 23:21:32', 3, '2020-03-09 23:21:32');
INSERT INTO `c_auth_resource` VALUES (1246066924136169472, 'parameter:view', '查看', 1225042542827929600, '', 3, '2020-04-03 21:28:00', 3, '2020-04-03 21:28:00');
INSERT INTO `c_auth_resource` VALUES (1246067318035841024, 'loginLog:view', '查看', 645215230518909025, '', 3, '2020-04-03 21:29:34', 3, '2020-04-03 21:29:34');
COMMIT;

-- ----------------------------
-- Table structure for c_auth_role
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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_CODE` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of c_auth_role
-- ----------------------------
BEGIN;
INSERT INTO `c_auth_role` VALUES (100, '平台管理员', 'PT_ADMIN', '平台内置管理员', b'1', b'1', 'ALL', 1, '2019-10-25 13:46:00', 3, '2020-02-13 11:05:28');
COMMIT;

-- ----------------------------
-- Table structure for c_auth_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_role_authority`;
CREATE TABLE `c_auth_role_authority` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `authority_id` bigint(20) NOT NULL COMMENT '资源id\n#c_auth_resource\n#c_auth_menu',
  `authority_type` varchar(10) NOT NULL DEFAULT 'MENU' COMMENT '权限类型\n#AuthorizeType{MENU:菜单;RESOURCE:资源;}',
  `role_id` bigint(20) NOT NULL COMMENT '角色id\n#c_auth_role',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IDX_KEY` (`role_id`,`authority_type`,`authority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色的资源';

-- ----------------------------
-- Records of c_auth_role_authority
-- ----------------------------
BEGIN;
INSERT INTO `c_auth_role_authority` VALUES (643444685339100198, 643444685339100198, 'RESOURCE', 100, '2020-04-05 19:03:08', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572630093824, 643445116756821697, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572646871040, 643444685339100197, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572651065344, 643444685339100196, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572651065345, 643450072595437889, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572651065346, 643444685339100193, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572659453952, 643444685339100195, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572659453953, 659702853945787041, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572663648256, 643444685339100194, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572663648257, 1225042949172101120, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572667842560, 643445197954353025, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572667842561, 1246067318035841024, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572667842562, 1232574830335754240, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572672036864, 1225043012455759872, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572672036865, 643445262110427201, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572672036866, 643450853134441825, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572676231168, 643445774687931841, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572676231169, 1225984228617879552, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572676231170, 659702756889592289, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572680425472, 643450551291353921, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572680425473, 643452458693043457, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572680425474, 643454110992968129, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572684619776, 643445016773002817, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572684619777, 659430164874134689, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572684619778, 643445162915137313, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572688814080, 643445229575210977, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572688814081, 1225984389242945536, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572693008384, 643448369779315777, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572693008385, 643454825052252961, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572697202688, 643456884694593409, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572697202689, 643445704177487105, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572697202690, 659045058871296641, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572701396992, 658002570005972001, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572701396993, 643451893279892129, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572701396994, 643445283996305569, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572705591296, 643451994945626977, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572705591297, 643445542076025601, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572705591298, 645288214990422241, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572709785600, 643450602218593185, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572709785601, 643450326619265761, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572709785602, 643456724186967649, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572713979904, 643445448081672673, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572713979905, 643448611161511169, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572713979906, 643445802106097185, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572718174208, 643448826945869409, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572718174209, 643452536954561985, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572718174210, 1225984359173980160, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572718174211, 643445514607528609, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572722368512, 1246066924136169472, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572722368513, 643448507767723169, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572726562816, 643450295858240129, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572726562817, 643449998909905121, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572726562818, 645288283693121889, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572726562819, 643444897201784193, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572730757120, 643448730950833601, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572730757121, 643456690892582401, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572730757122, 643445412774021505, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572734951424, 1237035586045345792, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572734951425, 643448656451605857, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572734951426, 643450171333548481, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572734951427, 643456761927315137, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572739145728, 643445477274028609, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572739145729, 1225984257483079680, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572739145730, 643445352703199521, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572739145731, 643445747320098145, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572743340032, 643452571645650465, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572743340033, 643444858974897441, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572743340034, 643449944866297985, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572747534336, 643450770317909249, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572747534337, 1225984321857257472, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572747534338, 643455720519380097, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572751728640, 643450210449627681, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572751728641, 659045100646564577, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572751728642, 643444992357959137, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572755922944, 643456789920100129, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572755922945, 659702641965662497, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572755922946, 658002632467547265, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572760117248, 643445641149680705, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572760117249, 643444819758154945, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572764311552, 643449629173618657, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572764311553, 643451945020826369, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572764311554, 643448338154263521, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572768505856, 643449573045442433, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572768505857, 1225042691843162112, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572768505858, 659702791312245313, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572768505859, 659045207890723745, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572772700160, 643452429496493217, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572772700161, 643454073500084577, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572772700162, 643455175503129569, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572772700163, 643445674330819745, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572776894464, 643452393857492033, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572776894465, 1237035798587506688, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572776894466, 643454150905965089, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572781088768, 643452086981239745, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572781088769, 645288375300915649, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572781088770, 643452488447436129, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572781088771, 659702674622513537, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572785283072, 1225042821497487360, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572785283073, 659045145735332673, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572785283074, 643449540959016737, 'RESOURCE', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572785283075, 645215230518909025, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572789477376, 605079411338773153, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572789477377, 605080648767505601, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572789477378, 603983082961243905, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572793671680, 603982542332235201, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572793671681, 603981723864141121, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572793671682, 605424535633666945, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572797865984, 605078371293987105, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572797865985, 644111530555611361, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572797865986, 605079751035454305, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572802060288, 605080023753294753, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572802060289, 605079239015793249, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572802060290, 605078672772170209, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572802060291, 603976297063910529, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572806254592, 605079545585861345, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572806254593, 605080107379327969, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572806254594, 605078979149300257, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572806254595, 101, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572810448896, 605080359394083937, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572810448897, 102, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572810448898, 103, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572810448899, 104, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572814643200, 105, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572814643201, 106, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572814643202, 107, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572814643203, 605078463069552993, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572818837504, 605080816296396097, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572818837505, 1225042542827929600, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572818837506, 603982713849908801, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572823031808, 605079658416833313, 'MENU', 100, '2020-04-03 21:30:35', 3);
INSERT INTO `c_auth_role_authority` VALUES (1246067572823031809, 605078538881597857, 'MENU', 100, '2020-04-03 21:30:35', 3);
COMMIT;

-- ----------------------------
-- Table structure for c_auth_role_org
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
-- Records of c_auth_role_org
-- ----------------------------
BEGIN;
INSERT INTO `c_auth_role_org` VALUES (1227790863468331008, 100, 100, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863493496832, 100, 101, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863497691136, 100, 102, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863497691137, 100, 643775612976106881, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863501885440, 100, 643775664683486689, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863501885441, 100, 643775904077582049, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863506079744, 100, 643776324342648929, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863506079745, 100, 643776407691858113, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863510274048, 100, 643776508795556193, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863514468352, 100, 643776594376135105, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863514468353, 100, 643776633823564321, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863514468354, 100, 643776668917305985, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863518662656, 100, 643776713909605089, '2020-02-13 11:05:28', 3);
INSERT INTO `c_auth_role_org` VALUES (1227790863518662657, 100, 643776757199016769, '2020-02-13 11:05:28', 3);
COMMIT;

-- ----------------------------
-- Table structure for c_auth_system_api
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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UNX_ID` (`id`) USING BTREE,
  UNIQUE KEY `UNX_CODE` (`code`) USING BTREE,
  KEY `service_id` (`service_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='API接口';

-- ----------------------------
-- Records of c_auth_system_api
-- ----------------------------
BEGIN;
INSERT INTO `c_auth_system_api` VALUES (1244245557581447168, '51f8f3180cdab905fa9681c0391705f2', NULL, 'test', '', 'GET', '', 'gmis-authority-server', '/aaaa', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.general.controller.AuthorityGeneralController', 'test', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245558432890880, '1faab62b8a2342bd22b158a343966ce8', NULL, 'test22', '', 'GET', '', 'gmis-authority-server', '/aaa2', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.general.controller.AuthorityGeneralController', 'test22', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245558642606080, '7713bbf4377be59e4ef40648616ffb1f', NULL, '获取当前系统指定枚举', '获取当前系统指定枚举', 'GET', '', 'gmis-authority-server', '/enums', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.general.controller.AuthorityGeneralController', 'enums', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245558684549120, 'e003b95b633f527246a867f9bc90bb4a', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/application', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245558873292800, '7d8a9ab8777327b5e9fd76505f93e423', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/application/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245558906847232, 'e2e1c4d06de2f48ba25c425f952d95f7', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/application/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245558944595968, 'dbe73a034a9a3b471c1083dcb8e60853', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/application/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245558978150400, 'ee95b7e12e6b54b21dc7c54e189c4817', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/application/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'get', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245559024287744, '8659175b4593742adbd12f1e364cc00e', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/application/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245559066230784, '8a9bb52ab9bf03a76b3251546f6c6024', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/application/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245559108173824, 'fbeb18cff593e69a843867d70d6669bf', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/applicationSystemApi', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559234002944, '54ce34a071fb4343788759b512c1121b', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/applicationSystemApi/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559271751680, 'c53c5385e4a2378ce1f114b428ae9c2f', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/applicationSystemApi/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559309500416, '2374eefe01bdf56d0e0d4c6f8e6feeb2', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/applicationSystemApi/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559355637760, 'a8a12f7d426e81761792d74c4ed31fae', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/applicationSystemApi/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'get', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559397580800, 'bbdfc29ac99dfefb134fa361e8507e8d', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/applicationSystemApi/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559435329536, '7304b6c81ee0d1ca9e6afd5ee833621f', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/applicationSystemApi/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559473078272, '678882d4a57f76a62c93488aae93a03c', NULL, '验证验证码', '验证验证码', 'GET', '', 'gmis-authority-server', '/anno/check', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.auth.LoginController', 'check', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559506632704, 'f65333a9bbac3d4a349bb92147cee6f3', NULL, '验证token', '验证token', 'GET', '', 'gmis-authority-server', '/anno/verify', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.auth.LoginController', 'verify', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559544381440, '355be064dc3aeeafb3a817b099bbc714', NULL, '仅供测试使用的登录方法', '仅供测试使用的登录方法', 'GET', '', 'gmis-authority-server', '/anno/token', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.auth.LoginController', 'tokenTx', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559577935872, '42ca64dee47034fa4d067d52851ac12d', NULL, '登录', '登录', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/anno/login', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.auth.LoginController', 'loginTx', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559611490304, 'e70c83f57f74ec8ddc5b588a9778a028', NULL, '超级管理员登录', '超级管理员登录', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/anno/admin/login', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.auth.LoginController', 'loginAdminTx', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559645044736, 'c0d7b954ce1953a4522bd1c1d5987057', NULL, '验证码', '验证码', 'GET', 'image/png', 'gmis-authority-server', '/anno/captcha', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.auth.LoginController', 'captcha', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559678599168, '05bfa6333a2a4219fa3a2e61ebefa12d', NULL, '查询系统所有的菜单', '查询系统所有的菜单', 'GET', '', 'gmis-authority-server', '/menu/tree', b'1', b'1', b'1', b'0', 'MenuController', 'allTree', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245559707959296, '4e49a2a76a1985d791ad686b1378c2f9', NULL, '查询用户可用的所有菜单路由树', '查询用户可用的所有菜单路由树', 'GET', '', 'gmis-authority-server', '/menu/router', b'1', b'1', b'1', b'0', 'MenuController', 'myRouter', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559737319424, 'd0250c0959d522116f1e485a17d7dcea', NULL, '查询用户可用的所有菜单', '查询用户可用的所有菜单', 'GET', '', 'gmis-authority-server', '/menu/menus', b'1', b'1', b'1', b'0', 'MenuController', 'myMenus', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559766679552, 'db398a0e2dc2d1e0728f97491ecf9951', NULL, '查询超管菜单路由树', '查询超管菜单路由树', 'GET', '', 'gmis-authority-server', '/menu/admin/router', b'1', b'1', b'1', b'0', 'MenuController', 'adminRouter', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245559796039680, '1fd5d37730a2fd5567b0aa980aae6e6e', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/menu/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.SuperCacheController', 'get', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245559825399808, '81a6466f3e4be8d531236d85dd69289d', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/menu', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245559959617536, 'ca1a020c1e30317f7aede5f87ea1eb19', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/menu/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560001560576, '9dd9d158b04220f1228c09ef0315b2e0', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/menu/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560030920704, '689eaa64156a9153014f445fb5c51a1e', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/menu/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560064475136, '5a301f511ff1f55902d7bb4e3876b731', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/menu/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560098029568, '2bdaa93443715b0716a55658af47b3a0', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/menu/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560123195392, 'c5ed8aaef3be23ad7cc47e535f8d3e1c', NULL, '查询用户可用的所有资源', '查询用户可用的所有资源', 'GET', '', 'gmis-authority-server', '/resource/visible', b'1', b'1', b'1', b'0', 'ResourceController', 'visible', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245560148361216, '644af62f1ff0b7f672c89e26a1eedfc9', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/resource/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.SuperCacheController', 'get', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560186109952, 'e7236c62a99641f27a7b1af638a3fc0c', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/resource', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560299356160, 'a19e67f7083c2c74accd74638e6519aa', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/resource/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560324521984, '6aa5632ce8868d2d3d32673b982e77ae', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/resource/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560353882112, 'e86467025865e24eab760373b6c06bf4', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/resource/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560383242240, '95abfbb2b5307a2d3d8774f9854c8091', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/resource/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560408408064, 'a1bbd89c6ce5181732bc556b32846690', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/resource/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560437768192, 'f228d388b7d803ac7c817b3b39f01f55', NULL, '查询指定角色关联的菜单和资源', '查询指定角色关联的菜单和资源', 'GET', '', 'gmis-authority-server', '/roleAuthority/{roleId}', b'1', b'1', b'1', b'0', 'RoleAuthorityController', 'queryByRoleId', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560462934016, 'ed868d0471522f634f53052070a1ae58', NULL, '检测角色编码', '检测角色编码', 'GET', '', 'gmis-authority-server', '/role/check/{code}', b'1', b'1', b'1', b'0', 'RoleController', 'check', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560488099840, 'b46cc8757b54b5b6cbaa0aa0e297bee6', NULL, '查询角色', '查询角色', 'GET', '', 'gmis-authority-server', '/role/details/{id}', b'1', b'1', b'1', b'0', 'RoleController', 'getDetails', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560517459968, '03a26cffab03f29bf38b4fa1ec34ab57', NULL, '给用户分配角色', '给用户分配角色', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/role/user', b'1', b'1', b'1', b'0', 'RoleController', 'saveUserRole', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560546820096, 'ee2d54108512752de45887e1f60cb9eb', NULL, '查询角色的用户', '查询角色的用户', 'GET', '', 'gmis-authority-server', '/role/user/{roleId}', b'1', b'1', b'1', b'0', 'RoleController', 'findUserIdByRoleId', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560571985920, 'b38900701680eef9a9cc14e0eda2f69f', NULL, '查询角色拥有的资源id集合', '查询角色拥有的资源id集合', 'GET', '', 'gmis-authority-server', '/role/authority/{roleId}', b'1', b'1', b'1', b'0', 'RoleController', 'findAuthorityIdByRoleId', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560601346048, '4e3c451da6a66bd55e76888fe673f141', NULL, '给角色配置权限', '给角色配置权限', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/role/authority', b'1', b'1', b'1', b'0', 'RoleController', 'saveRoleAuthority', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560630706176, '06596822d699ef7bfe5e131b36d65776', NULL, '根据角色编码查询用户ID', '根据角色编码查询用户ID', 'GET', '', 'gmis-authority-server', '/role/codes', b'1', b'1', b'1', b'0', 'RoleController', 'findUserIdByCode', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560660066304, '302c4fa916d8184683ed5abd381811ae', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/role/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.SuperCacheController', 'get', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560685232128, '42161352297085d54bad1f8728a6b19b', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/role', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560785895424, '5176ff97d2d49af2cae06c95bf9ed73e', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/role/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560811061248, 'ce20a2d0d4906e905326aebbc25f2fe0', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/role/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560840421376, '6090e85a7201da86894e15c1deb96c97', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/role/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560869781504, '69cd04039922559a73c08c783a14a445', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/role/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560903335936, '97292907dc8836ac3c9b1854674ad0c6', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/role/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560932696064, '475e7c2c9467348d1825259951bde2f2', NULL, '批量新增API接口', '批量新增API接口不为空的字段', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/systemApi/batch', b'1', b'1', b'1', b'0', 'SystemApiController', 'batchSave', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245560957861888, '86acf848bc28ad4249d17833744d1617', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/systemApi/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.SuperCacheController', 'get', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245560987222016, 'c4e2912bc64ce88324268c3e4ac5a7af', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/systemApi', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561075302400, '14733254e4a1c437939410b3da9e7ebc', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/systemApi/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561108856832, '9c9105a6035f448a5a9af1379a9a648e', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/systemApi/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561146605568, '141569dbf64423e2ace5ceb3171f4ffc', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/systemApi/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561180160000, 'ffa35e6480e4c23ccd3b1173bc5db496', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/systemApi/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561205325824, 'ed8f03aa43fa2b78f42c5ad66034825c', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/systemApi/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561234685952, '1832915cafcb033578fc0c9d05633340', NULL, '注册用户', '注册用户', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/user/anno/register', b'1', b'1', b'1', b'0', 'UserController', 'register', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245561259851776, 'ce1912ed212f11abfde5fd192ed8c69a', NULL, '查询用户详细', '查询用户详细', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/user/anno/id/{id}', b'1', b'1', b'1', b'0', 'UserController', 'getById', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245561285017600, '012cc91e86d356d231dd49af72d5daff', NULL, '查询用户权限范围', '根据用户id，查询用户权限范围', 'GET', '', 'gmis-authority-server', '/user/ds/{id}', b'1', b'1', b'1', b'0', 'UserController', 'getDataScopeById', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245561314377728, '9506e12001309981dba7a63c3e2ca62e', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/user/import', b'1', b'1', b'1', b'0', 'UserController', 'importExcel', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561339543552, '0927bb92ec0608f9ba0a8ed98d21d1ff', NULL, '修改头像', '修改头像', 'PUT', 'application/json;charset=UTF-8', 'gmis-authority-server', '/user/avatar', b'1', b'1', b'1', b'0', 'UserController', 'avatar', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561368903680, '2cab52a12a8f55aa8c23620bf7312031', NULL, '重置密码', '重置密码', 'GET', '', 'gmis-authority-server', '/user/reset', b'1', b'1', b'1', b'0', 'UserController', 'resetTx', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561394069504, 'e5daaab33ba67529a60a5c9b720365c6', NULL, '修改密码', '修改密码', 'PUT', 'application/json;charset=UTF-8', 'gmis-authority-server', '/user/password', b'1', b'1', b'1', b'0', 'UserController', 'updatePassword', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561415041024, 'd239d9c6971664fdc0df2c3d53b5c31a', NULL, '查询角色的已关联用户', '查询角色的已关联用户', 'GET', '', 'gmis-authority-server', '/user/role/{roleId}', b'1', b'1', b'1', b'0', 'UserController', 'findUserByRoleId', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561444401152, '6b4f7b5012ed9dd90a7512a5839fa1e9', NULL, '查询所有用户', '查询所有用户', 'GET', '', 'gmis-authority-server', '/user/find', b'1', b'1', b'1', b'0', 'UserController', 'findAllUserId', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561469566976, 'cf6c032aff0e3f62cc5e9150a6c9dde3', NULL, '根据id查询用户', '根据id查询用户', 'GET', '', 'gmis-authority-server', '/user/findUserByIds', b'1', b'1', b'1', b'0', 'UserController', 'findUserByIds', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245561498927104, 'e0c5a78953aba9df541d5144af20905a', NULL, '根据id查询用户名称', '根据id查询用户名称', 'GET', '', 'gmis-authority-server', '/user/findUserNameByIds', b'1', b'1', b'1', b'0', 'UserController', 'findUserNameByIds', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245561524092928, '77533d25e5836d4b90f8782838a0a35e', NULL, '清除缓存并重新加载数据', '清除缓存并重新加载数据', 'POST', '', 'gmis-authority-server', '/user/reload', b'1', b'1', b'1', b'0', 'UserController', 'reload', NULL, '2020-03-29 20:50:33', NULL, '2020-03-29 20:50:33');
INSERT INTO `c_auth_system_api` VALUES (1244245561553453056, '26efb9dba778270aa49bd856f917938a', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/user/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.SuperCacheController', 'get', NULL, '2020-03-29 20:50:33', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561587007488, 'f62e727d9e445f2b0d1e26ca25d68a2e', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/user', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561666699264, 'f5ff1e38323368e8f8f3dcfbb23789fe', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/user/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1244245561696059392, '915485bb5948b8cdcbad10f8f60c1614', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/user/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245561721225216, '70bca0dffa94667b1925acba11ad2c70', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/user/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245561746391040, '3dc84fb2cfeffab8a76363a80435e319', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/user/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245561767362560, '500eeb51a14559e6c62b2a4692f3604f', NULL, '检测地区编码是否重复', '检测地区编码是否重复', 'GET', '', 'gmis-authority-server', '/area/check/{code}', b'1', b'1', b'1', b'0', 'AreaController', 'check', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245561800916992, 'ad2a1de87ef1c2d73eb9a92da015e5c1', NULL, '级联查询缓存中的地区', '级联查询缓存中的地区', 'GET', '', 'gmis-authority-server', '/area/linkage', b'1', b'1', b'1', b'0', 'AreaController', 'linkageQuery', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245561826082816, 'de91592df81d96c6e01e4c82cd5ad1ea', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/area/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.SuperCacheController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245561893191680, '5e4f598b0e37f4728d721b3f6c5779f3', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/area', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562098712576, '090a7a869d52645cf7c71ae44229ae85', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/area/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562123878400, '9546b8050dec0974417e0b631aa762be', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/area/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562144849920, '383a7a888e5b2e56be47a3ece75d485a', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/area/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562170015744, 'df93f9777ae862155c4a8836a6a547d0', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/area/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562195181568, '3cb4cff6705839a73395eda8d5169d6c', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/area/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562216153088, 'e0145e055aa0333357d480a84c066274', NULL, 'index', '', 'GET', '', 'gmis-authority-server', '/dashboard/visit', b'1', b'1', b'1', b'0', 'DashboardController', 'index', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562241318912, '61a8ec3f9f0fb31c0f0cce677d25fead', NULL, 'generate', '', 'GET', '', 'gmis-authority-server', '/common/generateId', b'1', b'1', b'1', b'0', 'DashboardController', 'generate', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562266484736, '5857987ddbd1e203625adb654cd4de38', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/dictionary', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562341982208, 'b4248e5dac67cf1fa919239916b5d373', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/dictionary/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562362953728, 'feb0cc6c2e4c33aba67068ca826cd68e', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/dictionary/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562396508160, 'c128cd7caa940707000381e7d3276c93', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/dictionary/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562417479680, 'bc47aeaa0295b2fae71979e9980733cd', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/dictionary/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562442645504, 'a0efa5647959acd6fc2edcd81e93377e', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/dictionary/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562467811328, '393803a8988f92bae1986fe457eaf7ce', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/dictionary/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562488782848, 'e68dd4f1b81ba9506fc171194e8949a2', NULL, '根据类型查询字典条目', '根据类型查询字典条目', 'GET', '', 'gmis-authority-server', '/dictionaryItem/codes', b'1', b'1', b'1', b'0', 'DictionaryItemController', 'list', NULL, '2020-03-29 20:50:34', NULL, '2020-03-29 20:50:34');
INSERT INTO `c_auth_system_api` VALUES (1244245562513948672, 'e715298f573446743810b8bcc03d8ac0', NULL, '查询字典项', '根据id查询字典项', 'GET', '', 'gmis-authority-server', '/dictionaryItem/findDictionaryItem', b'1', b'1', b'1', b'0', 'DictionaryItemController', 'findDictionaryItem', NULL, '2020-03-29 20:50:34', NULL, '2020-03-29 20:50:34');
INSERT INTO `c_auth_system_api` VALUES (1244245562539114496, '6d7ff5849ae6e8cf58be9918813780b7', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/dictionaryItem/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.SuperCacheController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562568474624, '9e8f9b5df49cde8cc782516527e5ac36', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/dictionaryItem', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562648166400, '4e25480cb28a8d1f1580cd2e11de898c', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/dictionaryItem/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562673332224, '7b35006eb0f8ea96744b7c38f7fb497a', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/dictionaryItem/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562694303744, '1b53cfe50aef97b50a6c5800b4a90632', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/dictionaryItem/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562719469568, '44b4da7be1c536ec73209a8cb6cb2751', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/dictionaryItem/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562740441088, 'eede0406d427d008fcf59a75f7b81ad7', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/dictionaryItem/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562765606912, 'de4868b58e0027583532c4d511b609f7', NULL, '数据读取', '数据读取', 'GET', '', 'gmis-authority-server', '/j2cache/get', b'1', b'1', b'1', b'0', 'J2CacheController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562786578432, '1bc45ca8e726b0642c1bad8d041bc6a5', NULL, '清理', '清理', 'GET', '', 'gmis-authority-server', '/j2cache/clear', b'1', b'1', b'1', b'0', 'J2CacheController', 'clear', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562815938560, 'a2fecc705aae127ed9b9bb023e6e359e', NULL, '检测存在那级缓存', '检测存在那级缓存', 'GET', '', 'gmis-authority-server', '/j2cache/check', b'1', b'1', b'1', b'0', 'J2CacheController', 'check', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562845298688, 'e6f67b6bc6084e940332b5f83f4dbb64', NULL, '获取缓存的所有key', '获取缓存的所有key', 'GET', '', 'gmis-authority-server', '/j2cache/keys', b'1', b'1', b'1', b'0', 'J2CacheController', 'keys', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562878853120, '03a942e6dd7e1971e695bbf0197fb11c', NULL, '数据写入', '数据写入', 'GET', '', 'gmis-authority-server', '/j2cache/set', b'1', b'1', b'1', b'0', 'J2CacheController', 'set', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562904018944, '7035709eae4415c0e3a7b546db37f840', NULL, '检测是否存在', '检测是否存在', 'GET', '', 'gmis-authority-server', '/j2cache/exists', b'1', b'1', b'1', b'0', 'J2CacheController', 'exists', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562924990464, '843c63e6b8cedf399de69c6e5b90ebd2', NULL, '删除1级缓存 Region', '删除1级缓存 Region', 'GET', '', 'gmis-authority-server', '/j2cache/removeRegion', b'1', b'1', b'1', b'0', 'J2CacheController', 'regions', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562950156288, '41a6d99e7437962ab827860f8a75e675', NULL, '慎用！获取所有的缓存！慎用！', '慎用！获取所有的缓存！慎用！', 'GET', '', 'gmis-authority-server', '/j2cache/regions', b'1', b'1', b'1', b'0', 'J2CacheController', 'regions', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562975322112, '51fc92a32d436cb112ac3a9143a1e40c', NULL, '淘汰缓存', '淘汰缓存', 'GET', '', 'gmis-authority-server', '/j2cache/evict', b'1', b'1', b'1', b'0', 'J2CacheController', 'evict', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245562996293632, '5cb22c5edee875300dba3c663524ddf5', NULL, '清空日志', '', 'DELETE', '', 'gmis-authority-server', '/loginLog/clear', b'1', b'1', b'1', b'0', 'LoginLogController', 'clear', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563017265152, 'a6d1efc30dd9c4ddf90308704f02605e', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/loginLog', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563084374016, 'cbd9d1a51eaf0aff8c9454c4b544f5bc', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/loginLog/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563109539840, 'b97a598cd721312b9ae795786c667feb', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/loginLog/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563130511360, '7d8acfa8ef97a43b3438db1b8ed40b60', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/loginLog/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563155677184, '17d09a8fdbf1a0e5fa834d949b494edc', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/loginLog/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563176648704, 'c8b2bd091194d111cf3c65a1dc126bb3', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/loginLog/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563201814528, '57cb19e3dd883966859e26aef2dd3314', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/loginLog/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563222786048, '06eb8b0b65954d4fa96a781fdc97e94b', NULL, '清空日志', '', 'DELETE', '', 'gmis-authority-server', '/optLog/clear', b'1', b'1', b'1', b'0', 'OptLogController', 'clear', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563247951872, '22ecf1aba27862c5d96157c4e2bc2980', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/optLog', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563323449344, '78982af48c37bea7b3a52bae0f4ce279', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/optLog/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563344420864, '392e8abc7f755224166f7c957899d3be', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/optLog/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563365392384, '83bc3862a9f8c7330502bc8837314b45', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/optLog/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563390558208, 'd237966c9b5a3ac3457617a971f7fe76', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/optLog/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563415724032, '7154e8b19a7ed1667f5e4c669cdb7b28', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/optLog/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563436695552, '78791709fd8068efe06e594893c43bfe', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/optLog/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563457667072, '1b849dec6a79ded5c62e06cae2f8c429', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/parameter/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563482832896, 'd660472837907c571583f854fc606860', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/parameter', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563554136064, 'e67625654b117265dd2fdf076e7c63c2', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/parameter/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563591884800, 'f3707b6ac8ca469edc307123208bb1e6', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/parameter/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563617050624, 'b9bfaf98081fdc4ee234d5680173f194', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/parameter/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563638022144, '2c3051705da5e598694405ac791fed7c', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/parameter/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563663187968, '52fdb4a8682b49237b6cce625ff9ee41', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/parameter/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563688353792, 'd4ac74971c4b8a6d3119a980407dc97b', NULL, '查询系统所有的组织树', '查询系统所有的组织树', 'GET', '', 'gmis-authority-server', '/org/tree', b'1', b'1', b'1', b'0', 'OrgController', 'tree', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563726102528, 'b2e55ee6e317765a0b7141de567f6f0c', NULL, 'findOrgByIds', '', 'GET', '', 'gmis-authority-server', '/org/findOrgByIds', b'1', b'1', b'1', b'0', 'OrgController', 'findOrgByIds', NULL, '2020-03-29 20:50:34', NULL, '2020-03-29 20:50:34');
INSERT INTO `c_auth_system_api` VALUES (1244245563755462656, '9acc958401b113ffaefe9cba585e0416', NULL, 'findOrgNameByIds', '', 'GET', '', 'gmis-authority-server', '/org/findOrgNameByIds', b'1', b'1', b'1', b'0', 'OrgController', 'findOrgNameByIds', NULL, '2020-03-29 20:50:34', NULL, '2020-03-29 20:50:34');
INSERT INTO `c_auth_system_api` VALUES (1244245563784822784, '56fa11905e37596ef87477694ae8042e', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/org/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.SuperCacheController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563814182912, 'aa01ef8197a8c1fe940e74adbbc9eae6', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/org', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563906457600, '6f0e74ef3ee35040f2b6a359ec9a5ffa', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/org/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563927429120, 'ca6fac3b2ce047352f74d553aa492b89', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/org/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563952594944, '92827e7627fc674673724980ba8bd067', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/org/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245563977760768, '02326de44c89065f0b57e61b02f48451', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/org/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564002926592, 'a3d80d7664a1c7ae151dfda59e93eb5c', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/org/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564028092416, 'b101d136e8fa951edcb6f0421c2cdcc8', NULL, 'findStationByIds', '', 'GET', '', 'gmis-authority-server', '/station/findStationByIds', b'1', b'1', b'1', b'0', 'StationController', 'findStationByIds', NULL, '2020-03-29 20:50:34', NULL, '2020-03-29 20:50:34');
INSERT INTO `c_auth_system_api` VALUES (1244245564053258240, 'ca9622eeb34fa3567dd0446215a9e526', NULL, 'findStationNameByIds', '', 'GET', '', 'gmis-authority-server', '/station/findStationNameByIds', b'1', b'1', b'1', b'0', 'StationController', 'findStationNameByIds', NULL, '2020-03-29 20:50:34', NULL, '2020-03-29 20:50:34');
INSERT INTO `c_auth_system_api` VALUES (1244245564078424064, '0837fac3482e18790e5f88269c707726', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/station/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.SuperCacheController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564099395584, 'a0e1f18110c2e946f4abf992aa0b645d', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/station', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564179087360, '671fb4f7a62551fd4fd9236b3120c301', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/station/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564204253184, 'bc13a2e4bf6ea0e00d6e70ebaca6037d', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/station/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564229419008, 'a87938e158e185bd28c17dc25f060e63', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/station/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564250390528, 'f35f4b97f66d445514cd9b41c251e990', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/station/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564275556352, '43ecfbe3c832255d4dec8ad72bfef144', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/station/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564296527872, '6f5ac6656ce6ad4f0cc7c84a611e001e', NULL, '批量删除', '批量删除', 'DELETE', '', 'gmis-authority-server', '/globalUser/remove', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.defaults.GlobalUserController', 'remove', NULL, '2020-03-29 20:50:34', NULL, '2020-03-29 20:50:34');
INSERT INTO `c_auth_system_api` VALUES (1244245564317499392, '4ccae4fa388815756e87ff4f548844f2', NULL, '检测账号是否可用', '检测账号是否可用', 'GET', '', 'gmis-authority-server', '/globalUser/check', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.defaults.GlobalUserController', 'check', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564338470912, 'be1504509761142dfe8ff960d4198720', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/globalUser', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564405579776, 'b1f260a0590e17bd1aa699244d843f27', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/globalUser/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564426551296, '7bf43d7b8b9b5a2c9dba55cee942bdcc', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/globalUser/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564455911424, '57189d25d0b687cb8e375e67e0da1ec7', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/globalUser/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564476882944, 'ff02088d4b7bb65d78ca18ddc5b1af04', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/globalUser/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564502048768, '58fff5b608a667e2cf0eb1b38024fe29', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/globalUser/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564523020288, 'bb9a36501923558d89150ec08c81f24b', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/globalUser/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564552380416, '97be1dc398ba64e18bad90f944b857db', NULL, '获取当前所有数据源', '', 'GET', '', 'gmis-authority-server', '/datasources', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.defaults.LoadDatasourceController', 'list', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564653043712, 'fc9bd1509d9647e9ff0186cddb27e6b2', NULL, '基础Druid数据源', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/datasources/saveDruid', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.defaults.LoadDatasourceController', 'saveDruid', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564678209536, '7f751812592228508b04201cd6858ba2', NULL, '检测租户是否存在', '检测租户是否存在', 'GET', '', 'gmis-authority-server', '/tenant/check/{code}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.defaults.TenantController', 'check', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564711763968, 'fa3cf59ed4cf842f3f4d95f3264bf9ac', NULL, '查询所有企业', '查询所有企业', 'GET', '', 'gmis-authority-server', '/tenant/all', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.defaults.TenantController', 'list', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564732735488, '14740fbaed12f08f68da5d093e73cb24', NULL, '初始化企业', '快速初始化企业', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/tenant/init', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.defaults.TenantController', 'saveInit', NULL, '2020-03-29 20:50:34', NULL, '2020-03-29 20:50:34');
INSERT INTO `c_auth_system_api` VALUES (1244245564753707008, '0ca8d8991dc2e1b98adaf73e5413c664', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/tenant/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.SuperCacheController', 'get', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564774678528, 'a71d499f52d93019e8962420f68e767c', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/tenant', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564850176000, 'cb008310ea3fc99b341a45322917a05d', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/tenant/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564871147520, 'c73e4c8758703762b522831872905644', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/tenant/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564892119040, '04ed283639c2fabd47b327f9d4d9dd0f', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/tenant/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564917284864, '18c403cac24ae14164f3b295223aa74b', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/tenant/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564938256384, '35782be3401018767662de6d75fe3b56', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/tenant/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564959227904, 'd6bd99b173a824cbb2c5a3c249723257', NULL, '查询全局账号', '查询全局账号', 'GET', '', 'gmis-authority-server', '/systemApiScan', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.scan.controller.SystemApiScanController', 'scan', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245564980199424, '8e9ab79556cf6ed3e8040b829b72147d', NULL, 'apiSorts', '', 'GET', 'application/json,application/hal+json', 'gmis-authority-server', '/v2/api-docs-ext', b'1', b'1', b'1', b'0', 'com.github.xiaoymin.knife4j.spring.web.Knife4jController', 'apiSorts', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245565001170944, '0af79877f2389eba04df4e5996050b1c', NULL, 'uiConfiguration', '', '', '', 'gmis-authority-server', '/swagger-resources/configuration/ui', b'1', b'1', b'1', b'0', 'springfox.documentation.swagger.web.ApiResourceController', 'uiConfiguration', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245565022142464, 'a55f9c5ecb17ea5b6a580b8e3d3606de', NULL, 'securityConfiguration', '', '', '', 'gmis-authority-server', '/swagger-resources/configuration/security', b'1', b'1', b'1', b'0', 'springfox.documentation.swagger.web.ApiResourceController', 'securityConfiguration', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245565043113984, '1e1df4a0465e8232d213fd174ed9f67b', NULL, 'swaggerResources', '', '', '', 'gmis-authority-server', '/swagger-resources', b'1', b'1', b'1', b'0', 'springfox.documentation.swagger.web.ApiResourceController', 'swaggerResources', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245565064085504, 'ada25aa59870cb6f4d5071c437ee032e', NULL, 'standardByPathVar', '', '', '', 'gmis-authority-server', '/form/validator/**', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.validator.controller.FormValidatorController', 'standardByPathVar', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245565085057024, '5f891759a7f414e5232cfef691d3b782', NULL, 'standardByQueryParam', '', '', '', 'gmis-authority-server', '/form/validator', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.validator.controller.FormValidatorController', 'standardByQueryParam', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1244245565106028544, 'f9a0c1bfbfe0de1f41d0424f09a4ddaf', NULL, 'errorHtml', '', '', 'text/html', 'gmis-authority-server', '/error', b'1', b'1', b'1', b'0', 'org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController', 'errorHtml', NULL, '2020-03-29 20:50:34', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1246084842928472064, '7270904b3276f9c841cb3d0dcf078ab1', NULL, '修改基础信息', '', 'PUT', 'application/json;charset=UTF-8', 'gmis-authority-server', '/user/base', b'1', b'1', b'1', b'0', 'UserController', 'updateBase', 3, '2020-04-03 22:39:12', 3, '2020-04-03 22:41:38');
INSERT INTO `c_auth_system_api` VALUES (1246084843322736640, 'b39cdda5a887a5f44b5de40045610c93', NULL, '删除', '', 'DELETE', '', 'gmis-authority-server', '/userToken', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.DeleteController', 'delete', 3, '2020-04-03 22:39:12', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1246084843406622720, 'a4212001416652f0619e8b8d350687f7', NULL, '预览Excel', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/userToken/preview', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'preview', 3, '2020-04-03 22:39:12', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1246084843431788544, '2a37376b190b24f06324f7e5e2054060', NULL, '导出Excel', '', 'POST', 'application/octet-stream,application/json;charset=UTF-8', 'gmis-authority-server', '/userToken/export', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'exportExcel', 3, '2020-04-03 22:39:12', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1246084843461148672, '1f706307ef737e7d932e1fc199c5244c', NULL, '导入Excel', '', 'POST', '', 'gmis-authority-server', '/userToken/import', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.PoiController', 'importExcel', 3, '2020-04-03 22:39:12', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1246084843486314496, '73f52faa3c39ed7c47a7dbdbc94dd8a6', NULL, '查询', '查询', 'GET', '', 'gmis-authority-server', '/userToken/{id}', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'get', 3, '2020-04-03 22:39:12', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1246084843515674624, 'd9de9ed2d5c77461b1a79376f92d7920', NULL, '批量查询', '批量查询', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/userToken/query', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'query', 3, '2020-04-03 22:39:12', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1246084843540840448, '03284ff5667910ede9bdaec9ae2d0986', NULL, '分页列表查询', '', 'POST', 'application/json;charset=UTF-8', 'gmis-authority-server', '/userToken/page', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.base.controller.QueryController', 'page', 3, '2020-04-03 22:39:12', 3, '2020-04-03 22:41:39');
INSERT INTO `c_auth_system_api` VALUES (1246084846334246912, '792bdde65c9e3d7538c014edcc0fb369', NULL, '修改租户状态', '修改租户状态', 'POST', '', 'gmis-authority-server', '/tenant/status', b'1', b'1', b'1', b'0', 'com.cdqckj.gmis.authority.controller.defaults.TenantController', 'updateStatus', 3, '2020-04-03 22:39:13', 3, '2020-04-03 22:41:39');
COMMIT;

-- ----------------------------
-- Table structure for c_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_user`;
CREATE TABLE `c_auth_user` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `account` varchar(30) NOT NULL COMMENT '账号',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `org_id` bigint(20) DEFAULT NULL COMMENT '组织ID\n#c_core_org\n@InjectionField(api = ORG_ID_CLASS, method = ORG_ID_METHOD, beanClass = Org.class) RemoteData<Long, Org>',
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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_ACCOUNT` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- ----------------------------
-- Records of c_auth_user
-- ----------------------------
BEGIN;
INSERT INTO `c_auth_user` VALUES (3, 'gmis', '平台超管', 100, 100, '244387061@qq.com', '15218869991', 'W', b'1', 'cnrhVkzwxjPwAaCfPbdc.png', 'mz_hanz', 'BOSHI', 'WORKING', '疯狂加班111', '2020-04-22 21:53:11', 0, NULL, 'e10adc3949ba59abbe56e057f20f883e', '2020-04-22 21:53:11', 1, '2019-09-02 11:32:02', 3, '2020-04-03 22:01:34');
INSERT INTO `c_auth_user` VALUES (641577229343523041, 'test', '总经理', 102, 100, '', '', 'N', b'1', 'http://127.0.0.1:10000/file/0000/2019/11/c8df3238-ebca-42b3-baeb-37896468f028.png', 'mz_zz', 'COLLEGE', 'WORKING', '', '2019-12-21 16:45:13', 0, NULL, 'e10adc3949ba59abbe56e057f20f883e', '2019-12-21 16:45:14', 3, '2019-11-06 09:58:56', 3, '2019-11-26 11:02:42');
INSERT INTO `c_auth_user` VALUES (641590096981656001, 'manong', '码农', 643776594376135105, 642032719487828225, '', '', 'M', b'1', 'http://192.168.1.34:10000/file/0000/2019/11/6a759cd8-40f6-46d2-9487-6bd18a6695f2.jpg', 'mz_mz', 'ZHUANKE', 'LEAVE', '122', '2020-02-22 12:32:35', 0, NULL, 'e10adc3949ba59abbe56e057f20f883e', '2020-02-22 12:32:35', 3, '2019-11-06 10:50:01', 3, '2019-11-26 20:27:48');
COMMIT;

-- ----------------------------
-- Table structure for c_auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `c_auth_user_role`;
CREATE TABLE `c_auth_user_role` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色ID\n#c_auth_role',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID\n#c_core_accou',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IDX_KEY` (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色分配\r\n账号角色绑定';

-- ----------------------------
-- Records of c_auth_user_role
-- ----------------------------
BEGIN;
INSERT INTO `c_auth_user_role` VALUES (634707503061401697, 100, 3, 1, '2019-10-18 11:01:01');
COMMIT;

-- ----------------------------
-- Table structure for c_auth_user_token
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
-- Table structure for c_common_area
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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_CODE` (`code`) USING BTREE,
  KEY `IDX_PARENT_ID` (`parent_id`,`label`) USING BTREE COMMENT '查询'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地区表';

-- ----------------------------
-- Table structure for c_common_dictionary
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型';

-- ----------------------------
-- Records of c_common_dictionary
-- ----------------------------
BEGIN;
INSERT INTO `c_common_dictionary` VALUES (1, 'NATION', '民族', '', b'1', 1, '2019-06-01 09:42:50', 1, '2019-06-01 09:42:54');
INSERT INTO `c_common_dictionary` VALUES (2, 'POSITION_STATUS', '在职状态', '', b'1', 1, '2019-06-04 11:37:15', 1, '2019-06-04 11:37:15');
INSERT INTO `c_common_dictionary` VALUES (3, 'EDUCATION', '学历', '', b'1', 1, '2019-06-04 11:33:52', 1, '2019-06-04 11:33:52');
INSERT INTO `c_common_dictionary` VALUES (4, 'AREA_LEVEL', '行政区级', '', b'1', 3, '2020-01-20 15:12:05', 3, '2020-01-20 15:12:05');
COMMIT;

-- ----------------------------
-- Table structure for c_common_dictionary_item
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
  PRIMARY KEY (`id`) USING BTREE,
  KEY `dict_code_item_code_uniq` (`dictionary_type`,`code`) USING BTREE COMMENT '字典编码与字典项目编码联合唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典项';

-- ----------------------------
-- Records of c_common_dictionary_item
-- ----------------------------
BEGIN;
INSERT INTO `c_common_dictionary_item` VALUES (1, 4, 'AREA_LEVEL', 'COUNTRY', '国家', b'1', '', 1, 3, '2020-01-20 15:12:57', 3, '2020-01-20 15:12:57');
INSERT INTO `c_common_dictionary_item` VALUES (2, 4, 'AREA_LEVEL', 'PROVINCE', '省份/直辖市', b'1', '', 2, 3, '2020-01-20 15:13:45', 3, '2020-01-20 15:13:45');
INSERT INTO `c_common_dictionary_item` VALUES (3, 4, 'AREA_LEVEL', 'CITY', '地市', b'1', '', 3, 3, '2020-01-20 15:14:16', 3, '2020-01-20 15:14:16');
INSERT INTO `c_common_dictionary_item` VALUES (4, 4, 'AREA_LEVEL', 'COUNTY', '区县', b'1', '', 4, 3, '2020-01-20 15:14:54', 3, '2020-01-20 15:14:54');
INSERT INTO `c_common_dictionary_item` VALUES (38, 3, 'EDUCATION', 'ZHUANKE', '专科', b'1', '', 4, 1, '2019-06-04 11:36:29', 1, '2019-06-04 11:36:29');
INSERT INTO `c_common_dictionary_item` VALUES (39, 3, 'EDUCATION', 'COLLEGE', '本科', b'1', '', 5, 1, '2019-06-04 11:36:19', 1, '2019-06-04 11:36:19');
INSERT INTO `c_common_dictionary_item` VALUES (40, 3, 'EDUCATION', 'SUOSHI', '硕士', b'1', '', 6, 1, '2019-06-04 11:36:29', 1, '2019-06-04 11:36:29');
INSERT INTO `c_common_dictionary_item` VALUES (41, 3, 'EDUCATION', 'BOSHI', '博士', b'1', '', 7, 1, '2019-06-04 11:36:29', 1, '2019-06-04 11:36:29');
INSERT INTO `c_common_dictionary_item` VALUES (42, 3, 'EDUCATION', 'BOSHIHOU', '博士后', b'1', '', 8, 1, '2019-06-04 11:36:29', 1, '2019-06-04 11:36:29');
INSERT INTO `c_common_dictionary_item` VALUES (43, 1, 'NATION', 'mz_hanz', '汉族', b'1', '', 0, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (44, 1, 'NATION', 'mz_zz', '壮族', b'1', '', 1, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (45, 1, 'NATION', 'mz_mz', '满族', b'1', '', 2, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (46, 1, 'NATION', 'mz_hz', '回族', b'1', '', 3, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (47, 1, 'NATION', 'mz_miaoz', '苗族', b'1', '', 4, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (48, 1, 'NATION', 'mz_wwez', '维吾尔族', b'1', '', 5, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (49, 1, 'NATION', 'mz_tjz', '土家族', b'1', '', 6, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (50, 1, 'NATION', 'mz_yz', '彝族', b'1', '', 7, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (51, 1, 'NATION', 'mz_mgz', '蒙古族', b'1', '', 8, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (52, 1, 'NATION', 'mz_zhangz', '藏族', b'1', '', 9, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (53, 1, 'NATION', 'mz_byz', '布依族', b'1', '', 10, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (54, 1, 'NATION', 'mz_dz', '侗族', b'1', '', 11, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (55, 1, 'NATION', 'mz_yaoz', '瑶族', b'1', '', 12, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (56, 1, 'NATION', 'mz_cxz', '朝鲜族', b'1', '', 13, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (57, 1, 'NATION', 'mz_bz', '白族', b'1', '', 14, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (58, 1, 'NATION', 'mz_hnz', '哈尼族', b'1', '', 15, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (59, 1, 'NATION', 'mz_hskz', '哈萨克族', b'1', '', 16, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (60, 1, 'NATION', 'mz_lz', '黎族', b'1', '', 17, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (61, 1, 'NATION', 'mz_daiz', '傣族', b'1', '', 18, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (62, 1, 'NATION', 'mz_sz', '畲族', b'1', '', 19, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (63, 1, 'NATION', 'mz_llz', '傈僳族', b'1', '', 20, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (64, 1, 'NATION', 'mz_glz', '仡佬族', b'1', '', 21, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (65, 1, 'NATION', 'mz_dxz', '东乡族', b'1', '', 22, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (66, 1, 'NATION', 'mz_gsz', '高山族', b'1', '', 23, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (67, 1, 'NATION', 'mz_lhz', '拉祜族', b'1', '', 24, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (68, 1, 'NATION', 'mz_shuiz', '水族', b'1', '', 25, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (69, 1, 'NATION', 'mz_wz', '佤族', b'1', '', 26, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (70, 1, 'NATION', 'mz_nxz', '纳西族', b'1', '', 27, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (71, 1, 'NATION', 'mz_qz', '羌族', b'1', '', 28, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (72, 1, 'NATION', 'mz_tz', '土族', b'1', '', 29, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (73, 1, 'NATION', 'mz_zlz', '仫佬族', b'1', '', 30, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (74, 1, 'NATION', 'mz_xbz', '锡伯族', b'1', '', 31, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (75, 1, 'NATION', 'mz_kehzz', '柯尔克孜族', b'1', '', 32, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (76, 1, 'NATION', 'mz_dwz', '达斡尔族', b'1', '', 33, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (77, 1, 'NATION', 'mz_jpz', '景颇族', b'1', '', 34, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (78, 1, 'NATION', 'mz_mlz', '毛南族', b'1', '', 35, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (79, 1, 'NATION', 'mz_slz', '撒拉族', b'1', '', 36, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (80, 1, 'NATION', 'mz_tjkz', '塔吉克族', b'1', '', 37, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (81, 1, 'NATION', 'mz_acz', '阿昌族', b'1', '', 38, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (82, 1, 'NATION', 'mz_pmz', '普米族', b'1', '', 39, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (83, 1, 'NATION', 'mz_ewkz', '鄂温克族', b'1', '', 40, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (84, 1, 'NATION', 'mz_nz', '怒族', b'1', '', 41, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (85, 1, 'NATION', 'mz_jz', '京族', b'1', '', 42, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (86, 1, 'NATION', 'mz_jnz', '基诺族', b'1', '', 43, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (87, 1, 'NATION', 'mz_daz', '德昂族', b'1', '', 44, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (88, 1, 'NATION', 'mz_baz', '保安族', b'1', '', 45, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (89, 1, 'NATION', 'mz_elsz', '俄罗斯族', b'1', '', 46, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (90, 1, 'NATION', 'mz_ygz', '裕固族', b'1', '', 47, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (91, 1, 'NATION', 'mz_wzbkz', '乌兹别克族', b'1', '', 48, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (92, 1, 'NATION', 'mz_mbz', '门巴族', b'1', '', 49, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (93, 1, 'NATION', 'mz_elcz', '鄂伦春族', b'1', '', 50, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (94, 1, 'NATION', 'mz_dlz', '独龙族', b'1', '', 51, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (95, 1, 'NATION', 'mz_tkez', '塔塔尔族', b'1', '', 52, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (96, 1, 'NATION', 'mz_hzz', '赫哲族', b'1', '', 53, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (97, 1, 'NATION', 'mz_lbz', '珞巴族', b'1', '', 54, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (98, 1, 'NATION', 'mz_blz', '布朗族', b'1', '', 55, 1, '2018-03-15 20:11:01', 1, '2018-03-15 20:11:04');
INSERT INTO `c_common_dictionary_item` VALUES (99, 2, 'POSITION_STATUS', 'WORKING', '在职', b'1', '', 1, 1, '2019-06-04 11:38:16', 1, '2019-06-04 11:38:16');
INSERT INTO `c_common_dictionary_item` VALUES (100, 2, 'POSITION_STATUS', 'QUIT', '离职', b'1', '', 2, 1, '2019-06-04 11:38:50', 1, '2019-06-04 11:38:50');
INSERT INTO `c_common_dictionary_item` VALUES (1237038877428940800, 4, 'AREA_LEVEL', 'TOWNS', '乡镇', b'1', '', 5, 3, '2020-03-09 23:33:46', 3, '2020-03-09 23:33:46');
INSERT INTO `c_common_dictionary_item` VALUES (1237038991044247552, 3, 'EDUCATION', 'XIAOXUE', '小学', b'1', '', 1, 3, '2020-03-09 23:34:13', 3, '2020-03-09 23:34:13');
INSERT INTO `c_common_dictionary_item` VALUES (1237039071537135616, 3, 'EDUCATION', 'ZHONGXUE', '中学', b'1', '', 2, 3, '2020-03-09 23:34:32', 3, '2020-03-09 23:34:32');
INSERT INTO `c_common_dictionary_item` VALUES (1237039105171259392, 3, 'EDUCATION', 'GAOZHONG', '高中', b'1', '', 3, 3, '2020-03-09 23:34:40', 3, '2020-03-09 23:34:40');
INSERT INTO `c_common_dictionary_item` VALUES (1237039160271831040, 3, 'EDUCATION', 'QITA', '其他', b'1', '', 20, 3, '2020-03-09 23:34:54', 3, '2020-03-09 23:34:54');
INSERT INTO `c_common_dictionary_item` VALUES (1237040064488275968, 1, 'NATION', 'mz_qt', '其他', b'1', '', 100, 3, '2020-03-09 23:38:29', 3, '2020-03-09 23:38:29');
INSERT INTO `c_common_dictionary_item` VALUES (1237040319480987648, 2, 'POSITION_STATUS', 'LEAVE', '请假', b'1', '', 3, 3, '2020-03-09 23:39:30', 3, '2020-03-09 23:39:30');
COMMIT;

-- ----------------------------
-- Table structure for c_common_login_log
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
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IDX_BROWSER` (`browser`),
  KEY `IDX_OPERATING` (`operating_system`),
  KEY `IDX_LOGIN_DATE` (`login_date`,`account`) USING BTREE,
  KEY `IDX_ACCOUNT_IP` (`account`,`request_ip`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='登录日志';

-- ----------------------------
-- Table structure for c_common_opt_log
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
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_type` (`type`) USING BTREE COMMENT '日志类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统日志';

-- ----------------------------
-- Table structure for c_common_parameter
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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_KEY` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参数配置';

-- ----------------------------
-- Records of c_common_parameter
-- ----------------------------
BEGIN;
INSERT INTO `c_common_parameter` VALUES (1, 'LOGIN_POLICY', '登录策略', 'MANY', 'ONLY_ONE:一个用户只能登录一次; MANY:用户可以任意登录; ONLY_ONE_CLIENT:一个用户在一个应用只能登录一次', b'1', b'1', 1, '2020-04-02 21:56:19', 3, '2020-04-03 01:12:32');
COMMIT;

-- ----------------------------
-- Table structure for c_core_org
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
  PRIMARY KEY (`id`),
  FULLTEXT KEY `FU_PATH` (`tree_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织';

-- ----------------------------
-- Records of c_core_org
-- ----------------------------
BEGIN;
INSERT INTO `c_core_org` VALUES (100, '秦川集团股份有限公司', '秦川集团2', 0, ',', 1, b'1', '初始化数据', '2019-07-10 17:02:18', 1, '2019-07-10 17:02:16', 1);
INSERT INTO `c_core_org` VALUES (101, '秦川集团股份有限公司广州子公司', '广州秦川集团', 100, ',100,', 0, b'1', '初始化数据', '2019-08-06 09:10:53', 1, '2019-11-12 11:36:48', 3);
INSERT INTO `c_core_org` VALUES (102, '秦川集团股份有限公司北京分公司', '北京秦川集团', 100, ',100,', 1, b'1', '初始化数据', '2019-11-07 16:13:09', 1, '2019-11-07 16:13:12', 1);
INSERT INTO `c_core_org` VALUES (643775612976106881, '综合部', '', 101, ',100,101,', 0, b'1', '前台&HR', '2019-11-12 11:34:27', 3, '2019-11-12 11:34:27', 3);
INSERT INTO `c_core_org` VALUES (643775664683486689, '管理层', '', 100, ',100,', 3, b'1', '', '2019-11-12 11:34:39', 3, '2019-11-12 11:36:16', 3);
INSERT INTO `c_core_org` VALUES (643775904077582049, '总经办', '', 100, ',100,', 2, b'1', '', '2019-11-12 11:35:37', 3, '2019-11-12 11:36:52', 3);
INSERT INTO `c_core_org` VALUES (643776324342648929, '财务部', '', 100, ',100,', 4, b'1', '', '2019-11-12 11:37:17', 3, '2019-11-12 11:37:40', 3);
INSERT INTO `c_core_org` VALUES (643776407691858113, '市场部', '', 100, ',100,', 5, b'1', '', '2019-11-12 11:37:37', 3, '2019-11-12 11:37:37', 3);
INSERT INTO `c_core_org` VALUES (643776508795556193, '销售部', '', 100, ',100,', 6, b'1', '', '2019-11-12 11:38:01', 3, '2019-11-12 11:38:01', 3);
INSERT INTO `c_core_org` VALUES (643776594376135105, '研发部', '', 101, ',100,101,', 1, b'1', '', '2019-11-12 11:38:21', 3, '2019-11-12 11:38:21', 3);
INSERT INTO `c_core_org` VALUES (643776633823564321, '产品部', '', 101, ',100,101,', 2, b'1', '', '2019-11-12 11:38:31', 3, '2019-11-12 11:38:31', 3);
INSERT INTO `c_core_org` VALUES (643776668917305985, '综合部', '', 102, ',100,102,', 0, b'1', '', '2019-11-12 11:38:39', 3, '2019-11-12 11:38:39', 3);
INSERT INTO `c_core_org` VALUES (643776713909605089, '研发部', '', 102, ',100,102,', 0, b'1', '', '2019-11-12 11:38:50', 3, '2019-11-12 11:38:50', 3);
INSERT INTO `c_core_org` VALUES (643776757199016769, '销售部', '', 102, ',100,102,', 2, b'1', '', '2019-11-12 11:39:00', 3, '2019-11-12 11:39:00', 3);
COMMIT;

-- ----------------------------
-- Table structure for c_core_station
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
-- Records of c_core_station
-- ----------------------------
BEGIN;
INSERT INTO `c_core_station` VALUES (100, '总经理', 643775904077582049, b'1', '总部-1把手', '2019-07-10 17:03:03', 1, '2019-11-16 09:59:17', 3);
INSERT INTO `c_core_station` VALUES (101, '副总经理', 643775904077582049, b'1', '总部-2把手', '2019-07-22 17:07:55', 1, '2019-11-16 09:59:21', 3);
INSERT INTO `c_core_station` VALUES (642032719487828225, '研发经理', 643776594376135105, b'1', '子公司-研发部老大', '2019-11-07 16:08:49', 3, '2019-11-16 09:53:42', 3);
INSERT INTO `c_core_station` VALUES (645199319300842081, '副总经理', 101, b'1', '子公司-老大', '2019-11-16 09:51:45', 3, '2019-11-16 09:53:50', 3);
INSERT INTO `c_core_station` VALUES (645199745026892801, '产品经理', 643776633823564321, b'1', '子公司-产品部老大', '2019-11-16 09:53:27', 3, '2019-11-16 09:54:01', 3);
INSERT INTO `c_core_station` VALUES (645200064280536545, '人事经理', 643775612976106881, b'1', '子公司-综合老大', '2019-11-16 09:54:43', 3, '2019-11-16 09:54:43', 3);
INSERT INTO `c_core_station` VALUES (645200151886964289, 'Java工程师', 643776594376135105, b'1', '普通员工', '2019-11-16 09:55:04', 3, '2019-11-16 09:55:04', 3);
INSERT INTO `c_core_station` VALUES (645200250243393185, '需求工程师', 643776633823564321, b'1', '普通员工', '2019-11-16 09:55:27', 3, '2019-11-16 09:55:27', 3);
INSERT INTO `c_core_station` VALUES (645200304014370561, 'UI工程师', 643776633823564321, b'1', '普通员工', '2019-11-16 09:55:40', 3, '2019-11-16 09:55:40', 3);
INSERT INTO `c_core_station` VALUES (645200358959753057, '运维工程师', 643776594376135105, b'1', '普通员工', '2019-11-16 09:55:53', 3, '2019-11-16 09:55:53', 3);
INSERT INTO `c_core_station` VALUES (645200405453612993, '前台小姐姐', 643775612976106881, b'1', '普通员工', '2019-11-16 09:56:04', 3, '2019-11-16 09:56:04', 3);
INSERT INTO `c_core_station` VALUES (645200545698555937, '人事经理', 643776668917305985, b'1', '北京分公司-综合部老大', '2019-11-16 09:56:38', 3, '2019-11-16 09:56:38', 3);
INSERT INTO `c_core_station` VALUES (645200670781089921, '研发经理', 643776713909605089, b'1', '北京分公司-研发部老大', '2019-11-16 09:57:07', 3, '2019-11-16 09:57:07', 3);
INSERT INTO `c_core_station` VALUES (645200806559099105, '销售经理', 643776757199016769, b'1', '北京销售部老大', '2019-11-16 09:57:40', 3, '2019-11-16 09:57:40', 3);
INSERT INTO `c_core_station` VALUES (645200885772724545, '行政', 643776668917305985, b'1', '普通员工', '2019-11-16 09:57:59', 3, '2019-11-16 09:57:59', 3);
INSERT INTO `c_core_station` VALUES (645200938289605025, '大前端工程师', 643776713909605089, b'1', '普通员工', '2019-11-16 09:58:11', 3, '2019-11-16 09:58:11', 3);
INSERT INTO `c_core_station` VALUES (645201064705927681, '销售员工', 643776757199016769, b'1', '普通员工', '2019-11-16 09:58:41', 3, '2019-11-16 09:58:41', 3);
INSERT INTO `c_core_station` VALUES (645201184268757601, '销售总监', 643775664683486689, b'1', '总部2把手', '2019-11-16 09:59:10', 3, '2019-11-16 09:59:10', 3);
INSERT INTO `c_core_station` VALUES (645201307765844833, '财务总监', 643776324342648929, b'1', '总部2把手', '2019-11-16 09:59:39', 3, '2019-11-16 09:59:39', 3);
INSERT INTO `c_core_station` VALUES (645201405757369281, '市场经理', 643776407691858113, b'1', '总部市场部老大', '2019-11-16 10:00:03', 3, '2019-11-16 10:00:03', 3);
INSERT INTO `c_core_station` VALUES (645201481133206561, '销售总监', 643776508795556193, b'1', '总部销售部老大', '2019-11-16 10:00:21', 3, '2019-11-16 10:00:21', 3);
INSERT INTO `c_core_station` VALUES (645201573391117441, '前端工程师', 643776594376135105, b'1', '普通员工', '2019-11-16 10:00:43', 3, '2019-11-16 10:00:43', 3);
COMMIT;

-- ----------------------------
-- Table structure for f_attachment
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
-- Table structure for f_file
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
-- Table structure for m_order
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
-- Table structure for m_product
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
-- Table structure for mail_provider
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
-- Table structure for mail_send_status
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
-- Table structure for mail_task
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
-- Table structure for msgs_center_info
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
-- Table structure for msgs_center_info_receive
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
-- Table structure for sms_send_status
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
-- Table structure for sms_task
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
-- Table structure for sms_template
-- ----------------------------
DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template` (
  `id` bigint(20) NOT NULL COMMENT '模板ID',
  `provider_type` varchar(10) NOT NULL COMMENT '供应商类型\n#ProviderType{ALI:OK,阿里云短信;TENCENT:0,腾讯云短信;BAIDU:1000,百度云短信}',
  `app_id` varchar(255) NOT NULL COMMENT '应用ID',
  `app_secret` varchar(255) NOT NULL COMMENT '应用密码',
  `url` varchar(255) DEFAULT '' COMMENT 'SMS服务域名\n百度、其他厂商会用',
  `custom_code` varchar(20) NOT NULL DEFAULT '' COMMENT '模板编码\n用于api发送',
  `name` varchar(255) DEFAULT '' COMMENT '模板名称',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '模板内容',
  `template_params` varchar(255) NOT NULL DEFAULT '' COMMENT '模板参数',
  `template_code` varchar(50) NOT NULL DEFAULT '' COMMENT '模板CODE',
  `sign_name` varchar(100) DEFAULT '' COMMENT '签名',
  `template_describe` varchar(255) DEFAULT '' COMMENT '备注',
  `create_user` bigint(20) DEFAULT '0' COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT '0' COMMENT '秦川修改人',
  `update_time` datetime DEFAULT NULL COMMENT '秦川修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_CODE` (`custom_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='短信模板';

-- ----------------------------
-- Table structure for undo_log
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

SET FOREIGN_KEY_CHECKS = 1;


/* 安检相关表 */
drop table if exists gc_security_check_order_record;

/*==============================================================*/
/* Table: gc_security_check_order_record     安检工单             */
/*==============================================================*/
create table gc_security_check_order_record
(
   id                   bigint(32) not null,
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(300) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   sc_no                varchar(32) comment '安检计划编号',
   work_order_no        varchar(32) comment '工单编号',
   work_order_type      varchar(32) comment '工单类型',
   urgency              varchar(20) comment '紧急程度',
   receive_user_id      bigint(32) comment '接单人ID',
   receive_user_name    varchar(100) comment '接单人名称',
   receive_user_mobile  varchar(20) comment '联系电话',
   reveive_time         datetime comment '接单时间',
   work_order_desc      varchar(1000) comment '工单内容',
   terminate_reason     varchar(1000) comment '终止原因',
   cancellation_reason  varchar(1000) comment '作废原因（包括转单、拒单、退单）',
   end_job_user_id      bigint(32) comment '结单人ID',
   end_job_user_name    varchar(100) comment '结单人名称',
   end_job_time         datetime comment '结单时间',
   transfer_order_id    bigint(32) comment '转单id',
   order_status         smallint comment '工单状态
            0 待接单
             1 转单
             2 已接单
             3 待安检
             4 安检异常
             5 结单
             6 终止
             7 拒单
             8 退单',
   order_process_status smallint comment '工单流程状态
            0 预约安检
            1 上门安检
            2 待整改
            3 已整改
            4 作废
            5 终止
            6 结单',
   hidden_danger        varchar(1000) comment '安全隐患',
   hidden_danger_handle_status smallint comment '隐患处理状态
            0、待处理
            1、已按要求整改
            2、未按要求整改',
   hidden_danger_handle_time datetime comment '隐患处理登记时间',
   hidden_danger_handle_user_id varchar(100) comment '隐患处理登记人ID',
   hidden_danger_handle_user_name varchar(100) comment '隐患处理登记人名称',
   remark               varchar(200) comment '备注',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安检工单';

SET FOREIGN_KEY_CHECKS = 1;
drop table if exists gc_security_check_record;

/*==============================================================*/
/* Table: gc_security_check_record        安检计划                */
/*==============================================================*/
create table gc_security_check_record
(
   id                   bigint(32) not null,
   company_code         varchar(32) not null comment '公司CODE',
   company_name         varchar(300) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   customer_code        varchar(32) comment '客户编号',
   customer_name        varchar(60) comment '客户名称',
   gas_code             char(32) comment '气表编号',
   gas_meter_name       varchar(100) comment '气表名称',
   gas_meter_factory_id bigint(32) comment '气表厂家ID',
   use_gas_type_id      bigint(32) comment '用气类型ID',
   sc_no                varchar(32) comment '安检编号',
   plan_security_check_user_id char(32) comment '计划安检员ID',
   plan_security_check_user_name varchar(100) comment '计划安检员名称',
   plan_security_check_time datetime comment '计划安检时间',
   security_check_content varchar(1000) comment '安检内容',
   security_check_user_id bigint(32) comment '安检员ID',
   security_check_user_name varchar(100) comment '安检员名称',
   security_check_time  datetime comment '安检时间',
   review_user_id       bigint(32) comment '审批人ID',
   review_user_name     varchar(100) comment '审批人名称',
   review_time          datetime comment '审批时间',
   review_objection     varchar(100) comment '审批意见',
   distribution_user_id bigint(32) comment '派工人ID',
   distribution_user_name varchar(100) comment '派工人名称',
   distribution_time    datetime comment '派人时间',
   end_job_user_id      bigint(32) comment '结单人ID',
   end_job_user_name    varchar(100) comment '结单人名称',
   end_job_time         datetime comment '结单时间',
   reject_desc          varchar(1000) comment '驳回原因',
   terminate_user_id    bigint(32) comment '终止人ID',
   terminate_user_name  varchar(100) comment '终止人名称',
   terminate_time       datetime comment '终止时间',
   stop_desc            varchar(1000) comment '终止原因',
   data_status          smallint comment '安检状态:0 待提审
             1 待审核
             2 待派工
             3 待安检
             4 安检中
             5 异常
             6 结单
             7 终止',
   security_check_process_state smallint comment '安检流程状态
            0 提审
            1 审核
            2 派工
            3 驳回
            4 撤回
            5 接单确认
            6 拒单
            7 退单
            8 转单
            9 预约
            10 上门安检
            11 待整改
            12 已整改
            13 结单
            14 终止

            ',
   remark               varchar(1000) comment '备注',
   create_time          datetime comment '创建时间',
   create_user          bigint(32) comment '创建人ID',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安检记录';

SET FOREIGN_KEY_CHECKS = 1;

drop table if exists gc_security_check_result;

/*==============================================================*/
/* Table: gc_security_check_result  安检结果                      */
/*==============================================================*/
create table gc_security_check_result
(
   id                   bigint(32) not null,
   company_code         varchar(32) not null comment '公司CODE',
   company_name         varchar(300) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   customer_code        bigint(32) comment '客户编号',
   customer_name        varchar(60) comment '客户名称',
   gas_code             char(32) comment '气表编号',
   gas_meter_name       varchar(100) comment '气表名称',
   gas_meter_factory_id bigint(32) comment '气表厂家ID',
   use_gas_type_id      char(32) comment '用气类型ID',
   sc_no                varchar(32) comment '安检编号',
   security_check_content varchar(1000) comment '安检内容',
   security_check_user_id bigint(32) comment '安检员ID',
   security_check_user_name varchar(100) comment '安检员名称',
   security_check_time  datetime comment '安检时间',
   work_order_no        varchar(32) comment '工单编号',
   terminate_user_id    bigint(32) comment '终止人ID',
   terminate_user_name  varchar(100) comment '终止人名称',
   terminate_time       datetime comment '终止时间',
   stop_desc            varchar(1000) comment '终止原因',
   data_status          smallint comment '安检状态:0 待提审
             1 待审核
             2 待派工
             3 待安检
             4 安检中
             5 异常
             6 结单
             7 终止',
   security_check_process_state smallint comment '安检流程状态
            0 提审
            1 审核
            2 派工
            3 驳回
            4 撤回
            5 接单确认
            6 拒单
            7 退单
            8 转单
            9 预约
            10 上门安检
            11 待整改
            12 已整改
            13 结单
            14 终止
            ',
   remark               varchar(1000) comment '备注',
   create_time          datetime comment '创建时间',
   create_user          bigint(32) comment '创建人ID',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安检结果';

SET FOREIGN_KEY_CHECKS = 1;

drop table if exists gc_security_check_process;

/*==============================================================*/
/* Table: gc_security_check_process   安检流程操作记录             */
/*==============================================================*/
create table gc_security_check_process
(
   id                   bigint(32) not null comment 'ID',
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(100) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   security_check_id    bigint(32) comment '安检计划ID',
   process_state        varchar(32) comment '流程状态',
   oper_process_state   varchar(32) comment '操作状态',
   remark               varchar(32) comment '操作备注',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table gc_security_check_process comment '安检流程操作记录';

drop table if exists gc_security_order_process;

/*==============================================================*/
/* Table: gc_security_order_process       安检工单流程操作记录      */
/*==============================================================*/
create table gc_security_order_process
(
   id                   bigint(32) not null comment 'ID',
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(100) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   security_order_id    bigint(32) comment '安检工单ID',
   process_state        varchar(32) comment '流程状态',
   oper_process_state   varchar(32) comment '操作状态',
   remark               varchar(32) comment '操作备注',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table gc_security_order_process comment '安检工单流程操作记录';

/** 运行维护服务相关表 **/
drop table if exists gc_operation_record;

/*==============================================================*/
/* Table: gc_operation_record              运行维工单             */
/*==============================================================*/
create table gc_operation_record
(
   id                   bigint(32) not null,
   company_code         varchar(32) not null comment '公司CODE',
   company_name         varchar(300) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   customer_code        varchar(32) comment '客户编号',
   customer_name        varchar(60) comment '客户名称',
   gas_code             char(32) comment '气表编号',
   gas_meter_name       varchar(100) comment '气表名称',
   gas_meter_factory_id bigint(32) comment '气表厂家ID',
   use_gas_type_id      bigint(32) comment '用气类型ID',
   accept_no            varchar(32) comment '运维受理编号',
   plan_receive_user_id bigint(32) comment '计划接单人',
   plan_receive_user_name varchar(100) comment '计划接单人名称',
   plan_receive_user_phone varchar(20) comment '计划接单人电话',
   receive_user_id      bigint(32) comment '接单人ID',
   receive_user_name    varchar(100) comment '接单人名称',
   receive_user_phone   varchar(20) comment '接单人电话',
   receive_user_time    datetime comment '接单人时间',
   review_user_id       bigint(32) comment '审批人ID',
   review_user_name     varchar(100) comment '审批人名称',
   review_time          datetime comment '审批时间',
   review_objection     varchar(100) comment '审批意见',
   distribution_user_id bigint(32) comment '派单人ID',
   distribution_user_name varchar(100) comment '派单人名称',
   distribution_time    datetime comment '派单时间',
   end_job_user_id      bigint(32) comment '结单人ID',
   end_job_user_name    varchar(100) comment '结单人名称',
   end_job_time         datetime comment '结单时间',
   reject_reason        varchar(1000) comment '驳回原因',
   terminate_user_id    bigint(32) comment '终止人ID',
   terminate_user_name  varchar(100) comment '终止人名称',
   terminate_time       datetime comment '终止时间',
   stop_desc            varchar(1000) comment '终止原因',
   order_type           smallint comment '工单类型
            0 普通运行维护
            1 点火通气',
   urgency_level        smallint comment '紧急程度
            0 正常
            1 紧急
            2 非常紧急',
   order_content        varchar(1000) comment '工单内容',
   operation_status     smallint comment '运维状态:
             0 待提审
             1 待审核
             2 待接单
             3 待派单
             4 待运维
             5 运维中
             6 终止
             7 作废
             8 结单',
   operation_process_state smallint comment '运维流程状态
            0 提审
            1 审核
            2 派单
            3 驳回
            4 撤回
            5 接单确认
            6 拒单
            7 退单
            8 转单
            9 预约
            10 上门
            11 结单
            12 终止',
   transfer_id          bigint(32) comment '转单ID',
   remark               varchar(1000) comment '备注',
   create_time          datetime comment '创建时间',
   create_user          bigint(32) comment '创建人ID',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table gc_operation_record comment '运行维护工单';

drop table if exists gc_operation_process;

/*==============================================================*/
/* Table: gc_operation_process                运行维护流程操作记录  */
/*==============================================================*/
create table gc_operation_process
(
   id                   bigint(32) not null comment 'ID',
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(100) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   operation_id         bigint(32) comment '运行维护工单ID',
   process_state        varchar(32) comment '流程状态',
   oper_process_state   varchar(32) comment '操作状态',
   remark               varchar(32) comment '操作备注',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table gc_operation_process comment '运行维护流程操作记录';

drop table if exists gc_operation_acceptance;

/*==============================================================*/
/* Table: gc_operation_acceptance   运维受理表                    */
/*==============================================================*/
create table gc_operation_acceptance
(
   id                   bigint(32) not null,
   company_code         varchar(32) not null comment '公司CODE',
   company_name         varchar(300) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   alarm_customer_code  varchar(32) comment '报警人编号',
   alarm_customer_name  varchar(60) comment '报警人名称',
   alarm_customer_phone varchar(20) comment '报警人电话',
   alarm_content        varchar(1000) comment '报警内容',
   gas_code             char(32) comment '气表编号',
   gas_meter_name       varchar(100) comment '气表名称',
   gas_meter_factory_id bigint(32) comment '气表厂家ID',
   use_gas_type_id      bigint(32) comment '用气类型ID',
   oper_accept_no       varchar(32) comment '运维受理编号',
   plan_handle_user_id  char(32) comment '计划处警人ID',
   plan_handle_user_name varchar(100) comment '计划处警人名称',
   plan_handle_time     datetime comment '计划处警时间',
   handle_user_id       bigint(32) comment '处警人ID',
   handle_user_name     varchar(100) comment '处警人名称',
   handle_time          datetime comment '处警时间',
   terminate_user_id    bigint(32) comment '终止人ID',
   terminate_user_name  varchar(100) comment '终止人名称',
   terminate_time       datetime comment '终止时间',
   stop_desc            varchar(1000) comment '终止原因',
   accept_status        smallint comment '受理状态:
             0 待处理
             1 已处理
             2 未处理',
   accept_process_state smallint,
   remark               varchar(1000) comment '备注',
   create_time          datetime comment '创建时间',
   create_user          bigint(32) comment '创建人ID',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
 );
alter table gc_operation_acceptance comment '运行维护受理';

drop table if exists pz_preferential;
/*==============================================================*/
/* Table: 活动优惠表                                      */
/*==============================================================*/
create table pz_preferential
(
   ID                   bigint(32) not null comment 'ID',
   PZ__id               bigint(32)	    comment '用气类ID',
   use_gas_type_code    varchar(32)	    comment '用气类型code',
   use_gas_type_name    varchar(50)	    comment '用气类型名称',
   preferential_name    varchar(100)	comment '优惠活动名称',
   preferential_activate smallint		comment '活动有效期：1-长期有效		2-开户有效		3-固定时间有效',
   is_always_preferential smallint 	    comment '持续优惠：0-否	1-是',
   start_time           datetime	        comment '开始时间',
   end_time             datetime	    comment '结束时间',
   data_status          smallint	    comment '是否启用:0-否	1-是',
   preferential_gas     decimal(18,4)	comment '优惠气量',
   preferential_type    smallint	    comment '优惠方式:1-固定值		2-按比例		3-不固定',
   preferential_money   decimal(18,4)	comment '优惠金额',
   create_time          datetime	    comment '创建时间',
   create_user          bigint(32)	    comment '创建用户',
   update_time          datetime	    comment '更新时间',
   update_user          bigint(32)	    comment '更新用户',
   delete_status        smallint	    comment '删除状态：0-否	1-是',
   primary key (ID)
)comment='优惠活动表';





drop table if exists pz_business_template;

/*==============================================================*/
/* Table: pz_business_template     业务文件模板                   */
/*==============================================================*/
create table pz_business_template
(
   id                   bigint(32) not null comment 'ID',
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(100) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   template_code        varchar(64) comment '模板编码',
   template_name        varchar(64) comment '模板名称',
   file_id              bigint(20) comment '文件id',
   item_status          smallint comment '状态',
   item_describe        varchar(255) comment '描述',
   create_user          bigint(32) comment '创建人ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '修改人ID',
   update_time          datetime comment '修改时间',
   primary key (id),
   unique key unq_template_code (template_code)
);


drop table if exists gt_account_open_task_info;

/*==============================================================*/
/* Table: gt_account_open_task_info       导入开户任务信息         */
/*==============================================================*/
create table gt_account_open_task_info
(
   id                   bigint(32) not null comment 'ID',
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(100) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   business_hall_id     char(32) comment '营业厅ID',
   business_hall_name   varchar(100) comment '营业厅名称',
   total                bigint(32) comment '合计户数',
   success_total        bigint(32) comment '成功户数',
   fail_total           bigint(32) comment '失败户数',
   import_details       mediumtext comment '导入明细',
   status               smallint comment '状态',
   create_user          bigint(32) comment '操作员ID',
   create_time          datetime comment '操作时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
);
/*==============================================================*/
/* Table: gt_account_open_task_info       气表厂家         */
/*==============================================================*/
drop table if exists pt_gas_meter_factory;
CREATE TABLE `pt_gas_meter_factory` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `gas_meter_factory_name` varchar(100) NOT NULL COMMENT '厂家名称',
  `gas_meter_factory_code` varchar(10) NOT NULL COMMENT '厂家编号',
	`gas_meter_factory_address` varchar(100) NOT NULL COMMENT '厂家地址',
	`telphone` varchar(11) DEFAULT NULL COMMENT '电话',
	`contacts`  varchar(10)	DEFAULT NULL comment '联系人',
	`fax_number` varchar(20) DEFAULT NULL COMMENT '传真号码',
	`manager` varchar(10)	DEFAULT NULL comment '经理',
	`tax_registration_no` varchar(30)	DEFAULT NULL comment '税务登记号',
	`bank` varchar(20)	DEFAULT NULL comment '开户银行',
	`bank_account` varchar(20)	DEFAULT NULL comment '银行账号',
  `gas_meter_type` varchar(100) DEFAULT NULL COMMENT '普表\r\n ic卡智能燃气表\r\n 物联网表\r\n 流量计(普表)\r\n 流量 计(ic卡表)\r\n 流量计(物联网表)\r\n',
  `gas_meter_factory_status` smallint(6) NOT NULL DEFAULT 1 COMMENT '厂家状态：（0：启用；1：禁用）',
  `compatible_parameter` varchar(1000) DEFAULT NULL COMMENT '兼容参数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
	`delete_status` smallint default 0 comment '删除标识（0：存在；1-删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表厂家';

/*==============================================================*/
/* Table: gt_account_open_task_info       气表版号         */
/*==============================================================*/
drop table if exists pt_gas_meter_version;
CREATE TABLE `pt_gas_meter_version` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `gas_meter_version_name` varchar(30) DEFAULT NULL COMMENT '版号名称',
	`describe` varchar(30) DEFAULT NULL COMMENT '版号描述',
	`company_id` bigint(32) DEFAULT NULL COMMENT '生产厂家ID',
	`company_code` varchar(10) DEFAULT NULL COMMENT '生产厂家编号',
	`company_name` varchar(100) DEFAULT NULL COMMENT '生产厂家名称',
	`equipment_vendors_name` varchar(100)  DEFAULT NULL COMMENT '无线通信设备厂家名称',
	`wireless_version` varchar(10) DEFAULT NULL COMMENT '无线通信版本',
	`order_source_name` varchar(20) NOT NULL COMMENT '订单来源名称',
	`amount_mark` varchar(20) DEFAULT NULL COMMENT '金额标志',
	`remote_service_flag` varchar(20) DEFAULT NULL COMMENT '远程业务标志',
	`amount_digits` smallint COMMENT '气量表金额位数',
	`single_level_amount` smallint COMMENT '是否单阶金额表：0:是；1-否',
	`ic_card_core_mark` varchar(30) COMMENT 'IC卡内核标识',
	 `measurement_accuracy` smallint(6) DEFAULT NULL COMMENT '计费精度（小数位数）',
	`select_address` smallint DEFAULT 1 COMMENT '是否选择地址：0:是；1-否',
	`version_state` smallint DEFAULT 1 COMMENT '开启\r\n		禁用',
	`accumulated_amount` smallint DEFAULT 1 COMMENT '补气是否累加金额',
	`accumulated_count` smallint DEFAULT 1 COMMENT '补气是否累加次数',
	`card_type` varchar(30) COMMENT '卡号类型',
 	 `card_number_prefix` varchar(10) DEFAULT NULL COMMENT '卡号前缀',
 	 `card_number_length` smallint(6) DEFAULT NULL COMMENT '卡号长度',
 	 `open_in_meter_number` smallint(6) DEFAULT NULL COMMENT '开户录入表号',
	`issuing_cards` smallint DEFAULT 1 COMMENT  '是否发卡',
	`verification_table_no` smallint DEFAULT 1 COMMENT  '是否验证表号',
  	`gas_meter_type` varchar(30) DEFAULT NULL COMMENT 'ORDINARY_METER:普表\r\n            IC_METER:IC卡智能燃气表\r\n            IOT_METER物联网表\r\n            FLOWMETER_ORDINARY_METER:流量计(普表)\r\n            FLOWMETER_IC_METER:流量 计(IC卡表)\r\n            FLOWMETER_IOT_METER:流量计(物联网表)',
  	`settlement_type` varchar(10) DEFAULT NULL COMMENT 'GAS:气量\r\n            MONEY:金额',
  	`settlement_mode` varchar(10) DEFAULT NULL COMMENT 'READMETER:抄表结算\r\n            METER:表端结算\r\n            SERVICE:服务端结算',
  	`charge_type` varchar(20) DEFAULT NULL COMMENT 'IC_RECHARGE:IC卡充值\r\n            READMETER_PAY:抄表缴费\r\n            REMOTE_RECHARGE:远程充值',
	 `compatible_parameter` varchar(1000) DEFAULT NULL COMMENT '如气表功能控制、兼容标识、兼容参数等。\r\n            建议JSON存储',
	 `function` varchar(1000) DEFAULT NULL COMMENT 'READMETER:抄表\r\n            CLOSE_VALVE:普通关阀\r\n            FORCED_CLOSING_VALVE:强制关阀\r\n            OPEN_VALVE:开阀\r\n            MODIFY_PRICE:调价\r\n            UPDATE_CYCLE_GAS:周期累计量修改\r\n\r\n\r\n            ',
	 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	 `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
	 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
	 `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
	`remark` varchar(100) DEFAULT NULL COMMENT '版号说明(备注)',
	`delete_status` smallint default 0 comment '删除标识（0：存在；1-删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表版本';

/*==============================================================*/
/* Table: gt_account_open_task_info       气表型号                */
/*==============================================================*/
drop table if exists pt_gas_meter_model;
CREATE TABLE `pt_gas_meter_model` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_id` bigint(32) NOT NULL COMMENT '生产厂家ID',
	`company_code` varchar(10) DEFAULT NULL COMMENT '生产厂家编号',
	`company_name` varchar(100) DEFAULT NULL COMMENT '生产厂家名称',
	`gas_meter_version_id` bigint(32) NOT NULL COMMENT '版号ID',
	`gas_meter_version_name` varchar(30) DEFAULT NULL COMMENT '版号名称',
	`model_name` varchar(30) NOT NULL COMMENT '型号名称',
	`data_status` smallint default 1 comment '是否启用（0：启用；1-禁用）',
	`specification` numeric(12,2) NOT NULL comment '型号规格',
	`nominal_flow_rate` numeric(12,2) NOT NULL comment '公称流量',
	`max_flow` numeric(12,3) NOT NULL comment '最大流量',
	`min_flow` numeric(12,3) NOT NULL comment '最小流量',
	`max_pressure` numeric(12,2) NOT NULL comment '最大压力',
	`max_word_wheel` numeric(12,4) NOT NULL comment '字轮最大值',
	`rotational_volume` numeric(12,2) NOT NULL comment '回转体积',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
	`delete_status` smallint default 0 comment '删除标识（0：存在；1-删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表型号';


drop table if exists pz_give_reduction_conf;
/*==============================================================*/
/* Table: gt_account_open_task_info       赠送减免活动            */
/*==============================================================*/
CREATE TABLE pz_give_reduction_conf (
  ID bigint(32) NOT NULL COMMENT 'ID',
  activity_name     varchar(100)  DEFAULT NULL COMMENT '活动名称',
  start_time        date          DEFAULT NULL COMMENT '开始时间',
  end_time          date          DEFAULT NULL COMMENT '结束时间',
  give_gas          decimal(18,4) DEFAULT NULL COMMENT '赠送气量（充值场景才能设置）',
  activity_money_type varchar(32) DEFAULT NULL COMMENT '活动金额方式（缴费场景无百分比选项）\r\n            fixed： 固定金额   可以输入金额\r\n            unfixed： 不固定金额\r\n            percent: 百分比',
  activity_money    decimal(18,4) DEFAULT NULL COMMENT '活动金额',
  activity_percent  decimal(18,4) DEFAULT NULL COMMENT '活动比例',
  activity_scene    varchar(32)   DEFAULT NULL COMMENT '活动场景\r\n            RECHARGE_GIVE: 充值赠送\r\n            PRECHARGE_GIVE: 预存赠送\r\n            CHARGE_DE: 缴费减免',
  use_gas_types     varchar(1000) DEFAULT NULL COMMENT '用气类型(只针对充值场景)\r\n            存放jsonarray数据 包含用气类型id和用气类型名称。',
  data_status       smallint(6)   DEFAULT NULL COMMENT '是否启用\r\n            1 启用\r\n            0 不启用',
  delete_status     smallint(6)   DEFAULT NULL COMMENT '删除状态\r\n            1 删除\r\n            0 正常',
  create_time       datetime      DEFAULT NULL COMMENT '创建时间',
  create_user       bigint(32)    DEFAULT NULL COMMENT '创建用户ID',
  update_time       datetime      DEFAULT NULL COMMENT '更新时间',
  update_user       bigint(32)    DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赠送减免活动配置';


/*==============================================================*/
/* Table: gt_receipt      票据记录                                */
/*==============================================================*/
drop table if exists gt_receipt;
create table gt_receipt
(
   id                   bigint(32) not null comment 'ID',
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(100) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   business_hall_id     char(32) comment '营业厅ID',
   business_hall_name   varchar(100) comment '营业厅名称',
   receipt_no           varchar(32) comment '票据编号',
   receipt_type         smallint comment '票据类型
            0充值
            1缴费
            2预存',
   pay_no               varchar(32) comment '收费编号',
   pay_type             smallint comment '收费类型
            0现金
            1支付宝
            2微信
            3POS',
   pay_time             datetime comment '收费时间',
   charge_amount_total_lowercase numeric(18,4) default 0 comment '收费项目合计（小写）',
   charge_amount_total_uppercase char(32) comment '收费项目合计（大写）',
   recharge_give_gas    varchar(32) comment '充值活动赠送气量',
   discount_amount      numeric(18,4) default 0 comment '充值活动赠送金额',
   should_pay           numeric(18,4) default 0 comment '应收金额',
   make_collections     numeric(18,4) default 0 comment '入账金额（实收金额-找零金额+抵扣金额）',
   actual_collection    numeric(18,4) default 0 comment '实收金额（收款金额）',
   give_change          numeric(18,4) default 0 comment '找零金额',
   pre_deposit          numeric(18,4) default 0 comment '零存',
   customer_no          varchar(100) comment '客户编号',
   customer_name        char(32) comment '客户名称',
   customer_address     varchar(100) comment '客户地址',
   customer_phone       varchar(1000) comment '客户电话',
   operator_no          varchar(30) comment '操作员编号',
   operator_name        varchar(30) comment '操作员姓名',
   buyer_name           varchar(100) comment '购买方名称',
   buyer_tin_no         varchar(30) comment '购买方纳税人识别号',
   buyer_addr_phone     varchar(120) comment '购买方地址、电话',
   buyer_bank_account   varchar(100) comment '购买方开户行及账号',
   deduct_amount        numeric(18,4) default 0 comment '抵扣金额',
   recharge_amount      numeric(18,4) default 0 comment '充值金额',
   recharge_gas_volume  varchar(32) default '0' comment '充值气量',
   predeposit_amount    numeric(18,4) default 0 comment '预存金额',
   premium              numeric(18,4) default 0 comment '保费',
   receipt_state        smallint(0) default 0  comment '开票状态0未开票据1已开票据未开发票2已开票据发票',
   billing_date         datetime comment '开票日期',
   remark               varchar(100) comment '备注',
   data_status          smallint comment '数据状态',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
);




/*==============================================================*/
/* Table: gt_receipt_detail   票据明细                            */
/*==============================================================*/
drop table if exists gt_receipt_detail;
create table gt_receipt_detail
(
   id                   bigint(32) not null comment 'ID',
   receipt_id           varchar(32) comment '票据ID',
   goods_id             varchar(32) comment '商品名称',
   goods_name           varchar(100) comment '商品代码
            0充值
            1燃气收费
            2商品销售
            3场景收费
            4附加费
            5保险费
            ',
   specification_model  varchar(20) comment '规格型号',
   unit                 varchar(20) comment '单位',
   number               varchar(32) comment '数量',
   price                numeric(18,4) default 0 comment '单价',
   money                numeric(18,4) default 0 comment '金额',
   tax_rate             numeric(18,4) default 0 comment '税率',
   tax_money            numeric(18,4) default 0 comment '税额',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
);



drop table if exists pz_exception_rule_conf;
/*==============================================================*/
/* Table: gt_account_open_task_info       抄表异常规则            */
/*==============================================================*/
create table pz_exception_rule_conf (
  id bigint(32) NOT NULL comment 'ID',
  company_code  char(32)         DEFAULT NULL COMMENT '公司code',
  company_name  varchar(100)     DEFAULT NULL COMMENT '公司名称',
  org_id        bigint(32)       DEFAULT NULL COMMENT '组织ID',
  org_name      varchar(100)     DEFAULT NULL COMMENT '组织名称',
  use_gas_type_id   bigint(32)   DEFAULT NULL COMMENT '用气类型id',
  surge_multiple    decimal(4,1) DEFAULT NULL COMMENT '暴增倍数（大于1）',
  sharp_decrease    decimal(4,1) DEFAULT NULL COMMENT '锐减比例（0~1）',
  create_user   bigint(32)       DEFAULT NULL COMMENT '创建人ID',
  create_user_name  varchar(100) DEFAULT NULL COMMENT '创建人名称',
  create_time   datetime         DEFAULT NULL COMMENT '创建时间',
  update_user   bigint(32)       DEFAULT NULL COMMENT '更新人ID',
  update_time   datetime         DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表异常规则配置';


/*==============================================================*/
/* Table: gt_invoice         发票记录                             */
/*==============================================================*/
drop table if exists gt_invoice;
create table gt_invoice
(
   id                   bigint(32) not null comment 'ID',
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(100) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   business_hall_id     char(32) comment '营业厅ID',
   business_hall_name   varchar(100) comment '营业厅名称',
   invoice_number       varchar(30) comment '发票编号',
   pay_no               varchar(32) comment '收费编号',
   invoice_type         varchar(30) comment '发票类型
            007普票
            004专票
            026电票',
   invoice_kind         char(10) comment '发票种类
            0红票
            1蓝票
            2废票',
   billing_date         datetime comment '开票日期',
   invoice_code         varchar(32) comment '发票代码',
   invoice_no           varchar(32) comment '发票号码',
   buyer_name           varchar(100) comment '购买方名称',
   buyer_tin_no         varchar(20) comment '购买方纳税人识别号',
   buyer_addr_phone     varchar(100) comment '购买方地址、电话',
   buyer_bank_account   varchar(100) comment '购买方开户行及账号',
   seller_name          varchar(100) comment '销售方名称',
   seller_tin_no        varchar(20) comment '销售方纳税人识别号',
   seller_addr_phone    varchar(100) comment '销售方地址、电话',
   seller_bank_account  varchar(100) comment '销售方开户行及账户',
   total_amount         numeric(18,4) default 0 comment '合计金额',
   total_tax            numeric(18,4) default 0 comment '合计税额',
   price_tax_total_upper varchar(32) comment '价税合计（大写）',
   price_tax_total_lower numeric(18,4) default 0 comment '价税合计（小写）',
   payee_id             bigint(32) comment '收款人',
   payee_name           varchar(100) comment '收款人名称',
   reviewer_id          bigint(32) comment '复核人',
   reviewer_name        varchar(100) comment '复核人名称',
   drawer_id            bigint(32) comment '开票人',
   drawer_name          varchar(100) comment '开票人名称',
   telphone             varchar(11) comment '联系电话',
   email                varchar(50) comment '电子邮箱',
   push_method          varchar(30) comment '电子票推送方式
            0短信
            1邮件
            2微信',
   void_user_id         char(32) comment '作废人ID',
   void_user_name       varchar(100) comment '作废人名称',
   red_user_id          char(32) comment '冲红人ID',
   red_user_name        varchar(100) comment '冲红人名称',
   invoice_file_url     varchar(1000) comment '发票文件地址',
   invoice_serial_number varchar(30) comment '开票流水号',
   red_request_number   varchar(30) comment '专票冲红申请号',
   blue_order_number    varchar(30) comment '蓝票订单号',
   blue_invoice_number  varchar(30) comment '蓝票编号',
   blue__serial_number  varchar(30) comment '蓝票开票流水号',
   invoice_status       varchar(30) comment '开票状态
            0未开票
            1已开票
            2作废',
   invoice_result       varchar(100) comment '开票结果',
   print_times          smallint comment '打印次数',
   data_status          smallint comment '数据状态',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   remark               varchar(300) comment '备注',
   primary key (id)
);





/*==============================================================*/
/* Table: gt_invoice_details             发票明细                 */
/*==============================================================*/
drop table if exists gt_invoice_details;
create table gt_invoice_details
(
   id                   bigint(32) not null comment 'ID',
   invoice_id           char(32) comment '发票ID',
   goods_code           varchar(32) comment '商品代码',
   goods_name           varchar(100) comment '商品名称',
   specification_model  varchar(32) comment '规格型号',
   unit                 varchar(20) comment '单位',
   number               varchar(32) comment '数量',
   price                numeric(18,4) default 0 comment '单价',
   money                numeric(18,4) default 0 comment '金额',
   tax_rate             numeric(18,4) default 0 comment '税率',
   tax_money            numeric(18,4) default 0 comment '税额',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
);


/*==============================================================*/
/* Table: gt_seller_taxpayer_info        销售方税务相关信息        */
/*==============================================================*/
drop table if exists gt_seller_taxpayer_info;
create table gt_seller_taxpayer_info
(
   id                   bigint(32) not null comment 'ID',
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(100) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   seller_name          varchar(100) comment '销售方名称',
   seller_tin_no        varchar(20) comment '销售方纳税人识别号',
   seller_addr_phone    varchar(100) comment '销售方地址、电话',
   seller_bank_account  varchar(100) comment '销售方开户行及账户',
   state                smallint comment '状态',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
);


/*==============================================================*/
/* Table: gt_receipt_invoice_association        票据发票关联表    */
/*==============================================================*/
drop table if exists gt_receipt_invoice_association;
create table gt_receipt_invoice_association
(
   id                   bigint(32) not null comment 'ID',
   receipt_id           bigint(32) comment '票据ID',
   invoice_id           bigint(32) comment '发票ID',
   state                smallint comment '状态',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id)
);


/*==============================================================*/
/* Table: gt_summary_bill           扎帐信息表                    */
/*==============================================================*/
drop index unique_summary_bill on gt_summary_bill;
drop table if exists gt_summary_bill;
create table gt_summary_bill
(
   id                   bigint(32) not null comment 'ID',
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(100) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   total_amount         numeric(18,4) default 0 comment '合计金额',
   cash_amount          numeric(18,4) default 0 comment '现金金额',
   bank_transfer_amount numeric(18,4) default 0 comment '银行转账金额',
   alipay_amount        numeric(18,4) default 0 comment '支付宝金额',
   wechat_amount        numeric(18,4) default 0 comment '微信金额',
   refund_amount        numeric(18,4) default 0 comment '退费金额',
   pre_deposit_deduction numeric(18,4) default 0 comment '预存抵扣',
   receipt_invoice_total_num int comment '合计发票数',
   receipt_total_num    int comment '票据数',
   blue_invoice_total_num int comment '蓝票数',
   red_invoice_total_num int comment '红票数',
   invalid_invoice_total_num int comment '废票数',
   receipt_invoice_total_amount numeric(18,4) default 0 comment '合计发票金额',
   operator_no          varchar(32) comment '操作员编号',
   operator_name        varchar(100) comment '操作员姓名',
   summary_bill_date    varchar(8) comment '扎帐日期',
   data_status          smallint comment '数据状态',
   create_user          bigint(32) comment '创建用户ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '更新用户ID',
   update_time          datetime comment '更新时间',
   primary key (id),
   unique index unique_summary_bill (operator_no, summary_bill_date)
);








