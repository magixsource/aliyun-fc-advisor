package gl.linpeng.serverless.advisor.service;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.model.User;
import gl.linpeng.serverless.advisor.model.UserFeature;

/**
 * @author lin.peng
 * @since 1.0
 **/
public interface UserService {
    /**
     * get or save it
     *
     * @param openId wechat open id
     * @return user
     */
    User getOrSave(String openId);

    /**
     * Check is user exist
     *
     * @param openId wechat open id
     * @return true if user exist
     */
    Boolean isExist(String openId);

    /**
     * Get user
     *
     * @param openId wechat open id
     * @return user
     */
    User getUser(String openId);

    /**
     * Save user feature
     *
     * @param userId       user id
     * @param type         feature type
     * @param diseaseId    disease id
     * @param foodId       food id
     * @param ingredientId ingredient id
     * @param tagId        tag id
     * @return user feature
     */
    UserFeature saveFeature(Integer userId, Integer type, Integer diseaseId, Integer foodId, Integer ingredientId, Integer tagId);

    /**
     * delete user feature
     *
     * @param userId       user id
     * @param type         user feature type
     * @param diseaseId    disease id
     * @param foodId       food id
     * @param ingredientId ingredient id
     * @param tagId        tag id
     */
    void deleteUserFeature(Integer userId, Integer type, Integer diseaseId, Integer foodId, Integer ingredientId, Integer tagId);

    /**
     * Query user feature
     *
     * @param userId   user id
     * @param type     user feature type
     * @param pageSize page size
     * @param page     page
     * @return page info
     */
    PageInfo queryUserFeature(Integer userId, Integer type, Integer diseaseId, Integer foodId, Integer ingredientId, Integer pageSize, Integer page);
}
