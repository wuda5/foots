package com.cdqckj.gmis.pay.dto;

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
import com.cdqckj.gmis.base.entity.SuperEntity;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ApplymentInfoUpdateDTO", description = "特约商户进件申请信息")
public class ApplymentInfoUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 业务申请编号(系统生成)
     */
    @ApiModelProperty(value = "业务申请编号(系统生成)")
    @NotEmpty(message = "业务申请编号(系统生成)不能为空")
    @Length(max = 32, message = "业务申请编号(系统生成)长度不能超过32")
    private String businessCode;
    /**
     * 超级管理员姓名
     */
    @ApiModelProperty(value = "超级管理员姓名")
    @NotEmpty(message = "超级管理员姓名不能为空")
    @Length(max = 20, message = "超级管理员姓名长度不能超过20")
    private String contactName;
    /**
     *  超级管理员身份证件号码
     */
    @ApiModelProperty(value = " 超级管理员身份证件号码")
    @Length(max = 20, message = " 超级管理员身份证件号码长度不能超过20")
    private String contactIdNumber;
    /**
     *  超级管理员微信openid
     */
    @ApiModelProperty(value = " 超级管理员微信openid")
    @Length(max = 32, message = " 超级管理员微信openid长度不能超过32")
    private String openid;
    /**
     *  联系手机
     */
    @ApiModelProperty(value = " 联系手机")
    @NotEmpty(message = " 联系手机不能为空")
    @Length(max = 11, message = " 联系手机长度不能超过11")
    private String mobilePhone;
    /**
     *  联系邮箱
     */
    @ApiModelProperty(value = " 联系邮箱")
    @NotEmpty(message = " 联系邮箱不能为空")
    @Length(max = 20, message = " 联系邮箱长度不能超过20")
    private String contactEmail;
    /**
     *  SUBJECT_TYPE_ENTERPRISE（企业）
     */
    @ApiModelProperty(value = " SUBJECT_TYPE_ENTERPRISE（企业）")
    @Length(max = 32, message = " SUBJECT_TYPE_ENTERPRISE（企业）长度不能超过32")
    private String subjectType;
    /**
     *  营业执照照片MediaID
     */
    @ApiModelProperty(value = " 营业执照照片MediaID")
    @NotEmpty(message = " 营业执照照片MediaID不能为空")
    @Length(max = 255, message = " 营业执照照片MediaID长度不能超过255")
    private String licenseCopy;
    /**
     *  营业执照照片原始文件id
     */
    @ApiModelProperty(value = " 营业执照照片原始文件id")
    @NotEmpty(message = " 营业执照照片原始文件id不能为空")
    @Length(max = 255, message = " 营业执照照片原始文件id长度不能超过255")
    private String licenseCopyId;
    /**
     *  营业执照照片原始文件url
     */
    @ApiModelProperty(value = " 营业执照照片原始文件url")
    @NotEmpty(message = " 营业执照照片原始文件url不能为空")
    @Length(max = 200, message = " 营业执照照片原始文件url长度不能超过200")
    private String licenseCopyUrl;
    /**
     *  注册号/统一社会信用代码
     */
    @ApiModelProperty(value = " 注册号/统一社会信用代码")
    @NotEmpty(message = " 注册号/统一社会信用代码不能为空")
    @Length(max = 32, message = " 注册号/统一社会信用代码长度不能超过32")
    private String licenseNumber;
    /**
     *  商户名称
     */
    @ApiModelProperty(value = " 商户名称")
    @NotEmpty(message = " 商户名称不能为空")
    @Length(max = 128, message = " 商户名称长度不能超过128")
    private String merchantName;
    /**
     *  个体户经营者/法人姓名
     */
    @ApiModelProperty(value = " 个体户经营者/法人姓名")
    @NotEmpty(message = " 个体户经营者/法人姓名不能为空")
    @Length(max = 64, message = " 个体户经营者/法人姓名长度不能超过64")
    private String legalPerson;
    /**
     *  证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)
     */
    @ApiModelProperty(value = " 证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)")
    @Length(max = 42, message = " 证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)长度不能超过42")
    private String idDocType;
    /**
     *  身份证人像面照片MediaID
     */
    @ApiModelProperty(value = " 身份证人像面照片MediaID")
    @NotEmpty(message = " 身份证人像面照片MediaID不能为空")
    @Length(max = 256, message = " 身份证人像面照片MediaID长度不能超过256")
    private String idCardCopy;
    /**
     *  身份证人像面照片原始文件id
     */
    @ApiModelProperty(value = " 身份证人像面照片原始文件id")
    @NotEmpty(message = " 身份证人像面照片原始文件id不能为空")
    @Length(max = 32, message = " 身份证人像面照片原始文件id长度不能超过32")
    private String idCardCopyId;
    /**
     *  身份证人像面照片url
     */
    @ApiModelProperty(value = " 身份证人像面照片url")
    @NotEmpty(message = " 身份证人像面照片url不能为空")
    @Length(max = 200, message = " 身份证人像面照片url长度不能超过200")
    private String idCardCopyUrl;
    /**
     *  身份证国徽面照片MediaID
     */
    @ApiModelProperty(value = " 身份证国徽面照片MediaID")
    @NotEmpty(message = " 身份证国徽面照片MediaID不能为空")
    @Length(max = 256, message = " 身份证国徽面照片MediaID长度不能超过256")
    private String idCardNational;
    /**
     *  身份证国徽面照片原始文件id
     */
    @ApiModelProperty(value = " 身份证国徽面照片原始文件id")
    @NotEmpty(message = " 身份证国徽面照片原始文件id不能为空")
    @Length(max = 32, message = " 身份证国徽面照片原始文件id长度不能超过32")
    private String idCardNationalId;
    /**
     *  身份证国徽面照片原始文件url
     */
    @ApiModelProperty(value = " 身份证国徽面照片原始文件url")
    @NotEmpty(message = " 身份证国徽面照片原始文件url不能为空")
    @Length(max = 200, message = " 身份证国徽面照片原始文件url长度不能超过200")
    private String idCardNationalUrl;
    /**
     *  身份证姓名
     */
    @ApiModelProperty(value = " 身份证姓名")
    @NotEmpty(message = " 身份证姓名不能为空")
    @Length(max = 20, message = " 身份证姓名长度不能超过20")
    private String idCardName;
    /**
     *  身份证号码
     */
    @ApiModelProperty(value = " 身份证号码")
    @NotEmpty(message = " 身份证号码不能为空")
    @Length(max = 20, message = " 身份证号码长度不能超过20")
    private String idCardNumber;
    /**
     *  身份证有效期开始时间
     */
    @ApiModelProperty(value = " 身份证有效期开始时间")
    @NotEmpty(message = " 身份证有效期开始时间不能为空")
    @Length(max = 128, message = " 身份证有效期开始时间长度不能超过128")
    private String cardPeriodBegin;
    /**
     *  身份证有效期结束时间
     */
    @ApiModelProperty(value = " 身份证有效期结束时间")
    @NotEmpty(message = " 身份证有效期结束时间不能为空")
    @Length(max = 128, message = " 身份证有效期结束时间长度不能超过128")
    private String cardPeriodEnd;
    /**
     *  经营者/法人是否为受益人（1-true,0-false）
     */
    @ApiModelProperty(value = " 经营者/法人是否为受益人（1-true,0-false）")
    @NotNull(message = " 经营者/法人是否为受益人（1-true,0-false）不能为空")
    private Boolean owner;
    /**
     *  受益人证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)
     */
    @ApiModelProperty(value = " 受益人证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)")
    @Length(max = 32, message = " 受益人证件类型(IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证)长度不能超过32")
    private String uboIdType;
    /**
     *  受益人身份证人像面照片MediaID
     */
    @ApiModelProperty(value = " 受益人身份证人像面照片MediaID")
    @Length(max = 256, message = " 受益人身份证人像面照片MediaID长度不能超过256")
    private String uboIdCardCopy;
    /**
     *  受益人身份证人像面照片原始文件id
     */
    @ApiModelProperty(value = " 受益人身份证人像面照片原始文件id")
    @Length(max = 32, message = " 受益人身份证人像面照片原始文件id长度不能超过32")
    private String uboIdCardCopyId;
    /**
     *  受益人身份证人像面照片原始文件url
     */
    @ApiModelProperty(value = " 受益人身份证人像面照片原始文件url")
    @Length(max = 200, message = " 受益人身份证人像面照片原始文件url长度不能超过200")
    private String uboIdCardCopyUrl;
    /**
     *  受益人身份证国徽面照片MediaID
     */
    @ApiModelProperty(value = " 受益人身份证国徽面照片MediaID")
    @Length(max = 256, message = " 受益人身份证国徽面照片MediaID长度不能超过256")
    private String uboIdCardNational;
    /**
     *  受益人身份证国徽面照片原始文件id
     */
    @ApiModelProperty(value = " 受益人身份证国徽面照片原始文件id")
    @Length(max = 32, message = " 受益人身份证国徽面照片原始文件id长度不能超过32")
    private String uboIdCardNationalId;
    /**
     *  受益人身份证国徽面照片原始文件url
     */
    @ApiModelProperty(value = " 受益人身份证国徽面照片原始文件url")
    @Length(max = 200, message = " 受益人身份证国徽面照片原始文件url长度不能超过200")
    private String uboIdCardNationalUrl;
    /**
     *  受益人姓名
     */
    @ApiModelProperty(value = " 受益人姓名")
    @Length(max = 20, message = " 受益人姓名长度不能超过20")
    private String uboName;
    /**
     *  受益人证件号码
     */
    @ApiModelProperty(value = " 受益人证件号码")
    @Length(max = 20, message = " 受益人证件号码长度不能超过20")
    private String uboIdNumber;
    /**
     *  受益人证件有效期开始时间
     */
    @ApiModelProperty(value = " 受益人证件有效期开始时间")
    @Length(max = 128, message = " 受益人证件有效期开始时间长度不能超过128")
    private String uboIdPeriodBegin;
    /**
     *  证件有效期结束时间
     */
    @ApiModelProperty(value = " 证件有效期结束时间")
    @Length(max = 128, message = " 证件有效期结束时间长度不能超过128")
    private String uboIdPeriodEnd;
    /**
     *  商户简称
     */
    @ApiModelProperty(value = " 商户简称")
    @NotEmpty(message = " 商户简称不能为空")
    @Length(max = 64, message = " 商户简称长度不能超过64")
    private String merchantShortname;
    /**
     *  客服电话
     */
    @ApiModelProperty(value = " 客服电话")
    @NotEmpty(message = " 客服电话不能为空")
    @Length(max = 32, message = " 客服电话长度不能超过32")
    private String servicePhone;
    /**
     *  经营场景类型
     */
    @ApiModelProperty(value = " 经营场景类型")
    @NotEmpty(message = " 经营场景类型不能为空")
    @Length(max = 120, message = " 经营场景类型长度不能超过120")
    private String salesScenesType;
    /**
     *  门店名称
     */
    @ApiModelProperty(value = " 门店名称")
    @Length(max = 128, message = " 门店名称长度不能超过128")
    private String bizStoreName;
    /**
     *  门店省市编码	
     */
    @ApiModelProperty(value = " 门店省市编码	")
    @Length(max = 128, message = " 门店省市编码	长度不能超过128")
    private String bizAddressCode;
    /**
     *  门店地址
     */
    @ApiModelProperty(value = " 门店地址")
    @Length(max = 128, message = " 门店地址长度不能超过128")
    private String bizStoreAddress;
    /**
     *  门店门头照片MediaID
     */
    @ApiModelProperty(value = " 门店门头照片MediaID")
    @Length(max = 1024, message = " 门店门头照片MediaID长度不能超过1024")
    private String storeEntrancePic;
    /**
     *  门店门头照片原始文件id
     */
    @ApiModelProperty(value = " 门店门头照片原始文件id")
    @Length(max = 32, message = " 门店门头照片原始文件id长度不能超过32")
    private String storeEntrancePicId;
    /**
     *  门店门头照片原始文件url
     */
    @ApiModelProperty(value = " 门店门头照片原始文件url")
    @Length(max = 200, message = " 门店门头照片原始文件url长度不能超过200")
    private String storeEntrancePicUrl;
    /**
     *  店内环境照片MediaID
     */
    @ApiModelProperty(value = " 店内环境照片MediaID")
    @Length(max = 1024, message = " 店内环境照片MediaID长度不能超过1024")
    private String indoorPic;
    /**
     *  店内环境照片原始文件id
     */
    @ApiModelProperty(value = " 店内环境照片原始文件id")
    @Length(max = 32, message = " 店内环境照片原始文件id长度不能超过32")
    private String indoorPicId;
    /**
     *  店内环境照片原始文件url
     */
    @ApiModelProperty(value = " 店内环境照片原始文件url")
    @Length(max = 200, message = " 店内环境照片原始文件url长度不能超过200")
    private String indoorPicUrl;
    /**
     *  服务商小程序APPID
     */
    @ApiModelProperty(value = " 服务商小程序APPID")
    @Length(max = 256, message = " 服务商小程序APPID长度不能超过256")
    private String miniProgramAppid;
    /**
     *  商家小程序APPID
     */
    @ApiModelProperty(value = " 商家小程序APPID")
    @Length(max = 256, message = " 商家小程序APPID长度不能超过256")
    private String miniProgramSubAppid;
    /**
     *  服务商应用APPID
     */
    @ApiModelProperty(value = " 服务商应用APPID")
    @Length(max = 256, message = " 服务商应用APPID长度不能超过256")
    private String appAppid;
    /**
     *  商家应用APPID
     */
    @ApiModelProperty(value = " 商家应用APPID")
    @Length(max = 256, message = " 商家应用APPID长度不能超过256")
    private String appSubAppid;
    /**
     *  互联网网站域名
     */
    @ApiModelProperty(value = " 互联网网站域名")
    @Length(max = 1024, message = " 互联网网站域名长度不能超过1024")
    private String domain;
    /**
     *  入驻结算规则ID
     */
    @ApiModelProperty(value = " 入驻结算规则ID")
    @Length(max = 3, message = " 入驻结算规则ID长度不能超过3")
    private String settlementId;
    /**
     *  所属行业
     */
    @ApiModelProperty(value = " 所属行业")
    @Length(max = 30, message = " 所属行业长度不能超过30")
    private String qualificationType;
    /**
     *  特殊资质图片MediaID
     */
    @ApiModelProperty(value = " 特殊资质图片MediaID")
    @Length(max = 1024, message = " 特殊资质图片MediaID长度不能超过1024")
    private String qualifications;

    /**
     *  特殊资质图片原始文件id
     */
    @ApiModelProperty(value = " 特殊资质图片原始文件id")
    @Length(max = 32, message = " 特殊资质图片原始文件id长度不能超过32")
    private String qualificationsId;

    /**
     *  特殊资质图片原始文件url
     */
    @ApiModelProperty(value = " 特殊资质图片原始文件url")
    @Length(max = 200, message = " 特殊资质图片原始文件url长度不能超过200")
    private String qualificationsUrl;
    /**
     *  账户类型（BANK_ACCOUNT_TYPE_CORPORATE：对公银行账户）
     */
    @ApiModelProperty(value = " 账户类型（BANK_ACCOUNT_TYPE_CORPORATE：对公银行账户）")
    @Length(max = 27, message = " 账户类型（BANK_ACCOUNT_TYPE_CORPORATE：对公银行账户）长度不能超过27")
    private String bankAccountType;
    /**
     *  开户名称
     */
    @ApiModelProperty(value = " 开户名称")
    @NotEmpty(message = " 开户名称不能为空")
    @Length(max = 64, message = " 开户名称长度不能超过64")
    private String accountName;
    /**
     *  开户银行
     */
    @ApiModelProperty(value = " 开户银行")
    @NotEmpty(message = " 开户银行不能为空")
    @Length(max = 128, message = " 开户银行长度不能超过128")
    private String accountBank;
    /**
     *  开户银行省市编码
     */
    @ApiModelProperty(value = " 开户银行省市编码")
    @NotEmpty(message = " 开户银行省市编码不能为空")
    @Length(max = 128, message = " 开户银行省市编码长度不能超过128")
    private String bankAddressCode;
    /**
     *  开户银行全称（含支行]
     */
    @ApiModelProperty(value = " 开户银行全称（含支行]")
    @NotEmpty(message = " 开户银行全称（含支行]不能为空")
    @Length(max = 128, message = " 开户银行全称（含支行]长度不能超过128")
    private String bankName;
    /**
     *  银行账号
     */
    @ApiModelProperty(value = " 银行账号")
    @NotEmpty(message = " 银行账号不能为空")
    @Length(max = 20, message = " 银行账号长度不能超过20")
    private String accountNumber;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private Integer examineStatus;
}
