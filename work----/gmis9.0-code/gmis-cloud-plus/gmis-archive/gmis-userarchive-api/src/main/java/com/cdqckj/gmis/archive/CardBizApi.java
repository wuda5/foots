package com.cdqckj.gmis.archive;

import com.cdqckj.gmis.archive.hystrix.CardBizApiFallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.sim.dto.CardSaveDTO;
import com.cdqckj.gmis.sim.dto.CardUpdateDTO;
import com.cdqckj.gmis.sim.entity.Card;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallback = CardBizApiFallback.class
        , path = "/card", qualifier = "cardBizApi")
public interface CardBizApi {
    @RequestMapping(method = RequestMethod.POST)
    R<Card> saveCard(@RequestBody CardSaveDTO saveDTO);
    @RequestMapping(method = RequestMethod.PUT)
    R<Card> updateCard(@RequestBody CardUpdateDTO updateDTO);
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> deleteCard(@RequestParam("ids[]") List<Long> id);
    @RequestMapping(method = RequestMethod.GET)
    R<List<Card>>findCard(@RequestBody Card card);
    @PostMapping(value = "findCardByCustomcode")
     R<Card> findCardByCustomcode(@RequestParam("code") String code);

    @DeleteMapping(value = "/logicalDelete")
    //@PreAuth("hasPermit('{}logicalDelete')")
     R<Card> logicalDelete(@RequestParam("ids[]") List<Id> ids) throws Exception;

}
