package gl.linpeng.serverless.advisor.controller.request;

import gl.linpeng.gf.base.ServerlessRequest;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class BaseQueryRequest extends ServerlessRequest {

    private String q;
    private Long id;
    private String type;
    private String filter;
    private Integer pageSize;
    private Integer page;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

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
