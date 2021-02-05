package com.cdqckj.gmis.pay.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class StrategyFactory {

    @Autowired
    private Map<String , PayStrategy> payMap;

    public PayStrategy create(String payCode){
        return payMap.get(payCode);
    }

}