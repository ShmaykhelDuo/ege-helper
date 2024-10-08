import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrontendTests {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setup() {
        var options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    private void assertValid() {
        assertEquals("EGEHelper", driver.getTitle());
        assertEquals("EGEHelper", driver.findElement(By.id("logo")).getText());
    }

    private void assertNotSignedIn() {
        assertEquals(0, driver.findElements(By.id("user")).size());
    }

    private void assertSignedIn() {
        assertEquals(1, driver.findElements(By.id("user")).size());
    }

    @Test
    public void testSignIn() throws InterruptedException {
        driver.get("http://localhost:8081/");

        assertValid();
        assertNotSignedIn();

        driver.findElement(By.id("input_username")).sendKeys("admin");
        driver.findElement(By.id("input_password")).sendKeys("admin");
        driver.findElement(By.id("signin_button")).click();

        Thread.sleep(2000);

        assertSignedIn();
    }
}
