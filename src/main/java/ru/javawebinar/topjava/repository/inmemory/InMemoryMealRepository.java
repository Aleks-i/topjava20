package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();

    {
        for (Meal meal : MealsUtil.MEALS) {
            saveMeal(meal, 1);
        }
    }

    @Override
    public Meal saveMeal(Meal meal, int userId) {
        //meal.setId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal updateMeal(Meal meal, int userId) {
        Meal upateMeal = repository.get(meal.getId());

        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(id).getUserId() == userId) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id).getUserId() == userId) {
            return repository.get(id);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> userListFilterByUserId = filterByUserId(repository, userId);
        return sortMealOfDate(userListFilterByUserId);
    }

    public List<Meal> filterByUserId(Map<Integer, Meal> repository, Integer userId) {
        return repository.values().stream()
            .filter(mealId -> mealId.getUserId() == userId)
            .collect(Collectors.toList());
    }

    public List<Meal> sortMealOfDate(List< Meal> repository) {
        return repository.stream()
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}

