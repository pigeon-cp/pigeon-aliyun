package com.github.taccisum.pigeon.ext.aliyun.entity.template;

import com.github.taccisum.pigeon.core.data.MessageDO;
import com.github.taccisum.pigeon.core.entity.core.User;
import com.github.taccisum.pigeon.core.entity.core.template.SMSTemplate;
import org.apache.commons.lang.StringUtils;

/**
 * 阿里云短信模板
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public class AliCloudSMSTemplate extends SMSTemplate {
    public AliCloudSMSTemplate(Long id) {
        super(id);
    }

    @Override
    public MessageDO initMessageInMemory(String sender, User user, Object params, String signature, String ext) throws InitMessageException {
        if (user == null) {
            throw new InitMessageException("接收者不能为 Null");
        }

        if (StringUtils.isBlank(signature)) {
            throw new InitMessageException("必须指定签名");
        }

        return super.initMessageInMemory(sender, user, params, signature, ext);
    }
}
