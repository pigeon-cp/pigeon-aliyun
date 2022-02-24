package com.github.taccisum.pigeon.ext.aliyun.enums;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/1/18
 */
public enum SpType {
    /**
     * 阿里云
     */
    ALI_CLOUD(Lists.newArrayList("ALI_CLOUD")),
    /**
     * 钉钉
     */
    DING_TALK(Lists.newArrayList("DING_TALK", "DING-TALK", "DINGTALK"));

    /**
     * 可用 keys
     */
    private List<String> availableKeys;

    SpType(List<String> availableKeys) {
        this.availableKeys = availableKeys;
    }

    public boolean match(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return this.availableKeys.contains(key.toUpperCase());
    }

    public String primaryKey() {
        return this.availableKeys.get(0);
    }
}
