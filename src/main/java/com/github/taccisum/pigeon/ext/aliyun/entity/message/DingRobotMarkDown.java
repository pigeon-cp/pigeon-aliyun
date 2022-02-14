package com.github.taccisum.pigeon.ext.aliyun.entity.message;

import com.github.taccisum.pigeon.core.data.MessageDO;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.DingTalkRobot;
import org.apache.commons.lang.StringUtils;

/**
 * 钉钉机器人 MarkDown 消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public class DingRobotMarkDown extends DingRobotMessage {
    public DingRobotMarkDown(Long id) {
        super(id);
    }

    @Override
    protected void doDelivery() {
        DingTalkRobot robot = this.getRobot();
        MessageDO data = this.data();

        String content = "";

        String sender = data.getSender();
        if (StringUtils.isNotBlank(sender)) {
            content = data.getContent() + "\n > —— by " + sender;
        } else {
            content = data.getContent();
        }

        robot.sendMarkDown(data.getTitle(), content);
    }
}
