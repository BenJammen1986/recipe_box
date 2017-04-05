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

public class ReviewTest {

  @Rule
  public DatabaseRule databse = new DatabaseRule();

  @Test
  public void review_instantiatesCorrectly_true() {
    Review testReview = new Review(1, 89, "Good Recipe");
    assertTrue(testReview instanceof Review);
  }

  @Test
  public void getRecipeId_returnsCorrectRecipeId_1() {
    Review testReview = new Review(1, 89, "Good Recipe");
    assertEquals(1, testReview.getRecipeId());
  }

  @Test
  public void getRating_returnsCorrectRating_89() {
    Review testReview = new Review(1, 89, "Good Recipe");
    assertEquals(89, testReview.getRating());
  }

  @Test
  public void getReview_returnsCorrectReview_GoodRecipe() {
    Review testReview = new Review(1, 89, "Good Recipe");
    assertEquals("Good Recipe", testReview.getReview());
  }

  @Test
  public void save_savesReviewIntoDatabase_true() {
    Review testReview = new Review(1, 89, "Good Recipe");
    testReview.save();
    assertTrue(Review.all().get(0).equals(testReview));
  }

  @Test
  public void save_assignsAnIdtoReview_true() {
    Review testReview = new Review(1, 89, "Good Recipe");
    testReview.save();
    assertEquals(testReview.getId(), Review.all().get(0).getId());
  }

  @Test
  public void getId_returnsAnId_true() {
    Review testReview = new Review(1, 89, "Good Recipe");
    testReview.save();
    assertTrue(testReview.getId() > 0);
  }

  @Test
    public void find_returnsReviewWithSameId_testReview() {
      Review testReview = new Review(1, 89, "Good Recipe");
      testReview.save();
      assertEquals(testReview, Review.find(testReview.getId()));
    }

  @Test
  public void all_returnsAllReviewsInDB_true() {
    Review testReview1 = new Review(1, 89, "Good Recipe");
    testReview1.save();
    Review testReview2 = new Review(1, 99, "Really Good Recipe");
    testReview2.save();
    assertTrue(Review.all().get(0).equals(testReview1));
    assertTrue(Review.all().get(1).equals(testReview2));
  }

  @Test
  public void equals_ifReviewNameIsTheSame_true() {
    Review testReview1 = new Review(1, 89, "Good Recipe");
    Review testReview2 = new Review(1, 89, "Good Recipe");
    assertTrue(testReview1.equals(testReview2));
  }

}
