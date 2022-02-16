package com.github.taccisum.pigeon.ext.aliyun.repo.factory.sp;

import pigeon.core.repo.CustomConceptFactory;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.DingTalkOrganization;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class DingTalkOrganizationFactory implements CustomConceptFactory<String, DingTalkOrganization, Object> {
    @Override
    public DingTalkOrganization create(String id, Object o) {
        return new DingTalkOrganization(id);
    }

    @Override
    public boolean match(String id, Object o) {
        return true;
    }
}
