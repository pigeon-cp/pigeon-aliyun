package com.github.taccisum.pigeon.ext.aliyun.repo.factory.mass;

import com.github.taccisum.pigeon.core.entity.core.Message;
import com.github.taccisum.pigeon.core.entity.core.MessageMass;
import com.github.taccisum.pigeon.core.repo.factory.MessageMassFactory;
import com.github.taccisum.pigeon.ext.aliyun.entity.mass.AliCloudSMSMass;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.2
 */
@Extension
public class AliCloudMessageMassFactory implements MessageMassFactory {
    @Override
    public MessageMass create(Long id, Criteria criteria) {
        switch (criteria.getMessageType()) {
            case Message.Type.SMS:
                return new AliCloudSMSMass(id);
            default:
                throw new UnsupportedOperationException(criteria.getType());
        }
    }

    @Override
    public boolean match(Long id, Criteria criteria) {
        return SpType.ALI_CLOUD.match(criteria.getSpType()) && "PARTITION".equals(criteria.getType());
    }
}
