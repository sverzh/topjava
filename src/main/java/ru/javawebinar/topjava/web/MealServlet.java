package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private InMemoryMealStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new InMemoryMealStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String paramId = request.getParameter("id");
        Integer id = null;
        if (!paramId.isEmpty()) {
            id = Integer.valueOf(paramId);
        }
        LocalDateTime localDatetime = LocalDateTime.parse(request.getParameter("datetime"));
        String decription = request.getParameter("description");
        int calories = Integer.valueOf(request.getParameter("calories"));

        Meal meal = new Meal(id, localDatetime, decription, calories);
        storage.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("get all meals");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("listMealsTo", MealsUtil.listMealsTo(storage.getAll(), CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        String paramid = request.getParameter("id");
        int id;
        switch (action) {
            case "delete":
                id = Integer.valueOf(paramid);
                storage.delete(id);
                log.info("delete meal id {}", id);
                response.sendRedirect("meals");
                break;
            case "add":
                Meal meal = new Meal(LocalDateTime.now(), "", 100);
                log.info("add new meal");
                request.setAttribute("meal", meal);
                request.setAttribute("action", "Add");
                request.getRequestDispatcher("/editmeal.jsp").forward(request, response);
                break;
            case "update":
                id = Integer.valueOf(paramid);
                Meal updatingMeal = storage.get(id);
                log.info("update id {}", id);
                request.setAttribute("meal", updatingMeal);
                request.setAttribute("action", "Edit");
                request.getRequestDispatcher("/editmeal.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("meals");
        }

    }
}