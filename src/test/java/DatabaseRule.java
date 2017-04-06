import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/recipe_box_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteReviewQuery = "DELETE FROM reviews *;";
      con.createQuery(deleteReviewQuery).executeUpdate();
      String deleteRecipeQuery = "DELETE FROM recipes *;";
      con.createQuery(deleteRecipeQuery).executeUpdate();
      String deleteUserQuery = "DELETE FROM users *;";
      con.createQuery(deleteUserQuery).executeUpdate();
      String deleteTagQuery = "DELETE FROM tags *;";
      con.createQuery(deleteTagQuery).executeUpdate();
      String deleteIngredientQuery = "DELETE FROM ingredients *;";
      con.createQuery(deleteIngredientQuery).executeUpdate();
    }
  }
}
