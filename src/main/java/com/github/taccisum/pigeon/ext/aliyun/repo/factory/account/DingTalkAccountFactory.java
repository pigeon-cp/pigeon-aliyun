package com.github.taccisum.pigeon.ext.aliyun.repo.factory.account;

import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import com.github.taccisum.pigeon.core.repo.factory.ThirdAccountFactory;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.DingTalkRobot;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import org.pf4j.Extension;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Extension
public class DingTalkAccountFactory implements ThirdAccountFactory {
    @Override
    public ThirdAccount create(Long id, Criteria criteria) {
        if (criteria.getUsername().startsWith("robot:")) {
            return new DingTalkRobot(id);
        }

        throw new UnsupportedOperationException(criteria.getUsername());
    }

    @Override
    public boolean match(Long id, Criteria o) {
        return SpType.DING_TALK.match(o.getSpType());
    }
}
