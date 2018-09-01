package gl.linpeng.serverless.advisor.model;


import org.javalite.activejdbc.Model;

/**
 * Principle item model
 *
 * @author lin.peng
 */
public class PrincipleItem extends Model {

    private Integer adverb;
    private Integer type;
    private String target;

    public Integer getAdverb() {
        return adverb;
    }

    public void setAdverb(Integer adverb) {
        this.adverb = adverb;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }


}
