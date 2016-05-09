import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class ClearRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "Blake", "blake1997");
  }

  protected void after() {
    try(Connection con = DB.sql2o.open()){
      String sqlStylists = "DELETE FROM stylists *;";
      String sqlClients = "DELETE FROM clients *;";
      con.createQuery(sqlStylists).executeUpdate();
      con.createQuery(sqlClients).executeUpdate();
    }
  }
}
