package gl.linpeng.serverless.advisor.model;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class StatVo {

    private Long id;
    private Long principleId;

    private Integer type;
    private Long number;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrincipleId() {
        return principleId;
    }

    public void setPrincipleId(Long principleId) {
        this.principleId = principleId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }


}
