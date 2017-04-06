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
    Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    assertTrue(testRecipe instanceof Recipe);
  }

  @Test
  public void getTitle_returnsTitleOfRecipe_pasta() {
    Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    assertEquals("pasta", testRecipe.getTitle());
  }

  @Test
  public void getInstructions_returnsInstructions_true() {
    Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    assertEquals("bake for 45 hours at 3000 degrees", testRecipe.getInstructions());
  }

  @Test
  public void getUserId_returnsUserId_1() {
    Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    assertEquals(1, testRecipe.getUserId());
  }

  @Test
  public void save_savesRecipeIntoDatabase_true() {
    Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    testRecipe.save();
    assertTrue(Recipe.all().get(0).equals(testRecipe));
  }

  @Test
  public void all_returnsAllRecipesInDB_true() {
    Recipe testRecipe1 = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    testRecipe1.save();
    Recipe testRecipe2 = new Recipe("salad", "chop", 1);
    testRecipe2.save();
    assertTrue(Recipe.all().get(0).equals(testRecipe1));
    assertTrue(Recipe.all().get(1).equals(testRecipe2));
  }

  @Test
  public void equals_ifRecipeTitleIsTheSame_true() {
    Recipe testRecipe1 = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    Recipe testRecipe2 = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    assertTrue(testRecipe1.equals(testRecipe2));
  }

  @Test
  public void deleteRecipe_deletesRecipeFromDB_true() {
    Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    testRecipe.save();
    Review testReview1 = new Review(testRecipe.getId(), 89, "Good Recipe");
    testReview1.save();
    Review testReview2 = new Review(testRecipe.getId(), 99, "Really Good Recipe");
    testReview2.save();
    int testRecipeId = testRecipe.getId();
    int testReview1Id = testReview1.getId();
    int testReview2Id = testReview2.getId();
    testRecipe.deleteRecipe();
    assertEquals(null, Recipe.find(testRecipeId));
    assertEquals(null, Review.find(testReview1Id));
    assertEquals(null, Review.find(testReview2Id));
  }

  @Test
  public void addTag_addATagWithRecipeId_true() {
    Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    testRecipe.save();
    testRecipe.addTag("healthy");
    testRecipe.addTag("Christmas");
    testRecipe.addTag("Deli");
    assertTrue(Recipe.getTagId("healthy") > 0);
    assertTrue(Recipe.getTagId("Christmas") > Recipe.getTagId("healthy"));
    assertTrue(Recipe.getTagId("Deli") > Recipe.getTagId("Christmas"));
  }

  @Test
  public void getTags_returnsAllTagsForAGivenRecipe_List() {
    Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    testRecipe.save();
    testRecipe.addTag("healthy");
    testRecipe.addTag("Christmas");
    testRecipe.addTag("Deli");
    testRecipe.addTag("lunch");
    testRecipe.addTag("picnic");
    String[] pastaTags = new String[] {"healthy", "Christmas", "Deli", "lunch", "picnic"};
    assertTrue(Recipe.getTags(testRecipe.getId()).containsAll(Arrays.asList(pastaTags)));
  }

  // @Test
  // public void addIngredient_addAIngredientWithRecipeId_true() {
  //   Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
  //   testRecipe.save();
  //   testRecipe.addIngredient("apple");
  //   testRecipe.addIngredient("milk");
  //   testRecipe.addIngredient("flour");
  //   assertTrue(Recipe.getIngredientId("healthy") > 0);
  //   assertTrue(Recipe.getIngredientId("Christmas") > Recipe.getIngredientId("healthy"));
  //   assertTrue(Recipe.getIngredientId("Deli") > Recipe.getIngredientId("Christmas"));
  // }

  @Test
  public void getIngredients_returnsAllIngredientsForAGivenRecipe_List() {
    Recipe testRecipe = new Recipe("pasta", "bake for 45 hours at 3000 degrees", 1);
    testRecipe.save();
    testRecipe.addIngredient("apple");
    testRecipe.addIngredient("banana");
    testRecipe.addIngredient("Milk");
    testRecipe.addIngredient("pear");
    testRecipe.addIngredient("flour");
    String[] pastaIngredients = new String[] {"apple", "banana", "Milk", "pear", "flour"};
    assertTrue(Recipe.getIngredients(testRecipe.getId()).containsAll(Arrays.asList(pastaIngredients)));
  }


}
