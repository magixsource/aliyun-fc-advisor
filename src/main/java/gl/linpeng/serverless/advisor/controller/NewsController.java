package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.inject.Injector;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.ArticleQueryApi;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.api.OperationApi;
import gl.linpeng.serverless.advisor.common.Constants;
import gl.linpeng.serverless.advisor.controller.request.IdQueryRequest;
import gl.linpeng.serverless.advisor.controller.request.OperationLogRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import gl.linpeng.serverless.advisor.model.Article;
import gl.linpeng.serverless.advisor.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * News Controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class NewsController extends FunctionController<IdQueryRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<IdQueryRequest, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    private Injector injector;

    @Inject
    private ArticleQueryApi articleQueryApi;
    @Inject
    private OperationApi operationApi;
    @Inject
    private HealthQueryApi healthQueryApi;


    @Override
    public ServerlessResponse handleRequest(IdQueryRequest apiRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(apiRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(apiRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
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
        Article article = articleQueryApi.getArticleById(id);
        if (jsonDTO.getOpenId() != null && jsonDTO.getOpenId().length() > 0) {
            User user = healthQueryApi.getOrSaveUser(jsonDTO.getOpenId());
            OperationLogRequest visitLog = new OperationLogRequest();
            visitLog.setOperationType(Constants.OperationLogType.VISIT.getValue());
            visitLog.setOperationTargetType(Constants.OperationLogTargetType.ARTICLE.getValue());
            visitLog.setOperationTargetId(id);
            visitLog.setOperationSourceType(Constants.OperationLogSourceType.HUMAN.getValue());
            visitLog.setOperationSourceId(user.getLongId());
            operationApi.saveOperationLog(visitLog);
        }
        PayloadResponse payloadResponse = new PayloadResponse("success", article.toMap());
        return ServerlessResponse.builder().setObjectBody(payloadResponse).build();
    }

    private void initApplication() {
        logger.debug("init application.");
        injector = getInjector().createChildInjector(new AdvisorModule());

        // inject instance
        if (articleQueryApi == null) {
            articleQueryApi = injector.getInstance(ArticleQueryApi.class);
        }
        if (operationApi == null) {
            operationApi = injector.getInstance(OperationApi.class);
        }
        if (healthQueryApi == null) {
            healthQueryApi = injector.getInstance(HealthQueryApi.class);
        }
    }


}
