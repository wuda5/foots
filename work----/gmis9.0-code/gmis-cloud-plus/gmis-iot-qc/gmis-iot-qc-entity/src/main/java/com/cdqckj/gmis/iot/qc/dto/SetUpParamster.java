package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SetUpParamster<T> implements Serializable {
    public String paraType;
    public T Parameter;
}
