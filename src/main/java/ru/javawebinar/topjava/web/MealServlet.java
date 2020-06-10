package ru.javawebinar.topjava.web;
import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/s")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDaoImpl mealDaoImpl = MealDaoImpl.getINSTANCE();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void init(ServletConfig config) {
        try {
            super.init(config);
        }
        catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealTo> meals = mealDaoImpl.getMealTo();
        request.setAttribute("list", meals);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/meals.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");

        if (action != null && action.equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"), dtf);
            String description = request.getParameter("meal");
            int calories = Integer.parseInt(request.getParameter("calories"));
            mealDaoImpl.updateMeal(id, dateTime, description, calories);
        }
        else if (action!= null && action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDaoImpl.deleteMeal(id);
        }
        else {
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"), dtf);
            String description = request.getParameter("meal");
            int calories = Integer.parseInt(request.getParameter("calories"));
            Meal meal = new Meal(dateTime, description, calories);
            mealDaoImpl.addMeal(meal);
        }
        request.setAttribute("list", mealDaoImpl.getMealTo());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/meals.jsp");
        dispatcher.forward(request, response);
    }
}
