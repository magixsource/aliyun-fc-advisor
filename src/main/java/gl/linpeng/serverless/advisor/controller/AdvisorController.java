package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.common.base.Strings;
import com.google.inject.Injector;
import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.controller.request.IdSetQueryRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Health advisor controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class AdvisorController extends FunctionController<IdSetQueryRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<IdSetQueryRequest, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(AdvisorController.class);
    private Injector injector;

    @Inject
    private HealthQueryApi healthQueryApi;

    @Override
    public ServerlessResponse handleRequest(IdSetQueryRequest apiRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(apiRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(apiRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
//        ApiResponse apiResponse = new ApiResponse(serverlessResponse);
//        return apiResponse;
    }

    @Override
    public ServerlessResponse internalHandle(IdSetQueryRequest jsonDTO) {
        logger.debug("dto {} and json {}", jsonDTO, JSON.toJSONString(jsonDTO));
        // validate content
        if (jsonDTO == null || jsonDTO.getIds() == null || Strings.isNullOrEmpty(jsonDTO.getType()) || Strings.isNullOrEmpty(jsonDTO.getFilter())) {
            logger.error("bad request {}", JSON.toJSONString(jsonDTO));
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();

        Long[] ids = jsonDTO.getIds();
        String type = jsonDTO.getType().trim();
        String filter = jsonDTO.getFilter().trim();
        Integer pageSize = jsonDTO.getPageSize() == null ? 10 : jsonDTO.getPageSize();
        Integer page = jsonDTO.getPage() == null ? 1 : jsonDTO.getPage();


        PageInfo pageInfo = healthQueryApi.queryAdvises(ids, type, filter, pageSize, page);
        Map<String, Object> payload = new HashMap<>();
        payload.put("payload", pageInfo);
        PayloadResponse response = new PayloadResponse("success", payload);
        return ServerlessResponse.builder().setObjectBody(response).build();
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
