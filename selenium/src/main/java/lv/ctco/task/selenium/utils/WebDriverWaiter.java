package lv.ctco.task.selenium.utils;

import lombok.extern.slf4j.Slf4j;
import lv.ctco.task.selenium.exception.CTCoSeleniumException;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class WebDriverWaiter {

    private static final long DEFAULT_TIMEOUT = 20;
    private static WebDriverWait instance;
    private final WebDriver driver;

    public WebDriverWaiter(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriverWait getWebDriverWaitInstance() {
        WebDriverWait localInstance = instance;
        if (localInstance == null) {
            synchronized (WebDriverWaiter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
                }
            }
        }
        return localInstance;
    }

    /**
     * Method for finding webElements by timeout
     *
     * @see org.openqa.selenium.support.ui.FluentWait#until
     */
    public WebElement waitForVisibleElement(By locator) {
        WebElement webElement;
        String error = "webElement by locator [" + locator.toString() + "] could not be found";
        try {
            webElement = getWebDriverWaitInstance()
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception ex) {
            log.error(error);
            throw new CTCoSeleniumException(error, ex);
        }
        assertNotNull(webElement, "Element by locator: ["+locator+"] is not visible");
        return webElement;
    }

    public WebElement waitForClickableElement(By locator) {
        WebElement webElement;
        String error = "webElement by locator [" + locator.toString() + "] could not be found";
        try {
            webElement = getWebDriverWaitInstance()
                    .until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception ex) {
            log.error(error);
            throw new CTCoSeleniumException(error, ex);
        }
        assertNotNull(webElement, () -> "Element by locator: ["+locator+"] is not clickable");
        return webElement;
    }
}
