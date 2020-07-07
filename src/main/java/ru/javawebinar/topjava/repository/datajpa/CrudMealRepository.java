package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    /*@Transactional
    @Modifying
    @Query("SELECT * FROM Meals m WHERE m.id=:id AND m.user_id=:user_id")
    Meal get(@Param("id") int id, @Param("user_id") int user_id);*/
}
