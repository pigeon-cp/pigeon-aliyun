package com.github.taccisum.pigeon.ext.aliyun.entity.message;

import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.core.data.MessageDO;
import com.github.taccisum.pigeon.core.entity.core.MessageTemplate;
import com.github.taccisum.pigeon.core.entity.core.message.SMS;
import com.github.taccisum.pigeon.core.entity.core.sp.SMSServiceProvider;
import com.github.taccisum.pigeon.core.repo.MessageTemplateRepo;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.AliCloud;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.AliCloudAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 阿里云短信消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class AliCloudSMS extends SMS {
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
    protected void doDelivery() throws Exception {
        MessageDO data = this.data();

        AliCloudAccount account = this.getServiceProvider()
                .getAccountOrThrow(data.getSpAccountId());

        MessageTemplate template = this.getTemplate();

        if (template != null) {
            account.sendSMS(
                    template.data().getThirdCode(),
                    data.getTarget(),
                    "豌豆思维VIPThink",
                    data.getParams()
            );
        } else {
            throw new UnsupportedOperationException("暂不支持非模板消息");
        }
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
}
