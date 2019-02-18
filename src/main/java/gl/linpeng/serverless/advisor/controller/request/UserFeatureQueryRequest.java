package gl.linpeng.serverless.advisor.controller.request;

/**
 * User feature request dto
 *
 * @author lin.peng
 * @since 1.0
 **/
public class UserFeatureQueryRequest extends UserFeatureRequest {

    private Integer pageSize;
    private Integer page;


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}
