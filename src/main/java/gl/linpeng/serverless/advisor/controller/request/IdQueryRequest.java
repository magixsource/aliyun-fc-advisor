package gl.linpeng.serverless.advisor.controller.request;

import gl.linpeng.gf.base.ServerlessRequest;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class IdQueryRequest extends ServerlessRequest {

    @NotNull
    @Min(1)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
