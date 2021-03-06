package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.inject.Injector;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.base.api.ApiRequest;
import gl.linpeng.gf.base.api.ApiResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.controller.request.IdQueryRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import gl.linpeng.serverless.advisor.model.Disease;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Disease Controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class DiseaseController extends FunctionController<IdQueryRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<IdQueryRequest, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(DiseaseController.class);
    private Injector injector;

    @Inject
    private HealthQueryApi healthQueryApi;


    @Override
    public ServerlessResponse handleRequest(IdQueryRequest apiRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(apiRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(apiRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
//        ApiResponse apiResponse = new ApiResponse(serverlessResponse);
//        return apiResponse;
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
        Disease disease = healthQueryApi.getDiseaseById(id);
        PayloadResponse payloadResponse = new PayloadResponse("success", disease.toMap());
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
