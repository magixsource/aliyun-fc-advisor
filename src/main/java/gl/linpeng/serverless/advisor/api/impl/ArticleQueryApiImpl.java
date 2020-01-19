package gl.linpeng.serverless.advisor.api.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.api.ArticleQueryApi;
import gl.linpeng.serverless.advisor.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


/**
 * article query api implement
 *
 * @author lin.peng
 * @since 1.0
 **/
public class ArticleQueryApiImpl implements ArticleQueryApi {
    private static final Logger logger = LoggerFactory.getLogger(ArticleQueryApiImpl.class);

    @Inject
    ArticleService articleService;

    @Override
    public PageInfo getNewsByCatalogId(Long catalogId, Integer pageSize, Integer page) {
        logger.info("query news by catalog id {}", catalogId);
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        return articleService.query(catalogId, pageSize, page);
    }
}
