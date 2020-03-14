package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.serverless.advisor.model.FoodMaterial;
import gl.linpeng.serverless.advisor.service.FoodMaterialService;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * FoodMaterial service implement
 *
 * @author lin.peng
 * @since 1.0
 **/
public class FoodMaterialServiceImpl implements FoodMaterialService {
    private static final Logger logger = LoggerFactory.getLogger(FoodMaterialServiceImpl.class);

    static final String SQL_GET_ALL_MATERIAL_BY_FOOD = "select t2.* from ingredients t2,food_materials t where t2.id = t.ingredient_id and t.food_id = {food_id}";

    @Override
    public FoodMaterial save(Long foodId, Long ingredientId, int percent) {
        Base.open();
        FoodMaterial foodMaterial = new FoodMaterial();
        foodMaterial.set("food_id", foodId).set("ingredient_id", ingredientId).set("percent", percent).set("create_time", new Date()).saveIt();
        Base.close();
        return foodMaterial;
    }

    @Override
    public List queryFoodMaterialByFoodId(Long id) {
        String tempSql = SQL_GET_ALL_MATERIAL_BY_FOOD.replace("{food_id}", id.toString());
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
            logger.error("QueryFoodMaterialByFoodId error {}", e);
        }
        Base.close();
        return list;
    }
}
