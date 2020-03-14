package gl.linpeng.serverless.advisor.service;

import gl.linpeng.serverless.advisor.model.FoodMaterial;

import java.util.List;

/**
 * FoodMaterial service
 *
 * @author lin.peng
 * @since 1.0
 */
public interface FoodMaterialService {

    /**
     * Save component
     *
     * @param foodId       food id
     * @param ingredientId ingredient id
     * @param percent      percent of ingredient
     * @return food material
     */
    FoodMaterial save(Long foodId, Long ingredientId, int percent);

    /**
     * Get food material by food is
     *
     * @param id food id
     * @return food material set
     */
    List queryFoodMaterialByFoodId(Long id);


}
