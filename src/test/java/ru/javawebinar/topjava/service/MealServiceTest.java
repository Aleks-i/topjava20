package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;
    @Autowired
    private UserService userService;

    private Date dateStart;
    private Date dateEnd;

    @Before
    public void setDateStart() {
        dateStart = new Date();
    }

    @After
    public void setDateEnd() {
        dateEnd = new Date();
        long timeCompletedTest = dateEnd.getTime() - dateStart.getTime();
        System.out.println("The test was completed in " + timeCompletedTest + " ms");
    }

    @Test
    @Transactional
    public void delete() throws Exception {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        User user = userService.get(USER_ID);
        meal.setUser(user);
        service.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, USER_ID));
    }

    @Test
    @Transactional
    public void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    @Transactional
    public void deleteNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL1_ID, ADMIN_ID));
    }

    @Test
    @Transactional
    public void create() throws Exception {
        Meal created = service.create(getNew(), USER_ID);
        int newId = created.id();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        User user = userService.get(USER_ID);
        newMeal.setUser(user);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    //Возможно реализовать без исключения поля user?
    @Test
    @Transactional
    public void get() throws Exception {
        MEAL_MATCHER = TestMatcher.usingFieldsComparator("user");
        User user = userService.get(ADMIN_ID);
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        service.get(ADMIN_MEAL1.getId(), ADMIN_ID).setUser(user);
        actual.setUser(user);
        MEAL_MATCHER.assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    @Transactional
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    @Transactional
    public void getNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        User user = userService.get(USER_ID);
        updated.setUser(user);
        service.update(updated, USER_ID);
        service.get(MEAL1_ID, USER_ID).setUser(user);
        MEAL_MATCHER.assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    @Transactional
    public void updateNotOwn() throws Exception {
        User user = userService.get(USER_ID);
        MEAL1.setUser(user);
        assertThrows(NotFoundException.class, () -> service.update(MEAL1, ADMIN_ID));
    }

    @Test
    @Transactional
    public void getAll() throws Exception {
        User user = userService.get(USER_ID);
        for (Meal meal : MEALS) {
            meal.setUser(user);
        }
        MEAL_MATCHER.assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    @Transactional
    public void getBetweenInclusive() throws Exception {
        User user = userService.get(USER_ID);
        MEAL3.setUser(user);
        MEAL2.setUser(user);
        MEAL1.setUser(user);
        MEAL_MATCHER.assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID),
                MEAL3, MEAL2, MEAL1);
    }

    @Test
    @Transactional
    public void getBetweenWithNullDates() throws Exception {
        for (Meal meal : MEALS) {
            meal.setUser(userService.get(USER_ID));
        }
        MEAL_MATCHER.assertMatch(service.getBetweenInclusive(null, null, USER_ID), MEALS);
    }
}