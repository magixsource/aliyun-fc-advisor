package gl.linpeng.serverless.advisor.controller.request;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class IdSetQueryRequest extends BaseQueryRequest {
    private Long[] ids;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
