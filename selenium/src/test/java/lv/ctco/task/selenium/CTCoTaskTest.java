package lv.ctco.task.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import lv.ctco.task.selenium.utils.WebDriverWaiter;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class CTCoTaskTest {

    private static final String CT_CO_URL = "https://ctco.lv/";
    private static final String CAREERS_ITEM_ID = "menu-item-127";
    private static final String VACANCIES_ITEM_ID = "menu-item-131";
    private static final String TEST_AUTO_ENGINEER_ITEM_ID = "menu-item-5079";
    private static final String VACANCIES_SECOND_CONTENTS = "'vacancies-second-contents change-content" +
            " vacancies-custom-resize col-md-12 animated fadeIn active'";
    private static final String TEXT_BLOCK = "'text-block'";
    private static final String WAYSIWYG = "'wysiwyg wysiwyg-2'";


    private WebDriver driver;
    private WebDriverWaiter webDriverWaiter;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        webDriverWaiter = new WebDriverWaiter(driver);
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    /**
     * Checking the number of skills in the paragraph Additional sills and qualification required for the higher position
     */
    @Test
    @DisplayName("Check number of skills")
    public void checkCountOfSkills() {
        driver.navigate().to(CT_CO_URL);
        new Actions(driver).moveToElement(webDriverWaiter.waitForClickableElement(By.id(CAREERS_ITEM_ID))).perform();
        webDriverWaiter.waitForClickableElement(By.id(VACANCIES_ITEM_ID)).click();
        webDriverWaiter.waitForClickableElement(By.id(TEST_AUTO_ENGINEER_ITEM_ID)).click();
        WebElement skillsAndQualification = webDriverWaiter.waitForVisibleElement(By.xpath(
                "//div[@class=" + VACANCIES_SECOND_CONTENTS + "]" +
                        "/div[@class=" + TEXT_BLOCK + "]" +
                        "/div[@class=" + WAYSIWYG + "]/ul[2]")
        );
        List<WebElement> list = skillsAndQualification.findElements(By.tagName("li"));
        Assertions.assertEquals(5, list.size(),
                () -> "paragraph under Professional skills and qualification must contain exactly 5 skills," +
                        " but actually size: " + list.size());
    }
}
