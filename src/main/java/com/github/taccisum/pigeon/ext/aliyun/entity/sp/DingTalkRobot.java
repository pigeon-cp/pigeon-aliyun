package com.github.taccisum.pigeon.ext.aliyun.entity.sp;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import com.taobao.api.ApiException;

/**
 * 钉钉机器人
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public class DingTalkRobot extends ThirdAccount {
    /**
     * 钉钉服务器地址
     */
    private static final String SERVER_URL = "https://oapi.dingtalk.com/robot/send";

    public DingTalkRobot(long id) {
        super(id);
    }

    /**
     * 发送 markdown 消息
     *
     * @param title   标题
     * @param content 内容
     */
    public void sendMarkDown(String title, String content) {
        DingTalkClient client = new DefaultDingTalkClient(SERVER_URL);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.Markdown md = new OapiRobotSendRequest.Markdown();
        md.setTitle(title);
        md.setText(content);
        request.setMsgtype("markdown");
        request.setMarkdown(md);
        try {
            OapiRobotSendResponse rsp = client.execute(request, this.data().getAccessToken());
            if (!rsp.isSuccess()) {
                throw new DingTalk.OApiAccessException(rsp);
            }
        } catch (ApiException e) {
            throw new DingTalk.OApiAccessException(e);
        }
    }
}
