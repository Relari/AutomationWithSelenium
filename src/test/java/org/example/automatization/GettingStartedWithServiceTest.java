package org.example.automatization;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GettingStartedWithServiceTest {

    private static final String FILE_DRIVE = "C:/Users/cld_r/Documents/workspace-maven-proyect/automatization/src/test/resources/chromeDriver/chromedriver.exe";
    private static final String URI_FOR_SEARCH = "http://www.google.com.pe";
    private static final String COMPLEMENT_RESULT_SEARCH = " - Buscar con Google";
    private static final Integer TIME_OUT_SECOND = 10;
    
    private static ChromeDriverService service;
    private WebDriver driver;

    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(
                        new File(FILE_DRIVE))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @Before
    public void createDriver() {
        driver = new RemoteWebDriver(service.getUrl(), new ChromeOptions());
    }

    @Test
    public void testGoogleSearch() {
        driver.manage().window().maximize();
        driver.get(URI_FOR_SEARCH);
        // rest of the test...

        WebElement webElement = driver.findElement(By.name("q"));

        webElement.clear();

        String inputValue = "MitoCode";

        webElement.sendKeys(inputValue);

        webElement.submit();

        driver.manage().timeouts().implicitlyWait(TIME_OUT_SECOND, TimeUnit.SECONDS);

        Assert.assertEquals(inputValue.concat(COMPLEMENT_RESULT_SEARCH), driver.getTitle());
    }

    @After
    public void quitDriver() {
        driver.quit();
    }

    @AfterClass
    public static void stopService() {
        service.stop();
    }
}