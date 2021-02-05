package com.cdqckj.gmis.calculate.vo;

import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommondCaluteVO implements Serializable {
    private IotGasMeterCommand iotGasMeterCommand;
    private IotGasMeterCommandDetail iotGasMeterCommandDetail;
}
