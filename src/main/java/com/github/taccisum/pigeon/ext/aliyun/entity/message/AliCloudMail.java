package com.github.taccisum.pigeon.ext.aliyun.entity.message;

import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.core.data.MessageDO;
import com.github.taccisum.pigeon.core.entity.core.message.Mail;
import com.github.taccisum.pigeon.core.entity.core.sp.MailServiceProvider;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.AliCloud;
import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云邮件服务消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class AliCloudMail extends Mail {
    public AliCloudMail(Long id) {
        super(id);
    }

    @Override
    public boolean isRealTime() {
        return false;
    }

    @Override
    protected void doDelivery() {
        MessageDO data = this.data();
        this.getServiceProvider()
                .getAccountOrThrow(data.getSpAccountId())
                .sendMailVia(
                        data.getSender(),
                        data.getTarget(),
                        data.getTitle(),
                        data.getContent(),
                        data.getTag(),
                        null
                );
    }

    @Override
    public AliCloud getServiceProvider() {
        MailServiceProvider sp = super.getServiceProvider();
        if (sp instanceof AliCloud) {
            return (AliCloud) sp;
        }
        throw new DataErrorException("AliCloudMailMessage.ServiceProvider", this.id(), "阿里云邮件消息可能关联了错误的服务提供商：" + sp.getType() + "，请检查数据是否异常");
    }
}
