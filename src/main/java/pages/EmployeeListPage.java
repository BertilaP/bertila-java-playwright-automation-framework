package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.ConfigReader;

public class EmployeeListPage {

    private final Page page;
    private final ConfigReader config;

    public EmployeeListPage(Page page, ConfigReader config) {
        this.page = page;
        this.config = config;
    }

    public void openEmployeeList() {
        page.navigate(config.getEmployeeListUrl());
        page.waitForURL("**/pim/viewEmployeeList**",
                new Page.WaitForURLOptions().setTimeout(config.getNavigationTimeout()));
    }

    public void searchEmployee(String name) {
        Locator employeeNameInput = page.locator("input[placeholder='Type for hints...']").first();

        employeeNameInput.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
        employeeNameInput.click();
        employeeNameInput.fill(name);

        page.waitForTimeout(500);

        Locator searchBtn = page.locator("button[type='submit']");
        searchBtn.click();

        page.locator(".oxd-table-body .oxd-table-row").first()
                .waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
    }

    public String getFirstEmployeeName() {
        Locator firstRow = page.locator(".oxd-table-body .oxd-table-row").first();
        firstRow.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));

        Locator nameCell = firstRow.locator(".oxd-table-cell").nth(2);

        String text = nameCell.textContent();
        return text == null ? "" : text.trim();
    }
}
