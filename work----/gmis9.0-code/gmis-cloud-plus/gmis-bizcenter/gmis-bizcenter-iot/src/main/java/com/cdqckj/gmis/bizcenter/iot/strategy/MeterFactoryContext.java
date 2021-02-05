package com.cdqckj.gmis.bizcenter.iot.strategy;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.iot.qc.vo.ValveControlVO;
import org.springframework.stereotype.Component;

import java.util.List;

public class MeterFactoryContext {
    //持有一个具体的strategy
    private MeterStrategy strategy;

    public MeterFactoryContext(MeterStrategy strategy){
        this.strategy = strategy;
    }

    /**
     * 阀控操作
     * @param valveControlList
     * @return
     */
    public IotR valveControl(List<ValveControlVO> valveControlList){
        return strategy.valveControl(valveControlList);
    }
}
