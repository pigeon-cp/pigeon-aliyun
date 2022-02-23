package com.github.taccisum.pigeon.ext.aliyun.entity.mass;

import com.github.taccisum.domain.core.exception.DataErrorException;
import com.github.taccisum.pigeon.ext.aliyun.entity.sp.AliCloudAccount;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import pigeon.core.data.MessageMassDO;
import pigeon.core.entity.core.MassTactic;
import pigeon.core.entity.core.MessageTemplate;
import pigeon.core.entity.core.mass.PartitionMessageMass;

/**
 * 阿里云 Mail 群发消息集
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.2
 */
@Slf4j
public class AliCloudMailMass extends PartitionMessageMass {
    public AliCloudMailMass(Long id) {
        super(id);
    }

    @Override
    protected void doPrepare(boolean parallel) {
        AliCloudAccount account = this.getAliCloudAccount();
        String listName = getReceiverListName();
        String id = account.createMailReceiverList(listName, listName + "@aliyun.com", "aliyun mail mass receiver list for pigeon-aliyun");
        this.recordReceiverListId(id);
        super.doPrepare(parallel);
    }

    @Override
    protected void doDeliver(boolean ignored) throws DeliverException {
        MassTactic tactic = this.getMassTactic();
        MessageTemplate template = tactic.getMessageTemplate();
        AliCloudAccount account = this.getAliCloudAccount();
        account.sendMailBatch(template.data().getThirdCode(), this.getReceiverListName(), tactic.data().getDefaultSender());
    }

    @Override
    protected int maxSubMassSize() {
        // 每个子 mass 最多 500 封邮件，详见 https://help.aliyun.com/document_detail/315559.html
        return 500;
    }

    public AliCloudAccount getAliCloudAccount() {
        return (AliCloudAccount) this.getMassTactic()
                .getMessageTemplate()
                .getSpAccount();
    }

    private MassTactic getMassTactic() {
        return this.getTactic()
                .orElseThrow(() -> new DataErrorException("阿里云邮件集", this.id(), "关联策略不存在"));
    }

    private String getReceiverListName() {
        return String.format("rl_%d", this.id());
    }

    public String getReceiverListId() {
        return this.data().getExt();
    }

    private void recordReceiverListId(String listId) {
        MessageMassDO o = dao.newEmptyDataObject();
        o.setId(this.id());
        o.setExt(listId);
        this.dao.updateById(o);
    }

    /**
     * 移除关联的收件人列表
     */
    public void removeRelativeReceiverList() {
        throw new NotImplementedException();
    }
}
