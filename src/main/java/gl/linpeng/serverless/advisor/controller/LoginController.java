package gl.linpeng.serverless.advisor.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.google.inject.Injector;
import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.controller.FunctionController;
import gl.linpeng.serverless.advisor.controller.request.BaseQueryRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Login controller
 * Proxy tencent wechat login api
 *
 * @author lin.peng
 * @since 1.0
 **/
public class LoginController extends FunctionController<BaseQueryRequest, ServerlessRequest, ServerlessResponse> implements PojoRequestHandler<BaseQueryRequest, ServerlessResponse> {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String WECHAT_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private Injector injector;

    private String appId = "wx2a6b398bfba3ab6b";
    private String appSecret = "2e96438aa56b1ae52867f3e2404b17a4";
    private String grantType = "authorization_code";

    @Override
    public ServerlessResponse handleRequest(BaseQueryRequest baseQueryRequest, Context context) {
        logger.debug("recieve api request {}", JSON.toJSONString(baseQueryRequest));
        getFunction().getFunctionContext().put("ctx", context);
        ServerlessRequest serverlessRequest = new ServerlessRequest.Builder().setObjectBody(baseQueryRequest).build();
        ServerlessResponse serverlessResponse = handler(serverlessRequest);
        return serverlessResponse;
    }

    @Override
    public ServerlessResponse internalHandle(BaseQueryRequest dto) {
        if (dto == null) {
            logger.error("bad request {}", JSON.toJSONString(dto));
            throw new IllegalArgumentException("Bad request.");
        }
        String q = dto.getQ();
        if (q == null || q.trim().isEmpty()) {
            throw new IllegalArgumentException("Bad request.");
        }


        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(WECHAT_AUTH_URL + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + q + "&grant_type=" + grantType);
        CloseableHttpResponse response = null;
        String responseStr = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            logger.info("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                responseStr = EntityUtils.toString(responseEntity);
                logger.info("响应内容为:" + responseStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> map = new HashMap<>(1);
        map.put("data", responseStr);
        PayloadResponse payloadResponse = new PayloadResponse("success", map);
        return ServerlessResponse.builder().setObjectBody(payloadResponse).build();
    }

}
