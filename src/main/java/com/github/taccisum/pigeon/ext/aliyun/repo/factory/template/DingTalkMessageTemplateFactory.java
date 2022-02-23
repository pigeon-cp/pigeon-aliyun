package com.github.taccisum.pigeon.ext.aliyun.repo.factory.template;

import com.github.taccisum.pigeon.ext.aliyun.entity.template.DingRobotMessageTemplate;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;
import pigeon.core.entity.core.MessageTemplate;
import pigeon.core.repo.factory.MessageTemplateFactory;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class DingTalkMessageTemplateFactory extends MessageTemplateFactory.Base {
    @Override
    public MessageTemplate create(Long id, MessageTemplateFactory.Criteria criteria) {
        switch (criteria.getType()) {
            case "DING-ROBOT-MD":
            case "DING-ROBOT-TEXT":
            case "DING-ROBOT-LINK":
                return new DingRobotMessageTemplate(id);
            default:
                throw new UnsupportedOperationException(criteria.getType());
        }
    }

    @Override
    public boolean match(Long id, MessageTemplateFactory.Criteria criteria) {
        // TODO::
        return SpType.DING_TALK.match(criteria.getSpType());
    }

    @Override
    public CriteriaSet<MessageTemplateFactory.Criteria> getCriteriaSet() {
        return new CriteriaSet.Any<MessageTemplateFactory.Criteria>()
                .add(new MessageTemplateFactory.Criteria("DING-ROBOT-MD", SpType.DING_TALK.name()).setDesc("钉钉机器人 markdown 消息"))
                .add(new MessageTemplateFactory.Criteria("DING-ROBOT-TEXT", SpType.DING_TALK.name()).setDesc("钉钉机器人 text 消息"))
                .add(new MessageTemplateFactory.Criteria("DING-ROBOT-LINK", SpType.DING_TALK.name()).setDesc("钉钉机器人 link 消息"))
                ;
    }

    @Override
    public int getOrder() {
        return 99;
    }
}
