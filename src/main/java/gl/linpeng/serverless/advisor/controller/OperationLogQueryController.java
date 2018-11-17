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
import gl.linpeng.serverless.advisor.controller.request.IdSetQueryRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import gl.linpeng.serverless.advisor.model.StatVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Operation Log Query controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class OperationLogQueryController extends FunctionController<IdSetQueryRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<IdSetQueryRequest, ServerlessResponse> {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogQueryController.class);
    private Injector injector;

    @Inject
    private OperationQueryApi operationQueryApi;

    @Override
    public ServerlessResponse handleRequest(IdSetQueryRequest apiRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(apiRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(apiRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
    }

    @Override
    public ServerlessResponse internalHandle(IdSetQueryRequest jsonDTO) {
        // validate
        logger.debug("dto {} json {}", jsonDTO, JSON.toJSONString(jsonDTO));
        if (jsonDTO == null || jsonDTO.getIds() == null || jsonDTO.getType() == null || jsonDTO.getIds().length == 0) {
            logger.error("bad request {}", JSON.toJSONString(jsonDTO));
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();

        // stat
        Long operationTargetType = Long.valueOf(jsonDTO.getType());
        Set<Long> ids = new HashSet<>(Arrays.asList(jsonDTO.getIds()));
        Map<String, List<StatVo>> map = operationQueryApi.batchStat(operationTargetType, ids);
        Map<String, Object> payload = new HashMap<>(1);
        payload.put("stat", map);
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
