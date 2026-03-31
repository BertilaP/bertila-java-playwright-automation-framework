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

    private Locator usernameField() {
        return page.locator("input[name='username']");
    }

    private Locator passwordField() {
        return page.locator("input[name='password']");
    }

    private Locator loginBtn() {
        return page.locator("button[type='submit']");
    }

    private Locator errorMsg() {
        return page.locator("p.oxd-text.oxd-text--p.oxd-alert-content-text");
    }

    private Locator dashboardHeader() {
        return page.locator("h6.oxd-topbar-header-breadcrumb-module");
    }

    private Locator requiredFieldMsg() {
        return page.locator("span.oxd-input-field-error-message");
    }

    public void openLoginPage() {
        navigateTo(config.getBaseUrl());
        usernameField().waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
    }

    private String safeValue(String value) {
        return value == null ? "" : value;
    }

    public void login(String username, String password) {
        waitAndFill(usernameField(), safeValue(username), config.getTimeout());
        waitAndFill(passwordField(), safeValue(password), config.getTimeout());
        waitAndClick(loginBtn(), config.getTimeout());
    }

    public void loginWithValidUser() {
        login(config.getValidUsername(), config.getValidPassword());
        waitForDashboard();
    }

    public void waitForDashboard() {
        page.waitForURL(config.getDashboardUrl(),
                new Page.WaitForURLOptions().setTimeout(config.getNavigationTimeout()));

        dashboardHeader().waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
    }

    public boolean isDashboardVisible() {
        return dashboardHeader().isVisible();
    }

    public String getErrorMessage() {
        try {
            Locator error = errorMsg();
            error.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
            String text = error.textContent();
            return text != null ? text.trim() : "";
        } catch (Exception e) {
            return "";
        }
    }

    public boolean areRequiredFieldMessagesVisible() {
        try {
            Locator firstRequiredMessage = requiredFieldMsg().first();
            firstRequiredMessage.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
            return firstRequiredMessage.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginPageVisible() {
        try {
            page.waitForURL("**/auth/login",
                    new Page.WaitForURLOptions().setTimeout(config.getNavigationTimeout()));

            usernameField().waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
            return usernameField().isVisible();
        } catch (Exception e) {
            return false;
        }
    }
}