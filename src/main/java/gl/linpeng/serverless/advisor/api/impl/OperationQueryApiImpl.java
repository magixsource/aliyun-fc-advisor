package gl.linpeng.serverless.advisor.api.impl;

import gl.linpeng.serverless.advisor.api.OperationQueryApi;
import gl.linpeng.serverless.advisor.model.StatVo;
import gl.linpeng.serverless.advisor.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class OperationQueryApiImpl implements OperationQueryApi {
    private static final Logger logger = LoggerFactory.getLogger(OperationApiImpl.class);

    @Inject
    OperationLogService operationLogService;

    @Override
    public List<StatVo> stat(Long operationTargetType, Long operationTarget) {
        return operationLogService.stat(operationTargetType, operationTarget);
    }

    @Override
    public Map<String, List<StatVo>> batchStat(Long operationTargetType, Set<Long> ids) {
        List<StatVo> list = operationLogService.batchStat(operationTargetType, ids);
        Map<String, List<StatVo>> map = list.stream().collect(Collectors.groupingBy(e-> e.getPrincipleId().toString()));
        return map;
    }
}
