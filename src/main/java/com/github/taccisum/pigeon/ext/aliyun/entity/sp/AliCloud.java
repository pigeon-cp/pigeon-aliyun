package com.github.taccisum.pigeon.ext.aliyun.entity.sp;

import com.github.taccisum.domain.core.DomainException;
import com.github.taccisum.domain.core.exception.DataNotFoundException;
import pigeon.core.entity.core.ServiceProvider;
import pigeon.core.entity.core.ThirdAccount;
import pigeon.core.entity.core.sp.MailServiceProvider;
import pigeon.core.entity.core.sp.SMSServiceProvider;
import pigeon.core.repo.ThirdAccountRepo;
import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 阿里云
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class AliCloud extends ServiceProvider.Base implements
        MailServiceProvider,
        SMSServiceProvider {
    @Resource
    private ThirdAccountRepo thirdAccountRepo;

    public AliCloud() {
        super("ALI_CLOUD");
    }

    /**
     * 获取阿里云账号实体
     *
     * @param id 账号 id
     */
    @Override
    public AliCloudAccount getAccountOrThrow(Long id) throws DataNotFoundException {
        DomainException ex = new DataNotFoundException("阿里云账号", id);
        ThirdAccount account = this.getAccount(id)
                .orElseThrow(() -> ex);

        if (account instanceof AliCloudAccount) {
            return (AliCloudAccount) account;
        }
        throw ex;
    }

    /**
     * 获取阿里云账号实体，可以直接获取主账号或子账号（无主账号权限时）
     *
     * @param name 账号名，主账号（账号 id，示例：15201132xxxxxxxx）或子账号（完全名称，示例：sub@15201132xxxxxxxx.onaliyun.com）均可
     */
    public AliCloudAccount getAccount(String name) throws DataNotFoundException {
        DomainException ex = new DataNotFoundException("阿里云账号", name);
        ThirdAccount account = thirdAccountRepo.getByUsername(name)
                .orElseThrow(() -> ex);

        if (account instanceof AliCloudAccount) {
            return (AliCloudAccount) account;
        }
        throw ex;
    }

    @Override
    protected boolean match(ThirdAccount thirdAccount) {
        return SpType.ALI_CLOUD.match(thirdAccount.data().getSpType());
    }

    public enum Region {
        /**
         * 中国杭州
         */
        HANG_ZHOU("cn-hangzhou");

        @Getter
        @Accessors(fluent = true)
        private String key;

        Region(String key) {
            this.key = key;
        }
    }
}
