package com.cdqckj.gmis.bizcenter.iot.vo;

import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import io.lettuce.core.models.command.CommandDetail;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommandIotVO implements Serializable {
    private IotGasMeterCommand iotGasMeterCommand;
    private IotGasMeterCommandDetail iotGasMeterCommandDetail;
}
