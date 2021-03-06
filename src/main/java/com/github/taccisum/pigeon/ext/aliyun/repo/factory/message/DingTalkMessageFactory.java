package com.github.taccisum.pigeon.ext.aliyun.repo.factory.message;

import pigeon.core.entity.core.Message;
import pigeon.core.repo.Factory;
import pigeon.core.repo.factory.MessageFactory;
import com.github.taccisum.pigeon.ext.aliyun.entity.message.DingRobotLink;
import com.github.taccisum.pigeon.ext.aliyun.entity.message.DingRobotMarkDown;
import com.github.taccisum.pigeon.ext.aliyun.entity.message.DingRobotText;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class DingTalkMessageFactory implements MessageFactory {
    @Override
    public Message create(Long id, Criteria criteria) {
        switch (criteria.getType()) {
            case "DING-ROBOT-MD":
                return new DingRobotMarkDown(id);
            case "DING-ROBOT-TEXT":
                return new DingRobotText(id);
            case "DING-ROBOT-LINK":
                return new DingRobotLink(id);
            default:
                throw new Factory.CreateEntityException("pigeon-aliyun", id, criteria, MessageFactory.class);
        }
    }

    @Override
    public boolean match(Long id, Criteria o) {
        return SpType.DING_TALK.match(o.getSpType());
    }
}
