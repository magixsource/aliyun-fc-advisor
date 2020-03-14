package gl.linpeng.serverless.advisor.service;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.model.Food;

import java.util.List;

/**
 * Food service
 *
 * @author lin.peng
 * @since 1.0
 **/
public interface FoodService {

    /**
     * Get food by id
     *
     * @param id food id
     * @return food
     */
    Food getFoodById(Long id);

    /**
     * Save food
     *
     * @param name       food name
     * @param enName     food english name
     * @param region     what country this food is
     * @param department what department in region
     * @param summary    summary description
     * @param content    main content of food
     * @return food
     */
    Food save(String name, String enName, String region, String department, String summary, String content);


    /**
     * Fuzzy search by name
     *
     * @param name     food name
     * @param pageSize page size
     * @param page     page number
     * @return foods
     */
    PageInfo query(String name, Integer pageSize, Integer page);

    /**
     * Search by tag name
     *
     * @param tagName      name of food tag
     * @param tagCatalogId tag catalog id
     * @param pageSize     page size
     * @param page         page number
     * @return foods
     */
    PageInfo queryByTag(String tagName, Long tagCatalogId, Integer pageSize, Integer page);


    /**
     * Get all tags of food
     *
     * @param foodId       food id
     * @param tagCatalogId tag catalog id
     * @return tags
     */
    List getAllTagByFoodId(Long foodId, Long tagCatalogId);


    /**
     * Search by ingredient id
     *
     * @param ingredientId ingredient id
     * @param pageSize     page size
     * @param page         page number
     * @return foods
     */
    PageInfo queryByIngredient(Long ingredientId, Integer pageSize, Integer page);
}
