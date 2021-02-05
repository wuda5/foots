package com.cdqckj.gmis.systemparam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.api.TenantApi;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.systemparam.dto.TemplateItemPageDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateItemSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateItemUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import com.cdqckj.gmis.systemparam.service.TemplateItemService;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.utils.BizAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.exception.code.ExceptionCode.BASE_VALID_PARAM;


/**
 * <p>
 * 前端控制器
 * 模板项目表（Item）
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/templateItem")
@Api(value = "TemplateItem", tags = "模板项目表（Item）")
@PreAuth(replace = "templateItem:")
public class TemplateItemController extends SuperController<TemplateItemService, Long, TemplateItem, TemplateItemPageDTO, TemplateItemSaveDTO, TemplateItemUpdateDTO> {

    @Autowired
    public TenantApi tenantApi;

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<TemplateItem> templateItemList = list.stream().map((map) -> {
            TemplateItem templateItem = TemplateItem.builder().build();
            //TODO 请在这里完成转换
            return templateItem;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(templateItemList));
    }

    /**
     * 测试
     * @param path
     * @return
     */
    @ApiOperation(value = "测试")
    @PostMapping(value = "/test")
    @SysLog(value = "测试", request = false)
    public R<String> test(@RequestParam("path") String path){
        String result ="";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(60000);
            InputStream inputStream = conn.getInputStream();
            if(null!=inputStream){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    result +=lineTxt+"\n";
                    System.out.println(lineTxt);
                }
                read.close();
            }else{
                R.fail(getLangMessage(MessageConstants.FILE_VERIFY_EXIST));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.fail(getLangMessage(MessageConstants.FILE_PARSING_ERROR));
        }
        return R.success(result);
    };

    /**
     * 删除模板详情，同时删除文件信息
     * @param ids
     * @return
     *//*
    @RequestMapping(value = "deleteTemplateItem", method = RequestMethod.DELETE)
    public R<Boolean> deleteTemplateItem(@RequestParam("ids[]") List<Long> ids) {
        List<TemplateItem> list = null;//queryList(ids).getData();
        R<Boolean> result = handlerDelete(ids);
        *//*if (result.getDefExec()) {
            getBaseService().removeByIds(ids);
            if(list.size()>0){
                List<Long> fileList = list.stream().map(TemplateItem:: getFileId).collect(Collectors.toList());
                attachmentApi.delete(fileList);
            }
        }*//*
        return result;
    }*/

    /**
     * 根据id获取对象
     * @param id      文件id
     * @throws Exception
     */
    @Override
    @ApiOperation(value = "根据id获取对象", notes = "根据id获取对象")
    @PostMapping(value = "/getById")
    @SysLog("根据id获取对象")
    public R<TemplateItem> getById(
            @ApiParam(name = "id", value = "文件id")
            @RequestParam(value = "id") Long id) {
        BizAssert.isTrue(id!=null, BASE_VALID_PARAM.build("id不能为空","TemplateItem ID cannot be empty"));
        return R.success(baseService.getById(id));
    };

    @ApiOperation(value = "共享模板详情")
    @RequestMapping(value = "/share", method = RequestMethod.POST)
    R<TemplateItem> share(@RequestBody TemplateItem saveDTO){
        return baseService.share(saveDTO);
    };

    @ApiOperation(value = "平台管理员审核")
    @RequestMapping(value = "/adminExamine", method = RequestMethod.POST)
    R<Boolean> adminExamine(@RequestBody List<TemplateItem> list){
        return baseService.adminExamine(list);
    };

    @ApiOperation(value = "模板下发")
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    R<Boolean> publish(@RequestBody List<TemplateItem> list){
        List<Tenant> tenantList = tenantApi.all().getData();
        tenantList.stream().forEach(dto -> {
            String code = dto.getCode();
            list.stream().forEach(item ->{
                String updateCode = item.getCompanyCode();
                if(updateCode==null || !updateCode.equals(code)){
                    item.setId(null);
                    item.setCompanyCode(code);
                }
            });
            //切换数据库
            BaseContextHandler.setTenant(code);
            baseService.publish(list).getData();
        });
        return R.success();
    };

    @ApiOperation(value = "分页查询公共模板")
    @RequestMapping(value = "/pageAdminTemplate", method = RequestMethod.POST)
    R<IPage<TemplateItem>> pageAdminTemplate(@RequestBody PageParams<TemplateItemPageDTO> params){
        return baseService.pageAdminTemplate(params);
    };

    @ApiOperation(value = "根据租户编号修改对象数据库下的模板")
    @RequestMapping(value = "/updateByCode", method = RequestMethod.PUT)
    R<Boolean> updateByCode(@RequestBody List<TemplateItem> list,@RequestParam("tenantCode") String code){
        //切换数据库
        BaseContextHandler.setTenant(code);
        baseService.updateBatchById(list);
        return R.success();
    };
}
