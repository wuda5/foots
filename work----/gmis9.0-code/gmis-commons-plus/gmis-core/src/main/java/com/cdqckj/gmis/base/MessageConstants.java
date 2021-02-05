package com.cdqckj.gmis.base;

import lombok.experimental.Accessors;

@Accessors(chain = true)
public class MessageConstants {
    /**调用系统服务异常**/
    public static final String SYS_SERVICE_FAIL = "SYS_SERVICE_FAIL";
    /**日期参数只能介于0-365天之间*/
    public static String DATE_LIMIT = "DATE_LIMIT";
    /**参数验证异常*/
    public static String PARAM_ERROR = "PARAM_ERROR";
    /**密码与确认密码不一致*/
    public static String USER_VERIFY_PASSWORD = "USER_VERIFY_PASSWORD";
    /**用户不存在*/
    public static String USER_VERIFY_NAME = "USER_VERIFY_NAME";
    /**旧密码错误*/
    public static String USER_VERIFY_OLD_PASSWORD = "USER_VERIFY_OLD_PASSWORD";
    /**2次输入的密码不一致*/
    public static String USER_VERIFY_UPDATE_PASSWORD = "USER_VERIFY_UPDATE_PASSWORD";
    /**账号已经存在*/
    public static String USER_VERIFY_NAME_EXIST = "USER_VERIFY_NAME_EXIST";
    /**编码重复，请重新输入*/
    public static String CODE_REPETITION = "CODE_REPETITION";
    /**您配置的租户模式:{}不可用*/
    public static String TENANT_VERIFY_MODE = "TENANT_VERIFY_MODE";
    /**父组织不能为空*/
    public static String ORG_VERIFY_PARENT = "ORG_VERIFY_PARENT";
    /**请输入用户名或密码*/
    public static String USER_VERIFY_PASSWORD_INPUT = "USER_VERIFY_PASSWORD_INPUT";
    /**企业不存在*/
    public static String TENANT_VERIFY_EXIST = "TENANT_VERIFY_EXIST";
    /**企业不可用~*/
    public static String TENANT_VERIFY_NOT_ENABLE = "TENANT_VERIFY_NOT_ENABLE";
    /**企业服务已到期*/
    public static String TENANT_VERIFY_EXPIRED = "TENANT_VERIFY_EXPIRED";
    /**grantType 不支持，请传递正确的 grantType 参数*/
    public static String GRENT_VERIFY_TYPE = "GRENT_VERIFY_TYPE";
    /**请填写正确的客户端ID或者客户端秘钥*/
    public static String LOGIN_VERIFY_CID = "LOGIN_VERIFY_CID";
    /**客户端[%s]已被禁用*/
    public static String LOGIN_VERIFY_CUS_NOT_ENABLE = "LOGIN_VERIFY_CUS_NOT_ENABLE";
    /**用户名或密码错误!*/
    public static String LOGIN_VERIFY_NAME_PASSWORD = "LOGIN_VERIFY_NAME_PASSWORD";
    /**用户密码已过期，请修改密码或者联系管理员重置!*/
    public static String LOGIN_VERIFY_PASSWORD_EXPIRED = "LOGIN_VERIFY_PASSWORD_EXPIRED";
    /**用户被禁用，请联系管理员！*/
    public static String LOGIN_VERIFY_USER_NOT_ENABLE = "LOGIN_VERIFY_USER_NOT_ENABLE";
    /**用户登录次数已经达到上线*/
    public static String LOGIN_VERIFY_USER_LOCK = "LOGIN_VERIFY_USER_LOCK";
    /** 短信任务未保存成功*/
    public static String SMS_JOB_NOT_SAVE = "SMS_JOB_NOT_SAVE";
    /** 短信模板为空*/
    public static String SMS_MODE_IS_NULL = "SMS_MODE_IS_NULL";
    /** 短信供应商不存在*/
    public static String SMS_SUPPLIER_NOT_EXIST = "SMS_SUPPLIER_NOT_EXIST";
    /** 复制文件异常*/
    public static String COPY_FILE_ERROR = "COPY_FILE_ERROR";
    /** 复制文件异常*/
    public static String CREATE_FILE_FAILED = "CREATE_FILE_FAILED";
    /** 文件转换失败*/
    public static String CONVERSION_FILE_FAILED = "CONVERSION_FILE_FAILED";
    /** 文件输出失败*/
    public static String OUT_FILE_FAILED = "OUT_FILE_FAILED";
    /** 文件地址连接超时*/
    public static String FILE_URL_CONNECT_TIMEOUT = "FILE_URL_CONNECT_TIMEOUT";
    /** 没有逻辑删除字段 */
    public static String MISSING_FIELD_DELETESTATUS = "MISSING_FIELD_DELETESTATUS";
    /** 文件不存在 */
    public static String FILE_VERIFY_EXIST = "FILE_VERIFY_EXIST";
    /** 查询失败 */
    public static String SELECT_FAIL = "SELECT_FAIL";
    /** 文件解析错误 */
    public static String FILE_PARSING_ERROR = "FILE_PARSING_ERROR";
    /** 工单状态操作错误 */
    public static String WORK_ORDER_ERROR ="WORK_ORDER_ERROR";
    /** 任务状态操作错误 */
    public static String TASK_ORDER_ERROR ="TASK_ORDER_ERROR";
    /** 操作数据编辑错误 */
    public static String OPER_EDIT_ERROR  ="OPER_EDIT_ERROR";
    /** 阶梯气量不正确 */
    public static String PRICE_GAS_ERROR  ="PRICE_GAS_ERROR";
    /** 终止异常 */
    public static String TERMINATION_EXCEPTION  = "TERMINATION_EXCEPTION";
    /** 撤回异常 */
    public static String REVOKE_EXCEPTION  = "REVOKE_EXCEPTION";
    /** 客户表具已关联异常 */
    public static String CUSTOMER_METER_RELATION_EXCEPTION  = "customer.meter.relation.exception";
    /** 开户异常 */
    public static String ESTABLISH_ACCOUNT_EXCEPTION  = "ESTABLISH_ACCOUNT_EXCEPTION";
    /** 限购配置方案名称已村子*/
    public static String GAS_LIMIT_SET  = "GAS_LIMIT_SET";
    /** 限购方案时间段内已存在*/
    public static String GAS_TIME_EXIST = "GAS_TIME_EXIST";
    /**导入开户模板异常**/
    public static String EXPORT_ESTABLISH_ACCOUNT_TEMPLATE_EXCEPTION = "export.establish.account.template.exception";
    /**只能创建一个总公司**/
    public static String ORG_CREATE_ONLY = "ORG_CREATE_ONLY";
    /**组织名称已存在**/
    public static String ORG_CREATE_NAME = "ORG_CREATE_NAME";
    /**燃气方案不能为空**/
    public static String USE_GAS_NOT_EMPTY = "USE_GAS_NOT_EMPTY";
    /**滞纳金方案已存在**/
    public static String PENLTY_SCHEME_EXIST = "PENLTY_SCHEME_EXIST";
    /**身份证号码重复**/
    public static String REPEAT_ID_NUMBER = "REPEAT_ID_NUMBER";
    /**表身号重复**/
    public static String REPEAT_GAS_METER_NUMBER = "REPEAT_GAS_METER_NUMBER";
    /**设置黑名单失败**/
    public static String FAILED_TO_SET_BLACKLIST = "FAILED_TO_SET_BLACKLIST";
    /**基于票据打印发票异常**/
    public static String PRINT_INVOICE_BASED_ON_RECEIPT_FAIL = "PRINT_INVOICE_BASED_ON_RECEIPT_FAIL";
    /**街道去重**/
    public static String ADDRESS_STREET_REPEAT = "ADDRESS_STREET_REPEAT";
    /**小区去重**/
    public static String ADDRESS_COMMUNITY_REPEAT = "ADDRESS_COMMUNITY_REPEAT";
    /**收费项名称重复**/
    public static String REPEAT_TOLLTEM_NAME = "REPEAT_TOLLTEM_NAME";
    /**气表厂家名称重复**/
    public static String REPEAT_GAS_FACTORY_INFO = "gas.meter.factory.repeat";
    /**气表版号名称重复**/
    public static String REPEAT_GAS_VERSION_NAME = "REPEAT_GAS_VERSION_NAME";
    /**气表型号名称重复**/
    public static String REPEAT_GAS_MODEL_NAME = "REPEAT_GAS_MODEL_NAME";
    /**请导入数据**/
    public static String IMPORT_DATA = "IMPORT_DATA";
    /**详细地址**/
    public static String DETAIL_ADDRESS_REPEAT = "DETAIL_ADDRESS_REPEAT";
    /**存在冗余扎帐信息数据**/
    public static String REDUNDANT_SUMMARY_BILL_DATA = "REDUNDANT_SUMMARY_BILL_DATA";
    /**请输入正确的结束时间**/
    public static String INVALID_ENG_TIME = "INVALID_ENG_TIME";
    /**请输入正确的最大流量或最小流量**/
    public static String INVALID_FLOW = "INVALID_FLOW";

