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

/**
 * @author lin.peng
 * @since 1.0
 **/
public class OperationQueryControllerTest {

    private static OperationLogQueryController operationLogQueryController;

    @BeforeClass
    public static void init() {
        operationLogQueryController = new OperationLogQueryController();
    }

    @Test
    public void testOperation() throws IOException {
        String content = "{\"id\":\"11\",\"type\":\"1\"}";
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
        Object result = operationLogQueryController.handleRequest(request, ctx);
        Assert.assertNotNull(result);
        System.out.println(JSON.toJSONString(result, true));
    }
}
