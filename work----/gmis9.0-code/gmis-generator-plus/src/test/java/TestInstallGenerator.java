import com.cdqckj.gmis.generator.CodeGenerator;
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
public class TestInstallGenerator {
    /***
     * 注意，想要在这里直接运行，需要手动增加 mysql 驱动
     * @param args
     */
    public static void main(String[] args) {

         CodeGeneratorConfig build = buildCommonSuperEntity();

        build.setUsername("root");
        build.setPassword("123456");
        System.out.println("输出路径：");
        System.out.println(System.getProperty("user.dir") + "/gmis-cloud-plus/gmis-operations");
        build.setProjectRootPath(System.getProperty("user.dir") + "/gmis-cloud-plus/gmis-operations");

        // null 表示 使用下面的 生成策略
       // FileCreateConfig fileCreateConfig = new FileCreateConfig(null);
        // 不为null 表示忽略下面的 生成策略
        FileCreateConfig fileCreateConfig = new FileCreateConfig(GenerateType.IGNORE);

        //实体类的生成策略 为覆盖
        fileCreateConfig.setGenerateEntity(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateEnum(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateDto(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateXml(GenerateType.OVERRIDE);
        //dao 的生成策略为 忽略
        fileCreateConfig.setGenerateDao(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateServiceImpl(GenerateType.IGNORE);
        fileCreateConfig.setGenerateService(GenerateType.IGNORE);
        fileCreateConfig.setGenerateController(GenerateType.IGNORE);
        build.setFileCreateConfig(fileCreateConfig);
        String str="1012120000";
        System.out.println(str.substring(0,6));

//        CodeGenerator.run(build);
    }


    public static CodeGeneratorConfig buildCommonSuperEntity() {
        List<String> tables = Arrays.asList(
                "gc_install_detail",
                "gc_install_order_record",
                "gc_install_record",
                "gc_gas_install_file",
                "gc_install_charge_detail",
                "gc_install_process_record"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("install", "", "tp", "gc_", tables);
        build.setSuperEntity(EntityType.SUPER_ENTITY);
        build.setChildPackageName("");
        build.setUrl("jdbc:mysql://172.16.92.250:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }
}
