package base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import utils.ConfigReader;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected ConfigReader config;
    protected Page page;

    @BeforeAll
    public void setupAll() {
        playwright = Playwright.create();
        config = new ConfigReader();

        boolean headless = config.isHeadless();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);

        String browserName = config.getBrowser().toLowerCase();

        if (browserName.equals("firefox")) {
            browser = playwright.firefox().launch(options);
        } else if (browserName.equals("webkit")) {
            browser = playwright.webkit().launch(options);
        } else {
            // default chromium
            browser = playwright.chromium().launch(options);
        }
    }

    @BeforeEach
    public void setup() {
        page = browser.newPage();

        // Apply timeouts from config
        page.setDefaultTimeout(config.getTimeout());
        page.setDefaultNavigationTimeout(config.getNavigationTimeout());
    }

    @AfterEach
    public void tearDown() {
        if (page != null) {
            page.close();
        }
    }

    @AfterAll
    public void tearDownAll() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
