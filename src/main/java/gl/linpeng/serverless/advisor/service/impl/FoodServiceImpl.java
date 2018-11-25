package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.serverless.advisor.model.Food;
import gl.linpeng.serverless.advisor.service.FoodService;
import org.javalite.activejdbc.Base;

import java.util.Date;

/**
 * Food service implement class
 *
 * @author lin.peng
 * @since 1.0
 **/
public class FoodServiceImpl implements FoodService {

    @Override
    public Food getFoodById(Long id) {
        Base.open();
        Food food = Food.findById(id);
        Base.close();
        return food;
    }

    @Override
    public Food save(String name, String enName, String region, String department, String summary, String content) {
        Base.open();
        Food food = new Food();
        food.set("name", name).set("en_name", enName).set("region", region).set("department", department).set("summary", summary).set("content", content).set("create_time", new Date()).saveIt();
        Base.close();
        return food;
    }
}
