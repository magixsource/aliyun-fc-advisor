package gl.linpeng.serverless.advisor.inject;

import com.google.inject.AbstractModule;
import gl.linpeng.serverless.advisor.api.HealthQueryApi;
import gl.linpeng.serverless.advisor.api.OperationApi;
import gl.linpeng.serverless.advisor.api.OperationQueryApi;
import gl.linpeng.serverless.advisor.api.impl.HealthQueryApiImpl;
import gl.linpeng.serverless.advisor.api.impl.OperationApiImpl;
import gl.linpeng.serverless.advisor.api.impl.OperationQueryApiImpl;
import gl.linpeng.serverless.advisor.service.*;
import gl.linpeng.serverless.advisor.service.impl.*;


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
        bind(FoodService.class).to(FoodServiceImpl.class);
        bind(ComponentService.class).to(ComponentServiceImpl.class);
    }
}
