package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.ConfigReader;

public class DashboardPage {

    private final Page page;
    private final ConfigReader config;

    private final Locator pimMenu;
    private final Locator employeeListTab;

    // Logout locators
    private final Locator userDropdown;
    private final Locator logoutLink;

    public DashboardPage(Page page, ConfigReader config) {
        this.page = page;
        this.config = config;

        this.pimMenu = page.locator("a.oxd-main-menu-item:has-text('PIM')");
        this.employeeListTab = page.locator(".oxd-topbar-body-nav a:has-text('Employee List')");

        // ✅ NEW locator initialization
        this.userDropdown = page.locator(".oxd-userdropdown-tab");
        this.logoutLink = page.locator("a:has-text('Logout')");
    }

    public boolean isPimMenuVisible() {
        pimMenu.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
        return pimMenu.isVisible();
    }

    public void goToEmployeeList() {
        pimMenu.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
        pimMenu.click();

        employeeListTab.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
        employeeListTab.click();

        page.waitForURL("**/pim/viewEmployeeList**",
                new Page.WaitForURLOptions().setTimeout(config.getNavigationTimeout()));
    }

    public boolean isOnEmployeeListPage() {
        return page.url().contains("/pim/viewEmployeeList");
    }

    public void logout() {
        userDropdown.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
        userDropdown.click();

        logoutLink.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
        logoutLink.click();

        // wait until redirected to login page
        page.waitForURL("**/auth/login**",
                new Page.WaitForURLOptions().setTimeout(config.getNavigationTimeout()));
    }
}