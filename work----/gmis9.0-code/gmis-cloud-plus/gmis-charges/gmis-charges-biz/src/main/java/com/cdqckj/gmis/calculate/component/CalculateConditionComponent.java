package com.cdqckj.gmis.calculate.component;

import com.alibaba.fastjson.JSON;
import com.cdqckj.gmis.calculate.vo.CalculateVO;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataIotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class CalculateConditionComponent {

    @Autowired
    private ReadMeterDataIotService readMeterDataIotService;
    /**
     * 物联网表抄表 设置阶梯计费需要的条件
     * @author yangjb
     * @param price 价格方案
     * @param gasMeterInfo 气表使用情况
     * @param gasMeter 气表档案
     * @param meterDataVO 本次抄表数据
     * @param useGasType 用气类型
     * @return
     */
    public CalculateVO calculateCondition(PriceScheme price, GasMeter gasMeter, GasMeterInfo gasMeterInfo,
                                           ReadMeterDataIot meterDataVO, UseGasType useGasType, boolean isCycleClear){
        CalculateVO calculateVO = new CalculateVO();
        //判断是否启用人口递增
        PriceScheme priceScheme = isPopulationAddPrice(price,gasMeter,useGasType);
        //判断是否启用开户递减
        PriceScheme priceSchemeLast = isOpenDecrement(priceScheme,gasMeter,useGasType);
        //确定最终价格方案进行计算
        calculateVO.setPriceScheme(priceSchemeLast);
        //是否调价、周期清零
        calculateVO = isChangePriceClear(price,meterDataVO,calculateVO,gasMeterInfo,isCycleClear);
        calculateVO.setCurrentGas(meterDataVO.getMonthUseGas());
        return calculateVO;
    }
    /**
     * 普表抄表 设置阶梯计费需要的条件
     * @author yangjb
     * @param price 价格方案
     * @param gasMeterInfo 气表使用情况
     * @param gasMeter 气表档案
     * @param meterDataVO 本次抄表数据
     * @param useGasType 用气类型
     * @return
     */
    public CalculateVO calculateCondition(PriceScheme price, GasMeter gasMeter, GasMeterInfo gasMeterInfo,
                                          ReadMeterData meterDataVO, UseGasType useGasType, boolean isCycleClear){
        CalculateVO calculateVO = new CalculateVO();
        //判断是否启用人口递增
        PriceScheme priceScheme = isPopulationAddPrice(price,gasMeter,useGasType);
        //判断是否启用开户递减
        PriceScheme priceSchemeLast = isOpenDecrement(priceScheme,gasMeter,useGasType);
        //确定最终价格方案进行计算
        calculateVO.setPriceScheme(priceSchemeLast);
        //是否调价、周期清零
        calculateVO = isChangePriceClear(price,meterDataVO,calculateVO,gasMeterInfo,isCycleClear);
        calculateVO.setCurrentGas(meterDataVO.getMonthUseGas());
        return calculateVO;
    }

    /**
     * 判断是否启用人口递增
     * @author yangjb
     * @param price
     * @param gasMeter
     * @param useGasType
     * @return
     */
    private PriceScheme isPopulationAddPrice(PriceScheme price,GasMeter gasMeter,UseGasType useGasType){
        //为1标识启用人口递增
        if(useGasType.getPopulationGrowth()==1){
            //根据气表人口数计算递增气量
            Integer cusBaseNum = useGasType.getPopulationBase();
            Integer cusNum = gasMeter.getPopulation();
            if(cusNum>cusBaseNum){
                BigDecimal cus = BigDecimal.valueOf(cusNum-cusBaseNum);
                //每阶梯地增量
                BigDecimal addGas = useGasType.getPopulationIncrease().multiply(cus);
                //设置每个阶梯的地增量
                price.setGas1(price.getGas1().add(addGas));
                if(price.getGas2()!=null){price.setGas2(price.getGas2().add(addGas.multiply(new BigDecimal(2))));}
                if(price.getGas3()!=null){price.setGas3(price.getGas3().add(addGas.multiply(new BigDecimal(3))));}
                if(price.getGas4()!=null){price.setGas4(price.getGas4().add(addGas.multiply(new BigDecimal(4))));}
                if(price.getGas5()!=null){price.setGas5(price.getGas5().add(addGas.multiply(new BigDecimal(5))));}
                if(price.getGas6()!=null){price.setGas6(price.getGas6().add(addGas.multiply(new BigDecimal(6))));}
                log.info("**********************启用人口递增后的价格方案**********************");
                log.info(JSON.toJSONString(price));
            }
        }
        return price;
    }
    /**
     * 是否启用开户递减
     * @author yangjb
     * @param price
     * @param gasMeter
     * @param useGasType
     * @return
     */
    private PriceScheme isOpenDecrement(PriceScheme price,GasMeter gasMeter,UseGasType useGasType){
        if(useGasType.getOpenDecrement()==1&&useGasType.getDecrement()!=null){
            LocalDateTime openDate = gasMeter.getOpenAccountTime();
            int month = (openDate.getYear() - price.getCycleEnableDate().getYear())*12
                    + (openDate.getMonthValue()-price.getCycleEnableDate().getMonthValue());
            int months = month%(price.getCycle());
            if(months>0){
                //设置每个阶梯的递减量
                if(price.getGas1().compareTo((useGasType.getDecrement().multiply(new BigDecimal(months))))>0){
                    price.setGas1(price.getGas1().subtract((useGasType.getDecrement().multiply(new BigDecimal(months)))));
                    BigDecimal disGas2 = (useGasType.getDecrement().multiply(new BigDecimal(months))).multiply(new BigDecimal(2));
                    if(price.getGas2()!=null&&price.getGas2().compareTo(disGas2)>0){
                        price.setGas2(price.getGas2().subtract(disGas2));
                    }
                    BigDecimal disGas3 = (useGasType.getDecrement().multiply(new BigDecimal(months))).multiply(new BigDecimal(3));
                    if(price.getGas3()!=null&&price.getGas3().compareTo(disGas3)>0){
                        price.setGas3(price.getGas3().subtract(disGas3));
                    }
                    BigDecimal disGas4 = (useGasType.getDecrement().multiply(new BigDecimal(months))).multiply(new BigDecimal(4));
                    if(price.getGas4()!=null&&price.getGas4().compareTo(disGas4)>0){
                        price.setGas4(price.getGas4().subtract(disGas4));
                    }
                    BigDecimal disGas5= (useGasType.getDecrement().multiply(new BigDecimal(months))).multiply(new BigDecimal(5));
                    if(price.getGas5()!=null&&price.getGas5().compareTo(disGas5)>0){
                        price.setGas5(price.getGas5().subtract(disGas5));
                    }
                    BigDecimal disGas6= (useGasType.getDecrement().multiply(new BigDecimal(months))).multiply(new BigDecimal(6));
                    if(price.getGas6()!=null&&price.getGas6().compareTo(disGas6)>0){
                        price.setGas6(price.getGas6().subtract(disGas6));
                    }
                }
                log.info("**********************开户按月递减后的价格方案**********************");
                log.info(JSON.toJSONString(price));
            }
        }
        return price;
    }

    /**
     * 是否调价、周期清零
     * @author yangjb
     * @param price
     * @param meterData
     * @param calculateVO
     * @param gasMeterInfo
     * @param isCycleClear
     * @return
     */
    private CalculateVO isChangePriceClear(PriceScheme price,ReadMeterDataIot meterData,CalculateVO calculateVO,
                                           GasMeterInfo gasMeterInfo,boolean isCycleClear){
        int cycle = price.getCycle();
        LocalDate begin = price.getCycleEnableDate();
        LocalDate  djDate = price.getStartTime();
        LocalDate cycleDate =  isClearEx(price,meterData);
        Instant instant = meterData.getRecordTime().toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate recordDate = instant.atZone(zoneId).toLocalDate();
        //是否周期清零
        if(recordDate.isBefore(cycleDate)){
            //调价清零
            calculateVO = cycleClear(meterData,calculateVO,gasMeterInfo,isCycleClear,djDate);
        }else{
            calculateVO = cycleClear(meterData,calculateVO,gasMeterInfo,isCycleClear,cycleDate);
            boolean isCycle = calculateVO.getIsClear();
            if(price.getPriceChangeIsClear()==1){
                //调价清零
                calculateVO = cycleClear(meterData,calculateVO,gasMeterInfo,isCycleClear,djDate);
            }
            boolean isChange = calculateVO.getIsClear();
            if(isCycle==true||isChange==true){
                calculateVO.setIsClear(true);
            }
        }
        return calculateVO;
    }
    /**
     * 是否调价、周期清零
     * @author yangjb
     * @param price
     * @param meterData
     * @param calculateVO
     * @param gasMeterInfo
     * @param isCycleClear
     * @return
     */
    private CalculateVO isChangePriceClear(PriceScheme price,ReadMeterData meterData,CalculateVO calculateVO,
                                           GasMeterInfo gasMeterInfo,boolean isCycleClear){
        int cycle = price.getCycle();
        LocalDate begin = price.getCycleEnableDate();
        LocalDate  djDate = price.getStartTime();
        LocalDate cycleDate =  isClearEx(price,meterData);
        LocalDate recordDate = meterData.getRecordTime();
        //是否周期清零
        if(recordDate.isBefore(cycleDate)){
            //调价清零
            calculateVO = cycleClear(meterData,calculateVO,gasMeterInfo,isCycleClear,djDate);
        }else{
            calculateVO = cycleClear(meterData,calculateVO,gasMeterInfo,isCycleClear,cycleDate);
            boolean isCycle = calculateVO.getIsClear();
            if(price.getPriceChangeIsClear()==1){
                //调价清零
                calculateVO = cycleClear(meterData,calculateVO,gasMeterInfo,isCycleClear,djDate);
            }
            boolean isChange = calculateVO.getIsClear();
            if(isCycle==true||isChange==true){
                calculateVO.setIsClear(true);
            }
        }
        return calculateVO;
    }

    /**
     * @author yangjb
     * @param price
     * @param meterData
     * @return
     */
    private LocalDate isClearEx(PriceScheme price,ReadMeterDataIot meterData){
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String day = Integer.toString(price.getCycleEnableDate().getDayOfMonth());
        if(day.length()==1){day = "0"+day;}
        LocalDate begin = price.getCycleEnableDate();
        Instant instant = meterData.getRecordTime().toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate recordDate = instant.atZone(zoneId).toLocalDate();
        LocalDate end = recordDate;
        int cycle = price.getCycle();
        int months = (end.getYear() - price.getStartTime().getYear())*12 + (end.getMonthValue()-begin.getMonthValue());

        if((months%cycle==0)){
            String month = Integer.toString(recordDate.getMonthValue());
            if(month.length()==1){month = "0"+month;}
            LocalDate cycleDate = LocalDate.parse(recordDate.getYear()+"-"+month+"-"+day,fomatter);
            return cycleDate;
        }else{
            String month = Integer.toString(recordDate.getMonthValue());
            if(month.length()==1){month = "0"+month;}
            LocalDate cycleDate = LocalDate.parse(recordDate.getYear()+"-"+month+"-"+day,fomatter);
            return cycleDate.minusMonths((end.getMonthValue()-begin.getMonthValue()));
        }
    }
    /**
     * @author yangjb
     * @param price
     * @param meterData
     * @return
     */
    private LocalDate isClearEx(PriceScheme price,ReadMeterData meterData){
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String day = Integer.toString(price.getCycleEnableDate().getDayOfMonth());
        if(day.length()==1){day = "0"+day;}
        LocalDate begin = price.getCycleEnableDate();
        LocalDate recordDate = meterData.getRecordTime();
        LocalDate end = recordDate;
        int cycle = price.getCycle();
        int months = (end.getYear() - price.getStartTime().getYear())*12 + (end.getMonthValue()-begin.getMonthValue());

        if((months%cycle==0)){
            String month = Integer.toString(recordDate.getMonthValue());
            if(month.length()==1){month = "0"+month;}
            LocalDate cycleDate = LocalDate.parse(recordDate.getYear()+"-"+month+"-"+day,fomatter);
            return cycleDate;
        }else{
            String month = Integer.toString(recordDate.getMonthValue());
            if(month.length()==1){month = "0"+month;}
            LocalDate cycleDate = LocalDate.parse(recordDate.getYear()+"-"+month+"-"+day,fomatter);
            return cycleDate.minusMonths((end.getMonthValue()-begin.getMonthValue()));
        }
    }

    /**
     * 周期清零
     * @author yangjb
     * @param meterData
     * @param calculateVO
     * @param gasMeterInfo
     * @param isCycleClear
     * @param localDate
     * @return
     */
    private CalculateVO cycleClear(ReadMeterDataIot meterData,CalculateVO calculateVO,
                                   GasMeterInfo gasMeterInfo,boolean isCycleClear,LocalDate localDate){
        //是否调价清零
        LbqWrapper<ReadMeterDataIot> wrapper = new LbqWrapper<>();
        wrapper.eq(ReadMeterDataIot::getProcessStatus, ProcessEnum.SETTLED);
        wrapper.eq(ReadMeterDataIot::getGasMeterCode,meterData.getGasMeterCode());
        wrapper.eq(ReadMeterDataIot::getCustomerCode,meterData.getCustomerCode());
        wrapper.lt(ReadMeterDataIot::getRecordTime,meterData.getRecordTime());
        wrapper.ge(ReadMeterDataIot::getRecordTime,localDate);
        List<ReadMeterDataIot> list = readMeterDataIotService.list(wrapper);
        if(list==null||list.size()==0){
            if(isCycleClear){
                calculateVO.setIsClear(true);
            }else{
                calculateVO.setIsClear(false);
            }
            calculateVO.setHistoryGas(BigDecimal.ZERO);
        }else{
            BigDecimal cycleUseGas = gasMeterInfo.getCycleUseGas();
            calculateVO.setHistoryGas(cycleUseGas);
            calculateVO.setIsClear(false);
        }
        return calculateVO;
    }
    /**
     * 周期清零
     * @author yangjb
     * @param meterData
     * @param calculateVO
     * @param gasMeterInfo
     * @param isCycleClear
     * @param localDate
     * @return
     */
    private CalculateVO cycleClear(ReadMeterData meterData,CalculateVO calculateVO,
                                   GasMeterInfo gasMeterInfo,boolean isCycleClear,LocalDate localDate){
        //是否调价清零
        LbqWrapper<ReadMeterDataIot> wrapper = new LbqWrapper<>();
        wrapper.eq(ReadMeterDataIot::getProcessStatus, ProcessEnum.SETTLED);
        wrapper.eq(ReadMeterDataIot::getGasMeterCode,meterData.getGasMeterCode());
        wrapper.eq(ReadMeterDataIot::getCustomerCode,meterData.getCustomerCode());
        wrapper.lt(ReadMeterDataIot::getRecordTime,meterData.getRecordTime());
        wrapper.ge(ReadMeterDataIot::getRecordTime,localDate);
        List<ReadMeterDataIot> list = readMeterDataIotService.list(wrapper);
        if(list==null||list.size()==0){
            if(isCycleClear){
                calculateVO.setIsClear(true);
            }else{
                calculateVO.setIsClear(false);
            }
            calculateVO.setHistoryGas(BigDecimal.ZERO);
        }else{
            BigDecimal cycleUseGas = gasMeterInfo.getCycleUseGas();
            calculateVO.setHistoryGas(cycleUseGas);
            calculateVO.setIsClear(false);
        }
        return calculateVO;
    }
}
