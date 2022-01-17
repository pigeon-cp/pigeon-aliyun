package com.github.taccisum.pigeon.ext.aliyun.spring;

import com.github.taccisum.pigeon.ext.aliyun.AliCloudPlugin;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/1/14
 */
@Configuration
@ComponentScan(basePackageClasses = AliCloudPlugin.class)
public class SpringConfiguration {
}
