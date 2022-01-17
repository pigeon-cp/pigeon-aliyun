package com.github.taccisum.pigeon.ext.aliyun.repo.factory.message;

import com.github.taccisum.pigeon.core.entity.core.Message;
import com.github.taccisum.pigeon.core.repo.factory.MessageFactory;
import com.github.taccisum.pigeon.ext.aliyun.entity.message.AliCloudMail;
import com.github.taccisum.pigeon.ext.aliyun.entity.message.AliCloudSMS;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class AliCloudMailMessageFactory implements MessageFactory {
    @Override
    public Message create(Long id, Criteria criteria) {
        switch (criteria.getType()) {
            case "MAIL":
                return new AliCloudMail(id);
            case "SMS":
                return new AliCloudSMS(id);
            default:
                throw new UnsupportedOperationException(criteria.getType());
        }
    }

    @Override
    public boolean match(Long id, Criteria o) {
        return "ALI_CLOUD".equals(o.getSpType());
    }
}
