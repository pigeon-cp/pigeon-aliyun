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
public class AliCloudMessageTemplateFactory extends MessageTemplateFactory.Base {
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
    public CriteriaSet<MessageTemplateFactory.Criteria> getCriteriaSet() {
        return new CriteriaSet.Any<MessageTemplateFactory.Criteria>()
                .add(new MessageTemplateFactory.Criteria(Message.Type.MAIL, SpType.ALI_CLOUD.name()).setDesc("阿里云邮件消息"))
                .add(new MessageTemplateFactory.Criteria(Message.Type.SMS, SpType.ALI_CLOUD.name()).setDesc("阿里云短信消息"))
                ;
    }

    @Override
    public int getOrder() {
        return 99;
    }
}
