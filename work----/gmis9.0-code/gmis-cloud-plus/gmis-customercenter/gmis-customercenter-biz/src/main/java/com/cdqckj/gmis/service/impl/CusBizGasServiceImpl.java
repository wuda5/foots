package com.cdqckj.gmis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.CustomerGasMeterBindBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterBindUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterBind;
import com.cdqckj.gmis.entity.dto.GasMeterBindDTO;
import com.cdqckj.gmis.entity.vo.GasMeterICVO;
import com.cdqckj.gmis.entity.vo.GasMeterInfoVO;
import com.cdqckj.gmis.service.CusBizGasService;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import com.cdqckj.gmis.userarchive.dto.CustomerGasInfoDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.vo.CustomerPageVo;
import com.cdqckj.gmis.utils.CusCommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service("cusBizGasService")
public class CusBizGasServiceImpl implements CusBizGasService {

    @Autowired
    private CustomerGasMeterBindBizApi customerGasMeterBindBizApi;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;

    @Autowired
    private CustomerBizApi customerBizApi;

    @Autowired
    private RedisService redisService;

    private static final Long oneMonth = 3600*24*30L;

    @Override
    public List<CustomerGasInfoDTO> findGasMeters(List<String> meterChargeNos) {

        //根据缴费编号获取表具信息
        R<List<CustomerGasInfoDTO>> pageR = customerBizApi.findGasByChargeNos(meterChargeNos);

        return pageR.getData();
    }

    @Override
    public List<CustomerGasInfoDTO> bindGasMeters(GasMeterBindDTO gasMeterBindDTO) {

        ArrayList<String> value = new ArrayList<>();

       // 生成绑定燃气表具 缓存key
        String catchKey = CusCommonUtil.buildCatchKey(gasMeterBindDTO.getOpen_token());

        //同步
        if(CollectionUtils.isNotEmpty(gasMeterBindDTO.getMeterChargeNos())){

            List<String> meterChargeNos = gasMeterBindDTO.getMeterChargeNos();
            String values =  StringUtils.join(meterChargeNos, ",");

            redisService.set(catchKey, values,oneMonth);

            value = new ArrayList<>(meterChargeNos);
         //从缓存中获取
        }else{

            if(null!=redisService.get(catchKey)){
                String values = (String) redisService.get(catchKey);
                String[] split= StringUtils.split(values, ",");

                value =  new ArrayList<>( Arrays.asList(split));
            }
        }



        return this.findGasMeters(value);
    }

    @Override
    public List<GasMeterInfoVO> findNeedBindGasMeters(Customer targetCus) {
        List<GasMeterInfoVO> resultData = new ArrayList<>();

        //获取用户自身所属表具信息
        CustomerPageVo cusQueryData = new CustomerPageVo();
        cusQueryData.setCustomerCode(targetCus.getCustomerCode());
        cusQueryData.setDeleteStatus(0);
        cusQueryData.setPageNo(1);
        cusQueryData.setPageSize(30);
        R<Page<CustomerGasDto>> pageR = customerBizApi.findCustomerGasMeterPageTwo(cusQueryData);
        if(pageR.getIsError()){
            return null;
        }
        pageR.getData().getRecords().stream().forEach(item->{
            GasMeterInfoVO target = new GasMeterInfoVO();
            BeanUtils.copyProperties(item,target);
            resultData.add(target);
        });

        return resultData;
    }

    @Override
    public R<HashMap<String,Object>> findGasMeterByCode(String gascode) {
        R<HashMap<String,Object>> data = gasMeterBizApi.findGasMeterInfoByCode(gascode);
        return data;
    }

    @Override
    public Boolean unBindGasMeters(Long binId) {

        CustomerGasMeterBindUpdateDTO updateDTO = new CustomerGasMeterBindUpdateDTO();
        updateDTO.setBindStatus(false);
        updateDTO.setId(binId);
        R<CustomerGasMeterBind> bindR = customerGasMeterBindBizApi.update(updateDTO);
        if(bindR.getIsError()){
            return null;
        }
        if(null == bindR.getData()){
            return false;
        }
        return true;
    }

    @Override
    public R<GasMeterICVO> getICCardPWD(String encryptCardInfo) {
        GasMeterICVO result = new GasMeterICVO();
        //调用卡库服务
        return R.success(result);
    }
}
