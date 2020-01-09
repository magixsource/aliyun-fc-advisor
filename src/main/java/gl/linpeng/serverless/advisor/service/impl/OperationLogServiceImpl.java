package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.controller.request.OperationLogRequest;
import gl.linpeng.serverless.advisor.model.OperationLog;
import gl.linpeng.serverless.advisor.model.StatVo;
import gl.linpeng.serverless.advisor.service.OperationLogService;
import org.apache.bval.util.StringUtils;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Operation log service implements class
 *
 * @author lin.peng
 * @since 1.0
 **/

public class OperationLogServiceImpl implements OperationLogService {
    private static final Logger logger = LoggerFactory.getLogger(OperationLogServiceImpl.class);

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

    @Override
    public List<StatVo> batchStat(Long id, Long operationTargetType, Set<Long> operationTargetIds) {
        logger.info("batch stat,{},{}", operationTargetType, operationTargetIds);
        Base.open();
        Connection connection = Base.connection();
        String fullSql = "SELECT " +
            "t2.disease_id," +
            "t2.principleitem_id as id," +
            "t.operation_type AS operation_type," +
            "count(t.id) AS cnt " +
            "FROM " +
            "operation_logs t," +
            "principles t2 " +
            "WHERE " +
            "t2.disease_id = {diseaseId} "+
            "AND t2.id = t.operation_target_id " +
            "AND t.operation_target_type = {targetType} " +
            "and t2.principleitem_id in ({ids}) " +
            "GROUP BY " +
            "t2.disease_id," +
            "t2.principleitem_id," +
            "t.operation_type";
        fullSql = fullSql.replace("{diseaseId}", id.toString());
        fullSql = fullSql.replace("{targetType}", operationTargetType.toString());
        fullSql = fullSql.replace("{ids}", StringUtils.join(operationTargetIds, ","));
        logger.info("full sql,{}", fullSql);
        List<StatVo> list = null;
        try {
            PreparedStatement statement = connection.prepareStatement(fullSql);
            ResultSet resultSet = statement.executeQuery();

            list = new ArrayList<>(resultSet.getFetchSize());
            while (resultSet.next()) {
                StatVo vo = new StatVo();
                vo.setId(resultSet.getLong("disease_id"));
                vo.setPrincipleId(resultSet.getLong("id"));
                vo.setType(resultSet.getInt("operation_type"));
                vo.setNumber(resultSet.getLong("cnt"));
                list.add(vo);
            }
            logger.debug("result:{}", list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Base.close();
        return list;
    }
}
