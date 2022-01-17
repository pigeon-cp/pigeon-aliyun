package com.github.taccisum.pigeon.ext.aliyun.repo.factory.template;

import com.github.taccisum.pigeon.core.entity.core.MessageTemplate;
import com.github.taccisum.pigeon.core.repo.factory.MessageTemplateFactory;
import com.github.taccisum.pigeon.ext.aliyun.entity.template.AliCloudMailTemplate;
import com.github.taccisum.pigeon.ext.aliyun.entity.template.AliCloudSMSTemplate;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class AliCloudMessageTemplateFactory implements MessageTemplateFactory {
    @Override
    public MessageTemplate create(Long id, Criteria criteria) {
        switch (criteria.getType()) {
            case "MAIL":
                return new AliCloudMailTemplate(id);
            case "SMS":
                return new AliCloudSMSTemplate(id);
            default:
                throw new UnsupportedOperationException(criteria.getType());
        }
    }

    @Override
    public boolean match(Long id, Criteria criteria) {
        return "ALI_CLOUD".equals(criteria.getSpType());
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
