package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    protected static final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.MEALS) {
            createMeal(meal, 1);
        }
    }

    @Override
    public Meal createMeal(Meal meal, int userId) {
        meal.setId(counter.incrementAndGet());
        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal updateMeal(Meal meal, int idMeal, int userId) {
        if (get(idMeal, userId).getUserId() == userId) {
            Meal updateMeal = repository.get(meal.getId());
            updateMeal.setDateTime(meal.getDateTime());
            updateMeal.setDescription(meal.getDescription());
            updateMeal.setCalories(meal.getCalories());
            return updateMeal;
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        for (Meal meal : repository.values()) {
            if (meal.getId() == id && meal.getUserId() == userId) {
                repository.remove(id);
                counter.decrementAndGet();
                return true;
            }
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal getMeal = repository.get(id);
        if (getMeal.getUserId() == userId) {
            return getMeal;
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
            .filter(meal -> meal.getUserId() == userId)
            .collect(Collectors.toList());
    }

    public List<Meal> sortMealOfDate(List< Meal> repository) {
        return repository.stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

