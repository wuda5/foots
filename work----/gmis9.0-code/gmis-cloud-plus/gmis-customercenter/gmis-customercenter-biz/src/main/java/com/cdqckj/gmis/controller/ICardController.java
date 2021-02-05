package com.cdqckj.gmis.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.entity.dto.CusCardInfoDTO;
import com.cdqckj.gmis.entity.vo.GasMeterICVO;
import com.cdqckj.gmis.service.ICardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 客户缴费管理模块
 * @auther HC
 * @date 2020/10/19
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/iCard")
@Api(value = "iCard",tags = "ICard管理")
public class ICardController {

    @Autowired
    private ICardService iCardService;

    /**
     * 获取IC卡密码
     * @auther hc
     * @param encryptCardInfo IC卡头部数据，16进制，64字节
     * @return
     */
    @ApiOperation(value = "获取ic卡密码")
    @ApiImplicitParam(name = "encryptCardInfo",value = "IC卡头部数据,16进制,64字节",required = true)
    @GetMapping("/password")
    public R<GasMeterICVO> getICCardPWD(@RequestParam("encryptCardInfo") String encryptCardInfo){

        return iCardService.getICCardPWD(encryptCardInfo);
    }


    /**
     * 获取IC卡用户信息
     * @auther hc
     * @param cusCardInfoDTO
     * @return
     */
    @ApiOperation(value = "获取IC卡用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "encryptCardInfo",value = "IC卡整卡数据，16进制;102卡-160字节; 4442卡-256字节",required = true),
            @ApiImplicitParam(name = "cardType",value = "卡类型",required = true)
    })
    @PostMapping("/userInfo")
    public R<HashMap<String,Object>> getUserInfo(@RequestBody  CusCardInfoDTO cusCardInfoDTO ){

        return iCardService.getUserInfo(cusCardInfoDTO.getEncryptCardInfo(),cusCardInfoDTO.getCardType());
    }


    /**
     * 获取写卡数据
     * @auther hc
     * @param encryptCardInfo
     * @param payCode
     * @param cardType
     * @return
     */
    @ApiOperation(value = "获取写卡数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "encryptCardInfo",value = "IC卡整卡数据，16进制;102卡-160字节; 4442卡-256字节",required = true),
            @ApiImplicitParam(name = "cardType",value = "卡类型",required = true),
            @ApiImplicitParam(name = "payCode",value = "订单编号",required = true)
    })
    @GetMapping("/writeData")
    public R<Object> getWriteData(@RequestParam("encryptCardInfo") String encryptCardInfo,
                                  @RequestParam("payCode") String payCode,
                                  @RequestParam("cardType") Integer cardType){

        return iCardService.getWriteData(encryptCardInfo,cardType,payCode);
    }
}
