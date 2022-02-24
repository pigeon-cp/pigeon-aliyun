package com.github.taccisum.pigeon.ext.aliyun.docs;

import com.github.taccisum.pigeon.ext.aliyun.enums.SpType;
import com.google.common.collect.Lists;
import org.pf4j.Extension;
import pigeon.core.docs.PluginDocs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.2
 */
@Extension
public class AliCloudPluginDocs implements PluginDocs {
    @Override
    public List<String> listExtendedMessageType() {
        return Lists.newArrayList("DING-ROBOT-MD", "DING-ROBOT-TEXT", "DING-ROBOT-LINK");
    }

    @Override
    public List<String> listExtendedSpType() {
        return Arrays.stream(SpType.values())
                .map(val -> val.primaryKey())
                .collect(Collectors.toList());
    }
}
