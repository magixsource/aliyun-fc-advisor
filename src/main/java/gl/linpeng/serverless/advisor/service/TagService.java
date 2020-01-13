package gl.linpeng.serverless.advisor.service;

import gl.linpeng.gf.base.PageInfo;

/**
 * @author lin.peng
 * @since 1.0
 **/
public interface TagService {
    /**
     * Query tags by catalog id
     *
     * @param catalogId catalog id
     * @param pageSize  page size
     * @param page      page number
     * @return tags
     */
    PageInfo queryTagsByCatalogId(Long catalogId, Integer pageSize, Integer page);
}
