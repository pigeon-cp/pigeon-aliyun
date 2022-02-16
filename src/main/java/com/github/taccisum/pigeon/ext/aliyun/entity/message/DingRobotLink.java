package com.github.taccisum.pigeon.ext.aliyun.entity.message;

import com.github.taccisum.pigeon.core.data.MessageDO;
import com.github.taccisum.pigeon.core.utils.JsonUtils;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.DingTalkRobot;
import lombok.Data;

/**
 * 钉钉机器人 Link 消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.2
 */
public class DingRobotLink extends DingRobotMessage {
    public DingRobotLink(Long id) {
        super(id);
    }

    @Override
    protected void doDelivery() {
        DingTalkRobot robot = this.getRobot();
        MessageDO data = this.data();
        Extend ext = this.getExtend();
        robot.sendLink(data.getTitle(),
                buildContent(data.getContent(), data.getTarget(), data.getSender(), false),
                ext.getPic(),
                ext.getUrl()
        );
    }

    private Extend getExtend() {
        return JsonUtils.parse(this.data().getExt(), Extend.class);
    }

    @Data
    private static class Extend {
        private String pic;
        private String url;
    }
}
