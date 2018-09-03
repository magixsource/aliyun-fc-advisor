package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.common.base.Strings;
import com.google.inject.Injector;
import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.controller.request.BaseQueryRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Search Suggest controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class SuggestController extends FunctionController<BaseQueryRequest, PayloadResponse> implements PojoRequestHandler<BaseQueryRequest, PayloadResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SuggestController.class);
    private Injector injector;

    @Inject
    private HealthQueryApi healthQueryApi;

    @Override
    public PayloadResponse handleRequest(BaseQueryRequest jsonDTO, Context context) {
        getFunction().getFunctionContext().put("ctx", context);
        return handler(jsonDTO);
    }

    @Override
    public PayloadResponse internalHandle(BaseQueryRequest jsonDTO) {
        // validate content
        if (Strings.isNullOrEmpty(jsonDTO.getQ())) {
            logger.error("bad request {}", JSON.toJSONString(jsonDTO));
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();

        String q = jsonDTO.getQ().trim();
        PageInfo pageInfo = healthQueryApi.getQuerySuggests(q, 10, 1);

        PayloadResponse response = new PayloadResponse("success", pageInfo.toMap());
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
