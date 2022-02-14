package com.github.taccisum.pigeon.ext.aliyun.entity.sp;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import com.taobao.api.ApiException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        this.setAtByContent(request, content);

        try {
            OapiRobotSendResponse rsp = client.execute(request, this.data().getAccessToken());
            if (!rsp.isSuccess()) {
                throw new DingTalk.OApiAccessException(rsp);
            }
        } catch (ApiException e) {
            throw new DingTalk.OApiAccessException(e);
        }
    }


    private static final Pattern AT_ALL_PATTERN = Pattern.compile("(@all)[,\\s]?", Pattern.CASE_INSENSITIVE);
    private static final Pattern AT_MEMBER_PATTERN = Pattern.compile("(@[0-9a-zA-Z]+)[,\\s]?");

    /**
     * 根据要发送的内容设置要 at 的对象
     *
     * @param request 请求
     * @param content 内容
     * @since 0.2
     */
    void setAtByContent(OapiRobotSendRequest request, String content) {
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();

        if (AT_ALL_PATTERN.matcher(content).find()) {
            // @All
            at.setIsAtAll(true);
        } else {
            Matcher m2 = AT_MEMBER_PATTERN.matcher(content);
            ArrayList<String> atMobiles = new ArrayList<>();
            ArrayList<String> atUserIds = new ArrayList<>();
            if (m2.find()) {
                do {
                    String atStr = m2.group(1);
                    if (atStr.length() == 12) {
                        // @+{11位手机号}
                        atMobiles.add(atStr);
                    } else {
                        atUserIds.add(atStr);
                    }
                } while (m2.find());
                at.setAtMobiles(atMobiles);
                at.setAtUserIds(atUserIds);
            } else {
                at = null;
            }
        }

        if (at != null) {
            request.setAt(at);
        }
    }
}
