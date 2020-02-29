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
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.api.OperationApi;
import gl.linpeng.serverless.advisor.common.Constants;
import gl.linpeng.serverless.advisor.controller.request.OperationLogRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import gl.linpeng.serverless.advisor.model.OperationLog;
import gl.linpeng.serverless.advisor.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Operation Log controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class OperationLogController extends FunctionController<OperationLogRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<OperationLogRequest, ServerlessResponse> {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogController.class);
    private Injector injector;

    @Inject
    private OperationApi operationApi;
    @Inject
    private HealthQueryApi healthQueryApi;

    @Override
    public ServerlessResponse handleRequest(OperationLogRequest apiRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(apiRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(apiRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
    }

    @Override
    public ServerlessResponse internalHandle(OperationLogRequest jsonDTO) {
        // validate
        logger.debug("dto {} json {}", jsonDTO, JSON.toJSONString(jsonDTO));
        if (jsonDTO == null) {
            logger.error("bad request {}", JSON.toJSONString(jsonDTO));
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();

        // save
        int sourceType = jsonDTO.getOperationSourceType();
        String openId = jsonDTO.getOpenId();
        if (Constants.OperationLogSourceType.HUMAN.getValue() == sourceType && openId != null && openId.length() > 0) {
            User user = healthQueryApi.getOrSaveUser(openId);
            jsonDTO.setOperationSourceId(user.getLongId());
        }
        OperationLog log = operationApi.saveOperationLog(jsonDTO);
        Map<String, Object> map = log.toMap();
        PayloadResponse payloadResponse = new PayloadResponse("success", map);
        return ServerlessResponse.builder().setObjectBody(payloadResponse).build();
    }

    private void initApplication() {
        logger.debug("init application.");
        injector = getInjector().createChildInjector(new AdvisorModule());

        // inject instance
        if (operationApi == null) {
            operationApi = injector.getInstance(OperationApi.class);
        }
        if (healthQueryApi == null) {
            healthQueryApi = injector.getInstance(HealthQueryApi.class);
        }

    }
}