    /**当前组织下有用户不能删除**/
    /**无效或未被启用的版号**/
    public static String INVALID_GAS_METER_VERSION = "INVALID_GAS_METER_VERSION";
    /**无效或未被启用的厂家**/
    public static String INVALID_GAS_METER_FACTORY = "INVALID_GAS_METER_FACTORY";

    public static String CURRENT_ORGANIZATION_HAVE_USER = "CURRENT_ORGANIZATION_HAVE_USER";
    /**当前岗位下有用户不能删除**/
    public static String CURRENT_STATION_HAVE_USER = "CURRENT_STATION_HAVE_USER";
    /**appid不能为空**/
    public static String APPID_NOT_EMPTY = "APPID_NOT_EMPTY";
    /**appSECRT不能为空**/
    public static String APPSECRT_NOT_EMPTY = "APPSECRT_NOT_EMPTY";
    /**选择场景编码重复**/
    public static String SCENE_CODE = "SCENE_CODE";
    /**用气类型重复**/
    public static String USE_GAS_TYPE_REPEAT = "USE_GAS_TYPE_REPEAT";


    /**
     * 表具不存在
     */
    public static final String G_METER_NOT="global.meter.not";
    /**
     * 表具不可用
     */
    public static final String G_METER_NOTA="global.meter.notavailable";
    /**
     * 表具信息不完善
     */
    public static final String G_METER_UNCOMPLETEINFO="global.meter.uncompleteinfo";
    /**
     * 未开户
     */
    public static final String G_ACCOUNT_UNOPEN="global.account.unopen";
    /**
     * 账户余额不足
     */
    public static final String G_ACCOUNT_INA="global.account.inadequate";
    /**
     * 保存数据失败
     */
    public static final String G_SAVE_ERROR="global.save.error";
    /**
     * 修改数据失败
     */
    public static final String G_UPDATE_ERROR="global.update.error";
    /**
     * 删除数据失败
     */
    public static final String G_DELETE_ERROR="global.delete.error";
    /**
     * 操作金额受限
     */
    public static final String G_LIMIT_OPER_MONEY="global.oper.money.limit";
    /**
     * 操作气量受限
     */
    public static final String G_LIMIT_OPER_GAS="global.oper.gas.limit";
    /**
     * 操作余额不足
     */
    public static final String G_LIMIT_OPER_INA=" global.oper.money.inadequate";
    /**
     * 参数异常
     */
    public static final String G_ERROR_PARAM="global.err.param";

