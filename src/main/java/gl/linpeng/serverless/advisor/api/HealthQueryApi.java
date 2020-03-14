package gl.linpeng.serverless.advisor.api;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.controller.request.UserFeatureQueryRequest;
import gl.linpeng.serverless.advisor.controller.request.UserFeatureRequest;
import gl.linpeng.serverless.advisor.model.*;

import java.util.List;

/**
 * Health domain api
 *
 * @author lin.peng
 * @since 1.0
 **/
public interface HealthQueryApi {

    /**
     * Get Query suggest
     * 1. query food and disease
     * 2. wrapper to list
     *
     * @param query    input content
     * @param page     page default 1
     * @param pageSize page size default 10
     * @return data set include food and disease
     */
    PageInfo getQuerySuggests(String query, Integer pageSize, Integer page);

    /**
     * Health Common Query
     *
     * @param query    input content
     * @param type     f: food , d: disease,i: ingredient
     * @param pageSize page size default 10
     * @param page     page default 1
     * @return foods and diseases
     */
    PageInfo query(String query, String type, Integer pageSize, Integer page);

    /**
     * get health advises
     *
     * @param ids        food id or disease id
     * @param type       f|d|i
     * @param adviseType more|less|forbidden
     * @param pageSize   default 10
     * @param page       default 1
     * @return advises
     */
    PageInfo queryAdvises(Long[] ids, String type, String adviseType, Integer pageSize, Integer page);

    /**
     * Get disease by id
     *
     * @param id disease id
     * @return disease
     */
    Disease getDiseaseById(Long id);

    /**
     * Get ingredient by id
     *
     * @param id ingredient id
     * @return ingredient
     */
    Ingredient getIngredientById(Long id);

    /**
     * Get food by id
     *
     * @param id food id
     * @return food
     */
    Food getFoodById(Long id);

    /**
     * Query food by name
     *
     * @param name     food name
     * @param pageSize page size
     * @param page     page number
     * @return food page info
     */
    PageInfo queryFood(String name, Integer pageSize, Integer page);

    /**
     * Get all material of food
     *
     * @param id food id
     * @return collects of material
     */
    List getComponentsByFoodId(Long id);

    /**
     * Get all tag of food
     *
     * @param id food id
     * @return collects of tag
     */
    List getTagsByFoodId(Long id);

    /**
     * Query food by tag name
     *
     * @param name     tag name
     * @param pageSize page size
     * @param page     page number
     * @return foods
     */
    PageInfo queryFoodByTagName(String name, Integer pageSize, Integer page);

    /**
     * Query food by ingredient id
     *
     * @param ingredientId ingredient id
     * @param pageSize     page size
     * @param page         page number
     * @return foods
     */
    PageInfo queryFoodByIngredientId(Long ingredientId, Integer pageSize, Integer page);

    /**
     * Get user if user exist, save it otherwise
     *
     * @param openId we-chat openid
     * @return user model
     */
    User getOrSaveUser(String openId);

    /**
     * Save user feature
     *
     * @param dto user feature request dto
     * @return user feature model
     */
    UserFeature saveUserFeature(UserFeatureRequest dto);

    /**
     * Delete user feature
     *
     * @param dto user feature request dto
     */
    void deleteUserFeature(UserFeatureRequest dto);

    /**
     * Query User feature
     *
     * @param dto user feature query request dto
     * @return page info
     */
    PageInfo queryUserFeature(UserFeatureQueryRequest dto);

    /**
     * User recommend advise
     *
     * @param dto request dto
     * @return page info
     */
    PageInfo userRecommend(UserFeatureQueryRequest dto);

    /**
     * Query Tags by Catalog id
     *
     * @param catalogId catalog id
     * @param pageSize  page size
     * @param page      page number
     * @return tags
     */
    PageInfo queryTagsByCatalogId(Long catalogId, Integer pageSize, Integer page);
}
