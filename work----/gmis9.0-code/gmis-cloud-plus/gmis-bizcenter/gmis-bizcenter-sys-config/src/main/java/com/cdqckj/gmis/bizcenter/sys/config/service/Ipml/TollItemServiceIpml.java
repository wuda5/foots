package com.cdqckj.gmis.bizcenter.sys.config.service.Ipml;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.sys.config.service.TollItemService;
import com.cdqckj.gmis.operateparam.TollItemBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.systemparam.dto.TollItemSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TollItemUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.systemparam.enumeration.TolltermMoneyMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class TollItemServiceIpml extends SuperCenterServiceImpl implements TollItemService {
    @Autowired
    public TollItemBizApi tollItemBizApi;
    @Autowired
    public UseGasTypeBizApi useGasTypeBizApi;
    @Override
    public R<TollItem> saveTollItem(TollItemSaveDTO tollItemSaveDTO) {
        if(tollItemSaveDTO.getUseGasTypeId()!=null && tollItemSaveDTO.getUseGasTypeId().size()!=0) {
            List<UseGasType> useGasTypes = useGasTypeBizApi.queryList(tollItemSaveDTO.getUseGasTypeId()).getData();
            JSONArray array = new JSONArray();
            JSONObject object = null;
            for (UseGasType useGasType : useGasTypes) {
                object = new JSONObject();
                object.put("use_gas_type_id", useGasType.getId().toString());
//                object.put("use_gas_type_name", useGasType.getUseGasTypeName());
                array.add(object);
            }
            tollItemSaveDTO.setUseGasTypes(array.toJSONString());
        }
        tollItemSaveDTO.setDataStatus(DataStatusEnum.NORMAL.getValue());
        TollItem tollItem=new TollItem();
        tollItem.setSceneCode(tollItemSaveDTO.getSceneCode());
        tollItem.setItemName(tollItemSaveDTO.getItemName());
        if(tollItemSaveDTO.getMoneyMethod().equals(TolltermMoneyMethod.unfixed.getCode())){
            tollItemSaveDTO.setMoney(BigDecimal.ZERO);
        }
        List<TollItem> tollItems = tollItemBizApi.query(tollItem).getData();
        if(!TolltemSceneEnum.OTHER.eq(tollItemSaveDTO.getSceneCode())){
//            TolltemSceneEnum.get(tollItemSaveDTO.getSceneCode())
            Boolean data = tollItemBizApi.check(tollItemSaveDTO).getData();
            if(data==true){
                return R.fail(getLangMessage(MessageConstants.SCENE_CODE));
            }
            if(tollItems ==null || tollItems.size()==0){
                return tollItemBizApi.save(tollItemSaveDTO);
            }
            return R.fail(getLangMessage(MessageConstants.REPEAT_TOLLTEM_NAME));
        }
        if(tollItems ==null || tollItems.size()==0){
            return tollItemBizApi.save(tollItemSaveDTO);
        }
        return R.fail(getLangMessage(MessageConstants.REPEAT_TOLLTEM_NAME));
    }

    @Override
    public R<TollItem> updateTollItem(TollItemUpdateDTO tollItemUpdateDTO) {
        if(tollItemUpdateDTO.getUseGasTypeId()!=null && tollItemUpdateDTO.getUseGasTypeId().size()!=0) {
            List<UseGasType> useGasTypes = useGasTypeBizApi.queryList(tollItemUpdateDTO.getUseGasTypeId()).getData();
            JSONArray array = new JSONArray();
            JSONObject object = null;
            for (UseGasType useGasType : useGasTypes) {
                object = new JSONObject();
                object.put("use_gas_type_id", useGasType.getId().toString());
//                object.put("use_gas_type_name", useGasType.getUseGasTypeName());
                array.add(object);
            }
            tollItemUpdateDTO.setUseGasTypes(array.toJSONString());
        }
        if(tollItemUpdateDTO.getMoneyMethod().equals(TolltermMoneyMethod.unfixed.getCode())){
            tollItemUpdateDTO.setMoney(BigDecimal.ZERO);
        }
        TollItem tollItem=new TollItem();
        tollItem.setSceneCode(tollItemUpdateDTO.getSceneCode());
        List<TollItem> tollItems = tollItemBizApi.query(tollItem).getData();
        for (TollItem item : tollItems) {
            if(item.getId().longValue()!=tollItemUpdateDTO.getId().longValue() && item.getItemName().equals(tollItemUpdateDTO.getItemName())){
              //重复
                return R.fail(getLangMessage(MessageConstants.REPEAT_TOLLTEM_NAME));
            }
        }
        return tollItemBizApi.update(tollItemUpdateDTO);
    }
}
