import com.cdqckj.gmis.generator.CodeGenerator;
import com.cdqckj.gmis.generator.config.CodeGeneratorConfig;
import com.cdqckj.gmis.generator.config.FileCreateConfig;
import com.cdqckj.gmis.generator.type.EntityFiledType;
import com.cdqckj.gmis.generator.type.EntityType;
import com.cdqckj.gmis.generator.type.GenerateType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 测试代码生成权限系统的代码
 *
 * @author gmis
 * @date 2019/05/25
 */
public class TestAuthorityGenerator {
    /***
     * 注意，想要在这里直接运行，需要手动增加 mysql 驱动
     * @param args
     */
    public static void main(String[] args) {
//        CodeGeneratorConfig build = buildDefaultsEntity();
//        CodeGeneratorConfig build = buildAuthSuperEntity();
 //       CodeGeneratorConfig build = buildAuthEntity();
 //        CodeGeneratorConfig build = buildCommonEntity();
         CodeGeneratorConfig build = buildCommonSuperEntity();
//        CodeGeneratorConfig build = buildCoreEntity();

        build.setUsername("root");
        build.setPassword("123456");
        System.out.println("输出路径：");
        System.out.println(System.getProperty("user.dir") + "/gmis-authority");
        build.setProjectRootPath(System.getProperty("user.dir") + "/gmis-authority");

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

        //手动指定枚举类 生成的路径
//        Set<EntityFiledType> filedTypes = new HashSet<>();
//        filedTypes.addAll(Arrays.asList(
//                EntityFiledType.builder().name("httpMethod").table("c_common_opt_log")
//                        .packagePath("com.cdqckj.gmis.common.enums.HttpMethod").gen(GenerateType.IGNORE).build()
//                , EntityFiledType.builder().name("httpMethod").table("c_auth_resource")
//                        .packagePath("com.cdqckj.gmis.common.enums.HttpMethod").gen(GenerateType.IGNORE).build()
//                , EntityFiledType.builder().name("dsType").table("c_auth_role")
//                        .packagePath("com.cdqckj.gmis.database.mybatis.auth.DataScopeType").gen(GenerateType.IGNORE).build()
//        ));
//        build.setFiledTypes(filedTypes);
        CodeGenerator.run(build);
    }

    public static CodeGeneratorConfig buildDefaultsEntity() {
        List<String> tables = Arrays.asList(
                "d_global_user",
                "d_tenant"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "gmis", "d_", tables);
        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("defaults");
        build.setUrl("jdbc:mysql://127.0.0.1:3306/gmis_defaults?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    private static CodeGeneratorConfig buildAuthEntity() {
        List<String> tables = Arrays.asList(
                  "c_common_lang"
//                "c_auth_user_token"
//                "c_auth_application"
//                , "c_auth_application_system_api"
//                , "c_auth_system_api"
//                "c_auth_menu"
//                , "c_auth_resource"
//                "c_auth_role"
//                "c_auth_user"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "gmis", "c_common_", tables);
//                build("authority", "", "gmis", "c_auth_", tables);
        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("common");
        build.setUrl("jdbc:mysql://127.0.0.1:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    public static CodeGeneratorConfig buildAuthSuperEntity() {
        List<String> tables = Arrays.asList(
                "c_auth_role_authority"
                , "c_auth_role_org"
                , "c_auth_user_role"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "gmis", "c_auth_", tables);
        build.setSuperEntity(EntityType.SUPER_ENTITY);
        build.setChildPackageName("auth");
        build.setUrl("jdbc:mysql://127.0.0.1:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    private static CodeGeneratorConfig buildCommonEntity() {
        List<String> tables = Arrays.asList(
//                "c_common_area"
                "c_common_lang"
//                "c_common_parameter"
//                , "c_common_dictionary"
//                , "c_common_dictionary_item"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "gmis", "m_", tables);

//        build.setSuperEntity(EntityType.TREE_ENTITY);
        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("common");
        build.setUrl("jdbc:mysql://127.0.0.1:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    public static CodeGeneratorConfig buildCommonSuperEntity() {
        List<String> tables = Arrays.asList(
//                "c_common_opt_log"
                "c_common_lang"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "gmis", "c_common_", tables);
        build.setSuperEntity(EntityType.SUPER_ENTITY);
        build.setChildPackageName("common");
        build.setUrl("jdbc:mysql://127.0.0.1:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    public static CodeGeneratorConfig buildCoreEntity() {
        List<String> tables = Arrays.asList(
                "c_core_org"
                , "c_core_station"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "gmis", "c_core_", tables);
        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("core");
        build.setUrl("jdbc:mysql://127.0.0.1:3306/gmis_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }
}
