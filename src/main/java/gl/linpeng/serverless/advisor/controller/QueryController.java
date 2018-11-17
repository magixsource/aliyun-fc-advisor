package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.common.base.Strings;
import com.google.inject.Injector;
import gl.linpeng.gf.annotation.Serverless;
import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.controller.request.BaseQueryRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Disease Controller
 *
 * @author lin.peng
 * @since 1.0
 **/
@Serverless
public class QueryController extends FunctionController<BaseQueryRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<BaseQueryRequest, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(QueryController.class);
    private Injector injector;

    @Inject
    private HealthQueryApi healthQueryApi;


    @Override
    public ServerlessResponse handleRequest(BaseQueryRequest apiRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(apiRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(apiRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
//        ApiResponse apiResponse = new ApiResponse(serverlessResponse);
//        return apiResponse;
    }

    @Override
    public ServerlessResponse internalHandle(BaseQueryRequest jsonDTO) {
        // validate content
        logger.debug("dto {} json {}", jsonDTO, JSON.toJSONString(jsonDTO));
        if (jsonDTO == null || Strings.isNullOrEmpty(jsonDTO.getQ()) || jsonDTO.getType() == null || Strings.isNullOrEmpty(jsonDTO.getType())) {
            logger.error("bad request {}", JSON.toJSONString(jsonDTO));
            throw new IllegalArgumentException("Bad request.");
        }
        // init runtime
        initApplication();

        String q = jsonDTO.getQ().trim();
        String type = jsonDTO.getType().trim().toLowerCase();

        Integer pageSize = jsonDTO.getPageSize() == null ? 10 : jsonDTO.getPageSize();
        Integer page = jsonDTO.getPage() == null ? 1 : jsonDTO.getPage();
        PageInfo pageInfo = healthQueryApi.query(q, type, pageSize, page);
        PayloadResponse response = new PayloadResponse("success", pageInfo.toMap());
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
