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

public class TestConfigureGenerator {
    /***
     * 注意，想要在这里直接运行，需要手动增加 mysql 驱动
     * @param args
     */
    public static void main(String[] args) {
//        CodeGeneratorConfig build = buildHeheEntity();
        CodeGeneratorConfig build = buildConfigureEntity();

        //mysql 账号密码
        build.setUsername("root");
        build.setPassword("123456");

        System.out.println("输出路径：");
        System.out.println(System.getProperty("user.dir") + "/gmis-configure");
        build.setProjectRootPath(System.getProperty("user.dir") + "/gmis-configure");

//        FileCreateConfig fileCreateConfig = new FileCreateConfig(null);
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


    public static CodeGeneratorConfig buildHeheEntity() {
        List<String> tables = Arrays.asList(
                "m_wx_app","m_wx_pay"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("configure", "", "gmis", "m_", tables);
        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("");
        build.setUrl("jdbc:mysql://127.0.0.1:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    /**
     * @return
     */
    private static CodeGeneratorConfig buildConfigureEntity() {
        List<String> tables = Arrays.asList(
                "pz_community","pz_street"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("operateparam",// haha 服务
                        "", // 子模块
                        "gmis", // 作者
                        "pz_", // 表前缀
                        tables);

        // 实体父类
        build.setSuperEntity(EntityType.ENTITY);
//        build.setSuperEntity(EntityType.ENTITY);

        build.setSuperControllerClass(SuperClass.SUPER_CLASS.getController());
        build.setSuperServiceClass(SuperClass.SUPER_CLASS.getService());
        build.setSuperServiceImplClass(SuperClass.SUPER_CLASS.getServiceImpl());
        build.setSuperMapperClass(SuperClass.SUPER_CLASS.getMapper());
//        build.setSuperControllerClass(SuperClass.NONE.getController());
//        build.setSuperServiceClass(SuperClass.NONE.getService());
//        build.setSuperServiceImplClass(SuperClass.NONE.getServiceImpl());
//        build.setSuperMapperClass(SuperClass.NONE.getMapper());
//        build.setSuperControllerClass(SuperClass.SUPER_CACHE_CLASS.getController());
//        build.setSuperServiceClass(SuperClass.SUPER_CACHE_CLASS.getService());
//        build.setSuperServiceImplClass(SuperClass.SUPER_CACHE_CLASS.getServiceImpl());
//        build.setSuperMapperClass(SuperClass.SUPER_CACHE_CLASS.getMapper());

        // 子包名
        build.setChildPackageName("");
        build.setUrl("jdbc:mysql://127.0.0.1:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");

        return build;
    }
}
