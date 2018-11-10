package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.controller.request.OperationLogRequest;
import gl.linpeng.serverless.advisor.model.OperationLog;
import gl.linpeng.serverless.advisor.model.StatVo;
import gl.linpeng.serverless.advisor.service.OperationLogService;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Paginator;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Operation log service implements class
 *
 * @author lin.peng
 * @since 1.0
 **/

public class OperationLogServiceImpl implements OperationLogService {

    @Override
    public OperationLog saveOperationLog(OperationLogRequest operationLog) {
        Base.open();
        OperationLog log = new OperationLog();
        log.set("operation_type", operationLog.getOperationType()).set("operation_target_type", operationLog.getOperationTargetType()).set("operation_target_id", operationLog.getOperationTargetId()).set("operation_time", new Date()).saveIt();
        Base.close();
        return log;
    }

    @Override
    public OperationLog getOperationLogById(Long id) {
        Base.open();
        OperationLog operationLog = OperationLog.findById(id);
        Base.close();
        return operationLog;
    }

    @Override
    public PageInfo query(int operationType, Long operationTarget, Integer pageSize, Integer page) {
        Base.open();
        Paginator p = new Paginator(OperationLog.class, pageSize, "operation_type = ? and operation_target = ?", operationType, operationTarget).orderBy("id desc");
        List<OperationLog> list = p.getPage(page);
        PageInfo pageInfo = new PageInfo();
        List<Map<String, Object>> mapList = ((LazyList<OperationLog>) list).toMaps();

        pageInfo.setList(mapList);
        pageInfo.setTotal(p.getCount());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPage(page);
        Base.close();
        return pageInfo;
    }


    @Override
    public List<StatVo> stat(Long operationTargetType, Long operationTarget) {
        Base.open();

        LazyList<OperationLog> list = OperationLog.findBySQL("SELECT " +
            "t.operation_type AS operation_type," +
            "count(1) AS operation_target_id " +
            "FROM " +
            "operation_logs t " +
            "WHERE " +
            "t.operation_target_id = ? " +
            "and t.operation_target_type = ? " +
            "GROUP BY " +
            "t.operation_type", operationTarget, operationTargetType);
        List<Map<String, Object>> mapList = list.toMaps();
        Base.close();

        List<StatVo> result = mapList.stream().map(m -> {
            StatVo vo = new StatVo();
            vo.setType(Integer.valueOf(m.getOrDefault("operation_type", "-1").toString()));
            vo.setNumber(Long.valueOf(m.getOrDefault("operation_target_id", "0").toString()));
            return vo;
        }).collect(Collectors.toList());
        return result;
    }
}
