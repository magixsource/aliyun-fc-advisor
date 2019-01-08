package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.model.Disease;
import gl.linpeng.serverless.advisor.model.Food;
import gl.linpeng.serverless.advisor.service.FoodService;
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

/**
 * Food service implement class
 *
 * @author lin.peng
 * @since 1.0
 **/
public class FoodServiceImpl implements FoodService {
    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);
    public static final String SQL_GET_ALL_TAG_BY_FOOD_ID = "SELECT t2.id,t2.title from tag_nodes t,tags t2 where t2.id = t.tag_id and t.target_id = {food_id} and t.tag_catalog_id = {tag_catalog_id}";
    public static final String SQL_QUERY_FOODS_BY_TAGNAME = "SELECT t.* from foods t,tag_nodes t2,tags t3 WHERE t2.target_id = t.id and t2.tag_catalog_id = {tag_catalog_id} and  t3.id = t2.tag_id and t3.title = '{tag_name}'";
    public static final String SQL_QUERY_FOODS_BY_INGREDIENT = "SELECT t2.* from food_materials t,foods t2 where t2.id = t.food_id and t.ingredient_id = {ingredient_id}";

    @Override
    public Food getFoodById(Long id) {
        Base.open();
        Food food = Food.findById(id);
        Base.close();
        return food;
    }

    @Override
    public Food save(String name, String enName, String region, String department, String summary, String content) {
        Base.open();
        Food food = new Food();
        food.set("name", name).set("en_name", enName).set("region", region).set("department", department).set("summary", summary).set("content", content).set("create_time", new Date()).saveIt();
        Base.close();
        return food;
    }

    @Override
    public PageInfo query(String name, Integer pageSize, Integer page) {
        Base.open();
        Paginator p = new Paginator(Food.class, pageSize, "name like ?", (name + "%")).orderBy("name desc");
        List<Disease> list = p.getPage(page);
        PageInfo pageInfo = new PageInfo();
        List<Map<String, Object>> mapList = ((LazyList<Disease>) list).toMaps();
        pageInfo.setList(mapList);
        pageInfo.setTotal(p.getCount());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPage(page);
        Base.close();
        return pageInfo;
    }

    @Override
    public PageInfo queryByTag(String tagName, Long tagCatalogId, Integer pageSize, Integer page) {
        String tempSql = SQL_QUERY_FOODS_BY_TAGNAME.replace("{tag_name}", tagName).replace("{tag_catalog_id}", tagCatalogId.toString());
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
                        String name = resultSet.getString("name");
                        String summary = resultSet.getString("summary");
                        String content = resultSet.getString("content");
                        String tip = resultSet.getString("tip");
                        objectMap.put("id", id);
                        objectMap.put("name", name);
                        objectMap.put("summary", summary);
                        objectMap.put("content", content);
                        objectMap.put("tip", tip);
                        list.add(objectMap);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("QueryByTag got error. {}", e);
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
    public List getAllTagByFoodId(Long foodId, Long tagCatalogId) {
        String tempSql = SQL_GET_ALL_TAG_BY_FOOD_ID.replace("{food_id}", foodId.toString()).replace("{tag_catalog_id}", tagCatalogId.toString());
        Base.open();
        Connection connection = Base.connection();
        List<Map<String, Object>> list = null;
        try {
            PreparedStatement ps = connection.prepareStatement(tempSql);
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<>(rs.getFetchSize());
            while (rs.next()) {
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("id", rs.getLong("id"));
                objectMap.put("title", rs.getString("title"));
                list.add(objectMap);
            }
        } catch (SQLException e) {
            logger.error("GetAllTagByFoodId error {}", e);
        }
        Base.close();
        return list;
    }

    @Override
    public PageInfo queryByIngredient(Long ingredientId, Integer pageSize, Integer page) {
        String tempSql = SQL_QUERY_FOODS_BY_INGREDIENT.replace("{ingredient_id}", ingredientId.toString());
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
                        String name = resultSet.getString("name");
                        String summary = resultSet.getString("summary");
                        String content = resultSet.getString("content");
                        String tip = resultSet.getString("tip");
                        objectMap.put("id", id);
                        objectMap.put("name", name);
                        objectMap.put("summary", summary);
                        objectMap.put("content", content);
                        objectMap.put("tip", tip);
                        list.add(objectMap);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("QueryByIngredient got error. {}", e);
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
