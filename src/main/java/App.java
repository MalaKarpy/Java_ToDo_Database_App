import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.*;
public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("categories/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/category-form.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());


    get("/categories", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("categories", ToDoCategory.all());
      model.put("template", "templates/categories.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("categories/:id/tasks/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      ToDoCategory category = ToDoCategory.find(Integer.parseInt(request.params(":id")));
      List<Task> tasks = category.getTasks();
      model.put("category", category);
      model.put("tasks", tasks);
      model.put("template", "templates/category-tasks-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/categories", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String input = request.queryParams("name");
      ToDoCategory newToDoCategory = new ToDoCategory(input);
      newToDoCategory.save();
      model.put("categories", ToDoCategory.all());
      model.put("template", "templates/categories.vtl");
      return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    post("/tasks", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      ToDoCategory category = ToDoCategory.find(Integer.parseInt(request.queryParams("catId")));
      int catId = category.getId();

      String description = request.queryParams("taskName");
      Task newTask = new Task(description, catId);
      newTask.save();
      List<Task> tasks = category.getTasks();
      model.put("tasks", tasks);
      model.put("category", category);
      model.put("template", "templates/category.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    }
}
