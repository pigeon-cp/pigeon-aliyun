package com.github.taccisum.pigeon.ext.aliyun.repo.factory.account;

import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import com.github.taccisum.pigeon.core.repo.factory.ThirdAccountFactory;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.AliCloudAccount;
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
        return Objects.equals(o.getSpType(), "ALI_CLOUD");
    }
}
