package gl.linpeng.serverless.advisor.api;


import gl.linpeng.serverless.advisor.controller.request.OperationLogRequest;
import gl.linpeng.serverless.advisor.model.OperationLog;

/* Health domain api
 *
 * @author lin.peng
 * @since 1.0
 **/
public interface OperationApi {

    OperationLog saveOperationLog(OperationLogRequest operationLog);

}
