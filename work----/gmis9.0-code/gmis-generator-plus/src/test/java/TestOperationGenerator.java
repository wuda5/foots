import com.cdqckj.gmis.generator.CodeGenerator;
import com.cdqckj.gmis.generator.config.CodeGeneratorConfig;
import com.cdqckj.gmis.generator.config.FileCreateConfig;
import com.cdqckj.gmis.generator.type.EntityFiledType;
import com.cdqckj.gmis.generator.type.EntityType;
import com.cdqckj.gmis.generator.type.GenerateType;
import com.cdqckj.gmis.generator.type.SuperClass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 测试代码生成权限系统的代码
 *
 * @author gmis
 * @date 2020/07/21
 */
public class TestOperationGenerator {
    /***
     * 注意，想要在这里直接运行，需要手动增加 mysql 驱动
     * @param args
     */
    public static void main(String[] args) {
        CodeGeneratorConfig build = buildSecurityEntity();

        //mysql 账号密码
        build.setUsername("root");
        build.setPassword("123456");

        System.out.println("输出路径：");
        System.out.println(System.getProperty("user.dir") + "/gmis-readmeter");
        build.setProjectRootPath(System.getProperty("user.dir") + "/gmis-readmeter");

        //生成biz、entity
//        FileCreateConfig fileCreateConfig = new FileCreateConfig(null);
        //生成biz、controller、entity
//         生成全部后端类
        FileCreateConfig fileCreateConfig = new FileCreateConfig(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateEntity(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateEnum(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateDto(GenerateType.IGNORE);
        fileCreateConfig.setGenerateXml(GenerateType.IGNORE);
        fileCreateConfig.setGenerateDao(GenerateType.IGNORE);
        fileCreateConfig.setGenerateServiceImpl(GenerateType.IGNORE);
        fileCreateConfig.setGenerateService(GenerateType.IGNORE);
        fileCreateConfig.setGenerateController(GenerateType.IGNORE);
        build.setFileCreateConfig(fileCreateConfig);

        //手动指定枚举类 生成的路径
        Set<EntityFiledType> filedTypes = new HashSet<>();
        filedTypes.addAll(Arrays.asList(
//                EntityFiledType.builder().name("httpMethod").table("c_common_opt_log")
//                        .packagePath("com.cdqckj.gmis.common.enums.HttpMethod").gen(GenerateType.IGNORE).build()
        ));
        build.setFiledTypes(filedTypes);

        build.setPackageBase("com.cdqckj.gmis." + build.getChildModuleName());

        // 运行
        CodeGenerator.run(build);
    }

    private static CodeGeneratorConfig buildSecurityEntity() {
        List<String> tables = Arrays.asList(
                "cb_read_meter_latest_record"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("readmeter",// haha 服务
                        "", // 子模块
                        "gmis", // 作者
                        "cb_", // 表前缀
                        tables);

        // 实体父类
        build.setSuperEntity(EntityType.ENTITY);
        build.setSuperControllerClass(SuperClass.SUPER_CLASS.getController());
        build.setSuperServiceClass(SuperClass.SUPER_CLASS.getService());
        build.setSuperServiceImplClass(SuperClass.SUPER_CLASS.getServiceImpl());
        build.setSuperMapperClass(SuperClass.SUPER_CLASS.getMapper());
        // 子包名
        build.setChildPackageName("");
        build.setUrl("jdbc:mysql://172.16.92.250:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");

        return build;
    }
}
