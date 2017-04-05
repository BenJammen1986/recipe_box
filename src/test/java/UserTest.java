import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class UserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void user_instantiatesCorrectly_true() {
    User testUser = new User("Joe Smith", "js@gmail.com");
    assertTrue(testUser instanceof User);
  }

  @Test
  public void getName_returnsNameOfUser_Joe() {
    User testUser = new User("Joe Smith", "js@gmail.com");
    assertEquals("Joe Smith", testUser.getName());
  }

  @Test
  public void getEmail_returnsEmailOfUser_Joe() {
    User testUser = new User("Joe Smith", "js@gmail.com");
    assertEquals("js@gmail.com", testUser.getEmail());
  }

  @Test
  public void save_savesUserIntoDatabase_true() {
    User testUser = new User("Joe", "joe@email.com");
    testUser.save();
    assertTrue(User.all().get(0).equals(testUser));
  }

  @Test
  public void all_returnsAllUsersInDB_true() {
    User testUser1 = new User("Joe", "joe@email.com");
    testUser1.save();
    User testUser2 = new User("Bob", "bob@email.com");
    testUser2.save();
    assertTrue(User.all().get(0).equals(testUser1));
    assertTrue(User.all().get(1).equals(testUser2));
  }

  @Test
  public void equals_ifUserNameIsTheSame_true() {
    User testUser1 = new User("Joe", "joe@email.com");
    User testUser2 = new User("Joe", "joe@email.com");
    assertTrue(testUser1.equals(testUser2));
  }
}
