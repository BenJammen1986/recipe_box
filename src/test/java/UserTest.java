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

  @Test
  public void deleteUser_deletesUserFromDB_true() {
    User testUser = new User("Joe", "joe@email.com");
    testUser.save();
    Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", testUser.getId());
    testRecipe.save();
    Review testReview1 = new Review(testRecipe.getId(), 89, "Good Recipe");
    testReview1.save();
    Review testReview2 = new Review(testRecipe.getId(), 99, "Really Good Recipe");
    testReview2.save();
    int testUserId = testUser.getId();
    int testRecipeId = testRecipe.getId();
    int testReview1Id = testReview1.getId();
    int testReview2Id = testReview2.getId();
    testUser.deleteUser();
    assertEquals(null, User.find(testUserId));
    assertEquals(null, Recipe.find(testRecipeId));
    assertEquals(null, Review.find(testReview1Id));
    assertEquals(null, Review.find(testReview2Id));
  }

  @Test
  public void getRecipes_returnsAllRecipesWithSameUserId_List() {
    User testUser = new User("Joe", "joe@email.com");
    testUser.save();
    Recipe testRecipe1 = new Recipe("pasta", "bake for 45 hours at 3000 degrees", testUser.getId());
    testRecipe1.save();
    Recipe testRecipe2 = new Recipe("salad", "chop up and add dressing", testUser.getId());
    testRecipe2.save();
    Recipe[] recipes = new Recipe[] {testRecipe1, testRecipe2};
    assertTrue(testUser.getRecipes().containsAll(Arrays.asList(recipes)));
  }

  @Test
  public void getUserReviews_returnsAllReviewsForGivenUser_List() {
    User testUser = new User("Joe", "joe@email.com");
    testUser.save();
    Recipe testRecipe1 = new Recipe("pasta", "bake for 45 hours at 3000 degrees", testUser.getId());
    testRecipe1.save();
    Recipe testRecipe2 = new Recipe("salad", "chop up and add dressing", testUser.getId());
    testRecipe2.save();
    Review testReview1 = new Review(testRecipe1.getId(), 89, "Good Recipe");
    testReview1.save();
    Review testReview2 = new Review(testRecipe2.getId(), 99, "Really Good Recipe");
    testReview2.save();
    Review[] reviews = new Review[] {testReview1, testReview2};
    assertTrue(testUser.getUserReviews().containsAll(Arrays.asList(reviews)));
  }

  @Test
  public void getUserRating_returnsAverageRatingForGivenUser_float() {
    User testUser = new User("Joe", "joe@email.com");
    testUser.save();
    Recipe testRecipe1 = new Recipe("pasta", "bake for 45 hours at 300 degrees", testUser.getId());
    testRecipe1.save();
    Recipe testRecipe2 = new Recipe("salad", "chop up and add dressing", testUser.getId());
    testRecipe2.save();
    Review testReview1 = new Review(testRecipe1.getId(), 89, "Good Recipe");
    testReview1.save();
    Review testReview2 = new Review(testRecipe2.getId(), 99, "Really Good Recipe");
    testReview2.save();
    assertEquals(94.0f, testUser.getUserRating(), 0.1);
  }


}
