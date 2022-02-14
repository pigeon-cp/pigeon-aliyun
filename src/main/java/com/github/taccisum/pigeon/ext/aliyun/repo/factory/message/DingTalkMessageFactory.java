package com.github.taccisum.pigeon.ext.aliyun.repo.factory.message;

import com.github.taccisum.pigeon.core.entity.core.Message;
import com.github.taccisum.pigeon.core.repo.factory.MessageFactory;
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
            default:
                throw new UnsupportedOperationException(criteria.getType());
        }
    }

    @Override
    public boolean match(Long id, Criteria o) {
        return SpType.DING_TALK.match(o.getSpType());
    }
}
