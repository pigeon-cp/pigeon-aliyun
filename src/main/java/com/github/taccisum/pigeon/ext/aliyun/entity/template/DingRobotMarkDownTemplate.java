package com.github.taccisum.pigeon.ext.aliyun.entity.template;

/**
 * 钉钉机器人 MarkDown 消息模板
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public class DingRobotMarkDownTemplate extends DingRobotMessageTemplate {
    public DingRobotMarkDownTemplate(Long id) {
        super(id);
    }

    @Override
    public String getMessageType() {
        return "DING-ROBOT-MD";
    }
}
