package caw_package;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CAW_Class {
	
public static void main(String[] args) {
        
		WebDriver driver;
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		

		try {
            
            WebElement tableDataButton = driver.findElement(By.xpath("/html/body/div/div[3]/details/summary"));
            tableDataButton.click();

            String jsonData = "[{\"name\" : \"Bob\", \"age\" : 20, \"gender\": \"male\"}, {\"name\": \"George\", \"age\" : 42, \"gender\": \"male\"}, {\"name\":\n"
            		+ "\"Sara\", \"age\" : 42, \"gender\": \"female\"}, {\"name\": \"Conor\", \"age\" : 40, \"gender\": \"male\"}, {\"name\":\n"
            		+ "\"Jennifer\", \"age\" : 42, \"gender\": \"female\"}]";

            WebElement inputTextBox = driver.findElement(By.xpath("//*[@id=\"jsondata\"]"));
            inputTextBox.clear();
            inputTextBox.sendKeys(jsonData);

            WebElement refreshTableButton = driver.findElement(By.xpath("//*[@id=\"refreshtable\"]"));
            refreshTableButton.click();

            Thread.sleep(2000); 

            assertTableData(driver, jsonData);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static void assertTableData(WebDriver driver, String expectedData) {
        WebElement table = driver.findElement(By.xpath("//*[@id=\"dynamictable\"]"));
        java.util.List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (int i = 1; i < rows.size(); i++) {
            java.util.List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            String actualData = cells.get(0).getText() + " " + cells.get(1).getText() + " " + cells.get(2).getText();
            System.out.println("Actual Data: " + actualData);
            System.out.println("Expected Data: " + expectedData);
            System.out.println();

            assert actualData.equals(expectedData);
        }
    }

}
