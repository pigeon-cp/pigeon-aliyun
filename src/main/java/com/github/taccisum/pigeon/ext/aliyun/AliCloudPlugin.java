package com.github.taccisum.pigeon.ext.aliyun;

import com.github.taccisum.pigeon.ext.aliyun.spring.SpringConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Pigeon 阿里云集成插件
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/1/13
 */
@Slf4j
public class AliCloudPlugin extends SpringPlugin {
    public AliCloudPlugin(PluginWrapper wrapper) {
        super(wrapper);

        // you can use "wrapper" to have access to the plugin context (plugin manager, descriptor, ...)
    }

    @Override
    public void start() {
        log.info("{} start.", this.getClass().getSimpleName());
    }

    @Override
    public void stop() {
        log.info("{} stop.", this.getClass().getSimpleName());
    }

    @Override
    public void delete() {
        log.info("{} delete.", this.getClass().getSimpleName());
    }

    @Override
    protected ApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.setClassLoader(getWrapper().getPluginClassLoader());
        context.register(SpringConfiguration.class);
        context.refresh();
        return context;
    }
}
