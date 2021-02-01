package com.ruoyi.wutest;

import org.springframework.stereotype.Component;

@Component
public class slowCar implements car {

    @Override
    public int speed() {
        return 11;
    }
}
