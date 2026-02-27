package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.ConfigReader;

public class LoginPage extends BasePage {
    private final ConfigReader config;

    public LoginPage(Page page, ConfigReader config) {
        super(page);
        this.config = config;
    }

    // Locators are kept as methods (instead of fields) so they always resolve against the current page state.
    private Locator usernameField() { return page.locator("input[name='username']"); }
    private Locator passwordField() { return page.locator("input[name='password']"); }
    private Locator loginBtn() { return page.locator("button[type='submit']"); }
    private Locator errorMsg() { return page.locator("p.oxd-text.oxd-text--p.oxd-alert-content-text"); }

    // A simple, stable UI indicator that the Dashboard is loaded (better than only checking URL).
    private Locator dashboardHeader() { return page.locator("h6.oxd-topbar-header-breadcrumb-module"); }


    public void openLoginPage() {
        // We externalize URLs/timeouts in config so tests can switch env without code changes.
        navigateTo(config.getBaseUrl());

        // Small "page ready" wait to avoid typing before the form is rendered.
        usernameField().waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
    }


    public void login(String username, String password) {
        // Using BasePage helpers keeps action + wait logic consistent across pages.
        waitAndFill(usernameField(), username, config.getTimeout());
        waitAndFill(passwordField(), password, config.getTimeout());
        waitAndClick(loginBtn(), config.getTimeout());
    }

    public void loginWithValidUser() {
        login(config.getValidUsername(), config.getValidPassword());
        waitForDashboard(); // optional but recommended
    }

    public void loginWithInvalidUser() {
        login(config.getInvalidUsername(), config.getInvalidPassword());
    }


    public void waitForDashboard() {
        // Use a navigation timeout for URL changes (page loads / redirects can take longer).
        page.waitForURL(config.getDashboardUrl(),
                new Page.WaitForURLOptions().setTimeout(config.getNavigationTimeout()));

        // Extra UI check: ensures page is actually usable after navigation.
        dashboardHeader().waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
    }


    public boolean isDashboardVisible() {
        return dashboardHeader().isVisible();
    }


    public String getErrorMessage() {
        // For negative tests, returning empty string is a simple way to handle "message not found" cases.
        try {
            return waitAndGetText(errorMsg(), config.getTimeout());
        } catch (Exception e) {
            return "";
        }
    }
}
