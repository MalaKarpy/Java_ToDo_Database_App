import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Task {
  private int id;
  private String description;
  private int categoryId;


  public int getId() {
      return id;
    }

  public String getDescription() {
      return description;
    }

  public int getCategoryId() {
    return categoryId;
  }

  public Task(String description, int categoryId) {
    this.description = description;
    this.categoryId = categoryId;
  }

  public static List<Task> all() {
     String sql = "SELECT id, description, categoryId FROM Tasks";
     try(Connection con = DB.sql2o.open()) {
       return con.createQuery(sql).executeAndFetch(Task.class);
     }
   }

   @Override
   public boolean equals(Object otherTask){
     if (!(otherTask instanceof Task)) {
       return false;
     } else {
       Task newTask = (Task) otherTask;
       System.out.println(this.getCategoryId());
       System.out.println(newTask.getCategoryId());
       return this.getDescription().equals(newTask.getDescription()) &&
              this.getId() == newTask.getId() &&
              this.getCategoryId() == newTask.getCategoryId();
     }
   }

   public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Tasks(description, categoryId) VALUES (:description, :categoryId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", description)
        .addParameter("categoryId", categoryId)
        .executeUpdate()
        .getKey();
    }
  }



   public static Task find(int id) {
   try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM Tasks where id=:id";
    Task task = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Task.class);
    return task;
   }

   }

   public void update(String description) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET description = :description) WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", description)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM tasks WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }



}
