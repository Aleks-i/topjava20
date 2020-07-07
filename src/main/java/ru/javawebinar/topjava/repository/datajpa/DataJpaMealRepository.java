package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class DataJpaMealRepository implements MealRepository {
//    private static final Sort SORT_BY_DATE = Sort.by(Sort.Direction.DESC, "date_time");

    private final CrudMealRepository crudRepository;

    @PersistenceContext
    EntityManager em;

    public DataJpaMealRepository(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User userProxy = em.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(userProxy);
            return crudRepository.save(meal);
        }
        else if (em.getReference(Meal.class, meal.getId()).getUser().getId() == userId) {
            meal.setUser(userProxy);
            return crudRepository.save(meal);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        if (get(id, userId) == null) {
            return false;
        }
        else {
            crudRepository.delete(get(id, userId));
            return true;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.findById(id).orElse(null);
        if (meal == null || meal.getUser().getId() != userId) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = crudRepository.findAll();
        return meals.stream()
                .filter(meal -> meal.getUser().getId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        List<Meal> meals = getAll(userId);
        return meals.stream()
                .filter(meal -> meal.getDateTime().isAfter( startDateTime) && meal.getDateTime().isBefore(endDateTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}
