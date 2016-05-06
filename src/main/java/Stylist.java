import org.sql2o.*;
import java.util.List;

public class Stylist {
  private String name;
  private String contact;
  private static int id;

  public Stylist(String name, String contact) {
    this.name = name;
    this.contact = contact;

  }

  public String getName(){
    return name;
  }

  public String getContact(){
    return contact;
  }

  public int getId(){
    return id;
  }

  public static List<Stylist> all() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM stylists";
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO stylists (name, contact) VALUES (:name, :contact)";
      this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).addParameter("contact", this.contact).executeUpdate().getKey();
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM stylists WHERE id = :id";
      Stylist newStylist = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Stylist.class);
      return newStylist;
    }
  }

  @Override
  public boolean equals(Object obj){
    if (!(obj instanceof Stylist)){
      return false;
    } else {
      Stylist newStylist = (Stylist) obj;
      return newStylist.getName().equals(this.getName()) && newStylist.getContact().equals(this.getContact());
    }
  }

  public void addClient(Client client) {
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE clients SET stylist_id = :stylist_id WHERE id = :client_id";
      con.createQuery(sql).addParameter("stylist_id", this.id).addParameter("client_id", client.getId()).executeUpdate();
    }
  }

  public Client getClient(int clientId){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM clients WHERE stylist_id = :stylist_id AND id = :id";
      return con.createQuery(sql).addParameter("stylist_id", this.id).addParameter("id", clientId).executeAndFetchFirst(Client.class);
    }
  }

  public List<Client> allClients(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM clients WHERE stylist_id = :id";
      return con.createQuery(sql).addParameter("id", this.id).executeAndFetch(Client.class);
    }
  }
}
