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

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void recipe_instantiatesCorrectly_true() {
    Recipe testRecipe = new Recipe("pasta", 1 , "italian");
    assertTrue(testRecipe instanceof Recipe);
  }

  @Test
  public void getTitle_returnsTitleOfRecipe_pasta() {
    Recipe testRecipe = new Recipe("pasta", 1 , "italian");
    assertEquals("pasta", testRecipe.getTitle());
  }

  @Test
  public void getUserId_returnsUserId_1() {
    Recipe testRecipe = new Recipe("pasta", 1 , "italian");
    assertEquals(1, testRecipe.getUserId());
  }

  @Test
  public void getTag_returnsTag_italian() {
    Recipe testRecipe = new Recipe("pasta", 1 , "italian");
    assertEquals("italian", testRecipe.getTag());
  }


  @Test
  public void save_savesRecipeIntoDatabase_true() {
    Recipe testRecipe = new Recipe("pasta", 1 , "italian");
    testRecipe.save();
    assertTrue(Recipe.all().get(0).equals(testRecipe));
  }

  @Test
  public void all_returnsAllRecipesInDB_true() {
    Recipe testRecipe1 = new Recipe("pasta", 1 , "italian");
    testRecipe1.save();
    Recipe testRecipe2 = new Recipe("pasta", 1 , "italian");
    testRecipe2.save();
    assertTrue(Recipe.all().get(0).equals(testRecipe1));
    assertTrue(Recipe.all().get(1).equals(testRecipe2));
  }

  @Test
  public void equals_ifRecipeTitleIsTheSame_true() {
    Recipe testRecipe1 = new Recipe("pasta", 1 , "italian");
    Recipe testRecipe2 = new Recipe("pasta", 1 , "italian");
    assertTrue(testRecipe1.equals(testRecipe2));
  }




}
