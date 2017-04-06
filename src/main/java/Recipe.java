import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Recipe {
  private int id;
  private String title;
  private String instructions;
  private int userId;
  private int tagId;
  private int ingredientId;
  private List<String> tags;
  private List<String> ingredients;


  public Recipe(String title, String instructions, int userId) {
    this.title = title;
    this.instructions = instructions;
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public int getUserId() {
    return userId;
  }

  public String getInstructions() {
    return instructions;
  }

  public int getId() {
    return id;
  }


  public static Integer getTagId(String tag) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id FROM tags WHERE tag = :tag;";
      return con.createQuery(sql)
        .addParameter("tag", tag)
        .executeScalar(Integer.class);
    }
  }

  public static List<String> getTags(int recipeId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT tag FROM tags JOIN recipes ON (recipes.id = tags.recipeId) WHERE recipes.id = :id;";
      return con.createQuery(sql)
        .addParameter("id", recipeId)
        .executeAndFetch(String.class);
    }
  }

  public static List<String> getIngredients(int recipeId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT ingredient FROM ingredients JOIN recipes ON (recipes.id = ingredients.recipeId) WHERE recipes.id = :id;";
      return con.createQuery(sql)
        .addParameter("id", recipeId)
        .executeAndFetch(String.class);
    }
  }

  public void addTag(String tag) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (recipeId, tag) VALUES (:recipeId, :tag);";
      // this.tagId = (int)
      con.createQuery(sql)
        .addParameter("recipeId", this.id)
        .addParameter("tag", tag)
        .executeUpdate();
        // .getKey();
    }
  }

  public void addIngredient(String ingredient) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ingredients (recipeId, ingredient) VALUES (:recipeId, :ingredient);";
      this.ingredientId = (int) con.createQuery(sql, true)
        .addParameter("recipeId", this.id)
        .addParameter("ingredient", ingredient)
        .executeUpdate()
        .getKey();
    }
  }


  public static List<Recipe> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes;";
      return con.createQuery(sql)
        .executeAndFetch(Recipe.class);
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes (title, instructions, userId) VALUES (:title, :instructions, :userId);";
        this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("instructions", this.instructions)
        .addParameter("userId", this.userId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Recipe find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Recipe.class);
    }
  }

  public List<Review> getReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE recipeId = :recipeId;";
      return con.createQuery(sql)
        .addParameter("recipeId", this.id)
        .executeAndFetch(Review.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getTitle().equals(newRecipe.getTitle()) &&
             this.getInstructions().equals(newRecipe.getInstructions()) &&
             this.getUserId() == newRecipe.getUserId() &&
             this.getId() == newRecipe.getId();
    }
  }

  public void deleteRecipe() {
    List<Review> reviews = this.getReviews();
    for (Review review : reviews) {
      review.deleteReview();
    }
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM recipes WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public List<Review> getRecipeReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE recipeId = :recipeId;";
      return con.createQuery(sql)
        .addParameter("recipeId", this.id)
        .executeAndFetch(Review.class);
    }
  }

  public Float getRecipeRating() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT AVG(rating) FROM reviews WHERE recipeId = :recipeId;";
      return con.createQuery(sql)
        .addParameter("recipeId", this.id)
        .executeScalar(Float.class);
    }
  }

  public static List<Recipe> getTopRecipes() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes AS a ORDER BY (SELECT COALESCE(AVG(rating), 0) FROM reviews WHERE recipeId = a.id) desc;";
      return con.createQuery(sql)
        .executeAndFetch(Recipe.class);
    }
  }




}
