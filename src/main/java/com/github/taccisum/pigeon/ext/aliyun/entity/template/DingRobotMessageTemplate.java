package com.github.taccisum.pigeon.ext.aliyun.entity.template;

import pigeon.core.entity.core.MessageTemplate;
import pigeon.core.valueobj.MessageInfo;
import org.apache.commons.csv.CSVRecord;

/**
 * 钉钉机器人消息模板
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public class DingRobotMessageTemplate extends MessageTemplate {
    public DingRobotMessageTemplate(Long id) {
        super(id);
    }

    @Override
    public String getMessageType() {
        return this.data().getType();
    }

    @Override
    protected String getAccountHeaderName() {
        throw new UnsupportedOperationException("钉钉消息模板暂不支持此操作");
    }

    @Override
    protected MessageInfo map(CSVRecord row, MessageInfo def) {
        throw new UnsupportedOperationException("钉钉消息模板暂不支持此操作");
    }
}
