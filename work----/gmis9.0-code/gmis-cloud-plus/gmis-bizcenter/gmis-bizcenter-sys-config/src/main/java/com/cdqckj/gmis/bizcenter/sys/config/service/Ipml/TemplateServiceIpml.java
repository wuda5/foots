package com.cdqckj.gmis.bizcenter.sys.config.service.Ipml;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.authority.api.TenantApi;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.sys.config.service.TemplateService;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.file.entity.Attachment;
import com.cdqckj.gmis.operateparam.TemplateBizApi;
import com.cdqckj.gmis.operateparam.TemplateItemBizApi;
import com.cdqckj.gmis.operateparam.TemplateRecordBizApi;
import com.cdqckj.gmis.security.model.SysUser;
import com.cdqckj.gmis.systemparam.dto.TemplateItemUpdateDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordSaveDTO;
import com.cdqckj.gmis.systemparam.entity.Template;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import com.cdqckj.gmis.systemparam.entity.TemplateRecord;
import com.cdqckj.gmis.systemparam.entity.vo.TemplateVo;
import com.cdqckj.gmis.systemparam.enumeration.AuditEnum;
import com.cdqckj.gmis.systemparam.enumeration.DefaultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TemplateServiceIpml extends SuperCenterServiceImpl implements TemplateService {

    @Autowired
    public TemplateItemBizApi templateItemBizApi;
    @Autowired
    public TemplateBizApi templateBizApi;
    @Autowired
    public TenantApi tenantApi;
    @Autowired
    public AttachmentApi attachmentApi;
    @Autowired
    public TemplateRecordBizApi templateRecordBizApi;

    @Override
    public R<TemplateItem> share(Long templateItemId) {
        TemplateItem templateItem = templateItemBizApi.getById(templateItemId).getData();
        Boolean bool= false;
        if(null!=templateItem){
            templateItem.setDataStatus(AuditEnum.NOT_REVIEWED.getStatus());
            bool= templateItemBizApi.share(templateItem).getIsSuccess();
        }
        if(bool){
            TemplateItemUpdateDTO model = BeanUtil.toBean(templateItem, TemplateItemUpdateDTO.class);
            return templateItemBizApi.update(model);
        }
        return R.fail("共享失败");
    }

    @Override
    public R<Boolean> adminExamine(List<TemplateItem> list) {
        if(list.size()==0){
            return R.fail("请至少选择一条数据");
        }else{
            Integer dataStatus = list.get(0).getDataStatus();
            if(dataStatus.equals(AuditEnum.APPROVED.getStatus()) || dataStatus.equals(AuditEnum.REJECT.getStatus())){
                Boolean bool = templateItemBizApi.adminExamine(list).getData();
                //defaults数据库修改成功后下发
                if(bool){
                    Map<String,List<TemplateItem>> codeList = list.stream().collect(Collectors.groupingBy(TemplateItem::getCompanyCode));
                    for(Map.Entry<String,List<TemplateItem>> entry:codeList.entrySet()){
                        templateItemBizApi.updateByCode(entry.getValue(),entry.getKey());
                    }
                }
                /*if(dataStatus.equals(AuditEnum.APPROVED.getStatus())){
                    templateItemBizApi.publish(list);
                }*/
                return R.success();
            }else{
                return R.fail("请选择处理方式");
            }
        }
    }

    @Deprecated
    @Override
    public R<Boolean> deleteTemplateItem(List<Long> ids) {
        //TODO 先删除附件
        R<List<TemplateItem>>  rList = templateItemBizApi.queryList(ids);
        List<TemplateItem> list = rList.getData();
        R<Boolean> result = R.success();
        if(list.size()>0){
            List<Long> idsList = list.stream().map(TemplateItem::getFileId).collect(Collectors.toList());
            attachmentApi.deleteByIds(idsList);
            //修改状态
            List<TemplateItemUpdateDTO> updateList = list.stream().map((template) -> {
                TemplateItemUpdateDTO dto = new TemplateItemUpdateDTO();
                dto.setId(template.getId());
                dto.setFileId(null);
                dto.setItemStatus(1);
                return dto;
            }).collect(Collectors.toList());
            if(list.size()==1){
                templateItemBizApi.update(updateList.get(0));
                result = R.success(true);
            }else{
                result = templateItemBizApi.updateBatch(updateList);
            }
        }
        //R<Boolean> result = templateItemBizApi.delete(ids);
        return result;
    }

    @Override
    public R<List<TemplateItem>> groupByCode(TemplateItem params) {
        List<TemplateItem> list = templateItemBizApi.query(params).getData();
        Map<String,TemplateItem> map = new HashMap<>();
        list.stream().forEach(dto -> {
            String code = dto.getTemplateCode();
            if(!map.containsKey(code)){
                TemplateItem item = new TemplateItem();
                item.setTemplateCode(code);
                item.setTemplateName(dto.getTemplateName());
                map.put(dto.getTemplateCode(),item);
            }
        });
        List<TemplateItem> resultList = map.size()==0? new ArrayList<>(): new ArrayList<>( map.values());
        return R.success(resultList);
    }

    @Override
    public R<String> test(TemplateItem templateItem) {
        TemplateItem result = templateItemBizApi.getById(templateItem.getId()).getData();
        if(null!=result){
            Long fileId = result.getItemStatus()==1? result.getDefaultFileId():result.getFileId();
            R<Attachment> attachmentR = attachmentApi.getById(fileId);
            String url = attachmentR.getData().getUrl();
            return templateItemBizApi.test(url);
        }
        return null;
    }

    @Override
    public R<TemplateRecord> saveTemplateRecord(TemplateRecordSaveDTO saveDTO) {
        String code = saveDTO.getTemplateCode();
        TemplateRecord templateRecord = new TemplateRecord();
        templateRecord.setTemplateCode(code);
        templateRecord.setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
        List<TemplateRecord> list = templateRecordBizApi.query(templateRecord).getData();
        if(list.size()==0){
            return templateRecordBizApi.save(saveDTO);
        }else{
            return R.fail("该类型模板已有配置默认模板");
        }
    }

    @Override
    public R<Boolean> setTemplateDefault(TemplateItem item) {
        Long id = item.getId();
        item.setId(null);
        item.setDefaultStatus(DefaultEnum.NOT_REVIEWED.getStatus());
        List<TemplateItem> list = templateItemBizApi.query(item).getData();
        if(list.size()>0){
            list.get(0).setDefaultStatus(DefaultEnum.APPROVED.getStatus());
        }
        item.setId(id);
        list.add(item);
        List<TemplateItemUpdateDTO> updateList = list.stream().map(dto-> {
            TemplateItemUpdateDTO updateDTO = BeanUtil.toBean(dto, TemplateItemUpdateDTO.class);
            updateDTO.setId(dto.getId());
          return updateDTO;
        }).collect(Collectors.toList());
        return templateItemBizApi.updateBatch(updateList);
    }

    @Override
    public R<List<Object>> listAll(Template params) {
        if(params.getDeleteStatus()==null){
            params.setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
        }
        List<Template> list = templateBizApi.query(params).getData();
        List<TemplateVo> listResult = list.stream().map(e -> {
            TemplateVo b = BeanUtil.toBean(e, TemplateVo.class);
            return b;
        }).collect(Collectors.toList());
        Map<Long,TemplateVo> map = listResult.stream().filter(item -> null==item.getParentId()).collect(Collectors.toMap(TemplateVo::getId, a -> a,(k1,k2)->k1));
        listResult.stream().forEach(dto -> {
            Long parentId = dto.getParentId();
            if(null!=parentId){
                TemplateVo template = map.get(parentId);
                if(null!=template){
                    template.getChildren().add(dto);
                }
            }
        });
        List<Object> list1 = Arrays.asList(map.values().toArray());
        return R.success(list1);
    }
}
