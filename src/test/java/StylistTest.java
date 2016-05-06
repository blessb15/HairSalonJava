import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {
  private String user = "Blake";
  private String pass = "blake1997";

  @Before
  public void setUp(){
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", user, pass);
  }

  @After
  public void teardown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists *";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Stylist_checkifStylistInstiatesCorrectly_true(){
    Stylist gill = new Stylist("Gill","Bates");
    assertTrue(gill instanceof Stylist);
  }

  @Test
  public void Stylist_checkifStylistInstiateswithName_Gill(){
    Stylist gill = new Stylist("Gill","Bates");
    assertEquals("Gill", gill.getName());
  }

  @Test
  public void Stylist_checkifInstanceofStylistSavestoDataBase(){
    Stylist gill = new Stylist("Gill","Bates");
    gill.save();
    assertEquals(1, Stylist.all().size());
  }

  @Test
  public void Stylist_StylistObjectsEqual(){
    Stylist gill = new Stylist("Gill", "Bates");
    Stylist gill2 = new Stylist("Gill", "Bates");
    assertTrue(gill.equals(gill2));
  }

  @Test
  public void Stylist_FindStylistinData(){
    Stylist gill = new Stylist("Gill", "Bates");
    gill.save();
    assertTrue(gill.equals(Stylist.find(gill.getId())));
  }

  @Test
  public void Stylist_checkifClientsHoldStylistId(){
    Client bill = new Client("Bill", "Gates");
    Stylist gill = new Stylist("Gill", "Bates");
    bill.save();
    gill.save();
    gill.addClient(bill);
    Stylist foundStylist = Stylist.find(gill.getId());
    assertTrue(bill.equals(foundStylist.getClient(bill.getId())));
  }

}
