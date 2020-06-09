package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.DAO.MealsDao;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealController {
    MealsDao mealsDao = new MealsDao();

    public List<MealTo> getMealTo() {
        List<MealTo> mealTo = MealsUtil.sheetMeal(mealsDao.getMeals(), 2000);

        return mealTo;
    }
}
