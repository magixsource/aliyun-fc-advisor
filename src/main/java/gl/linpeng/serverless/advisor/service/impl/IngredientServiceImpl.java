package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.model.Disease;
import gl.linpeng.serverless.advisor.model.Ingredient;
import gl.linpeng.serverless.advisor.service.IngredientService;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Paginator;

import java.util.List;

/**
 * Ingredient service implement
 *
 * @author lin.peng
 * @since 1.0
 **/
public class IngredientServiceImpl implements IngredientService {

    @Override
    public Ingredient getIngredientById(Long id) {
        Base.open();
        Ingredient ingredient = Ingredient.findById(id);
        Base.close();
        return ingredient;
    }

    @Override
    public PageInfo query(String query, Integer pageSize, Integer page) {
        Base.open();
        Paginator p = new Paginator(Ingredient.class, pageSize, "name like ?", "%" + query + "%").orderBy("name desc");
        List<Disease> list = p.getPage(page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(list);
        pageInfo.setTotal(p.getCount());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPage(page);
//        Base.close();
        return pageInfo;
    }
}
