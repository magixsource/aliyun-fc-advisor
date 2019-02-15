package gl.linpeng.serverless.advisor.service;

import gl.linpeng.serverless.advisor.model.User;

/**
 * @author lin.peng
 * @since 1.0
 **/
public interface UserService {
    /**
     * get or save it
     *
     * @param openId
     * @return
     */
    User getOrSave(String openId);
}
