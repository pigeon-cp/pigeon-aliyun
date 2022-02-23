# Pigeon Aliyun

为 `Pigeon` 项目提供与阿里云服务以及钉钉服务集成的扩展.

## References

### 邮件发送


邮件发送的三方账号需要先在阿里云后台创建好（需要具备权限 `AliyunDirectMailFullAccess`），然后添加到 `Pigeon` 中即可（指定 app_id 及 app_secret）

添加模板，使用刚才创建的三方账号为邮件发送账号

### 短信发送

### 钉钉机器人发送

### 钉钉机器人 Link 消息发送

message 扩展参数
ext: {
    "pic": ""
    "url": ""
}


### 扩展字段参考

短信 type: MAIL, SMS, DING-ROBOT-MD
模板 type: MAIL, SMS, DING-ROBOT-MD
服务商 sp_type: ALI_CLOUD, DING-TALK
