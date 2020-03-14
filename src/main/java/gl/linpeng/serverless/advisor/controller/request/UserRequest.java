package gl.linpeng.serverless.advisor.controller.request;

import javax.validation.constraints.NotNull;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class UserRequest {
    private int id;
    private String name;
    private int gender;

    @NotNull
    private String openId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
