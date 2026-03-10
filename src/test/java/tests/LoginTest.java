package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    void setupTest() {
        loginPage = new LoginPage(page, config);
        loginPage.openLoginPage();
    }

    @Test
    @DisplayName("Login succeeds with valid credentials")
    void shouldLoginWithValidCredentials() {
        loginPage.loginWithValidUser();
        // Assertion: confirms we actually landed on Dashboard (not just “no error”)
        assertTrue(loginPage.isDashboardVisible(), "Dashboard header should be visible after login");
    }

    @Test
    @DisplayName("Login fails with invalid credentials and shows error message")
    void shouldShowErrorForInvalidCredentials() {
        loginPage.loginWithInvalidUser();

        String error = loginPage.getErrorMessage();

        // Assertion 1: error should exist (not blank)
        assertFalse(error == null || error.trim().isEmpty(), "Error message should be displayed for invalid login");

        // Assertion 2: error content should mention invalid (case-insensitive)
        assertTrue(error.toLowerCase().contains("invalid"), "Error message should mention 'invalid'");
    }
}