import org.junit.*;
import static org.junit.Assert.*;

public class ToDoCategoryTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(ToDoCategory.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
  ToDoCategory firstCategory = new ToDoCategory("Household chores");
  ToDoCategory secondCategory = new ToDoCategory("Household chores");
  assertTrue(firstCategory.equals(secondCategory));
  }

  @Test
  public void save_savesIntoDatabase_true() {
  ToDoCategory myCategory1 = new ToDoCategory("Household chores");

  myCategory1.save();
  ToDoCategory myCategory2 = new ToDoCategory("Repairs chores");
  myCategory2.save();
  assertTrue(ToDoCategory.all().get(0).equals(myCategory1));
  }

  @Test
  public void find_findCategoryInDatabase_true() {
  ToDoCategory myCategory = new ToDoCategory("Household chores");
  myCategory.save();
  ToDoCategory savedCategory = ToDoCategory.find(myCategory.getId());
  assertTrue(myCategory.equals(savedCategory));
  }

  // @Test
  // public void getTasks_retrievesALlTasksFromDatabase_tasksList() {
  //   ToDoCategory myCategory = new ToDoCategory("Household chores");
  //   myCategory.save();
  //   Task firstTask = new Task("Mow the lawn", myCategory.getId());
  //   firstTask.save();
  //   Task secondTask = new Task("Do the dishes", myCategory.getId());
  //   secondTask.save();
  //   Task[] tasks = new Task[] { firstTask, secondTask };
  //   assertTrue(myCategory.getTasks().containsAll(arrays.asList(tasks)));
  // }
}
