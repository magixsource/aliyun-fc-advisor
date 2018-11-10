package gl.linpeng.serverless.advisor.inject;

import com.google.inject.AbstractModule;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.api.OperationApi;
import gl.linpeng.serverless.advisor.api.OperationQueryApi;
import gl.linpeng.serverless.advisor.api.impl.HealthQueryApiImpl;
import gl.linpeng.serverless.advisor.api.impl.OperationApiImpl;
import gl.linpeng.serverless.advisor.api.impl.OperationQueryApiImpl;
import gl.linpeng.serverless.advisor.service.DiseaseService;
import gl.linpeng.serverless.advisor.service.HealthAdvisorService;
import gl.linpeng.serverless.advisor.service.IngredientService;
import gl.linpeng.serverless.advisor.service.OperationLogService;
import gl.linpeng.serverless.advisor.service.impl.DiseaseServiceImpl;
import gl.linpeng.serverless.advisor.service.impl.HealthAdvisorServiceImpl;
import gl.linpeng.serverless.advisor.service.impl.IngredientServiceImpl;
import gl.linpeng.serverless.advisor.service.impl.OperationLogServiceImpl;


/**
 * Inject configuration
 *
 * @author lin.peng
 */
public class AdvisorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HealthQueryApi.class).to(HealthQueryApiImpl.class);
        bind(DiseaseService.class).to(DiseaseServiceImpl.class);
        bind(IngredientService.class).to(IngredientServiceImpl.class);
        bind(HealthAdvisorService.class).to(HealthAdvisorServiceImpl.class);
        bind(OperationApi.class).to(OperationApiImpl.class);
        bind(OperationLogService.class).to(OperationLogServiceImpl.class);
        bind(OperationQueryApi.class).to(OperationQueryApiImpl.class);
    }
}
