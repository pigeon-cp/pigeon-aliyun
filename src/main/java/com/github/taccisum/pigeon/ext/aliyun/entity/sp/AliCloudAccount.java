package com.github.taccisum.pigeon.ext.aliyun.entity.sp;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.github.taccisum.domain.core.exception.annotation.ErrorCode;
import com.github.taccisum.pigeon.core.data.MessageDO;
import com.github.taccisum.pigeon.core.data.ThirdAccountDO;
import com.github.taccisum.pigeon.core.entity.core.RawMessageDeliverer;
import com.github.taccisum.pigeon.core.entity.core.ThirdAccount;
import com.github.taccisum.pigeon.core.utils.JsonUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.function.Consumer;

/**
 * 阿里云账号
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.1
 */
@Slf4j
public class AliCloudAccount extends ThirdAccount {
    private IAcsClient client;

    public AliCloudAccount(Long id) {
        super(id);
    }

    /**
     * 通过阿里云邮件服务指定账号发送一封邮件，详见 https://help.aliyun.com/document_detail/29459.html
     *
     * @param account     发信账号，最终会显示在发信人上（如 robot -> robot@smtp.foo.com）
     * @param toAddresses 目标地址，支持指定多个，收件人之间用逗号隔开
     * @param subject     主题
     * @param htmlBody    内容（支持 HTML）
     * @param tag         邮件标签
     */
    public void sendMailVia(String account, String toAddresses, String subject, String htmlBody, String tag, MailOptions opts)
            throws MailSendException {
        // TODO:: 暂时写死
        AliCloud.Region region = AliCloud.Region.HANG_ZHOU;
        opts = Optional.ofNullable(opts).orElse(new MailOptions());

        try {
            IAcsClient client = this.getClient(region);
            SingleSendMailRequest request = new SingleSendMailRequest();
            if (region != AliCloud.Region.HANG_ZHOU) {
                // 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
                request.setVersion("2017-06-22");
            }
            request.setAccountName(account);
            request.setFromAlias(opts.getNickname());
            //0：为随机账号 1：为发信地址
            request.setAddressType(0);
            request.setTagName(tag);
            // 是否启用管理控制台中配置好回信地址（状态须验证通过），取值范围是字符串true或者false
            request.setReplyToAddress(true);

            request.setToAddress(toAddresses);
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //request.setToAddress("邮箱1,邮箱2");

            request.setSubject(subject);
            request.setHtmlBody(htmlBody);
            request.setMethod(MethodType.POST);
            //如果调用成功，正常返回 httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        } catch (ServerException e) {
            throw new MailSendException(e);
        } catch (ClientException e) {
            throw new MailSendException(e);
        }
    }

    /**
     * 通过阿里云接口发送短信，详细文档 https://help.aliyun.com/document_detail/101414.html
     *
     * @param templateCode 短信模板 code
     * @param phone        接受方手机号码
     * @param signature    短信签名
     * @param params       参数
     */
    public void sendSMS(String templateCode, String phone, String signature, String params)
            throws SMSSendException {
        AliCloud.Region region = AliCloud.Region.HANG_ZHOU;
        IAcsClient client = this.getClient(region);

        try {
            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");
            request.putQueryParameter("RegionId", region.key());
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", signature);
            request.putQueryParameter("TemplateCode", templateCode);
            if (StringUtils.isNotBlank(params)) {
                request.putQueryParameter("TemplateParam", params);
            }
            CommonResponse response = client.getCommonResponse(request);

            log.debug("请求阿里运营商返回：{}", JsonUtils.stringify(response));
            Map<String, Object> rsp = JsonUtils.parse(response.getData(), Map.class);
            if (Objects.equals(rsp.get("Code"), "OK")) {
                throw new SMSSendException(response);
            }
        } catch (ClientException e) {
            throw new SMSSendException(e);
        }
    }

    /**
     * 批量发送短信（不超过 100 个用户）
     */
    public SendBatchSmsResponse sendBatchSMSLimit100(String templateCode,
                                                     String[] phones,
                                                     String[] signatures,
                                                     String[] paramsArray) {
        if (phones.length > 100) {
            // 每次请求最多 100 个 用户，128k
            throw new IllegalArgumentException("阿里云短信群发数量不能超过 100");
        }

        AliCloud.Region region = AliCloud.Region.HANG_ZHOU;
        IAcsClient client = this.getClient(region);

        try {
            SendBatchSmsRequest req = new SendBatchSmsRequest();
            req.setSysMethod(MethodType.POST);
            req.setSysVersion("2017-05-25");

            req.setTemplateCode(templateCode);
            req.setPhoneNumberJson(JsonUtils.stringify(phones));
            req.setSignNameJson(JsonUtils.stringify(signatures));
            if (paramsArray != null) {
                req.setTemplateParamJson(JsonUtils.stringify(paramsArray));
            }

            return client.getAcsResponse(req);
        } catch (ClientException e) {
            throw new BatchSMSSendException(e);
        }
    }


