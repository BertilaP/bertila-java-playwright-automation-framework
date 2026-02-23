package base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * BasePage contains reusable wrapper methods for common UI actions.
 * This helps keep Page Object classes clean and avoids repeating wait logic.
 */
public class BasePage {

    // Protected so child page classes can directly use the Playwright page instance.
    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    /**
     * Waits for an element before clicking.
     * Explicit timeout is used for cases where element load time varies.
     */
    public void waitAndClick(Locator locator, int timeoutMs) {
        locator.waitFor(new Locator.WaitForOptions().setTimeout(timeoutMs));
        locator.click();
    }

    /**
     * Waits for an element before entering text.
     * Keeps interaction + synchronization logic centralized.
     */
    public void waitAndFill(Locator locator, String text, int timeoutMs) {
        locator.waitFor(new Locator.WaitForOptions().setTimeout(timeoutMs));
        locator.fill(text);
    }

    /**
     * Waits for an element and retrieves its visible text.
     * Useful for validations and error message checks.
     */
    public String waitAndGetText(Locator locator, int timeoutMs) {
        locator.waitFor(new Locator.WaitForOptions().setTimeout(timeoutMs));
        return locator.textContent();
    }

    /**
     * Wrapper for page navigation.
     * Keeping navigation here allows consistent control if behavior changes later.
     */
    public void navigateTo(String url) {
        page.navigate(url);
    }
}
