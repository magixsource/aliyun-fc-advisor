package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.service.TagService;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tag service implement
 *
 * @author lin.peng
 * @since 1.0
 **/
public class TagServiceImpl implements TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);
    public static final String SQL_QUERY_TAGS_BY_CATALOG_ID = "SELECT t.* from tags t WHERE t.catalog_id = {catalog_id}";

    @Override
    public PageInfo queryTagsByCatalogId(Long catalogId, Integer pageSize, Integer page) {
        String tempSql = SQL_QUERY_TAGS_BY_CATALOG_ID.replace("{catalog_id}", catalogId.toString());
        Base.open();
        Connection connection = Base.connection();
        List list = new ArrayList();
        Long total = 0L;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(count(tempSql));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    total = resultSet.getLong(1);
                }
            }

            if (total > 0) {
                preparedStatement = connection.prepareStatement(page(tempSql, pageSize, page));
                resultSet = preparedStatement.executeQuery();

                if (resultSet != null) {
                    while (resultSet.next()) {
                        Map<String, Object> objectMap = new HashMap<>();
                        Long id = resultSet.getLong("id");
                        String title = resultSet.getString("title");
                        String dbCatalogId = resultSet.getString("catalog_id");
                        objectMap.put("id", id);
                        objectMap.put("title", title);
                        objectMap.put("catalogId", dbCatalogId);
                        list.add(objectMap);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Query Tag by catalog got error. {}", e);
        }
        Base.close();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(page);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(total);
        pageInfo.setList(list);
        return pageInfo;
    }

    private String count(String sql) {
        return "select count(1) as ct from (" + sql + ") as tmp";
    }

    private String page(String sql, Integer pageSize, Integer page) {
        return sql + " limit " + pageSize + " offset " + ((page - 1) * pageSize);
    }
}
