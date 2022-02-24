package com.github.taccisum.pigeon.ext.aliyun.repo.factory.sp;

import com.github.taccisum.pigeon.ext.aliyun.entity.sp.AliCloud;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;
import pigeon.core.entity.core.ServiceProvider;
import pigeon.core.repo.factory.ServiceProviderFactory;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class AliCloudFactory implements ServiceProviderFactory {
    @Override
    public ServiceProvider create(String id, Criteria o) {
        return new AliCloud();
    }

    @Override
    public boolean match(String id, Criteria o) {
        return SpType.ALI_CLOUD.match(o.getSpType());
    }
}
