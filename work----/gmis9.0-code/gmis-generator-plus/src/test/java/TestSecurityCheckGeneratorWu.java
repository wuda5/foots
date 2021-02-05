import com.cdqckj.gmis.generator.CodeGenerator;
import com.cdqckj.gmis.generator.ProjectGenerator;
import com.cdqckj.gmis.generator.config.CodeGeneratorConfig;
import com.cdqckj.gmis.generator.config.FileCreateConfig;
import com.cdqckj.gmis.generator.type.EntityType;
import com.cdqckj.gmis.generator.type.GenerateType;

import java.util.Arrays;
import java.util.List;

/**
 * 测试代码生成权限系统的代码
 *
 * @author gmis
 * @date 2019/05/25
 */
public class TestSecurityCheckGeneratorWu {
    /***
     * 注意，想要在这里直接运行，需要手动增加 mysql 驱动
     * @param args
     */
    public static void main(String[] args) {

        // 1.生成项目
        buildProject();
        // 2.生成代码
        generateCode();
    }

    private static void generateCode() {
        CodeGeneratorConfig build = buildCommonSuperEntity();

        build.setUsername("root");
        build.setPassword("123456");
        System.out.println("输出路径：");
        String url = System.getProperty("user.dir") + "/gmis-cloud-plus/gmis-securityed";
//        String url = System.getProperty("user.dir") + "/gmis-cloud-plus";
        System.out.println(url);
        build.setProjectRootPath(url);

        // null 表示 使用下面的 生成策略
        // FileCreateConfig fileCreateConfig = new FileCreateConfig(null);
        // 不为null 表示忽略下面的 生成策略
        FileCreateConfig fileCreateConfig = new FileCreateConfig(GenerateType.OVERRIDE);

        //实体类的生成策略 为覆盖
        fileCreateConfig.setGenerateEntity(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateEnum(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateDto(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateXml(GenerateType.OVERRIDE);
        //dao 的生成策略为 忽略
        fileCreateConfig.setGenerateDao(GenerateType.OVERRIDE);

        fileCreateConfig.setGenerateServiceImpl(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateService(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateController(GenerateType.OVERRIDE);

        build.setFileCreateConfig(fileCreateConfig);

        CodeGenerator.run(build);
    }


    public static CodeGeneratorConfig buildCommonSuperEntity() {
        List<String> tables = Arrays.asList(

                "gc_security_check_record",
                "gc_security_check_process","gc_security_check_result","gc_security_check_"

        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("securityed", "", "tp", "gc_", tables);
        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("");
        build.setUrl("jdbc:mysql://172.16.92.250:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }




    /***
     * 1.构建项目 gmis-operationcenter
     *
     * */
    public static void buildProject(){
        System.out.println("----------项目绝对路径 System.getProperty(\"user.dir\") ："+System.getProperty("user.dir") +"--------------------------");
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config
                // gmis-admin-cloud 项目的 绝对路径！
                /**System.getProperty("user.dir") 的话是生成道外一层了 */
                .setProjectRootPath("D:\\A_work\\gmis9.0-code\\gmis-cloud-plus")
                // 需要新建的 服务名      该例会生成 gmis-haha 服务
                .setServiceName("securityed")

                // 子模块的设置请参考 消息服务 （msgs 服务下的 sms 模块即 视为子模块）
//                .setChildModuleName("read-meter")

                // 生成代码的注释 @author gmis
                .setAuthor("gmis")
                // 项目描述
                .setDescription("报装模块")
                // 项目的版本， 一定要跟 gmis-admin-cloud 下的其他服务版本一致， 否则会出错哦
                .setVersion("c.2.3-SNAPSHOT")
                // 服务的端口号
                .setServerPort("8873")
                // 项目的 groupId
                .setGroupId("com.cdqckj.gmis")
        ;
        // 项目的业务代码 存放的包路径
        config.setPackageBase("com.cdqckj.gmis.securityed");

        System.out.println("项目初始化根路径：" + config.getProjectRootPath());
        ProjectGenerator pg = new ProjectGenerator(config);
        pg.build();
    }
}
