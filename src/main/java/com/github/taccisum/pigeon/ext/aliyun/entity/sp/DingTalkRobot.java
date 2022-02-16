package com.github.taccisum.pigeon.ext.aliyun.entity.sp;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import pigeon.core.entity.core.ThirdAccount;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoResponse;

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
     * 调用钉钉机器人接口发送 text 消息，详见 https://open.dingtalk.com/document/group/message-types-and-data-format
     *
     * @param content 内容
     */
    public void sendText(String content) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        request.setText(text);
        this.setAtByContent(request, content);

        call(client -> client.execute(request, this.data().getAccessToken()));
    }

    /**
     * 调用钉钉机器人接口发送 markdown 消息
     *
     * @param title   标题
     * @param content 内容
     */
    public void sendMarkDown(String title, String content) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.Markdown md = new OapiRobotSendRequest.Markdown();
        md.setTitle(title);
        md.setText(content);
        request.setMsgtype("markdown");
        request.setMarkdown(md);
        this.setAtByContent(request, content);

        call(client -> client.execute(request, this.data().getAccessToken()));
    }

    /**
     * 调用钉钉机器人接口发送 link 消息
     *
     * @param title   标题
     * @param content 内容
     * @param pic     图片 url
     * @param url     跳转链接
     */
    public void sendLink(String title, String content, String pic, String url) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setTitle(title);
        link.setText(content);
        link.setPicUrl(pic);
        link.setMessageUrl(url);
        request.setMsgtype("link");
        request.setLink(link);
        this.setAtByContent(request, content);

        call(client -> client.execute(request, this.data().getAccessToken()));
    }

    private DefaultDingTalkClient getClient() {
        return new DefaultDingTalkClient(SERVER_URL);
    }

    <T extends TaobaoResponse> TaobaoResponse call(Func func) {
        DingTalkClient client = this.getClient();
        try {
            TaobaoResponse resp = func.apply(client);
            if (!resp.isSuccess()) {
                throw new DingTalk.OApiAccessException(resp);
            }
            return (T) resp;
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

    @FunctionalInterface
    private interface Func {
        TaobaoResponse apply(DingTalkClient dingTalkClient) throws ApiException;
    }
}