    /**
     * 服务接口异常
     */
    public static final String G_ERROR_SERVICE="global.err.service";

    /**
     * 计算错误
     */
    public static final String G_ERROR_CALCUL="global.err.calcul";
    /**
     * 错误请求
     */
    public static final String G_ERROR_UNKNOWN="global.err.unknown";

    /**
     * 导入重复
     */
    public static final String G_REPEAT_NOOPER="global.repeat.nooper";

    /**
     * 新增修改校验重复
     */
    public static final String G_REPEAT_EXITS="global.repeat.nameexits";




    /**
     *标注的属性值长度，有最大最小长度
     */
    public static final String SYS_VALID_LENGTH="SYS_VALID_LENGTH";

    /**
     *标注的属性值必须为空
     */
    public static final String SYS_VALID_NULL="SYS_VALID_NULL";
    /**
     *标注的属性值不能为空
     */
    public static final String SYS_VALID_NOTNULL="SYS_VALID_NOTNULL";
    /**
     *标注的属性值必须为TRUE
     */
    public static final String SYS_VALID_ASSERTTRUE="SYS_VALID_ASSERTTRUE";
    /**
     *标注的属性值必须为FALSE
     */
    public static final String SYS_VALID_ASSERTFALSE="SYS_VALID_ASSERTFALSE";
    /**
     *标注的属性值不能小于MIN中指定的值
     */
    public static final String SYS_VALID_MIN="SYS_VALID_MIN";
    /**
     *标注的属性值不能大于MAX中指定的值
     */
    public static final String SYS_VALID_MAX="SYS_VALID_MAX";
    /**
     *小数值，同上
     */
    public static final String SYS_VALID_DECIMALMIN="SYS_VALID_DECIMALMIN";
    /**
     *小数值，同上
     */
    public static final String SYS_VALID_DECIMALMAX="SYS_VALID_DECIMALMAX";
    /**
     *负数
     */
    public static final String SYS_VALID_NEGATIVE="SYS_VALID_NEGATIVE";
    /**
     *0或者负数
     */
    public static final String SYS_VALID_NEGATIVEORZERO="SYS_VALID_NEGATIVEORZERO";
    /**
     *整数
     */
    public static final String SYS_VALID_POSITIVE="SYS_VALID_POSITIVE";
    /**
     *0或者整数
     */
    public static final String SYS_VALID_POSITIVEORZERO="SYS_VALID_POSITIVEORZERO";
    /**
     *指定字符串长度，注意是长度，有两个值，MIN以及MAX，用于指定最小以及最大长度
     */
    public static final String SYS_VALID_SIZE="SYS_VALID_SIZE";
    /**
     *内容必须是数字
     */
    public static final String SYS_VALID_DIGITS="SYS_VALID_DIGITS";
    /**
     *时间必须是过去的时间
     */
    public static final String SYS_VALID_PAST="SYS_VALID_PAST";
    /**
     *过去或者现在的时间
     */
    public static final String SYS_VALID_PASTORPRESENT="SYS_VALID_PASTORPRESENT";
    /**
     *将来的时间
     */
    public static final String SYS_VALID_FUTURE="SYS_VALID_FUTURE";
    /**
     *将来或者现在的时间
     */
    public static final String SYS_VALID_FUTUREORPRESENT="SYS_VALID_FUTUREORPRESENT";
    /**
     *用于指定一个正则表达式
     */
    public static final String SYS_VALID_PATTERN="SYS_VALID_PATTERN";
    /**
     *字符串内容非空
     */
    public static final String SYS_VALID_NOTEMPTY="SYS_VALID_NOTEMPTY";
    /**
     *字符串内容非空且长度大于0
     */
    public static final String SYS_VALID_NOTBLANK="SYS_VALID_NOTBLANK";
    /**
     *邮箱
     */
    public static final String SYS_VALID_EMAIL="SYS_VALID_EMAIL";
    /**
     *用于指定数字，注意是数字的范围，有两个值，MIN以及MAX
     */
    public static final String SYS_VALID_RANGE="SYS_VALID_RANGE";

