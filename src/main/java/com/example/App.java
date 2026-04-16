package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class App 
{
    public static void main( String[] args )
    {
        // 1. Setup Chrome Options to reduce pop-up interference
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/opt/chrome146/chrome");
        options.addArguments("--headless=new");       
        options.addArguments("--no-sandbox");         
        options.addArguments("--disable-dev-shm-usage"); 
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // 2. Navigate directly to the Products page
            driver.get("https://automationexercise.com/products");

            // 3. Search for a product (e.g., 'Men T Shirt')
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_product")));
            searchBox.sendKeys("Men T Shirt");
            
            WebElement searchBtn = driver.findElement(By.id("submit_search"));
            searchBtn.click();

            // 4. Locate the FIRST 'Add to Cart' button in the search results
            // We use (xpath)[1] to ensure we get the very first item displayed
            WebElement firstAddToCartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[@class='productinfo text-center']//a[contains(text(),'Add to cart')])[1]")
            ));

            // 5. Scroll to the element and click using JavaScript
            // This is the "No-Ads" trick: JS clicks the element even if an ad is covering it
            js.executeScript("arguments[0].scrollIntoView(true);", firstAddToCartBtn);
            Thread.sleep(1000); // Wait for scroll to stabilize
            js.executeScript("arguments[0].click();", firstAddToCartBtn);

            System.out.println("First search result added to cart!");

            // 6. Click 'View Cart' on the confirmation modal
            WebElement viewCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//u[contains(text(),'View Cart')]")
            ));
            viewCartBtn.click();

            // 7. Verify the product is in the cart
            wait.until(ExpectedConditions.urlContains("view_cart"));
            System.out.println("Success: Navigated to Cart page.");

        } catch (Exception e) {
            System.err.println("Automation failed: " + e.getMessage());
        } finally {
            // driver.quit(); // Keep open to see the result
        }
    }
}
