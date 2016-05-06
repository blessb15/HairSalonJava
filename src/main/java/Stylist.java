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

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO clients (name, contact) VALUES (:name, :contact)";
      this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).addParameter("contact", this.contact).executeUpdate().getKey();
    }
  }

  public static List<Stylist> all() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT name, contact, id FROM clients";
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT name, contact, id FROM clients WHERE id = :id";
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
      return newStylist.getName().equals(this.getName()) && newStylist.getLastName().equals(this.getLastName());
    }

  }

}
