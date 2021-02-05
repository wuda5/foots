import com.cdqckj.gmis.generator.CodeGenerator;
import com.cdqckj.gmis.generator.ProjectGenerator;
import com.cdqckj.gmis.generator.config.CodeGeneratorConfig;
import com.cdqckj.gmis.generator.config.FileCreateConfig;
import com.cdqckj.gmis.generator.type.EntityType;
import com.cdqckj.gmis.generator.type.GenerateType;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

/**
 * 测试代码生成权限系统的代码
 *
 * @author gmis
 * @date 2019/05/25
 */
public class TestChargeGenerator {
    /***
     * 注意，想要在这里直接运行，需要手动增加 mysql 驱动
     * @param args
     */
    public static void main(String[] args) {
//        buildProject();
        LocalDate from = LocalDate.of(2020,8,1);

        LocalDate now = LocalDate.now();
        Period period = Period.between(from,LocalDate.now());
        System.out.println("2017-09-01到当前日期" + LocalDate.now());
        System.out.println("距离当前多少年：" + period.getYears());
        System.out.println("距离当前多少月：" + period.getMonths());
        System.out.println("距离当前多少日：" + period.getDays());
        from=from
                .plusYears(12)
                .withMonth(from.getMonthValue())
                .withDayOfMonth(from.getDayOfMonth()).minusDays(1);
        System.out.println("距离当前多少日：" + from.toString());

         CodeGeneratorConfig build = buildCommonSuperEntity();

        build.setUsername("root");
        build.setPassword("123456");
        System.out.println("输出路径：");
//        System.out.println(System.getProperty("user.dir") + "/gmis-charges");
        build.setProjectRootPath("D:\\WorkSpace\\gmis9.0-code\\gmis-cloud-plus\\gmis-charges");

        // null 表示 使用下面的 生成策略
        FileCreateConfig fileCreateConfig = new FileCreateConfig(null);
        // 不为null 表示忽略下面的 生成策略
//        FileCreateConfig fileCreateConfig = new FileCreateConfig(GenerateType.OVERRIDE);

        //实体类的生成策略 为覆盖
        fileCreateConfig.setGenerateEntity(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateEnum(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateDto(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateXml(GenerateType.OVERRIDE);
        //dao 的生成策略为 忽略
        fileCreateConfig.setGenerateDao(GenerateType.IGNORE);
        fileCreateConfig.setGenerateServiceImpl(GenerateType.IGNORE);
        fileCreateConfig.setGenerateService(GenerateType.IGNORE);
        fileCreateConfig.setGenerateController(GenerateType.IGNORE);
        fileCreateConfig.setGenerateAPI1(GenerateType.IGNORE);
//        fileCreateConfig.setGenerateAPI1FallBack(GenerateType.IGNORE);
        build.setFileCreateConfig(fileCreateConfig);
        String str="1012120000";
        System.out.println(str.substring(0,6));

        CodeGenerator.run(build);
    }


    public static CodeGeneratorConfig buildCommonSuperEntity() {
        List<String> tables = Arrays.asList(
                "gt_customer_enjoy_activity_record"
//                "gt_charge_record_pay",
//                "gt_customer_account",
//                "gt_customer_account_serial",
//                "gt_gasmeter_arrears_detail"
//                "gt_gasmeter_settlement_detail"
//                "gt_recharge_record",
//                "gt_customer_enjoy_activity_record",
//                "gt_charge_record",
//                "gt_charge_insurance_detail",
//                "gt_charge_item_record"
//                                "gt_charge_insurance_detail"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("charges", "", "tp", "gt_", tables);
        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("");
        build.setUrl("jdbc:mysql://localhost:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }
    public static void buildProject(){
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config
                // gmis-admin-cloud 项目的 绝对路径！
                .setProjectRootPath(System.getProperty("user.dir"))
                // 需要新建的 服务名      该例会生成 gmis-haha 服务
                .setServiceName("charges")

                // 子模块的设置请参考 消息服务 （msgs 服务下的 sms 模块即 视为子模块）
//                .setChildModuleName("hehe")

                // 生成代码的注释 @author gmis
                .setAuthor("gmis")
                // 项目描述
                .setDescription("收费模块")
                // 项目的版本， 一定要跟 gmis-admin-cloud 下的其他服务版本一致， 否则会出错哦
                .setVersion("c.2.3-SNAPSHOT")
                // 服务的端口号
                .setServerPort("8785")
                // 项目的 groupId
                .setGroupId("com.cdqckj.gmis")
        ;
        // 项目的业务代码 存放的包路径
        config.setPackageBase("com.cdqckj.gmis.charges");

        System.out.println("项目初始化根路径：" + config.getProjectRootPath());
        ProjectGenerator pg = new ProjectGenerator(config);
        pg.build();
    }
}
