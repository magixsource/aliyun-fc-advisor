package gl.linpeng.serverless.advisor.controller;


import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.Credentials;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.FunctionParam;
import gl.linpeng.serverless.advisor.controller.request.IdQueryRequest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class DiseaseControllerTest {

    private static DiseaseController diseaseController;

    @BeforeClass
    public static void init() {
        diseaseController = new DiseaseController();
    }

    @Test
    public void testGetDisease() throws IOException {
        String content = "{\"id\":\"3\"}";
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

        IdQueryRequest idQueryRequest = JSON.parseObject(content,IdQueryRequest.class);
        Object result = diseaseController.handleRequest(idQueryRequest, ctx);
        Assert.assertNotNull(result);
        System.out.println(JSON.toJSONString(result, true));
    }
}
