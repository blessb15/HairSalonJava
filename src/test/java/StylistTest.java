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
    Stylist will = new Stylist("Will","Bates");
    assertTrue(will instanceof Stylist);
  }

  @Test
  public void Stylist_checkifStylistInstiateswithName_Will(){
    Stylist will = new Stylist("Will","Bates");
    assertEquals("Will", will.getName());
  }

  @Test
  public void Stylist_checkifInstanceofStylistSavestoDataBase(){
    Stylist will = new Stylist("Will","Bates");
    will.save();
    assertEquals(1, Stylist.all().size());
  }

  @Test
  public void Stylist_StylistObjectsEqual(){
    Stylist will = new Stylist("Will", "Bates");
    Stylist will2 = new Stylist("Will", "Bates");
    assertTrue(will.equals(will2));
  }

  @Test
  public void Stylist_FindStylistinData(){
    Stylist will = new Stylist("Will", "Bates");
    will.save();
    assertTrue(will.equals(Stylist.find(will.getId())));
  }

}
