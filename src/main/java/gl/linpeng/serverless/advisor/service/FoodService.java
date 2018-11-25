package gl.linpeng.serverless.advisor.service;

import gl.linpeng.serverless.advisor.model.Food;

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
}
