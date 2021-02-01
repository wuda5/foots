package com.ruoyi.wutest;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
//@Service
public class superCar implements car {

    @Override
    public int speed() {
        return 99999;
    }
}
