package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.model.Article;
import gl.linpeng.serverless.advisor.service.ArticleService;
import org.javalite.activejdbc.Base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Article service implement class
 *
 * @author lin.peng
 * @since 1.0
 */
public class ArticleServiceImpl implements ArticleService {
    String TAG_QUERY_SQL = "select t.id from tags t,tag_nodes tn where t.id = tn.tag_id and tn.tag_catalog_id = {TAG_CATALOG_ID}";
    String ARTICLE_QUERY_SQL = "SELECT t.* FROM articles t,tag_nodes tn WHERE t.id = tn.target_id and tn.tag_id in ({TAG_IDS}) ORDER BY t.create_time DESC";

    @Override
    public PageInfo query(Long catalogId, Integer pageSize, Integer page) {
        Base.open();
        Connection connection = Base.connection();
        List list = new ArrayList();
        StringBuilder ids = new StringBuilder();
        Long total = 0L;
        try {
            String sql = TAG_QUERY_SQL.replace("{TAG_CATALOG_ID}", catalogId.toString());
            PreparedStatement preparedStatement = connection.prepareStatement(count(sql));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    total = resultSet.getLong(1);
                }
            }
            if (total > 0) {
                preparedStatement = connection.prepareStatement(page(sql, 20, 1));
                resultSet = preparedStatement.executeQuery();

                if (resultSet != null) {
                    while (resultSet.next()) {
                        Map<String, Object> objectMap = new HashMap<>();
                        Long id = resultSet.getLong("id");
                        ids.append("'").append(id).append("',");
                    }
                }
            }

            if (ids.length() > 0) {
                ids.deleteCharAt(ids.length() - 1);
                sql = ARTICLE_QUERY_SQL.replace("{TAG_IDS}", ids);
                preparedStatement = connection.prepareStatement(count(sql));
                resultSet = preparedStatement.executeQuery();
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
                            String title = resultSet.getString("title");
                            String summary = resultSet.getString("summary");
                            String content = resultSet.getString("content");
                            String tags = resultSet.getString("tags");
                            Date createTime = resultSet.getTimestamp("create_time");
                            Date updateTime = resultSet.getTimestamp("update_time");

                            objectMap.put("id", id);
                            objectMap.put("title", title);
                            objectMap.put("summary", summary);
                            objectMap.put("content", content);
                            objectMap.put("tags", tags);
                            objectMap.put("createTime", createTime);
                            objectMap.put("updateTime", updateTime);
                            list.add(objectMap);
                        }
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
    public Article get(Long id) {
        Base.open();
        Article article = Article.findById(id);
        Base.close();
        return article;
    }

    private String count(String sql) {
        return "select count(1) as ct from (" + sql + ") as tmp";
    }

    private String page(String sql, Integer pageSize, Integer page) {
        return sql + " limit " + pageSize + " offset " + ((page - 1) * pageSize);
    }
}
