package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.inject.Injector;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.controller.request.IdQueryRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import gl.linpeng.serverless.advisor.model.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Ingredient Controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class IngredientController extends FunctionController<IdQueryRequest> implements PojoRequestHandler<IdQueryRequest, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(IngredientController.class);
    private Injector injector;

    @Inject
    private HealthQueryApi healthQueryApi;


    @Override
    public ServerlessResponse handleRequest(IdQueryRequest jsonDTO, Context context) {
        getFunction().getFunctionContext().put("ctx", context);
        return handler(jsonDTO);
    }

    @Override
    public ServerlessResponse internalHandle(IdQueryRequest jsonDTO) {
        // validate content
        if (jsonDTO == null) {
            logger.error("bad request {}", JSON.toJSONString(jsonDTO));
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();
        Long id = jsonDTO.getId();
        Ingredient ingredient = healthQueryApi.getIngredientById(id);
        PayloadResponse response = new PayloadResponse("success", ingredient.toMap());
        return response;
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
