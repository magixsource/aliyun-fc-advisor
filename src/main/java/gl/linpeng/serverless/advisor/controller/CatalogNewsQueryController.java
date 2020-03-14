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
import gl.linpeng.serverless.advisor.api.ArticleQueryApi;
import gl.linpeng.serverless.advisor.controller.request.BaseQueryRequest;
import gl.linpeng.serverless.advisor.inject.AdvisorModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;

/**
 * Catalog news controller
 *
 * @author lin.peng
 * @since 1.0
 **/
public class CatalogNewsQueryController extends FunctionController<BaseQueryRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<BaseQueryRequest, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(CatalogNewsQueryController.class);
    private Injector injector;

    @Inject
    private ArticleQueryApi articleQueryApi;

    @Override
    public ServerlessResponse handleRequest(BaseQueryRequest idQueryRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(idQueryRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(idQueryRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
    }

    @Override
    public ServerlessResponse internalHandle(BaseQueryRequest dto) {
        if (dto == null) {
            logger.error("bad request {}", JSON.toJSONString(dto));
            throw new IllegalArgumentException("Bad request.");
        }

        // init runtime
        initApplication();

        Long catalogId = dto.getId();
        if (catalogId == null) {
            throw new IllegalArgumentException("Bad request.");
        }

        Integer pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        Integer page = dto.getPage() == null ? 1 : dto.getPage();
        PageInfo pageInfo = articleQueryApi.getNewsByCatalogId(catalogId, pageSize, page);
        Map<String, Object> map = pageInfo.toMap();
        map.put("ext", "[{\"id\":\"71\",\"title\":\"热门\",\"parentId\":\"7\"},{\"id\":\"72\",\"title\":\"健康\",\"parentId\":\"7\"},{\"id\":\"73\",\"title\":\"生活\",\"parentId\":\"7\"},{\"id\":\"74\",\"title\":\"美容\",\"parentId\":\"7\"},{\"id\":\"75\",\"title\":\"养生\",\"parentId\":\"7\"}]");
        PayloadResponse response = new PayloadResponse("success", map);
        return ServerlessResponse.builder().setObjectBody(response).build();
    }

    private void initApplication() {
        logger.debug("init application.");
        injector = getInjector().createChildInjector(new AdvisorModule());

        // inject instance
        if (articleQueryApi == null) {
            articleQueryApi = injector.getInstance(ArticleQueryApi.class);
        }
    }
}
