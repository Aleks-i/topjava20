package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MealDaoImpl implements MealDao {
    private static final MealDaoImpl INSTANCE = new MealDaoImpl();
    private static final List<Meal> meals = new ArrayList<>();

    private MealDaoImpl() {}

    public static MealDaoImpl getINSTANCE() {
        return INSTANCE;
    }

    static {
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public synchronized List<MealTo> getMealTo() {
        return MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
    }

    @Override
    public synchronized void addMeal(Meal meal) {
        meals.add(meal);
    }

    @Override
    public synchronized void deleteMeal(int id) {
        for(Meal meal : meals) {
            if (id == meal.getId()) {
                meals.remove(meal);
                break;
            }
            Meal.identificator.incrementAndGet();
        }
    }

    @Override
    public synchronized void updateMeal(int id, LocalDateTime dateTime, String description, int calories) {
        meals.get(id).setDateTime(dateTime);
        meals.get(id).setDescription(description);
        meals.get(id).setCalories(calories);
    }
}
