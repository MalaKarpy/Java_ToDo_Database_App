import java.util.List;
import org.sql2o.*;


public class ToDoCategory {
  private String name;
  private int id;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ToDoCategory(String name) {
    this.name = name;
  }

  public static List<ToDoCategory> all() {
      String sql = "SELECT id, name FROM Categories";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(ToDoCategory.class);
      }
    }



    @Override
    public boolean equals(Object otherCategory){
      if (!(otherCategory instanceof ToDoCategory)) {
        return false;
      } else {
        ToDoCategory newCategory = (ToDoCategory) otherCategory;
        return this.getName().equals(newCategory.getName());
      }
    }

    public void save() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO Categories(name) VALUES (:name)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("name", this.name)
          .executeUpdate()
          .getKey();
      }
    }

    public static ToDoCategory find(int id) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM Categories where id=:id";
    ToDoCategory ToDoCategory = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(ToDoCategory.class);
    return ToDoCategory;
    }
  }

  public List<Task> getTasks() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM Tasks where categoryId=:id";
    return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Task.class);
    }
  }
}
