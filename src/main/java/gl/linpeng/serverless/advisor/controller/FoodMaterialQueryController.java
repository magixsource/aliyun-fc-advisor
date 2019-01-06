package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.inject.Injector;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.controller.request.IdQueryRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Food material controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class FoodMaterialQueryController extends FunctionController<IdQueryRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<IdQueryRequest, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(FoodMaterialQueryController.class);
    private Injector injector;

    @Inject
    private HealthQueryApi healthQueryApi;

    @Override
    public ServerlessResponse handleRequest(IdQueryRequest idQueryRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(idQueryRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(idQueryRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
    }

    @Override
    public ServerlessResponse internalHandle(IdQueryRequest dto) {
        if (dto == null) {
            logger.error("bad request {}", JSON.toJSONString(dto));
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();

        Long foodId = dto.getId();
        if (foodId == null) {
            throw new IllegalArgumentException("Bad request.");
        }
        List list = healthQueryApi.getComponentsByFoodId(foodId);
        Map<String, Object> payload = new HashMap<>(1);
        payload.put("list", list);
        PayloadResponse payloadResponse = new PayloadResponse("success", payload);
        return ServerlessResponse.builder().setObjectBody(payloadResponse).build();
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
