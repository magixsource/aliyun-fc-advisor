package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.common.base.Strings;
import com.google.inject.Injector;
import gl.linpeng.gf.base.JsonDTO;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import gl.linpeng.serverless.advisor.model.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Ingredient Controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class IngredientController extends FunctionController<JsonDTO> implements PojoRequestHandler<JsonDTO, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(IngredientController.class);
    private Injector injector;

    @Inject
    private HealthQueryApi healthQueryApi;


    @Override
    public ServerlessResponse handleRequest(JsonDTO jsonDTO, Context context) {
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest request = ServerlessRequest.builder().setObjectBody(jsonDTO).build();
        return handler(request);
    }

    @Override
    public ServerlessResponse internalHandle(JsonDTO jsonDTO) {
        // validate content
        if (jsonDTO == null || Strings.isNullOrEmpty(jsonDTO.getContent())) {
            logger.error("bad request {}", JSON.toJSONString(jsonDTO));
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();
        Map<String, String> requestBody = JSON.parseObject(jsonDTO.getContent(), Map.class);
        if (requestBody.containsKey("id") == false || requestBody.get("id") == null) {
            logger.error("bad request {}", requestBody);
            throw new IllegalArgumentException("Bad request.");
        }

        Long id = Long.valueOf(requestBody.get("id"));
        Ingredient ingredient = healthQueryApi.getIngredientById(id);
        Map<String, Object> payload = new HashMap<>();
        payload.put("payload", ingredient);
        PayloadResponse response = new PayloadResponse("success", payload);
        return new ServerlessResponse.Builder().setObjectBody(response).build();
    }

    private void initApplication() {
        logger.debug("init application.");
        injector = getInjector().createChildInjector(new AdvisorModule());

        // inject instance
        if (healthQueryApi == null) {
            healthQueryApi = injector.getInstance(HealthQueryApi.class);
        }
    }
}
