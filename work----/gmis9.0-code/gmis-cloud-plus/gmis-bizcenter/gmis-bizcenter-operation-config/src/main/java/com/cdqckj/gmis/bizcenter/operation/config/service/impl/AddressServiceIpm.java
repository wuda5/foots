package com.cdqckj.gmis.bizcenter.operation.config.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.operation.config.service.AddressService;
import com.cdqckj.gmis.common.utils.LatitudeUtils;
import com.cdqckj.gmis.operateparam.BatcgDetailedAddressBizApi;
import com.cdqckj.gmis.operateparam.CommunityBizApi;
import com.cdqckj.gmis.operateparam.StreetBizApi;
import com.cdqckj.gmis.operateparam.dto.*;
import com.cdqckj.gmis.operateparam.entity.BatchDetailedAddress;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operateparam.enumeration.DetailAddressFlagEnum;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceIpm extends SuperCenterServiceImpl implements AddressService {
    @Autowired
    private StreetBizApi streetBizApi;
    @Autowired
    private CommunityBizApi communityBizApi;
    @Autowired
    private BatcgDetailedAddressBizApi detailedAddressBizApi;
    @Override
    public R<Community> saveCommunity(CommunitySaveDTO communitySaveDTO) {
        R<Community> res = R.fail(getLangMessage(MessageConstants.ADDRESS_COMMUNITY_REPEAT));
        Street street = streetBizApi.get(communitySaveDTO.getStreetId()).getData();
        StringBuilder address=new StringBuilder();
        address.append(street.getProvinceName()).append(street.getCityName())
                .append(street.getAreaName()).append(street.getStreetName()).append(communitySaveDTO.getCommunityName());
        Map<String, String> map = LatitudeUtils.getGeocoderLatitude(address.toString());
        if(map !=null && map.containsKey("lng")&& map.containsKey("lat")){
            String lng = map.get("lng");
            BigDecimal longitude =new BigDecimal(lng);
            String lat = map.get("lat");
            BigDecimal latitude =new BigDecimal(lat);
            communitySaveDTO.setLongitude(longitude);
            communitySaveDTO.setLatitude(latitude);
        }
        //小区去重
        PageParams<CommunityPageDTO> params = new PageParams<>();
        CommunityPageDTO pageDTO = new CommunityPageDTO();
        pageDTO.setCommunityName(communitySaveDTO.getCommunityName());
        pageDTO.setStreetId(communitySaveDTO.getStreetId());
        pageDTO.setDeleteStatus(0);
        params.setModel(pageDTO);
        Page<Community> list = communityBizApi.page(params).getData();
        if(list.getRecords()!=null&&list.getRecords().size()>0){return res;}
        return communityBizApi.save(communitySaveDTO);
    }

    @Override
    public R<Street> saveStreet(StreetSaveDTO streetSaveDTO) {
        R<Street> res = R.fail(getLangMessage(MessageConstants.ADDRESS_STREET_REPEAT));
        //小区去重
        PageParams<StreetPageDTO> params = new PageParams<>();
        StreetPageDTO pageDTO = new StreetPageDTO();
        pageDTO.setProvinceCode(streetSaveDTO.getProvinceCode());
        pageDTO.setCityCode(streetSaveDTO.getCityCode());
        pageDTO.setAreaCode(streetSaveDTO.getAreaCode());
        pageDTO.setStreetName(streetSaveDTO.getStreetName());
        params.setModel(pageDTO);
        Page<Street> list = streetBizApi.page(params).getData();
        if(list.getRecords()!=null&&list.getRecords().size()>0){return res;}
        return streetBizApi.save(streetSaveDTO);
    }

    @Override
    public R<BatchDetailedAddress> batchAddresss(BatchDetailedAddressSaveDTO detailedAddressSaveDTO) {
        R<BatchDetailedAddress> res = R.fail(getLangMessage(MessageConstants.DETAIL_ADDRESS_REPEAT));
        //楼层
        Integer storey =detailedAddressSaveDTO.getStorey();
        //户数
        Integer households =detailedAddressSaveDTO.getHouseholds();
        StringBuilder address = new StringBuilder();
        StringBuilder detailAddress = new StringBuilder();
        BatchDetailedAddressSaveDTO temp;
        List<BatchDetailedAddressSaveDTO> saveDTOS= Lists.newArrayList();
        BatchDetailedAddress detailedAddress=new BatchDetailedAddress();
        detailedAddress.setCommunityId(detailedAddressSaveDTO.getCommunityId());
        detailedAddress.setBuilding(detailedAddressSaveDTO.getBuilding());
        detailedAddress.setUnit(detailedAddressSaveDTO.getUnit());
        List<BatchDetailedAddress> detailedAddresslist = detailedAddressBizApi.query(detailedAddress).getData();
        List<String> repeataddress=new ArrayList<>();
        if(detailedAddresslist !=null || detailedAddresslist.size()==0){
            for (BatchDetailedAddress detailedAddressone : detailedAddresslist) {
                repeataddress.add(detailedAddressone.getDetailAddress());
            }
        }
        for (int i = 1; i <=storey ; i++) {//2楼层
            for (int j = 1; j <=households; j++) {//3户数
                temp=new BatchDetailedAddressSaveDTO();
                address=address.append(i).append("层").append(j).append("号");
                if(detailedAddressSaveDTO.getBuilding() !=null && detailedAddressSaveDTO.getBuilding().intValue()!=0){
                    detailAddress=detailAddress.append(detailedAddressSaveDTO.getBuilding()).append("栋");
                }
                if(detailedAddressSaveDTO.getUnit()!=null && detailedAddressSaveDTO.getUnit().intValue() !=0){
                    detailAddress=detailAddress.append(detailedAddressSaveDTO.getUnit()).append("单元");
                }
                detailAddress=detailAddress.append(i).append("层").append(j).append("号");
                BeanUtils.copyProperties(detailedAddressSaveDTO,temp);
                temp.setDetailAddress(address.toString());
                temp.setMoreDetailAddress(detailAddress.toString());
                temp.setFlag(DetailAddressFlagEnum.CITY.getCode());
                saveDTOS.add(temp);
                if(repeataddress !=null && repeataddress.size()>0 && repeataddress.contains(address.toString())){
                    return res;
                }
                address=new StringBuilder();
                detailAddress=new StringBuilder();
            }
        }
            return detailedAddressBizApi.saveList(saveDTOS);
    }

    @Override
    @GlobalTransactional
    public R<BatchDetailedAddress> batchAddresssEx(BatchDetailedAddressSaveDTO addressDTO){
        /**
         * 1、小区ID为空，表示新增小区后再批量建地址
         * 2、调压箱号批量新增：默认为农村
         */
        //新增小区
        if(null == addressDTO.getCommunityId()){
            CommunitySaveDTO communitySaveDTO = new CommunitySaveDTO();
            communitySaveDTO.setCommunityName(addressDTO.getCommunityName());
            communitySaveDTO.setStreetId(addressDTO.getStreetId());
            communitySaveDTO.setDataStatus(1);
            communitySaveDTO.setDeleteStatus(0);
            R<Community> communityR = this.saveCommunity(communitySaveDTO);
            if(communityR.getIsError()){
                return R.fail(communityR.getMsg());
            }
            addressDTO.setCommunityId(communityR.getData().getId());

        //已建有小区
        }else{

            //验证当前地址下是否已批量建过地址
            BatchDetailedAddress detailedAddress=new BatchDetailedAddress();
            detailedAddress.setCommunityId(addressDTO.getCommunityId());
            //调压箱号批量
            if(!addressDTO.getNodeFlag()) {
                detailedAddress.setBuilding(addressDTO.getBuilding());
                detailedAddress.setUnit(addressDTO.getUnit());
                detailedAddress.setFlag(1);
            }else{
                detailedAddress.setFlag(0);
            }
            detailedAddress.setDeleteStatus(0);
            List<BatchDetailedAddress> list = detailedAddressBizApi.query(detailedAddress).getData();
            if(CollectionUtils.isNotEmpty(list)){
                return R.fail(getLangMessage(MessageConstants.DETAIL_ADDRESS_REPEAT));
            }
        }

        //批量生成数据
        final List<BatchDetailedAddressSaveDTO> saveData = new ArrayList<>();
        //调压箱号批量
        if(addressDTO.getNodeFlag()){
            for(int i=1;i<=addressDTO.getNodeCount();i++){
                BatchDetailedAddressSaveDTO item = new BatchDetailedAddressSaveDTO();
                item.setCommunityId(addressDTO.getCommunityId());
                item.setCommunityName(addressDTO.getCommunityName());
                item.setFlag(0);
                item.setDataStatus(1);
                item.setDeleteStatus(0);
                String address = "调压箱"+i+"号";
                item.setDetailAddress(address);
                item.setMoreDetailAddress(address);

                saveData.add(item);
            }
        }else{

            for(int floor=1;floor<=addressDTO.getStorey();floor++){

                for(int i=1;i<=addressDTO.getHouseholds();i++){

                    BatchDetailedAddressSaveDTO item = new BatchDetailedAddressSaveDTO();
                    item.setCommunityId(addressDTO.getCommunityId());
                    item.setCommunityName(addressDTO.getCommunityName());
                    item.setBuilding(addressDTO.getBuilding());
                    item.setUnit(addressDTO.getUnit());
                    item.setFlag(1);
                    item.setDataStatus(1);
                    item.setDeleteStatus(0);
                    String address = floor + "层"+ i +"号";
                    String moreAddress = addressDTO.getBuilding() +"栋"+ addressDTO.getUnit()+"单元"+address;
                    item.setDetailAddress(address);
                    item.setMoreDetailAddress(moreAddress);

                    saveData.add(item);
                }
            }
        }

        return detailedAddressBizApi.saveList(saveData);
    }

    @Override
    public R<BatchDetailedAddress> saveDetailAddresss(BatchDetailedAddressSaveDTO detailedAddressSaveDTO) {
        StringBuilder moreDetailAddress=new StringBuilder();
        if(detailedAddressSaveDTO.getBuilding() !=null && detailedAddressSaveDTO.getBuilding().intValue()!=0){
            moreDetailAddress=moreDetailAddress.append(detailedAddressSaveDTO.getBuilding()).append("栋");
        }
        if(detailedAddressSaveDTO.getUnit()!=null && detailedAddressSaveDTO.getUnit().intValue() !=0){
            moreDetailAddress=moreDetailAddress.append(detailedAddressSaveDTO.getUnit()).append("单元");
        }
        moreDetailAddress.append(detailedAddressSaveDTO.getDetailAddress());
        detailedAddressSaveDTO.setMoreDetailAddress(moreDetailAddress.toString());

        BatchDetailedAddress checkDetail =new BatchDetailedAddress()
                .setCommunityId(detailedAddressSaveDTO.getCommunityId())
                .setMoreDetailAddress(moreDetailAddress.toString());// 村id+ more地址, 就可以确定兼容城市和 农村 是否唯一
//            .setBuilding(detailedAddressSaveDTO.getBuilding())
//            .setUnit(detailedAddressSaveDTO.getUnit());
        List<BatchDetailedAddress> data = detailedAddressBizApi.query(checkDetail).getData();
        if(data !=null && data.size()>0){
            return R.fail(getLangMessage(MessageConstants.DETAIL_ADDRESS_REPEAT));
        }
        return detailedAddressBizApi.save(detailedAddressSaveDTO);
    }

    @Override
    public R<Community> updateCommunity(CommunityUpdateDTO communityUpdateDTO) {
        R<Community> res = R.fail(getLangMessage(MessageConstants.ADDRESS_COMMUNITY_REPEAT));
        Boolean data = communityBizApi.check(communityUpdateDTO).getData();
        if(data==true){
            return res;
        }
        return communityBizApi.update(communityUpdateDTO);
    }

    @Override
    public R<Street> updateStreet(StreetUpdateDTO streetUpdateDTO) {
        R<Street> res = R.fail(getLangMessage(MessageConstants.ADDRESS_STREET_REPEAT));
        Boolean data = streetBizApi.check(streetUpdateDTO).getData();
        if (data==true){
            return res;
        }
        return streetBizApi.update(streetUpdateDTO);
    }

    @Override
    public R<BatchDetailedAddress>
    updateDetailAddresss(BatchDetailedAddressUpdateDTO updateDTO) {
        R<BatchDetailedAddress> res = R.fail(getLangMessage(MessageConstants.DETAIL_ADDRESS_REPEAT));
        Boolean data = detailedAddressBizApi.check(updateDTO).getData();
        if (data==true){
            return res;
        }
        String morAddress;
        //判别农村还是城市
        if(updateDTO.getFlag().equals(0)){
            //龙村
            morAddress = updateDTO.getDetailAddress();
        }else{
            //城市
            StringBuilder dd = new StringBuilder();
            dd.append(updateDTO.getBuilding()+"栋");
            dd.append(updateDTO.getUnit()+"单元");
            dd.append(updateDTO.getDetailAddress());
            morAddress = dd.toString();
        }
        updateDTO.setMoreDetailAddress(morAddress);

        return detailedAddressBizApi.update(updateDTO);
    }
}
