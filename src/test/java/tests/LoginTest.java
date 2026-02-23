package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setupTest() {
        // BaseTest creates a fresh Page per test (test isolation: no shared state between tests).
        loginPage = new LoginPage(page, config);

        // Keeping navigation in setup reduces repetition and makes each test focus on only assertions.
        loginPage.openLoginPage();
    }

    @Test
    @DisplayName("Login with valid credentials")
    public void testValidLogin() {
        // Test data is read from config to avoid hardcoding credentials in code.
        loginPage.login(config.getValidUsername(), config.getValidPassword());
        loginPage.waitForDashboard();

        // UI assertion: confirms login succeeded beyond just a URL change.
        assertTrue(loginPage.isDashboardVisible(), "Dashboard header should be visible");
    }

    @Test
    @DisplayName("Login with invalid credentials (Negative)")
    public void testInvalidLogin() {
        loginPage.login(config.getInvalidUsername(), config.getInvalidPassword());

        // Negative assertion: confirm user sees a meaningful validation message.
        String error = loginPage.getErrorMessage();
        assertFalse(error.isEmpty(), "Error message should appear");
        assertTrue(error.toLowerCase().contains("invalid"), "Error message should mention 'invalid'");
    }
}
