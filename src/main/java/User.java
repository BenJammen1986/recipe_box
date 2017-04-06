import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class User {
  public int id;
  public String name;
  public String email;

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public int getId() {
    return id;
  }

  public List<Recipe> getRecipes() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes WHERE userId = :userId;";
      return con.createQuery(sql)
        .addParameter("userId", this.id)
        .executeAndFetch(Recipe.class);
    }
  }

  public static List<User> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users;";
      return con.createQuery(sql)
        .executeAndFetch(User.class);
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (name, email) VALUES (:name, :email);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("email", this.email)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherUser) {
    if (!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getName().equals(newUser.getName()) &&
             this.getEmail().equals(newUser.getEmail()) &&
             this.getId() == newUser.getId();
    }
  }

  public static User find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
    }
  }

  public void deleteUser() {
    List<Recipe> recipes = this.getRecipes();
    for (Recipe recipe : recipes) {
      recipe.deleteRecipe();
    }
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM users WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public List<Review> getUserReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews AS a WHERE recipeId IN (SELECT id FROM recipes WHERE userId = :userId) ORDER By a.rating desc;";
      return con.createQuery(sql)
        .addParameter("userId", this.id)
        .executeAndFetch(Review.class);
    }
  }

  public Float getUserRating() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT AVG(rating) FROM reviews WHERE recipeId IN (SELECT id FROM recipes WHERE userId = :userId);";
      return con.createQuery(sql)
        .addParameter("userId", this.id)
        .executeScalar(Float.class);
    }
  }
}
