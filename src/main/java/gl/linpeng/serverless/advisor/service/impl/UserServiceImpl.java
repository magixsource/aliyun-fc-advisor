package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.serverless.advisor.common.Constants;
import gl.linpeng.serverless.advisor.model.User;
import gl.linpeng.serverless.advisor.model.UserFeature;
import gl.linpeng.serverless.advisor.service.UserService;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

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
}
