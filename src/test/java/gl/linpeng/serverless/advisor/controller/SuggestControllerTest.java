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

public class SuggestControllerTest {

    private static SuggestController suggestController;

    @BeforeClass
    public static void init() {
        suggestController = new SuggestController();
    }

    @Test
    public void testGetSuggest() throws IOException {
        String content = "{\"q\":\"病\"}";
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
        Object result = suggestController.handleRequest(jsonDTO, ctx);

        System.out.println(JSON.toJSONString(result, true));
    }
}
