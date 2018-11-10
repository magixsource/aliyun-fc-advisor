package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.Credentials;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.FunctionParam;
import gl.linpeng.serverless.advisor.controller.request.OperationLogRequest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class OperationControllerTest {

    private static OperationLogController operationLogController;

    @BeforeClass
    public static void init() {
        operationLogController = new OperationLogController();
    }

    @Test
    public void testOperation() throws IOException {
        String content = "{\"operationType\":\"1\",\"operationTargetId\":\"11\",\"operationTargetType\":1}";
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

        OperationLogRequest request = JSON.parseObject(content, OperationLogRequest.class);
        Object result = operationLogController.handleRequest(request, ctx);
        Assert.assertNotNull(result);
        System.out.println(JSON.toJSONString(result, true));
    }
}
