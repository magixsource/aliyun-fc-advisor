package gl.linpeng.serverless.advisor.api.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.model.Disease;
import gl.linpeng.serverless.advisor.model.Ingredient;
import gl.linpeng.serverless.advisor.service.DiseaseService;
import gl.linpeng.serverless.advisor.service.HealthAdvisorService;
import gl.linpeng.serverless.advisor.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Health query api implement
 *
 * @author lin.peng
 * @since 1.0
 **/
public class HealthQueryApiImpl implements HealthQueryApi {
    private static final Logger logger = LoggerFactory.getLogger(HealthQueryApiImpl.class);

    @Inject
    DiseaseService diseaseService;
    @Inject
    IngredientService ingredientService;
    @Inject
    HealthAdvisorService healthAdvisorService;

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
        if ("f".equalsIgnoreCase(type)) {
            return ingredientService.query(query, pageSize, page);
        } else if ("d".equalsIgnoreCase(type)) {
            return diseaseService.query(query, pageSize, page);
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
}
