package gl.linpeng.serverless.advisor.controller.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class IdQueryRequest {

    @NotNull
    @Min(1)
    private Long id;

    private String openId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
