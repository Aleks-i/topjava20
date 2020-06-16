package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal createMeal(Meal meal, int authUserId) {
        return repository.createMeal(meal, authUserId);
    }

    public Meal update(Meal meal, int idMeal, int authUserId) {
        return ValidationUtil.checkNotFoundWithId(repository.updateMeal(meal, idMeal, authUserId), authUserId);
    }

    public void delete(int id, int authUserId) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, authUserId), authUserId);
    }

    public Meal get(int id, int authUserId) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id, authUserId), authUserId);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

}