    /**
     * 租户已过时效 hc
     */
    public static final String SYS_VALID_TENANT_TIME = "SYS_VALID_TENANT_TIME";

    /**
     * 租户不可用 hc
     */
    public static final String SYS_VALID_TENANT_USE = "SYS_VALID_TENANT_USE";

    /**
     * 当前应用不存在 hc
     */
    public static final String SYS_VALID_APP_NOT = "SYS_VALID_APP_NOT";

    /**
     * 应用未生效 hc
     */
    public static final String SYS_VALID_APP_EFFECTIVE = "SYS_VALID_APP_EFFECTIVE";
    /**
     * 应用已失效 hc
     */
    public static final String SYS_VALID_APP_FAILURE = "SYS_VALID_APP_FAILURE";
    /**只有物联网燃气表才能预开户**/
    public static final String ONLY_IOT_GAS_METER_ESTABLISH_ACCOUNT = "only.iot.gas.meter.establish.account ";
    /**入库状态的表具才能预开户**/
    public static final String OPEN_ACCOUNT_IN_ADVANCE_MUST_WAREHOUSING = "open.account.in.advance.must.be.in.warehousing";
    /**预开户失败**/
    public static final String ACCOUNT_OPENING_IN_ADVANCE_FAILED = "account.opening.in.advance.failed";
    /**证件号码不存在**/
    public static final String CARD_NUMBER_CANNOT_BE_EMPTY = "card.number.cannot.be.empty";
    /**表身号不存在**/
    public static final String GAS_METER_BODY_NUMBER_CANNOT_BE_EMPTY = "gas.meter.body.number.cannot.be.empty";
    /**气表厂家编号不存在**/
    public static final String GAS_METER_FACTORY_CODE_CANNOT_BE_EMPTY = "gas.meter.factory.code.cannot.be.empty";
    /**没有厂家信息**/
    public static final String NO_MANUFACTURER_INFORMATION = "no.manufacturer.information";
    /**气表厂家名称错误**/
    public static final String GAS_METER_FACTORY_NAME_IS_WRONG = "gas.meter.factory.name.is.wrong";
    /**没有气表版号信息**/
    public static final String NO_GAS_METER_VERSION_INFORMATION = "no.gas.meter.version.information";
    /**气表版号名称错误**/
    public static final String GAS_METER_VERSION_TYPE_NAME_IS_WRONG = "gas.meter.version.type.name.is.wrong";

