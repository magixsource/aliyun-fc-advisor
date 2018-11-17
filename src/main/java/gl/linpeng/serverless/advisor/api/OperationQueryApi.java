package gl.linpeng.serverless.advisor.api;


import gl.linpeng.serverless.advisor.model.StatVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * batch stat
     *
     * @param operationTargetType target type
     * @param ids                 id sets
     * @return
     */
    Map<String, List<StatVo>> batchStat(Long operationTargetType, Set<Long> ids);
}
