package com.github.taccisum.pigeon.ext.aliyun.repo.factory.account;

import pigeon.core.entity.core.ThirdAccount;
import pigeon.core.repo.factory.ThirdAccountFactory;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.AliCloudAccount;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;

import java.util.Objects;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class AliCloudAccountFactory implements ThirdAccountFactory {
    @Override
    public ThirdAccount create(Long id, Criteria criteria) {
        return new AliCloudAccount(id);
    }

    @Override
    public boolean match(Long id, Criteria o) {
        return SpType.ALI_CLOUD.match(o.getSpType());
    }
}
