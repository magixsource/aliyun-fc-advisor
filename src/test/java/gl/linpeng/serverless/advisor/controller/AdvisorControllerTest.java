package gl.linpeng.serverless.advisor.controller;


import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.Credentials;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.FunctionParam;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.serverless.advisor.controller.request.IdSetQueryRequest;
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
        String content = "{\"ids\":[\"1\",\"2\"],\"type\":\"i\",\"filter\":\"m\"}";
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
        IdSetQueryRequest request = JSON.parseObject(content, IdSetQueryRequest.class);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(request).build();
//        ApiRequest apiRequest = new ApiRequest();
//        apiRequest.setBody(serverlessRequest.getBody());
//        apiRequest.setIsBase64Encoded(false);
        Object result = advisorController.handleRequest(request, ctx);
        Assert.assertNotNull(result);
        System.out.println(JSON.toJSONString(result, true));
    }
}
