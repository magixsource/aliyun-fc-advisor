package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.inject.Injector;
import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.controller.request.UserFeatureQueryRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * User recommend advise controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class UserRecommendController extends FunctionController<UserFeatureQueryRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<UserFeatureQueryRequest, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(UserRecommendController.class);
    private Injector injector;

    @Inject
    private HealthQueryApi healthQueryApi;

    @Override
    public ServerlessResponse handleRequest(UserFeatureQueryRequest userFeatureRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(userFeatureRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(userFeatureRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
    }

    @Override
    public ServerlessResponse internalHandle(UserFeatureQueryRequest dto) {
        if (dto == null) {
            logger.error("bad request {}", JSON.toJSONString(dto));
            throw new IllegalArgumentException("Bad request.");
        }
        String openId = dto.getOpenId();
        if (openId == null || openId.trim().isEmpty()) {
            throw new IllegalArgumentException("Bad request.");
        }
        Integer userId = dto.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();
        PageInfo pageInfo = healthQueryApi.userRecommend(dto);
        PayloadResponse payloadResponse = new PayloadResponse("success", pageInfo.toMap());
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
