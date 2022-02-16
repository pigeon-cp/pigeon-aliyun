package com.github.taccisum.pigeon.ext.aliyun.entity.mass;

import pigeon.core.entity.core.mass.PartitionMessageMass;

/**
 * 阿里云 SMS 群发消息集
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.2
 */
public class AliCloudSMSMass extends PartitionMessageMass {
    public AliCloudSMSMass(Long id) {
        super(id);
    }

    @Override
    protected int maxSubMassSize() {
        // 阿里云群发消息单次不能超过 100 条，这里默认返回 100，避免后续调用群发接口时发生错误
        // 详见 https://help.aliyun.com/document_detail/102364.htm?spm=a2c4g.11186623.0.0.633f309biDY2cp
        return 100;
    }
}
