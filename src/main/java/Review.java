import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Review {
  private int id;
  private int recipeId;
  private String review;
  private int rating;

  public Review(int recipeId, int rating, String review) {
    this.recipeId = recipeId;
    this.review = review;
    this.rating = rating;
  }

  public int getId() {
    return id;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public int getRating() {
    return rating;
  }

  public String getReview() {
    return review;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO reviews (recipeId, rating, review) VALUES (:recipeId, :rating, :review);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("recipeId", this.recipeId)
        .addParameter("rating", this.rating)
        .addParameter("review", this.review)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Review> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews;";
      return con.createQuery(sql)
        .executeAndFetch(Review.class);
    }
  }

  @Override
  public boolean equals(Object otherReview) {
    if (!(otherReview instanceof Review)) {
      return false;
    } else {
      Review newReview = (Review) otherReview;
      return this.getRecipeId() == newReview.getRecipeId() &&
             this.getRating() == newReview.getRating() &&
             this.getReview().equals(newReview.getReview()) &&
             this.getId() == newReview.getId();
    }
  }

  public static Review find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Review.class);
    }
  }


}
