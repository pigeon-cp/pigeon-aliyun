package com.github.taccisum.pigeon.ext.aliyun.entity.sp;

import com.dingtalk.api.response.OapiRobotSendResponse;
import com.github.taccisum.domain.core.DomainException;
import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.core.entity.core.ServiceProvider;
import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import com.github.taccisum.pigeon.core.entity.core.sp.MessageServiceProvider;
import com.github.taccisum.pigeon.core.repo.Factory;
import com.github.taccisum.pigeon.core.repo.ThirdAccountRepo;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import com.github.taccisum.pigeon.ext.aliyun.repo.factory.sp.DingTalkOrganizationFactory;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 钉钉
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class DingTalk extends ServiceProvider.Base implements
        MessageServiceProvider {
    @Resource
    private Factory factory;

    public DingTalk() {
        super("DING_TALK");
    }

    @Resource
    private ThirdAccountRepo thirdAccountRepo;

    /**
     * 根据账号 id 获取钉钉机器人
     *
     * @param id 三方账号 id
     * @return 钉钉机器人实体
     */
    public DingTalkRobot getRobot(long id) {
        Optional<ThirdAccount> account = this.getAccount(id);
        if (!account.isPresent()) {
            return null;
        }
        if (account.get() instanceof DingTalkRobot) {
            return (DingTalkRobot) account.get();
        } else {
            throw new DataErrorException("钉钉机器人", id, "类型不匹配");
        }
    }

    /**
     * 根据名称获取钉钉机器人
     *
     * @param name 钉钉机器人名称
     * @return 钉钉机器人实体
     */
    public DingTalkRobot getRobot(String name) {
        String fullName = "robot:" + name;
        return (DingTalkRobot) thirdAccountRepo.getByUsername(fullName)
                .orElse(null);
    }

    /**
     * 获取钉钉企业
     *
     * @param name 企业名称
     * @return 企业实体
     */
    public DingTalkOrganization getOrganization(String name) {
        // TODO:: via repo
        return factory.createExt(name, null, DingTalkOrganizationFactory.class);
    }

    @Override
    protected boolean match(ThirdAccount account) {
        return SpType.DING_TALK.match(account.data().getSpType());
    }

    /**
     * 钉钉 Open API 访问异常
     */
    public static class OApiAccessException extends DomainException {
        public OApiAccessException(OapiRobotSendResponse response) {
            super(String.format("请求钉钉 Open API 失败，响应内容：%s", response.getBody()));
        }

        public OApiAccessException(ApiException e) {
            super(String.format("请求钉钉 Open API 失败，错误信息：%s", e.getErrMsg()), e);
        }
    }
}
