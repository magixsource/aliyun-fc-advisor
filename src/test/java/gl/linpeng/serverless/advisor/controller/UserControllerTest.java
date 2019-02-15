package gl.linpeng.serverless.advisor.controller;


import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.Credentials;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.FunctionParam;
import gl.linpeng.serverless.advisor.controller.request.UserRequest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class UserControllerTest {

    private static UserController userController;

    @BeforeClass
    public static void init() {
        userController = new UserController();
    }

    @Test
    public void testGetOrSaveUser() throws IOException {
        String content = "{\"openId\":\"3333\"}";
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

        UserRequest userRequest = JSON.parseObject(content, UserRequest.class);
        Object result = userController.handleRequest(userRequest, ctx);
        Assert.assertNotNull(result);
        System.out.println(JSON.toJSONString(result, true));
    }
}
