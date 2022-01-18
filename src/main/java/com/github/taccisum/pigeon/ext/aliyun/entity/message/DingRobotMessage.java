package com.github.taccisum.pigeon.ext.aliyun.entity.message;

import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.core.entity.core.Message;
import com.github.taccisum.pigeon.core.entity.core.ServiceProvider;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.DingTalk;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.DingTalkRobot;

/**
 * 钉钉机器人消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public abstract class DingRobotMessage extends Message {
    public DingRobotMessage(Long id) {
        super(id);
    }

    @Override
    protected boolean isRealTime() {
        // 钉钉消息基本都是实时消息
        return true;
    }

    @Override
    protected DingTalk getServiceProvider() {
        ServiceProvider sp = this.serviceProviderRepo.get(this.data().getSpType());
        if (sp instanceof DingTalk) {
            return (DingTalk) sp;
        }
        throw new DataErrorException("DingRobotMessage.ServiceProvider", this.id(), "钉钉机器人消息可能关联了错误的服务提供商：" + sp.getType() + "，请检查数据是否异常");
    }

    /**
     *
     * @return
     */
    protected DingTalkRobot getRobot() {
        return this.getServiceProvider().getRobot(this.data().getSpAccountId());
    }
}
