package gl.linpeng.serverless.advisor.controller;


import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.Credentials;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.FunctionParam;
import gl.linpeng.gf.base.JsonDTO;
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
        String content = "{\"id\":\"4\",\"type\":\"d\",\"filter\":\"m\"}";
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
        JsonDTO jsonDTO = new JsonDTO();
        jsonDTO.setContent(content);
        Object result = advisorController.handleRequest(jsonDTO, ctx);

        System.out.println(JSON.toJSONString(result, true));
    }
}
