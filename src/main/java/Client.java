import org.sql2o.*;
import java.util.List;

public class Client {
  private String first_name;
  private String last_name;

  public Client(String first_name, String last_name) {
    this.first_name = first_name;
    this.last_name = last_name;

  }

  public String getFirstName(){
    return first_name;
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO clients (first_name, last_name) VALUES (:first_name, :last_name)";
      con.createQuery(sql).addParameter("first_name", this.first_name).addParameter("last_name", this.last_name).executeUpdate();
    }
  }

  public static List<Client> all() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT first_name, last_name FROM clients";
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

}
