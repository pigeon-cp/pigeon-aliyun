package com.github.taccisum.pigeon.ext.aliyun.repo.factory.template;

import com.github.taccisum.pigeon.ext.aliyun.entity.template.AliCloudMailTemplate;
import com.github.taccisum.pigeon.ext.aliyun.entity.template.AliCloudSMSTemplate;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;
import pigeon.core.entity.core.Message;
import pigeon.core.entity.core.MessageTemplate;
import pigeon.core.repo.factory.MessageTemplateFactory;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class AliCloudMessageTemplateFactory implements MessageTemplateFactory {
    @Override
    public MessageTemplate create(Long id, MessageTemplateFactory.Criteria criteria) {
        switch (criteria.getType()) {
            case Message.Type.MAIL:
                return new AliCloudMailTemplate(id);
            case Message.Type.SMS:
                return new AliCloudSMSTemplate(id);
            default:
                throw new UnsupportedOperationException(criteria.getType());
        }
    }

    @Override
    public boolean match(Long id, MessageTemplateFactory.Criteria criteria) {
        return "ALI_CLOUD".equals(criteria.getSpType());
    }

    @Override
    public int getOrder() {
        return 99;
    }
}
