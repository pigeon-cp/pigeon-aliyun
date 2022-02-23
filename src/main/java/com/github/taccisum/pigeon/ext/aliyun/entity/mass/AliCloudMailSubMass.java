package com.github.taccisum.pigeon.ext.aliyun.entity.mass;

import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.AliCloudAccount;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import pigeon.core.data.MessageDO;
import pigeon.core.data.SubMassDO;
import pigeon.core.entity.core.MassTactic;
import pigeon.core.entity.core.mass.AbstractSubMass;
import pigeon.core.valueobj.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 阿里云 Mail 群发消息子集
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.2
 */
@Slf4j
public class AliCloudMailSubMass extends AbstractSubMass {
    public AliCloudMailSubMass(Long id) {
        super(id);
    }

    @Override
    protected void doPrepare() {
        SubMassDO data = this.data();

        log.debug("自动为群发子集 {} 创建阿里云模板及收信人列表", this.id());

        AliCloudMailMass main = (AliCloudMailMass) this.getMain();
        MassTactic tactic = main.getTactic().orElseThrow(() -> new DataErrorException("消息子集", this.id(), "关联策略不存在"));
        List<MessageDO> messages = new ArrayList<>();
        List<AliCloudAccount.ReceiverInfo> receivers = tactic.listMessageInfos(data.getStart(), data.getEnd())
                .stream()
                .map(info -> {
                    AliCloudAccount.ReceiverInfo ri = new AliCloudAccount.ReceiverInfo();
                    ri.setEmailAddress(info.getAccount().toString());
                    ri.setName("TODO");
                    ri.setNickname("TODO");
                    ri.setGender("TODO");
                    ri.setBirthday("TODO");
                    ri.setMobile("TODO");
                    return ri;
                })
                .collect(Collectors.toList());

        AliCloudAccount account = main.getAliCloudAccount();
        account.saveMailReceiverDetail(main.getReceiverListId(), receivers);
    }

    @Override
    protected int doDeliver(Messages messages) {
        return messages.size();
    }

    private MassTactic getTactic() {
        throw new NotImplementedException();
    }
}
