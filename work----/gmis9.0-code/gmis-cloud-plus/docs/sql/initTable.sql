-- 营业厅信息
drop table if exists pz_business_hall;
create table pz_business_hall
(
   id                   bigint(32) 			not null comment 'id',
   company_code         varchar(32)			comment '公司编码',
   company_name         varchar(100) 		comment '公司名称',
   org_id               bigint(32) 			comment '组织id',
   org_name	            varchar(100)		comment '组织名称',
   business_hall_name   varchar(100)		comment '营业厅名称',
   business_hall_address varchar(100)		comment '营业厅地址',
   contacts             varchar(100)		comment '联系人',
   telphone             varchar(20)			comment '联系电话',
   email                varchar(30)			comment '联系邮件',
   point_type           varchar(30)			comment '配置类型',
   is_sale              smallint			comment '代售点',
   point_of_sale        smallint			comment '代售点（配额）',
   restrict_status      smallint 			comment '营业限制0：不限制，1：限制',
   balance              numeric(18,4)		comment '营业余额',
   single_limit			numeric(18,4) 		comment '单笔限额',
   business_hall_status smallint 			comment '状态1：启用 0：停用',
   create_user          bigint(32)			comment '创建人',
   create_time          datetime			comment '创建时间',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   delete_user          bigint(32)			comment '删除人id',
   delete_time          datetime			comment '删除时间',
   delete_status        smallint            default 0 comment '删除标识',
   primary key (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营业厅信息表';

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
  `data_status` smallint(6) DEFAULT 0 COMMENT '状态\r\n            1 启用\r\n            0 禁用',
  `delete_status` smallint(6) DEFAULT 0 COMMENT '删除状态\r\n            1 删除\r\n            0 正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收费项配置\r\n开户费\r\n换表费\r\n补卡费\r\n报装费\r\n卡费';


-- 模板类型配置表
drop table if exists pz_template;
create table pz_template
(
   id                   bigint(32) 			not null comment 'id',
   company_code         varchar(32) 		comment '公司编码',
   company_name         varchar(100) 		comment '公司名称',
   org_id               bigint(32) 			comment '组织id',
   org_name             varchar(100)		comment '组织名称',
   template_type_code   varchar(64)			comment '模板类型编码',
   template_type_name   varchar(64)			comment '模板类型名称',
   template_type_describe    varchar(200)	comment '模板类型描述',
   template_status		smallint			comment '状态',
   remark               varchar(300)		comment '备注',
   create_user          bigint(32)			comment '创建人id',
   create_time          datetime			comment '创建时间',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   delete_status        smallint            default 0 comment '删除标识',
   primary key (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板类型配置表';

-- 模板项目表（Item）
drop table if exists pz_template_item;
create table pz_template_item
(
   id                   bigint(32) 			not null comment 'id',
   company_code         varchar(32)			comment '公司编码',
   company_name         varchar(100) 		comment '公司名称',
   org_id               bigint(32) 			comment '组织id',
   org_name             varchar(100)		comment '组织名称',
   template_type_id     bigint(20)			comment '模板类型id',
   template_type_name   varchar(64)			comment '模板类型名称',
   template_code        varchar(64)			comment '模板编码',
   template_name        varchar(64)			comment '模板名称',
   file_id              bigint(32) 			comment '文件id',
   default_file_id      bigint(32) 			comment '默认文件id',
   file_path            varchar(300)		comment '文件路径',
   item_status          smallint			default 1 comment '状态（1-默认文件，0-自定义文件）',
   item_describe        varchar(255)		comment '描述',
   item_content         varchar(5000)		comment '内容',
   data_status          smallint			default null comment '审核状态（-1-未通过，0-提交但未审核，1-审核通过，null-未提交审核）',
   sort_value           int(11)				comment '排序',
   create_user          bigint(32)			comment '创建人id',
   create_time          datetime			comment '创建时间',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   delete_status        smallint            default 0 comment '删除标识',
   primary key (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板项目表（Item）';

-- 短信模板配置
drop table if exists pz_mobile_message;
create table pz_mobile_message
(
   id                   bigint(32) 			not null comment 'id',
   company_code         char(32) 			comment '公司编码',
   company_name         varchar(100) 		comment '公司名称',
   org_id               bigint(32) 			comment '组织id',
   org_name             varchar(100)		comment '组织名称',
   template_code        varchar(64) 		comment '模板编码',
   template_name        varchar(64)			comment '模板名称',
   template_describe    varchar(300)		comment '模板描述',
   template_cntent	    varchar(300)		comment '模板内容',
   file_path            varchar(300)		comment '文件路径',
   template_status      smallint			comment '状态',
   remark               varchar(300)		comment '备注',
   create_user          bigint(32)			comment '创建人id',
   create_time          datetime			comment '创建时间',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   delete_status        smallint            default 0 comment '删除标识',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信模板配置';

--气表厂家
drop table if exists pt_gas_meter_factory;
CREATE TABLE `pt_gas_meter_factory` (
  `id`                      bigint(32) NOT NULL         COMMENT 'ID',
  `gas_meter_factory_name`  varchar(100) DEFAULT NULL   COMMENT '厂家名称',
  `gas_meter_factory_code`  varchar(10) DEFAULT NULL    COMMENT '厂家编号',
  `gas_meter_type`          varchar(100) DEFAULT NULL   COMMENT '普表\r\n ic卡智能燃气表\r\n 物联网表\r\n 流量计(普表)\r\n 流量 计(ic卡表)\r\n 流量计(物联网表)\r\n',
  `gas_meter_factory_status` smallint(6) DEFAULT NULL   COMMENT '厂家状态',
  `compatible_parameter`    varchar(1000) DEFAULT NULL  COMMENT '兼容参数',
  `create_time` datetime    DEFAULT NULL                COMMENT '创建时间',
  `create_user` bigint(32)  DEFAULT NULL                COMMENT '创建人',
  `update_time` datetime    DEFAULT NULL                COMMENT '更新时间',
  `update_user` bigint(32)  DEFAULT NULL                COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表厂家';

--气表版号
drop table if exists pt_gas_meter_version;
CREATE TABLE `pt_gas_meter_version` (
  `id`                  bigint(32) NOT NULL COMMENT 'ID',
  `company_id`          char(32) DEFAULT NULL COMMENT '厂家ID',
  `gas_meter_version_name` varchar(30) DEFAULT NULL COMMENT '版号名称',
  `remark`              varchar(100) DEFAULT NULL   COMMENT '版号说明',
  `gas_meter_type`      varchar(30) DEFAULT NULL    COMMENT 'ORDINARY_METER:普表\r\n            IC_METER:IC卡智能燃气表\r\n            IOT_METER物联网表\r\n            FLOWMETER_ORDINARY_METER:流量计(普表)\r\n            FLOWMETER_IC_METER:流量 计(IC卡表)\r\n            FLOWMETER_IOT_METER:流量计(物联网表)',
  `settlement_type`     varchar(10) DEFAULT NULL    COMMENT 'GAS:气量\r\n            MONEY:金额',
  `settlement_mode`     varchar(10) DEFAULT NULL    COMMENT 'READMETER:抄表结算\r\n            METER:表端结算\r\n            SERVICE:服务端结算',
  `charge_type`         varchar(20) DEFAULT NULL    COMMENT 'IC_RECHARGE:IC卡充值\r\n            READMETER_PAY:抄表缴费\r\n            REMOTE_RECHARGE:远程充值',
  `measurement_accuracy` smallint(6) DEFAULT NULL   COMMENT '计费精度',
  `card_number_prefix`  varchar(10) DEFAULT NULL    COMMENT '卡号前缀',
  `card_number_length`  smallint(6) DEFAULT NULL    COMMENT '卡号长度',
  `open_in_meter_number` smallint(6) DEFAULT NULL   COMMENT '开户录入表号',
  `compatible_parameter` varchar(1000) DEFAULT NULL COMMENT '如气表功能控制、兼容标识、兼容参数等。\r\n            建议JSON存储',
  `function`            varchar(1000) DEFAULT NULL  COMMENT 'READMETER:抄表\r\n            CLOSE_VALVE:普通关阀\r\n            FORCED_CLOSING_VALVE:强制关阀\r\n            OPEN_VALVE:开阀\r\n            MODIFY_PRICE:调价\r\n            UPDATE_CYCLE_GAS:周期累计量修改\r\n\r\n\r\n            ',
  `version_state`       smallint(6) DEFAULT NULL    COMMENT '开启\r\n		禁用',
  `create_time`         datetime DEFAULT NULL       COMMENT '创建时间',
  `create_user`         bigint(32) DEFAULT NULL     COMMENT '创建人',
  `update_time`         datetime DEFAULT NULL       COMMENT '更新时间',
  `update_user`         bigint(32) DEFAULT NULL     COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表版本';

--节点设备厂家信息
drop table if exists pt_node_factory;
CREATE TABLE `pt_node_factory` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `node_facotry_name` varchar(100) DEFAULT NULL COMMENT '厂家名称',
  `node_facotry_code` varchar(10) DEFAULT NULL COMMENT '厂家编号',
  `facotry_use_node_type` varchar(100) DEFAULT NULL COMMENT 'RTU:rtu设备\r\n            FLOWMETER:流量计',
  `node_factory_status` smallint(6) DEFAULT NULL COMMENT '启用\r\n	禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='节点厂家';

--节点设备型号
drop table if exists pt_node_type;
CREATE TABLE `pt_node_type` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `node_facotry_id` char(32) DEFAULT NULL COMMENT '厂家ID',
  `node_name` varchar(50) DEFAULT NULL COMMENT '型号名称',
  `node_code` varchar(10) DEFAULT NULL COMMENT '型号编码',
  `remark` varchar(100) DEFAULT NULL COMMENT '型号说明',
  `type` varchar(10) DEFAULT NULL COMMENT 'RTU:rtu设备\r\n            FLOWMETER:流量计',
  `function` varchar(500) DEFAULT NULL COMMENT 'TEMPERATURE:温度监测\r\n            PRESSURE:压力监测\r\n            FLOW:流量监测\r\n            VALVE:阀门控制\r\n\r\n            注：多选\r\n\r\n            ',
  `node_status` smallint(6) DEFAULT NULL COMMENT '启用\r\n            禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='节点类型';

--公司使用节点设备
drop table if exists pt_company_use_node_type;
CREATE TABLE `pt_company_use_node_type` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `node_factory_id` bigint(32) DEFAULT NULL COMMENT '厂家ID',
  `node_type_id` bigint(32) DEFAULT NULL COMMENT '型号ID',
    node_facotry_name    varchar(100) comment '厂家名称',
   node_name            varchar(50) comment '型号名称',
   use_status           smallint comment '启用\r\n            禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='节点类型管理';



--公司气表版号
drop table if exists pt_company_user_gas_meter_version;
CREATE TABLE `pt_company_user_gas_meter_version` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司CODE',
  `gas_meter_factory_id` bigint(32) DEFAULT NULL COMMENT '厂家ID',
  `gas_meter_version_id` bigint(32) DEFAULT NULL COMMENT '版号ID',
   gas_meter_factory_name    varchar(100) comment '厂家名称',
   gas_meter_version_name            varchar(50) comment '版号名称',
   use_status           smallint comment '启用\r\n            禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='气表版号管理';

-- 支付配置
drop table if exists pz_pay_info;
create table pz_pay_info
(
   id                   bigint(32) 			not null comment 'id',
   company_code         char(32)			comment '公司编码',
   company_name         varchar(100)		comment '公司名称',
   org_id               bigint(32)			comment '组织id',
   org_name             varchar(100)		comment '组织名称',
   account_level		smallint			comment '账号等级（0-总公司，1-分公司）',
   used_parent_pay      smallint			comment '使用总公司账号',
   pay_code             varchar(64)			comment '支付类型编码',
   pay_type             varchar(64)			comment '支付类型名称',
   merchant_no          varchar(100)		comment '商户号',
   app_id               varchar(100)		comment 'APPID',
   app_secret           varchar(100)		comment 'APPSECRET',
   api_secret           varchar(100)		comment 'API秘钥',
   merchant_private_key varchar(2000)		comment '商户私钥',
   alipay_public_key    varchar(400)		comment '支付宝公钥',
   service_ratio        decimal(4,3)        DEFAULT NULL COMMENT '提现用户承担手续费比例',
   service_status       smallint            default 0 comment '是否启用手续费比例（0-启用，1-禁用）',
   remark               varchar(300)		comment '备注',
   pay_status           smallint			comment '状态',
   create_user          bigint(32)			comment '创建人id',
   create_time          datetime			comment '创建时间',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   delete_status        smallint            default 0 comment '删除标识',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付配置';

-- 开票参数配置
drop table if exists pz_invoice_param;
create table pz_invoice_param
(
   id                   bigint(32) 			not null comment 'id',
   company_code         varchar(32)			comment '公司编码',
   company_name         varchar(100)		comment '公司名称',
   org_id               bigint(32)			comment '组织id',
   org_name             varchar(100)		comment '组织名称',
   account_level		smallint			comment '账号等级（0-总公司，1-分公司）',
   name                 varchar(100)		comment '销方名称',
   taxpayer_no          varchar(100)		comment '销方纳税人编号',
   address              varchar(300)		comment '销方地址',
   telephone            varchar(20)			comment '销方电话',
   bank_name            varchar(100)		comment '销方开户行名称',
   bank_account         varchar(60)			comment '销方银行账户',
   invoice_status               smallint			comment '状态',
   remark               varchar(300)		comment '备注',
   create_user          bigint(32)			comment '创建人id',
   create_time          datetime			comment '创建时间',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   delete_status        smallint            default 0 comment '删除标识',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开票参数配置';

-- 抄表计划
drop table if exists cb_read_meter_plan;
create table cb_read_meter_plan
(
   id                   bigint(32) 			not null comment 'id',
   company_code         varchar(32)			comment '公司编码',
   company_name         varchar(100)		comment '公司名称',
   org_id               bigint(32)			comment '组织id',
   org_name             varchar(100)		comment '组织名称',
   read_meter_year      smallint			comment '抄表年份',
   read_meter_month     smallint			comment '抄表月份',
   plan_start_time      date			comment '计划开始时间',
   plan_end_time        date			comment '计划结束时间',
   total_read_meter_count int				default 0 comment '总户数',
   read_meter_count     int					default 0 comment '已抄数',
   review_count         int					default 0 comment '已审数',
   settlement_count     int					default 0 comment '结算数',
   data_status          smallint			default -1 comment '状态（-1：未开启；1：执行中；2-暂停；0-执行完成）',
   create_user          bigint(32)			comment '创建人id',
   create_time          datetime			comment '创建时间',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表计划';

-- 抄表每月用气止数记录表
drop table if exists cb_read_meter_month_gas;
CREATE TABLE `cb_read_meter_month_gas` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `customer_id` char(32) DEFAULT NULL COMMENT '客户id',
  `customer_code` varchar(30) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `gas_meter_code` char(32) DEFAULT NULL COMMENT '气表编号',
  `gas_meter_number` char(30) DEFAULT NULL COMMENT '表身号',
  `gas_meter_name` varchar(100) DEFAULT NULL COMMENT '气表名称',
  `year` smallint DEFAULT NULL COMMENT '年份',
  `jan_total_gas` decimal(18,4) DEFAULT NULL COMMENT '一月止数',
  `Feb_total_gas` decimal(18,4) DEFAULT NULL COMMENT '二月止数',
  `Mar_total_gas` decimal(18,4) DEFAULT NULL COMMENT '三月止数',
  `Apr_total_gas` decimal(18,4) DEFAULT NULL COMMENT '四月止数',
  `May_total_gas` decimal(18,4) DEFAULT NULL COMMENT '五月止数',
  `Jun_total_gas` decimal(18,4) DEFAULT NULL COMMENT '六月止数',
  `Jul_total_gas` decimal(18,4) DEFAULT NULL COMMENT '七月止数',
  `Aug_total_gas` decimal(18,4) DEFAULT NULL COMMENT '八月止数',
  `Sept_total_gas` decimal(18,4) DEFAULT NULL COMMENT '九月止数',
  `Oct_total_gas` decimal(18,4) DEFAULT NULL COMMENT '十月止数',
  `Nov_total_gas` decimal(18,4) DEFAULT NULL COMMENT '十一月止数',
  `Dec_total_gas` decimal(18,4) DEFAULT NULL COMMENT '十二月止数',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表每月用气止数记录表';

-- 抄表任务分配
drop table if exists cb_read_meter_plan_scope;
create table cb_read_meter_plan_scope
(
   ID                   bigint(32)			not null comment 'id',
   readmeter_plan_id    bigint(32)			comment '抄表计划id',
   book_id              bigint(32)			comment '抄表册id',
   book_name            varchar(30)			comment '抄表册名称',
   read_meter_user_name varchar(100) DEFAULT NULL COMMENT '抄表员名称',
   total_read_meter_count int				default 0 comment '总户数',
   read_meter_count     int					default 0 comment '已抄数',
   review_count         int					default 0 comment '已审数',
   settlement_count     int					default 0 comment '结算数',
   data_status          smallint 			default -1 comment '状态（-1：未抄表；1：抄表中；2-暂停；0-抄表完成）',
   create_user          bigint(32)			comment '创建人id',
   create_time          datetime			comment '创建时间0',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   delete_status        smallint            default 0 comment '删除标识（0：存在；1：删除）',
   primary key (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表任务分配';

-- 抄表册
drop table if exists cb_read_meter_book;
create table cb_read_meter_book
(
   id                   bigint(32) 			not null comment 'id',
   company_code         varchar(32)			comment '公司编码',
   company_name         varchar(100)		comment '公司名称',
   org_id               bigint(32)			comment '组织id',
   org_name             varchar(100)		comment '组织名称',
   community_id         bigint(32)			comment '所属区域id',
   community_name       varchar(100)		comment '所属区域名称',
   book_code            varchar(10)			comment '抄表册编号',
   book_name            varchar(30)			comment '抄表册名称',
   read_meter_user      bigint(32)			comment '抄表员id',
   read_meter_user_name varchar(100)		comment '抄表员名称',
   total_read_meter_count numeric(18)		default 0 comment '总户数',
   book_status          smallint			default -1 comment '状态(-1：空闲;1：未抄表；0：抄表完成；2：抄表中，)',
   cited_count          smallint			default 0 comment '被未完成抄表计划引用次数',
   remark               varchar(100)		comment '说明',
   create_user          bigint(32)			comment '创建人id',
   create_time          datetime			comment '创建时间',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   delete_status        smallint            default 0 comment '删除标识（0：存在；1：删除）',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表册';

-- 抄表册关联客户
drop table if exists cb_gas_meter_book_record;
create table cb_gas_meter_book_record
(
   id                   bigint(32) 			not null comment 'id',
   read_meter_book      char(32)			comment '抄表册id',
   community_id         bigint(32)			comment '所属区域id',
   community_name       varchar(100)		comment '所属区域名称',
   street_id            bigint(32)			comment '所属街道id',
   street_name          varchar(100)		comment '所属街道名称',
   customer_id          bigint(32)			comment '客户id',
   customer_code        varchar(30)			comment '客户编号',
   customer_name        varchar(60)			comment '客户名称',
   gas_meter_code       varchar(60)			comment '气表编号',
   gas_meter_type       varchar(60)			comment '气表类型',
   use_gas_type_id      varchar(30)			comment '用气类型id',
   use_gas_type_name    varchar(60)			comment '用气类型名称',
   gas_meter_address    varchar(200)		comment '安装地址',
   read_meter_user bigint(32) DEFAULT NULL COMMENT '抄表员id',
   read_meter_user_name varchar(100) DEFAULT NULL COMMENT '抄表员名称',
   record_status        smallint			comment '状态',
   sort                 int					comment '自定义序号',
   create_time          datetime			comment '创建时间',
   create_user          bigint(32)			comment '创建人id',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   delete_status        smallint            default 0 comment '删除标识（0：存在；1：删除）',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表册关联客户';

-- 抄表数据
drop table if exists cb_read_meter_data;
create table cb_read_meter_data
(
   id                   bigint(32) 			not null comment 'id',
   company_code         varchar(32)			comment '公司编码',
   company_name         varchar(100)		comment '公司名称',
   org_id               bigint(32)			comment '组织id',
   org_name             varchar(100)		comment '组织名称',
   business_hall_id     char(32)			comment '营业厅id',
   business_hall_name   varchar(100)		comment '营业厅名称',
   customer_id          char(32)			comment '客户id',
   customer_code        varchar(30)			comment '客户编号',
   customer_name        varchar(100)		comment '客户名称',
   gas_meter_code       char(32)			comment '气表编号',
   gas_meter_number     char(30)			comment '表身号',
   gas_meter_name       varchar(100)		comment '气表名称',
   gas_meter_address    varchar(100)		comment '安装地址',
   plan_id              bigint(32)			comment '计划id',
   book_id              bigint(32)			comment '抄表册id',
   read_meter_year      smallint			comment '抄表年份',
   read_meter_month     smallint			comment '抄表月份',
   read_time            date			    comment '抄表年月',
   last_total_gas       numeric(18,4)		comment '上期止数',
   current_total_gas    numeric(18,4)		comment '本期止数',
   month_use_gas        numeric(18,4)		comment '本期用量',
   unit_price           numeric(18,4)		comment '单价',
   cycle_total_use_gas  numeric(18,4)		comment '周期累计用气量',
   use_gas_type_id      bigint(32)			comment '用气类型id',
   use_gas_type_name    varchar(50)			comment '用气类型名称',
   price_scheme_id      int					comment '价格号',
   process_status       varchar(30)			comment '流程状态' ,
   charge_status        varchar(30)			comment '抄表收费状态',
   data_status          smallint			default -1 comment '数据状态（-1：未抄表；2-取消；0-已抄）',
   read_meter_user_id   bigint(32)			comment '抄表员id',
   read_meter_user_name varchar(100)		comment '抄表员名称',
   record_user_id       bigint(32)			comment '记录员id',
   record_user_name     varchar(100)		comment '记录员名称',
   record_time          datetime			comment '记录时间',
   review_user_id       bigint(32)			comment '审核人id',
   review_user_name     varchar(100)		comment '审核人名称',
   review_time          datetime			comment '审核时间',
   review_objection     varchar(100)		comment '审核意见',
   settlement_user_id   bigint(32)			comment '结算人id',
   settlement_user_name varchar(100)		comment '结算人名称',
   settlement_time      datetime			comment '结算时间',
   gas_owe_code         char(32)			comment '燃气欠费编号',
   free_amount          numeric(18,4)		comment '收费金额',
   penalty              numeric(18,4)		comment '滞纳金',
   days_overdue         int					comment '超期天数',
   gas_1                decimal(18,4)		comment '1阶气量',
   price_1              numeric(18,4)		comment '1阶价格',
   gas_2                decimal(18,4)		comment '2阶气量',
   price_2              numeric(18,4)		comment '2阶价格',
   gas_3                decimal(18,4)		comment '3阶气量',
   price_3              numeric(18,4)		comment '3阶价格',
   gas_4                decimal(18,4)		comment '4阶气量',
   price_4              numeric(18,4)		comment '4阶价格',
   gas_5                decimal(18,4)		comment '5阶气量',
   price_5              numeric(18,4)		comment '5阶价格',
   gas_6                decimal(18,4)		comment '6阶气量',
   price_6              numeric(18,4)		comment '6阶价格',
   create_user          bigint(32)			comment '创建人id',
   create_time          datetime			comment '创建时间',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表数据';

-- 抄表调价补差信息
drop table if exists cb_compensation_for_read_meter;
create table cb_compensation_for_read_meter
(
   ID                   bigint(32) 			not null comment 'id',
   read_meter_data_id   bigint(32)			comment '抄表id',
   compensation_price   numeric(18,4)		comment '补差价',
   compensation_gas     numeric(18,4)		comment '补差量',
   compensation_money   numeric(18,4)		comment '补差金额',
   accounting_user_id   bigint(32)			comment '核算人id',
   accounting_user_name varchar(100)		comment '核算人名称',
   review_user_id       bigint(32)			comment '审批人id',
   review_user_name     varchar(100)		comment '审批人名称',
   review_time          datetime			comment '审批时间',
   review_objection     varchar(100)		comment '审批意见',
   accounting_time      datetime			comment '核算时间',
   charge_status        smallint			comment '收费状态',
   charge_time          datetime			comment '收费时间',
   charge_order_id      bigint(32)			comment '收费订单id',
   data_status          smallint			comment '状态',
   primary key (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄表调价补差信息';

-- 银行代扣记录
drop table if exists cb_bank_withhold_record;
create table cb_bank_withhold_record
(
   id                   bigint(32) 			not null comment 'id',
   company_code         varchar(32)			comment '公司编码',
   company_name         varchar(100)		comment '公司名称',
   org_id               bigint(32)			comment '组织id',
   org_name             varchar(100)		comment '组织名称',
   customer_code        varchar(32)			comment '客户编号',
   customer_name        varchar(100)		comment '客户名称',
   gas_meter_code       char(32)			comment '气表编号',
   gas_meter_name       varchar(100)		comment '气表名称',
   gas_meter_type_id    char(32)			comment '气表类型id',
   gas_meter_type_name  varchar(100)		comment '气表类型名称',
   gas_meter_address    varchar(200)		comment '气表安装地址',
   cardholder           varchar(30)			comment '持卡人',
   bank_account         varchar(100)		comment '银行账号',
   amount               numeric(18,4)		comment '金额',
   import_status        smallint			comment '导入状态',
   remark               varchar(100)		comment '备注',
   create_user          bigint(32)			comment '创建人id',
   create_time          datetime			comment '创建时间',
   update_user          bigint(32)			comment '修改人id',
   update_time          datetime			comment '修改时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='银行代扣记录';

-- 初始化模板类型
INSERT INTO `c_common_dictionary` VALUES (5, 'TEMPLATE_TYPE', '模板类型', '', b'1', 1, '2020-07-02 10:15:42', 1, '2020-07-02 10:15:42');
INSERT INTO `c_common_dictionary_item` VALUES (1278513583239462912, 5, 'TEMPLATE_TYPE', 'BILL_TEMPLATE', '单据模板', b'1', '', 1, 1, '2020-07-02 10:19:27', 1, '2020-07-02 10:19:27');
INSERT INTO `c_common_dictionary_item` VALUES (1278513855198134272, 5, 'TEMPLATE_TYPE', 'TICKET_TEMPLATE', '票据模板', b'1', '', 1, 1, '2020-07-02 10:20:31', 1, '2020-07-02 10:20:31');
INSERT INTO `c_common_dictionary_item` VALUES (1278514034953420800, 5, 'TEMPLATE_TYPE', 'INVOICE_TEMPLATE', '发票模板', b'1', '', 1, 1, '2020-07-02 10:21:14', 1, '2020-07-02 10:21:14');
INSERT INTO `c_common_dictionary` VALUES (6, 'PAYMENT_METHOD', '支付方式', '', b'1', 1, '2020-07-07 11:18:59', 1, '2020-07-07 11:18:59');
INSERT INTO `c_common_dictionary_item` VALUES (1280340884994392064, 6, 'PAYMENT_METHOD', 'WECHAT', '微信', b'1', '', 1, 1, '2020-07-07 11:20:29', 1, '2020-07-07 11:20:29');
INSERT INTO `c_common_dictionary_item` VALUES (1280341031853752320, 6, 'PAYMENT_METHOD', 'ALIPAY', '支付宝', b'1', '', 1, 1, '2020-07-07 11:21:04', 1, '2020-07-07 11:21:04');
INSERT INTO `c_common_dictionary_item` VALUES (1280341287521746944, 6, 'PAYMENT_METHOD', 'CASH', '现金', b'1', '', 1, 1, '2020-07-07 11:22:05', 1, '2020-07-07 11:22:05');
INSERT INTO `c_common_dictionary_item` VALUES (1280341361932894208, 6, 'PAYMENT_METHOD', 'POS', 'pos', b'1', '', 1, 1, '2020-07-07 11:22:23', 1, '2020-07-07 11:22:23');
-- 短信类型初始化
INSERT INTO `c_common_dictionary` VALUES (7, 'SMS_TYPE', '短信类型', '', b'1', 1, '2020-07-07 11:18:59', 1, '2020-07-07 11:18:59');
INSERT INTO `c_common_dictionary_item` VALUES ('1290854419893583872', '7', 'SMS_TYPE', 'NOTICE', '公告', b'1', '', '1', '3', '2020-08-05 11:37:31', '3', '2020-08-05 11:37:31');
INSERT INTO `c_common_dictionary_item` VALUES ('1290854634004414464', '7', 'SMS_TYPE', 'PAYMENT_NOTICE', '缴费通知', b'1', '', '1', '3', '2020-08-05 11:38:22', '3', '2020-08-05 11:38:22');
INSERT INTO `c_common_dictionary_item` VALUES ('1290855060246364160', '7', 'SMS_TYPE', 'ARREARS_NOTICE', '欠费通知', b'1', '', '1', '3', '2020-08-05 11:40:04', '3', '2020-08-05 11:40:04');
INSERT INTO `c_common_dictionary_item` VALUES ('1290855278815739904', '7', 'SMS_TYPE', 'COLLECTION_LETTER', '催款单', b'1', '', '1', '3', '2020-08-05 11:40:56', '3', '2020-08-05 11:40:56');
INSERT INTO `c_common_dictionary_item` VALUES ('1290855545846104064', '7', 'SMS_TYPE', 'GAS_BALANCE_REMINDER', '气表余额提醒', b'1', '', '1', '3', '2020-08-05 11:42:00', '3', '2020-08-05 11:42:00');
INSERT INTO `c_common_dictionary_item` VALUES ('1290855812327014400', '7', 'SMS_TYPE', 'CAPTCHA_NOTIFICATION', '验证码通知', b'1', '', '1', '3', '2020-08-05 11:43:03', '3', '2020-08-05 11:43:03');
-- 短信字段初始化
INSERT INTO `c_common_dictionary` VALUES (8, 'SMS_FIELD', '短信字段', '用于添加短信模板时选择的不确定参数', b'1', '3', '2020-08-10 17:15:44', '3', '2020-08-10 17:15:44');
INSERT INTO `c_common_dictionary_item` VALUES ('1292751621406588928', '8', 'SMS_FIELD', 'startTime', '开始时间', b'1', '', '1', '3', '2020-08-10 17:16:19', '3', '2020-08-10 17:16:19');
INSERT INTO `c_common_dictionary_item` VALUES ('1292751723307204608', '8', 'SMS_FIELD', 'endTime', '结束时间', b'1', '', '1', '3', '2020-08-10 17:16:44', '3', '2020-08-10 17:16:44');
INSERT INTO `c_common_dictionary_item` VALUES ('1292751799945527296', '8', 'SMS_FIELD', 'deadTime', '截止时间', b'1', '', '1', '3', '2020-08-10 17:17:02', '3', '2020-08-10 17:17:02');
INSERT INTO `c_common_dictionary_item` VALUES ('1292751868665004032', '8', 'SMS_FIELD', 'userName', '用户名', b'1', '', '1', '3', '2020-08-10 17:17:18', '3', '2020-08-10 17:17:18');
INSERT INTO `c_common_dictionary_item` VALUES ('1292751947492753408', '8', 'SMS_FIELD', 'balance', '余额', b'1', '', '1', '3', '2020-08-10 17:17:37', '3', '2020-08-10 17:17:37');
INSERT INTO `c_common_dictionary_item` VALUES ('1292752117534031872', '8', 'SMS_FIELD', 'arrears', '欠费金额', b'1', '', '1', '3', '2020-08-10 17:18:18', '3', '2020-08-10 17:18:18');
INSERT INTO `c_common_dictionary_item` VALUES ('1292752195816521728', '8', 'SMS_FIELD', 'totalAmount', '总计金额', b'1', '', '1', '3', '2020-08-10 17:18:36', '3', '2020-08-10 17:18:36');
INSERT INTO `c_common_dictionary_item` VALUES ('1292991885391953920', '8', 'SMS_FIELD', 'captcha', '验证码', b'1', '', '1', '3', '2020-08-11 09:11:03', '3', '2020-08-11 09:11:03');
INSERT INTO `c_common_dictionary_item` VALUES ('1292991949359284224', '8', 'SMS_FIELD', 'place', '地点', b'1', '', '1', '3', '2020-08-11 09:11:18', '3', '2020-08-11 09:11:18');
INSERT INTO `c_common_dictionary_item` VALUES ('1292992106691821568', '8', 'SMS_FIELD', 'residueGas', '剩余气量', b'1', '', '1', '3', '2020-08-11 09:11:56', '3', '2020-08-11 09:11:56');
-- 预制模板
INSERT INTO `f_attachment` VALUES ('1290085393122721792', '1290085392820731904', '222', 'DOC', '测试模板.txt', '', '0000/2020/08/d357e24c-4aee-43be-b089-eaa0f0338268.txt', 'https://file-test-1259370834.cos.ap-chengdu.myqcloud.com/0000/2020/08/d357e24c-4aee-43be-b089-eaa0f0338268.txt', 'https://file-test-1259370834.cos.ap-chengdu.myqcloud.com/0000/2020/08/d357e24c-4aee-43be-b089-eaa0f0338268.txt', NULL, 'text/plain', '.txt', 'txt', '1486', NULL, 'el-icon-document', '2020年08月', '2020年32周', '2020年08月03日', '2020-08-03 08:41:41', '3', '2020-08-03 08:41:41', '3');
INSERT INTO `pz_template` VALUES ('1288663484254388224', '0000', '1', '0', '', 'BILL_TEMPLATE', '单据模板', '', '0', '', NULL, '2020-07-30 10:31:32', NULL, '2020-07-30 10:31:32', '0');
INSERT INTO `pz_template` VALUES ('1288663681818689536', '0000', '1', '0', '', 'TICKET_TEMPLATE', '票据模板', '', '0', '', NULL, '2020-07-30 10:32:19', NULL, '2020-07-30 10:32:19', '0');
INSERT INTO `pz_template` VALUES ('1288663754338205696', '0000', '1', '0', '', 'INVOICE_TEMPLATE', '发票模板', '', '0', '', NULL, '2020-07-30 10:32:36', NULL, '2020-07-30 10:32:36', '0');
INSERT INTO `pz_template_item` VALUES ('1288666073863815168', '0000', '', '0', '', '1288663484254388224', '单据模板', 'WORK_ORDER', '包装工单', '1290085393122721792', '1290085393122721792', '', '0', '', '0', NULL, '2020-07-30 10:41:49', '3', '2020-08-03 08:41:41', '0');
INSERT INTO `pz_template_item` VALUES ('1288666346875256832', '0000', '', '0', '', '1288663484254388224', '单据模板', 'PAYMENT_NOTICE', '缴费通知单', '1289124974765801472', '1290085393122721792', '', '0', '', '0', NULL, '2020-07-30 10:42:54', '3', '2020-07-31 17:05:20', '0');
INSERT INTO `pz_template_item` VALUES ('1288666550168977408', '0000', '', '0', '', '1288663681818689536', '票据模板', 'GAS_BILL', '燃气收费票据', NULL, '1290085393122721792', '', '1', '', '0', NULL, '2020-07-30 10:43:42', NULL, '2020-07-31 16:06:15', '0');
INSERT INTO `pz_template_item` VALUES ('1288666609774231552', '0000', '', '0', '', '1288663681818689536', '票据模板', 'COMMODITY_CHARGE_BILL', '商品收费票据', NULL, '1290085393122721792', '', '1', '', '0', NULL, '2020-07-30 10:43:57', NULL, '2020-07-31 15:26:01', '0');
INSERT INTO `pz_template_item` VALUES ('1288666732738641920', '0000', '', '0', '', '1288663754338205696', '发票模板', 'COMMODITY_INVOICE', '商品发票', '1289125608999092224', '1290085393122721792', '', '0', '', '0', NULL, '2020-07-30 10:44:26', '3', '2020-07-31 17:07:51', '0');
-- 气表
INSERT INTO `c_common_dictionary` VALUES ('1296627384161665024', 'WIRELESS_EQUIPMENT_VENDORS', '无线通信设备厂商', '无线通信设备厂商', b'1', '3', '2020-08-21 09:57:13', '3', '2020-08-21 09:57:33');
INSERT INTO `c_common_dictionary` VALUES ('1296631159827988480', 'AMOUNT_MARK', '金额标志', '金额标志', b'1', '3', '2020-08-21 10:12:13', '3', '2020-08-21 10:12:13');
INSERT INTO `c_common_dictionary` VALUES ('1296632113117790208', 'REMOTE_SERVICE_FLAG', '远程业务标志', '远程业务标志', b'1', '3', '2020-08-21 10:16:01', '3', '2020-08-21 10:16:01');
INSERT INTO `c_common_dictionary` VALUES ('1296633741384351744', 'CARD_TYPE', '卡号类型', '卡号类型', b'1', '3', '2020-08-21 10:22:29', '3', '2020-08-21 10:22:29');
INSERT INTO `c_common_dictionary` VALUES ('1296634238786863104', 'MODEL_AND_SPECIFICATION', '型号规格', '型号规格', b'1', '3', '2020-08-21 10:24:27', '3', '2020-08-21 10:24:27');
INSERT INTO `c_common_dictionary_item` VALUES ('1296630742045949952', '1296627384161665024', 'WIRELESS_EQUIPMENT_VENDORS', 'QIN_CHUAN', '成都秦川物联网科技股份有限公司', b'1', '成都秦川物联网科技股份有限公司', '1', '3', '2020-08-21 10:10:34', '3', '2020-08-21 10:10:34');
INSERT INTO `c_common_dictionary_item` VALUES ('1296630890943741952', '1296627384161665024', 'WIRELESS_EQUIPMENT_VENDORS', 'PARTNER', '合作方', b'1', '合作方', '1', '3', '2020-08-21 10:11:09', '3', '2020-08-21 10:11:09');
INSERT INTO `c_common_dictionary_item` VALUES ('1296631518138990592', '1296631159827988480', 'AMOUNT_MARK', 'GAS_METER', '气量表', b'1', '气量表', '1', '3', '2020-08-21 10:13:39', '3', '2020-08-21 10:13:39');
INSERT INTO `c_common_dictionary_item` VALUES ('1296631877091721216', '1296631159827988480', 'AMOUNT_MARK', 'LADDER_GAS_PRICE_AMOUNT_TABLE', '阶梯气价金额表', b'1', '阶梯气价金额表', '1', '3', '2020-08-21 10:15:04', '3', '2020-08-21 10:15:04');
INSERT INTO `c_common_dictionary_item` VALUES ('1296632375458922496', '1296632113117790208', 'REMOTE_SERVICE_FLAG', 'GAS_PURCHASING', '购气', b'1', '购气', '1', '3', '2020-08-21 10:17:03', '3', '2020-08-21 10:17:03');
INSERT INTO `c_common_dictionary_item` VALUES ('1296632526592278528', '1296632113117790208', 'REMOTE_SERVICE_FLAG', 'CLOSE_THE_VALVE', '关阀', b'1', '关阀', '1', '3', '2020-08-21 10:17:39', '3', '2020-08-21 10:17:39');
INSERT INTO `c_common_dictionary_item` VALUES ('1296632668657549312', '1296632113117790208', 'REMOTE_SERVICE_FLAG', 'MODIFY_PRICE', '调价', b'1', '调价', '1', '3', '2020-08-21 10:18:13', '3', '2020-08-21 10:18:13');
INSERT INTO `c_common_dictionary_item` VALUES ('1296632823431561216', '1296632113117790208', 'REMOTE_SERVICE_FLAG', 'OPEN_AN_ACCOUNT', '开户', b'1', '开户', '1', '3', '2020-08-21 10:18:50', '3', '2020-08-21 10:18:50');
INSERT INTO `c_common_dictionary_item` VALUES ('1296632986023755776', '1296632113117790208', 'REMOTE_SERVICE_FLAG', 'OPEN_THE_VALVE', '开户', b'1', '开户', '1', '3', '2020-08-21 10:19:29', '3', '2020-08-21 10:19:29');
INSERT INTO `c_common_dictionary_item` VALUES ('1296633209106202624', '1296632113117790208', 'REMOTE_SERVICE_FLAG', 'REMOTE_METER_READING', '远程抄表', b'1', '远程抄表', '1', '3', '2020-08-21 10:20:22', '3', '2020-08-21 10:20:22');
INSERT INTO `c_common_dictionary_item` VALUES ('1296633394691571712', '1296632113117790208', 'REMOTE_SERVICE_FLAG', 'CHANGE_THE_WATCH', '换表', b'1', '换表', '1', '3', '2020-08-21 10:21:06', '3', '2020-08-21 10:21:06');
INSERT INTO `c_common_dictionary_item` VALUES ('1296633885710352384', '1296633741384351744', 'CARD_TYPE', 'CHARACTER', '字符', b'1', '字符', '1', '3', '2020-08-21 10:23:03', '3', '2020-08-21 10:23:03');
INSERT INTO `c_common_dictionary_item` VALUES ('1296633969218945024', '1296633741384351744', 'CARD_TYPE', 'NUMBER', '数字', b'1', '数字', '1', '3', '2020-08-21 10:23:23', '3', '2020-08-21 10:23:23');
INSERT INTO `c_common_dictionary_item` VALUES ('1296634875075362816', '1296634238786863104', 'MODEL_AND_SPECIFICATION', '1.6', '1.6', b'1', '1.6', '1', '3', '2020-08-21 10:26:59', '3', '2020-08-21 10:26:59');
INSERT INTO `c_common_dictionary_item` VALUES ('1296634923045617664', '1296634238786863104', 'MODEL_AND_SPECIFICATION', '2.5', '2.5', b'1', '2.5', '1', '3', '2020-08-21 10:27:11', '3', '2020-08-21 10:27:11');
INSERT INTO `c_common_dictionary_item` VALUES ('1296634959938715648', '1296634238786863104', 'MODEL_AND_SPECIFICATION', '4', '4', b'1', '4', '1', '3', '2020-08-21 10:27:19', '3', '2020-08-21 10:27:19');
INSERT INTO `c_common_dictionary_item` VALUES ('1296635005119758336', '1296634238786863104', 'MODEL_AND_SPECIFICATION', '6', '6', b'1', '6', '1', '3', '2020-08-21 10:27:30', '3', '2020-08-21 10:27:30');
INSERT INTO `c_common_dictionary_item` VALUES ('1296635069980475392', '1296634238786863104', 'MODEL_AND_SPECIFICATION', '10', '10', b'1', '10', '1', '3', '2020-08-21 10:27:46', '3', '2020-08-21 10:27:46');
INSERT INTO `c_common_dictionary_item` VALUES ('1296635125504671744', '1296634238786863104', 'MODEL_AND_SPECIFICATION', '16', '16', b'1', '16', '1', '3', '2020-08-21 10:27:59', '3', '2020-08-21 10:27:59');
INSERT INTO `c_common_dictionary_item` VALUES ('1296635211781505024', '1296634238786863104', 'MODEL_AND_SPECIFICATION', '25', '25', b'1', '25', '1', '3', '2020-08-21 10:28:19', '3', '2020-08-21 10:28:19');
INSERT INTO `c_common_dictionary_item` VALUES ('1296635238948012032', '1296634238786863104', 'MODEL_AND_SPECIFICATION', '40', '40', b'1', '40', '1', '3', '2020-08-21 10:28:26', '3', '2020-08-21 10:28:26');



drop table if exists gc_install_order_record;
create table gc_install_order_record
(
   id                   bigint(32)     not null  comment 'id',
   company_code         varchar(32)              comment '公司code',
   company_name         varchar(100)             comment '公司名称',
   org_id               bigint(32)               comment '组织id',
   org_name             varchar(100)             comment '组织名称',
   business_hall_id     char(32)                 comment '营业厅id',
   business_hall_name   varchar(100)             comment '营业厅名称',
   install_number       varchar(32)              comment '报装编号',
   work_order_no        varchar(32)              comment '工单编号',
   work_order_type      varchar(32)              comment '工单类型',
   urgency              varchar(20)              comment '紧急程度',
   review_user_id       bigint(32)               comment '审核人id',
   review_user_name     varchar(100)             comment '审核人名称',
   review_time          datetime                 comment '审核时间',
   review_desc          varchar(300)             comment '审核意见',
   receive_user_id      bigint(32)               comment '接单人id',
   receive_user_name    varchar(100)             comment '接单人名称',
   receive_user_mobile  varchar(20)              comment '联系电话',
   work_order_desc      varchar(1000)            comment '工单内容',
   reject_desc          varchar(300)             comment '驳回原因',
   stop_desc            varchar(300)             comment '终止原因',
   end_job_user_id      bigint(32)               comment '结单人id',
   end_job_user_name    varchar(100)             comment '结单人名称',
   end_job_time         datetime                 comment '结单时间',
   remark               varchar(200)             comment '备注',
   status               smallint                 comment '工单状态',
   create_user          bigint(32)               comment '创建用户id',
   create_time          datetime                 comment '创建时间',
   update_user          bigint(32)               comment '更新用户id',
   update_time          datetime                 comment '更新时间',
   primary key (id)
)engine=innodb default charset=utf8mb4 comment='报装工单记录';



drop table if exists gc_install_record;
create table gc_install_record
(
   id                   bigint(32)          not null           comment 'id',
   company_code         varchar(32)                            comment '公司code',
   company_name         varchar(100)                           comment '公司名称',
   org_id               bigint(32)                             comment '组织id',
   org_name             varchar(100)                           comment '组织名称',
   business_hall_id     char(32)                               comment '营业厅id',
   business_hall_name   varchar(100)                           comment '营业厅名称',
   install_number       varchar(30)                            comment '报装编号',
   install_type_id      char(32)                               comment '报装类型id：商福报装 工业报装 零散报装 集中报装',
   install_type_name    varchar(30)                            comment '报装类型名称',
   city_id              bigint                                 comment '市id',
   city_name            char(32)                               comment '市',
   area_id              bigint                                 comment '区县id',
   area_name            char(32)                               comment '区县id',
   street_id            bigint                                 comment '街道（乡镇）id',
   street_name          char(32)                               comment '街道（乡镇）',
   community_id         bigint                                 comment '小区（村）id',
   community_name       char(32)                               comment '小区（村）',
   storied_id           bigint                                 comment '楼栋（组）',
   storied_name         char(32)                               comment '楼栋（组）',
   floor_num            int                                    comment '楼层',
   population           int                                    comment '户数',
   address_remark       varchar(1000)                          comment '详细地址备注',
   customer_name        varchar(100)                           comment '报装人',
   telphone             varchar(11)                            comment '联系电话',
   id_type_id           char(32)                               comment '证件类型id：证件类型 身份证 营业执照',
   id_type_name         varchar(50)                            comment '证件名称',
   id_number            varchar(30)                            comment '证件号码',
   accept_time          datetime                               comment '受理时间',
   charge_status        smallint                               comment '收费状态',
   money                numeric(18,4)                          comment '收费金额',
   process_status       varchar(30)                            comment '流程状态',
   step_on_user_id      varchar(1000)                          comment '踏勘人id',
   step_on_user_name    varchar(1000)                          comment '踏勘人名称',
   step_on_time         datetime                               comment '踏勘时间',
   install_user_id      varchar(1000)                          comment '施工人id',
   install_user_name    varchar(1000)                          comment '施工人名称',
   install_time         datetime                               comment '施工时间',
   remark               varchar(200)                           comment '备注',
   review_user_id       char(32)                               comment '审核人id',
   review_user_name     varchar(100)                           comment '审核人名称',
   review_time          datetime                               comment '审核时间',
   review_objection     varchar(100)                           comment '审核意见',
   end_job_user_id      char(32)                               comment '结单人id',
   end_job_user_name    varchar(100)                           comment '结单人名称',
   end_job_time         datetime                               comment '结单时间',
   data_status          smallint                               comment '报装状态',
   create_user          bigint(32)                             comment '创建用户id',
   create_time          datetime                               comment '创建时间',
   update_user          bigint(32)                             comment '更新用户id',
   update_time          datetime                               comment '更新时间',
   primary key (id)
)engine=innodb default charset=utf8mb4 comment='报装受理记录';



drop table if exists gc_install_detail;

create table gc_install_detail
(
   id                      bigint(32)            not null        comment 'id',
   company_code            varchar(32)                           comment '公司code',
   company_name            varchar(100)                          comment '公司名称',
   org_id                  bigint(32)                            comment '组织id',
   org_name                varchar(100)                          comment '组织名称',
   business_hall_id        char(32)                              comment '营业厅id',
   business_hall_name      varchar(100)                          comment '营业厅名称',
   install_number          char(32)                              comment '报装编号',
   address_detail          char(32)                              comment '详细地址',
   gas_meter_factory_id    varchar(32)                           comment '厂家id',
   gas_meter_factory_name  varchar(32)                           comment '厂家名称',
   gas_meter_id            char(32)                              comment '气表id',
   gas_meter_name          char(32)                              comment '气表名称',
   create_user             bigint(32)                            comment '创建用户id',
   create_time             datetime                              comment '创建时间',
   update_user             bigint(32)                            comment '更新用户id',
   update_time             datetime                              comment '更新时间',
   primary key (id)
)engine=innodb default charset=utf8mb4 comment='安装明细';



drop table if exists gc_gas_install_file;
create table gc_gas_install_file
(
   id                      bigint(32)            not null           comment 'id',
   install_number          char(32)                                 comment '报装编号',
   material_type           varchar(10)                              comment '材料类型 id：身份证件 contract：合同 other:其他 ',
   material_url            varchar(1000)                            comment '材料地址',
   material_image_url      varchar(1000)                            comment '缩略图地址 ',
   material_file_name      varchar(200)                             comment '原文件名 ',
   material_file_extension varchar(10)                              comment '原文件扩展名',
   status                  smallint                              	comment '状态',
   create_user             bigint(32)                               comment '创建用户id',
   create_time             datetime                                 comment '创建时间',
   update_user             bigint(32)                               comment '更新用户id',
   update_time             datetime                                 comment '更新时间',
   primary key (id)
)engine=innodb default charset=utf8mb4 comment='报装材料';

drop table if exists pz_function_switch;

/*==============================================================*/
/* Table: PZ_FUNCTION_SWITCH                功能项配置           */
/*==============================================================*/
create table pz_function_switch
(
   id                   bigint(32) not null comment 'ID',
   company_code         varchar(32) comment '公司CODE',
   company_name         varchar(100) comment '公司名称',
   org_id               bigint(32) comment '组织ID',
   org_name             varchar(100) comment '组织名称',
   open_id_card         smallint comment '启用ID卡  启用1，禁用0',
   open_customer_prefix smallint comment '自编客户编号前缀',
   customer_prefix      varchar(100) comment '客户编号前缀',
   settlement_date      datetime comment '结算日期',
   open_vat_invoice     smallint comment '启用增值税发票',
   tax_rate             decimal(5,2) comment '税率',
    rounding            smallint COMMENT '取整方式',
   open_black_list      smallint comment '启用黑名单限购',
   black_max_volume     numeric(18,2) comment '黑名单限购最大充值气量',
   black_max__money     numeric(18,2) comment '黑名单限购最大充值金额',
   open_check_limit     smallint comment '启动安检限购',
   check_max_volume     numeric(18,2) comment '安检限购最大充值气量',
   check_max_money      numeric(18,2) comment '安检限购最大充值金额',
   remark               varchar(300) comment '备注',
   create_user          bigint(32) comment '创建人ID',
   create_time          datetime comment '创建时间',
   update_user          bigint(32) comment '修改人ID',
   update_time          datetime comment '修改时间',
   primary key (id)
)comment='功能项配置';


