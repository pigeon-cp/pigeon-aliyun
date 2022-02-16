package com.github.taccisum.pigeon.ext.aliyun;

import pigeon.core.entity.core.PigeonPlugin;
import com.github.taccisum.pigeon.ext.aliyun.spring.SpringConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginWrapper;

/**
 * Pigeon 阿里云集成插件
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class AliCloudPlugin extends PigeonPlugin {
    public AliCloudPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected Class<?> getSpringConfigurationClass() {
        return SpringConfiguration.class;
    }
}
