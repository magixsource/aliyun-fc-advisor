package gl.linpeng.serverless.advisor.model;


import org.javalite.activejdbc.Model;

/**
 * Disease model
 *
 * @author lin.peng
 */
public class Disease extends Model {

    private String name;
    private String summary;
    private String department;
    private String treatment;

    public Disease() {
    }

    public Disease(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
