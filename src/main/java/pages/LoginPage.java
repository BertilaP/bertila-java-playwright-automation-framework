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

    // Locators are defined as methods so they resolve against the latest DOM state on dynamic pages.
    private Locator usernameField() {
        return page.locator("input[name='username']"); }
    private Locator passwordField() {
        return page.locator("input[name='password']"); }
    private Locator loginBtn() {
        return page.locator("button[type='submit']"); }
    private Locator errorMsg() {
        return page.locator("p.oxd-text.oxd-text--p.oxd-alert-content-text"); }

    // Locator for the Dashboard header to verify the page is loaded.
    private Locator dashboardHeader() {
        return page.locator("h6.oxd-topbar-header-breadcrumb-module"); }


    public void openLoginPage() {
        // Externalized URLs/timeouts in config so tests can switch env without code changes.
        navigateTo(config.getBaseUrl());

        // Small "page ready" wait to avoid typing before the form is loaded.
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
        waitForDashboard(); // Waits for dashboard to load after login to ensure navigation is complete
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
        // For negative tests: return empty string if error message is not found, so assertions can handle validation without throwing exceptions.
        try {
            return waitAndGetText(errorMsg(), config.getTimeout());
        } catch (Exception e) {
            return "";
        }
    }
}
