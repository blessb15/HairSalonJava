import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {
  private String user = "Blake";
  private String pass = "blake1997";

  @Before
  public void setUp(){
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", user, pass);
  }

  @After
  public void teardown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients *";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Client_checkifClientInstiatesCorrectly_true(){
    Client bill = new Client("Bill","Gates");
    assertTrue(bill instanceof Client);
  }

  @Test
  public void Client_checkifClientInstiateswithFirstName_Bill(){
    Client bill = new Client("Bill","Gates");
    assertEquals("Bill", bill.getFirstName());
  }

  @Test
  public void Client_checkifInstanceofClientSavestoDataBase(){
    Client bill = new Client("Bill","Gates");
    bill.save();
    assertEquals(1, Client.all().size());
  }

  @Test
  public void Client_ClientObjectsEqual(){
    Client bill = new Client("Bill", "Gates");
    Client bill2 = new Client("Bill", "Gates");
    assertTrue(bill.equals(bill2));
  }

  @Test
  public void Client_FindClientinData(){
    Client bill = new Client("Bill", "Gates");
    bill.save();
    assertTrue(bill.equals(Client.find(bill.getId())));
  }

}
