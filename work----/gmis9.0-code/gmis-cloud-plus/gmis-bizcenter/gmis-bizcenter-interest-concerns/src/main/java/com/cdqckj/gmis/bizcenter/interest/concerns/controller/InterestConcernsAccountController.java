package com.cdqckj.gmis.bizcenter.interest.concerns.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songyz
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/interestConcernsAccount")
@Api(value = "interestConcernsAccount", tags = "兴趣关注点-账户")
public class InterestConcernsAccountController {

}
