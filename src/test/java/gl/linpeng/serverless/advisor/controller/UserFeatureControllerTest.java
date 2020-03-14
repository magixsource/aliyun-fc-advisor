package gl.linpeng.serverless.advisor.controller;


import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.Credentials;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.FunctionParam;
import gl.linpeng.serverless.advisor.controller.request.UserFeatureRequest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class UserFeatureControllerTest {

    private static UserFeatureController userController;

    @BeforeClass
    public static void init() {
        userController = new UserFeatureController();
    }

    @Test
    public void testSaveUserFeature() throws IOException {
        String content = "{\"openId\":\"o6zHH5cIm4nuMr_gTyRar9Xy2x-U\",\"userId\":1,\"type\":1,\"diseaseId\":1}";
        Context ctx = new Context() {
            @Override
            public String getRequestId() {
                return "11111111111";
            }

            @Override
            public Credentials getExecutionCredentials() {
                return null;
            }

            @Override
            public FunctionParam getFunctionParam() {
                return null;
            }

            @Override
            public FunctionComputeLogger getLogger() {
                return null;
            }
        };

        UserFeatureRequest userRequest = JSON.parseObject(content, UserFeatureRequest.class);
        Object result = userController.handleRequest(userRequest, ctx);
        Assert.assertNotNull(result);
        System.out.println(JSON.toJSONString(result, true));
    }
}
