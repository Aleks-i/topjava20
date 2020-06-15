package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal create_update(Meal meal) {
        log.info("delete {}", meal);
        checkNew(meal);
        if (meal.isNew()) {
            return service.createMeal(meal, SecurityUtil.authUserId());
        }
        return service.update(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Collection<Meal> getAll() {
        log.info("getAll");
        return service.getAll(SecurityUtil.authUserId());
    }
}