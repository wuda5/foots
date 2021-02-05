package ${packageBase}.config;

import com.cdqckj.gmis.boot.config.BaseConfig;
import org.springframework.context.annotation.Configuration;
import com.cdqckj.gmis.oauth.api.LogApi;
import com.cdqckj.gmis.log.event.SysLogListener;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;

/**
 * ${description}-Web配置
 *
 * @author ${author}
 * @date ${date}
 */
@Configuration
public class ${service}WebConfiguration extends BaseConfig {

    /**
    * gmis.log.enabled = true 并且 gmis.log.type=DB时实例该类
    *
    * @param optLogService
    * @return
    */
    @Bean
    @ConditionalOnExpression("${r'${'}gmis.log.enabled:true${r'}'} && 'DB'.equals('${r'${'}gmis.log.type:LOGGER${r'}'}')")
    public SysLogListener sysLogListener(LogApi logApi) {
        return new SysLogListener((log) -> logApi.save(log));
    }
}