    /**赠送减免活动过期**/
    public static final String EXPIER = "expier";
    public static final String CUSTOMER_CODE_NOTEXIST = "customer.code.notexist";
    public static final String CUSTOMER_CODE_EMPTY = "customer.code.empty";
    public static final String CUSTMOER_NAME_NOT_EMPTY = "custmoer.name.not.empty";
    public static final String IDCARD_TYPE_CANNOT_EMPTY = "idcard.type.cannot.empty";
    public static final String IDCARD_NO_CANNOT_EMPTY = "idcard.no.cannot.empty";
    public static final String PHONE_WRONG = "phone.wrong";
    public static final String PHONE_CANNOT_EMPTY = "phone.cannot.empty";
    public static final String GAS_METER_CODE_NOTEXIST = "gas.meter.code.notexist";
    public static final String GAS_METER_CODE_NOT_EMPTY = "gas.meter.code.not.empty";
    public static final String PROVINCE_NAME_CANNOT_EMPTY = "province.name.cannot.empty";
    public static final String CITY_NAME_CANNOT_EMPTY = "city.name.cannot.empty";
    public static final String COUNTY_NAME_CANNOT_EMPTY = "county.name.cannot.empty";
    public static final String STREET_NAME_CANNOT_EMPTY = "street.name.cannot.empty";
    public static final String COMMUNITY_NAME_CANNOT_EMPTY = "community.name.cannot.empty";
    public static final String USE_GAS_TYPE_NAME_CANNOT_EMPTY = "use.gas.type.name.cannot.empty";
    public static final String VALID_SUC_SET_WRONG_MSG = "valid.suc.set.wrong.msg";
    public static final String CUSTOMER_METER_RELATED = "customer.meter.related";


    /**认证票据生成失败 add by hc **/
    public static final String TICKET_BUILD_FAIL = "TICKET_BUILD_FAIL";
    /**认证票据以失效 hc **/
    public static final String TICKET_PAPER_FAILURE = "TICKET_PAPER_FAILURE";
    /** 认证密匙错误 hc **/
    public static final String OAUTH_SECRET_FAIL = "OAUTH_SECRET_FAIL";
    /** 认证token生成失败 hc **/
    public static final String OAUTH_TOKEN_FAIL = "OAUTH_TOKEN_FAIL";
    /** 认证失败：未注册于该燃气公司**/
    public static final String OAUTH_REGISTER_NOT = "OAUTH_REGISTER_NOT";
    /** 验证码错误 **/
    public static final String OAUTH_VERIFY_CODE_FAIL = "OAUTH_VERIFY_CODE_FAIL";
    /** appId不能为空 **/
    public static final String APP_ID_NOT_NULL = "APP_ID_NOT_NULL";
    /** appSecret不能为空 **/
    public static final String APP_SECRET_NOT_NULL = "APP_SECRET_NOT_NULL";
    /** 租户编码不能为空 **/
    public static final String TENANT_CODE_NOT_NULL = "TENANT_CODE_NOT_NULL";

