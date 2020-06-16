package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface MealRepository {
    // null if not found, when updated
    Meal createMeal(Meal meal, int userId);

    Meal updateMeal(Meal meal, int idMeal, int userId);

    // false if not found
    boolean delete(int id, int userId);

    // null if not found
    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> filterByUserId(Map<Integer, Meal> repository, Integer userId);

    List<Meal> sortMealOfDate(List<Meal> repository);

}
