package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.serverless.advisor.model.Component;
import gl.linpeng.serverless.advisor.service.ComponentService;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Component service implement
 *
 * @author lin.peng
 * @since 1.0
 **/
public class ComponentServiceImpl implements ComponentService {
    private static final Logger logger = LoggerFactory.getLogger(ComponentServiceImpl.class);

    static final String sql = "select t2.* from ingredients t2,components t where t2.id = t.ingredient_id and t.food_id = {food_id}";

    @Override
    public Component save(Long foodId, Long ingredientId, int percent) {
        Base.open();
        Component component = new Component();
        component.set("food_id", foodId).set("ingredient_id", ingredientId).set("percent", percent).set("create_time", new Date()).saveIt();
        Base.close();
        return component;
    }

    @Override
    public List queryComponentByFoodId(Long id) {
        String tempSql = sql.replace("{food_id}", id.toString());
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
                objectMap.put("name", rs.getString("name"));
                objectMap.put("enName", rs.getString("en_name"));
                objectMap.put("kind", rs.getInt("kind"));
                objectMap.put("origin", rs.getString("origin"));
                list.add(objectMap);
            }
        } catch (SQLException e) {
            logger.error("QueryComponentByFoodId error {}", e);
        }
        Base.close();
        return list;
    }
}
