package gl.linpeng.serverless.advisor.api;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.model.Article;

/**
 * Content domain api
 *
 * @author lin.peng
 * @since 1.0
 **/
public interface ArticleQueryApi {
    /**
     * get news by  catalog id
     *
     * @param parentCatalogId catalog id
     * @param pageSize        page size
     * @param page            page number
     * @return news
     */
    PageInfo getNewsByCatalogId(Long parentCatalogId, Integer pageSize, Integer page);

    /**
     * get news by id
     *
     * @param id article id
     * @return article object
     */
    Article getArticleById(Long id);
}
