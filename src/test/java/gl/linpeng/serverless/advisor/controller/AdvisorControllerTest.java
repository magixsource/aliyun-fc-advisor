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

public class AdvisorControllerTest {

    private static AdvisorController advisorController;

    @BeforeClass
    public static void init() {
        advisorController = new AdvisorController();
    }

    @Test
    public void testGetAdvises() throws IOException {
        String content = "{\"id\":\"1\",\"type\":\"d\",\"filter\":\"m\"}";
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
        BaseQueryRequest request = JSON.parseObject(content, BaseQueryRequest.class);
        Object result = advisorController.handleRequest(request, ctx);
        Assert.assertNotNull(result);
        System.out.println(JSON.toJSONString(result, true));
    }
}
