package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import java.time.LocalDate;
import java.time.LocalTime;
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
            save(meal, 1);
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(),meal);
            return meal;
        } else if (repository.get(meal.getId()).getUserId() == (userId)) {
            meal.setUserId(userId);
            return repository.computeIfPresent(meal.getId(), (a, b) -> meal);
        } else return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(id).getUserId() == userId) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal getMeal = repository.get(id);
        if (getMeal != null && getMeal.getUserId() == userId) {
            return getMeal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilter(LocalDate dateStart, LocalDate dateEnd, LocalTime timeStart, LocalTime timeEnd, int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getDate().isAfter(dateStart) && meal.getDate().isBefore(dateStart) &&
                        meal.getTime().isAfter(timeStart) && meal.getTime().isBefore(timeEnd))
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toList());
    }
}

