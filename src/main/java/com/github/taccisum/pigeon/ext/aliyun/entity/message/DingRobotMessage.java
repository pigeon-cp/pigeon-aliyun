package com.github.taccisum.pigeon.ext.aliyun.entity.message;

import com.github.taccisum.domain.core.exception.DataErrorException;
import pigeon.core.data.MessageDO;
import pigeon.core.entity.core.Message;
import pigeon.core.entity.core.ServiceProvider;
import pigeon.core.repo.ThirdAccountRepo;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.DingTalk;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.DingTalkRobot;
import org.apache.commons.lang.StringUtils;

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
    public boolean isRealTime() {
        // 钉钉消息基本都是实时消息
        return true;
    }

    @Override
    public boolean shouldRelateTemplate() {
        // 钉钉消息无需关联模板也可自由发送
        return false;
    }

    @Override
    public DingTalk getServiceProvider() {
        ServiceProvider sp = this.serviceProviderRepo.get(this.data().getSpType());
        if (sp instanceof DingTalk) {
            return (DingTalk) sp;
        }
        throw new DataErrorException("DingRobotMessage.ServiceProvider", this.id(), "钉钉机器人消息可能关联了错误的服务提供商：" + sp.getType() + "，请检查数据是否异常");
    }

    /**
     * 获取用于发送此消息的钉钉机器人
     */
    protected DingTalkRobot getRobot() {
        MessageDO data = this.data();
        return this.getServiceProvider()
                .getRobot(data.getSpAccountId())
                .orElseThrow(() -> new ThirdAccountRepo.NotFoundException(data.getSpAccountId(), data.getSpType()));
    }

    /**
     * 构建最终发送的钉钉消息
     *
     * @param content 内容
     * @param target  目标对象
     * @param sender  发送人
     * @param isMd    是否 markdown
     */
    protected static String buildContent(String content, String target, String sender, boolean isMd) {
        StringBuilder sb = new StringBuilder();
        sb.append(content);

        if (StringUtils.isNotBlank(target)) {
            String[] targets = target.split("[,，]");
            if (targets.length > 0) {
                sb.append("\n");
                for (String t : targets) {
                    sb.append("@")
                            .append(t.trim());
                }
            }
        }

        if (StringUtils.isNotBlank(sender)) {
            sb.append("\n");
            if (isMd) {
                sb.append("> —— by ");
            } else {
                sb.append("—— by ");
            }
            sb.append(sender);
        }
        return sb.toString();
    }
}
