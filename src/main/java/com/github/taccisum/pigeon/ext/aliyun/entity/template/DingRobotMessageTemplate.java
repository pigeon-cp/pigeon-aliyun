package com.github.taccisum.pigeon.ext.aliyun.entity.template;

import com.github.taccisum.pigeon.core.entity.core.MessageTemplate;
import com.github.taccisum.pigeon.core.valueobj.MessageInfo;
import org.apache.commons.csv.CSVRecord;

/**
 * 钉钉机器人消息模板
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public abstract class DingRobotMessageTemplate extends MessageTemplate {
    public DingRobotMessageTemplate(Long id) {
        super(id);
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
