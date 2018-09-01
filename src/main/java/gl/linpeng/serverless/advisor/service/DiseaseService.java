package gl.linpeng.serverless.advisor.service;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.model.Disease;

/**
 * @author lin.peng
 * @since 1.0
 **/
public interface DiseaseService {

    Disease getDiseaseById(Long id);

    PageInfo query(String query, Integer pageSize, Integer page);
}
