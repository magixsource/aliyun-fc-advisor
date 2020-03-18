package gl.linpeng.serverless.advisor.controller;


import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.Credentials;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.FunctionParam;
import gl.linpeng.serverless.advisor.controller.request.BaseQueryRequest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class LoginControllerTest {

    private static LoginController loginController;

    @BeforeClass
    public static void init() {
        loginController = new LoginController();
    }

    @Test
    public void testLogin() throws IOException {
        String content = "{\"q\":\"3333\"}";
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

        BaseQueryRequest baseQueryRequest = JSON.parseObject(content, BaseQueryRequest.class);
        Object result = loginController.handleRequest(baseQueryRequest, ctx);
        Assert.assertNotNull(result);
        System.out.println(JSON.toJSONString(result, true));
    }
}
