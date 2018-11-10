package gl.linpeng.serverless.advisor.api;


import gl.linpeng.serverless.advisor.model.StatVo;

import java.util.List;

/**
 * Operation query api
 *
 * @author lin.peng
 * @since 1.0
 **/
public interface OperationQueryApi {

    /**
     * Stat operation
     *
     * @param operationTargetType target type
     * @param operationTarget     target id
     * @return collection of stat
     */
    List<StatVo> stat(Long operationTargetType, Long operationTarget);
}
