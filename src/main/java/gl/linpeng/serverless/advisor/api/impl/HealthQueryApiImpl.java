package gl.linpeng.serverless.advisor.api.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.controller.request.UserFeatureQueryRequest;
import gl.linpeng.serverless.advisor.controller.request.UserFeatureRequest;
import gl.linpeng.serverless.advisor.model.*;
import gl.linpeng.serverless.advisor.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * Health query api implement
 *
 * @author lin.peng
 * @since 1.0
 **/
public class HealthQueryApiImpl implements HealthQueryApi {
    private static final Logger logger = LoggerFactory.getLogger(HealthQueryApiImpl.class);
    public static final Long TAG_CATALOG_FOOD = 1L;

    @Inject
    DiseaseService diseaseService;
    @Inject
    IngredientService ingredientService;
    @Inject
    HealthAdvisorService healthAdvisorService;
    @Inject
    FoodService foodService;
    @Inject
    FoodMaterialService foodMaterialService;
    @Inject
    UserService userService;

    @Override
    public PageInfo getQuerySuggests(String query, Integer pageSize, Integer page) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        return healthAdvisorService.getQuerySuggests(query, pageSize, page);
    }

    @Override
    public PageInfo query(String query, String type, Integer pageSize, Integer page) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        if ("i".equalsIgnoreCase(type)) {
            return ingredientService.query(query, pageSize, page);
        } else if ("d".equalsIgnoreCase(type)) {
            return diseaseService.query(query, pageSize, page);
        } else if ("f".equalsIgnoreCase(type)) {
            return foodService.query(query, pageSize, page);
        } else {
            //TODO auto determine f or d
            logger.error("unsupported type {}", type);
            throw new UnsupportedOperationException("unsupported type " + type);
        }
    }

    @Override
    public PageInfo queryAdvises(Long id, String type, String adviseType, Integer pageSize, Integer page) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        return healthAdvisorService.queryAdvises(id, type, adviseType, pageSize, page);
    }

    @Override
    public Disease getDiseaseById(Long id) {
        return diseaseService.getDiseaseById(id);
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientService.getIngredientById(id);
    }

    @Override
    public Food getFoodById(Long id) {
        return foodService.getFoodById(id);
    }

    @Override
    public PageInfo queryFood(String name, Integer pageSize, Integer page) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        return foodService.query(name, pageSize, page);
    }

    @Override
    public List getComponentsByFoodId(Long id) {
        return foodMaterialService.queryFoodMaterialByFoodId(id);
    }

    @Override
    public List getTagsByFoodId(Long id) {
        return foodService.getAllTagByFoodId(id, TAG_CATALOG_FOOD);
    }

    @Override
    public PageInfo queryFoodByTagName(String name, Integer pageSize, Integer page) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        return foodService.queryByTag(name, TAG_CATALOG_FOOD, pageSize, page);
    }

    @Override
    public PageInfo queryFoodByIngredientId(Long ingredientId, Integer pageSize, Integer page) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        return foodService.queryByIngredient(ingredientId, pageSize, page);
    }

    @Override
    public User getOrSaveUser(String openId) {
        return userService.getOrSave(openId);
    }

    @Override
    public UserFeature saveUserFeature(UserFeatureRequest dto) {
        // check user
        checkUser(dto);

        Integer userId = dto.getUserId();
        Integer type = dto.getType();
        Integer diseaseId = dto.getDiseaseId();
        Integer foodId = dto.getFoodId();
        Integer ingredientId = dto.getIngredientId();

        UserFeature userFeature = userService.saveFeature(userId, type, diseaseId, foodId, ingredientId);
        return userFeature;
    }

    private void checkUser(UserFeatureRequest dto) {
        // check if user exist
        String openId = dto.getOpenId();
        User user = userService.getUser(openId);
        if (user == null) {
            logger.error("User {} is not exist.", openId);
            throw new IllegalArgumentException("User is not exist.");
        }
        Integer userId = dto.getUserId();
        if (!user.getInteger("id").equals(userId)) {
            logger.error("User {} is not match {}.", openId, userId);
            throw new IllegalArgumentException("User is not match.");
        }
    }

    @Override
    public void deleteUserFeature(UserFeatureRequest dto) {
        // check user
        checkUser(dto);
        Integer userId = dto.getUserId();
        Integer type = dto.getType();
        Integer diseaseId = dto.getDiseaseId();
        Integer foodId = dto.getFoodId();
        Integer ingredientId = dto.getIngredientId();
        userService.deleteUserFeature(userId, type, diseaseId, foodId, ingredientId);
    }

    @Override
    public PageInfo queryUserFeature(UserFeatureQueryRequest dto) {
        checkUser(dto);
        Integer pageSize = dto.getPageSize();
        Integer page = dto.getPage();

        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        Integer userId = dto.getUserId();
        Integer type = dto.getType();

        return userService.queryUserFeature(userId, type, pageSize, page);
    }
}
