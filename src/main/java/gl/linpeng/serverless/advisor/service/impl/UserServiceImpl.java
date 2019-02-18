package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.common.Constants;
import gl.linpeng.serverless.advisor.model.User;
import gl.linpeng.serverless.advisor.model.UserFeature;
import gl.linpeng.serverless.advisor.service.UserService;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * User service implement
 *
 * @author lin.peng
 * @since 1.0
 **/
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User getOrSave(String openId) {
        Base.open();
        User user = User.findFirst("open_id = ?", openId);
        if (user == null) {
            logger.info("Could not find user {},There will create new one.", openId);
            user = new User();
            Date now = new Date();
            user.setString("open_id", openId).setTimestamp("create_time", now).setTimestamp("update_time", now).saveIt();
        }
        Base.close();
        return user;
    }

    @Override
    public Boolean isExist(String openId) {
        Base.open();
        boolean result = false;
        User user = User.findFirst("open_id = ?", openId);
        if (user != null) {
            result = true;
        }
        Base.close();
        return result;
    }

    @Override
    public User getUser(String openId) {
        Base.open();
        User user = User.findFirst("open_id = ?", openId);
        Base.close();
        return user;
    }

    @Override
    public UserFeature saveFeature(Integer userId, Integer type, Integer diseaseId, Integer foodId, Integer ingredientId) {
        Base.open();
        UserFeature userFeature = new UserFeature();
        Date now = new Date();
        userFeature.setInteger("user_id", userId).setInteger("type", type).setInteger("dr", 0).setTimestamp("create_time", now).setTimestamp("update_time", now);
        if (Constants.UserFeatureType.DISEASE.getValue().equals(type)) {
            userFeature.setInteger("disease_id", diseaseId);
        }
        if (Constants.UserFeatureType.FOOD.getValue().equals(type)) {
            userFeature.setInteger("food_id", foodId);
        }
        if (Constants.UserFeatureType.INGREDIENT.getValue().equals(type)) {
            userFeature.setInteger("ingredient_id", ingredientId);
        }
        userFeature.saveIt();
        Base.close();
        return userFeature;
    }

    @Override
    public void deleteUserFeature(Integer userId, Integer type, Integer diseaseId, Integer foodId, Integer ingredientId) {
        Base.open();
        String conditions = "user_id = ? ";
        if (Constants.UserFeatureType.DISEASE.getValue().equals(type)) {
            conditions += " and type = 1 and disease_id = " + diseaseId;
        }
        if (Constants.UserFeatureType.FOOD.getValue().equals(type)) {
            conditions += " and type = 2 and food_id = " + foodId;
        }
        if (Constants.UserFeatureType.INGREDIENT.getValue().equals(type)) {
            conditions += " and type = 3 and ingredient_id = " + ingredientId;
        }
        UserFeature.update("dr = 1", conditions, userId);
        Base.close();
    }

    @Override
    public PageInfo queryUserFeature(Integer userId, Integer type, Integer pageSize, Integer page) {
        String sql = "select t.*,t2.name as disease_name,t3.name as food_name,t4.name as ingredient_name from user_features t LEFT OUTER JOIN diseases t2 on t.disease_id = t2.id LEFT OUTER JOIN foods t3 on t.food_id = t3.id LEFT OUTER JOIN ingredients t4 on t.ingredient_id = t4.id and t.dr = 0 and t.user_id = " + userId + " and t.type = " + type + " order by create_time desc";
        Base.open();
        Connection connection = Base.connection();
        List list = new ArrayList();
        Long total = 0L;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(count(sql));
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
                        Integer resultUserId = resultSet.getInt("user_id");
                        Integer resultType = resultSet.getInt("type");
                        Integer resultDiseaseId = resultSet.getInt("disease_id");
                        Integer resultFoodId = resultSet.getInt("food_id");
                        Integer resultIngredientId = resultSet.getInt("ingredient_id");
                        String resultDiseaseName = resultSet.getString("disease_name");
                        String resultFoodName = resultSet.getString("food_name");
                        String resultIngredientName = resultSet.getString("ingredient_name");
                        objectMap.put("id", id);
                        objectMap.put("userId", resultUserId);
                        objectMap.put("type", resultType);
                        objectMap.put("diseaseId", resultDiseaseId);
                        objectMap.put("foodId", resultFoodId);
                        objectMap.put("ingredientId", resultIngredientId);
                        objectMap.put("diseaseName", resultDiseaseName);
                        objectMap.put("foodName", resultFoodName);
                        objectMap.put("ingredientName", resultIngredientName);
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


    private String count(String sql) {
        return "select count(1) as ct from (" + sql + ") as tmp";
    }

    private String page(String sql, Integer pageSize, Integer page) {
        return sql + " limit " + pageSize + " offset " + ((page - 1) * pageSize);
    }
}