    /** 根据父类ID查询地区信息发生异常 **/
    public static final String QUERY_REGION_BY_PARENTID_THROW_EXCEPTION  = "QUERY_REGION_BY_PARENTID_THROW_EXCEPTION";
    /** 查询地区信息发生异常 **/
    public static final String QUERY_REGION_THROW_EXCEPTION  = "QUERY_REGION_THROW_EXCEPTION";
    /** 查询省份信息异常 **/
    public static final String QUERY_PROVINCE_THROW_EXCEPTION  = "QUERY_PROVINCE_THROW_EXCEPTION";
    /** 查询市信息异常 **/
    public static final String QUERY_CITY_THROW_EXCEPTION  = "QUERY_CITY_THROW_EXCEPTION";
    /** 查询区信息异常 **/
    public static final String QUERY_COUNTY_THROW_EXCEPTION  = "QUERY_COUNTY_THROW_EXCEPTION";
    /** 查询街道发生异常 **/
    public static final String QUERY_STREET_THROW_EXCEPTION  = "QUERY_STREET_THROW_EXCEPTION";
    /** 查询小区发生异常 **/
    public static final String QUERY_COMMUNITY_THROW_EXCEPTION  = "QUERY_COMMUNITY_THROW_EXCEPTION";
    /*** 查询证件类型异常 **/
    public static final String QUERY_ID_TYPE_THROW_EXCEPTION  = "QUERY_ID_TYPE_THROW_EXCEPTION";
    /*** 查询用气类型异常 **/
    public static final String QUERY_USE_GAS_TYPE_THROW_EXCEPTION  = "QUERY_USE_GAS_TYPE_THROW_EXCEPTION";
    /*** 下载导入开户失败文件异常 **/
    public static final String DOWDLOAD_ESTABLISH_ACCOUNT_ERROR_FILE_FAILED  = "DOWDLOAD_ESTABLISH_ACCOUNT_ERROR_FILE_FAILED";
    /*** 判断账户是否存在异常 **/
    public static final String JUDGING_WHETHER_THE_ACCOUNT_EXISTS_EXCEPTION  = "JUDGING_WHETHER_THE_ACCOUNT_EXISTS_EXCEPTION";
    /*** 查询表具信息异常 **/
    public static final String QUERY_GAS_METER_THROW_EXCEPTION  = "QUERY_GAS_METER_THROW_EXCEPTION";
    /*** 查询客户信息异常 **/
    public static final String QUERY_CUSTOMER_THROW_EXCEPTION  = "QUERY_CUSTOMER_THROW_EXCEPTION";
    /*** 符合条件数据大于一条 **/
    public static final String MORE_THAN_ONE_QUALIFIED_DATA  = "MORE_THAN_ONE_QUALIFIED_DATA";
    /*** 没有符合条件的数据 **/
    public static final String THERE_IS_NO_ELIGIBLE_DATA  = "THERE_IS_NO_ELIGIBLE_DATA";
    /*** 没有导入开户任务信息 **/
    public static final String NO_IMPORT_ESTABLISH_ACCOUNT_TASK_INFO = "NO_IMPORT_ESTABLISH_ACCOUNT_TASK_INFO";

    /*** 待通气状态的气表才能通气 **/
    public static final String ONLY_WHEN_GAS_METER_IN_VENTILATION_CAN_VENTILATE = "ONLY_WHEN_GAS_METER_IN_VENTILATION_CAN_VENTILATE";
    public static final String FACTORY_NAME_CANNOT_EMPTY = "FACTORY_NAME_CANNOT_EMPTY";
    public static final String VERSION_NUM_CANNOT_EMPTY = "VERSION_NUM_CANNOT_EMPTY";
    public static final String MODEL_NUM_CANNOT_EMPTY = "MODEL_NUM_CANNOT_EMPTY";
    public static final String METER_BODY_NUM_CANNOT_EMPTY = "METER_BODY_NUM_CANNOT_EMPTY";




}
