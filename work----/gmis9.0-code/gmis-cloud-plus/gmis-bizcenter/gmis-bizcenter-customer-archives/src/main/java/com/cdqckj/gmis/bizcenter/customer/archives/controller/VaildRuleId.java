package com.cdqckj.gmis.bizcenter.customer.archives.controller;

import com.cdqckj.gmis.archive.CustomerBizApi;

import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class VaildRuleId implements ValidRule {


    @ApiModelProperty(value = "验证的类型")
    private CustomerBizApi customerBizApi;

    @ApiModelProperty(value = "失败的说明")
    private String validDesc;

    @ApiModelProperty(value = "空的说明")
    private String emptyDesc;

    private RedisService redisService;

    public VaildRuleId(CustomerBizApi customerBizApi,RedisService redisService, String validDesc, String emptyDesc) {
        this.customerBizApi = customerBizApi;
        this.validDesc = validDesc;
        this.emptyDesc = emptyDesc;
        this.redisService=redisService;
    }

    @Override
    public int validType() {
        return 0;
    }

    @Override
    public String validFailDesc() {
        return validDesc;
    }

    @Override
    public Integer validColStart() {
        return 3;
    }

    @Override
    public Integer validColSum() {
        return 1;
    }

    @Override
    public String validEmptyFailDesc() {
        return emptyDesc;
    }

    @Override
    public void validProcess(ValidResult validResult) {
        String data =  validResult.getColDataList().get(0);
        String key= CacheKeyUtil.createTenantKey("Ua","Ic", "idNumber");
        if (!redisService.hasKey(key)){
            List<String> oList = customerBizApi.findIdNumber().getData();
            for (String o: oList){
                redisService.redisTemplate.opsForSet().add(key, o);
            }
        }
        Boolean status = redisService.redisTemplate.opsForSet().isMember(key, data);
        if (status==true){
            validResult.setStatus(false);
            validResult.setInvalidDesc(validDesc);
        }
    }

}
