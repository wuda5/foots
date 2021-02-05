package com.cdqckj.gmis.biztemporary.controller;

import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.biztemporary.dto.AccountOpenTaskInfoPageDTO;
import com.cdqckj.gmis.biztemporary.dto.AccountOpenTaskInfoSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.AccountOpenTaskInfoUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.AccountOpenTaskInfo;
import com.cdqckj.gmis.biztemporary.service.AccountOpenTaskInfoService;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.utils.BizAssert;
import com.cdqckj.gmis.utils.I18nUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/accountOpenTaskInfo")
@Api(value = "AccountOpenTaskInfo", tags = "导入任务信息")
@PreAuth(replace = "accountOpenTaskInfo:")
public class AccountOpenTaskInfoController extends SuperController<AccountOpenTaskInfoService, Long, AccountOpenTaskInfo, AccountOpenTaskInfoPageDTO, AccountOpenTaskInfoSaveDTO, AccountOpenTaskInfoUpdateDTO> {

    @Autowired
    private AccountOpenTaskInfoService accountOpenTaskInfoService;
    @Resource
    private I18nUtil i18nUtil;
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<AccountOpenTaskInfo> accountOpenTaskInfoList = list.stream().map((map) -> {
            AccountOpenTaskInfo accountOpenTaskInfo = AccountOpenTaskInfo.builder().build();
            //TODO 请在这里完成转换
            return accountOpenTaskInfo;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(accountOpenTaskInfoList));
    }

    /**
     * 加载最后的导入开户任务信息
     * @return 查询结果
     */
    @ApiOperation(value = "加载最后的导入开户任务信息", notes = "加载最后的导入开户任务信息")
    @PostMapping("/queryImportTaskInfo")
    @SysLog("加载最后的导入开户任务信息")
    public R<AccountOpenTaskInfo> queryImportTaskInfo() {

        LbqWrapper<AccountOpenTaskInfo> wrapper = Wraps.<AccountOpenTaskInfo>lbQ().orderByDesc(AccountOpenTaskInfo::getCreateTime);
        List<AccountOpenTaskInfo> accountOpenTaskInfoList = accountOpenTaskInfoService.list(wrapper);
        if(accountOpenTaskInfoList == null || accountOpenTaskInfoList.isEmpty()){
            BizAssert.isTrue(false,
                    i18nUtil.getMessage(MessageConstants.NO_IMPORT_ESTABLISH_ACCOUNT_TASK_INFO));
        }
        return success(accountOpenTaskInfoList.get(0));
    }

}
