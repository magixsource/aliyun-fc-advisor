package gl.linpeng.serverless.advisor.service;

import gl.linpeng.serverless.advisor.model.Component;

import java.util.List;

/**
 * Component service
 *
 * @author lin.peng
 * @since 1.0
 */
public interface ComponentService {

    /**
     * Save component
     *
     * @param foodId       food id
     * @param ingredientId ingredient id
     * @param percent      percent of ingredient
     * @return component
     */
    Component save(Long foodId, Long ingredientId, int percent);

    /**
     * Get component by food is
     *
     * @param id food id
     * @return component set
     */
    List<Component> queryComponentByFoodId(Long id);


}
