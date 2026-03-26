package base;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import utils.ConfigReader;

import java.io.ByteArrayInputStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected ConfigReader config;
    protected Page page;

    // JUnit 5 hook: runs after each test and knows whether it failed
    @RegisterExtension
    AfterTestExecutionCallback screenshotOnFailure = new AfterTestExecutionCallback() {
        @Override
        public void afterTestExecution(ExtensionContext context) {
            if (page == null) return;

            // If test failed, capture screenshot and attach to Allure
            if (context.getExecutionException().isPresent()) {
                byte[] screenshotBytes = page.screenshot(
                        new Page.ScreenshotOptions().setFullPage(true)
                );

                Allure.addAttachment(
                        "Failure Screenshot",
                        "image/png",
                        new ByteArrayInputStream(screenshotBytes),
                        ".png"
                );
            }
        }
    };

    @BeforeAll
    public void setupAll() {
        playwright = Playwright.create();
        config = new ConfigReader();

        boolean headless = config.isHeadless();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);

        String browserName = config.getBrowser().toLowerCase();
        System.out.println("Running tests on browser: " + browserName);

        switch (browserName) {
            case "chromium":
                browser = playwright.chromium().launch(options);
                break;
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "webkit":
                browser = playwright.webkit().launch(options);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
    }

    @BeforeEach
    public void setup() {
        page = browser.newPage();
        page.setDefaultTimeout(config.getTimeout());
        page.setDefaultNavigationTimeout(config.getNavigationTimeout());

        Allure.parameter("Browser", config.getBrowser());
    }

    @AfterEach
    public void tearDown() {
        if (page != null) {
            page.close();
        }
    }

    @AfterAll
    public void tearDownAll() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}