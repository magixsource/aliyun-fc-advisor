package gl.linpeng.serverless.advisor.model;


import org.javalite.activejdbc.Model;

/**
 * Principle model
 *
 * @author lin.peng
 */
public class Principle extends Model {

    private Long diseaseId;

    private Long principleItemId;

    public Principle() {
    }

    public Principle(Long diseaseId, Long principleItemId) {
        this.diseaseId = diseaseId;
        this.principleItemId = principleItemId;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Long getPrincipleItemId() {
        return principleItemId;
    }

    public void setPrincipleItemId(Long principleItemId) {
        this.principleItemId = principleItemId;
    }


}
