package gl.linpeng.serverless.advisor.service;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.model.Ingredient;

/**
 * @author lin.peng
 * @since 1.0
 **/
public interface IngredientService {
    Ingredient getIngredientById(Long id);

    PageInfo query(String query, Integer pageSize, Integer page);
}
