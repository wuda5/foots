package com.cdqckj.gmis.generator.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SuperClass {

    SUPER_CLASS("com.cdqckj.gmis.base.controller.SuperController", "com.cdqckj.gmis.base.service.SuperService",
            "com.cdqckj.gmis.base.service.SuperServiceImpl", "com.cdqckj.gmis.base.mapper.SuperMapper"),
    SUPER_CACHE_CLASS("com.cdqckj.gmis.base.controller.SuperCacheController", "com.cdqckj.gmis.base.service.SuperCacheService",
            "com.cdqckj.gmis.base.service.SuperCacheServiceImpl", "com.cdqckj.gmis.base.mapper.SuperMapper"),
    NONE("", "", "", "");

    private String controller;
    private String service;
    private String serviceImpl;
    private String mapper;

    public SuperClass setController(String controller) {
        this.controller = controller;
        return this;
    }

    public SuperClass setService(String service) {
        this.service = service;
        return this;
    }

    public SuperClass setMapper(String mapper) {
        this.mapper = mapper;
        return this;
    }

    public SuperClass setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
        return this;
    }
}
