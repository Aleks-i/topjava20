package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDao {

    List<MealTo> getMealTo();

    void addMeal(Meal meal);

    void deleteMeal(int id);

    void updateMeal(int id, LocalDateTime localDateTime, String description, int calories);
}
