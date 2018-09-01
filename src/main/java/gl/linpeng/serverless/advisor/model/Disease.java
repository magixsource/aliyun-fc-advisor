package gl.linpeng.serverless.advisor.model;


import org.javalite.activejdbc.Model;

/**
 * Disease model
 *
 * @author lin.peng
 */
public class Disease extends Model {

    private String name;

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


}
