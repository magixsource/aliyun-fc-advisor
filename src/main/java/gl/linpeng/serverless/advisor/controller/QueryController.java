package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.common.base.Strings;
import com.google.inject.Injector;
import gl.linpeng.gf.base.*;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Disease Controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class QueryController extends FunctionController<JsonDTO> implements PojoRequestHandler<JsonDTO, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(QueryController.class);
    private Injector injector;

    @Inject
    private HealthQueryApi healthQueryApi;


    @Override
    public ServerlessResponse handleRequest(JsonDTO jsonDTO, Context context) {
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest request = ServerlessRequest.builder().setObjectBody(jsonDTO).build();
        return handler(request);
    }

    @Override
    public ServerlessResponse internalHandle(JsonDTO jsonDTO) {
        // validate content
        if (jsonDTO == null || Strings.isNullOrEmpty(jsonDTO.getContent())) {
            logger.error("bad request {}", JSON.toJSONString(jsonDTO));
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();
        Map<String, String> requestBody = JSON.parseObject(jsonDTO.getContent(), Map.class);
        if (requestBody.containsKey("q") == false || requestBody.get("q") == null || requestBody.containsKey("type") == false || requestBody.get("type") == null) {
            logger.error("bad request {}", requestBody);
            throw new IllegalArgumentException("Bad request.");
        }

        String q = requestBody.get("q").trim();
        String type = requestBody.get("type").trim().toLowerCase();
        String pageSizeStr = requestBody.get("pageSie");
        String pageStr = requestBody.get("page");
        Integer pageSize = Strings.isNullOrEmpty(pageSizeStr) ? 10 : Integer.valueOf(pageSizeStr.trim());
        Integer page = Strings.isNullOrEmpty(pageStr) ? 1 : Integer.valueOf(pageStr.trim());
        PageInfo pageInfo = healthQueryApi.query(q, type, pageSize, page);
        Map<String, Object> payload = new HashMap<>();
        payload.put("payload", pageInfo);
        PayloadResponse response = new PayloadResponse("success", payload);
        return new ServerlessResponse.Builder().setObjectBody(response).build();
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