    /**
     * 通过阿里云接口批量发送短信，详细文档https://help.aliyun.com/document_detail/102364.html
     *
     * <pre>
     * 发送数量以传递的 phones 为准，signatures & parasArray 不足会自动补充（视为空），多余会被忽视。尽量确保三个参数的 size 一致
     * </pre>
     *
     * @param templateCode 短信模板 code
     * @param phones       接受方手机号码清单
     * @param signatures   短信签名清单
     * @param paramsArray  模板参数清单
     * @param callback     单次请求返回后执行（由于阿里云批量发送接口单次请求有发送数量限制，短信将分片进行发送，需要针对每次请求结果进行处理时可以使用此参数）
     */
    public void sendBatchSMS(String templateCode,
                             String[] phones,
                             String[] signatures,
                             String[] paramsArray,
                             Consumer<SingleSMSBatchSendContext> callback
    ) throws BatchSMSSendException {
        final int PHONES_PER_REQUEST = 100;
        // 计算切片数
        int part = phones.length / PHONES_PER_REQUEST;
        if (phones.length % PHONES_PER_REQUEST > 0) {
            part++;
        }

        for (int i = 0; i < part; i++) {
            // 取子数组，分批发送
            int left = i * PHONES_PER_REQUEST;
            int right = Math.min(left + PHONES_PER_REQUEST, phones.length);

            // TODO:: handle 数组不行长时的情况
            String[] subPhones = Arrays.copyOfRange(phones, left, right);
            String[] subSignatures = Arrays.copyOfRange(signatures, left, right);
            String[] subParamsArray = Arrays.copyOfRange(paramsArray, left, right);

            SendBatchSmsResponse resp = this.sendBatchSMSLimit100(templateCode, subPhones, subSignatures, subParamsArray);

            // 直接交由调用端处理，无论结果如何均不抛出异常
            callback.accept(new SingleSMSBatchSendContext(i, left, right - left, resp));
        }
    }

    IAcsClient getClient(AliCloud.Region region) {
        if (this.client == null) {
            ThirdAccountDO data = this.data();
            IClientProfile profile = DefaultProfile.getProfile(region.key(), data.getAppId(), data.getAppSecret());

            if (region != AliCloud.Region.HANG_ZHOU) {
                // TODO::
                // 如果是除杭州region外的其它region（如新加坡、澳洲Region），需要将下面的"cn-hangzhou"替换为"ap-southeast-1"、或"ap-southeast-2"。
                // 如果是除杭州region外的其它region（如新加坡region）， 需要做如下处理
                //try {
                //DefaultProfile.addEndpoint("dm.ap-southeast-1.aliyuncs.com", "ap-southeast-1", "Dm",  "dm.ap-southeast-1.aliyuncs.com");
                //} catch (ClientException e) {
                //e.printStackTrace();
                //}
            }
            this.client = new DefaultAcsClient(profile);
        }
        return this.client;
    }

    /**
     * 获取主账号下的子账号实体
     *
     * @param name 子账号名称（用户名即可，示例：sub，会被识别为 sub@main_id.onaliyun.com）
     */
    public AliCloudAccount getSubAccount(String name) {
        throw new NotImplementedException();
    }

    /**
     * @since 0.2
     */
    public RawMessageDeliverer toRawMessageDeliverer() {
        AliCloudAccount account = this;
        return new RawMessageDeliverer() {
            @Override
            public String deliver(MessageDO data) {
                account.sendSMS(
                        data.getThirdTemplateCode(),
                        data.getTarget(),
                        data.getSignature(),
                        data.getParams()
                );
                // TODO:: return biz id
                return null;
            }

            @Override
            public String deliverBatchFast(List<MessageDO> messages) {
                String[] phones = new String[messages.size()];
                String[] signatures = new String[messages.size()];
                String[] paramsArray = new String[messages.size()];
                for (int i = 0; i < messages.size(); i++) {
                    phones[i] = messages.get(i).getTarget();
                    signatures[i] = messages.get(i).getSignature();
                    paramsArray[i] = messages.get(i).getParams();
                }

                SendBatchSmsResponse resp = account.sendBatchSMSLimit100(messages.get(0).getThirdTemplateCode(), phones, signatures, paramsArray);
                return resp.getBizId();
            }
        };
    }

    @Data
    public static class MailOptions {
        /**
         * 发信人昵称，长度小于 15 个字符。
         */
        private String nickname;
    }

    /**
     * 阿里云 Open API 访问异常
     */
    @ErrorCode(value = "ALI_YUN.OPEN_API")
    public static class OApiAccessException extends com.github.taccisum.domain.core.DomainException {
        public OApiAccessException(String key, ClientException cause) {
            super(String.format("阿里云 Open API \"%s\" 访问异常，错误码： %s，错误信息：%s", key, cause.getErrCode(), cause.getErrMsg()),
                    cause);
        }

        public OApiAccessException(String key, CommonResponse response) {
            super(String.format("阿里云 Open API \"%s\" 请求失败，响应内容：%s", key, response.getData()));
        }
    }

    public static class MailSendException extends OApiAccessException {
        public MailSendException(ClientException cause) {
            super("MailSend", cause);
        }
    }

    public static class SMSSendException extends OApiAccessException {
        public SMSSendException(ClientException cause) {
            super("SMS_Send", cause);
        }

        public SMSSendException(CommonResponse response) {
            super("SMS_Send", response);
        }
    }

    public static class BatchSMSSendException extends OApiAccessException {
        public BatchSMSSendException(ClientException cause) {
            super("BATCH_SMS_Send", cause);
        }
    }

    /**
     * 单次批量群发短信请求上下文
     */
    @Data
    public static class SingleSMSBatchSendContext {
        /**
         * 关联分片信息
         */
        private PartInfo part;
        /**
         * 响应内容
         */
        private SendBatchSmsResponse response;

        public SingleSMSBatchSendContext(int index, int start, int size, SendBatchSmsResponse response) {
            this.part = new PartInfo()
                    .setIndex(index)
                    .setStart(start)
                    .setSize(size);
            this.response = response;
        }

        @Data
        @Accessors(chain = true)
        public static class PartInfo {
            /**
             * 在分片结果中的 index
             */
            private int index;
            /**
             * 在父数组中的起始 index
             */
            private int start;
            /**
             * 分片大小
             */
            private int size;
        }
    }
}
