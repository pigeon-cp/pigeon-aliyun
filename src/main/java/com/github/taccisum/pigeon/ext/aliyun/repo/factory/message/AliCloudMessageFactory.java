package com.github.taccisum.pigeon.ext.aliyun.repo.factory.message;

import com.github.taccisum.pigeon.ext.aliyun.entity.message.AliCloudMail;
import com.github.taccisum.pigeon.ext.aliyun.entity.message.AliCloudSMS;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;
import pigeon.core.entity.core.Message;
import pigeon.core.repo.Factory;
import pigeon.core.repo.factory.MessageFactory;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class AliCloudMessageFactory implements MessageFactory {
    @Override
    public Message create(Long id, Criteria criteria) {
        switch (criteria.getType()) {
            case Message.Type.MAIL:
                return new AliCloudMail(id);
            case Message.Type.SMS:
                return new AliCloudSMS(id);
            default:
                throw new Factory.CreateEntityException("pigeon-aliyun", id, criteria, MessageFactory.class);
        }
    }

    @Override
    public boolean match(Long id, Criteria o) {
        return SpType.ALI_CLOUD.match(o.getSpType());
    }
}
