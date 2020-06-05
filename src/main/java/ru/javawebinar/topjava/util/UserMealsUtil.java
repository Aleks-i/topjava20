package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateMapWithCalories = new HashMap<>();
        for (UserMeal meal : meals) {
            dateMapWithCalories.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

 /* my Solution       List<UserMealWithExcess> usersMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            boolean excess = dateMapWithCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                usersMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess));
        }*/
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for(UserMeal userMeal : meals) {
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcesses.add(createTo(userMeal, dateMapWithCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        /*
        Map<LocalDate, Integer> dateMapWithCalories = meals.stream()
                .collect(Collectors.toMap(meals -> meals.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

         */

//Solution Grigory Kislin
        Map<LocalDate,Integer> caloriesSumByDay = meals.stream().collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));

/*  My Solution      List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> userMealWithExcessList.add(
                new UserMealWithExcess(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        caloriesSumByDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)))
                .collect(Collectors.toList());*/
//  Solution Grigory Kislin
        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static UserMealWithExcess createTo(UserMeal meal, boolean excess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
