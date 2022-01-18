package com.github.taccisum.pigeon.ext.aliyun.entity.sp;

import com.github.taccisum.pigeon.core.entity.core.CustomConcept;
import com.github.taccisum.pigeon.core.repo.ThirdAccountRepo;

import javax.annotation.Resource;

/**
 * 钉钉企业
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
public class DingTalkOrganization extends CustomConcept.Base<String> {
    private String code;

    @Resource
    private ThirdAccountRepo thirdAccountRepo;

    public DingTalkOrganization(String code) {
        super(code);
        this.code = code;
    }

    /**
     * 获取该企业下的钉钉机器人
     *
     * @param name 钉钉机器人名称
     * @return 钉钉机器人实体
     */
    public DingTalkRobot getRobot(String name) {
        String fullName = String.format("robot:%s:%s", this.code, name);
        return (DingTalkRobot) thirdAccountRepo.getByUsername(fullName)
                .orElse(null);
    }
}
