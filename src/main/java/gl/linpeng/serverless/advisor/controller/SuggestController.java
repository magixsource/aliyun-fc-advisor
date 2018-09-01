package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.common.base.Strings;
import com.google.inject.Injector;
import gl.linpeng.gf.base.*;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Search Suggest controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class SuggestController extends FunctionController<JsonDTO> implements PojoRequestHandler<JsonDTO, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SuggestController.class);
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
        if (requestBody.containsKey("q") == false || requestBody.get("q") == null) {
            logger.error("bad request {}", requestBody);
            throw new IllegalArgumentException("Bad request.");
        }

        String q = requestBody.get("q").trim();
        PageInfo pageInfo = healthQueryApi.getQuerySuggests(q, 10, 1);
        Map<String, Object> payload = new HashMap<>();
        payload.put("payload", pageInfo);
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
