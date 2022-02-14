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

        StringBuilder content = new StringBuilder();

        String sender = data.getSender();

        content.append(data.getContent());

        if (StringUtils.isNotBlank(data.getTarget())) {
            String[] targets = data.getTarget().split("[,，]");
            if (targets.length > 0) {
                content.append("\n");
                for (String target : targets) {
                    content.append("@")
                            .append(target.trim());
                }
            }
        }

        if (StringUtils.isNotBlank(sender)) {
            content.append("\n > —— by ")
                    .append(sender);
        }

        robot.sendMarkDown(data.getTitle(), content.toString());
    }
}
