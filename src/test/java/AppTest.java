import org.fluentlenium.adapter.FluentTest;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

  public class AppTest extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public ClearRule clear = new ClearRule();

  @Test
  public void RootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Hairz");
  }

  @Test
  public void StylistCreated(){
    goTo("http://localhost:4567/stylists/new");
    fill("#name").with("Phil");
    fill("#contact").with("503");
    submit(".btn");
    assertThat(pageSource()).contains("Success!");
    }

  @Test
  public void StylistStored(){
    Stylist phil = new Stylist("Phil", "503");
    phil.save();
    goTo("http://localhost:4567/stylists");
    assertThat(pageSource()).contains("Phil");
  }

  @Test
  public void ClientCreated(){
    Stylist phil = new Stylist("Phil", "503");
    phil.save();
    String url = String.format("http://localhost:4567/stylists/%d/clients/new", phil.getId());
    goTo(url);
    fill("#fName").with("Bill");
    fill("#lName").with("Bob");
    submit(".btn");
    assertThat(pageSource()).contains("Success!");
  }

  @Test
  public void ClientStored(){
    Stylist phil = new Stylist("Phil", "503");
    phil.save();
    Client bill = new Client("Bill", "Trill");
    bill.save();
    phil.addClient(bill);
    String url = String.format("http://localhost:4567/stylists/%d", phil.getId());
    goTo(url);
    assertThat(pageSource()).contains("Bill, Trill");
  }
}
