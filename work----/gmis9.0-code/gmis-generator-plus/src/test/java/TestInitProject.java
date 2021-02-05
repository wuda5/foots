import com.cdqckj.gmis.generator.ProjectGenerator;
import com.cdqckj.gmis.generator.config.CodeGeneratorConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 初始化项目 代码
 *
 * @author gmis
 * @date 2019/05/25
 */
@Slf4j
public class TestInitProject {

    public static void main(String[] args) {
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config
                // gmis-admin-cloud 项目的 绝对路径！
                .setProjectRootPath(System.getProperty("user.dir")+"gmis-cloud-plus")
                // 需要新建的 服务名      该例会生成 gmis-haha 服务
                .setServiceName("SecurityChecked")

                // 子模块的设置请参考 消息服务 （msgs 服务下的 sms 模块即 视为子模块）
//                .setChildModuleName("hehe")

                // 生成代码的注释 @author gmis
                .setAuthor("gmis")
                // 项目描述
                .setDescription("安检服务")
                // 项目的版本， 一定要跟 gmis-admin-cloud 下的其他服务版本一致， 否则会出错哦
                .setVersion("c.2.3-SNAPSHOT")
                // 服务的端口号
                .setServerPort("8873")
                // 项目的 groupId
                .setGroupId("com.cdqckj.gmis")
        ;
        // 项目的业务代码 存放的包路径
        config.setPackageBase("com.cdqckj.gmis." + config.getChildModuleName());

        System.out.println("项目初始化根路径：" + config.getProjectRootPath());
        ProjectGenerator pg = new ProjectGenerator(config);
        pg.build();
    }
}
