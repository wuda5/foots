package com.cdqckj.gmis.statistics.domain.log.process;

import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.iot.qc.entity.IotAlarm;
import com.cdqckj.gmis.statistics.domain.log.FormatRowData;
import com.cdqckj.gmis.statistics.domain.log.ProcessTypeEnum;
import com.cdqckj.gmis.statistics.domain.log.SysServiceBean;
import com.cdqckj.gmis.statistics.domain.log.TableInsertLog;
import com.cdqckj.gmis.statistics.domain.log.util.DateStsUtil;
import com.cdqckj.gmis.statistics.domain.log.util.EntityFieldUtil;
import com.cdqckj.gmis.statistics.entity.MeterUploadStsNow;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 * @author: lijianguo
 * @time: 2020/11/13 15:04
 * @remark: 报警
 */
public class IotAlarmLog implements TableInsertLog<IotAlarm> {

    @ApiModelProperty(value = "对应的类的名字")
    private Class className;

    @ApiModelProperty(value = "系统的服务类")
    private SysServiceBean sysServiceBean;

    public IotAlarmLog(Class className, SysServiceBean sysServiceBean) {
        this.className = className;
        this.sysServiceBean = sysServiceBean;
    }

    @Override
    public Class getEntityClass() {
        return className;
    }

    @Override
    public Boolean logNeedProcess(FormatRowData<IotAlarm> formatRowData, ProcessTypeEnum typeEnum) {
        return true;
    }

    @Override
    public Boolean insertLog(FormatRowData<IotAlarm> formatRowData) {

        IotAlarm after = formatRowData.getAfterRowValue();
        GasMeter gasMeter = sysServiceBean.getGasMeterBizApi().findOnLineMeterByNumber(after.getDeviceId()).getData();
        if (gasMeter == null){
            return true;
        }
        MeterUploadStsNow logData = new MeterUploadStsNow();
        logData.setTCode(BaseContextHandler.getTenant());
        logData.setCompanyCode(gasMeter.getCompanyCode());
        logData.setOrgId(gasMeter.getOrgId());
        logData.setBusinessHallId(sysServiceBean.getBusinessHallId(gasMeter.getOrgId()));
        logData.setCreateUserId(gasMeter.getCreateUser());
        logData.setDeviceId(after.getDeviceId());
        logData.setType(2);
        logData.setAlarmType(after.getAlarmType());
        logData.setStsDay(DateStsUtil.dayBeginTime(after.getCreateTime()));

        String sql = EntityFieldUtil.searchSameRecordSql(logData, Arrays.asList("uploadTimes", "create_user_id"));
        MeterUploadStsNow recordData = sysServiceBean.getMeterUploadStsNowService().getNowRecord(sql);
        if (recordData == null){

            recordData = logData;
            recordData.setUploadTimes(1);
            sysServiceBean.getMeterUploadStsNowService().save(recordData);
        }else {
            recordData.setUploadTimes(recordData.getUploadTimes() + 1);
            sysServiceBean.getMeterUploadStsNowService().updateById(recordData);
        }
        return true;
    }
}
