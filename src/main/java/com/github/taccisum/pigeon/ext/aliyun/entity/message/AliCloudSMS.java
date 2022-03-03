package com.github.taccisum.pigeon.ext.aliyun.entity.message;

import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.AliCloud;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.AliCloudAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pigeon.core.entity.core.MessageTemplate;
import pigeon.core.entity.core.RawMessageDeliverer;
import pigeon.core.entity.core.holder.MessageDelivererHolder;
import pigeon.core.entity.core.message.SMS;
import pigeon.core.entity.core.sp.SMSServiceProvider;
import pigeon.core.repo.MessageTemplateRepo;

/**
 * 阿里云短信消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class AliCloudSMS extends SMS implements MessageDelivererHolder {
    @Autowired
    private MessageTemplateRepo messageTemplateRepo;

    public AliCloudSMS(Long id) {
        super(id);
    }

    @Override
    public boolean isRealTime() {
        return false;
    }

    @Override
    protected void doDelivery() {
        this.getServiceProvider()
                .getAccountOrThrow(this.data().getSpAccountId())
                .toRawMessageDeliverer()
                .deliver(this.data());
    }

    private MessageTemplate getTemplate() {
        return messageTemplateRepo.get(this.data().getTemplateId())
                .orElse(null);
    }

    @Override
    public AliCloud getServiceProvider() {
        SMSServiceProvider sp = super.getServiceProvider();
        if (sp instanceof AliCloud) {
            return (AliCloud) sp;
        }
        throw new DataErrorException("AliCloudSMS.ServiceProvider", this.id(), "阿里云短信消息可能关联了错误的服务提供商：" + sp.getType() + "，请检查数据是否异常");
    }

    @Override
    public AliCloudAccount getSpAccount() {
        return (AliCloudAccount) super.getSpAccount();
    }

    @Override
    public RawMessageDeliverer getMessageDeliverer() {
        return this.getSpAccount().toRawMessageDeliverer();
    }
}
