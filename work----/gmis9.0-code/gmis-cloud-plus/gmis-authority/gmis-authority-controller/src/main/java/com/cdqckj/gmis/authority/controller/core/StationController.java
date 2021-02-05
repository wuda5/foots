package com.cdqckj.gmis.authority.controller.core;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.dto.core.StationPageDTO;
import com.cdqckj.gmis.authority.dto.core.StationSaveDTO;
import com.cdqckj.gmis.authority.dto.core.StationUpdateDTO;
import com.cdqckj.gmis.authority.dto.core.UpdateStatusVO;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.entity.core.Station;
import com.cdqckj.gmis.authority.service.auth.UserService;
import com.cdqckj.gmis.authority.service.core.StationService;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperCacheController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * 岗位
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
@Slf4j
@RestController
@RequestMapping("/station")
@Api(value = "Station", tags = "岗位")
@PreAuth(replace = "station:")
public class StationController extends SuperCacheController<StationService, Long, Station, StationPageDTO, StationSaveDTO, StationUpdateDTO> {
    @Autowired
    private UserService userService;

    @Override
    public void query(PageParams<StationPageDTO> params, IPage<Station> page, Long defSize) {
        baseService.findStationPage(page, params.getModel());
    }

    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<Station> stationList = list.stream().map((map) -> {
            Station item = new Station();
            /*item.setDescribe(map.getOrDefault("描述", ""));
            item.setName(map.getOrDefault("名称", ""));
            item.setOrg(new RemoteData<>(Convert.toLong(map.getOrDefault("组织", ""))));
            item.setStatus(Convert.toBool(map.getOrDefault("状态", "")));*/
            return item;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(stationList));
    }

    /**
     * 重写岗位分页查询方法
     * @auther hc
     * @date 2020/09/04
     * @param params
     * @return
     */
    @Override
    public R<IPage<Station>> page(@Valid PageParams<StationPageDTO> params) {


        return success(baseService.findStationPageEx(params));
    }


    /**
     * 岗位重复性校验
     * @auther hc
     * @param orgId
     * @param name
     * @return
     */
    @ApiOperation("岗位重复性校验")
    @GetMapping("/check")
    public R<Boolean> checkStation(@RequestParam("orgId") @ApiParam(name = "orgId",value = "所属组织id",required = true) @NotNull Long orgId,
                                    @RequestParam("name") @ApiParam(name = "name",value = "岗位名称",required = true) @NotEmpty String name){
        return success(baseService.checkStation(orgId,name));
    }

    /**
     * 批量修改岗位状态
     * @auther hc
     * @return
     */
    @ApiOperation("批量修改岗位状态")
    @PostMapping("/status")
    public R<Boolean> reviseStatus(@RequestBody @Valid UpdateStatusVO updateStatusVO){

        return success(baseService.reviseStatus(updateStatusVO));
    }

    @Override
    public R<Station> update(StationUpdateDTO stationUpdateDTO) {
        //add by hc 2020/09/21 禁用岗位验证其下是否存在用户,否则不能禁用
        if(!stationUpdateDTO.getStatus()) {
            User user = new User();
            RemoteData<Long, String> station = new RemoteData<>();
            station.setKey(stationUpdateDTO.getId());
            user.setStation(station);
            user.setStatus(true);
            List<User> list = userService.findUser(user);
            if(CollectionUtils.isNotEmpty(list)){
                //当前岗位下有用户，不能禁用
                return R.fail(redisService.getLangMessage(MessageConstants.CURRENT_STATION_HAVE_USER));
            }
        }

        return super.update(stationUpdateDTO);
    }
}
