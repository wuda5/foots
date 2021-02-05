package com.cdqckj.gmis.bizcenter.customer.archives.controller;

import com.cdqckj.gmis.archive.CustomerMaterialBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.customer.archives.service.CustomerMaterialService;
import com.cdqckj.gmis.bizcenter.customer.archives.vo.ImgVO;
import com.cdqckj.gmis.bizcenter.customer.archives.vo.UserMaterialVO;
import com.cdqckj.gmis.userarchive.dto.CustomerMaterialUpdateDTO;
import io.seata.common.util.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hp
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/archive/customerMaterial")
@Api(value = "customerMaterial", tags = "客户档案资料上传")
public class CustomerMaterialController {

    @Autowired
    CustomerMaterialService customerMaterialService;
    @Autowired
    CustomerMaterialBizApi customerMaterialBizApi;

    @ApiOperation("用户档案资料上传")
    @PostMapping(value = "uploadMaterial")
    public R<List<ImgVO>> uploadMaterial(@RequestBody UserMaterialVO materialVO) {
        List<ImgVO> imgVOS = customerMaterialService.uploadMaterial(materialVO);
        return R.success(imgVOS);
    }


    @ApiOperation("查询用户档案资料")
    @PostMapping("/query")
    public R<List<ImgVO>> query(@RequestBody UserMaterialVO query) {

        return R.success(customerMaterialService.query(query));
    }

    /**
     * 逻辑删除数据
     *
     * @param ids id数组
     * @return true or false
     */
    @ApiOperation("删除数据")
    @PostMapping("/logicalDelete")
    public R<Boolean> logicalDelete(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return R.fail("参数不能为空");
        }
        List<CustomerMaterialUpdateDTO> updateList = new ArrayList<>();
        ids.forEach(id -> {
            CustomerMaterialUpdateDTO update = CustomerMaterialUpdateDTO.builder()
                    .id(id)
                    .dataStatus(DataStatusEnum.NOT_AVAILABLE.getValue())
                    .build();
            updateList.add(update);
        });
        return customerMaterialBizApi.updateBatchById(updateList);
    }
}
