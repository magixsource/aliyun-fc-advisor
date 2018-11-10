package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.inject.Inject;
import com.google.inject.Injector;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.OperationQueryApi;
import gl.linpeng.serverless.advisor.controller.request.BaseQueryRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import gl.linpeng.serverless.advisor.model.StatVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Operation Log Query controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class OperationLogQueryController extends FunctionController<BaseQueryRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<BaseQueryRequest, ServerlessResponse> {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogQueryController.class);
    private Injector injector;

    @Inject
    private OperationQueryApi operationQueryApi;

    @Override
    public ServerlessResponse handleRequest(BaseQueryRequest apiRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(apiRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(apiRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
    }

    @Override
    public ServerlessResponse internalHandle(BaseQueryRequest jsonDTO) {
        // validate
        logger.debug("dto {} json {}", jsonDTO, JSON.toJSONString(jsonDTO));
        if (jsonDTO == null || jsonDTO.getId() == null || jsonDTO.getType() == null) {
            logger.error("bad request {}", JSON.toJSONString(jsonDTO));
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();

        // stat
        Long operationTargetType = Long.valueOf(jsonDTO.getType());
        List<StatVo> list = operationQueryApi.stat(operationTargetType,jsonDTO.getId());
        Map<String, Object> payload = new HashMap<>(1);
        payload.put("stat", list);
        PayloadResponse payloadResponse = new PayloadResponse("success", payload);
        return ServerlessResponse.builder().setObjectBody(payloadResponse).build();
    }

    private void initApplication() {
        logger.debug("init application.");
        injector = getInjector().createChildInjector(new AdvisorModule());

        // inject instance
        if (operationQueryApi == null) {
            operationQueryApi = injector.getInstance(OperationQueryApi.class);
        }
    }
}
