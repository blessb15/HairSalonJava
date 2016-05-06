import org.sql2o.*;
import java.util.List;

public class Client {
  private String first_name;
  private String last_name;
  private static int id;

  public Client(String first_name, String last_name) {
    this.first_name = first_name;
    this.last_name = last_name;

  }

  public String getFirstName(){
    return first_name;
  }

  public String getLastName(){
    return last_name;
  }

  public int getId(){
    return id;
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO clients (first_name, last_name) VALUES (:first_name, :last_name)";
      this.id = (int) con.createQuery(sql, true).addParameter("first_name", this.first_name).addParameter("last_name", this.last_name).executeUpdate().getKey();
    }
  }

  public static List<Client> all() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT first_name, last_name, id FROM clients";
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT first_name, last_name, id FROM clients WHERE id = :id";
      Client newClient = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Client.class);
      return newClient;
    }
  }

  @Override
  public boolean equals(Object obj){
    if (!(obj instanceof Client)){
      return false;
    } else {
      Client newClient = (Client) obj;
      return newClient.getFirstName().equals(this.getFirstName()) && newClient.getLastName().equals(this.getLastName());
    }

  }

}
