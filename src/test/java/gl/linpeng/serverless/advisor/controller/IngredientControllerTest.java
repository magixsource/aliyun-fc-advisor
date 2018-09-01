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

public class IngredientControllerTest {

    private static IngredientController ingredientController;

    @BeforeClass
    public static void init() {
        ingredientController = new IngredientController();
    }

    @Test
    public void testGetIngredient() throws IOException {
        String content = "{\"id\":\"7\"}";
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
        Object result = ingredientController.handleRequest(jsonDTO, ctx);

        System.out.println(JSON.toJSONString(result, true));
    }
}
