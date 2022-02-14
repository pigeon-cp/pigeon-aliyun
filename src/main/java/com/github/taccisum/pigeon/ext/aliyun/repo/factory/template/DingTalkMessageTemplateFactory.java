package com.github.taccisum.pigeon.ext.aliyun.repo.factory.template;

import com.github.taccisum.pigeon.core.entity.core.MessageTemplate;
import com.github.taccisum.pigeon.core.repo.factory.MessageTemplateFactory;
import com.github.taccisum.pigeon.ext.aliyun.entity.template.DingRobotMarkDownTemplate;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class DingTalkMessageTemplateFactory implements MessageTemplateFactory {
    @Override
    public MessageTemplate create(Long id, Criteria criteria) {
        switch (criteria.getType()) {
            case "DING-ROBOT-MD":
                return new DingRobotMarkDownTemplate(id);
            default:
                throw new UnsupportedOperationException(criteria.getType());
        }
    }

    @Override
    public boolean match(Long id, Criteria criteria) {
        return SpType.DING_TALK.match(criteria.getSpType());
    }

    @Override
    public int getOrder() {
        return 99;
    }
}
