package com.github.taccisum.pigeon.ext.aliyun.entity.message;

import com.github.taccisum.pigeon.core.data.MessageDO;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.DingTalkRobot;

/**
 * 钉钉机器人 TEXT 消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.2
 */
public class DingRobotText extends DingRobotMessage {
    public DingRobotText(Long id) {
        super(id);
    }

    @Override
    protected void doDelivery() {
        DingTalkRobot robot = this.getRobot();
        MessageDO data = this.data();
        robot.sendText(buildContent(data.getContent(), data.getTarget(), data.getSender(), false));
    }
}
