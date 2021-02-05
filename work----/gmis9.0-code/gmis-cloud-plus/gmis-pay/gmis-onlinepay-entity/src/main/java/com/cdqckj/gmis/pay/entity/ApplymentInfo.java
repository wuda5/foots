package com.cdqckj.gmis.pay.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 特约商户进件申请信息
 * </p>
 *
 * @author gmis
 * @since 2020-11-17
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_applyment_info")
@ApiModel(value = "ApplymentInfo", description = "特约商户进件申请信息")
@AllArgsConstructor
public class ApplymentInfo extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 业务申请编号(系统生成)
     */
    @ApiModelProperty(value = "业务申请编号(系统生成)")
    @NotEmpty(message = "业务申请编号(系统生成)不能为空")
    @Length(max = 32, message = "业务申请编号(系统生成)长度不能超过32")
    @TableField(value = "business_code", condition = LIKE)
    @Excel(name = "业务申请编号(系统生成)")
    private String businessCode;

    /**
     * 超级管理员姓名
     */
    @ApiModelProperty(value = "超级管理员姓名")
    @NotEmpty(message = "超级管理员姓名不能为空")
    @Length(max = 20, message = "超级管理员姓名长度不能超过20")
    @TableField(value = "contact_name", condition = LIKE)
    @Excel(name = "超级管理员姓名")
    private String contactName;

    /**
     *  超级管理员身份证件号码
     */
    @ApiModelProperty(value = " 超级管理员身份证件号码")
    @Length(max = 20, message = " 超级管理员身份证件号码长度不能超过20")
    @TableField(value = "contact_id_number", condition = LIKE)
    @Excel(name = " 超级管理员身份证件号码")
    private String contactIdNumber;

    /**
     *  超级管理员微信openid
     */
    @ApiModelProperty(value = " 超级管理员微信openid")
    @Length(max = 32, message = " 超级管理员微信openid长度不能超过32")
    @TableField(value = "openid", condition = LIKE)
    @Excel(name = " 超级管理员微信openid")
    private String openid;

    /**
     *  联系手机
     */
    @ApiModelProperty(value = " 联系手机")
    @NotEmpty(message = " 联系手机不能为空")
    @Length(max = 11, message = " 联系手机长度不能超过11")
    @TableField(value = "mobile_phone", condition = LIKE)
    @Excel(name = " 联系手机")
    private String mobilePhone;

    /**
     *  联系邮箱
     */
    @ApiModelProperty(value = " 联系邮箱")
    @NotEmpty(message = " 联系邮箱不能为空")
    @Length(max = 20, message = " 联系邮箱长度不能超过20")
    @TableField(value = "contact_email", condition = LIKE)
    @Excel(name = " 联系邮箱")
    private String contactEmail;

    /**
     *  SUBJECT_TYPE_ENTERPRISE（企业）
     */
    @ApiModelProperty(value = " SUBJECT_TYPE_ENTERPRISE（企业）")
    @Length(max = 32, message = " SUBJECT_TYPE_ENTERPRISE（企业）长度不能超过32")
    @TableField(value = "subject_type", condition = LIKE)
    @Excel(name = " SUBJECT_TYPE_ENTERPRISE（企业）")
    private String subjectType;

    /**
     *  营业执照照片MediaID
     */
    @ApiModelProperty(value = " 营业执照照片MediaID")
    @NotEmpty(message = " 营业执照照片MediaID不能为空")
    @Length(max = 255, message = " 营业执照照片MediaID长度不能超过255")
    @TableField(value = "license_copy", condition = LIKE)
    @Excel(name = " 营业执照照片MediaID")
    private String licenseCopy;

    /**
     *  营业执照照片原始文件id
     */
    @ApiModelProperty(value = " 营业执照照片原始文件id")
    @NotEmpty(message = " 营业执照照片原始文件id不能为空")
    @Length(max = 255, message = " 营业执照照片原始文件id长度不能超过255")
    @TableField(value = "license_copy_id", condition = LIKE)
    @Excel(name = " 营业执照照片原始文件id")
    private String licenseCopyId;

    /**
     *  营业执照照片原始文件url
     */
    @ApiModelProperty(value = " 营业执照照片原始文件url")
    @NotEmpty(message = " 营业执照照片原始文件url不能为空")
    @Length(max = 200, message = " 营业执照照片原始文件url长度不能超过200")
    @TableField(value = "license_copy_url", condition = LIKE)
    @Excel(name = " 营业执照照片原始文件url")
    private String licenseCopyUrl;

    /**
     *  注册号/统一社会信用代码
     */
    @ApiModelProperty(value = " 注册号/统一社会信用代码")
    @NotEmpty(message = " 注册号/统一社会信用代码不能为空")
    @Length(max = 32, message = " 注册号/统一社会信用代码长度不能超过32")
    @TableField(value = "license_number", condition = LIKE)
    @Excel(name = " 注册号/统一社会信用代码")
    private String licenseNumber;

    /**
     *  商户名称
     */
    @ApiModelProperty(value = " 商户名称")
    @NotEmpty(message = " 商户名称不能为空")
    @Length(max = 128, message = " 商户名称长度不能超过128")
    @TableField(value = "merchant_name", condition = LIKE)
    @Excel(name = " 商户名称")
    private String merchantName;

    /**
     *  个体户经营者/法人姓名
     */
    @ApiModelProperty(value = " 个体户经营者/法人姓名")
    @NotEmpty(message = " 个体户经营者/法人姓名不能为空")
    @Length(max = 64, message = " 个体户经营者/法人姓名长度不能超过64")
    @TableField(value = "legal_person", condition = LIKE)
    @Excel(name = " 个体户经营者/法人姓名")
    private String legalPerson;

    /**
     *  证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)
     */
    @ApiModelProperty(value = " 证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)")
    @Length(max = 42, message = " 证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)长度不能超过42")
    @TableField(value = "id_doc_type", condition = LIKE)
    @Excel(name = " 证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)")
    private String idDocType;

    /**
     *  身份证人像面照片MediaID
     */
    @ApiModelProperty(value = " 身份证人像面照片MediaID")
    @NotEmpty(message = " 身份证人像面照片MediaID不能为空")
    @Length(max = 256, message = " 身份证人像面照片MediaID长度不能超过256")
    @TableField(value = "id_card_copy", condition = LIKE)
    @Excel(name = " 身份证人像面照片MediaID")
    private String idCardCopy;

    /**
     *  身份证人像面照片原始文件id
     */
    @ApiModelProperty(value = " 身份证人像面照片原始文件id")
    @NotEmpty(message = " 身份证人像面照片原始文件id不能为空")
    @Length(max = 32, message = " 身份证人像面照片原始文件id长度不能超过32")
    @TableField(value = "id_card_copy_id", condition = LIKE)
    @Excel(name = " 身份证人像面照片原始文件id")
    private String idCardCopyId;

    /**
     *  身份证人像面照片url
     */
    @ApiModelProperty(value = " 身份证人像面照片url")
    @NotEmpty(message = " 身份证人像面照片url不能为空")
    @Length(max = 200, message = " 身份证人像面照片url长度不能超过200")
    @TableField(value = "id_card_copy_url", condition = LIKE)
    @Excel(name = " 身份证人像面照片url")
    private String idCardCopyUrl;

    /**
     *  身份证国徽面照片MediaID
     */
    @ApiModelProperty(value = " 身份证国徽面照片MediaID")
    @NotEmpty(message = " 身份证国徽面照片MediaID不能为空")
    @Length(max = 256, message = " 身份证国徽面照片MediaID长度不能超过256")
    @TableField(value = "id_card_national", condition = LIKE)
    @Excel(name = " 身份证国徽面照片MediaID")
    private String idCardNational;

    /**
     *  身份证国徽面照片原始文件id
     */
    @ApiModelProperty(value = " 身份证国徽面照片原始文件id")
    @NotEmpty(message = " 身份证国徽面照片原始文件id不能为空")
    @Length(max = 32, message = " 身份证国徽面照片原始文件id长度不能超过32")
    @TableField(value = "id_card_national_id", condition = LIKE)
    @Excel(name = " 身份证国徽面照片原始文件id")
    private String idCardNationalId;

    /**
     *  身份证国徽面照片原始文件url
     */
    @ApiModelProperty(value = " 身份证国徽面照片原始文件url")
    @NotEmpty(message = " 身份证国徽面照片原始文件url不能为空")
    @Length(max = 200, message = " 身份证国徽面照片原始文件url长度不能超过200")
    @TableField(value = "id_card_national_url", condition = LIKE)
    @Excel(name = " 身份证国徽面照片原始文件url")
    private String idCardNationalUrl;

    /**
     *  身份证姓名
     */
    @ApiModelProperty(value = " 身份证姓名")
    @NotEmpty(message = " 身份证姓名不能为空")
    @Length(max = 20, message = " 身份证姓名长度不能超过20")
    @TableField(value = "id_card_name", condition = LIKE)
    @Excel(name = " 身份证姓名")
    private String idCardName;

    /**
     *  身份证号码
     */
    @ApiModelProperty(value = " 身份证号码")
    @NotEmpty(message = " 身份证号码不能为空")
    @Length(max = 20, message = " 身份证号码长度不能超过20")
    @TableField(value = "id_card_number", condition = LIKE)
    @Excel(name = " 身份证号码")
    private String idCardNumber;

    /**
     *  身份证有效期开始时间
     */
    @ApiModelProperty(value = " 身份证有效期开始时间")
    @NotEmpty(message = " 身份证有效期开始时间不能为空")
    @Length(max = 128, message = " 身份证有效期开始时间长度不能超过128")
    @TableField(value = "card_period_begin", condition = LIKE)
    @Excel(name = " 身份证有效期开始时间")
    private String cardPeriodBegin;

    /**
     *  身份证有效期结束时间
     */
    @ApiModelProperty(value = " 身份证有效期结束时间")
    @NotEmpty(message = " 身份证有效期结束时间不能为空")
    @Length(max = 128, message = " 身份证有效期结束时间长度不能超过128")
    @TableField(value = "card_period_end", condition = LIKE)
    @Excel(name = " 身份证有效期结束时间")
    private String cardPeriodEnd;

    /**
     *  经营者/法人是否为受益人（1-true,0-false）
     */
    @ApiModelProperty(value = " 经营者/法人是否为受益人（1-true,0-false）")
    @NotNull(message = " 经营者/法人是否为受益人（1-true,0-false）不能为空")
    @TableField("owner")
    @Excel(name = " 经营者/法人是否为受益人（1-true,0-false）", replace = {"是_true", "否_false", "_null"})
    private Boolean owner;

    /**
     *  受益人证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)
     */
    @ApiModelProperty(value = " 受益人证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)")
    @Length(max = 32, message = " 受益人证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)长度不能超过32")
    @TableField(value = "ubo_id_type", condition = LIKE)
    @Excel(name = " 受益人证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)")
    private String uboIdType;

    /**
     *  受益人身份证人像面照片MediaID
     */
    @ApiModelProperty(value = " 受益人身份证人像面照片MediaID")
    @Length(max = 256, message = " 受益人身份证人像面照片MediaID长度不能超过256")
    @TableField(value = "ubo_id_card_copy", condition = LIKE)
    @Excel(name = " 受益人身份证人像面照片MediaID")
    private String uboIdCardCopy;

    /**
     *  受益人身份证人像面照片原始文件id
     */
    @ApiModelProperty(value = " 受益人身份证人像面照片原始文件id")
    @Length(max = 32, message = " 受益人身份证人像面照片原始文件id长度不能超过32")
    @TableField(value = "ubo_id_card_copy_id", condition = LIKE)
    @Excel(name = " 受益人身份证人像面照片原始文件id")
    private String uboIdCardCopyId;

    /**
     *  受益人身份证人像面照片原始文件url
     */
    @ApiModelProperty(value = " 受益人身份证人像面照片原始文件url")
    @Length(max = 200, message = " 受益人身份证人像面照片原始文件url长度不能超过200")
    @TableField(value = "ubo_id_card_copy_url", condition = LIKE)
    @Excel(name = " 受益人身份证人像面照片原始文件url")
    private String uboIdCardCopyUrl;

    /**
     *  受益人身份证国徽面照片MediaID
     */
    @ApiModelProperty(value = " 受益人身份证国徽面照片MediaID")
    @Length(max = 256, message = " 受益人身份证国徽面照片MediaID长度不能超过256")
    @TableField(value = "ubo_id_card_national", condition = LIKE)
    @Excel(name = " 受益人身份证国徽面照片MediaID")
    private String uboIdCardNational;

    /**
     *  受益人身份证国徽面照片原始文件id
     */
    @ApiModelProperty(value = " 受益人身份证国徽面照片原始文件id")
    @Length(max = 32, message = " 受益人身份证国徽面照片原始文件id长度不能超过32")
    @TableField(value = "ubo_id_card_national_id", condition = LIKE)
    @Excel(name = " 受益人身份证国徽面照片原始文件id")
    private String uboIdCardNationalId;

    /**
     *  受益人身份证国徽面照片原始文件url
     */
    @ApiModelProperty(value = " 受益人身份证国徽面照片原始文件url")
    @Length(max = 200, message = " 受益人身份证国徽面照片原始文件url长度不能超过200")
    @TableField(value = "ubo_id_card_national_url", condition = LIKE)
    @Excel(name = " 受益人身份证国徽面照片原始文件url")
    private String uboIdCardNationalUrl;

    /**
     *  受益人姓名
     */
    @ApiModelProperty(value = " 受益人姓名")
    @Length(max = 20, message = " 受益人姓名长度不能超过20")
    @TableField(value = "ubo_name", condition = LIKE)
    @Excel(name = " 受益人姓名")
    private String uboName;

    /**
     *  受益人证件号码
     */
    @ApiModelProperty(value = " 受益人证件号码")
    @Length(max = 20, message = " 受益人证件号码长度不能超过20")
    @TableField(value = "ubo_id_number", condition = LIKE)
    @Excel(name = " 受益人证件号码")
    private String uboIdNumber;

    /**
     *  受益人证件有效期开始时间
     */
    @ApiModelProperty(value = " 受益人证件有效期开始时间")
    @Length(max = 128, message = " 受益人证件有效期开始时间长度不能超过128")
    @TableField(value = "ubo_id_period_begin", condition = LIKE)
    @Excel(name = " 受益人证件有效期开始时间")
    private String uboIdPeriodBegin;

    /**
     *  证件有效期结束时间
     */
    @ApiModelProperty(value = " 证件有效期结束时间")
    @Length(max = 128, message = " 证件有效期结束时间长度不能超过128")
    @TableField(value = "ubo_id_period_end", condition = LIKE)
    @Excel(name = " 证件有效期结束时间")
    private String uboIdPeriodEnd;

    /**
     *  商户简称
     */
    @ApiModelProperty(value = " 商户简称")
    @NotEmpty(message = " 商户简称不能为空")
    @Length(max = 64, message = " 商户简称长度不能超过64")
    @TableField(value = "merchant_shortname", condition = LIKE)
    @Excel(name = " 商户简称")
    private String merchantShortname;

    /**
     *  客服电话
     */
    @ApiModelProperty(value = " 客服电话")
    @NotEmpty(message = " 客服电话不能为空")
    @Length(max = 32, message = " 客服电话长度不能超过32")
    @TableField(value = "service_phone", condition = LIKE)
    @Excel(name = " 客服电话")
    private String servicePhone;

    /**
     *  经营场景类型
     */
    @ApiModelProperty(value = " 经营场景类型")
    @NotEmpty(message = " 经营场景类型不能为空")
    @Length(max = 120, message = " 经营场景类型长度不能超过120")
    @TableField(value = "sales_scenes_type", condition = LIKE)
    @Excel(name = " 经营场景类型")
    private String salesScenesType;

    /**
     *  门店名称
     */
    @ApiModelProperty(value = " 门店名称")
    @Length(max = 128, message = " 门店名称长度不能超过128")
    @TableField(value = "biz_store_name", condition = LIKE)
    @Excel(name = " 门店名称")
    private String bizStoreName;

    /**
     *  门店省市编码	
     */
    @ApiModelProperty(value = " 门店省市编码	")
    @Length(max = 128, message = " 门店省市编码	长度不能超过128")
    @TableField(value = "biz_address_code", condition = LIKE)
    @Excel(name = " 门店省市编码	")
    private String bizAddressCode;

    /**
     *  门店地址
     */
    @ApiModelProperty(value = " 门店地址")
    @Length(max = 128, message = " 门店地址长度不能超过128")
    @TableField(value = "biz_store_address", condition = LIKE)
    @Excel(name = " 门店地址")
    private String bizStoreAddress;

    /**
     *  门店门头照片MediaID
     */
    @ApiModelProperty(value = " 门店门头照片MediaID")
    @Length(max = 1024, message = " 门店门头照片MediaID长度不能超过1024")
    @TableField(value = "store_entrance_pic", condition = LIKE)
    @Excel(name = " 门店门头照片MediaID")
    private String storeEntrancePic;

    /**
     *  门店门头照片原始文件id
     */
    @ApiModelProperty(value = " 门店门头照片原始文件id")
    @Length(max = 32, message = " 门店门头照片原始文件id长度不能超过32")
    @TableField(value = "store_entrance_pic_id", condition = LIKE)
    @Excel(name = " 门店门头照片原始文件id")
    private String storeEntrancePicId;

    /**
     *  门店门头照片原始文件url
     */
    @ApiModelProperty(value = " 门店门头照片原始文件url")
    @Length(max = 200, message = " 门店门头照片原始文件url长度不能超过200")
    @TableField(value = "store_entrance_pic_url", condition = LIKE)
    @Excel(name = " 门店门头照片原始文件url")
    private String storeEntrancePicUrl;

    /**
     *  店内环境照片MediaID
     */
    @ApiModelProperty(value = " 店内环境照片MediaID")
    @Length(max = 1024, message = " 店内环境照片MediaID长度不能超过1024")
    @TableField(value = "indoor_pic", condition = LIKE)
    @Excel(name = " 店内环境照片MediaID")
    private String indoorPic;

    /**
     *  店内环境照片原始文件id
     */
    @ApiModelProperty(value = " 店内环境照片原始文件id")
    @Length(max = 32, message = " 店内环境照片原始文件id长度不能超过32")
    @TableField(value = "indoor_pic_id", condition = LIKE)
    @Excel(name = " 店内环境照片原始文件id")
    private String indoorPicId;

    /**
     *  店内环境照片原始文件url
     */
    @ApiModelProperty(value = " 店内环境照片原始文件url")
    @Length(max = 200, message = " 店内环境照片原始文件url长度不能超过200")
    @TableField(value = "indoor_pic_url", condition = LIKE)
    @Excel(name = " 店内环境照片原始文件url")
    private String indoorPicUrl;

    /**
     *  服务商小程序APPID
     */
    @ApiModelProperty(value = " 服务商小程序APPID")
    @Length(max = 256, message = " 服务商小程序APPID长度不能超过256")
    @TableField(value = "mini_program_appid", condition = LIKE)
    @Excel(name = " 服务商小程序APPID")
    private String miniProgramAppid;

    /**
     *  商家小程序APPID
     */
    @ApiModelProperty(value = " 商家小程序APPID")
    @Length(max = 256, message = " 商家小程序APPID长度不能超过256")
    @TableField(value = "mini_program_sub_appid", condition = LIKE)
    @Excel(name = " 商家小程序APPID")
    private String miniProgramSubAppid;

    /**
     *  服务商应用APPID
     */
    @ApiModelProperty(value = " 服务商应用APPID")
    @Length(max = 256, message = " 服务商应用APPID长度不能超过256")
    @TableField(value = "app_appid", condition = LIKE)
    @Excel(name = " 服务商应用APPID")
    private String appAppid;

    /**
     *  商家应用APPID
     */
    @ApiModelProperty(value = " 商家应用APPID")
    @Length(max = 256, message = " 商家应用APPID长度不能超过256")
    @TableField(value = "app_sub_appid", condition = LIKE)
    @Excel(name = " 商家应用APPID")
    private String appSubAppid;

    /**
     *  互联网网站域名
     */
    @ApiModelProperty(value = " 互联网网站域名")
    @Length(max = 1024, message = " 互联网网站域名长度不能超过1024")
    @TableField(value = "domain", condition = LIKE)
    @Excel(name = " 互联网网站域名")
    private String domain;

    /**
     *  入驻结算规则ID
     */
    @ApiModelProperty(value = " 入驻结算规则ID")
    @Length(max = 3, message = " 入驻结算规则ID长度不能超过3")
    @TableField(value = "settlement_id", condition = LIKE)
    @Excel(name = " 入驻结算规则ID")
    private String settlementId;

    /**
     *  所属行业
     */
    @ApiModelProperty(value = " 所属行业")
    @Length(max = 30, message = " 所属行业长度不能超过30")
    @TableField(value = "qualification_type", condition = LIKE)
    @Excel(name = " 所属行业")
    private String qualificationType;

    /**
     *  特殊资质图片MediaID
     */
    @ApiModelProperty(value = " 特殊资质图片MediaID")
    @Length(max = 1024, message = " 特殊资质图片MediaID长度不能超过1024")
    @TableField(value = "qualifications", condition = LIKE)
    @Excel(name = " 特殊资质图片MediaID")
    private String qualifications;

    /**
     *  特殊资质图片原始文件id
     */
    @ApiModelProperty(value = " 特殊资质图片原始文件id")
    @Length(max = 32, message = " 特殊资质图片原始文件id长度不能超过32")
    @TableField(value = "qualifications_id", condition = LIKE)
    @Excel(name = " 特殊资质图片原始文件id")
    private String qualificationsId;

    /**
     *  特殊资质图片原始文件url
     */
    @ApiModelProperty(value = " 特殊资质图片原始文件url")
    @Length(max = 200, message = " 特殊资质图片原始文件url长度不能超过200")
    @TableField(value = "qualifications_url", condition = LIKE)
    @Excel(name = " 特殊资质图片原始文件url")
    private String qualificationsUrl;

    /**
     *  账户类型（BANK_ACCOUNT_TYPE_CORPORATE：对公银行账户）
     */
    @ApiModelProperty(value = " 账户类型（BANK_ACCOUNT_TYPE_CORPORATE：对公银行账户）")
    @Length(max = 27, message = " 账户类型（BANK_ACCOUNT_TYPE_CORPORATE：对公银行账户）长度不能超过27")
    @TableField(value = "bank_account_type", condition = LIKE)
    @Excel(name = " 账户类型（BANK_ACCOUNT_TYPE_CORPORATE：对公银行账户）")
    private String bankAccountType;

    /**
     *  开户名称
     */
    @ApiModelProperty(value = " 开户名称")
    @NotEmpty(message = " 开户名称不能为空")
    @Length(max = 64, message = " 开户名称长度不能超过64")
    @TableField(value = "account_name", condition = LIKE)
    @Excel(name = " 开户名称")
    private String accountName;

    /**
     *  开户银行
     */
    @ApiModelProperty(value = " 开户银行")
    @NotEmpty(message = " 开户银行不能为空")
    @Length(max = 128, message = " 开户银行长度不能超过128")
    @TableField(value = "account_bank", condition = LIKE)
    @Excel(name = " 开户银行")
    private String accountBank;

    /**
     *  开户银行省市编码
     */
    @ApiModelProperty(value = " 开户银行省市编码")
    @NotEmpty(message = " 开户银行省市编码不能为空")
    @Length(max = 128, message = " 开户银行省市编码长度不能超过128")
    @TableField(value = "bank_address_code", condition = LIKE)
    @Excel(name = " 开户银行省市编码")
    private String bankAddressCode;

    /**
     *  开户银行全称（含支行]
     */
    @ApiModelProperty(value = " 开户银行全称（含支行]")
    @NotEmpty(message = " 开户银行全称（含支行]不能为空")
    @Length(max = 128, message = " 开户银行全称（含支行]长度不能超过128")
    @TableField(value = "bank_name", condition = LIKE)
    @Excel(name = " 开户银行全称（含支行]")
    private String bankName;

    /**
     *  银行账号
     */
    @ApiModelProperty(value = " 银行账号")
    @NotEmpty(message = " 银行账号不能为空")
    @Length(max = 20, message = " 银行账号长度不能超过20")
    @TableField(value = "account_number", condition = LIKE)
    @Excel(name = " 银行账号")
    private String accountNumber;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    @TableField("examine_status")
    @Excel(name = "审核状态")
    private Integer examineStatus;

    @Builder
    public ApplymentInfo(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, Integer examineStatus,
                    String businessCode, String contactName, String contactIdNumber, String openid, String mobilePhone, 
                    String contactEmail, String subjectType, String licenseCopy, String licenseCopyId, String licenseCopyUrl, String licenseNumber, 
                    String merchantName, String legalPerson, String idDocType, String idCardCopy, String idCardCopyId, String idCardCopyUrl, 
                    String idCardNational, String idCardNationalId, String idCardNationalUrl, String idCardName, String idCardNumber, String cardPeriodBegin, 
                    String cardPeriodEnd, Boolean owner, String uboIdType, String uboIdCardCopy, String uboIdCardCopyId, String uboIdCardCopyUrl, 
                    String uboIdCardNational, String uboIdCardNationalId, String uboIdCardNationalUrl, String uboName, String uboIdNumber, String uboIdPeriodBegin, 
                    String uboIdPeriodEnd, String merchantShortname, String servicePhone, String salesScenesType, String bizStoreName, String bizAddressCode, 
                    String bizStoreAddress, String storeEntrancePic, String storeEntrancePicId, String storeEntrancePicUrl, String indoorPic, String indoorPicId, 
                    String indoorPicUrl, String miniProgramAppid, String miniProgramSubAppid, String appAppid, String appSubAppid, String domain, 
                    String settlementId, String qualificationType, String bankAccountType, String accountName, String accountBank, String bankAddressCode, 
                    String bankName, String accountNumber, String qualifications, String qualificationsId, String qualificationsUrl) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.businessCode = businessCode;
        this.contactName = contactName;
        this.contactIdNumber = contactIdNumber;
        this.openid = openid;
        this.mobilePhone = mobilePhone;
        this.contactEmail = contactEmail;
        this.subjectType = subjectType;
        this.licenseCopy = licenseCopy;
        this.licenseCopyId = licenseCopyId;
        this.licenseCopyUrl = licenseCopyUrl;
        this.licenseNumber = licenseNumber;
        this.merchantName = merchantName;
        this.legalPerson = legalPerson;
        this.idDocType = idDocType;
        this.idCardCopy = idCardCopy;
        this.idCardCopyId = idCardCopyId;
        this.idCardCopyUrl = idCardCopyUrl;
        this.idCardNational = idCardNational;
        this.idCardNationalId = idCardNationalId;
        this.idCardNationalUrl = idCardNationalUrl;
        this.idCardName = idCardName;
        this.idCardNumber = idCardNumber;
        this.cardPeriodBegin = cardPeriodBegin;
        this.cardPeriodEnd = cardPeriodEnd;
        this.owner = owner;
        this.uboIdType = uboIdType;
        this.uboIdCardCopy = uboIdCardCopy;
        this.uboIdCardCopyId = uboIdCardCopyId;
        this.uboIdCardCopyUrl = uboIdCardCopyUrl;
        this.uboIdCardNational = uboIdCardNational;
        this.uboIdCardNationalId = uboIdCardNationalId;
        this.uboIdCardNationalUrl = uboIdCardNationalUrl;
        this.uboName = uboName;
        this.uboIdNumber = uboIdNumber;
        this.uboIdPeriodBegin = uboIdPeriodBegin;
        this.uboIdPeriodEnd = uboIdPeriodEnd;
        this.merchantShortname = merchantShortname;
        this.servicePhone = servicePhone;
        this.salesScenesType = salesScenesType;
        this.bizStoreName = bizStoreName;
        this.bizAddressCode = bizAddressCode;
        this.bizStoreAddress = bizStoreAddress;
        this.storeEntrancePic = storeEntrancePic;
        this.storeEntrancePicId = storeEntrancePicId;
        this.storeEntrancePicUrl = storeEntrancePicUrl;
        this.indoorPic = indoorPic;
        this.indoorPicId = indoorPicId;
        this.indoorPicUrl = indoorPicUrl;
        this.miniProgramAppid = miniProgramAppid;
        this.miniProgramSubAppid = miniProgramSubAppid;
        this.appAppid = appAppid;
        this.appSubAppid = appSubAppid;
        this.domain = domain;
        this.settlementId = settlementId;
        this.qualificationType = qualificationType;
        this.bankAccountType = bankAccountType;
        this.accountName = accountName;
        this.accountBank = accountBank;
        this.bankAddressCode = bankAddressCode;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.qualifications = qualifications;
        this.qualificationsId = qualificationsId;
        this.qualificationsUrl = qualificationsUrl;
        this.examineStatus = examineStatus;
    }

}
