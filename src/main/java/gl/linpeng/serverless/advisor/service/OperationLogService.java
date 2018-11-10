package gl.linpeng.serverless.advisor.service;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.controller.request.OperationLogRequest;
import gl.linpeng.serverless.advisor.model.OperationLog;
import gl.linpeng.serverless.advisor.model.StatVo;

import java.util.List;

/**
 * Operation log service
 *
 * @author lin.peng
 * @since 1.0
 */
public interface OperationLogService {

    /**
     * Save operation log
     *
     * @param operationLog model
     * @return model after save
     */
    OperationLog saveOperationLog(OperationLogRequest operationLog);

    /**
     * find operation log by id
     *
     * @param id unique id
     * @return model
     */
    OperationLog getOperationLogById(Long id);


    /**
     * query operation logs in page
     *
     * @param operationType   operation type
     * @param operationTarget operation target id
     * @param pageSize        page size
     * @param page            page number
     * @return page info object
     */
    PageInfo query(int operationType, Long operationTarget, Integer pageSize, Integer page);

    /**
     * Stat
     *
     * @param operationTargetType target type
     * @param operationTarget     log target id
     * @return
     */
    List<StatVo> stat(Long operationTargetType, Long operationTarget);
}
