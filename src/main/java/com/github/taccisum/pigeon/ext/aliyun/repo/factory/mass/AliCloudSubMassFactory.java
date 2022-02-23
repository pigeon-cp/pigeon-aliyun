package com.github.taccisum.pigeon.ext.aliyun.repo.factory.mass;

import com.github.taccisum.pigeon.ext.aliyun.entity.mass.AliCloudMailSubMass;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;
import pigeon.core.entity.core.Message;
import pigeon.core.entity.core.SubMass;
import pigeon.core.repo.factory.SubMassFactory;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.2
 */
@Extension
public class AliCloudSubMassFactory implements SubMassFactory {
    @Override
    public SubMass create(Long id, Criteria criteria) {
        switch (criteria.getMessageType()) {
            case Message.Type.MAIL:
                return new AliCloudMailSubMass(id);
            default:
                throw new UnsupportedOperationException(criteria.getSpType());
        }
    }

    @Override
    public boolean match(Long id, Criteria criteria) {
        return SpType.ALI_CLOUD.match(criteria.getSpType());
    }
}
