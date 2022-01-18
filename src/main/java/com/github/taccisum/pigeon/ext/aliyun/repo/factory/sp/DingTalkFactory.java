package com.github.taccisum.pigeon.ext.aliyun.repo.factory.sp;

import com.github.taccisum.pigeon.core.entity.core.ServiceProvider;
import com.github.taccisum.pigeon.core.repo.factory.ServiceProviderFactory;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.DingTalk;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class DingTalkFactory implements ServiceProviderFactory {
    @Override
    public ServiceProvider create(String id, Criteria o) {
        return new DingTalk();
    }

    @Override
    public boolean match(String id, Criteria o) {
        return SpType.DING_TALK.match(o.getSpType());
    }
}
