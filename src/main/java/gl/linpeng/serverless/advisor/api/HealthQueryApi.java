package gl.linpeng.serverless.advisor.api;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.model.Disease;
import gl.linpeng.serverless.advisor.model.Ingredient;

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
     * @param type     f: food , d: disease
     * @param pageSize page size default 10
     * @param page     page default 1
     * @return foods and diseases
     */
    PageInfo query(String query, String type, Integer pageSize, Integer page);

    /**
     * get health advises
     *
     * @param id         food id or disease id
     * @param type       f|d
     * @param adviseType more|less|forbidden
     * @param pageSize   default 10
     * @param page       default 1
     * @return advises
     */
    PageInfo queryAdvises(Long id, String type, String adviseType, Integer pageSize, Integer page);

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
}
