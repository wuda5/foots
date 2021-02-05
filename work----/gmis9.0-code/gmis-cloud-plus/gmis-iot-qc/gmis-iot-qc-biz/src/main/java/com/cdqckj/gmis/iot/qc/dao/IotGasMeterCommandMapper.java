package com.cdqckj.gmis.iot.qc.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * Mapper 接口
 * 物联网气表指令数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Repository
public interface IotGasMeterCommandMapper extends SuperMapper<IotGasMeterCommand> {

    /**
     * 查询指令列表
     * @param page
     * @param params
     * @return
     */
    Page<CommandEchoVO> queryCommand(Page<CommandEchoVO> page, @Param("params") CommandVO params);
}
