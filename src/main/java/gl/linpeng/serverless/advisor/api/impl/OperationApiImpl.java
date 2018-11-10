package gl.linpeng.serverless.advisor.api.impl;

import gl.linpeng.serverless.advisor.api.OperationApi;
import gl.linpeng.serverless.advisor.controller.request.OperationLogRequest;
import gl.linpeng.serverless.advisor.model.OperationLog;
import gl.linpeng.serverless.advisor.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Operation api implement class
 *
 * @author lin.peng
 * @since 1.0
 **/
public class OperationApiImpl implements OperationApi {

    private static final Logger logger = LoggerFactory.getLogger(OperationApiImpl.class);

    @Inject
    OperationLogService operationLogService;

    @Override
    public OperationLog saveOperationLog(OperationLogRequest operationLog) {
        return operationLogService.saveOperationLog(operationLog);
    }
}
