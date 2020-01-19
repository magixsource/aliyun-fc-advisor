package gl.linpeng.serverless.advisor.service;

import gl.linpeng.gf.base.PageInfo;


/**
 * Article Service
 *
 * @author lin.peng
 * @since 1.0
 **/
public interface ArticleService {
    /**
     * Query article by catalog id
     *
     * @param catalogId catalog id
     * @param pageSize  page size
     * @param page      page number
     * @return articles
     */
    public PageInfo query(Long catalogId, Integer pageSize, Integer page);
}
