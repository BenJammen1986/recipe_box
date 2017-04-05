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
  private String tag;

  public Recipe(String title, String instructions, int userId, String tag) {
    this.title = title;
    this.instructions = instructions;
    this.userId = userId;
    this.tag = tag;
  }

  public String getTitle() {
    return title;
  }

  public int getUserId() {
    return userId;
  }

  public String getTag() {
    return tag;
  }

  public String getInstructions() {
    return instructions;
  }

  public int getId() {
    return id;
  }

  // public List<String> getIngredients(int recipeId) {
  //   try (Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT ingredients.ingredient FROM ingredients WHERE recipeId = :recipeId;";
  //     return con.createQuery(sql)
  //       .addParameter("recipeId", recipeId)
  //       .executeAndFetch(String.class);
  //   }
  // }

  // public String addIngredient(String ingredient) {
  //   try (Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO ingredients (recipeId, ingredient) VALUES (:recipeId, :ingredient);";
  //       con.createQuery(sql)
  //       .addParameter("recipeId", recipeId)
  //       .addParameter("ingredient", ingredient)
  //       .executeUpdate();
  //   }
  // }

  public static List<Recipe> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes;";
      return con.createQuery(sql)
        .executeAndFetch(Recipe.class);
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes (title, userId, tag) VALUES (:title, :userId, :tag);";
        this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("userId", this.userId)
        .addParameter("tag", this.tag)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getTitle().equals(newRecipe.getTitle()) &&
             this.getUserId() == newRecipe.getUserId() &&
             this.getTag().equals(newRecipe.getTag()) &&
             this.getId() == newRecipe.getId();
    }
  }



}
