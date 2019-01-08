package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.common.Constants;
import gl.linpeng.serverless.advisor.model.Disease;
import gl.linpeng.serverless.advisor.model.Ingredient;
import gl.linpeng.serverless.advisor.service.HealthAdvisorService;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Health advisor service implement
 *
 * @author lin.peng
 * @since 1.0
 **/
public class HealthAdvisorServiceImpl implements HealthAdvisorService {

    @Override
    public PageInfo getQuerySuggests(String query, Integer pageSize, Integer page) {
        String sql = "SELECT t.id,t.name,t.type from (SELECT t1.id,t1.name,'i' as type from ingredients t1 UNION ALL SELECT t2.id,t2.name,'d' as type from diseases t2 UNION ALL SELECT t3.id,t3.name, 'f' as type from foods t3) t where t.name LIKE '%" + query + "%'";
        Base.open();
        Connection connection = Base.connection();
        List list = new ArrayList();
        Long total = 0L;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(count(sql));
            //connection.prepareStatement(page(sql, pageSize, page));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    total = resultSet.getLong(1);
                }
            }
            if (total > 0) {
                preparedStatement = connection.prepareStatement(page(sql, pageSize, page));
                resultSet = preparedStatement.executeQuery();

                if (resultSet != null) {
                    while (resultSet.next()) {
                        Map<String, Object> objectMap = new HashMap<>();
                        Long id = resultSet.getLong("id");
                        String name = resultSet.getString("name");
                        String type = resultSet.getString("type");
                        objectMap.put("id", id);
                        objectMap.put("name", name);
                        objectMap.put("type", type);
                        list.add(objectMap);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Base.close();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(page);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(total);
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public PageInfo queryAdvises(Long id, String type, String adviseType, Integer pageSize, Integer page) {
        String dSql = "SELECT DISTINCT(t.id),t.adverb,t.type,t.target,t3.`name` as target_name,t2.id as principle_id from principle_items t LEFT OUTER JOIN ingredients t3 on t.target = t3.id,principles t2 where t.id = t2.principleitem_id and t2.disease_id = {diseaseId} and t.adverb = {adverb}";
        String iSql = "SELECT DISTINCT(t4.id) as target,t.adverb,t.type,t4.`name` as target_name,t3.id as principle_id from principle_items t LEFT OUTER JOIN principles t3 LEFT OUTER JOIN diseases t4 on t3.disease_id = t4.id ON t3.principleitem_id = t.id,ingredients t2 where t.type = 1 and t.target = t2.id and t2.id = {ingredientId} and t.adverb = {adverb}";
        Integer adverb;
        if ("m".equalsIgnoreCase(adviseType)) {
            adverb = Constants.Adverb.MORE.getValue();
        } else if ("l".equalsIgnoreCase(adviseType)) {
            adverb = Constants.Adverb.LESS.getValue();
        } else if ("f".equalsIgnoreCase(adviseType)) {
            adverb = Constants.Adverb.NO.getValue();
        } else {
            throw new UnsupportedOperationException("unsupported adviseType. " + adviseType);
        }

        List list = new ArrayList();
        Base.open();
        Connection connection = Base.connection();
        PageInfo pageInfo = new PageInfo();
        try {
            if ("i".equalsIgnoreCase(type)) {
                getAdvisesByType(type, id, pageSize, page, iSql, adverb, list, pageInfo, connection);
            } else if ("d".equalsIgnoreCase(type)) {
                getAdvisesByType(type, id, pageSize, page, dSql, adverb, list, pageInfo, connection);
            } else {
                throw new UnsupportedOperationException("unsupported type. " + type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Base.close();
        }
        pageInfo.setList(list);
        pageInfo.setPage(page);
        pageInfo.setPageSize(pageSize);
        return pageInfo;
    }

    private void getAdvisesByType(String type, Long id, Integer pageSize, Integer page, String sql, Integer adverb, List list, PageInfo pageInfo, Connection connection) throws SQLException {
        boolean isDiseaseType = false;
        String retType = null;
        if ("i".equalsIgnoreCase(type)) {
            retType = "d";
        } else if ("d".equalsIgnoreCase(type)) {
            isDiseaseType = true;
            retType = "i";
        } else {
            throw new UnsupportedOperationException("unsupported adviseType. " + type);
        }
        PreparedStatement preparedStatement;
        if (isDiseaseType) {
            sql = sql.replace("{diseaseId}", id.toString());
        } else {
            sql = sql.replace("{ingredientId}", id.toString());
        }
        sql = sql.replace("{adverb}", adverb.toString());
        String countSql = count(sql);
        preparedStatement = connection.prepareStatement(countSql);
        ResultSet countRs = preparedStatement.executeQuery();
        Long total = null;
        while (countRs.next()) {
            total = countRs.getLong("ct");
        }

        if (total != null && total > 0) {
            ResultSet resultSet = preparedStatement.executeQuery(page(sql, pageSize, page));
            Model model;
            while (resultSet.next()) {
                if (isDiseaseType) {
                    model = new Ingredient();
                } else {
                    model = new Disease();
                }
                Long principleItemId = resultSet.getLong("id");
                Long principleId = resultSet.getLong("principle_id");
                Long targetId = resultSet.getLong("target");
                String targetName = resultSet.getString("target_name");
                model.set("name", targetName);
                Map map = model.toMap();
                map.put("id", principleItemId);
                map.put("type", retType);
                map.put("targetId", targetId);
                map.put("principleId", principleId);
                map.put("adverb", resultSet.getInt("adverb"));
                list.add(map);
            }
        }
        pageInfo.setTotal(total);
    }

    private String count(String sql) {
        return "select count(1) as ct from (" + sql + ") as tmp";
    }

    private String page(String sql, Integer pageSize, Integer page) {
        return sql + " limit " + pageSize + " offset " + ((page - 1) * pageSize);
    }
}
