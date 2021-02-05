package com.cdqckj.gmis.bizcenter.read.meter.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterDataIotBizService;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotPageDTO;
import com.cdqckj.gmis.readmeter.vo.ReadMeterDataIotVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author hp
 */

@Service
public class ReadMeterDataIotBizServiceImpl extends SuperCenterServiceImpl implements ReadMeterDataIotBizService {

    @Autowired
    CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    ReadMeterDataIotApi readMeterDataIotApi;

    /**
     * 分页查询
     *
     * @param params 分页查询参数
     * @return 分页列表
     */
    @Override
    public R<Page<ReadMeterDataIotVo>> page(PageParams<ReadMeterDataIotPageDTO> params) {
        ReadMeterDataIotPageDTO param = params.getModel();
        String customerChargeNo = param.getCustomerChargeNo();
        if (!StringUtils.isEmpty(customerChargeNo) && StringUtils.isEmpty(param.getGasMeterCode())) {
            List<CustomerGasMeterRelated> relatedList = customerGasMeterRelatedBizApi.queryByChargeNo(customerChargeNo).getData();
            List<String> collect = relatedList.stream().map(CustomerGasMeterRelated::getGasMeterCode).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(collect)){
                return R.success(new Page<>(params.getCurrent(), params.getSize()));
            }
            param.setGasMeterCodeList(collect);
        }

        return readMeterDataIotApi.pageList(params);
    }

}
