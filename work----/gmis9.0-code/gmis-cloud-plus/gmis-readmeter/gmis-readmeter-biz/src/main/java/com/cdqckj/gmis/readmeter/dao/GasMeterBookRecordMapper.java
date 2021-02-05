package com.cdqckj.gmis.readmeter.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;

import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.vo.GasMeterBookRecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 抄表册关联客户
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Repository
public interface GasMeterBookRecordMapper extends SuperMapper<GasMeterBookRecord> {
    @Select("select * from cb_gas_meter_book_record ${ew.customSqlSegment}")
    List<GasMeterBookRecord> selectAll(@Param(Constants.WRAPPER) Wrapper<GasMeterBookRecord> wrapper);

    IPage<GasMeterBookRecordVo> pageBookRecord(IPage<GasMeterBookRecordVo> page, @Param("parmes") GasMeterBookRecordPageDTO record);
}
