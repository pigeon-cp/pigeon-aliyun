package com.github.taccisum.pigeon.ext.aliyun.entity.sp;

import com.dingtalk.api.request.OapiRobotSendRequest;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/2/14
 */
class DingTalkRobotTest {
    private DingTalkRobot robot;

    @BeforeEach
    void setUp() {
        robot = new DingTalkRobot(1L);
    }


    @Nested
    @DisplayName("#SetAtByContent")
    class SetAtByContentTest {
        @Test
        @DisplayName("@手机号码")
        void index() {
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            robot.setAtByContent(request, "hello. @13712345678, @13800000000");
            assertThat(request.getAt()).isNotNull();
            assertThat(JsonPath.<List<String>>read(request.getAt(), "$.atMobiles")).containsOnly("@13712345678", "@13800000000");
        }

        @Test
        @DisplayName("@用户id")
        void atUserId() {
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            robot.setAtByContent(request, "hello. @u1, @u2");
            assertThat(request.getAt()).isNotNull();
            assertThat(JsonPath.<List<String>>read(request.getAt(), "$.atUserIds")).containsOnly("@u1", "@u2");
        }

        @Test
        @DisplayName("@手机号，@用户id 混输")
        void atMobileAndUserId() {
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            robot.setAtByContent(request, "hello. @13712345678, @u1");
            assertThat(request.getAt()).isNotNull();
            assertThat(JsonPath.<List<String>>read(request.getAt(), "$.atMobiles")).containsOnly("@13712345678");
            assertThat(JsonPath.<List<String>>read(request.getAt(), "$.atUserIds")).containsOnly("@u1");
        }

        @Test
        @DisplayName("多行文本")
        void multiLineContext() {
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            robot.setAtByContent(request, "hello. @13712345678,@13712345679\n@13800000000,@13800000001");
            assertThat(request.getAt()).isNotNull();
            assertThat(JsonPath.<List<String>>read(request.getAt(), "$.atMobiles")).containsOnly("@13712345678", "@13712345679", "@13800000000", "@13800000001");
        }

        @Test
        @DisplayName("不@任何人")
        void notAt() {
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            robot.setAtByContent(request, "hello.");
            assertThat(request.getAt()).isNull();
        }

        @Test
        @DisplayName("无效@")
        void invalidAt() {
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            robot.setAtByContent(request, "hello. @所有人");
            assertThat(request.getAt()).isNull();
        }

        @Test
        @DisplayName("@所有人")
        void atAll() {
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            robot.setAtByContent(request, "hello. @all");
            assertThat(JsonPath.<Boolean>read(request.getAt(), "$.isAtAll")).isTrue();

            robot.setAtByContent(request, "hello. @All");
            assertThat(JsonPath.<Boolean>read(request.getAt(), "$.isAtAll")).isTrue();

            robot.setAtByContent(request, "hello. @ALL");
            assertThat(JsonPath.<Boolean>read(request.getAt(), "$.isAtAll")).isTrue();

            robot.setAtByContent(request, "hello. @all. @13712345678");
            assertThat(JsonPath.<Boolean>read(request.getAt(), "$.isAtAll")).isTrue();
        }
    }
}