package gl.linpeng.serverless.advisor.service;

import gl.linpeng.gf.base.PageInfo;

/**
 * @author lin.peng
 * @since 1.0
 **/
public interface HealthAdvisorService {

    PageInfo getQuerySuggests(String query, Integer pageSize, Integer page);

    PageInfo queryAdvises(Long[] ids, String type, String adviseType, Integer pageSize, Integer page);

}
